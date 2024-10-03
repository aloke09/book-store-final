package com.bridgelabz.order_microservice1.controller;

import com.bridgelabz.order_microservice1.DTO.BookDto;
import com.bridgelabz.order_microservice1.DTO.OrderDto;
import com.bridgelabz.order_microservice1.external.Book;
import com.bridgelabz.order_microservice1.external.Cart;
import com.bridgelabz.order_microservice1.external.User;
import com.bridgelabz.order_microservice1.model.OrderModel;
import com.bridgelabz.order_microservice1.service.OrderServiceImpl;
import com.bridgelabz.order_microservice1.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    private Utility utility;

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/placed")
    public ResponseEntity<?> orderPlaced(@RequestHeader("Authorization") String authHeader,
                                                     @RequestBody OrderModel orderModel)//only address and cancel by default true
    {
        User authenticatedUser = utility.getAuthenticatedUser(authHeader);
        if(authenticatedUser!=null)
        {
            if(orderModel.getCancel())
            {
                List<Cart> allCartItemsForUser = utility.getAllCartItemsForUser(authHeader);
                List<OrderDto> orderDtos = orderService.listOfOrderPlaced(allCartItemsForUser, orderModel);
//                String s = utility.removedItemFromCartOnceOrderPlaced(authHeader);
//                System.out.println(s);
                return new ResponseEntity<>(orderDtos, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("order canceled", HttpStatus.NOT_FOUND);
            }
        }
        else
        {
            return new ResponseEntity<>("unauthorised", HttpStatus.UNAUTHORIZED);
        }
    }

//    @DeleteMapping("/canceled/{OrderId}")
//    public ResponseEntity<?> orderCanceled(@RequestHeader("Authorization") String authHeader,
//                                           @PathVariable Long OrderId)
//    {
//        User authenticatedUser = utility.getAuthenticatedUser(authHeader);
//        if(authenticatedUser!=null)
//        {
//            BookDto bookDto = orderService.orderCanceled(authenticatedUser,OrderId);
//            Book bookMS = utility.getBookId(bookDto.getBookId());
//
//            String s1 = utility.orderCanceledAddedBackToBook(bookDto.getBookId(), bookDto.getQty(), authHeader);
//
//            return new ResponseEntity<>("order canceled",HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>("unauthorised",HttpStatus.UNAUTHORIZED);
//        }
//    }

    @DeleteMapping("/canceled/{OrderId}")
    public ResponseEntity<?> orderCanceled(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long OrderId) {
        User authenticatedUser = utility.getAuthenticatedUser(authHeader);

        if (authenticatedUser != null) {
            BookDto bookDto = orderService.orderCanceled(authenticatedUser, OrderId);

            if (bookDto == null) {
                return new ResponseEntity<>("Order not found or unable to cancel", HttpStatus.NOT_FOUND);
            }

            // Get book details from the book microservice
            Book bookMS = utility.getBookId(bookDto.getBookId());

            // Call the method to add back to stock
            String s1 = utility.orderCanceledAddedBackToBook(bookDto.getBookId(), bookDto.getQty(), authHeader);

            return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/allDetails")//admin
    public ResponseEntity<List<OrderDto>> getAllOrderDetails(@RequestHeader("Authorization") String authHeader)
    {
        User authenticatedUser = utility.getAuthenticatedUser(authHeader);
        if(authenticatedUser!=null && authenticatedUser.getRole().equals("admin"))
        {
            List<OrderDto> listOfAllOrders = orderService.getListOfAllOrders();
            return new ResponseEntity<>(listOfAllOrders,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/allDetailsForUser")//user
    public ResponseEntity<List<OrderDto>> getAllOrderDetailsForUser(@RequestHeader("Authorization") String authHeader)
    {
        User authenticatedUser = utility.getAuthenticatedUser(authHeader);
        if(authenticatedUser!=null)
        {
            List<OrderDto> listOfAllOrdersForUser = orderService.getListOfAllOrdersForUser(authenticatedUser.getId());
            return new ResponseEntity<>(listOfAllOrdersForUser,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }




}
