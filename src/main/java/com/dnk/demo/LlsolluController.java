package com.dnk.demo;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnk.demo.dto.LlsolluDto;
import com.dnk.demo.service.llsolluService;

@Controller
public class LlsolluController {
	
	@Autowired
	llsolluService llsolluService;
	
	@RequestMapping("/llsollu")
	public String goLlusollu() {
		String address = "test/llsolluTest";
		return address;
	}
	
	@RequestMapping("/contensTest")
	public String goContensTest() {
		String address = "test/llsolluTest2";
		return address;
	}
	
	@RequestMapping("/llsolluChina")
	@ResponseBody
	public String Translation(@RequestParam(value = "korean", defaultValue = "-")String korean,
			Model model) {
		LlsolluDto ld = new LlsolluDto();
		String apikey = "734ac34d-5577-452f-8fed-56cd96f54c19";
		String target = "zh";
		korean =URLEncoder.encode(korean);
		ld.setKorean(korean);
		ld.setApikey(apikey);
		ld.setTarget(target);
		String china = llsolluService.getllsolluTranslation(ld);
		return china;
	}
	
	@RequestMapping("/llsolluEnglish")
	@ResponseBody
	public String Translation2(@RequestParam(value = "korean", defaultValue = "-")String korean,
			Model model) {
		LlsolluDto ld = new LlsolluDto();
		String apikey = "734ac34d-5577-452f-8fed-56cd96f54c19";
		String target = "en";
		korean =URLEncoder.encode(korean);
		ld.setKorean(korean);
		ld.setApikey(apikey);
		ld.setTarget(target);
		String china = llsolluService.getllsolluTranslation(ld);
		return china;
	}
	
	@RequestMapping("/contensChina")
	@ResponseBody
	public String Translation3(@RequestParam(value = "korean", defaultValue = "-")String korean,
			Model model) {
		LlsolluDto ld = new LlsolluDto();
		String apikey = "734ac34d-5577-452f-8fed-56cd96f54c19";
		String target = "zh";
		ld.setKorean(korean);
		ld.setApikey(apikey);
		ld.setTarget(target);
		String china = llsolluService.getllsolluTranslate(ld);
		return china;
	}
	
	@RequestMapping("/contensEnglish")
	@ResponseBody
	public String Translation4(@RequestParam(value = "korean", defaultValue = "-")String korean,
			Model model) {
		LlsolluDto ld = new LlsolluDto();
		String apikey = "734ac34d-5577-452f-8fed-56cd96f54c19";
		String target = "en";
		ld.setKorean(korean);
		ld.setApikey(apikey);
		ld.setTarget(target);
		String china = llsolluService.getllsolluTranslate(ld);
		return china;
	}

	
}
