package com.farm;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ErrorController {

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable, final Model model) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
