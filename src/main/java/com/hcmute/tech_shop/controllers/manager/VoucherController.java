package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.dtos.requests.VoucherRequest;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller("managerVoucherController")
@RequestMapping("/manager/vouchers")
public class VoucherController {

    @Autowired
    private IVoucherService voucherService;

    @GetMapping("")
    public String index(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);

        return "manager/vouchers/voucherlist";
    }

    @GetMapping("/save")
    public String addOrEdit(Model model ) {
        model.addAttribute("voucher", new VoucherRequest());
        return "manager/vouchers/newvoucher";
    }

    @GetMapping("/save/{id}")
    public String showEditVoucherForm(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            Optional<Voucher> voucher = voucherService.findById(id);
            if (voucher.isPresent()) {
                Voucher foundVoucher = voucher.get();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = foundVoucher.getExpiredDate().format(formatter);
                model.addAttribute("voucher", foundVoucher);
                model.addAttribute("expiredDateFormatted", formattedDate);
            }
        }
        return "manager/vouchers/newvoucher";
    }

    @PostMapping("/savevoucher")
    public String addOrEditVoucher(@Valid @ModelAttribute("voucher") VoucherRequest voucherRequest,
                                   BindingResult bindingResult,
                                   Model model
                                   ) {

        if (bindingResult.hasErrors()) {
            return "manager/vouchers/newvoucher";
        }

        Optional<Voucher> existingVoucher = voucherService.findByName(voucherRequest.getName());
        if (existingVoucher.isPresent()) {
            // Neu doi ten # hoac them moi thi check
            if (voucherRequest.getId() == null || !existingVoucher.get().getId().equals(voucherRequest.getId())) {
                model.addAttribute("error", "The voucher code already exists!");
                return "manager/vouchers/newvoucher";
            }
        }
        voucherService.save(voucherRequest);
        return "redirect:/manager/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        voucherService.deleteById(id);
        return "redirect:/manager/vouchers";
    }

}
