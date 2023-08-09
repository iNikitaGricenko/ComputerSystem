package fake.adapter;

import com.wolfhack.cloud.customer.adapter.PaymentAdapter;
import com.wolfhack.cloud.customer.model.CustomerOrder;

public class FakePayment implements PaymentAdapter {
	@Override
	public String pay(CustomerOrder order) {
		return "";
	}

	@Override
	public String confirm(String charge) {
		return "";
	}

	@Override
	public String cancel(String chargeReference, Long refundedAmount) {
		return "";
	}
}
