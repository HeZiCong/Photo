package cc.gzvtc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;

public class Md5CryptUtil {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(Md5CryptUtil.class.getName()
					+ "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	/**
	 * 功能：加盐版的MD5.返回格式为MD5(密码+{盐值})
	 * 
	 */
	public static String getMD5StringWithSalt(String password, String salt) {
		if (password == null) {
			throw new IllegalArgumentException("password不能为null");
		}
		if (!StringUtils.isNotBlank(salt)) {
			throw new IllegalArgumentException("salt不能为空");
		}
		if ((salt.toString().lastIndexOf("{") != -1)
				|| (salt.toString().lastIndexOf("}") != -1)) {
			throw new IllegalArgumentException("salt中不能包含 { 或者 }");
		}
		return getMD5String(password + "{" + salt.toString() + "}");
	}

	/**
	 * 功能：得到文件的md5值。
	 * 
	 */
	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,file.length());
		messagedigest.update(byteBuffer);
		in.close();
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * 功能：得到一个字符串的MD5值。
	 * 
	 */
	public static String getMD5String(String str) {
		return getMD5String(str.getBytes());
	}

	private static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	public static String EncryptPassword(String password,String salt){
   	 final String MAGICPREFIX="$1$";
   	 return Md5Crypt.md5Crypt(password.getBytes(),MAGICPREFIX+salt);
    }
	
	public static String generateSalt(){
		final String ITOA64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder resultSalt=new StringBuilder();
		Random saltRandom=new Random();
		for(int i=0;i<8;i++){
			resultSalt.append(ITOA64.charAt(saltRandom.nextInt(64)));
		}
		return resultSalt.toString();
	}

}
