package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.CustomerOrder;

public interface PaymentAdapter {

	String pay(CustomerOrder order);

	String confirm(String charge);

	String cancel(String chargeReference, Long refundedAmount);
}
