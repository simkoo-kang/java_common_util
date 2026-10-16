/**
 * Config.java
 */
package kr.pe.util.io.que.v2;

import kr.pe.util.io.que.v2.codec.Codec;
import kr.pe.util.io.que.v2.codec.ObjectCodec;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class Config {

	private Codec codec = new ObjectCodec();
	private String name = "default";
	private String baseDir = "/data/appdatas/filequeue";
	private int msgAvgLen = 1024;
	private long readingFileNo = -1L;
	private long readingOffset = 0L;
	private long fileSiz = 1024 * 1024 * 100;
	private boolean bakReadFile = false;

	public boolean isBakReadFile() {
		return this.bakReadFile;
	}

	public void setBakReadFile(boolean bakReadFile) {
		this.bakReadFile = bakReadFile;
	}

	public long getFileSiz() {
		return this.fileSiz;
	}

	public void setFileSiz(long fileSiz) {
		this.fileSiz = fileSiz;
	}

	public void setCodec(Codec codec) {
		this.codec = codec;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setMsgAvgLen(int msgAvgLen) {
		this.msgAvgLen = msgAvgLen;
	}

	public void setReadingFileNo(long readingFileNo) {
		this.readingFileNo = readingFileNo;
	}

	public void setReadingOffset(long readingOffset) {
		this.readingOffset = readingOffset;
	}

	public Codec getCodec() {
		return this.codec;
	}

	public String getName() {
		return this.name;
	}

	public String getBaseDir() {
		return this.baseDir;
	}

	public int getMsgAvgLen() {
		return this.msgAvgLen;
	}

	public long getReadingFileNo() {
		return this.readingFileNo;
	}

	public long getReadingOffset() {
		return this.readingOffset;
	}

	public long getFileSize() {
		return this.fileSiz;
	}
}
