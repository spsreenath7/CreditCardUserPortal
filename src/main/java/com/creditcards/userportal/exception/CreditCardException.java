package com.creditcards.userportal.exception;

public class CreditCardException extends Exception {

  String userid;

  public CreditCardException(final String errorMessage, final String userid){
    super(errorMessage);
    this.userid =userid;
  }

  public String getUserid() {
    return userid;
  }
}
