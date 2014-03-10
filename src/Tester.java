import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Tester {
	
	public static void main(String args[]) {
		ServerConnect con = new ServerConnect();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String data = null;
		String msgValue = null;
		int msgValueInt = 0;
		
		System.out.println("Welcome to my chat client!" +
				"\r\nHere are the different commands you can use:" +
				"\r\nexit, echo, login, logoff, create user, delete user, create store, send, query");
		
		while(true) {
			System.out.print("Enter your command: ");
			try {
				msgValue = br.readLine();
				if(msgValue.equalsIgnoreCase("exit")) {
					msgValueInt = 0;
				}
				else if(msgValue.equalsIgnoreCase("echo")) {
					msgValueInt = 2;
				}
				else if(msgValue.equalsIgnoreCase("login")) {
					msgValueInt = 3;
				}
				else if(msgValue.equalsIgnoreCase("logoff") || msgValue.equalsIgnoreCase("logout") || msgValue.equalsIgnoreCase("log out")) {
					msgValueInt = 4;
				}
				else if(msgValue.equalsIgnoreCase("create user")) {
					msgValueInt = 5;
				}
				else if(msgValue.equalsIgnoreCase("delete user")) {
					msgValueInt = 6;
				}
				else if(msgValue.equalsIgnoreCase("create store")) {
					msgValueInt = 7;
				}
				else if(msgValue.equalsIgnoreCase("send")) {
					msgValueInt = 8;
				}
				else if(msgValue.equalsIgnoreCase("query")) {
					msgValueInt = 9;
				}
				else {
					msgValueInt = 1;
				}
			}
			catch (IOException ioe) {
		         System.out.println("IO error!");
		         System.exit(1);
			}
			System.out.print("Enter your data: ");
			try {
				data = br.readLine();
			}
			catch (IOException ioe) {
		         System.out.println("IO error!");
		         System.exit(1);
			}
	
			Message msg = new Message(msgValueInt, 0, data);
			
			con.sendOverSocket(msg);
			con.receiveOverSocket();
		}
//		System.out.println("Success");
	}
}
