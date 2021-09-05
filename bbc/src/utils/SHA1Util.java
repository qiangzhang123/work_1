package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class SHA1Util {
	
	public static String sha1(String content){
		MessageDigest md = null;
        String tmpStr = "";
        try {
           md = MessageDigest.getInstance("SHA-1");
           byte[] digest = md.digest(content.getBytes());
           tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
        	throw new RuntimeException(e);
        }
        return tmpStr.toLowerCase();
	}
	/**
	 * 将字节数组转换为十六进制字符丄1�7
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符丄1�7
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}


}
