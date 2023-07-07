package config;

import com.wolfhack.cloud.customer.factory.IOrderCreator;
import com.wolfhack.cloud.customer.service.IOrderService;
import fake.FakeOrderCreator;
import fake.FakeOrderService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestConfig {

	@Bean
	public IOrderCreator getOrderCreator() {
		return new FakeOrderCreator();
	}

	@Bean
	public IOrderService getOrderService() {
		return new FakeOrderService();
	}

}
