package org.qsheker.orderservice.api;

import org.qsheker.orderservice.service.OrderService;
import org.qsheker.orderservice.web.dto.OrderRequest;
import org.qsheker.orderservice.web.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrder(@PathVariable Long id){
        return orderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.create(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAllOrder(){
        return orderService.findAllOrder();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse update(@PathVariable Long id, @RequestBody OrderRequest orderRequest){
        return orderService.update(id, orderRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        orderService.delete(id);
    }
}
