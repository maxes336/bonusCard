package com.test.bonusCard.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.OWN_CARDS_READ, Permission.OWN_CARDS_WRITE)),

    MODERATOR(Set.of(Permission.ALL_CARDS_READ, Permission.ALL_CARDS_WRITE,
            Permission.USER_READ, Permission.USER_WRITE)),

    ADMIN(Set.of(Permission.ALL_CARDS_READ, Permission.ALL_CARDS_WRITE,
            Permission.USER_READ, Permission.USER_WRITE,
            Permission.MODERATOR_READ, Permission.MODERATOR_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
