package com.creditcards.userportal.service;

import com.creditcards.userportal.dto.CreditCardDto;
import com.creditcards.userportal.repositry.creditcard.CreditCard;
import com.creditcards.userportal.repositry.creditcard.CreditCardRepository;
import com.creditcards.userportal.repositry.user.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceTest {

    CreditCardService creditCardService;

    @Mock
    CreditCardRepository mockCreditCardRepository;

    @Mock
    UserAccountService mockUserAccountService;

    @Mock
    User mockUser;

    @BeforeEach
    public void setup(){
        creditCardService = new CreditCardService(mockCreditCardRepository, mockUserAccountService);
    }

    @Test
    public void saveCardTest()
    {
        Mockito.doNothing().when(mockCreditCardRepository.save(Mockito.isA(CreditCard.class)));
        Mockito.when(mockUserAccountService.getUser(12l)).thenReturn(Optional.ofNullable(mockUser));

        creditCardService.saveCard(CreditCardDto.builder().build(), "12");

        Mockito.verify(mockCreditCardRepository, Mockito.times(1)).save(Mockito.isA(CreditCard.class));
        Mockito.verify(mockUserAccountService, Mockito.times(1)).getUser(12l);
    }

    @Test
    public void saveCardUserNotFoundExceptionTest()
    {
        Mockito.doNothing().when(mockCreditCardRepository.save(Mockito.isA(CreditCard.class)));
        Mockito.when(mockUserAccountService.getUser(12l)).thenReturn(Optional.ofNullable(null));

        try {
            creditCardService.saveCard(CreditCardDto.builder().build(), "12");
        }catch (Exception exception){
            Assert.assertEquals("User not authorised to perform this operation",exception.getMessage());
        }

        Mockito.verify(mockCreditCardRepository, Mockito.times(0)).save(Mockito.isA(CreditCard.class));
        Mockito.verify(mockUserAccountService, Mockito.times(1)).getUser(12l);
    }

    @Test
    public void saveCardNumberExistsExceptionTest()
    {
        Mockito.when(mockCreditCardRepository.save(Mockito.isA(CreditCard.class))).thenThrow(new DataIntegrityViolationException("some message"));
        Mockito.when(mockUserAccountService.getUser(12l)).thenReturn(Optional.ofNullable(null));

        try {
            creditCardService.saveCard(CreditCardDto.builder().build(), "12");
        }catch (Exception exception){
            Assert.assertEquals("Exception occurred in saving card. Credit card may already exist.",exception.getMessage());
        }

        Mockito.verify(mockCreditCardRepository, Mockito.times(0)).save(Mockito.isA(CreditCard.class));
        Mockito.verify(mockUserAccountService, Mockito.times(1)).getUser(12l);
    }
}
