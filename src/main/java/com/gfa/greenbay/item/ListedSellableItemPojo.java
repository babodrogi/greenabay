package com.gfa.greenbay.item;

import com.gfa.greenbay.bid.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListedSellableItemPojo {
  private Long itemId;
  private String name;
  private String photoUrl;
  private Bid highestBid;

  public Bid getHighestBid() {
    return highestBid;
  }
}
