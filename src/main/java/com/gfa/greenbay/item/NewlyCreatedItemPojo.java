package com.gfa.greenbay.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewlyCreatedItemPojo {
  private String name;
  private String description;
  private String photoUrl;
  private Integer startingPrice;
  private Integer purchasePrice;
  private String seller;
}
