package com.hcmute.tech_shop.exception;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException ex,
                                                  RedirectAttributes redirect) {
        redirect.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login?usernameNotFound=true";
    }

    @ExceptionHandler(DisabledException.class)
    public String handleDisabledException(DisabledException ex, RedirectAttributes redirect) {
        redirect.addFlashAttribute("message", ex.getMessage());
        return "redirect:/login?disabled=true";
    }

}
