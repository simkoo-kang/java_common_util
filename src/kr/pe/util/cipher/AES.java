/**
 * AES.java
 */
package kr.pe.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import kr.pe.util.Enc;

/**
 * PC 2026. 4. 7. 오후 7:55:22
 */
public class AES {

	private static final String PRIVATE_256_KEY = "AES_PRIVATE_KEY_THIS_TEST_32BYTE";
	private static final String PRIVATE_CHARSET = Enc.UTF_8; // UTF-8

	/**
	 * use default key 256 bytes
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCEncode(String plainText) throws Exception {
		return aesCBCEncode(plainText, PRIVATE_256_KEY);
	}

	/**
	 * @param plainText
	 * @param loop      encoding repeat count
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCEncode(String plainText, int loop) throws Exception {
		return aesCBCEncode(plainText, PRIVATE_256_KEY, loop);
	}

	/**
	 * 
	 * @param plainText
	 * @param key
	 * @param loop      encoding repeat count
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCEncode(String plainText, String key, int loop) throws Exception {
		String encodedString = aesCBCEncode(plainText, key);
		if (1 < loop--) {
			while (0 < loop--) {
				encodedString = aesCBCEncode(encodedString, key);
			}
		}
		return encodedString;
	}

	/**
	 * @param plainText
	 * @param key       32 bytes = 256 bits
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCEncode(String plainText, String key) throws Exception {

		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(PRIVATE_CHARSET), "AES");
		IvParameterSpec IV = new IvParameterSpec(key.substring(0, 16).getBytes());

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

		c.init(Cipher.ENCRYPT_MODE, secretKey, IV);

		byte[] encrpytionByte = c.doFinal(plainText.getBytes(PRIVATE_CHARSET));

		return Hex.encodeHexString(encrpytionByte);
	}

	/**
	 * use default key 256 bytes
	 * 
	 * @param encodedText
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCDecode(String encodedText) throws Exception {
		return aesCBCDecode(encodedText, PRIVATE_256_KEY);
	}

	/**
	 * @param encodedText
	 * @param loop        decoding repeat count
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCDecode(String encodedText, int loop) throws Exception {
		return aesCBCDecode(encodedText, PRIVATE_256_KEY, loop);
	}

	/**
	 * @param encodedText
	 * @param key
	 * @param loop        decoding repeat count
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCDecode(String encodedText, String key, int loop) throws Exception {
		String decodedString = aesCBCDecode(encodedText, key);
		if (0 < loop--) {
			while (0 < loop--) {
				decodedString = aesCBCDecode(decodedString, key);
			}
		}
		return decodedString;
	}

	/**
	 * @param encodedText
	 * @param key         32 bytes = 256 bits
	 * @return
	 * @throws Exception
	 */
	public static String aesCBCDecode(String encodedText, String key) throws Exception {

		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(PRIVATE_CHARSET), "AES");
		IvParameterSpec IV = new IvParameterSpec(key.substring(0, 16).getBytes());

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

		c.init(Cipher.DECRYPT_MODE, secretKey, IV);

		byte[] decodeByte = Hex.decodeHex(encodedText.toCharArray());

		return new String(c.doFinal(decodeByte), PRIVATE_CHARSET);
	}

	private static void echo(String str) {
		System.out.println(str + " " + str.length());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String plainText = "victory!963";
		String encodedString;
		int loop = 1;
		try {
			encodedString = aesCBCEncode(plainText, loop);
			echo(encodedString);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String decodedString;
		try {
			decodedString = aesCBCDecode(encodedString, loop);
			echo(decodedString);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
