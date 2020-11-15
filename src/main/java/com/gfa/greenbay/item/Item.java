package com.gfa.greenbay.item;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.user.User;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private String photoUrl;
  @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
  private List<Bid> bids;
  private Integer startingPrice;
  private Integer purchasePrice;
  @ManyToOne
  private User seller;
  private boolean sellable;
  @ManyToOne
  private User buyer;
  private Date createdAt;

  public Item(String name, String description, String photoUrl, Integer startingPrice,
              Integer purchasePrice,
              User seller) {
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.startingPrice = startingPrice;
    this.purchasePrice = purchasePrice;
    this.seller = seller;
    sellable = true;
    createdAt = new Date();
  }



}
