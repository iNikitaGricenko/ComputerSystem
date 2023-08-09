package com.wolfhack.cloud.product.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

	@Value("${AWS_ACCESS_KEY_ID}") private String key;

	@Value("${AWS_SECRET_ACCESS_KEY}") private String secret;

	@Value("${AWS_ACCESS_ENDPOINT}") private String endpoint;

	@Value("${AWS_ACCESS_REGION}") private String region;

	@Bean
	public AmazonS3 getAmazonS3() {
		AwsClientBuilder.EndpointConfiguration endpointConfiguration = getAWSEndpointConfiguration();
		AWSCredentialsProvider credentials = getAWSCredentialsProvider();
		return AmazonS3ClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).withCredentials(credentials).build();
	}

	private AwsClientBuilder.EndpointConfiguration getAWSEndpointConfiguration() {
		return new AwsClientBuilder.EndpointConfiguration(endpoint, region);
	}

	private AWSCredentialsProvider getAWSCredentialsProvider() {
		return new AWSStaticCredentialsProvider(getAWSCredentials());
	}

	private AWSCredentials getAWSCredentials() {
		return new BasicAWSCredentials(key, secret);
	}

}
