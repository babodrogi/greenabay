package com.gfa.greenbay.transaction;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.item.Item;
import com.gfa.greenbay.item.ItemService;
import com.gfa.greenbay.user.User;
import com.gfa.greenbay.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
  private ItemService itemService;
  private UserService userService;

  @Autowired
  public TransactionService(ItemService itemService, UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
  }

  public int getMinimumBidAmount(Item item) {
    try {
      return item.getBids().get(item.getBids().size() - 1).getAmount() + 1 ;
    } catch (IndexOutOfBoundsException exception) {
      return item.getStartingPrice();
    }
  }

  public void placeBid(Item item, Bid bid) {
    List<Bid> bidsUpdate = item.getBids();
    bidsUpdate.add(bid);
    item.setBids(bidsUpdate);
    itemService.save(item);
  }

  public void buyItem(Item item, User user) {
    item.setBuyer(user);
    item.setSellable(false);
    List<Item> boughtItemsUpdate = user.getBoughtItems();
    boughtItemsUpdate.add(item);
    user.setBoughtItems(boughtItemsUpdate);
    userService.save(user);
  }
}
