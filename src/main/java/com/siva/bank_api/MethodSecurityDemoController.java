package com.siva.bank_api;

import org.springframework.web.bind.annotation.RestController;

import com.siva.bank_api.service.AdminReportService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MethodSecurityDemoController {

    private final AdminReportService adminReportService;

    public MethodSecurityDemoController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }
    
    @GetMapping ("/api/method-security-demo")
    public String testMethodSecurity() {
        return adminReportService.getAdminReport();
    }
}
