package com.dnk.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.dnk.demo.dto.MysecretDto;
import com.dnk.demo.util.PropertiesLoader;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	public String test() {
		String test = "나는야 테스트";
		return test;
	}

	// 중국어번역(Naver Rest Api)
	@Override
	public String getChinese(MysecretDto msd) {
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		String result = "";
		try {
			Properties properties = PropertiesLoader.fromResource("api.properties");
			String clientId = properties.getProperty("clientId");
			String clientSecret = properties.getProperty("clientSecret");
			HttpPost post = new HttpPost(apiURL);
			post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			post.addHeader("X-Naver-Client-Id", clientId);
			post.addHeader("X-Naver-Client-Secret", clientSecret);
			StringBuilder json = new StringBuilder();
			json.append("source=ko&target=zh-CN&text=" + msd.getKorean());
			post.setEntity(new StringEntity(json.toString(), "UTF-8"));
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			response.close();
			httpClient.close();
		} catch (Exception e) {
			result = e.getMessage();
		}
		return result;
	}

	// 영어번역(Naver Rest Api)
	@Override
	public String getEnglish(MysecretDto msd) {
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		String result = "";
		try {
			Properties properties = PropertiesLoader.fromResource("api.properties");
			String clientId = properties.getProperty("clientId");
			String clientSecret = properties.getProperty("clientSecret");
			HttpPost post = new HttpPost(apiURL);
			post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			post.addHeader("X-Naver-Client-Id", clientId);
			post.addHeader("X-Naver-Client-Secret", clientSecret);
			StringBuilder json = new StringBuilder();
			json.append("source=ko&target=en&text=" + msd.getKorean());
			post.setEntity(new StringEntity(json.toString(), "UTF-8"));
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			response.close();
			httpClient.close();
		} catch (Exception e) {
			result = e.getMessage();
		}
		return result;
	}

}
