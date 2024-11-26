package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.repositories.OrderRepository;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final IProductService productService;
    private final ICartDetailService cartDetailService;

    @Override
    public void createOrder(User user, BigDecimal totalPrice, Voucher voucher, Payment payment, Address address,
                               Long cartId,
                               List<CartDetailResponse> cartDetailList) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setActive(true);
        order.setUser(user);
        order.setVoucher(voucher);
        order.setPayment(payment);
        order.setAddress(address);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetailResponse cartDetailResponse : cartDetailList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productService.findByName(cartDetailResponse.getProductName()));
            orderDetail.setQuantity(cartDetailResponse.getQuantity());
            orderDetail.setTotalPrice(cartDetailResponse.getTotalPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        cartDetailService.deleteAll(cartId);
    }

    @Override
    public void createOrder(User user, BigDecimal totalPrice, Payment payment, Address address,
                               Long cartId,
                               List<CartDetailResponse> cartDetailList) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setActive(true);
        order.setUser(user);
        order.setPayment(payment);
        order.setAddress(address);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetailResponse cartDetailResponse : cartDetailList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productService.findByName(cartDetailResponse.getProductName()));
            orderDetail.setQuantity(cartDetailResponse.getQuantity());
            orderDetail.setTotalPrice(cartDetailResponse.getTotalPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        cartDetailService.deleteAll(cartId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }

    @Override
    public Optional<Order> findById(Long aLong) {
        return orderRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return orderRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        orderRepository.deleteById(aLong);
    }

    @Override
    public List<Order> findAll(Sort sort) {
        return orderRepository.findAll(sort);
    }
}
