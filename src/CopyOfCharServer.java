
import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyOfCharServer {

	private static ServerSocket ssocket = null ;
    private boolean stared = false ;
    //private List<Client> clients = new ArrayList<Client>() ;
    private HashMap<String, Client> clients = new HashMap<String, Client>() ;
    private int n = 0 ;
    private String flag = "-";
    private String Keys[] ;
	public static void main(String[] args) {
		new CopyOfCharServer().initServer();
	}

	private void initServer() {
		DataOutputStream dos0 = null ;
		try {
			ssocket = new ServerSocket(8899);
			stared = true;
		} catch (SocketException e1) {
			System.out.println("端口使用中。。。");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			while (stared) {
				
				Socket socket = ssocket.accept();
				System.out.println(socket.getInetAddress().toString());
				Client c = new Client(socket);
				c.name = "`fengpiaoxu"+"客户端"+(n);
				idCmd(c, dos0, c.name) ;
				System.out.println("一个客户端连接。。。。。");
				
				new Thread(c).start();
				clients.put(c.name, c);
				flag = flag +"客户端"+(n++)+ "-";
				System.out.println("连接："+flag);
				contantCmd(dos0,flag);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ssocket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	//发送联系人信息
	private void contantCmd(DataOutputStream out,String str){
		try {
				Keys = flag.split("-") ;
				for (int i = 0; i < Keys.length; i++) {
					Client c = clients.get("`fengpiaoxu"+Keys[i]);
					if (c != null) {
						out = new DataOutputStream(c.socket.getOutputStream());
						out.writeUTF(str);
					}
				}
			
		}catch(SocketException e){
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//返回服务器为客户端所设置的id
	private void idCmd(Client c, DataOutputStream out, String str) {
		try {
			if (c != null) {
				out = new DataOutputStream(c.socket.getOutputStream());
				out.writeUTF(str);
			}

		} catch (SocketException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Client implements Runnable {
		private Socket socket = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null ;
		private boolean connected = false;
		private String name = null;
        
		public Client(Socket socket) {
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream()) ;
				connected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void send(String str){
			try {
				dos.writeUTF(str) ;
			}catch(SocketException e){
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			try {
				while (connected) {
					String s = dis.readUTF();
					if(s.startsWith("-*-") && s.endsWith("断开连接")) {
						String ks[] = s.split("-----fengpiaoxu-----") ;
						flag = flag.replace(ks[1]+"-", "") ;
						clients.remove("`fengpiaoxu"+ks[1]) ;
						Chartpublic(flag);
					} else {
						String si[] = s.split("\nfengpiaoxu");
						Keys = flag.split("-") ;
							for (int i = 0; i < Keys.length; i++) {
								if (Keys[i].equals(si[0])) {
									//si[2]发送人 si[1]发送信息
									Chartprivate(clients.get("`fengpiaoxu"+Keys[i]), si[2] + "-\n\n\n-"
											+ si[1]);
								}
							}
						
					}
				}
			} catch (SocketException e) {
				// clients.remove(this) ;
				//e.printStackTrace();
			} catch (EOFException e0) {
				System.out.println("一个客户端断开连接。。。。");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (socket != null)
						socket.close();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		}
		
		//公开聊天
		private void Chartpublic(String str) {
			Keys = flag.split("-") ;
			for (int i = 0; i < Keys.length; i++) {
				Client c = clients.get("`fengpiaoxu" + Keys[i]);

				if (c == null) {
					clients.remove(c);
				} else {
					c.send(str);
				}

			}
		}
		
		//私聊
		private void Chartprivate(Client client,String str){
			if(client != null){
			       client.send(str);
			}else{
				System.out.println("该客户端已退出。。。");
			}
			
		}
		
		
		
		
	}
}
