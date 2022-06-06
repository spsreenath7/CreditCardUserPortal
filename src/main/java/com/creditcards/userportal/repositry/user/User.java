package com.creditcards.userportal.repositry.user;

import java.util.List;

import javax.persistence.*;

import com.creditcards.userportal.repositry.creditcard.CreditCard;
import lombok.Data;

@Entity
@Data
@Table(
      name="USERS",
    uniqueConstraints=
    @UniqueConstraint(columnNames={"email"})
)
public class User {


  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  private List<CreditCard> creditCards;

}
