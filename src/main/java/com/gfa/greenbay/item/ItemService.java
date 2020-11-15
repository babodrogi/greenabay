package com.gfa.greenbay.item;

import com.gfa.greenbay.exception.NoSuchItemException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

  public List<Item> findNthFive(Integer n){
    return itemRepository.findNthFive(n * 5);
  }

  public List<SellableItemListDto> listSellableItemDtos(Integer n){
    List<SellableItemListDto> list = new ArrayList<>();
    for (Item item :findNthFive(n)) {
      if (item.getBids().size() > 0) {
        list.add(new SellableItemListDto(item.getName(), item.getPhotoUrl(),
            item.getBids().get(item.getBids().size() - 1)));
      }else {
        list.add(new SellableItemListDto(item.getName(), item.getPhotoUrl(),
            null));
      }
    }
  return list;
  }


  public Item findById(Long id){
    return itemRepository.findById(id).orElse(null);
  }

}
