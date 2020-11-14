package com.gfa.greenbay.user;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
  List<User> findAll();
}