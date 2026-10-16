/**
 * UserTestDto.java
 */
package kr.pe.util.xml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
// 테스트를 위해 같은 파일 안에 만든 임시 DTO 클래스
@XmlRootElement(name = "user")
class UserTestDto {
	private String id;
	private String name;
	private int age;

	public UserTestDto() {
	} // JAXB 필수 기본 생성자

	public UserTestDto(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "age")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}