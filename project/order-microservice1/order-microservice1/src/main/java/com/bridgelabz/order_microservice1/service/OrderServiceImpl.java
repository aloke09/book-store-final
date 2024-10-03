package com.bridgelabz.order_microservice1.service;

import com.bridgelabz.order_microservice1.DTO.BookDto;
import com.bridgelabz.order_microservice1.DTO.OrderDto;
import com.bridgelabz.order_microservice1.external.Cart;
import com.bridgelabz.order_microservice1.external.User;
import com.bridgelabz.order_microservice1.mapper.OrderModelToOrderDto;
import com.bridgelabz.order_microservice1.model.OrderModel;
import com.bridgelabz.order_microservice1.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl
{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderModelToOrderDto orderModelToOrderDto;

    public OrderDto orderPlaced(OrderModel orderModel)
    {
        OrderModel save = orderRepository.save(orderModel);
        OrderDto dto=new OrderDto();
        dto.setUserId(save.getUserId());
        dto.setBookId(save.getBookId());
        dto.setQty(save.getQty());
        dto.setAddress(save.getAddress());
        return dto;
    }

    @Transactional
    public List<OrderDto> listOfOrderPlaced(List<Cart> listOfCartItems,OrderModel orderModel)
    {
        List<OrderDto> listOfConfirmedOrder=new ArrayList<>();
        for(Cart c:listOfCartItems)
        {
            orderModel.setOrderDate(LocalDate.now());
            orderModel.setBookId(c.getBookId());
            orderModel.setQty(c.getQuantity());
            orderModel.setCancel(true);
            orderModel.setUserId(c.getUserId());
            orderModel.setPrice(c.getTotalPrice());
            orderRepository.save(orderModel);

            OrderDto dto=new OrderDto();
            dto.setAddress(orderModel.getAddress());
            dto.setQty(orderModel.getQty());
            dto.setBookId(orderModel.getBookId());
            dto.setUserId(orderModel.getUserId());
            listOfConfirmedOrder.add(dto);
        }
        return listOfConfirmedOrder;
    }

    public BookDto orderCanceled(User user, Long orderId) {
        List<OrderModel> listOfOrderByUser = orderRepository.findByUserId(user.getId());

        // Check if user has any orders
        if (listOfOrderByUser.isEmpty()) {
            System.out.println("No orders found for user: " + user.getId());
            return null;
        }

        // Log the orders for debugging
        System.out.println("Orders found for user: " + user.getId());

        for (OrderModel om : listOfOrderByUser) {
            if (Objects.equals(om.getOrderId(), orderId)) {
                // Order found, proceed with cancellation
                orderRepository.deleteById(orderId);
                Long bookId = om.getBookId();
                Long qty = om.getQty();

                // Build BookDto and return
                BookDto bdto = new BookDto();
                bdto.setBookId(bookId);
                bdto.setQty(qty);
                return bdto;
            }
        }

        // If no matching order is found, return null
        System.out.println("No matching order found for orderId: " + orderId);
        return null;
    }


    public List<OrderDto> getListOfAllOrders()//for admin
    {
        List<OrderModel> allOrderDetails = orderRepository.findAll();
        if (allOrderDetails.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrderDto> orderDtos = orderModelToOrderDto.orderDetailsMappedToOrderDto(allOrderDetails);
        return orderDtos;
    }

    public List<OrderDto> getListOfAllOrdersForUser(Long userId)//for user
    {
        List<OrderModel> allByUserId = orderRepository.findAllByUserId(userId);
        if (allByUserId.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrderDto> orderDtos = orderModelToOrderDto.orderDetailsMappedToOrderDto(allByUserId);
        return orderDtos;
    }
}
