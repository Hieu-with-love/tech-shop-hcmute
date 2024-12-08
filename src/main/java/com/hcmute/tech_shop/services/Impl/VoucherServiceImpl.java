package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.VoucherRequest;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.repositories.VoucherRepository;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements IVoucherService {
    @Autowired
    VoucherRepository voucherRepository;

    @Override
    public void decreaseQuantity(Long id) {
        Voucher voucher = voucherRepository.findById(id).get();
        voucher.setQuantity(voucher.getQuantity() - 1);
        voucherRepository.save(voucher);
    }

    @Override
    public Voucher findValidVoucher(String name) {
        return voucherRepository.findByNameAndQuantityGreaterThanAndExpiredDateGreaterThan(name, 0, LocalDate.now());
    }

    @Override
    public List<Voucher> findValidVoucher() {
        return voucherRepository.findByQuantityGreaterThanAndExpiredDateGreaterThan(0, LocalDate.now());
    }

    @Override
    public List<Voucher> findByQuantityGreaterThan(int quantityIsGreaterThan) {
        return voucherRepository.findByQuantityGreaterThan(quantityIsGreaterThan);
    }

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
    public boolean deleteById(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isPresent()) {
            voucher.get().setQuantity(0);
            voucherRepository.save(voucher.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Optional<Voucher> findByName(String name) {
        return voucherRepository.findByName(name);
    }

    @Override
    public int getAvailableVoucherCount() {
        LocalDate currentDate = LocalDate.now();
        List<Voucher> availableVouchers = voucherRepository.findByQuantityGreaterThanAndExpiredDateGreaterThan(0, currentDate);
        return availableVouchers.size();
    }
}
