package com.bridgelabz.order_microservice1.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart
{
    private Long cartId;

    private Long userId;

    private Long bookId;

    private Long quantity;

    private Long totalPrice;
}
