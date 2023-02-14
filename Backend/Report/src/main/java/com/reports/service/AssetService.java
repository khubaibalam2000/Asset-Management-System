package com.reports.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.reports.repository.*;
import com.reports.model.*;
import java.util.*;

@Service
public class AssetService {

	@Autowired
	private AssetRepository assetRepository;
	
	public List<Asset> getAllAssets(){
		return assetRepository.findAll();
	}
	
	public Asset getAssetById(Integer assetId) {
		return assetRepository.findById(assetId).get();
	}

	public List<Asset> getAssetByType(String type) {
		return assetRepository.findByType(type);
	}

	public List<Asset> getAssetByLocation(String location) {
		return assetRepository.findByLocation(location);
	}

	public List<Asset> getAssetByThreat(String threat) {
		return assetRepository.findByThreat(threat);
	}

	public List<Asset> getAssetByLevel(Integer lowLevel, Integer highLevel) {
		return assetRepository.findByLevel(lowLevel, highLevel);
	}	
}