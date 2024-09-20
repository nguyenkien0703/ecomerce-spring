package com.ecommerce.shopapp.services;

import com.ecommerce.shopapp.dtos.request.OrderDTO;
import com.ecommerce.shopapp.entity.Order;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {

    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrderById(Long orderId);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long orderId);
    List<OrderResponse> findByUserId(Long userId);
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);



}
