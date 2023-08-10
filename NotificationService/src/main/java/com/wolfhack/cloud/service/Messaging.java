package com.wolfhack.cloud.service;

import com.wolfhack.cloud.model.MessagePOJO;
import com.wolfhack.cloud.model.MulticastMessagePOJO;
import com.wolfhack.cloud.model.SubscriptionPOJO;
import com.wolfhack.cloud.model.TopicMessagePOJO;

import java.util.List;

public interface Messaging {

	String send(MessagePOJO message);

	String send(TopicMessagePOJO message);

	List<String> send(MulticastMessagePOJO message);

	void subscribe(SubscriptionPOJO subscription);

}
