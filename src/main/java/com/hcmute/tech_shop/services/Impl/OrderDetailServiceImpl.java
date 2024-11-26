package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.OrderDetail;
import com.hcmute.tech_shop.repositories.OrderDetailRepository;
import com.hcmute.tech_shop.services.interfaces.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public boolean createOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
        return true;
    }

    @Override
    public List<OrderDetail> findByProductId(Long productId){
        return orderDetailRepository.findByProductId(productId);
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public <S extends OrderDetail> S save(S entity) {
        return orderDetailRepository.save(entity);
    }

    @Override
    public Optional<OrderDetail> findById(Long aLong) {
        return orderDetailRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return orderDetailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return orderDetailRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        orderDetailRepository.deleteById(aLong);
    }

    @Override
    public void delete(OrderDetail entity) {
        orderDetailRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        orderDetailRepository.deleteAll();
    }

    @Override
    public List<OrderDetail> findAll(Sort sort) {
        return orderDetailRepository.findAll(sort);
    }
}
