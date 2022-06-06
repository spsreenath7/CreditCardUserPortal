package com.creditcards.userportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.exception.CreditCardException;
import com.creditcards.userportal.repositry.creditcard.CreditCard;
import com.creditcards.userportal.repositry.creditcard.CreditCardRepository;
import com.creditcards.userportal.repositry.user.User;
import com.creditcards.userportal.service.transformer.CreditCardTransformer;
import com.creditcards.userportal.utils.CreditCardPortalHelper;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

  @Autowired
  CreditCardRepository creditCardRepository;

  @Autowired
  UserAccountService userAccountService;

  public CreditCardService(CreditCardRepository creditCardRepository, UserAccountService userAccountService) {
    this.creditCardRepository = creditCardRepository;
    this.userAccountService = userAccountService;
  }

  @SneakyThrows
  public void saveCard(CreditCardDto creditCardDto, String userid) {
    Optional<User> user = userAccountService.getUser(Long.valueOf(userid));
    if(!user.isPresent())
      throw  new CreditCardException("User not authorised to perform this operation", userid);

    CreditCard card = CreditCardTransformer.mapCreditCard(creditCardDto, user.get());
    try{
      creditCardRepository.save(card);
    }catch(DataIntegrityViolationException exception){
      throw new CreditCardException("Exception occurred in saving card. Credit card may already exsist.", userid);
    }
  }

  @SneakyThrows
  public CreditCardDto fetchCreditCardDetail(String creditCardId, String userid){
    Optional<CreditCard> creditCard = creditCardRepository.findById(Long.valueOf(creditCardId));
    if(! creditCard.isPresent())
      throw  new CreditCardException("User not authorised to perform this operation", userid);

    String cardUserId = String.valueOf(creditCard.get().getUser().getId());
    if(! userid.equalsIgnoreCase(cardUserId))
      throw  new CreditCardException("User not authorised to perform this operation", userid);

    return CreditCardTransformer.mapCreditCardDto(creditCard.get());
  }

  @SneakyThrows
  public List<CreditCardDto> fetchCreditCards(String userid, String cardNumber){
   List<CreditCard> creditCards;
    try {
      creditCards = Optional.of(creditCardRepository.fetchCreditCardForUser(Long.valueOf(userid), CreditCardPortalHelper.getSearchCardQueryParam(cardNumber))).orElse(CreditCardPortalHelper.emptyCardList()); //.orElse(new ArrayList<>()));
    }catch (Exception exception){
      throw  new CreditCardException("Delete operation failed.", userid);
    }

    return creditCards.stream().map(CreditCardTransformer::mapCreditCardDto).collect(Collectors.toList());
  }

  @SneakyThrows
  public void deleteCard(String creditCardId, String userid){
    try {
      creditCardRepository.deleteById(Long.valueOf(creditCardId));
    }catch (Exception exception){
      throw  new CreditCardException("Delete operation failed.", userid);
    }
  }

}
