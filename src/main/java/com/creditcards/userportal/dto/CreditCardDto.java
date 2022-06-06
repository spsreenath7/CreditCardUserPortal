package com.creditcards.userportal.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

  String cardId;

  String cardNumber;

  String holderName;

  String expiryMonth;
}
