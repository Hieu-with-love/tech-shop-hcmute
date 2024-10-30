package com.hcmute.tech_shop.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/vouchers")
public class VoucherController {

    @GetMapping("")
    public String index() {
        return "admin/vouchers/voucherlist";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/vouchers/newvoucher";
    }

}
