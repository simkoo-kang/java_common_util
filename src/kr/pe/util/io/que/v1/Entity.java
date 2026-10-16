/**
 * 
 */
package kr.pe.util.io.que.v1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Data file 데이터 파일
 */
public class Entity {

	public static final byte WRITESUCCESS = 1;
	public static final byte WRITEFAILURE = 2;
	public static final byte WRITEFULL = 3;
	public static final String MAGIC = "FQueuefs";
	public static int MESSAGE_START_POSITION = 20;

	private static final String DB_FILE_PREFIX = "filequeue_";
	private static final String DB_FILE_SUFFIX = ".db";
	private File file;
	private RandomAccessFile raFile;
	private FileChannel fc;
	MappedByteBuffer mappedByteBuffer;
	private Index idx = null;
	private int fileLimitLength;
	/**
	 * 文件操作位置信息
	 */
	private String magicString = null;
	private int version = -1;
	private int readerPosition = -1;
	private int writerPosition = -1;
	private int endPosition = -1;
	private int currentFileNumber = -1;
	private int nextFileNumber = -1;

	public Entity(String path, int fileNumber, int fileLimitLength, Index db) throws IOException, FileFormatException {
		this(path, fileNumber, fileLimitLength, db, false);
	}

	public Entity(String path, int fileNumber, int fileLimitLength, Index idx, boolean create)
			throws IOException, FileFormatException {
		this.currentFileNumber = fileNumber;
		this.fileLimitLength = fileLimitLength;
		this.idx = idx;
		this.file = getIdbFile(path, fileNumber);
		// Create a file if it does not exist 파일이 존재하지 않으면 생성.
		if (!this.file.exists() || create) {
			createLogEntity();
		} else {
			this.raFile = new RandomAccessFile(this.file, "rwd");
			int fileLength = (int) this.raFile.length();
			if (fileLength < MESSAGE_START_POSITION) {
				throw new FileFormatException("file format error");
			}
			// If the original file size is larger than the defined length,
			// the file size should be based on the actual size to prevent data
			// from being truncated when it is not read.
			// 원본 파일 크기가 정의된 길이보다 큰 경우,
			// 데이터를 읽지 않을 때 잘리지 않도록 실제 크기를 기준으로 파일 크기를 조정해야 합니다.
			if (fileLimitLength < fileLength) {
				fileLimitLength = fileLength;
				this.fileLimitLength = fileLength;
			}
			this.fc = this.raFile.getChannel();
			this.mappedByteBuffer = this.fc.map(FileChannel.MapMode.READ_WRITE, 0, fileLimitLength);
			// magicString
			byte[] bytes = new byte[8];
			this.mappedByteBuffer.get(bytes);
			this.magicString = new String(bytes);
			if (this.magicString.equals(MAGIC) == false) {
				throw new FileFormatException("file format error");
			}
			// version
			this.version = this.mappedByteBuffer.getInt();
			// nextFileNumber
			this.nextFileNumber = this.mappedByteBuffer.getInt();
			this.endPosition = this.mappedByteBuffer.getInt();
			if (this.endPosition == -1) { // 未写满
				this.writerPosition = idx.getWriterPosition();
			} else { // 已写满
				this.writerPosition = this.endPosition;
			}
			if (idx.getReaderIndex() == this.currentFileNumber) {
				this.readerPosition = idx.getReaderPosition();
			} else {
				this.readerPosition = MESSAGE_START_POSITION;
			}
		}
		Thread t = new Thread(new Sync());
		t.setDaemon(true);
		t.start();
	}

	public static File getIdbFile(String path, int fileNumber) {
		return new File(path, DB_FILE_PREFIX + fileNumber + DB_FILE_SUFFIX);
	}

	public int getCurrentFileNumber() {
		return this.currentFileNumber;
	}

	public int getNextFileNumber() {
		return this.nextFileNumber;
	}

	private boolean createLogEntity() throws IOException {
		this.raFile = new RandomAccessFile(this.file, "rwd");
		this.raFile.setLength(0);
		this.fc = this.raFile.getChannel();
		this.mappedByteBuffer = this.fc.map(FileChannel.MapMode.READ_WRITE, 0, this.fileLimitLength);
		this.mappedByteBuffer.put(MAGIC.getBytes());
		this.mappedByteBuffer.putInt(this.version);// 8 version
		this.mappedByteBuffer.putInt(this.nextFileNumber);// 12 next fileindex
		this.mappedByteBuffer.putInt(this.endPosition);// 16
		this.magicString = MAGIC;
		this.writerPosition = MESSAGE_START_POSITION;
		this.readerPosition = MESSAGE_START_POSITION;
		this.idx.putWriterPosition(this.writerPosition);
		return true;
	}

	public void reset() throws IOException {
		this.version = -1;
		this.endPosition = -1;
		this.currentFileNumber = -1;
		this.nextFileNumber = -1;
		this.mappedByteBuffer.position(0);
		this.mappedByteBuffer.put(MAGIC.getBytes());
		this.mappedByteBuffer.putInt(this.version);// 8 version
		this.mappedByteBuffer.putInt(this.nextFileNumber);// 12 next fileindex
		this.mappedByteBuffer.putInt(this.endPosition);// 16
		this.mappedByteBuffer.force();
		this.magicString = MAGIC;
		this.writerPosition = MESSAGE_START_POSITION;
		this.readerPosition = MESSAGE_START_POSITION;
	}

	/**
	 * write next File number id.
	 *
	 * @param number
	 */
	public void putNextFileNumber(int number) throws IOException {
		this.mappedByteBuffer.position(12);
		this.mappedByteBuffer.putInt(number);
		this.nextFileNumber = number;
	}

	public boolean isFull(int increment) {
		// confirm if the file is full
		if (this.fileLimitLength < this.writerPosition + increment) {
			return true;
		}
		return false;
	}

	public byte write(byte[] bytes) throws IOException {
		int increment = bytes.length + 4;
		if (isFull(increment)) {
			this.mappedByteBuffer.position(16);
			this.mappedByteBuffer.putInt(this.writerPosition);
			this.endPosition = this.writerPosition;
			return WRITEFULL;
		}
		this.mappedByteBuffer.position(this.writerPosition);
		this.mappedByteBuffer.putInt(bytes.length);
		this.mappedByteBuffer.put(bytes);
		this.writerPosition += increment;
		this.idx.putWriterPosition(this.writerPosition);
		return WRITESUCCESS;
	}

	/**
	 * @param commit 如果为 false，则只读取数据，不移动读取指针。
	 * @return
	 * @throws IOException
	 * @throws FileEOFException
	 */
	public byte[] read(boolean commit) throws IOException, FileEOFException {
		if (this.endPosition != -1 && this.readerPosition >= this.endPosition) {
			throw new FileEOFException("file eof");
		}
		if (this.readerPosition >= this.writerPosition) {
			return null;
		}
		this.mappedByteBuffer.position(this.readerPosition);
		int length = this.mappedByteBuffer.getInt();
		byte[] bytes = new byte[length];
		this.mappedByteBuffer.get(bytes);
		if (commit) {
			this.readerPosition += length + 4;
			this.idx.putReaderPosition(this.readerPosition);
		}
		return bytes;
	}

	public byte[] read() throws IOException, FileEOFException {
		return read(true);
	}

	public File getFile() {
		return this.file;
	}

	public void close() throws IOException {
		if (this.mappedByteBuffer == null) {
			return;
		}
		this.mappedByteBuffer.force();
		this.mappedByteBuffer = null;
		this.fc.close();
		this.raFile.close();
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
		sb.append(" nextFileNumber:");
		sb.append(this.nextFileNumber);
		sb.append(" endPosition:");
		sb.append(this.endPosition);
		sb.append(" currentFileNumber:");
		sb.append(this.currentFileNumber);
		return sb.toString();
	}

	private class Sync implements Runnable {
		public Sync() {
			super();
		}

		@Override
		public void run() {
			while (true) {
				if (Entity.this.mappedByteBuffer != null) {
					try {
						Entity.this.mappedByteBuffer.force();
					} catch (Exception e) {
						break;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						break;
					}
				} else {
					break;
				}
			}
		}
	}
}
