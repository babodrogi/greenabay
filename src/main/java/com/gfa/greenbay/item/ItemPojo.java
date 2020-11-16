package com.gfa.greenbay.item;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.user.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemPojo {
  private String name;
  private String description;
  private String photoUrl;
  private List<Bid> bids;
  private Integer purchasePrice;
  private String seller;
  private String sellable;
  private String buyer;
}
