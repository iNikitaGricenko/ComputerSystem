package com.wolfhack.cloud.customer.factory;

import java.util.function.Consumer;
import java.util.function.Function;

public interface UpdateFactory {

    <T, U> UpdateFactory edit(T editor, Function<T, U> getMethod, Consumer<U> setMethod);

    <T> T update();
}