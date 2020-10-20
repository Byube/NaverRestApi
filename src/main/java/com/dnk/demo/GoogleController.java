package com.dnk.demo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beust.jcommander.Parameter;
import com.dnk.demo.dao.ApiDao;
import com.dnk.demo.dto.GoogleDto;
import com.dnk.demo.service.GoogleService;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201809.cm.ApiException;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.utils.examples.ArgumentNames;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.ads.common.lib.utils.examples.CodeSampleParams;
import com.google.api.client.auth.oauth2.Credential;

@Controller
public class GoogleController {

	private static class UpdateKeywordParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.AD_GROUP_ID, required = true)
		private Long adGroupId;
		@Parameter(names = ArgumentNames.KEYWORD_ID, required = true)
		private Long keywordId;
		@Parameter(names = ArgumentNames.CPC_BID_MICRO_AMOUNT)
		private Long cpcBidMicroAmount;
		@Parameter(names = ArgumentNames.AD_ID, required = true)
		private Long adId;
	}

	private static class UpdateCampaignParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.CAMPAIGN_ID, required = true)
		private Long campaignId;
	}
	
	@Autowired
	GoogleService googleService;
	
	@Autowired
	ApiDao dao;
	
	// 1.1 키워드 가져오기 기능
		@RequestMapping("/google/getKeyword/{customerId}")
		public String getKeyword(@PathVariable final int customerId, Model model, HttpServletRequest request) {
			String address = "test/googleTest";
			AdWordsSession session;
			GoogleDto gd = new GoogleDto();
			Configuration config = null;
			try {
				gd = dao.getGoogleSecret(customerId);
				if(gd==null) {
					address = "test/nodata";
					return address;
				}
				
//				config.addProperty("api.adwords.refreshToken", gd.getGoogle_refreshToken());
//				config.addProperty("api.adwords.clientId", gd.getGoogle_clientId());
//				config.addProperty("api.adwords.clientSecret", gd.getGoogle_clientSecret());
//				config.addProperty("api.adwords.clientCustomerId", gd.getGoogle_clientCustomerId());
//				config.addProperty("api.adwords.developerToken", gd.getGoogle_developerToken());
				
//				config.setProperty("api.adwords.refreshToken", gd.getGoogle_refreshToken());
//				config.setProperty("api.adwords.clientId", gd.getGoogle_clientId());
//				config.setProperty("api.adwords.clientSecret", gd.getGoogle_clientSecret());
//				config.setProperty("api.adwords.clientCustomerId", gd.getGoogle_clientCustomerId());
//				config.setProperty("api.adwords.developerToken", gd.getGoogle_developerToken());
				
//				System.out.println(">>>>>>>>>>>>>>>"+config.getString("api.adwords.refreshToken"));
				
//				Credential oAuth2Credential = new OfflineCredentials.Builder().forApi(Api.ADWORDS).from(config).build().generateCredential();
				
				
				Credential oAuth2Credential = new OfflineCredentials.Builder().forApi(Api.ADWORDS).fromFile().build()
						.generateCredential();

				session = new AdWordsSession.Builder().fromFile().withOAuth2Credential(oAuth2Credential).build();
				
			} catch (ConfigurationLoadException cle) {
				model.addAttribute("message", cle.getMessage());
				return address;
			} catch (ValidationException ve) {
				model.addAttribute("message", ve.getMessage());
				return address;
			} catch (OAuthException oe) {
				model.addAttribute("message", oe.getMessage());
				return address;
			} 
			AdWordsServicesInterface adWordsServices = AdWordsServices.getInstance();

			Long adGroupId = Long.parseLong("104374667885"); // 그룹 ID

			try {
				List<JSONObject> result = new ArrayList<JSONObject>();
				result = googleService.getKeyword(adWordsServices, session, adGroupId);
				model.addAttribute("message", result);

			} catch (ApiException apiException) {
				model.addAttribute("message", "Request failed due to ApiException. Underlying ApiErrors:");
				if (apiException.getErrors() != null) {
					model.addAttribute("message", apiException.getMessage());
				}
			} catch (RemoteException re) {
				model.addAttribute("message", re.getMessage());
			}
			return address;
		}
	
	
	
}
