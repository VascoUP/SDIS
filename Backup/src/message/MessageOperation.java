package message;

import java.util.Arrays;

class MessageOperation {

    public static String[] splitMultipleSpaces(String messageHead) {
		return messageHead.split(" +");		
	}

    public static byte[] trim(byte[] mArr) {
		int i = mArr.length - 1;
	    while (i >= 0 && mArr[i] == 0)
	        --i;
	    
        return Arrays.copyOf(mArr, i + 1);
	}
}
