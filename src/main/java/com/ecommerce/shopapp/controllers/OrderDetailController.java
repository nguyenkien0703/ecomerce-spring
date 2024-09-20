package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.OrderDetailDTO;
import com.ecommerce.shopapp.entity.OrderDetail;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.responses.OrderDetailResponse;
import com.ecommerce.shopapp.services.orderdetail.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;


    //Thêm mới 1 order detail
    @PostMapping("")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
//        OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(newOrderDetail);
//        return ResponseEntity.ok().body(
//                ResponseObject.builder()
//                        .message("Create order detail successfully")
//                        .status(HttpStatus.CREATED)
//                        .data(orderDetailResponse)
//                        .build()
//        );
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(newOrderDetail) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
//        OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(orderDetail);
//        return ResponseEntity.ok().body(
//                ResponseObject.builder()
//                        .message("Get order detail successfully")
//                        .status(HttpStatus.OK)
//                        .data(orderDetailResponse)
//                        .build()
//        );
        return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail)   );
    }
    //lấy ra danh sách các order_details của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();


//        return ResponseEntity.ok().body(
//                ResponseObject.builder()
//                        .message("Get order details by orderId successfully")
//                        .status(HttpStatus.OK)
//                        .data(orderDetailResponses)
//                        .build()
//        );
        return ResponseEntity.ok(orderDetailResponses);
    }




    @PutMapping("/{id}")
//    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO) throws  Exception {
        OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
//        return ResponseEntity.ok().body(ResponseObject
//                .builder()
//                .data(orderDetail)
//                .message("Update order detail successfully")
//                .status(HttpStatus.OK)
//                .build());
        return ResponseEntity.ok().body(orderDetail);
    }
    @DeleteMapping("/{id}")
//    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteOrderDetail(
            @Valid @PathVariable("id") Long id) {
        orderDetailService.deleteById(id);
//        return ResponseEntity.ok()
//                .body(ResponseObject.builder()
//                        .message(localizationUtils
//                                .getLocalizedMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY))
//                        .build());
        return ResponseEntity.ok(id);
    }





}
