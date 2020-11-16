package com.gfa.greenbay.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfa.greenbay.item.Item;
import com.gfa.greenbay.user.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int amount;
  @ManyToOne
  @JsonIgnore
  private Item item;
  @ManyToOne
  @JsonIgnore
  private User bidder;

  public Bid(int amount, Item item, User bidder) {
    this.amount = amount;
    this.item = item;
    this.bidder = bidder;
  }
}
