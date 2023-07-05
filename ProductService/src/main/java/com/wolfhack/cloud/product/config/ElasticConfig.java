package com.wolfhack.cloud.product.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.wolfhack.cloud.product.repository.search")
@ComponentScan(basePackages = {"com.wolfhack.cloud.product.service.search"})
public class ElasticConfig extends AbstractElasticsearchConfiguration {

	@Value("${elasticsearch-host-port}")
	private String elasticHostPort;

	@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(elasticHostPort).build();

		return RestClients.create(clientConfiguration).rest();
	}

}
