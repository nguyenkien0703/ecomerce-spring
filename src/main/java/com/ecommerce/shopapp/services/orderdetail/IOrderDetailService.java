package com.ecommerce.shopapp.services.orderdetail;

import com.ecommerce.shopapp.dtos.request.OrderDetailDTO;
import com.ecommerce.shopapp.entity.OrderDetail;
import com.ecommerce.shopapp.exception.DataNotFoundException;

import java.util.List;
public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);


}
