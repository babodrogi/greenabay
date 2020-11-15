package com.gfa.greenbay.user;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.item.Item;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private int greenBayDollars;
  @OneToMany(mappedBy = "bidder",cascade = CascadeType.ALL)
  private List<Bid> bids;
  @OneToMany(mappedBy = "seller")
  private List<Item> sellableItems;
  @OneToMany(mappedBy = "buyer")
  private List<Item> boughtItems;

  public User(String username, String password,int greenBayDollars) {
    this.username = username;
    this.password = password;
    this.greenBayDollars = greenBayDollars;
  }

  public User(String username) {
    this.username = username;
  }
}
