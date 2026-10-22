/**
 * Html.java
 */
package kr.pe.util.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.Enc;
import kr.pe.util.Str;
import kr.pe.util.io.FileUtil;
import kr.pe.util.iter.Iter;
import kr.pe.util.iter.StrIter;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 16.
 */
public class Html {

	private Logger logger;
	private Document docs;
	private File file;

	private boolean isLoaded;

	/**
	 * <pre></pre>
	 */
	public Html() {
		this.docs = null;
		this.file = null;
		this.isLoaded = false;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * <pre></pre>
	 * 
	 * @param html
	 */
	public Html(String html) {
		if (Str.isEmpty(html)) {
			this.logger.debug("html문자열이 null 입니다.");
		} else {
			this.docs = Jsoup.parse(html);
			this.isLoaded = true;
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * @param file
	 */
	public Html(File file) {
		this.loadFromFile(file, Enc.UTF_8);
	}

	public void connect(String url) {
		Connection conn = Jsoup.connect(url);
		try {
			this.docs = conn.get();
			this.isLoaded = true;
		} catch (IOException e) {
			this.logger.debug("Jsoup.connect({})", url, e);
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * @param filename
	 */
	public void loadFromFile(String filename) {
		this.loadFromFile(new File(filename), Enc.UTF_8);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param file
	 */
	public void loadFromFile(File file) {
		this.loadFromFile(file, Enc.UTF_8);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param file
	 * @param encording
	 */
	private void loadFromFile(File file, String encording) {
		this.file = file;
		try {
			this.docs = Jsoup.parse(this.file);
			this.isLoaded = true;
		} catch (IOException e) {
			this.logger.debug("Jsoup.parse({})", this.file.getAbsolutePath(), e);
		}
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * @return the isLoaded
	 */
	public boolean isLoaded() {
		return this.isLoaded;
	}

	/**
	 * <pre></pre>
	 * 
	 * @return org.jsoup.nodes.Document;
	 */
	public Document getDocument() {
		return this.docs;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @return org.jsoup.nodes.Element;
	 */
	public Element getElementById(String id) {
		if (this.docs == null) {
			return null;
		}
		return this.docs.getElementById(id);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param classname
	 * @return org.jsoup.select.Elements;
	 */
	public Elements getElementsByClass(String classname) {
		if (this.docs == null) {
			return null;
		}
		return this.docs.getElementsByClass(classname);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param tagname
	 * @return org.jsoup.select.Elements;
	 */
	public Elements getElementsByTag(String tagname) {
		if (this.docs == null) {
			return null;
		}
		return this.docs.getElementsByTag(tagname);
	}

	/**
	 * <pre>
	 * doc.selectFirst("a.link-item")
	 * </pre>
	 * 
	 * @param classname
	 * @return org.jsoup.nodes.Element;
	 */
	public Element getSelectFirst(String classname) {
		if (this.docs == null) {
			return null;
		}
		return this.docs.selectFirst(classname);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param cssQuery
	 * @return
	 */
	public Elements select(String cssQuery) {
		return this.docs.select(cssQuery);
	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param evaluator
	 * @return
	 */
	public Elements select(Evaluator evaluator) {
		return this.docs.select(evaluator);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param element
	 * @return org.jsoup.nodes.Attributes
	 */
	public Attributes getAttrs(Element element) {
		return element.attributes();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param element
	 * @param attrname
	 * @return org.jsoup.nodes.Attribute
	 */
	public Attribute getAttr(Element element, String attrname) {
		return element.attribute(attrname);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param element
	 * @param attrname
	 * @return
	 */
	public String getAttrValue(Element element, String attrname) {
		return element.attributes().get(attrname);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. 테스트용 HTML 문자열 정의
		// @formatter:off
		String htmlStr = "<html>" +
		        "<body>" +
		        "  <h1 id='main-title'>블로그 제목</h1>" +
		        "  <p class='content'>첫 번째 본문 내용입니다.</p>" +
		        "  <p class='content'>두 번째 본문 내용입니다.</p>" +
		        "  <a href='https://github.com' class='link-item'>깃허브 바로가기</a>" +
		        "</body>" +
		        "</html>";
		// @formatter:on

		// 2. HTML 문자열 파싱 (Document 객체 생성)
		Document doc = Jsoup.parse(htmlStr);

		// ---------------------------------------------------------------
		// [방법 1] ID로 단일 요소 가져오기 : getElementById(id)
		// ---------------------------------------------------------------
		Element titleElement = doc.getElementById("main-title");
		if (titleElement != null) {
			String titleText = titleElement.text(); // 태그 내부 글자 추출
			System.out.println("ID 추출 결과: " + titleText);
		}

		// ---------------------------------------------------------------
		// [방법 2] 클래스명으로 여러 요소 가져오기 : getElementsByClass(className)
		// ---------------------------------------------------------------
		// 여러 개를 가져오므로 반환 타입이 Elements(리스트 형태)입니다.
		Elements contentElements = doc.getElementsByClass("content");
		System.out.println("\nClass 추출 결과:");
		for (Element content : contentElements) {
			System.out.println("- " + content.text());
		}

		// ---------------------------------------------------------------
		// [방법 3] 태그명으로 여러 요소 가져오기 : getElementsByTag(tagName)
		// ---------------------------------------------------------------
		Elements pTags = doc.getElementsByTag("p");
		System.out.println("\nTag(p) 추출 결과 개수: " + pTags.size());

		// ---------------------------------------------------------------
		// [추천 방법 4] CSS 선택자로 가져오기 : select() / selectFirst()
		// ---------------------------------------------------------------
		// 실제 개발할 때는 위 메서드들보다 select() 하나로 처리하는 경우가 훨씬 많습니다.
		// #은 ID, .은 Class를 의미합니다.

		// selectFirst는 매칭되는 것 중 첫 번째 단일 요소만 반환합니다.
		Element firstLink = doc.selectFirst("a.link-item");
		if (firstLink != null) {
			String url = firstLink.attr("href"); // 속성(href) 값 추출
			String linkText = firstLink.text();
			System.out.println("\nCSS 선택자 추출 결과:");
			System.out.println("- 링크 텍스트: " + linkText);
			System.out.println("- 이동 주소(URL): " + url);
		}

		Attributes attrs = firstLink.attributes();
		for (Attribute attr : attrs) {
			String key = attr.getKey(); // 속성 이름 (예: id, class, data-id)
			String value = attr.getValue(); // 속성 값 (예: user-profile, card active)

			System.out.println("속성명(Key): [" + key + "] ➔ 값(Value): [" + value + "]");
		}
		Evaluator evaluator = Selector.evaluatorOf(".link-item");
		Elements alinks = doc.select(evaluator);
		System.out.println(alinks.size());
		System.out.println(alinks.getFirst());

		String filename = FileUtil.PATH_ECLIPSE + "/peace2026/conf/color_table.html";
		Html html = new Html(new File(filename));
		List<String> list = new ArrayList<String>();
		Element el = html.getSelectFirst("ul.np");
		if (el != null) {
			Iter it = new StrIter();
			it.find(el.text());
			while (it.hasMore()) {
				String next = it.next();
				list.add(next);
				System.out.println(next);
			}
			for (String color : list) {
				String selector = "table.colortable[summary*=" + color + "] tbody tr";
				Elements rows = html.getDocument().select(selector);
				System.out.println(color + " = " + rows.size());
				for (Element row : rows) {
					System.out.println(row.text());
				}
			}
		}
	}
}
