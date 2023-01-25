package com.gaaji.chat.statusmanagement.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class Subscribe implements Serializable {
    @NonNull
    private Boolean isSubscribe;

    public static Subscribe create() {
        return Subscribe.of(false);
    }

    public static Subscribe subscribe() {
        return Subscribe.of(true);
    }

    public static Subscribe unsubscribe() {
        return Subscribe.of(false);
    }

}
