package com.creditcards.userportal.service.transformer;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.repositry.creditcard.CreditCard;
import com.creditcards.userportal.repositry.user.User;

public class CreditCardTransformer {

    public static CreditCard mapCreditCard(CreditCardDto creditCardDto, User user) {
        CreditCard card = new CreditCard();
        card.setNumber(creditCardDto.getCardNumber());
        card.setHolderName(creditCardDto.getHolderName());
        card.setExpiryMonth(card.getExpiryMonth());
        card.setUser(user);

        return card;
    }

    public static CreditCardDto mapCreditCardDto(CreditCard creditCard) {
        CreditCardDto creditCardDto = CreditCardDto.builder()
                .cardId(String.valueOf(creditCard.getId()))
                .cardNumber(creditCard.getNumber())
                .holderName(creditCard.getHolderName())
                .expiryMonth(creditCard.getExpiryMonth())
                .build();

        return creditCardDto;
    }
}
