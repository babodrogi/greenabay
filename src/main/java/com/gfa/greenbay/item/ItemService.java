package com.gfa.greenbay.item;

import com.gfa.greenbay.user.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
  private ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public void save(Item item) {
    itemRepository.save(item);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }

  public List<Item> findNthFive(Integer n) {
    return itemRepository.findNthFive(n * 5);
  }

  public List<ListedSellableItemDao> listListedSellableItemDaos(Integer n) {
    List<ListedSellableItemDao> list = new ArrayList<>();
    for (Item item : findNthFive(n)) {
      if (item.getBids().size() > 0) {
        list.add(new ListedSellableItemDao(item.getId(),item.getName(), item.getPhotoUrl(),
            item.getBids().get(item.getBids().size() - 1)));
      } else {
        list.add(new ListedSellableItemDao(item.getId(),item.getName(), item.getPhotoUrl(),
            null));
      }
    }
    return list;
  }

  public Item findById(Long id) {
    return itemRepository.findById(id).orElse(null);
  }

  public boolean anyParameterMissing(NewItemDto newItemDto) {
    return newItemDto.getName() == null || newItemDto.getDescription() == null ||
        newItemDto.getPhotoUrl() == null || newItemDto.getStartingPrice() == null ||
        newItemDto.getPurchasePrice() == null;
  }

  public boolean anyWronglyProvidedPriceParameter(NewItemDto newItemDto) {
    return newItemDto.getStartingPrice() - newItemDto.getStartingPrice().intValue() != 0 ||
        newItemDto.getStartingPrice() <= 0 ||
        newItemDto.getPurchasePrice() - newItemDto.getPurchasePrice().intValue() != 0 ||
        newItemDto.getPurchasePrice() <= 0;
  }

  public Item createNewItemFromDto(NewItemDto newItemDto, User user) {
    return new Item(newItemDto.getName(), newItemDto.getDescription(), newItemDto.getPhotoUrl(),
        newItemDto.getStartingPrice().intValue(), newItemDto.getPurchasePrice().intValue(),
        user);
  }

  public NewlyCreatedItemDao createReturnDtoFromRequestDto(NewItemDto newItemDto, User user) {
    return new NewlyCreatedItemDao(newItemDto.getName(), newItemDto.getDescription(),
        newItemDto.getPhotoUrl(),
        newItemDto.getStartingPrice().intValue(), newItemDto.getPurchasePrice().intValue(),
        user.getUsername());
  }

  public ItemDao createItemDao(Item item) {
    return new ItemDao(item.getName(), item.getDescription(),
        item.getPhotoUrl(), item.getBids(), item.getPurchasePrice(),
        item.getSeller().getUsername(),
        item.isSellable() ? "Not yet sold" : "Sold",
        item.isSellable() ? null : item.getBuyer().getUsername());
  }

}
