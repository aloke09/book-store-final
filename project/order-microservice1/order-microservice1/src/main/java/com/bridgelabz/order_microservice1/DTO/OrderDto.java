package com.bridgelabz.order_microservice1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto
{
    private Long userId;
    private Long bookId;
    private Long qty;
    private String address;

}
