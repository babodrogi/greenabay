package com.gfa.greenbay.bid;

import org.springframework.stereotype.Service;

@Service
public class BidService {
  private BidRepository bidRepository;

  public BidService(BidRepository bidRepository) {
    this.bidRepository = bidRepository;
  }

  public void save(Bid bid){
    bidRepository.save(bid);
  }
}
