/**
 * StreamUtils.java
 */
package kr.pe.util.io.que.v2;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class StreamUtils {
	public static int readFully(RandomAccessFile file, byte[] b, int off, int len) throws IOException {
		if (len < 0) {
			throw new IndexOutOfBoundsException();
		}

		int n = 0;
		while (n < len) {
			int count = file.read(b, off + n, len - n);
			if (count < 0) {
				throw new EOFException("Can not read data. Expected to " + len + " bytes, read " + n
						+ " bytes before inputstream close.");
			}

			n += count;
		}
		return n;
	}
}
