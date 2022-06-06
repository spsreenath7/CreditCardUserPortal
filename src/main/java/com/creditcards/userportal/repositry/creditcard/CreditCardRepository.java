package com.creditcards.userportal.repositry.creditcard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

  @Query(value = "SELECT * FROM CARDS c WHERE c.user_id = :user_id and number like :card_number",
      nativeQuery = true)
  List<CreditCard> fetchCreditCardForUser(@Param("user_id") Long user_id, @Param("card_number") String card_number);

}
