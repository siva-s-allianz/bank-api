
package com.siva.bank_api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import com.siva.bank_api.dto.BankAccountForm;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/accounts/new")
    public String showAccountForm(Model model) {
        model.addAttribute("bankAccountForm", new BankAccountForm());
        return "account-form";
    }

    @PostMapping("/accounts/create")
    public String createAccount(
            @Valid @ModelAttribute("bankAccountForm") BankAccountForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "account-form";
        }

        model.addAttribute("customerName", form.getCustomerName());
        model.addAttribute("accountNumber", form.getAccountNumber());
        model.addAttribute("balance", form.getBalance());

        return "success";
    }
}
