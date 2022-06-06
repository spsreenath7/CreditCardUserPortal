package com.creditcards.userportal.controller;

import java.util.ArrayList;
import java.util.List;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.service.CreditCardService;
import com.creditcards.userportal.utils.CreditCardPortalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CreditCardController {

  @Autowired
  CreditCardService creditCardService;

  @GetMapping("/searchCard/{userid}/cards")
  public ModelAndView searchCards(@PathVariable("userid") String userid, @RequestParam("cardnumber") String cardNumber) {
    return CreditCardPortalHelper.getSearchModelView(userid, creditCardService.fetchCreditCards(userid, cardNumber));
  }

  @GetMapping("/searchCard/{userid}")
  public ModelAndView searchCards(@PathVariable("userid") String userid) {
    return CreditCardPortalHelper.getSearchModelView(userid, CreditCardPortalHelper.emptyCardDtoList());
  }

  @GetMapping("/addCard/{userid}")
  public ModelAndView saveCreditCard(@PathVariable("userid") String userid) {
    return CreditCardPortalHelper.getSaveModelView(userid, CreditCardDto.builder().build());
  }

  @PostMapping("/saveCard/{userid}")
  public ModelAndView saveCreditCard(@PathVariable("userid") String userid, @ModelAttribute CreditCardDto creditCardDto) {

    List<String> errors = CreditCardPortalHelper.validateCardDetail(creditCardDto);
    if(! errors.isEmpty()){
      ModelAndView result= CreditCardPortalHelper.getSaveModelView(userid, CreditCardDto.builder().build());
      result.addObject("errors", errors);
      return result;
    }
    creditCardService.saveCard(creditCardDto, userid);
    return CreditCardPortalHelper.getPortalHomeModelView(userid, "Credit card deleted.");
  }

  @GetMapping("/updateCard/{userid}/card/{creditCardId}")
  public ModelAndView updateCreditCardDetail(@PathVariable("userid") String userid, @PathVariable("creditCardId") String creditCardId) {
    return CreditCardPortalHelper.getSaveModelView(userid, creditCardService.fetchCreditCardDetail(creditCardId, userid));
  }

  @GetMapping("/deleteCard/{userid}/card/{creditCardId}")
  public ModelAndView deleteCreditCardDetail(@PathVariable("userid") String userid, @PathVariable("creditCardId") String creditCardId) {
    creditCardService.deleteCard(creditCardId, userid);
    return CreditCardPortalHelper.getPortalHomeModelView(userid, "Credit card deleted.");
  }
}
