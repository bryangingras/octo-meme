import java.nio.ByteBuffer;


public class Message {
	int msgType;
	int subMsgType;
	int size;
	byte[] msgData;
	
	public Message(int msgType, int subMsgType, int size, String data) {
		this.msgType = msgType;
		this.subMsgType = subMsgType;
		this.size = size;
		if(size >= 0 && size <= 262144) {
			this.msgData = data.getBytes();
		}
		else {
			System.out.println("Illegal error size of" + size);
			System.exit(1);
		}
	}
	
	public byte[] toByteArray() {
		byte[] byteArray = new byte[4*3 + 4 * this.size +1];
		
		System.out.println("Size of byte array: "+byteArray.length);
		
		byte[] typeInBytes = ByteBuffer.allocate(4).putInt(this.msgType).array();
		byte[] subTypeInBytes = ByteBuffer.allocate(4).putInt(this.subMsgType).array();
		byte[] sizeInBytes = ByteBuffer.allocate(4).putInt(this.size).array();
		
		System.arraycopy(typeInBytes,0,byteArray,0,4);
		System.arraycopy(subTypeInBytes,0,byteArray,4,4);
		System.arraycopy(sizeInBytes,0,byteArray,8,4);
		System.arraycopy(this.msgData,0,byteArray,12,this.size);
		
		return byteArray;
	}
}
