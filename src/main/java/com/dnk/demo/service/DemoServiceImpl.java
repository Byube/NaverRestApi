package com.dnk.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

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
		String clientId = "";
		String clientSecret = "";
		String korean = msd.getKorean();
		System.out.println(">>>>>" + korean);
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		String text;
		String result = "";
		String line = "";
		try {
			text = URLEncoder.encode(korean, "UTF-8");
			String param = "source=ko&target=zh-CN&text=" + text;
			URL url = new URL(apiURL);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
			osw.write(param);
			osw.flush();

			int responseCode = con.getResponseCode();

			if (responseCode != 200) {
				result += "responseCode : " + responseCode;
				Map<String, List<String>> map = con.getRequestProperties();
				result += "Printing Response Header...\n";
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					if (entry.getKey().equals("apikey")) {
						result += "";
					} else {
						result += "Key : " + entry.getKey() + " ,Value : " + entry.getValue();
					}
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
			while ((line = br.readLine()) != null) {
				result = line;
			}
			br.close();

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (IOException e) {
			result = e.getMessage();
		}

		return result;
	}

	@Override
	public String getChinese2(MysecretDto msd) {
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
