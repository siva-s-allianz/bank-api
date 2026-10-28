package com.siva.bank_api;

import com.siva.bank_api.service.AdminReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminReportService adminReportService;

    public AdminController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Admin";
    }

    @GetMapping("/report")
    public String getReport() {
        return adminReportService.getAdminReport();
    }
}