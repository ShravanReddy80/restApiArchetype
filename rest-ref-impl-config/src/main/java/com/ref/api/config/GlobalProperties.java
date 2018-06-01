package com.ref.api.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(ignoreUnknownFields = true) // no prefix, find root level values.
public class GlobalProperties {

	@Max(10)
	@Min(0)
	private int threadPool;

	@NotNull
	private String email;

	public int getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(int threadPool) {
		this.threadPool = threadPool;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + threadPool;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlobalProperties other = (GlobalProperties) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (threadPool != other.threadPool)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalProperties [threadPool=").append(threadPool).append(", email=").append(email).append("]");
		return builder.toString();
	}

}
