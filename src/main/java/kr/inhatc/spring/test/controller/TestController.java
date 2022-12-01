package kr.inhatc.spring.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.inhatc.spring.test.dto.TestDto;

//일반적으로 @Controller를 적는다.
//아래에 있는 정보를 그대로 client에게 return
@RestController
public class TestController {
//	옛날방식
//	@RequestMapping( value = "/", method = RequestMethod.GET)
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello world 123";
	}
	
	@GetMapping("/test")
	public TestDto test() {
		TestDto dto = new TestDto();
		dto.setName("김민주");
		dto.setAge(20);
		return dto;
	}

}
