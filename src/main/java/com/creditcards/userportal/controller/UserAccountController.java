package com.creditcards.userportal.controller;

import java.util.Optional;

import com.creditcards.userportal.repositry.user.User;
import com.creditcards.userportal.service.UserAccountService;
import com.creditcards.userportal.utils.CreditCardPortalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {

  @Autowired
  UserAccountService userAccountService;

  @GetMapping("/users")
  public ModelAndView getAllUsers() {
    ModelAndView mav = new ModelAndView("list-users");
    mav.addObject("users", userAccountService.getUsers());
    return mav;
  }

  @GetMapping({"/landingPage", "/"})
  public ModelAndView addEmployeeForm() {
    ModelAndView mav = new ModelAndView("add-user-form");
    User newUser = new User();
    mav.addObject("loginError", "");
    mav.addObject("user", newUser);
    return mav;
  }

  @PostMapping("/signUp")
  public String createUserAccount(@ModelAttribute User user) {
    userAccountService.addUser(user);
    return "redirect:/landingPage";
  }

  @PostMapping("/login")
  public ModelAndView login(@ModelAttribute User user) {

    ModelAndView result;

    Optional<User> loggedInUser = userAccountService.fetchUserAccount(user.getEmail(), user.getPassword());

    if(! loggedInUser.isPresent()){
      result = new ModelAndView("add-user-form");
      result.addObject("loginError", "invalid email or password");
      User newUser = new User();
      result.addObject("user", newUser);
      return result;
    }

    result = CreditCardPortalHelper.getPortalHomeModelView(String.valueOf(loggedInUser.get().getId()), "");
    result.addObject("username", loggedInUser.get().getName());
    return result;
  }



}
