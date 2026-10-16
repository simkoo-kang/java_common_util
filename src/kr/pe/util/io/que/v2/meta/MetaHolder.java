/**
 * MetaHolder.java
 */
package kr.pe.util.io.que.v2.meta;

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
public interface MetaHolder {
	void update(long readingFileNo, long readingFileOffset);

	void init() throws IOException;

	long getReadingFileNo();

	long getReadingFileOffset();
}
