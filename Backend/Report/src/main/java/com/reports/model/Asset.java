package com.reports.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
	@Id
	private Integer assetId;
	private String type;
	private String location;
	private String threat;
	private Integer level;
	private String currentdefense;
	private String proposeddefense;
	private LocalDateTime createdat;
}
