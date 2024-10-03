package com.bridgelabz.order_microservice1.mapper;

import com.bridgelabz.order_microservice1.DTO.OrderDto;
import com.bridgelabz.order_microservice1.model.OrderModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderModelToOrderDto
{
    public List<OrderDto> orderDetailsMappedToOrderDto(List<OrderModel> allOrderDetails)
    {
        List<OrderDto> allOrderDtoDetails=new ArrayList<>();
        for (OrderModel om: allOrderDetails)
        {
            OrderDto orderDto=new OrderDto();
            orderDto.setUserId(om.getUserId());
            orderDto.setQty(om.getQty());
            orderDto.setAddress(om.getAddress());
            orderDto.setBookId(om.getBookId());
            allOrderDtoDetails.add(orderDto);
        }
        return allOrderDtoDetails;
    }
}
