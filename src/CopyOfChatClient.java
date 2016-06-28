
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import unit.MyJFrame;
import unit.MyJPanel;
import unit.MyJTabbedPane;
import unit.MyJTextPane;
import unit.myCloseJPanelType;

public class CopyOfChatClient implements ActionListener, ItemListener {

	private MyJFrame jframe;
	private MyJTextPane area;
	private MyJTabbedPane showmessage;
	private JTextPane jTextPane;
	private JButton send, connec;
	private static Socket socket = null;
	private static DataOutputStream dos = null;
	private static DataInputStream dis = null;
	private JComboBox box = null;
	private Thread thread = new Thread(new rebackmessage());
	private Vector<String> client = new Vector<String>();
	private String idstr = null;
	private JTextField sockname = null;

	private Date lastdate = null;

	public static void main(String[] args) {
		CopyOfChatClient chatClient = new CopyOfChatClient();
		chatClient.launchFrame();
	}

	private void initShock() {
		try {
			socket = new Socket("127.0.0.1", 8899);
			System.out.println("已连接服务器。。。。");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			thread.start();
			initview(true);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private void launchFrame() {
		jframe = new MyJFrame("聊天系统", 0, 0, 560, 500) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void windowClosed(WindowEvent e) {

				if (idstr != null) {
					disconnection();
				}
				new myCloseJPanelType().dissolveExit(jframe, 1, true);
			}
		};

		// 窗体大小不能改变
		jframe.setResizable(false);

		setshowmessage(jframe);

		setPanel(jframe);

		initview(false);
		// 窗体可见
		jframe.setVisible(true);
	}

	private void disconnection() {
		try {

			disconnecCmd();

			if (dos != null)
				dos.close();
			if (dis != null)
				dis.close();
			if (socket != null)
				socket.close();

			System.out.println("断开连接。。。");
		} catch (IOException t) {
			t.printStackTrace();
		}
	}

	private void setPanel(MyJFrame jframe2) {

		area = new MyJTextPane() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void DocumentEventremove() {
				send.setEnabled(false);
			}

			@Override
			public void DocumentEventinsert() {
				send.setEnabled(true);
			}
		};
		

		JScrollPane scrollPane1 = new JScrollPane(area);
		scrollPane1.setBounds(40, 400, 299, 70);

		send = new JButton("发送");
		send.addActionListener(this);
		send.setBounds(341, 420, 90, 50);
		send.setEnabled(false);
		send.setToolTipText("点击以发送信息");

		connec = new JButton("链接到服务器");
		connec.setToolTipText("点击连接服务器");
		connec.setBounds(435, 50, 115, 30);
		connec.addActionListener(this);

		JTextArea meaaagearea = new JTextArea();
		meaaagearea.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(meaaagearea);
		scrollPane2.setBounds(0, 480, 500, 50);

		box = new JComboBox(client);
		box.setBounds(440, 150, 100, 30);
		box.addItemListener(this);
		JLabel label1 = new JLabel("请选择联系人：");
		label1.setBounds(box.getX(), box.getY() - 30, box.getWidth(),
				box.getHeight());

		JLabel label2 = new JLabel("本机：");
		label2.setBounds(440, 10, 50, 30);
		sockname = new JTextField("未知");
		sockname.setHorizontalAlignment(JTextField.CENTER);
		sockname.setEditable(false);
		sockname.setBounds(label2.getX() + 40, label2.getY(),
				label2.getWidth() + 25, label2.getHeight());

		jframe2.add(sockname);
		jframe2.add(label2);
		jframe2.add(label1);
		jframe2.add(box);
		jframe2.add(scrollPane1);
		jframe2.add(send);
		jframe2.add(connec);
		jframe2.add(scrollPane2);

	}

	private void setshowmessage(MyJFrame jframe2) {

		showmessage = new MyJTabbedPane();

		showmessage.setBounds(0, 0, 434, 400);

		jframe2.add(showmessage);
	}

	private void initview(boolean val) {
		showmessage.setEnabled(val);
		area.setEnabled(val);
		box.setEnabled(val);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 发送按钮
		if (e.getSource() == send) {
			
			String sendtext = area.getText().trim();
			
			area.setText("");
			
			
			
			showmessage.addmessage(box.getSelectedItem().toString(), new MyJPanel(MyJPanel.POST_MESSAGE,
					lastdate, idstr, sendtext));
			lastdate = new Date();
			sendmessage(sendtext);

		}

		// 连接按钮
		if (e.getSource() == connec) {
			connec.setEnabled(false);
			initShock();
		}

	}

	// 通知服务器断开连接
	private void disconnecCmd() {
		try {
			dos.writeUTF("-*-" + "-----fengpiaoxu-----" + idstr
					+ "-----fengpiaoxu-----" + "断开连接");
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	
	//发送信息
	private void sendmessage(String st) {
		try {
			dos.writeUTF(box.getSelectedItem() + "\nfengpiaoxu" + st
					+ "\nfengpiaoxu" + idstr);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class rebackmessage implements Runnable {

		@Override
		public void run() {

			try {
				while (true) {
					String str = dis.readUTF();
					if (str.startsWith("-")) {
						cantant(str);
					} else if (str.startsWith("`fengpiaoxu")) {
						idString(str);
					} else {

						String meaaage[] = str.split("-\n\n\n-");
                        
						if(showmessage.isExit(meaaage[0]))
							box.setSelectedItem(meaaage[0]) ;
						//接收信息
						showmessage.addmessage(meaaage[0], new MyJPanel(
								MyJPanel.GET_MESSAGE, lastdate, meaaage[0],
								meaaage[1]));
						System.out.println(lastdate);
						lastdate = new Date();
						System.out.println(lastdate);
					}
				}
			} catch (SocketException e) {

			} catch (EOFException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 接收联系人信息
	private void cantant(String str) {
		String clients[] = str.split("-");
		client.removeAllElements();
		for (int i = 1; i < clients.length; i++) {
			if (!clients[i].equals(idstr))
				client.addElement(clients[i]);
		}
		if(client.size()>0)
		        box.setSelectedIndex(0) ;
		box.validate() ;
	}

	// 接收本机id
	private void idString(String str) {
		String sid[] = str.split("`fengpiaoxu");
		idstr = sid[1];
		sockname.setText(idstr);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String tip = box.getSelectedItem().toString();
			if (showmessage.isExit(tip)) {
				showmessage.addMYTab(tip);
			} else {
				// 通过title选择未能实现
			}
		}

	}

}
