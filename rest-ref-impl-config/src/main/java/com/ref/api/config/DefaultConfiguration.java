package com.ref.api.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "default" })
public class DefaultConfiguration implements Configuration {

	@Override
	public String getName() {
		return "default";
	}
}