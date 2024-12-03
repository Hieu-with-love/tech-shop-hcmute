package com.hcmute.tech_shop.controllers.shipper;

import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.services.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/shipper")
@RequiredArgsConstructor
public class ShipperOperation {
    private final IAddressService addressService;

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
    public String showInfo(Model model){
        List<Address> addressList = addressService.findAll();
        var address = addressList.stream()
                .findFirst()
                .orElse(new Address());
        String detailAddress = "Ho Chi Minh City";
        if (address.getId() != null){
            detailAddress = address.getDetailLocation() + " " + address.getDistrict() + " " + address.getCity();
        }

        model.addAttribute("address", detailAddress);
        return "shipper/infoShipper";
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<?> updateAvatar(){
        return ResponseEntity.ok("Successfully!");
    }
}
