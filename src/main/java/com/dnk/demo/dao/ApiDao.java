package com.dnk.demo.dao;


import org.apache.ibatis.annotations.Mapper;

import com.dnk.demo.dto.GoogleDto;

@Mapper
public interface ApiDao {
	public GoogleDto getGoogleSecret(int customerId);
}
