package com.dnk.demo.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.ads.adwords.axis.utils.v201809.SelectorBuilder;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterionPage;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.BidSource;
import com.google.api.ads.adwords.axis.v201809.cm.BiddableAdGroupCriterion;
import com.google.api.ads.adwords.axis.v201809.cm.Bids;
import com.google.api.ads.adwords.axis.v201809.cm.CpcBid;
import com.google.api.ads.adwords.axis.v201809.cm.Criterion;
import com.google.api.ads.adwords.axis.v201809.cm.Keyword;
import com.google.api.ads.adwords.axis.v201809.cm.Selector;
import com.google.api.ads.adwords.axis.v201809.mcm.ManagedCustomer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.selectorfields.v201809.cm.AdGroupCriterionField;


@Service
public class GoogleServiceImpl implements GoogleService{
	private static final int NUMBER_OF_ADS = 5;
	private static final int PAGE_SIZE = 100;

	private static class ManagedCustomerTreeNode {
		protected ManagedCustomerTreeNode parentNode;
		protected ManagedCustomer account;
		protected List<ManagedCustomerTreeNode> childAccounts = new ArrayList<ManagedCustomerTreeNode>();

		public ManagedCustomerTreeNode() {
		}

		@Override
		public String toString() {
			return String.format("%s, %s", account.getCustomerId(), account.getName());
		}

		public StringBuffer toTreeString(int depth, StringBuffer sb) {
			sb.append(StringUtils.repeat("-", depth * 2)).append(this).append(SystemUtils.LINE_SEPARATOR);
			childAccounts.forEach(childAccount -> childAccount.toTreeString(depth + 1, sb));
			return sb;
		}

	}
	
	@Override
	public List<JSONObject> getKeyword(AdWordsServicesInterface adWordsServices, AdWordsSession session, Long adGroupId)
			throws RemoteException {
		System.out.println("------------------------");
		System.out.println(adWordsServices.toString());
		System.out.println(session.getDeveloperToken());
		System.out.println("------------------------");
		
		// 모든 광고 그룹의 값을 가져오기
		AdGroupCriterionServiceInterface adGroupCriterionService = adWordsServices.get(session,
				AdGroupCriterionServiceInterface.class);

		int offset = 0;
		boolean morePages = true;

		// JSON으로 응답받을 keyword
		List<JSONObject> result = new ArrayList<JSONObject>();

		// Create selector.
		SelectorBuilder builder = new SelectorBuilder();
		Selector selector = builder
				.fields(AdGroupCriterionField.Id, AdGroupCriterionField.CriteriaType,
						AdGroupCriterionField.KeywordMatchType, AdGroupCriterionField.KeywordText,
						AdGroupCriterionField.CpcBid, AdGroupCriterionField.FinalUrls)
				.orderAscBy(AdGroupCriterionField.KeywordText).offset(offset).limit(PAGE_SIZE)
				.in(AdGroupCriterionField.AdGroupId, adGroupId.toString())
				.in(AdGroupCriterionField.CriteriaType, "KEYWORD").build();

		while (morePages) {

			// Get all ad group criteria.
			AdGroupCriterionPage page = adGroupCriterionService.get(selector);

			if (page.getEntries() != null && page.getEntries().length > 0) {

				Arrays.stream(page.getEntries()).forEach(adGroupCriterionResult -> {
					JSONObject obj = new JSONObject();
					BiddableAdGroupCriterion bidCriterion = null;
					if (adGroupCriterionResult instanceof BiddableAdGroupCriterion) {
						bidCriterion = (BiddableAdGroupCriterion) adGroupCriterionResult;
					}
					final Criterion criterion = adGroupCriterionResult.getCriterion();

					Keyword tmpKeyword = (Keyword) criterion;
					obj.put("KEYWORD", tmpKeyword.getText());
					obj.put("KEYWORD ID", tmpKeyword.getId());

					if (bidCriterion != null) {
						for (Bids bids : bidCriterion.getBiddingStrategyConfiguration().getBids()) {
							if (bids instanceof CpcBid) {
								CpcBid cpcBid = (CpcBid) bids;
								if (BidSource.CRITERION.equals(cpcBid.getCpcBidSource())) {
									if (cpcBid.getBid() != null) {
										obj.put("입찰가격", cpcBid.getBid().getMicroAmount());
									}
								}
							}
						}
						obj.put("url", bidCriterion.getFinalUrls());
					}
					result.add(obj);
				});
			} else {
				System.out.println("error : No ad group criteria were found.");
			}
			offset += PAGE_SIZE;
			selector = builder.increaseOffsetBy(PAGE_SIZE).build();
			morePages = offset < page.getTotalNumEntries();
		}
		return result;
	}
}
