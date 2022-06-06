package com.creditcards.userportal.repositry.creditcard;

import javax.persistence.*;

import com.creditcards.userportal.repositry.user.User;
import lombok.Data;

@Entity
@Data
@Table(
    name="CARDS",
    uniqueConstraints=
    @UniqueConstraint(columnNames={"number"})
)
public class CreditCard {

  @Id
  @Column(name = "card_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "number")
  private String number;

  @Column(name = "expiry")
  private String expiryMonth;

  @Column(name = "holder_name")
  private String holderName;

  @ManyToOne
  @JoinColumn(name = "user_id",referencedColumnName="user_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CreditCard that = (CreditCard) o;

    if (!id.equals(that.id)) return false;
    if (!number.equals(that.number)) return false;
    if (!expiryMonth.equals(that.expiryMonth)) return false;
    if (!holderName.equals(that.holderName)) return false;
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + number.hashCode();
    result = 31 * result + expiryMonth.hashCode();
    result = 31 * result + holderName.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
