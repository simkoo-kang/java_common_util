/**
 * Jaxb.java
 */
package kr.pe.util.xml;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Jaxb {

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("========== 1. 객체를 XML로 변환 (Marshalling) ==========");

		// 1) 테스트할 데이터 객체 생성
		UserTestDto user = new UserTestDto("user_99", "임꺽정", 35);

		// 2) 앞서 만든 유틸리티를 이용해 XML 변환
		String xmlOutput = Jaxb.convertToXml(user);

		// 3) 콘솔 출력으로 눈으로 확인하기
		System.out.println(xmlOutput);

		System.out.println("\n========== 2. XML을 객체로 변환 (Unmarshalling) ==========");

		// 1) 가상의 XML 문자열 준비
		String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<user id=\"user_77\">\n"
				+ "    <name>심청이</name>\n" + "    <age>18</age>\n" + "</user>";

		// 2) 유틸리티를 이용해 객체로 복원
		UserTestDto restoredUser = Jaxb.convertToObject(inputXml, UserTestDto.class);

		// 3) 값이 잘 들어왔는지 출력해보기
		System.out.println("복원된 ID  : " + restoredUser.getId());
		System.out.println("복원된 이름: " + restoredUser.getName());
		System.out.println("복원된 나이: " + restoredUser.getAge());
	}

	// Java 객체 -> XML 문자열 변환 (Marshalling)
	public static String convertToXml(Object object) {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();

			// XML 포맷팅(들여쓰기) 설정
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter writer = new StringWriter();
			marshaller.marshal(object, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("JAXB 변환(객체->XML) 중 오류가 발생했습니다.", e);
		}
	}

	// XML 문자열 -> Java 객체 변환 (Unmarshalling)
	@SuppressWarnings("unchecked")
	public static <T> T convertToObject(String xml, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader reader = new StringReader(xml);
			return (T) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			throw new RuntimeException("JAXB 변환(XML->객체) 중 오류가 발생했습니다.", e);
		}
	}

	/**
	 * <pre>
	 * [추가] Java 객체 -> 실제 XML 파일로 저장
	 * </pre>
	 * 
	 * @param object
	 * @param filePath
	 */
	public static void convertToFile(Object object, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();

			// 들여쓰기 설정
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			File file = new File(filePath);
			marshaller.marshal(object, file);
			System.out.println("💾 XML 파일 저장 완료: " + file.getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException("XML 파일 저장 중 오류가 발생했습니다.", e);
		}
	}

	/**
	 * <pre>
	 * [추가] XML 파일 -> Java 객체로 로드
	 * </pre>
	 * 
	 * @param <T>
	 * @param filePath
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertFromFile(String filePath, Class<T> clazz) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new IllegalArgumentException("지정한 경로에 XML 파일이 존재하지 않습니다: " + filePath);
			}

			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(file);
		} catch (Exception e) {
			throw new RuntimeException("XML 파일 로드 중 오류가 발생했습니다.", e);
		}
	}
}
