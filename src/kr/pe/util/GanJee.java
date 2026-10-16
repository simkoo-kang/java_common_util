/**
 * GanJee.java
 */
package kr.pe.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 19.
 */
public final class GanJee {
	static Logger log = LoggerFactory.getLogger(GanJee.class);

	public static String[][] GAN, JEE, GABJA;
	public static Map<String, String> ganStringMap, jeeStringMap, gabjaStringMap;
	public static Map<String, Integer> ganIndexMap, jeeIndexMap, gabjaIndexMap;

	static {
		/**
		 * 천간
		 */
		GAN = new String[][] { { "甲", "갑" }, { "乙", "을" }, { "丙", "병" }, { "丁", "정" }, { "戊", "무" }, { "己", "기" },
				{ "庚", "경" }, { "辛", "신" }, { "壬", "임" }, { "癸", "계" } };

		ganStringMap = new HashMap<String, String>();
		ganIndexMap = new HashMap<String, Integer>();
		for (int i = 0; i < 10; i++) {
			ganStringMap.put(GAN[i][0], GAN[i][1]);
			ganStringMap.put(GAN[i][1], GAN[i][0]);
			ganIndexMap.put(GAN[i][0], i + 1);
		}

		/**
		 * 지지
		 */
		JEE = new String[][] { { "子", "자" }, { "丑", "축" }, { "寅", "인" }, { "卯", "묘" }, { "辰", "진" }, { "巳", "사" },
				{ "午", "오" }, { "未", "미" }, { "申", "신" }, { "酉", "유" }, { "戌", "술" }, { "亥", "해" } };

		jeeStringMap = new HashMap<String, String>();
		jeeIndexMap = new HashMap<String, Integer>();
		for (int i = 0; i < 12; i++) {
			jeeStringMap.put(JEE[i][0], JEE[i][1]);
			jeeStringMap.put(JEE[i][1], JEE[i][0]);
			jeeIndexMap.put(JEE[i][0], i + 1);
		}

		/**
		 * 갑자
		 */
		gabjaStringMap = new HashMap<String, String>();
		gabjaIndexMap = new HashMap<String, Integer>();
		int gi = 0, ji = 0;
		for (int i = 0; i < 60; i++) {
			String gah = GAN[gi][0], gak = GAN[gi][1];
			String jeh = JEE[ji][0];
			String jek = JEE[ji][1];
			String key = gah + jeh;
			gabjaStringMap.put(key, gak + jek);
			gabjaStringMap.put(gak + jek, key);
			gabjaIndexMap.put(key, i);
			gi++;
			if (9 < gi)
				gi = 0;
			ji++;
			if (11 < ji)
				ji = 0;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
