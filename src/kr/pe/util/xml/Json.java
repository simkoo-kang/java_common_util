/**
 * Json.java
 */
package kr.pe.util.xml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.pe.util.xml.sample.ColorApiResponse;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
public class Json {

	// Jackson의 핵심 변환 엔진 인스턴스 싱글톤 생성
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * .class 명시 없이 JAXB와 Jackson 정석 방식으로 JSON 문자열을 객체로 다이렉트 변환합니다.
	 */
	public static <T> T parse(String jsonString, TypeReference<T> typeReference) {
		try {
			return OBJECT_MAPPER.readValue(jsonString, typeReference);
		} catch (Exception e) {
			throw new RuntimeException("JSON 파싱 중 치명적인 오류 발생: " + e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		// 보내주신 실제 JSON 데이터 원본 샘플
		String rawJson = "{\n" + "\t\"hex\":{\"value\":\"#FA4AD3\",\"clean\":\"FA4AD3\"},\n" + "\t\"rgb\":{\n"
				+ "\t\t\"fraction\":{\"r\":0.98,\"g\":0.29,\"b\":0.82},\n" + "\t\t\"r\":250,\"g\":74,\"b\":211,\n"
				+ "\t\t\"value\":\"rgb(250, 74, 211)\"\n" + "\t},\n" + "\t\"name\":{\n"
				+ "\t\t\"value\":\"Purple Pizzazz\",\n" + "\t\t\"closest_named_hex\":\"#FE4EDA\",\n"
				+ "\t\t\"exact_match_name\":false,\n" + "\t\t\"distance\":355\n" + "\t}\n" + "}";

		// 🚀 [호출 치트키] 뒤에 .class를 매번 붙이지 않고 객체 타입 참조 방식으로 깔끔하게 자동 수집
		ColorApiResponse response = Json.parse(rawJson, new TypeReference<ColorApiResponse>() {
		});

		// 최종 가공 데이터 출력 검증
		System.out.println("====== 객체 자동 매핑 결과 ======");
		System.out.println("정방향 이름 매핑 결과 : " + response.getName().getValue()); // Purple Pizzazz
		System.out.println("정방향 16진수 매핑 결과 : " + response.getHex().getValue()); // #FA4AD3
		System.out.println("정방향 RGB식 매핑 결과 : " + response.getRgb().getValue()); // rgb(250, 74, 211)
	}
}
