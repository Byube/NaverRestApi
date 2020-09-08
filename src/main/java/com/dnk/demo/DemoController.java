package com.dnk.demo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dnk.demo.dto.DemoDto;
import com.dnk.demo.service.DemoService;

@Controller
public class DemoController {
	
	@Autowired
	DemoService demoService;
	
	
	@RequestMapping("/test")
	public String test() {
		return "test/index";
	}
	
	@RequestMapping("/test2")
	public String test2(Model model) {
		String test = demoService.test();
		model.addAttribute("message", test);
		return "test/index";
	}

	@RequestMapping("/chinese")
	@ResponseBody
	public String Chinese(@RequestParam(value = "korean", defaultValue = "-")String korean,
									Model model) throws Exception{		
		DemoDto dd = new DemoDto();
		dd.setKorean(korean);		
		String china = demoService.getChinese(dd);
		return china;
	}
	@RequestMapping("/english")
	@ResponseBody
	public String English(@RequestParam(value = "korean", defaultValue = "-")String korean,
									Model model) throws Exception{		
		DemoDto dd = new DemoDto();
		dd.setKorean(korean);		
		String english = demoService.getEnglish(dd);
		return english;
	}
	
}
