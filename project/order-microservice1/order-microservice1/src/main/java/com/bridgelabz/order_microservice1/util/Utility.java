package com.bridgelabz.order_microservice1.util;

import com.bridgelabz.order_microservice1.client.BookServiceClient;
import com.bridgelabz.order_microservice1.client.CartServiceClient;
import com.bridgelabz.order_microservice1.client.UserServiceClient;
import com.bridgelabz.order_microservice1.external.Book;
import com.bridgelabz.order_microservice1.external.Cart;
import com.bridgelabz.order_microservice1.external.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Utility
{
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private BookServiceClient bookServiceClient;

    public User getAuthenticatedUser(String authHeader) {
        User user = userServiceClient.getUser(authHeader);
        System.out.println(user);
        if(user!=null)
        {
            return user;
        }
        return null;
    }

    public Book getBookId(Long bookId)
    {
        Book book = bookServiceClient.aParticularBook(bookId);
        return  book;
    }

    public List<Cart> getAllCartItemsForUser(String authHeader)
    {
//        cartServiceClient.getAllCartItemsForA_User(authHeader);

        return (List<Cart>) cartServiceClient.getAllCartItemsForA_User(authHeader);
    }
    public String removedItemFromCartOnceOrderPlaced(String authHeader)
    {
        String s= String.valueOf(cartServiceClient.deleteAllByUserId(authHeader));
        return s;
    }

    public String removeFromCartAddToBook(Long bookId, Long qty)
    {
        String s = cartServiceClient.removeFromCartAddToBook(bookId, qty);
        return s;
    }
    public String orderCanceledAddedBackToBook(Long bookId, Long qty, String authHeader) {
        // Call the Feign client and pass the Authorization header
        ResponseEntity<String> responseEntity = bookServiceClient.orderedCanceledAddBackToBook(bookId, qty,authHeader);

        // Convert the response to string and return
        return String.valueOf(responseEntity.getBody());
    }
}