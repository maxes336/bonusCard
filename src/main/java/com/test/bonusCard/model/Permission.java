package com.test.bonusCard.model;

public enum Permission {
    OWN_CARDS_READ("own_cards:read"),
    ALL_CARDS_READ("all_cards:read"),
    OWN_CARDS_WRITE("own_cards:write"),
    ALL_CARDS_WRITE("all_cards:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    MODERATOR_READ("moderator:read"),
    MODERATOR_WRITE("moderator:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
