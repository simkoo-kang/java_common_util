/**
 * Index.java
 */
package kr.pe.util.io.que.v1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data Index File 데이터 인덱스 파일
 * admin  2025. 4. 17.
 */
public class Index {

	private static final int INDEX_LIMIT_LENGTH = 32;
	private static final String INDEX_FILE_NAME = "filequeue.idx";

	private RandomAccessFile dbRandFile = null;
	private FileChannel fc;
	private MappedByteBuffer mappedByteBuffer;

	/**
	 * File operation location information  파일 작업 위치 정보
	 */
	private String magicString = null;
	private int version = -1;
	private int readerPosition = -1;
	private int writerPosition = -1;
	private int readerIndex = -1;
	private int writerIndex = -1;
	private AtomicInteger size = new AtomicInteger();

	public Index(String path) throws IOException, FileFormatException {
		File dbFile = new File(path, INDEX_FILE_NAME);

		// Create a file if it does not exist  파일이 존재하지 않으면 생성.
		if (dbFile.exists() == false) {
			dbFile.createNewFile();
			this.dbRandFile = new RandomAccessFile(dbFile, "rwd");
			initIdxFile();
		} else {
			this.dbRandFile = new RandomAccessFile(dbFile, "rwd");
			if (this.dbRandFile.length() < INDEX_LIMIT_LENGTH) {
				throw new FileFormatException("file format error.");
			}
			byte[] bytes = new byte[INDEX_LIMIT_LENGTH];
			this.dbRandFile.read(bytes);
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			bytes = new byte[Entity.MAGIC.getBytes().length];
			buffer.get(bytes);
			this.magicString = new String(bytes);
			this.version = buffer.getInt();
			this.readerPosition = buffer.getInt();
			this.writerPosition = buffer.getInt();
			this.readerIndex = buffer.getInt();
			this.writerIndex = buffer.getInt();
			int sz = buffer.getInt();
			if (this.readerPosition == this.writerPosition && this.readerIndex == this.writerIndex && sz <= 0) {
				initIdxFile();
			} else {
				this.size.set(sz);
			}
		}
		this.fc = this.dbRandFile.getChannel();
		this.mappedByteBuffer = this.fc.map(MapMode.READ_WRITE, 0, INDEX_LIMIT_LENGTH);
	}

	private void initIdxFile() throws IOException {
		this.magicString = Entity.MAGIC;
		this.version = 1;
		this.readerPosition = Entity.MESSAGE_START_POSITION;
		this.writerPosition = Entity.MESSAGE_START_POSITION;
		this.readerIndex = 1;
		this.writerIndex = 1;
		this.dbRandFile.setLength(32);
		this.dbRandFile.seek(0);
		this.dbRandFile.write(this.magicString.getBytes());// magic
		this.dbRandFile.writeInt(this.version);// 8 version
		this.dbRandFile.writeInt(this.readerPosition);// 12 reader position
		this.dbRandFile.writeInt(this.writerPosition);// 16 write position
		this.dbRandFile.writeInt(this.readerIndex);// 20 reader index
		this.dbRandFile.writeInt(this.writerIndex);// 24 writer index
		this.dbRandFile.writeInt(0);// 28 size
	}

	public void clear() throws IOException {
		this.mappedByteBuffer.clear();
		this.mappedByteBuffer.force();
		initIdxFile();
	}

	/**
	 * Record write location  기록 작성 위치
	 *
	 * @param pos
	 */
	public void putWriterPosition(int pos) {
		this.mappedByteBuffer.position(16);
		this.mappedByteBuffer.putInt(pos);
		this.writerPosition = pos;
	}

	/**
	 * Record the location you read.  읽은 위치를 기록합니다.
	 *
	 * @param pos
	 */
	public void putReaderPosition(int pos) {
		this.mappedByteBuffer.position(12);
		this.mappedByteBuffer.putInt(pos);
		this.readerPosition = pos;
	}

	/**
	 * Record write file index  기록 쓰기 파일 인덱스
	 *
	 * @param index
	 */
	public void putWriterIndex(int index) {
		this.mappedByteBuffer.position(24);
		this.mappedByteBuffer.putInt(index);
		this.writerIndex = index;
	}

	/**
	 * Read File Index  읽기 파일 인덱스
	 *
	 * @param index
	 */
	public void putReaderIndex(int index) {
		this.mappedByteBuffer.position(20);
		this.mappedByteBuffer.putInt(index);
		this.readerIndex = index;
	}

	public void incrementSize() {
		int num = this.size.incrementAndGet();
		this.mappedByteBuffer.position(28);
		this.mappedByteBuffer.putInt(num);
	}

	public void decrementSize() {
		int num = this.size.decrementAndGet();
		this.mappedByteBuffer.position(28);
		this.mappedByteBuffer.putInt(num);
	}

	public String getMagicString() {
		return this.magicString;
	}

	public int getVersion() {
		return this.version;
	}

	public int getReaderPosition() {
		return this.readerPosition;
	}

	public int getWriterPosition() {
		return this.writerPosition;
	}

	public int getReaderIndex() {
		return this.readerIndex;
	}

	public int getWriterIndex() {
		return this.writerIndex;
	}

	public int getSize() {
		return this.size.get();
	}

	/**
	 * Close index file
	 */
	public void close() throws IOException {
		this.mappedByteBuffer.force();
		this.mappedByteBuffer.clear();
		this.fc.close();
		this.dbRandFile.close();
		this.mappedByteBuffer = null;
		this.fc = null;
		this.dbRandFile = null;
	}

	public String headerInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(" magicString:");
		sb.append(this.magicString);
		sb.append(" version:");
		sb.append(this.version);
		sb.append(" readerPosition:");
		sb.append(this.readerPosition);
		sb.append(" writerPosition:");
		sb.append(this.writerPosition);
		sb.append(" size:");
		sb.append(this.size);
		sb.append(" readerIndex:");
		sb.append(this.readerIndex);
		sb.append(" writerIndex:");
		sb.append(this.writerIndex);
		return sb.toString();
	}
}
