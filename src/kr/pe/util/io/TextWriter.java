/**
 * 
 */
package kr.pe.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import kr.pe.util.Enc;

/**
 * 
 */
public class TextWriter {

	private BufferedWriter writer = null;

	/**
	 * open filename to write
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public TextWriter(String filename) throws IOException {
		this(filename, true, Enc.UTF_8);
	}

	/**
	 * @param filename
	 * @param append
	 * @throws IOException
	 */
	public TextWriter(String filename, boolean append) throws IOException {
		this(filename, append, Enc.UTF_8);
	}

	/**
	 * open file to write
	 * 
	 * @param file
	 * @throws IOException
	 */
	public TextWriter(File file) throws IOException {
		this(file, true, Enc.UTF_8);
	}

	/**
	 * @param file
	 * @param append
	 * @throws IOException
	 */
	public TextWriter(File file, boolean append) throws IOException {
		this(file, append, Enc.UTF_8);
	}

	/**
	 * open filename to write with charset
	 * 
	 * @param filename
	 * @param charset
	 * @throws IOException
	 */
	public TextWriter(String filename, String charset) throws IOException {
		this(filename, true, charset);
	}

	/**
	 * @param filename
	 * @param append
	 * @param charset
	 * @throws IOException
	 */
	public TextWriter(String filename, boolean append, String charset) throws IOException {
		this.writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filename, append), Enc.UTF_8));
	}

	/**
	 * open file to write with charset
	 * 
	 * @param file
	 * @param charset
	 * @throws IOException
	 */
	public TextWriter(File file, String charset) throws IOException {
		this(file, true, charset);
	}

	/**
	 * @param file
	 * @param append
	 * @param charset
	 * @throws IOException
	 */
	public TextWriter(File file, boolean append, String charset) throws IOException {
		this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), Enc.UTF_8));
	}

	/**
	 * close buffered reader
	 */
	public void close() {
		if (this.writer != null) {
			try {
				this.writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.writer = null;
		}
	}

	/**
	 * write contents
	 * 
	 * @param contents
	 * @throws IOException
	 */
	public void write(String contents) throws IOException {
		this.writer.write(contents);
	}

	/**
	 * @param contents
	 * @throws IOException
	 */
	public void writeln(String contents) throws IOException {
		write(contents + "\r\n");
	}

	public void writeLine(String line) throws IOException {
		write(line + "\r\n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
