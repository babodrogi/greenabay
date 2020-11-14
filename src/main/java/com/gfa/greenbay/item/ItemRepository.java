package com.gfa.greenbay.item;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item,Long> {
  List<Item> findAll();
}
