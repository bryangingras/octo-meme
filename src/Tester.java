
public class Tester {
	
	public static void main(String args[]) {
		ServerConnect con = new ServerConnect();
		String data = "Hello World";
		Message msg = new Message(4, 0, data.length(), data);
		
		con.sendOverSocket(msg);

		System.out.println("Success");
	}
}
