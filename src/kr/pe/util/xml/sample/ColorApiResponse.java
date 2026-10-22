/**
 * ColorApiResponse.java
 */
package kr.pe.util.xml.sample;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
//1. [DTO 설계] API 응답 구조와 1:1 매핑되는 클래스
@JsonIgnoreProperties(ignoreUnknown = true) // 정의되지 않은 복잡한 메타데이터는 자동 무시
public class ColorApiResponse {

	private ColorData hex;
	private ColorData rgb;
	private ColorData name;

	public ColorData getHex() {
		return this.hex;
	}

	public void setHex(ColorData hex) {
		this.hex = hex;
	}

	public ColorData getRgb() {
		return this.rgb;
	}

	public void setRgb(ColorData rgb) {
		this.rgb = rgb;
	}

	public ColorData getName() {
		return this.name;
	}

	public void setName(ColorData name) {
		this.name = name;
	}

	// 중첩된 {"value": "..."} 구조를 소화하기 위한 내부 클래스
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ColorData {
		private String value;

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
