/**
 * DataStore.java
 */
package kr.pe.util.io.que.v2.datastore;

import java.io.IOException;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public interface DataStore<E> {

	void put(E element) throws IOException;

	E take() throws IOException;

	void init() throws IOException;

	void close();

	long readingFileOffset();

	long readingFileNo();

	long writingFileNo();

	long writingFileOffset();
}
