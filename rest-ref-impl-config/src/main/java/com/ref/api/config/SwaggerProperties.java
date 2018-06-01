package com.ref.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = true)
public class SwaggerProperties {

    private String apiGroupName;
    private String apiVersion;
    private String apiInfoTitle;
    private String apiInfoDescription;
    private String email;
    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String licenseUrl;
    private String licenseName;
    
    public String getApiGroupName() {
        return apiGroupName;
    }
    
    public void setApiGroupName(String apiGroupName) {
        this.apiGroupName = apiGroupName;
    }
    
    
    public String getApiVersion() {
        return apiVersion;
    }

    
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
    
    public String getApiInfoTitle() {
        return apiInfoTitle;
    }
    
    public void setApiInfoTitle(String apiInfoTitle) {
        this.apiInfoTitle = apiInfoTitle;
    }
    
    public String getApiInfoDescription() {
        return apiInfoDescription;
    }
    
    public void setApiInfoDescription(String apiInfoDescription) {
        this.apiInfoDescription = apiInfoDescription;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }
    
    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String getContactUrl() {
        return contactUrl;
    }
    
    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }
    
    public String getLicenseUrl() {
        return licenseUrl;
    }
    
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }
    
    public String getLicenseName() {
        return licenseName;
    }
    
    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SwaggerProperties [apiGroupName=").append(apiGroupName).append(", apiVersion=")
                .append(apiVersion).append(", apiInfoTitle=")
                .append(apiInfoTitle).append(", apiInfoDescription=").append(apiInfoDescription).append(", email=")
                .append(email).append(", termsOfServiceUrl=").append(termsOfServiceUrl).append(", contactName=")
                .append(contactName).append(", contactUrl=").append(contactUrl).append(", licenseUrl=")
                .append(licenseUrl).append(", licenseName=").append(licenseName).append("]");
        return builder.toString();
    }

}
