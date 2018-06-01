package com.ref.api.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "dev" })
public class DevelopmentConfiguration implements Configuration {

	@Override
	public String getName() {
		return "dev";
	}
}