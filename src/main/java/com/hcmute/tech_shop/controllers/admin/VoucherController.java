package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.VoucherRequest;
import com.hcmute.tech_shop.entities.Voucher;
import com.hcmute.tech_shop.services.interfaces.IVoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Optional<Voucher> existingVoucher = voucherService.findByName(voucherRequest.getName());
        if (existingVoucher.isPresent()) {
            // Neu doi ten # hoac them moi thi check
            if (voucherRequest.getId() == null || !existingVoucher.get().getId().equals(voucherRequest.getId())) {
                model.addAttribute("error", "The voucher code already exists!");
                return "admin/vouchers/newvoucher";
            }
        }
        voucherService.save(voucherRequest);
        return "redirect:/admin/vouchers";
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable(value = "id") Long id) {
        Map<String, String> response = new HashMap<>();
        if (voucherService.deleteById(id)) {
            response.put("status", "success");
            response.put("message", "Voucher set to 0.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to delete the voucher.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
