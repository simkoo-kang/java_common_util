/**
 * 
 */
package kr.pe.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import kr.pe.util.Enc;
import kr.pe.util.Obj;
import kr.pe.util.Str;

/**
 * 
 */
public final class TextReader {

	private BufferedReader reader = null;

	/**
	 * open filename to read
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public TextReader(String filename) throws IOException {
		this(filename, Enc.UTF_8);
	}

	/**
	 * open file to read
	 * 
	 * @param file
	 * @throws IOException
	 */
	public TextReader(File file) throws IOException {
		this(file, Enc.UTF_8);
	}

	/**
	 * open filename to read with charset
	 * 
	 * @param filename
	 * @param charset
	 * @throws IOException
	 */
	public TextReader(String filename, String charset) throws IOException {
		this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), charset));
	}

	/**
	 * open file to read with charset
	 * 
	 * @param file
	 * @param charset
	 * @throws IOException
	 */
	public TextReader(File file, String charset) throws IOException {
		this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
	}

	/**
	 * close buffered reader
	 */
	public void close() {
		if (this.reader != null) {
			try {
				this.reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.reader = null;
		}
	}

	/**
	 * read all contents
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readAll() throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = this.reader.readLine()) != null) {
			sb.append(line).append(Str.CRLF);
		}
		return sb.toString();
	}

	public String readLine() throws IOException {
		return this.reader.readLine();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "./src/main/java/kr/pe/util/Obj.java";
		TextReader reader = null;
		try {
			reader = new TextReader(filename);
			String contents = reader.readAll();
			System.out.println(contents);
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader = (TextReader) Obj.invokeCloseIfExists(reader);
		}
	}
}
