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
	Socket socket;
	OutputStream out;
	int responseSubType = 1;
	// DataOutputStream dos;
	InputStream in;
	// DataInputStream dis;

	public ServerConnect() {
		try {
			this.socket = new Socket("dsp2014.ece.mcgill.ca", 5001);
			this.out = socket.getOutputStream();
			// this.dos = new DataOutputStream(out);
			this.in = socket.getInputStream();
			// this.dis = new DataInputStream(in);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: dsp2014.ece.mcgill.ca");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Constructor: No I/O");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void sendOverSocket(Message msg) {
		try {
			// Send msg over the Socket to the server
			this.out.write(msg.toByteArray());
			this.out.flush();
			// this.dos.write(msg.toByteArray());
			// this.dos.flush();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: dsp2014.ece.mcgill.ca");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Sender: No I/O");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void receiveOverSocket() {
		while (true) {
			try {
				while (in.available() < 12) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
				byte[] header1 = new byte[4];
				byte[] header2 = new byte[4];
				byte[] sizeInBytes = new byte[4];
				in.read(header1, 0, 4);
				in.read(header2, 0, 4);
				in.read(sizeInBytes, 0, 4);
				int size = ByteBuffer.wrap(sizeInBytes).getInt();
				byte[] data = new byte[size];
				in.read(data, 0, size);
				
				responseSubType = ByteBuffer.wrap(header1).getInt(0);

				String serverResponseData = new String(data);
				if (!(serverResponseData.equals("No messages available"))
						|| Tester.isQuery) {
					if (Tester.msgValueInt == 2) {
						System.out.println("Response Type: "
								+ responseSubType);
					}
					System.out.println("Response sub-type: "
							+ ByteBuffer.wrap(header2).getInt(0));
					if (Tester.msgValueInt == 2) {
						System.out.println("Size of message: " + size);
					}
					System.out.println("response from server (Data): "
							+ serverResponseData);
					Tester.isQuery = false;
				}

				// System.out.println("Number of bytes read:" + numBytesRead);

			} catch (UnknownHostException e) {
				System.out.println("Unknown host: dsp2014.ece.mcgill.ca");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Receiver: No I/O");
				System.exit(1);
			}
		}
	}

	public void closeConnection() {
		try {
			// this.dis.close();
			// this.dos.close();
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: dsp2014.ece.mcgill.ca");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}

	}
}
