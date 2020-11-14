package com.gfa.greenbay.user;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
  List<User> findAll();

  @Query(value = "select username from user",nativeQuery = true)
  List<String> getUsernames();

  User findByUsername(String username);
}
