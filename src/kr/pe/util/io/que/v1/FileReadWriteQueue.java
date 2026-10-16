/**
 * FSQueue.java
 */
package kr.pe.util.io.que.v1;

import java.io.File;
import java.io.IOException;

/**
 * File-based Read/Write Queues 파일 기반 읽기/ 쓰기 큐
 * 
 * admin 2025. 4. 17.
 */
public class FileReadWriteQueue {

	private int entityLimitLength;
	private String path = null;
	/**
	 * File operation examples
	 */
	private Index idx = null;
	private Entity writerHandle = null;
	private Entity readerHandle = null;
	/**
	 * File Job Location Information
	 */
	private int readerIndex = -1;
	private int writerIndex = -1;

	public FileReadWriteQueue(String dir) throws IOException, FileFormatException {
		this(new File(dir));
	}

	/**
	 * Initialize queue storage with fileLimitLength as the maximum size limit for a
	 * single data file in the specified directory. 지정된 디렉토리에 있는 단일 데이터 파일의 최대 크기
	 * 제한으로 fileLimitLength 파일을 사용하여 대기열 저장소를 초기화합니다.
	 *
	 * @param dir               Path to Queue datastore 대기열 데이터스토어의 경로
	 * @param entityLimitLength The size of a single data file cannot exceed 2GB.
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public FileReadWriteQueue(String dir, int entityLimitLength) throws IOException, FileFormatException {
		this(new File(dir), entityLimitLength);
	}

	public FileReadWriteQueue(File dir) throws IOException, FileFormatException {
		this(dir, 1024 * 1024 * 2);
	}

	/**
	 * Initialize queue storage with fileLimitLength as the maximum size limit for a
	 * single data file in the specified directory. 지정된 디렉토리에 있는 단일 데이터 파일의 최대 크기
	 * 제한으로 fileLimitLength 파일을 사용하여 대기열 저장소를 초기화합니다.
	 *
	 * @param dir               Path to Queue datastore 대기열 데이터스토어의 경로
	 * @param entityLimitLength The size of a single data file cannot exceed 2GB.
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public FileReadWriteQueue(File dir, int entityLimitLength) throws IOException, FileFormatException {
		if (dir.exists() == false && dir.isDirectory() == false) {
			if (dir.mkdirs() == false) {
				throw new IOException("create dir error");
			}
		}
		this.entityLimitLength = entityLimitLength;
		this.path = dir.getAbsolutePath();
		// Open index file
		this.idx = new Index(this.path);
		initHandle();
	}

	private void initHandle() throws IOException, FileFormatException {
		this.writerIndex = this.idx.getWriterIndex();
		this.readerIndex = this.idx.getReaderIndex();
		this.writerHandle = new Entity(this.path, this.writerIndex, this.entityLimitLength, this.idx);
		if (this.readerIndex == this.writerIndex) {
			this.readerHandle = this.writerHandle;
		} else {
			this.readerHandle = new Entity(this.path, this.readerIndex, this.entityLimitLength, this.idx);
		}
	}

	/**
	 * When a file's data write reaches fileLimitLength, scroll to the next file
	 * instance. 파일의 데이터 쓰기가 fileLimitLength에 도달하면 다음 파일 인스턴스로 스크롤합니다.
	 * 
	 * @throws IOException
	 * @throws FileFormatException
	 */
	private void rotateNextLogWriter() throws IOException, FileFormatException {
		this.writerIndex = this.writerIndex + 1;
		this.writerHandle.putNextFileNumber(this.writerIndex);
		if (this.readerHandle != this.writerHandle) {
			this.writerHandle.close();
		}
		this.idx.putWriterIndex(this.writerIndex);
		this.writerHandle = new Entity(this.path, this.writerIndex, this.entityLimitLength, this.idx, true);
	}

	/**
	 * Add string to queue storage 대기열 저장소에 문자열 추가하기
	 *
	 * @param message message
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public void add(String message) throws IOException, FileFormatException {
		add(message.getBytes());
	}

	/**
	 * Add a byte array to queue storage
	 *
	 * @param message
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public void add(byte[] message) throws IOException, FileFormatException {
		short status = this.writerHandle.write(message);
		if (status == Entity.WRITEFULL) {
			rotateNextLogWriter();
			status = this.writerHandle.write(message);
		}
		if (status == Entity.WRITESUCCESS) {
			this.idx.incrementSize();
		}
	}

	private byte[] read(boolean commit) throws IOException, FileFormatException {
		byte[] bytes = null;
		try {
			bytes = this.readerHandle.read(commit);
		} catch (FileEOFException e) {
			int nextFileNumber = this.readerHandle.getNextFileNumber();
			this.readerHandle.reset();
			File deleteFile = this.readerHandle.getFile();
			this.readerHandle.close();
			deleteFile.delete();
			// Update the location and index for the next read
			this.idx.putReaderPosition(Entity.MESSAGE_START_POSITION);
			this.idx.putReaderIndex(nextFileNumber);
			if (this.writerHandle.getCurrentFileNumber() == nextFileNumber) {
				this.readerHandle = this.writerHandle;
			} else {
				this.readerHandle = new Entity(this.path, nextFileNumber, this.entityLimitLength, this.idx);
			}
			try {
				bytes = this.readerHandle.read(commit);
			} catch (FileEOFException e1) {
				throw new FileFormatException(e1);
			}
		}
		if (bytes != null && commit) {
			this.idx.decrementSize();
		}
		return bytes;
	}

	/**
	 * Read the data from the queue head but do not remove it.
	 *
	 * @return
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public byte[] readNext() throws IOException, FileFormatException {
		return read(false);
	}

	/**
	 * Retrieve the data that joined the queue first from the queue storage and
	 * remove it.
	 *
	 * @return
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public byte[] readNextAndRemove() throws IOException, FileFormatException {
		return read(true);
	}

	public void clear() throws IOException, FileFormatException {
		this.idx.clear();
		initHandle();
	}

	public void close() throws IOException {
		this.readerHandle.close();
		this.writerHandle.close();
		this.idx.close();
	}

	public int getQueueSize() {
		return this.idx.getSize();
	}
}
