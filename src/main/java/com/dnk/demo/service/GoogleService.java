package com.dnk.demo.service;

import java.rmi.RemoteException;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;

public interface GoogleService {
	public List<JSONObject> getKeyword(AdWordsServicesInterface adWordsServices, AdWordsSession session, Long adGroupId)
			throws RemoteException;
}
