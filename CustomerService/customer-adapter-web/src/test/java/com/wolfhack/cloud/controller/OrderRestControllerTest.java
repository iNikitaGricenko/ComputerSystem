package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerRequestDTO;
import com.wolfhack.cloud.customer.dto.OrderItemRequestDTO;
import com.wolfhack.cloud.customer.model.enums.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
//@SpringBootTest(classes = TestContext.class)
class OrderRestControllerTest {

//	@Autowired
	private MockMvc mockMvc;

//	@Test
	void getOne() throws Exception {
		mockMvc.perform(get("/api/order/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Test
	void getAnalytics() throws Exception {
		mockMvc.perform(get("/api/order/analytics"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Test
	void add() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da", "");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO =
				new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard",
						Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON).param(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Test
	void addNullCustomerOrderRequestDTOFails() throws Exception {
		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO(null, null, null, null, null, null, null, null, null, null);

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON).param(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

//	@Test
	void addNullCustomerFails() throws Exception {
		CustomerOrderRequestDTO requestDTO;

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da", "");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		requestDTO = new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard",
						Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", null, Set.of(item));

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON).param(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

//	@Test
	void addNullItemsFails() throws Exception {
		CustomerOrderRequestDTO requestDTO;

		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		requestDTO =
				new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard",
						Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, null);

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON).param(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}


//	@Test
	void addWithValidationFails() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+11 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da", "");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO =
				new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard",
						Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		mockMvc.perform(post("/api/order").accept(MediaType.APPLICATION_JSON).param(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

//	@Test
	void update() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da", "");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO =
				new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard",
						Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		mockMvc.perform(put("/api/order/analytics").accept(MediaType.APPLICATION_JSON).content(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

//	@Test
	void updateFails() throws Exception {
		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO(null, null, null, null, null, null, null, null, null, null);

		mockMvc.perform(put("/api/order/analytics").accept(MediaType.APPLICATION_JSON).content(requestDTO.toString()))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}