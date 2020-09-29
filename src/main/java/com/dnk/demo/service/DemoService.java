package com.dnk.demo.service;

import com.dnk.demo.dto.MysecretDto;

public interface DemoService {
	public String test();
	public String getChinese(MysecretDto msd);
	public String getChinese2(MysecretDto msd);
	public String getEnglish(MysecretDto msd);
}
