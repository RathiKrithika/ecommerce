package com.shop.controller;

import com.shop.dto.AddToCart;
import com.shop.dto.PlaceOrder;
import com.shop.service.OrderAndPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shop")
public class OrderAndPurchaseController {

    @Autowired
    private OrderAndPurchaseService service;

    @GetMapping("/viewAllProducts")
    public ResponseEntity getAllProducts(){
       return new ResponseEntity<>( service.getAllProducts(), HttpStatus.OK);
    }

   @PostMapping("/addToCart/{userId}")
    public ResponseEntity addItemsToCart(@RequestBody List<AddToCart> c,@PathVariable("userId") Long userId){
     return new ResponseEntity(service.addItemToCart(c, userId),HttpStatus.CREATED) ;
 }

  @GetMapping("/viewCart/{userId}")
    public ResponseEntity viewMyCart(@PathVariable("userId") Long userId){
      return new ResponseEntity(service.viewCart(userId), HttpStatus.OK) ;
  }

  @PostMapping("/purchase")
  public ResponseEntity placeMyOrder(@RequestBody PlaceOrder p){
         return new ResponseEntity( service.placeMyOrderAndPay(p), HttpStatus.OK) ;
  }

}
