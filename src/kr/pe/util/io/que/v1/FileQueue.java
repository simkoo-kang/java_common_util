/**
 * FQueue.java
 */
package kr.pe.util.io.que.v1;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Persistent queues based on file systems 파일 시스템 기반의 영구 대기열
 * 
 * admin 2025. 4. 17.
 */
public class FileQueue extends AbstractQueue<byte[]> implements Serializable {

	private static final long serialVersionUID = -1L;

	private FileReadWriteQueue frwQueue = null;
	private Lock lock = new ReentrantReadWriteLock().writeLock();

	public FileQueue(String path) throws IOException, FileFormatException {
		this.frwQueue = new FileReadWriteQueue(path);
	}

	/**
	 * Create a permanent queue 영구적인 큐 만들기
	 *
	 * @param path              File storage path
	 * @param entityLimitLength The size of a single file for storing data
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public FileQueue(String path, int entityLimitLength) throws IOException, FileFormatException {
		this.frwQueue = new FileReadWriteQueue(path, entityLimitLength);
	}

	public FileQueue(File dir) throws IOException, FileFormatException {
		this.frwQueue = new FileReadWriteQueue(dir);
	}

	/**
	 * Create a persistent queue
	 *
	 * @param path              File storage directory
	 * @param entityLimitLength The size of a single file for storing data
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public FileQueue(File dir, int entityLimitLength) throws IOException, FileFormatException {
		this.frwQueue = new FileReadWriteQueue(dir, entityLimitLength);
	}

	@Override
	public Iterator<byte[]> iterator() {
		throw new UnsupportedOperationException("iterator Unsupported now");
	}

	@Override
	public int size() {
		return this.frwQueue.getQueueSize();
	}

	@Override
	public boolean offer(byte[] e) {
		try {
			this.lock.lock();
			this.frwQueue.add(e);
			return true;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (FileFormatException ex) {
		} finally {
			this.lock.unlock();
		}
		return false;
	}

	/**
	 * @param str
	 * @return
	 */
	public boolean offer(String str) {
		return offer(str.getBytes());
	}

	@Override
	public byte[] peek() {
		try {
			this.lock.lock();
			return this.frwQueue.readNext();
		} catch (IOException ex) {
			return null;
		} catch (FileFormatException ex) {
			return null;
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * same peek()
	 * 
	 * @return
	 */
	public String top() {
		byte[] bs = peek();
		return (bs == null) ? null : new String(bs);
	}

	@Override
	public byte[] poll() {
		try {
			this.lock.lock();
			return this.frwQueue.readNextAndRemove();
		} catch (IOException ex) {
			return null;
		} catch (FileFormatException ex) {
			return null;
		} finally {
			this.lock.unlock();
		}
	}

	public String pop() {
		byte[] bs = poll();
		return (bs == null) ? null : new String(bs);
	}

	@Override
	public void clear() {
		try {
			this.lock.lock();
			this.frwQueue.clear();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (FileFormatException e) {
		} finally {
			this.lock.unlock();
		}
	}

	/**
	 * Close file queue
	 *
	 * @throws IOException
	 * @throws FileFormatException
	 */
	public void close() throws IOException, FileFormatException {
		if (this.frwQueue != null) {
			this.frwQueue.close();
		}
	}

	/**
	 * @param args
	 * @throws FileFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, FileFormatException {
		FileQueue queue = new FileQueue("./conf/file/filequeue");

		queue.offer("abc".getBytes());
		System.out.println("label1: " + queue.size());

		String data2 = new String(queue.peek());
		System.out.println("label2: " + queue.size());
		System.out.println("label2: data2 -> " + data2);

		String data3 = new String(queue.remove());
		System.out.println("label3: " + queue.size());
		System.out.println("label3: data3 -> " + data3);

		queue.offer("ehktspqr456");
		System.out.println("top: " + queue.top());
		System.out.println("pop: " + queue.pop());
	}
}
