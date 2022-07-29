package com.yang.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    boolean auth(HttpServletRequest request, Authentication authentication);
}
