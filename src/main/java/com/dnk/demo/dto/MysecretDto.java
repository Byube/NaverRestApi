package com.dnk.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysecretDto {
	private int seq;
	private String clientId;
	private String clientSecret;
	private String restapikey;
	private String kakaoId;
	private String kakaoapikey;
	private String korean;
}
