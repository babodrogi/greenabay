package com.gfa.greenbay.item;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
  private ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public void save(Item item){
    itemRepository.save(item);
  }

  public List<Item> findAll(){
   return itemRepository.findAll();
  }
}
