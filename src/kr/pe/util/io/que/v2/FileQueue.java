/**
 * FileQueue.java
 */
package kr.pe.util.io.que.v2;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public interface FileQueue<E> {

	public E get() throws InterruptedException, IOException;

	public E get(long timeout, TimeUnit timeUnit) throws InterruptedException, IOException;

	public void add(E m) throws IOException, FileQueueClosedException;

	public void close();
}
