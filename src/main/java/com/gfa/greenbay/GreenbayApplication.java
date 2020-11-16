package com.gfa.greenbay;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.bid.BidService;
import com.gfa.greenbay.item.Item;
import com.gfa.greenbay.item.ItemService;
import com.gfa.greenbay.user.User;
import com.gfa.greenbay.user.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenbayApplication implements CommandLineRunner {

  @Autowired
  private UserService userService;

  @Autowired
  private ItemService itemService;

  @Autowired
  private BidService bidService;

  public static void main(String[] args) {
    SpringApplication.run(GreenbayApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//
//    User user1 = new User("Palika");
//    User user2 = new User("pocs");
//    Item item1 = new Item();
//    Item item2 = new Item();
//    Item item3 = new Item();
//    item1.setSellable(true);
//    item2.setSellable(true);
//    item3.setSellable(true);
//    item1.setSeller(user1);
//    item2.setSeller(user1);
//    item3.setSeller(user2);
//    List<Bid> bids1 = new ArrayList<>();
//    List<Bid> bids2 = new ArrayList<>();
//    Bid bid1 = new Bid();
//    Bid bid2 = new Bid();
//    Bid bid3 = new Bid();
//    Bid bid4 = new Bid();
//    Bid bid5 = new Bid();
//    bid1.setItem(item1);
//    bid2.setItem(item1);
//    bid3.setItem(item1);
//    bid4.setItem(item1);
//    bid5.setItem(item2);
//    bids1.add(bid1);
//    bids1.add(bid2);
//    bids1.add(bid3);
//    bids1.add(bid4);
//    bids2.add(bid5);
//    item1.setBids(bids1);
//    item2.setBids(bids2);
//    userService.save(user1);
//    userService.save(user2);
//    itemService.save(item2);
//    itemService.save(item1);
//    itemService.save(item3);
  }
}
