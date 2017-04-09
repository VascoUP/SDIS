package message;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * This class builds the message's operations
 *
 */
public class MessageOperation {

	/**
	 * Adds bytes to an ArrayList of bytes
	 * @param arrayList ArrayList of bytes
	 * @param mArr Bytes to be added
	 */
	public static void addBytes(ArrayList<Byte> arrayList, byte[] mArr) {
		for( byte b : mArr )
			arrayList.add(b);		
	}
	
	/**
	 * Prints the byte's array
	 * @param arr Byte's array that will be printed
	 */
	public static void printByteArray(byte[] arr) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : arr) {
	        sb.append(String.format("%02X ", b));
	    }
	    System.out.println(sb.toString());
	}

	/**
	 * Splits the message's header by multiple spaces
	 * @param messageHead Message's header
	 * @return The message's splitted
	 */
	public static String[] splitMultipleSpaces(String messageHead) {
		return messageHead.split(" +");		
	}
	
	/**
	 * Converts a object's array into a byte's array
	 * @param arrByte Object's array
	 * @return The byte's array converted
	 */
	public static byte[] tobyte(Object[] arrByte) {
		byte[] arrbyte = new byte[arrByte.length];
	    
		for( int i = 0; i < arrByte.length; i++ )
			arrbyte[i] = (Byte)arrByte[i];
		
		return arrbyte;
	}
	
	/**
	 * Removes the spaces from the message (byte's array)
	 * @param mArr Byte's array that will be analyzed
	 * @return The byte's array after the trim operation
	 */
	public static byte[] trim(byte[] mArr) {
		int i = mArr.length - 1;
	    while (i >= 0 && mArr[i] == 0)
	        --i;
	    
        return Arrays.copyOf(mArr, i + 1);
	}
}
