package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.VoucherRequest;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.repositories.VoucherRepository;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements IVoucherService {
    @Autowired
    VoucherRepository voucherRepository;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher save(VoucherRequest voucherDTO) {
        Voucher voucherEntity = new Voucher();
        BeanUtils.copyProperties(voucherDTO, voucherEntity);

        return voucherRepository.save(voucherEntity);
    }

    @Override
    public void deleteById(Long id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Optional<Voucher> findByName(String name) {
        return voucherRepository.findByName(name);
    }
}
