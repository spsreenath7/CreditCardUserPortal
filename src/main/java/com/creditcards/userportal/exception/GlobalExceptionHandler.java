package com.creditcards.userportal.exception;

import java.util.ArrayList;
import java.util.List;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.utils.CreditCardPortalHelper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler  {

    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler(value = { CreditCardException.class })
    protected ModelAndView handleInternalErrors(final CreditCardException ex) {

        log.error("Outbound request failed :{}", ex.getMessage());
        return CreditCardPortalHelper.getPortalHomeModelView(ex.getUserid(), ex.getMessage());

    }

}
