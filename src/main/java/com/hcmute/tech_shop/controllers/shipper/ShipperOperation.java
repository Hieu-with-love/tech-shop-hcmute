package com.hcmute.tech_shop.controllers.shipper;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.OrderReponse;
import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.Impl.OrderServiceImpl;
import com.hcmute.tech_shop.services.Impl.UserServiceImpl;
import com.hcmute.tech_shop.services.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    public UserRequest getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .active(user.isActive())
                .image(user.getImage())
                .build();
        return userRequest;
    }

    @GetMapping("/list-need-ship")
    public String showListShipping(Model model) {
        UserRequest userRequest = getUser();
        List<OrderReponse> orderReponses = orderService.findOrderByShipperNameAndStatus(userRequest.getId(),"PENDING");

        model.addAttribute("orderReponses", orderReponses);
        return "shipper/page-list-returns";
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
