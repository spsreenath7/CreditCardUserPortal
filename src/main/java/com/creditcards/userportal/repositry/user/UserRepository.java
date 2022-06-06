package com.creditcards.userportal.repositry.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.email = :email and u.password = :password")
  List<User> findByCredentials(@Param("email")String emailId, @Param("password")String password);
}
