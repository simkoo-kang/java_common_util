/**
 * EncryptUtils.java
 */
package kr.pe.util.io.que.v2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class EncryptUtils {

	public static byte[] sha1(byte[] data) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			return mDigest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			return new byte[40];
		}
	}

	public static void main(String[] args) {
		System.out.println(sha1(new byte[] { 1, 2 }).length);
	}
}
