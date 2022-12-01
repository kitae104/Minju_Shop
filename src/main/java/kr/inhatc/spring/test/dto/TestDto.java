package kr.inhatc.spring.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// 생성자들을 만들어 보아요
@NoArgsConstructor
@AllArgsConstructor
@Builder
//JPA를 사용할 때 무한루프에 빠질 수 있기 때문에 조심히 써야 한다. 
@ToString
public class TestDto {
	
	private String name;
	private int age;

}
