/**
 * WildcardFilter.java
 */
package kr.pe.util.io;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import kr.pe.util.Arr;

/**
 * PC 2025. 4. 27.
 */
public class WildcardFilter implements FileFilter {

	protected String[] cards;
	protected WildcardFileFilter filter;

	/**
	 * @param cards
	 */
	public WildcardFilter(String... cards) {
		super();
		this.cards = cards;
		init();
	}

	/**
	 * @param list
	 */
	public WildcardFilter(List<String> list) {
		super();
		this.cards = Arr.listToStringArray(list);
		init();
	}

	/**
	 * 
	 */
	private void init() {
		this.filter = WildcardFileFilter.builder().setWildcards(this.cards).get();
	}

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return true;
		}
		return this.filter.accept(pathname);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileFilter filter = new WildcardFilter("lib", "*.xml");
//		FileFilter filter = new WildcardFilter("*.xml");
		File[] files = new File("./conf").listFiles(filter);
		if (files != null) {
			for (File f : files) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}

}
