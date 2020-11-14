package com.gfa.greenbay.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void save(User user){
    userRepository.save(user);
  }

  public List<User> findAll(){
    return userRepository.findAll();
  }

  public List<String> listUsername(){
    return userRepository.getUsernames();
  }

  public User findByUsername(String username){
    return userRepository.findByUsername(username);
  }

}
