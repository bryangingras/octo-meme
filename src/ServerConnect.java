import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class ServerConnect {
	public ServerConnect(){
		
	}
	
	public void sendOverSocket(Message msg) {
		try {
			Socket socket = new Socket("dsp2014.ece.mcgill.ca", 5000);
			OutputStream out = socket.getOutputStream(); 
		    DataOutputStream dos = new DataOutputStream(out);
		    InputStream in = socket.getInputStream();
		    DataInputStream dis = new DataInputStream(in);
	//		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
			dos.write(msg.toByteArray());
			
			byte[] reader = new byte[msg.size + 16];
			
			int numBytesRead = dis.read(reader);
			int responseType = ByteBuffer.wrap(reader).getInt(0);
			int responseSubType = ByteBuffer.wrap(reader).getInt(4);
			int responseSize = ByteBuffer.wrap(reader).getInt(8);
			int responseData = ByteBuffer.wrap(reader).getInt(12);
			
			System.out.println("response from server (Type): " + responseType);
			System.out.println("response from server (Sub-Type): " + responseSubType);
			System.out.println("response from server (Size): " + responseSize);
			System.out.println("response from server (part of Data): " + responseData);
			System.out.println("Number of bytes read:" + numBytesRead);	
			
			dis.close();
			dos.close();
			socket.close();
		}
		catch(UnknownHostException e) {
			System.out.println("Unknown host: dsp2014.ece.mcgill.ca");
		    System.exit(1);
		}
		catch (IOException e) {
		     System.out.println("No I/O");
		     System.exit(1);
		}
		
	}
}
