package message;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageOperation {

	public static void addBytes(ArrayList<Byte> arrayList, byte[] mArr) {
		for( byte b : mArr )
			arrayList.add(b);		
	}
	
	public static void printByteArray(byte[] arr) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : arr) {
	        sb.append(String.format("%02X ", b));
	    }
	    System.out.println(sb.toString());
	}

	public static String[] splitMultipleSpaces(String messageHead) {
		return messageHead.split(" +");		
	}
	
	public static byte[] tobyte(Object[] arrByte) {
		byte[] arrbyte = new byte[arrByte.length];
	    
		for( int i = 0; i < arrByte.length; i++ )
			arrbyte[i] = (Byte)arrByte[i];
		
		return arrbyte;
	}
	
	public static byte[] trim(byte[] mArr) {
		int i = mArr.length - 1;
	    while (i >= 0 && mArr[i] == 0)
	        --i;
	    
        return Arrays.copyOf(mArr, i + 1);
	}
}
