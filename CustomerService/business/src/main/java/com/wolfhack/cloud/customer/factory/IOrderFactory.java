package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.CustomerOrder;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IOrderFactory {

    AnalyticsResponse create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException;

    CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor);

}
