/**
 * 
 */
package kr.pe.util.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import kr.pe.util.Obj;

/**
 * FilenameUtils ./conf/lib/lombok.jar:getBaseName - |lombok|
 * ./conf/lib/lombok.jar:getExtension - |jar| ./conf/lib/lombok.jar:getName -
 * |lombok.jar| ./conf/lib/lombok.jar:getPath - |./conf/lib/|
 * ./conf/lib/lombok.jar:getPathNoEndSeparator - |./conf/lib|
 */
public class FileUtil {

	public final static String DIR_DEFAULT_CONF = "./conf";

	public final static String PATH_WORKSPACE_DOCS = "D:/Workspace/docs";
	public final static String PATH_SCREENSHOTS = "C:/Users/PC/OneDrive/사진/Screenshots";
	public final static String PATH_문서 = "C:/Users/PC/OneDrive/문서";
	public final static String PATH_다운로드 = "C:/Users/PC/Downloads";
	public final static String PATH_사진 = "C:/Users/PC/OneDrive/사진";
	public final static String PATH_ECLIPSE = "D:/Workspace/eclipse";
	public final static String PATH_CAPTURE = "D:/M/F/Capture";

	/**
	 * <pre>
	 * Read the binary file and return it to byte[]
	 * binary file을 읽어서 byte[]로 반환
	 * </pre>
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAllBytes(String filename) throws IOException {
		return FileUtils.readFileToByteArray(new File(filename));
	}

	/**
	 * @param bytes
	 * @return
	 */
	public static String bytesToBase64String(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getBaseName - |lombok|
	 */
	public static String getBasename(String filename) {
		return FilenameUtils.getBaseName(filename);
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getBaseName - |lombok|
	 */
	public static String getBasename(File file) {
		return getBasename(file.getPath());
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getExtension - |jar|
	 */
	public static String getExtension(String filename) {
		return FilenameUtils.getExtension(filename);
	}

	/**
	 * @param file
	 * @return ./conf/lib/lombok.jar:getExtension - |jar|
	 */
	public static String getExtension(File file) {
		return FilenameUtils.getExtension(file.getPath());
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getName - |lombok.jar|
	 */
	public static String getName(String filename) {
		return FilenameUtils.getName(filename);
	}

	/**
	 * @param file
	 * @return ./conf/lib/lombok.jar:getName - |lombok.jar|
	 */
	public static String getName(File file) {
		return FilenameUtils.getName(file.getPath());
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getPath - |./conf/lib/|
	 */
	public static String getPath(String filename) {
		return FilenameUtils.getFullPath(filename).replace("\\", "/");
	}

	/**
	 * @param file
	 * @return ./conf/lib/lombok.jar:getPath - |./conf/lib/|
	 */
	public static String getPath(File file) {
		return FilenameUtils.getFullPath(file.getPath()).replace("\\", "/");
	}

	/**
	 * @param filename
	 * @return ./conf/lib/lombok.jar:getPathNoEndSeparator - |./conf/lib|
	 */
	public static String getPathNoEndSeparator(String filename) {
//		return FilenameUtils.getName(filename).replace("\\", "/");
		return getPathNoEndSeparator(new File(filename));
	}

	/**
	 * @param file
	 * @return ./conf/lib/lombok.jar:getPathNoEndSeparator - |./conf/lib|
	 */
	public static String getPathNoEndSeparator(File file) {
//		return FilenameUtils.getName(file.getPath()).replace("\\", "/");
		return file.getParent().replace("\\", "/");
	}

	/**
	 * @param filename
	 */
	public static void ifmkdir(String filename) {
//		String filename = savename.replace("\\]", "/");
//		if (filename.startsWith(DEFAULT_DIR)) {
//			filename = filename.substring(filename.indexOf(DEFAULT_DIR));
//		}
		String path = FilenameUtils.getPath(filename);
		File pathfile = new File(path);
		if (!pathfile.exists()) {
			pathfile.mkdirs();
		}
	}

	/**
	 * 디렉토리 존재여부 확인, File객체이면 FileUtils.isDirectory 사용하지만 문자열인 경우 사용
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean existDir(String filename) {
		return FileUtils.isDirectory(new File(filename));
	}

	/**
	 * 디렉토리 존재여부 확인, File객체이면 FileUtils.isDirectory 사용하지만 문자열인 경우 사용
	 * 
	 * @param file
	 * @return
	 */
	public static boolean existDir(File file) {
		return FileUtils.isDirectory(file);
	}

	/**
	 * 파일 존재여부 확인, File객체이면 FileUtils.isDirectory 사용하지만 문자열인 경우 사용
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean existFile(String filename) {
		return existFile(new File(filename));
	}

	/**
	 * 파일 존재여부 확인, File객체이면 FileUtils.isDirectory 사용하지만 문자열인 경우 사용
	 * 
	 * @param file
	 * @return
	 */
	public static boolean existFile(File file) {
		return file.exists() && !file.isDirectory();
	}

	/**
	 * @param filename
	 * @return if duplicate filename, filename_number.ext else filename
	 */
	public static String duplicate(String filename) {
		return duplicate(new File(filename));
	}

	/**
	 * @param file ./conf/lib/lombok.jar
	 * @return if duplicate filename, filename_number.ext else filename
	 */
	public static String duplicate(File file) {
		String abspath = file.getAbsolutePath();
		if (!file.exists()) {
			return abspath;
		}
		String path = FilenameUtils.getFullPath(abspath); // |D:\Workspace\eclipse\peace\./conf/lib/|
		String base = FilenameUtils.getBaseName(abspath); // |lombok|
		String ext = FilenameUtils.getExtension(abspath); // |jar|
		int index = 1;
		String name = base + "_" + index;
		String fullname = path + name + "." + ext;
		file = new File(fullname);
		while (file.exists()) {
			index++;
			name = base + "_" + index;
			fullname = path + name + "." + ext;
			file = new File(fullname);
		}
		return fullname;
	}

	/**
	 * read all from filename
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String read(String filename) throws IOException {
		TextReader reader = null;
		try {
			reader = new TextReader(filename);
			return reader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				reader = (TextReader) Obj.invokeCloseIfExists(reader);
			}
		}
	}

	/**
	 * read all from file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String read(File file) throws IOException {
		TextReader reader = null;
		try {
			reader = new TextReader(file);
			return reader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				reader = (TextReader) Obj.invokeCloseIfExists(reader);
			}
		}
	}

	/**
	 * read all from filename
	 * 
	 * @param filename
	 * @return
	 */
	public static List<String> readAll(String filename) {
		List<String> list = new ArrayList<>();
		TextReader reader = null;
		try {
			String line;
			reader = new TextReader(filename);
			while ((line = reader.readLine()) != null) {
				list.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return list;
	}

	/**
	 * read all from file
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> readAll(File file) {
		List<String> list = new ArrayList<>();
		TextReader reader = null;
		try {
			String line;
			reader = new TextReader(file);
			while ((line = reader.readLine()) != null) {
				list.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return list;
	}

	/**
	 * Save everything to a file. It's add mode by default(append). default append =
	 * true
	 * 
	 * @param savename
	 * @param contents
	 * @throws IOException
	 */
	public static void write(String savename, String contents) throws IOException {
		write(savename, contents, true);
	}

	/**
	 * @param savename
	 * @param contents
	 * @param append   default true
	 * @throws IOException
	 */
	public static void write(String savename, String contents, boolean append) throws IOException {
		String filename = null;
		ifmkdir(savename);
		filename = savename;
		TextWriter writer = null;
		try {
			writer = new TextWriter(filename, append);
			writer.write(contents);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer = (TextWriter) Obj.invokeCloseIfExists(writer);
			}
		}
	}

	/**
	 * write all to file default append = true
	 * 
	 * @param file
	 * @param contents
	 * @throws IOException
	 */
	public static void write(File file, String contents) throws IOException {
		write(file, contents, true);
	}

	/**
	 * @param file
	 * @param contents
	 * @param append
	 * @throws IOException
	 */
	public static void write(File file, String contents, boolean append) throws IOException {
		ifmkdir(file.getAbsolutePath());
		TextWriter writer = null;
		try {
			writer = new TextWriter(file, append);
			writer.write(contents);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer = (TextWriter) Obj.invokeCloseIfExists(writer);
			}
		}
	}

	/**
	 * Save everything to a file. It's add mode by default(append). default append =
	 * true
	 * 
	 * @param savename
	 * @param contents
	 * @throws IOException
	 */
	public static void writeln(String savename, String contents) throws IOException {
		writeln(savename, contents, true);
	}

	/**
	 * @param savename
	 * @param contents
	 * @param append   default true
	 * @throws IOException
	 */
	public static void writeln(String savename, String contents, boolean append) throws IOException {
		String filename = null;
		ifmkdir(savename);
		filename = savename;
		TextWriter writer = null;
		try {
			writer = new TextWriter(filename, append);
			writer.writeln(contents);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer = (TextWriter) Obj.invokeCloseIfExists(writer);
			}
		}
	}

	/**
	 * write all to file default append = true
	 * 
	 * @param file
	 * @param contents
	 * @throws IOException
	 */
	public static void writeln(File file, String contents) throws IOException {
		writeln(file, contents, true);
	}

	/**
	 * @param file
	 * @param contents
	 * @param append
	 * @throws IOException
	 */
	public static void writeln(File file, String contents, boolean append) throws IOException {
		ifmkdir(file.getAbsolutePath());
		TextWriter writer = null;
		try {
			writer = new TextWriter(file, append);
			writer.writeln(contents);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (writer != null) {
				writer = (TextWriter) Obj.invokeCloseIfExists(writer);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fn = FileUtil.class.getClassLoader().getResource("image/exit.gif").getFile();
		System.out.println(fn);
		File fnfile = new File(fn);
		String filename = "./src/main/resources/image/upload/" + fnfile.getName();
		System.out.println(filename);
		File file = new File(filename);
		try {
			FileUtils.copyFile(fnfile, file);
			if (file.exists()) {
				System.out.println("exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String filename = "./conf/lib/regex.jar";
//		prt(filename, "getBaseName", FilenameUtils.getBaseName(filename));
//		prt(filename, "getExtension", FilenameUtils.getExtension(filename));
//		prt(filename, "getName", FilenameUtils.getName(filename));
//		prt(filename, "getPath", FilenameUtils.getPath(filename));
//		prt(filename, "getPathNoEndSeparator", FilenameUtils.getPathNoEndSeparator(filename));
//		System.out.println();
//		String fullname = duplicate(filename).replace("\\", "/");
//		System.out.println(fullname);
//		System.out.println();
//		System.out.println("./ = |" + Paths.get("./") + "|");
//		System.out.println("\"\" = |" + Paths.get("") + "|");
//		System.out.println("./conf = |" + Paths.get("./conf") + "|");
//		int pos = fullname.indexOf("./conf");
//		System.out.println(pos);
//		System.out.println(fullname.substring(pos));
//		File file = new File(filename);
//		System.out.println(file.getPath());
//		System.out.println(file.getParent());
//		System.out.println(file.getAbsolutePath());
//		System.out.println(FileUtil.getPath(file.getAbsolutePath()));
//		System.out.println(FileUtil.getPathNoEndSeparator(file.getAbsolutePath()));
	}

	/**
	 * @param filename
	 * @param string
	 * @param baseName
	 */
//	private static void prt(String filename, String string, String baseName) {
//		System.out.println(filename + ":" + string + " - |" + baseName + "|");
//	}
}
