package com.ref.api.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "prod" })
public class ProductionConfiguration implements Configuration {

	@Override
	public String getName() {
		return "prod";
	}
	
	
}