/**
 * DataStoreImpl.java
 */
package kr.pe.util.io.que.v2.datastore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.io.que.v2.Config;
import kr.pe.util.io.que.v2.codec.Codec;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class DataStoreImpl<E> implements DataStore<E> {
	private static final String DATAFILE_DIRNAME = "data";
	private static final String DATAFILE_PREFIX = "fdata-";
	private static final String DATAFILE_SUFIX = ".fq";
	private static final String DATAFILE_BAKDIR = "bak";

	private static final Logger log = LoggerFactory.getLogger(DataStoreImpl.class);

	private static final byte[] DATAFILE_END_CONTENT = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA,
			(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAB };

	private byte[] DATAFILE_END;
	private File baseDir;
	private File bakDir;
	private int blockSize;
	private Codec codec;
	private long fileSize;
	private String name;
	private RandomAccessFile readingFile = null;
	private AtomicLong readingFileNo = new AtomicLong(-1L);
	private AtomicLong readingOffset = new AtomicLong(0L);
	private boolean bakReadFile = false;

	private AtomicLong writingFileNo = new AtomicLong(-1L);
	private RandomAccessFile writingFile = null;

	public DataStoreImpl(Config config) {
		this.name = config.getName();
		this.baseDir = new File(new File(config.getBaseDir(), this.name), DATAFILE_DIRNAME);
		this.blockSize = BlockGroup.estimateBlockSize(config.getMsgAvgLen());
		this.readingFileNo = new AtomicLong(config.getReadingFileNo());
		this.readingOffset = new AtomicLong(config.getReadingOffset());
		this.codec = config.getCodec();
		this.fileSize = config.getFileSize();
		this.bakReadFile = config.isBakReadFile();
		this.bakDir = new File(new File(config.getBaseDir(), this.name), DATAFILE_BAKDIR);
	}

	private boolean createBaseDirIfNeeded() {
		if (!this.baseDir.exists()) {
			return this.baseDir.mkdirs();
		}
		return true;
	}

	private boolean createBakDirIfNeeded() {
		if (!this.bakDir.exists()) {
			return this.bakDir.mkdirs();
		}
		return true;
	}

	private void createNewWriteFile() throws IOException {
		if (this.writingFile != null) {
			try {
				this.writingFile.write(this.DATAFILE_END);
				this.writingFile.close();
			} catch (IOException e) {
				throw e;
			}
			this.writingFile = null;
		}

		String newWriteFileName = getNewWriteFileName();
		if (StringUtils.isNotBlank(newWriteFileName)) {
			try {
				this.writingFile = new RandomAccessFile(new File(this.baseDir, newWriteFileName), "rw");
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(String.format("File(%s) not found", newWriteFileName), e);
			}
		} else {
			throw new IllegalStateException("Can not create new write file");
		}
	}

	private long getFileNumber(String fileName) {
		return StringUtils.isBlank(fileName) ? 0L
				: Long.valueOf(
						fileName.substring(DATAFILE_PREFIX.length(), fileName.length() - DATAFILE_SUFIX.length()));
	}

	public String getNewWriteFileName() {
		return getDataFileName(this.writingFileNo.incrementAndGet());
	}

	public String getDataFileName(long fileNo) {
		return DATAFILE_PREFIX + String.format("%018d", fileNo) + DATAFILE_SUFIX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geekhua.filequeue.datastore.DataStore#init()
	 */
	public void init() throws IOException {
		BlockGroup endFileBlockGroup = BlockGroup.allocate(DATAFILE_END_CONTENT.length, this.blockSize);
		endFileBlockGroup.setContent(DATAFILE_END_CONTENT);
		this.DATAFILE_END = endFileBlockGroup.array();

		createBaseDirIfNeeded();
		createBakDirIfNeeded();
		getLastDataFileNo();
		recoverLastDataFileIfNeeded();
		createNewWriteFile();
		checkReadingFile();
		openReadingFile();
	}

	private void checkReadingFile() {
		if (this.readingFileNo.longValue() < 0) {
			this.readingFileNo = new AtomicLong(0);
			this.readingOffset.set(0L);
		}

		File file = new File(this.baseDir, getDataFileName(this.readingFileNo.longValue()));
		while (!file.exists()) {
			file = new File(this.baseDir, getDataFileName(this.readingFileNo.incrementAndGet()));
			this.readingOffset.set(0L);
		}

	}

	private void recoverLastDataFileIfNeeded() throws IOException {
		if (this.writingFileNo.longValue() >= 0) {
			RandomAccessFile lastWriteFile = null;
			try {
				lastWriteFile = new RandomAccessFile(
						new File(this.baseDir, getDataFileName(this.writingFileNo.longValue())), "rw");
				if (lastWriteFile.length() % this.blockSize != 0) {
					long newLength = (lastWriteFile.length() / this.blockSize + 1) * this.blockSize;
					lastWriteFile.seek(newLength);
					lastWriteFile.setLength(newLength);
				} else {
					lastWriteFile.seek(lastWriteFile.length());
				}
				lastWriteFile.write(this.DATAFILE_END);
			} finally {
				if (lastWriteFile != null) {
					lastWriteFile.close();
				}
			}

		}
	}

	private void getLastDataFileNo() {
		long maxDataFileNo = -1L;
		String[] dataFilesArr = this.baseDir.list(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (StringUtils.endsWith(name, DATAFILE_SUFIX) && StringUtils.startsWith(name, DATAFILE_PREFIX)) {
					return true;
				}

				return false;
			}
		});

		for (String dataFile : dataFilesArr) {
			long fileNo = getFileNumber(dataFile);
			if (fileNo > maxDataFileNo) {
				maxDataFileNo = fileNo;
			}
		}

		this.writingFileNo.set(maxDataFileNo);
	}

	private void openReadingFile() {
		if (this.readingFileNo.longValue() < 0 && this.writingFileNo.longValue() >= 0) {
			this.readingFileNo = new AtomicLong(this.writingFileNo.longValue());
			this.readingOffset.set(0L);
		}
		if (this.readingFileNo.longValue() >= 0) {
			try {
				this.readingFile = new RandomAccessFile(
						new File(this.baseDir, getDataFileName(this.readingFileNo.longValue())), "r");

				if (this.readingOffset.longValue() > 0L) {
					this.readingFile
							.seek(this.readingOffset.longValue() % this.blockSize == 0 ? this.readingOffset.longValue()
									: ((this.readingOffset.longValue() / this.blockSize + 1) * this.blockSize));
				}
			} catch (IOException e) {
				if (this.readingFile != null) {
					try {
						this.readingFile.close();
					} catch (Exception e1) {
						// ignore
					}
					this.readingFile = null;
				}
				throw new IllegalStateException(
						String.format("File(%s) open fail", getDataFileName(this.readingFileNo.longValue())), e);
			}
		}
	}

	public void put(E element) throws IOException {
		byte[] content = this.codec.encode(element);
		if (content != null && content.length != 0) {
			if (this.writingFile.length() >= this.fileSize) {
				createNewWriteFile();
			}

			BlockGroup blockGroup = BlockGroup.allocate(content.length, this.blockSize);
			blockGroup.setContent(content);

			if (this.writingFile.length() % this.blockSize != 0) {
				this.writingFile.seek((this.writingFile.length() / this.blockSize + 1) * this.blockSize);
			}
			this.writingFile.write(blockGroup.array());
		}
	}

	@SuppressWarnings("unchecked")
	public E take() throws IOException {
		BlockGroup blockGroup = null;

		if (this.readingFileNo.longValue() >= 0) {
			if (this.readingFile == null) {
				openReadingFile();
			}

			blockGroup = BlockGroup.read(this.readingFile, this.blockSize);
			if ((blockGroup != null && Arrays.equals(blockGroup.array(), this.DATAFILE_END)) || blockGroup == null) {
				if (this.readingFileNo.longValue() < this.writingFileNo.longValue()) {
					if (this.readingFile != null) {
						this.readingFile.close();
						this.readingFile = null;
						if (this.bakReadFile) {
							try {
								FileUtils.moveFileToDirectory(
										new File(this.baseDir, getDataFileName(this.readingFileNo.longValue())),
										this.bakDir, true);
							} catch (IOException e) {
								log.warn("Move file({}) to dir({}) fail.",
										new File(this.baseDir, getDataFileName(this.readingFileNo.longValue())),
										this.bakDir);
							}
						} else {
							FileUtils.deleteQuietly(
									new File(this.baseDir, getDataFileName(this.readingFileNo.longValue())));
						}
					}
					this.readingFileNo.incrementAndGet();
					this.readingOffset.set(0L);
					openReadingFile();
					return take();
				}
			}
		}
		if (blockGroup == null) {
			return null;
		} else {
			this.readingOffset.set(this.readingFile.getFilePointer());
			return (E) this.codec.decode(blockGroup.getContent());
		}

	}

	public long readingFileOffset() {
		return this.readingOffset.longValue();
	}

	public long readingFileNo() {
		return this.readingFileNo.longValue();
	}

	public void close() {
		if (this.readingFile != null) {
			try {
				this.readingFile.close();
			} catch (IOException e) {
				log.warn("Close reading file({}) fail.", getDataFileName(this.readingFileNo.longValue()));
			}
		}

		if (this.writingFile != null) {
			try {
				this.writingFile.close();
			} catch (IOException e) {
				log.warn("Close reading file({}) fail.", getDataFileName(this.writingFileNo.longValue()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geekhua.filequeue.datastore.DataStore#writingFileNo()
	 */
	public long writingFileNo() {
		return this.writingFileNo.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.geekhua.filequeue.datastore.DataStore#writingFileOffset()
	 */
	public long writingFileOffset() {
		try {
			return this.writingFile.getFilePointer();
		} catch (IOException e) {
			return -1;
		}
	}
}
