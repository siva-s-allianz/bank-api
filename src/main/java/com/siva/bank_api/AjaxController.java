
package com.siva.bank_api;

import java.util.Map;
import com.siva.bank_api.dto.AjaxAccountForm;
import com.siva.bank_api.service.AjaxAccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class AjaxController {

    private final AjaxAccountService ajaxAccountService;

    // Constructor Injection
    public AjaxController(AjaxAccountService ajaxAccountService) {
        this.ajaxAccountService = ajaxAccountService;
    }

    @PostMapping("/api/ajax-account")
    public ResponseEntity<Map<String, Object>> createAccount(
            @Valid @RequestBody AjaxAccountForm form) {

        String result = ajaxAccountService.createAccount(form);

        if (result.startsWith("Account number already exists")) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "success", false,
                            "message", result
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "success", true,
                        "message", "Account created successfully!",
                        "accountNumber", form.getAccountNumber()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
        MethodArgumentNotValidException exception){
            String errorMessage = exception.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage();

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success",false,
                    "message", errorMessage));
    }
}
