package com.example.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorRedirectController {

    @GetMapping("/error-handler")
    public String errorHandler(Model model) {
        // by default go back to home index where error message will be shown
        // TO MAKE THIS UNIVERSAL, YOU CAN ROUTE BACK TO THE LAST VISITED PAGE

        // fallback: redirect to home
        return "redirect:/";
    }
}
