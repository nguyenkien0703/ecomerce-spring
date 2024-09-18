package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.dtos.request.OrderDetailDTO;
import com.ecommerce.shopapp.entity.Order;
import com.ecommerce.shopapp.entity.OrderDetail;
import com.ecommerce.shopapp.entity.Product;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.repositories.OrderDetailRepository;
import com.ecommerce.shopapp.repositories.OrderRepository;
import com.ecommerce.shopapp.repositories.ProductRepository;
import com.ecommerce.shopapp.services.IOrderDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        //tìm xem orderId có tồn tại ko
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : "+orderDetailDTO.getOrderId()));
        // Tìm Product theo id
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        //lưu vào db
        return orderDetailRepository.save(orderDetail);
    }











    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find OrderDetail with id: "+id));
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO)
            throws DataNotFoundException {
        //tìm xem order detail có tồn tại ko đã
        OrderDetail existingOrderDetail = (OrderDetail) orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: "+id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: "+id));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
