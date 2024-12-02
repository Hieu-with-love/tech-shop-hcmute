package com.hcmute.tech_shop.controllers.shipper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipper/dashboard")
public class ShipperDashboard {

    @GetMapping
    public String dashboard(Model model){

        return "shipper/dashboard";
    }
}
