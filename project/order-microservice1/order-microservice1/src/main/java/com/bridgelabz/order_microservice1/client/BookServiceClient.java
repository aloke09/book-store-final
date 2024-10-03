package com.bridgelabz.order_microservice1.client;

import com.bridgelabz.order_microservice1.external.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "BOOK-MICROSERVICE1")
public interface BookServiceClient
{
    @GetMapping("books/{id}")
    Book aParticularBook(@PathVariable Long id);

    @PutMapping("books/minusAddToCartQuantity/{id}")
    String minusAddToCartQuantity(@PathVariable Long id, @RequestPart Long qty);

    @PutMapping("books/removeFromCart/{id}")
    String removeFromCartAddToBook(@PathVariable Long id, @RequestPart Long qty);

    @PutMapping("/canceled/{bookId}/{qty}")
    ResponseEntity<String> orderedCanceledAddBackToBook(@PathVariable long bookId,
                                                               @PathVariable long qty,
                                                               @RequestHeader("Authorization") String authHeader);


}