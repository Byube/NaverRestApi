package com.dnk.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnk.demo.dto.MysecretDto;
import com.dnk.demo.service.DemoService;

@Controller
public class DemoController {
	
	@Autowired
	DemoService demoService;
	
	@RequestMapping("/returngood")
	public String returnGood() {
		String address = "test/reciveCode";
		return address;
	}

	@RequestMapping("/login")
	public String login() {
		String address = "test/login";
		return address;
	}
	
	@RequestMapping("/test")
	public String test() {
		String address = "test/index";
		return address;
	}
	
	@RequestMapping("/test2")
	public String test2(Model model) {
		String address = "test/index";
		String test = demoService.test();
		model.addAttribute("message", test);
		return address;
	}

	//중국어번역 (Naver Rest Api)
	@RequestMapping("/chinese")
	@ResponseBody
	public String Chinese(@RequestParam(value = "korean", defaultValue = "-")String korean,
									Model model) throws Exception{
		MysecretDto msd = new MysecretDto();
		msd.setSeq(1);
		msd.setKorean(korean);		
		String china = demoService.getChinese(msd);
		return china;
	}
	
	//영어번역 (Naver Rest Api)
	@RequestMapping("/english")
	@ResponseBody
	public String English(@RequestParam(value = "korean", defaultValue = "-")String korean,
									Model model) throws Exception{		
		MysecretDto msd = new MysecretDto();
		msd.setSeq(1);
		msd.setKorean(korean);		
		String english = demoService.getEnglish(msd);
		return english;
	}
	
}
