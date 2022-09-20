package com.wolfhack.cloud.oauth2.factory;

import com.wolfhack.cloud.oauth2.model.User;

import java.util.function.Consumer;
import java.util.function.Function;

public interface UpdateFactory {

    <U, T> UpdateFactory update(U editor, Function<U, T> getMethod, Consumer<T> setMethod);

    User build();
}
