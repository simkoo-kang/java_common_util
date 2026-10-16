/**
 * UserDto.java
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
@XmlRootElement(name = "user")
public class UserDto {

	private String id; // 속성으로 들어갈 데이터
	private String name;
	private int age;

	// element.setAttribute("id", "1") 역할과 동일하게 매핑됨
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
