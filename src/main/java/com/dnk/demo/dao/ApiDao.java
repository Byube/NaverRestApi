package com.dnk.demo.dao;



import org.apache.ibatis.annotations.Mapper;

import com.dnk.demo.dto.MysecretDto;

@Mapper
public interface ApiDao {
	public MysecretDto getSecret(MysecretDto msd);	
	public MysecretDto getkakao(MysecretDto msd);	
}
