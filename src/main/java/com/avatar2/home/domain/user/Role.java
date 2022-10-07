package com.avatar2.home.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "sonnim"),
    USER("ROLE_USER", "sayongja");

    private final String key;
    private final String title;
}
