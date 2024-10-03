package com.bridgelabz.order_microservice1.client;

import com.bridgelabz.order_microservice1.external.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CARTMS2")
public interface CartServiceClient
{
    @GetMapping("cart/CartItemsForUser/userId")//normal user
    List<Cart> getAllCartItemsForA_User(@RequestHeader("Authorization") String authHeader);

    @GetMapping("cart/removeAllCartItemsForUser/userId")//normal user
    ResponseEntity<String> deleteAllByUserId(@RequestHeader("Authorization") String authHeader);

    @PutMapping("cart/removeFromCart/{id}")
    String removeFromCartAddToBook(@PathVariable Long id, @RequestPart Long qty);
}
