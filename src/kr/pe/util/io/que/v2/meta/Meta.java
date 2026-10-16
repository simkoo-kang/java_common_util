/**
 * Meta.java
 */
package kr.pe.util.io.que.v2.meta;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class Meta {
	private long readingFileNo;
	private long readingFileOffset;

	public Meta() {
	}

	public Meta(long readingFileNo, long readingFileOffset) {
		super();
		this.readingFileNo = readingFileNo;
		this.readingFileOffset = readingFileOffset;
	}

	public long getReadingFileNo() {
		return this.readingFileNo;
	}

	public void setReadingFileNo(long readingFileNo) {
		this.readingFileNo = readingFileNo;
	}

	public long getReadingFileOffset() {
		return this.readingFileOffset;
	}

	public void setReadingFileOffset(long readingFileOffset) {
		this.readingFileOffset = readingFileOffset;
	}
}
