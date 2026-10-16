/**
 * Codec.java
 */
package kr.pe.util.io.que.v2.codec;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public interface Codec {

	byte[] encode(Object element);

	Object decode(byte[] bytes);

}
