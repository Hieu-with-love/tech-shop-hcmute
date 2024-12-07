package com.hcmute.tech_shop.controllers.shipper;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.OrderReponse;
import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.Order;
import com.hcmute.tech_shop.entities.OrderDetail;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.OrderStatus;
import com.hcmute.tech_shop.services.Impl.OrderDetailServiceImpl;
import com.hcmute.tech_shop.services.Impl.OrderServiceImpl;
import com.hcmute.tech_shop.services.Impl.UserServiceImpl;
import com.hcmute.tech_shop.services.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        return "shipper/index";
    }

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

    @GetMapping("/shipping")
    public String showListShipping(Model model, @RequestParam(value = "status", required = false) String status) {
        UserRequest userRequest = getUser();
        List<OrderReponse> orderReponses;
        if (status == null||status.isEmpty()||status.equals("")) {
            orderReponses = orderService.findAllByShipperId(userRequest.getId());
        }
        else {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            orderReponses = orderService.findOrderByShipperNameAndStatus(userRequest.getId(),orderStatus);
        }

        // Tạo danh sách trạng thái, đặt trạng thái được chọn lên đầu
        List<String> orderStatusNews = new ArrayList<>(List.of("SHIPPING", "DELIVERED", "CANCELLED"));
        if (status != null && !status.isEmpty()) {
            orderStatusNews.remove(status);
            orderStatusNews.add(0, status); // Đặt trạng thái được chọn lên đầu
        }

        model.addAttribute("orderStatusNew", orderStatusNews);
        model.addAttribute("currentStatus", status); // Để dùng trong view

        model.addAttribute("orderReponses", orderReponses);
        model.addAttribute("orderStatus", OrderStatus.values());
        return "shipper/page-list-returns";
    }

    @GetMapping("/shipping/{id}")
    public String showDetailShipping(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id).get();
        List<OrderDetail> orderDetailList = orderDetailService.findAllByOrderId(id);

        model.addAttribute("order", order);
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("orderStatus", OrderStatus.values());
        return "shipper/pages-invoice";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam(value = "status") String status, @RequestParam(value = "orderId") Long orderId, Model model) {
        if (status.equals("SHIPPING")) {
            orderService.orderShipping(orderId);
        }
        else if (status.equals("DELIVERED")){
            orderService.orderDelivered(orderId);
        }
        else orderService.orderCancelled(orderId);
        return "redirect:/shipper/shipping/"+orderId;
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
