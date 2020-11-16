package com.gfa.greenbay.item;

import com.gfa.greenbay.bid.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListedSellableItemDao {
  private Long itemId;
  private String name;
  private String photoUrl;
  private Bid highestBid;

  public Bid getHighestBid() {
    return highestBid;
  }
}
