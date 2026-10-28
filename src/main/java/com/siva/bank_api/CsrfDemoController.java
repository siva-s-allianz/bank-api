package com.siva.bank_api;

import java.util.Map;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo/csrf")
public class CsrfDemoController {

    @GetMapping("/token")
    public Map<String, String> token(CsrfToken csrfToken) {
        return Map.of(
            "headerName", csrfToken.getHeaderName(),
            "token", csrfToken.getToken()
        );
    }

    @PostMapping("/test")
    public Map<String, String> test() {
        return Map.of("message", "CSRF validation passed");
    }
}