package com.wolfhack.cloud.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return getPermissionsFromSource(source).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    private List<String> getPermissionsFromSource(Jwt source) {
        List<String> permissions = new ArrayList<>(getAuthoritiesFromSource(source));
        permissions.addAll(getScopesFromSource(source));
        return permissions;
    }

    private List<String> getScopesFromSource(Jwt source) {
        return ((Collection<String>) source.getClaims().get("scope"))
                .stream()
                .map(scope -> "SCOPE_"+scope)
                .collect(toList());
    }

    private List<String> getAuthoritiesFromSource(Jwt source) {
        return new ArrayList<>(((Collection<String>) source.getClaims().get("authorities")));
    }
}
