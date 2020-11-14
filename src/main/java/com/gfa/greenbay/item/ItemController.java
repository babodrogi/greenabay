package com.gfa.greenbay.item;

import com.gfa.greenbay.exception.InvalidPriceException;
import com.gfa.greenbay.exception.InvalidUrlException;
import com.gfa.greenbay.exception.MissingParametersException;
import com.gfa.greenbay.exception.NoTokenException;
import com.gfa.greenbay.user.User;
import com.gfa.greenbay.user.UserService;
import com.gfa.greenbay.util.JwtTokenUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    }
    else if (newItemDto.getName() == null || newItemDto.getDescription() == null ||
        newItemDto.getPhotoUrl() == null || newItemDto.getStartingPrice() == null ||
        newItemDto.getPurchasePrice() == null) {
      throw new MissingParametersException(newItemDto);
    }
    else if (newItemDto.getStartingPrice() - newItemDto.getStartingPrice().intValue() != 0 ||
        newItemDto.getStartingPrice() <= 0 ||
        newItemDto.getPurchasePrice() - newItemDto.getPurchasePrice().intValue() != 0 ||
        newItemDto.getPurchasePrice() <= 0) {
      throw new InvalidPriceException();
    }
    else if (!urlValidator.isValid(newItemDto.getPhotoUrl())) {
      throw new InvalidUrlException();
    }
    else {
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

//  @GetMapping("/item")
//  public ResponseEntity<?> list(){
//
//  }
}
