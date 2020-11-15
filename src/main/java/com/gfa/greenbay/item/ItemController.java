package com.gfa.greenbay.item;

import com.gfa.greenbay.exception.InvalidPageNumberException;
import com.gfa.greenbay.exception.InvalidPriceException;
import com.gfa.greenbay.exception.InvalidUrlException;
import com.gfa.greenbay.exception.MissingParametersException;
import com.gfa.greenbay.exception.NoSuchItemException;
import com.gfa.greenbay.exception.NoTokenException;
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
    } else if (newItemDto.getName() == null || newItemDto.getDescription() == null ||
        newItemDto.getPhotoUrl() == null || newItemDto.getStartingPrice() == null ||
        newItemDto.getPurchasePrice() == null) {
      throw new MissingParametersException(newItemDto);
    } else if (newItemDto.getStartingPrice() - newItemDto.getStartingPrice().intValue() != 0 ||
        newItemDto.getStartingPrice() <= 0 ||
        newItemDto.getPurchasePrice() - newItemDto.getPurchasePrice().intValue() != 0 ||
        newItemDto.getPurchasePrice() <= 0) {
      throw new InvalidPriceException();
    } else if (!urlValidator.isValid(newItemDto.getPhotoUrl())) {
      throw new InvalidUrlException();
    } else {
      User user = userService.
          findByUsername(
              jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromRequest(request)));
      Item newItem =
          new Item(newItemDto.getName(), newItemDto.getDescription(), newItemDto.getPhotoUrl(),
              newItemDto.getStartingPrice().intValue(), newItemDto.getPurchasePrice().intValue(),
              user);
      NewlyCreatedItemDto responseItem =
          new NewlyCreatedItemDto(newItemDto.getName(), newItemDto.getDescription(),
              newItemDto.getPhotoUrl(),
              newItemDto.getStartingPrice().intValue(), newItemDto.getPurchasePrice().intValue(),
              user.getUsername());

      itemService.save(newItem);
      return ResponseEntity.status(HttpStatus.OK).body(responseItem);
    }
  }

  @GetMapping("/items")
  public ResponseEntity<?> list(@RequestParam(required = false) Double n) {
    if (n == null) {
      return ResponseEntity.status(HttpStatus.OK).body(itemService.listSellableItemDtos(0));
    } else if (n - n.intValue() != 0 || n < 1) {
      throw new InvalidPageNumberException();
    } else {
      return ResponseEntity.status(HttpStatus.OK)
          .body(itemService.listSellableItemDtos(n.intValue()));
    }
  }

  @GetMapping("/item/{itemId}")
  public ResponseEntity<?> sellableItem(@PathVariable(required = false) Long itemId) {
    Item item = itemService.findById(itemId);
    if (item == null) {
      throw new NoSuchItemException();
    } else {
      SellabelItemDto sellabelItemDto = new SellabelItemDto(item.getName(), item.getDescription(),
          item.getPhotoUrl(), item.getBids(), item.getPurchasePrice(),
          item.getSeller().getUsername(),
          item.isSellable() ? "Not yet sold" : "Sold",
          item.isSellable() ? null : item.getBuyer().getUsername());
      return ResponseEntity.status(HttpStatus.OK).body(sellabelItemDto);
    }
  }

}
