package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.dtos.request.OrderDTO;
import com.ecommerce.shopapp.entity.Order;
import com.ecommerce.shopapp.entity.OrderDetail;
import com.ecommerce.shopapp.entity.OrderStatus;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.repositories.OrderRepository;
import com.ecommerce.shopapp.repositories.UserRepository;
import com.ecommerce.shopapp.services.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService  implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository  userRepository;
    private final ModelMapper modelMapper;





    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //tìm xem user'id có tồn tại ko
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        //convert orderDTO => Order
        //dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());//lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING);
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);//đoạn này nên set sẵn trong sql
        //EAV-Entity-Attribute-Value model
        order.setTotalMoney(orderDTO.getTotalMoney());

        // Tạo danh sách các đối tượng OrderDetail từ cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
//            // Tạo một đối tượng OrderDetail từ CartItemDTO
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order);
//
//            // Lấy thông tin sản phẩm từ cartItemDTO
//            Long productId = cartItemDTO.getProductId();
//            int quantity = cartItemDTO.getQuantity();
//

//            // Tìm thông tin sản phẩm từ cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
//            Product product = productRepository.findById(productId)
//                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));
//
//            // Đặt thông tin cho OrderDetail
//            orderDetail.setProduct(product);
//            orderDetail.setNumberOfProducts(quantity);
//            // Các trường khác của OrderDetail nếu cần
//            orderDetail.setPrice(product.getPrice());
//
//            // Thêm OrderDetail vào danh sách
//            orderDetails.add(orderDetail);
//        }

        //coupon
//        String couponCode = orderDTO.getCouponCode();
//        if (!couponCode.isEmpty()) {
//            Coupon coupon = couponRepository.findByCode(couponCode)
//                    .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));
//
//            if (!coupon.isActive()) {
//                throw new IllegalArgumentException("Coupon is not active");
//            }
//
//            order.setCoupon(coupon);
//        } else {
//            order.setCoupon(null);
//        }
//        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
//        orderDetailRepository.saveAll(orderDetails);
        orderRepository.save(order);
       return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));
        // tao 1 luong bang anh xa de rieng de kiem soat viec anh xa
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));


        // cap nhat cac trg cua don hang tu orderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return order;
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        //no hard-delete, => please soft-delete
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return null;
    }
}
