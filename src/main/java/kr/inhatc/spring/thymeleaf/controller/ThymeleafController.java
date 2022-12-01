package kr.inhatc.spring.thymeleaf.controller;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.item.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;

@Controller
//공통되는 앞에 주소를 아예 고정으로 박아놓는것
@Slf4j
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

	@GetMapping(value = "/ex1")
	public String ex1(Model model) {
		Point p = new Point(10, 20);
		model.addAttribute("data", p);
		return "/thymeleaf/ex1";
	}

//	값 전달 연습
	@GetMapping(value = "/ex2")
	public String ex2(Model model) {

		ItemDto itemDto = new ItemDto();
		itemDto.setItemDetail("상품 상세 설명");
		itemDto.setItemNm("테스트 상품1");
		itemDto.setPrice(10000);
		itemDto.setRegTime(LocalDateTime.now());

		model.addAttribute("itemDto", itemDto);

		return "/thymeleaf/ex2";
	}

//	3, 4를 한 번에 담을 수 있다. 
	@GetMapping(value = { "/ex3", "/ex4" })
	public void ex3(Model model) {

		List<ItemDto> list = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			ItemDto itemDto = new ItemDto();
			itemDto.setItemDetail("상품 상세 설명" + i);
			itemDto.setItemNm("테스트 상품1" + i);
			itemDto.setPrice(10000 * i);
			itemDto.setRegTime(LocalDateTime.now());
			list.add(itemDto);
		}

//		리스트로 전달
		model.addAttribute("list", list);

	}

	@GetMapping(value = "/ex5")
	public String ex5(@RequestParam("param1") String p1, String param2, Model model) {
//	    데이터를 잘 넘겼는지 확인하기 위함
	    log.info("==============================>" + p1 + "," + param2 );
	    
	    model.addAttribute("param1", p1);
	    model.addAttribute("param2", param2);
		return "/thymeleaf/ex5";
	}
	
	   @GetMapping(value = {"/ex6", "/ex7"})
	    public void ex6() {
	       
	   }

}
