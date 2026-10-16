/**
 * FileQueueImpl.java
 */
package kr.pe.util.io.que.v2;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.impl.Assert;

import kr.pe.util.io.que.v2.datastore.DataStore;
import kr.pe.util.io.que.v2.datastore.DataStoreImpl;
import kr.pe.util.io.que.v2.meta.MetaHolder;
import kr.pe.util.io.que.v2.meta.MetaHolderImpl;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class FileQueueImpl<E> implements FileQueue<E> {
	private DataStore<E> dataStore;
	private Config config;
	private MetaHolder metaHolder;
	private volatile boolean stopped = false;
	private final ReentrantLock writeLock = new ReentrantLock();
	private final ReentrantLock readLock = new ReentrantLock();

	public FileQueueImpl() {
		this(null);
	}

	public FileQueueImpl(Config config) {
		if (config == null) {
			config = new Config();
		}
		try {
			this.metaHolder = new MetaHolderImpl(config.getName(), config.getBaseDir());
			this.metaHolder.init();
			this.config = config;
			this.config.setReadingFileNo(this.metaHolder.getReadingFileNo());
			this.config.setReadingOffset(this.metaHolder.getReadingFileOffset());
			this.dataStore = new DataStoreImpl<E>(this.config);
			this.dataStore.init();

		} catch (IOException e) {
			throw new RuntimeException("FileQueue init fail.", e);
		}
	}

	public E get() throws InterruptedException, IOException {
		this.readLock.lockInterruptibly();
		try {
			E res = null;
			while (res == null) {
				res = this.dataStore.take();
				if (res != null) {
					this.metaHolder.update(this.dataStore.readingFileNo(), this.dataStore.readingFileOffset());
					return res;
				}
				// Since res == null not only caused by queue empty,
				// but also last msg not flush to disk completely. We
				// couldn't wait until not empty like
				// LinkedBlockingQueue.poll
				TimeUnit.NANOSECONDS.sleep(1000);
			}
			return null;
		} finally {
			this.readLock.unlock();
		}
	}

	public E get(long timeout, TimeUnit unit) throws InterruptedException, IOException {
		long startNanos = System.nanoTime();
		long timeoutNanos = unit.toNanos(timeout);
		this.readLock.lockInterruptibly();
		try {
			E res = null;
			while (res == null) {
				res = this.dataStore.take();
				if (res != null) {
					this.metaHolder.update(this.dataStore.readingFileNo(), this.dataStore.readingFileOffset());
					return res;
				} else {
					// Since res == null not only caused by queue empty,
					// but also last msg not flush to disk completely. We
					// couldn't wait until not empty like
					// LinkedBlockingQueue.poll
					if (System.nanoTime() - startNanos < timeoutNanos) {
						TimeUnit.NANOSECONDS.sleep(100);
					} else {
						return null;
					}
				}
			}
			return null;
		} finally {
			this.readLock.unlock();
		}
	}

	public void add(E m) throws IOException, FileQueueClosedException {
		this.writeLock.lock();
		try {
			if (this.stopped) {
				throw new FileQueueClosedException();
			}
			this.dataStore.put(m);
		} finally {
			this.writeLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geekhua.filequeue.FileQueue#close()
	 */
	public void close() {
		this.writeLock.lock();
		try {
			this.stopped = true;
			this.dataStore.close();
		} finally {
			this.writeLock.unlock();
		}
	}

	public long getReadingFileNo() {
		return this.metaHolder.getReadingFileNo();
	}

	public long getReadingFileOffset() {
		return this.metaHolder.getReadingFileOffset();
	}

	public long getWritingFileNo() {
		return this.dataStore.writingFileNo();
	}

	public long getWritingFileOffset() {
		return this.dataStore.writingFileOffset();
	}

	/**
	 * @param args
	 * @throws FileQueueClosedException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws Exception {
		Logger log = LoggerFactory.getLogger(FileQueue.class);
		String path = "./conf/file/filequeue";
		Config config = new Config();
		config.setBaseDir(new File(path).getAbsolutePath());
		config.setMsgAvgLen(10);
		config.setName("test");
		FileQueue<String> fq = new FileQueueImpl<String>(config);
		fq.add("ssss");
		String str = fq.get();
		log.debug(str);
		Assert.assrt("ssss".equals(str));
		fq.close();
	}

}
