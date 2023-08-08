package com.wolfhack.cloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerRequestDTO;
import com.wolfhack.cloud.customer.dto.OrderItemRequestDTO;
import com.wolfhack.cloud.customer.model.enums.Currency;
import config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {TestConfig.class})
@Import(OrderRestController.class)
@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest {

	@Autowired private MockMvc mockMvc;

	@Test
	void getAll() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard", Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(requestDTO);

		mockMvc.perform(post("/api/order/").contentType(MediaType.APPLICATION_JSON).content(requestJson));

		mockMvc.perform(get("/api/order/")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void getOne() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard", Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(requestDTO);

		mockMvc.perform(post("/api/order/").contentType(MediaType.APPLICATION_JSON).content(requestJson));

		mockMvc.perform(get("/api/order/{id}/", 1)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value("1"));
	}

	@Test
	void getAnalytics() throws Exception {
		mockMvc.perform(get("/api/order/analytics/").param("status", "INPROGRESS").param("from", "2023-07-06").param("to", "2023-07-08")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.totalPrice").value("3475.0")).andExpect(jsonPath("$.totalQuantity").value("2")).andExpect(jsonPath("$.maxOrderPrice").value("3405.0")).andExpect(jsonPath("$.minOrderPrice").value("70.0"));
	}

	@Test
	void add() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard", Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(requestDTO);

		mockMvc.perform(post("/api/order/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	}

	@Test
	void update() throws Exception {
		CustomerRequestDTO customer = new CustomerRequestDTO("email@domain.com", "Nikit", "Sambatist", "+111 (202) 555-0125");

		OrderItemRequestDTO item = new OrderItemRequestDTO(3L, "Intel Core i3", "i83da");
		item.setUnitPrice(99.9f);
		item.setQuantity(1);

		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO("Historichna 84", "Some test description", "Mastercard", Currency.EUR, "Zaporizhzhya", "Zaporizhzhya", "Ukraine", "69000", customer, Set.of(item));

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(requestDTO);

		mockMvc.perform(post("/api/order/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();

		mockMvc.perform(put("/api/order/{id}", "1").param("status", "DELIVERED")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(1));
	}

	@Test
	void updateFails() throws Exception {
		CustomerOrderRequestDTO requestDTO = new CustomerOrderRequestDTO(null, null, null, null, null, null, null, null, null, null);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(requestDTO);

		mockMvc.perform(put("/api/order/analytics/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isBadRequest());
	}
}