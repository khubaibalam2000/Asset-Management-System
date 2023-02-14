package com.reports.repository;

import com.reports.model.Asset;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AssetRepository extends MongoRepository<Asset, Integer> {
	List<Asset> findByType(String type);
	List<Asset> findByLocation(String location);
	List<Asset> findByThreat(String threat);
	
	@Query("{level : {$gte : ?0, $lte: ?1} }")
	List<Asset> findByLevel(Integer lowLevel, Integer highLevel);
}