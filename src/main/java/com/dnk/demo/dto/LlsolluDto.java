package com.dnk.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlsolluDto {
	private String korean;
	private String apikey;
	private String target;
	private String source;
}
