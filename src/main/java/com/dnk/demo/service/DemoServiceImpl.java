package com.dnk.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnk.demo.dao.ApiDao;
import com.dnk.demo.dto.MysecretDto;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	ApiDao dao;

	@Override
	public String test() {
		String test = "나는야 테스트";
		return test;
	}

	//중국어번역(Naver Rest Api)
	@Override
	public String getChinese(MysecretDto msd) {
		MysecretDto md = dao.getSecret(msd);
		String clientId  = md.getClientId();
		String clientSecret = md.getClientSecret();
		String korean = msd.getKorean();
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
		//	result += "responseCode  : " + responseCode;
		//	result += "\n";
			if (responseCode != 200) {
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

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

	//영어번역(Naver Rest Api)
	@Override
	public String getEnglish(MysecretDto msd) {
		MysecretDto md = dao.getSecret(msd);
		String clientId  = md.getClientId();
		String clientSecret = md.getClientSecret();
		String korean = msd.getKorean();
		String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
		String text;
		String result = "";
		String line = "";
		try {
			text = URLEncoder.encode(korean, "UTF-8");
			String param = "source=ko&target=en&text=" + text;
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
//			result += "responseCode  : " + responseCode;
//			result += "\n";
			if (responseCode != 200) {
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

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = br.readLine()) != null) {
				result = line ;
			}
			br.close();

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("인코딩 실패", e);
		} catch (IOException e) {
			result = e.getMessage();
		}

		return result;
	}

}
