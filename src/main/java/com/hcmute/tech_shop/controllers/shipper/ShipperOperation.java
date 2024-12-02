package com.hcmute.tech_shop.controllers.shipper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipper")
public class ShipperOperation {

    @GetMapping("/list-need-ship")
    public String showListShipping(){

        return "shipper/listNeedShipOrder";
    }

    @GetMapping("/list-receive")
    public String showNeedShipping(){

        return "shipper/needShippingOrder";
    }

    @GetMapping("/shipper-list-history-ship")
    public String showListHistShipOrder(){

        return "shipper/listHisShipOrder";
    }

    @GetMapping("/shipper-statistical")
    public String showStatistical(){

        return "shipper/statistical";
    }

    @GetMapping("/shipper-info")
    public String showInfo(){

        return "shipper/infoShipper";
    }
}
