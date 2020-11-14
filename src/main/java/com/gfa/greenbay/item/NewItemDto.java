package com.gfa.greenbay.item;

import lombok.Getter;

@Getter
public class NewItemDto {

  private String name;
  private String description;
  private String photoUrl;
  private Double startingPrice;
  private Double purchasePrice;
}
