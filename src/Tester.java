import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tester {
	public static boolean isExit = false;
	public static boolean isLoggedin = false;
	public static boolean isQuery = false;
	public static int msgValueInt = 0;

	public static void main(String args[]) {
		final ServerConnect con = new ServerConnect();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String data = null;
		String msgValue = null;
		// int msgValueInt = 0;

		Runnable r = new Runnable() {
			public void run() {
				con.receiveOverSocket();
			}
		};
		new Thread(r).start();

		System.out.println("Welcome to my chat client!"
						+ "\r\nHere are the different commands you can use:"
						+ "\r\nexit, echo, login, logoff, create user, delete user, create store, send, query");

		while (!isExit) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
			System.out.print("Enter your command: ");
			try {
				msgValue = br.readLine();
				if (msgValue.equalsIgnoreCase("exit")) {
					msgValueInt = 0;
					isExit = true;
				} else if (msgValue.equalsIgnoreCase("echo")) {
					msgValueInt = 2;
				} else if (msgValue.equalsIgnoreCase("login")) {
					msgValueInt = 3;
				} else if (msgValue.equalsIgnoreCase("logoff")
						|| msgValue.equalsIgnoreCase("logout")
						|| msgValue.equalsIgnoreCase("log out")) {
					msgValueInt = 4;
				} else if (msgValue.equalsIgnoreCase("create user")) {
					msgValueInt = 5;
				} else if (msgValue.equalsIgnoreCase("delete user")) {
					msgValueInt = 6;
				} else if (msgValue.equalsIgnoreCase("create store")) {
					msgValueInt = 7;
				} else if (msgValue.equalsIgnoreCase("send")) {
					msgValueInt = 8;
				} else if (msgValue.equalsIgnoreCase("query")) {
					msgValueInt = 9;
				} else {
					msgValueInt = 1;
				}
			} catch (IOException ioe) {
				System.out.println("IO error!");
				System.exit(1);
			}
			if (msgValueInt != 0 && msgValueInt != 4 && msgValueInt != 9 && msgValueInt != 7) {
				System.out.print("Enter your data: ");
				try {
					data = br.readLine();
				} catch (IOException ioe) {
					System.out.println("IO error!");
					System.exit(1);
				}
			} else {
				data = "emptystring";
			}
			Message msg = new Message(msgValueInt, 0, data);
			con.sendOverSocket(msg);
			Runnable r2 = new Runnable() {
				public void run() {
					queryMsg(con);
					// con.receiveOverSocket();
				}
			};
			if (msgValueInt == 0 || msgValueInt == 6 || msgValueInt == 4) {
				isLoggedin = false;
			}
			if (msgValueInt == 3 && con.responseSubType == 0) {
	//			System.out.println("Response subtype:" + con.responseSubType);
				isLoggedin = true;
				new Thread(r2).start();
			}
			if (msgValueInt == 9) {
				isQuery = true;
			}
			// con.receiveOverSocket();
		}
		// System.out.println("Success");
	}

	public static void queryMsg(ServerConnect con) {
		while (isLoggedin) {
			String data2 = "nothing";
			Message msg = new Message(9, 0, data2);
			// ServerConnect con = null;
			con.sendOverSocket(msg);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}
}
