
public class Tester {
	
	public static void main(String args[]) {
		ServerConnect con = new ServerConnect();
		String data = "1";
		Message msg = new Message(2, 6, data.length(), data);
		
		con.sendOverSocket(msg);

		System.out.println("Success");
	}
}
