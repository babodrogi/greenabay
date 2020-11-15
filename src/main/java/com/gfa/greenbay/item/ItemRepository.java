package com.gfa.greenbay.item;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
  List<Item> findAll();

  @Query(value = "SELECT * FROM item WHERE sellable = 1 ORDER BY created_at DESC LIMIT 5 OFFSET ?1 ",nativeQuery = true)
  List<Item> findNthFive(Integer n);
}
