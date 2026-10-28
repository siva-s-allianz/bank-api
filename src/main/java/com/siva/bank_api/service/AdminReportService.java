package com.siva.bank_api.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service 
public class AdminReportService {
    
    @PreAuthorize ("hasRole('ADMIN')")
    public String getAdminReport() {
        // Logic to generate the report
        return "Admin report generated successfully.";
    }
}
