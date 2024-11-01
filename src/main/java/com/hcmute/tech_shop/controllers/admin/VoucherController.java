package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.VoucherRequest;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/vouchers")
public class VoucherController {

    @Autowired
    private IVoucherService voucherService;

    @GetMapping("")
    public String index(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);

        return "admin/vouchers/voucherlist";
    }

    @GetMapping("/save")
    public String addOrEdit(Model model ) {
        model.addAttribute("voucher", new VoucherRequest());
        return "admin/vouchers/newvoucher";
    }

    @GetMapping("/save/{id}")
    public String showEditVoucherForm(@PathVariable("id") Long id, Model model) {
         if(id != null) {
             Optional<Voucher> voucher = voucherService.findById(id);
             model.addAttribute("voucher", voucher.get());
         }
         return "admin/vouchers/newvoucher";
    }

    @PostMapping("/savevoucher")
    public String addOrEditVoucher(@Valid @ModelAttribute("voucher") VoucherRequest voucherRequest,
                                   BindingResult bindingResult,
                                   Model model
                                   ) {

        if (bindingResult.hasErrors()) {
            return "admin/vouchers/newvoucher";
        }

        if(voucherService.findByName(voucherRequest.getName()).isPresent()) {
            model.addAttribute("error", "The voucher code already exists!");
            return "admin/vouchers/newvoucher";

        }
        voucherService.save(voucherRequest);
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        voucherService.deleteById(id);
        return "redirect:/admin/vouchers";
    }

}
