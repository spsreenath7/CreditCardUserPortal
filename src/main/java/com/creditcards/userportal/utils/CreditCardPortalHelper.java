package com.creditcards.userportal.utils;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.repositry.creditcard.CreditCard;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class CreditCardPortalHelper {

    public static ModelAndView getPortalHomeModelView(String userid, String message){
        ModelAndView result=new ModelAndView("credit-portal-home");
        List<CreditCardDto> creditCardDtos = new ArrayList<>();
        result.addObject("userid", userid);
        result.addObject("previousTransMessage", message);

        return result;
    }

    public static ModelAndView getSearchModelView(String userid, List<CreditCardDto> creditCardDtos){
        ModelAndView result=new ModelAndView("list-cards");
        result.addObject("userid", userid);
        result.addObject("cards", creditCardDtos);

        return result;
    }

    public static ModelAndView getSaveModelView(String userid, CreditCardDto creditCardDto){
        ModelAndView result=new ModelAndView("list-cards");
        List<CreditCardDto> creditCardDtos = new ArrayList<>();
        result.addObject("userid", userid);
        result.addObject("creditCardDto", creditCardDto);

        return result;
    }

    public static String getSearchCardQueryParam(String cardNumber){
        StringBuilder queryParam = new StringBuilder();
        queryParam.append('%');
        queryParam.append(cardNumber);
        queryParam.append('%');

        return queryParam.toString();
    }

    public static List<String> validateCardDetail(CreditCardDto creditCardDto) {
        List<String> errors = new ArrayList<>();

        if(creditCardDto.getCardNumber().length() != 16)
            errors.add("Card number must be 16 digits.");
        if(! creditCardDto.getExpiryMonth().contains("-"))
            errors.add("invalid input format");

        return errors;
    }

    public static List<CreditCard> emptyCardList(){
        return new ArrayList<>();
    }

    public static List<CreditCardDto> emptyCardDtoList(){
        return new ArrayList<>();
    }
}
