/**
 * Xml.java
 */
package kr.pe.util.xml;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Xml {

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. 새 문서 만들기
		Document doc = Xml.createNewDocument();

		// 2. 루트 노드 생성 <shop>
//		Element root = Xml.createNewDocument().createElement("shop");

		// 위 코드는 예시이며, 실제로는 아래와 같이 문서를 기준으로 생성합니다.
		Element shop = doc.createElement("shop");
		doc.appendChild(shop);

		// 3. 자식 노드 추가 및 속성(id="1") 지정하기
		// element.setAttribute("id", "1") 기능이 래핑된 메서드 사용
		Element product = Xml.createElement(doc, shop, "product", null, "id", "1");

		// 4. 하위 태그 추가
		Xml.createElement(doc, product, "name", "최신형 노트북");
		Xml.createElement(doc, product, "price", "1500000");

		// 5. 최종 XML 결과 출력
		String xmlResult = Xml.toString(doc);
		System.out.println(xmlResult);
	}

	// 2. 기본 엘리먼트 생성 및 부모 노드에 추가
	public static Element createElement(Document doc, Element parent, String name, String value) {
		Element element = doc.createElement(name);
		if (value != null) {
			element.setTextContent(value);
		}
		if (parent != null) {
			parent.appendChild(element);
		}
		return element;
	}

	// 1. 단일 속성을 가진 엘리먼트 생성 및 추가
	public static Element createElement(Document doc, Element parent, String name, String value, String attrName,
			String attrValue) {
		Element element = doc.createElement(name);
		if (value != null) {
			element.setTextContent(value);
		}
		if (attrName != null && attrValue != null) {
			element.setAttribute(attrName, attrValue);
		}
		if (parent != null) {
			parent.appendChild(element);
		}
		return element;
	}

	// 2. 여러 속성을 한 번에 추가하는 메서드
	public static Element createElementWithAttributes(Document doc, Element parent, String name, String value,
			Map<String, String> attributes) {
		Element element = doc.createElement(name);
		if (value != null) {
			element.setTextContent(value);
		}
		if (attributes != null) {
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				element.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		if (parent != null) {
			parent.appendChild(element);
		}
		return element;
	}

	/**
	 * <pre>
	 * 1. 새로운 비어있는 XML Document 생성
	 * </pre>
	 * 
	 * @return
	 */
	public static Document createNewDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.newDocument();
		} catch (Exception e) {
			throw new RuntimeException("XML 문서 생성 실패", e);
		}
	}

	/**
	 * <pre>
	 * 2. XML 파일 읽기 (Load)
	 * </pre>
	 * 
	 * @param filePath
	 * @return
	 */
	public static Document loadXml(String filePath) {
		File xmlFile = new File(filePath);
		return loadXml(xmlFile);
	}

	/**
	 * <pre>
	 * 2. XML 파일 읽기 (Load)
	 * </pre>
	 * 
	 * @param file
	 * @return
	 */
	public static Document loadXml(File xmlFile) {
		try {
			if (!xmlFile.exists()) {
				throw new IllegalArgumentException("파일이 존재하지 않습니다: " + xmlFile.getAbsolutePath());
			}
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize(); // 구조 정형화
			return doc;
		} catch (Exception e) {
			throw new RuntimeException("XML 파일 로드 실패: " + xmlFile.getAbsolutePath(), e);
		}
	}

	public static void toSave(Document doc, File file) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// XML 예쁘게 정렬하기 위한 설정 (들여쓰기 등)
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://apache.org}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new RuntimeException("XML 파일 저장 실패: " + file.getAbsolutePath(), e);
		}
	}

	/**
	 * <pre>
	 * 3. XML 파일 저장 (Save / Write) - 들여쓰기 기본 적용
	 * </pre>
	 * 
	 * @param doc
	 * @param filePath
	 */
	public static void toSave(Document doc, String filePath) {
		toSave(doc, new File(filePath));
	}

	// 5. Document 객체를 예쁜 XML 문자열(String)로 변환 (들여쓰기 포함)
	public static String toString(Document doc) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// XML 설정 (들여쓰기 활성화 및 인코딩 지정)
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://apache.org}indent-amount", "4"); // 들여쓰기 4칸

			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("XML 문자열 변환 중 오류가 발생했습니다.", e);
		}
	}

	/**
	 * <pre>
	 * 4. 편의 기능: 특정 태그명을 가진 하위 엘리먼트의 텍스트 값 가져오기
	 * </pre>
	 * 
	 * @param parent
	 * @param tagName
	 * @return
	 */
	public static String getElementValue(Element parent, String tagName) {
		NodeList list = parent.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			return list.item(0).getTextContent();
		}
		return null;
	}

	/**
	 * <pre>
	 * 5. 편의 기능: 하위 엘리먼트를 생성하고 텍스트 값 설정하기
	 * </pre>
	 * 
	 * @param doc
	 * @param parent
	 * @param tagName
	 * @param textContent
	 */
	public static void addChildElement(Document doc, Element parent, String tagName, String textContent) {
		Element child = doc.createElement(tagName);
		if (textContent != null) {
			child.setTextContent(textContent);
		}
		parent.appendChild(child);
	}
}
