package com.gfa.greenbay.item;

import com.gfa.greenbay.exception.InvalidPageNumberException;
import com.gfa.greenbay.exception.InvalidPriceException;
import com.gfa.greenbay.exception.InvalidUrlException;
import com.gfa.greenbay.exception.MissingParametersException;
import com.gfa.greenbay.exception.NoSuchItemException;
import com.gfa.greenbay.exception.NoTokenException;
import com.gfa.greenbay.exception.TokenExpiredException;

import com.gfa.greenbay.user.User;
import com.gfa.greenbay.user.UserService;
import com.gfa.greenbay.util.JwtTokenUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.validator.routines.UrlValidator;

@RestController
@RequestMapping("/greenbay")
public class ItemController {

  private ItemService itemService;
  private JwtTokenUtil jwtTokenUtil;
  private UserService userService;

  @Autowired
  public ItemController(ItemService itemService, JwtTokenUtil jwtTokenUtil,
                        UserService userService) {
    this.itemService = itemService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userService = userService;
  }

  @PostMapping("/item")
  public ResponseEntity<?> create(@RequestBody NewItemDto newItemDto, HttpServletRequest request) {

    UrlValidator urlValidator = new UrlValidator();

    if (request.getHeader("jwt") == null) {
      throw new NoTokenException();
    } else if (jwtTokenUtil.isTokenExpired(jwtTokenUtil.getTokenFromRequest(request))) {
      throw new TokenExpiredException();
    } else if (itemService.anyParameterMissing(newItemDto)) {
      throw new MissingParametersException(newItemDto);
    } else if (itemService.anyWronglyProvidedPriceParameter(newItemDto)) {
      throw new InvalidPriceException();
    } else if (!urlValidator.isValid(newItemDto.getPhotoUrl())) {
      throw new InvalidUrlException();
    } else {
      User user = userService.findByUsername(
          jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromRequest(request)));
      Item newItem = itemService.createNewItemFromDto(newItemDto, user);
      NewlyCreatedItemDao responseItem =
          itemService.createReturnDtoFromRequestDto(newItemDto, user);
      itemService.save(newItem);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseItem);
    }
  }

  @GetMapping("/items")
  public ResponseEntity<?> list(@RequestParam(required = false) Double n,
                                HttpServletRequest request) {
    if (request.getHeader("jwt") == null) {
      throw new NoTokenException();
    } else if (jwtTokenUtil.isTokenExpired(jwtTokenUtil.getTokenFromRequest(request))) {
      throw new TokenExpiredException();
    } else if (n == null) {
      return ResponseEntity.status(HttpStatus.OK).body(itemService.listListedSellableItemDaos(0));
    } else if (n - n.intValue() != 0 || n < 1) {
      throw new InvalidPageNumberException();
    } else {
      return ResponseEntity.status(HttpStatus.OK)
          .body(itemService.listListedSellableItemDaos(n.intValue()));
    }
  }

  @GetMapping("/item/{itemId}")
  public ResponseEntity<?> sellableItem(@PathVariable Long itemId,
                                        HttpServletRequest request) {
    Item item = itemService.findById(itemId);
    if (request.getHeader("jwt") == null) {
      throw new NoTokenException();
    } else if (jwtTokenUtil.isTokenExpired(jwtTokenUtil.getTokenFromRequest(request))) {
      throw new TokenExpiredException();
    } else if (item == null) {
      throw new NoSuchItemException();
    } else {
      ItemDao itemDao = itemService.createItemDao(item);
      return ResponseEntity.status(HttpStatus.OK).body(itemDao);
    }
  }

}
