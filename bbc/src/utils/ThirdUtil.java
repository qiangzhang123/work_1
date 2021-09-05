package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class ThirdUtil {
	
	
	public static String mask(String mask) {
	    StringBuffer maskStr = new StringBuffer();
	    int lenth = mask.length();
	    int maskLenth = 4;
		maskStr.append(mask.substring(0, 3));
		maskStr.append(String.format("%1$-" + maskLenth + "s", new Object[] { "" }).replace(" ", "*"));
    	maskStr.append(mask.substring(lenth - 4, lenth));
        return maskStr.toString();
    }
	
	/**  base64转string     **/
	public static String base64decode(String data){
		try {
			if(StringUtils.isEmpty(data)){
				return data;
			}
			data = URLDecoder.decode(new String(Base64.decodeBase64(data)), "UTF-8");
		} catch (Exception e) {
		}
		return data;
	}
	public static String urlDecode(String urlData){
		String url = null;
		try {
			url = URLDecoder.decode(urlData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	public static String urlEncode(String urlData){
		if (StringUtils.isEmpty(urlData)) {
			return "";
		}
		String url = null;
		try {
			url = URLEncoder.encode(urlData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static byte[] getBytes(String data){
		byte[] bytes = null;
		try {
			bytes = data.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	public static String getString(byte[] bytes){
		String value = null;
		try {
			value = new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	}   
	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	}   
	/**  
	 * Convert char to byte  
	 * @param c char  
	 * @return byte  
	 */  
	 private static byte charToByte(char c) {   
	    return (byte) "0123456789ABCDEF".indexOf(c);   
	}  
}
