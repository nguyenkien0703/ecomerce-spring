package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.OrderDTO;
import com.ecommerce.shopapp.entity.Order;
import com.ecommerce.shopapp.services.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult
    ){
        try{
            if(bindingResult.hasErrors()) {
                List<String> errorMessage =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/user/{user_id}") // Thêm biến đường dẫn "user_id"
    //GET http://localhost:8088/api/v1/orders/user/4
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        List<Order> orders = orderService.findByUserId(userId);

        return ResponseEntity.ok(orders);
    }


    //GET http://localhost:8088/api/v1/orders/2
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        Order existingOrder = orderService.getOrderById(orderId);
//        OrderResponse orderResponse = OrderResponse.fromOrder(existingOrder);
//        return ResponseEntity.ok(new ResponseObject(
//                "Get order successfully",
//                HttpStatus.OK,
//                orderResponse
//        ));
        return ResponseEntity.ok(existingOrder);

    }


    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //PUT http://localhost:8088/api/v1/orders/2
    //công việc của admin
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO) throws Exception {

        Order order = orderService.updateOrder(id, orderDTO);
//        return ResponseEntity.ok(new ResponseObject("Update order successfully", HttpStatus.OK, order));
        return ResponseEntity.ok(order);
    }



    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        //xóa mềm => cập nhật trường active = false
        orderService.deleteOrder(id);
//        String message = localizationUtils.getLocalizedMessage(
//                MessageKeys.DELETE_ORDER_SUCCESSFULLY, id);
//        return ResponseEntity.ok(
//                ResponseObject.builder()
//                        .message(message)
//                        .build()
//        );
        return ResponseEntity.ok("deleete oke ");
    }


}
