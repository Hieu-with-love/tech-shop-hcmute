package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.OrderReponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.repositories.OrderRepository;
import com.hcmute.tech_shop.services.interfaces.ICartDetailService;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import com.hcmute.tech_shop.utils.Constant;
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
    private final IVoucherService voucherService;

    @Override
    public List<Order> findByUsername(String username) {
        return orderRepository.findByUser_Username(username);
    }

    @Override
    public void orderPending(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
    }

    @Override
    public void orderCancelled(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void orderDelivered(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @Override
    public void orderShipping(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.SHIPPING);
        orderRepository.save(order);
    }

    @Override
    public void createOrder(User user, BigDecimal totalPrice, Voucher voucher, Payment payment, Address address,
                               Long cartId,
                               List<CartDetailResponse> cartDetailList) {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setActive(true);
        order.setUser(user);
        order.setVoucher(voucher);
        // Decrease voucher quantity
        voucherService.decreaseQuantity(voucher.getId());
        order.setPayment(payment);
        order.setAddress(address);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetailResponse cartDetailResponse : cartDetailList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(productService.findByName(cartDetailResponse.getProductName()));
            orderDetail.setQuantity(cartDetailResponse.getQuantity());
            // Decrease product quantity
            productService.decreaseStockQuantity(orderDetail.getProduct().getId(), cartDetailResponse.getQuantity());
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public List<OrderReponse> findOrderByShipperNameAndStatus(Long shipperId, OrderStatus orderStatus) {
        List<Order> orderList = orderRepository.getAllByShipperIdAndStatus(shipperId, orderStatus);
        List<OrderReponse> orderReponseList = new ArrayList<>();
        for (Order order : orderList) {
            OrderReponse orderReponse = new OrderReponse();
            orderReponse.setOrderId(order.getId());
            orderReponse.setShipper(order.getShipper());
            orderReponse.setStatus(order.getStatus());
            orderReponse.setCustomerName(order.getUser().getLastName()+order.getUser().getFirstName());
            orderReponse.setPaymentName(order.getPayment().getName());
            orderReponse.setTotalPrice(Constant.formatter.format(order.getTotalPrice()));
            orderReponse.setShippingAddress(order.getAddress());
            orderReponseList.add(orderReponse);
        }
        return orderReponseList;
    }

    @Override
    public List<OrderReponse> findAllByShipperId(Long shipperId) {
        List<Order> orderList = orderRepository.getAllByShipperId(shipperId);
        List<OrderReponse> orderReponseList = new ArrayList<>();
        for (Order order : orderList) {
            OrderReponse orderReponse = new OrderReponse();
            orderReponse.setOrderId(order.getId());
            orderReponse.setShipper(order.getShipper());
            orderReponse.setStatus(order.getStatus());
            orderReponse.setCustomerName(order.getUser().getLastName()+" "+order.getUser().getFirstName());
            orderReponse.setPaymentName(order.getPayment().getName());
            orderReponse.setTotalPrice(Constant.formatter.format(order.getTotalPrice()));
            orderReponse.setShippingAddress(order.getAddress());
            orderReponseList.add(orderReponse);
        }
        return orderReponseList;
    }

    @Override
    public OrderReponse findByOrderId(Long id){
        Order order = orderRepository.findById(id).get();
        OrderReponse orderReponse = new OrderReponse();
        orderReponse.setOrderId(order.getId());
        orderReponse.setShipper(order.getShipper());
        orderReponse.setStatus(order.getStatus());
        orderReponse.setCustomerName(order.getUser().getLastName()+" "+order.getUser().getFirstName());
        orderReponse.setPaymentName(order.getPayment().getName());
        orderReponse.setTotalPrice(Constant.formatter.format(order.getTotalPrice()));
        orderReponse.setShippingAddress(order.getAddress());
        return orderReponse;
    }

    @Override
    public List<Order> ordersByYearAndMonthForShipper(int year, int month, Long shipperId) {
        return orderRepository.ordersByYearAndMonthForShipper(year, month, shipperId);
    }

    @Override
    public List<Order> totalPriceByYearAndMonthForShipper(int year, int month, Long shipperId) {
        return orderRepository.totalPriceByYearAndMonthForShipper(year, month, shipperId);
    }

    public String totalPriceByYearAndMonthByShipper(int year, int month, Long shipperId) {
        List<Order> orderList = this.totalPriceByYearAndMonthForShipper(year, month, shipperId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Order order : orderList) {
            totalPrice = totalPrice.add(order.getTotalPrice());
        }
        return Constant.formatter.format(totalPrice);
    }
}
