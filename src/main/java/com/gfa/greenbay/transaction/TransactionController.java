package com.gfa.greenbay.transaction;

import com.gfa.greenbay.bid.Bid;
import com.gfa.greenbay.exception.InSufficientBidAmountException;
import com.gfa.greenbay.exception.InsufficientFundsException;
import com.gfa.greenbay.exception.ItemNotSellableException;
import com.gfa.greenbay.exception.NoSuchItemException;
import com.gfa.greenbay.exception.NoTokenException;
import com.gfa.greenbay.exception.TokenExpiredException;
import com.gfa.greenbay.item.Item;
import com.gfa.greenbay.item.ItemService;
import com.gfa.greenbay.user.User;
import com.gfa.greenbay.user.UserService;
import com.gfa.greenbay.util.JwtTokenUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greenbay")
public class TransactionController {

  private ItemService itemService;
  private JwtTokenUtil jwtTokenUtil;
  private TransactionService transactionService;
  private UserService userService;

  @Autowired
  public TransactionController(ItemService itemService,
                               JwtTokenUtil jwtTokenUtil,
                               TransactionService transactionService,
                               UserService userService) {
    this.itemService = itemService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.transactionService = transactionService;
    this.userService = userService;
  }

  @PostMapping("/bid")
  public ResponseEntity<?> bid(HttpServletRequest request, @RequestParam Long itemId,
                               @RequestParam int amount) {
    if (request.getHeader("jwt") == null) {
      throw new NoTokenException();
    } else {
      String token = jwtTokenUtil.getTokenFromRequest(request);
      if (jwtTokenUtil.isTokenExpired(token)) {
        throw new TokenExpiredException();
      } else {
        Item item = itemService.findById(itemId);
        User user = userService.findByUsername(jwtTokenUtil.getUsernameFromToken(token));
        Bid bid = new Bid(amount, item, user);
        if (item == null) {
          throw new NoSuchItemException();
        } else if (!item.isSellable()) {
          throw new ItemNotSellableException();
        } else if (transactionService.getMinimumBidAmount(item) > user.getGreenBayDollars()) {
          throw new InsufficientFundsException();
        } else if (bid.getAmount() < transactionService.getMinimumBidAmount(item)) {
          throw new InSufficientBidAmountException();
        } else if (bid.getAmount() < item.getPurchasePrice()){
          transactionService.placeBid(item, bid);
          return ResponseEntity.status(HttpStatus.OK).body(itemService.createItemPojo(item));
        }else {
          transactionService.buyItem(item, user);
          return ResponseEntity.status(HttpStatus.OK).body(itemService.createItemPojo(item));
        }
      }
    }
  }
}
