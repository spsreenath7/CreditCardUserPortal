package com.creditcards.userportal.service;

import java.util.List;
import java.util.Optional;

import com.creditcards.userportal.repositry.user.User;
import com.creditcards.userportal.repositry.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getUsers(){
    Iterable<User> users = userRepository.findAll();
    return (List<User>) users;
  }

  public User addUser(User user){
    return userRepository.save(user);
  }

  public Optional<User> getUser(Long userid){
    return userRepository.findById(userid);
  }

  public Optional<User> fetchUserAccount(String email, String password){

    List<User> users = userRepository.findByCredentials(email, password);

    if(users.isEmpty())
      return Optional.ofNullable(null);



    return Optional.ofNullable(users.get(0));
  }

}
