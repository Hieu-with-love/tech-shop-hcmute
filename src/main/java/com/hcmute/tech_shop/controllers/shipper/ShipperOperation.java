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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public String dashboard(Model model, HttpSession session) {
        UserRequest userRequest = getUser();

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        List<Order> orderList = orderService.ordersByYearAndMonthForShipper(currentYear, currentMonth, getUser().getId());
        List<Order> totalPriceOrderList = orderService.totalPriceByYearAndMonthForShipper(currentYear, currentMonth, getUser().getId());

        String totalPrice = orderService.totalPriceByYearAndMonthByShipper(currentYear, currentMonth, getUser().getId());

        model.addAttribute("user", userRequest);
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalPriceOrderList", totalPriceOrderList);
        model.addAttribute("totalPrice", totalPrice);

        session.setAttribute("user", userRequest);
        session.setAttribute("orderList", orderList);
        session.setAttribute("totalPriceOrderList", totalPriceOrderList);
        session.setAttribute("totalPrice", totalPrice);

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
    public String showListShipping(Model model, @RequestParam(value = "status", required = false) String status, HttpSession session) {
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

        session.setAttribute("user", userRequest);
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

    @GetMapping("/summary")
    public String showListHistShipOrder(Model model, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "month", required = false) Integer month) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        if(year == null){
            year = currentYear;
        }
        if(month == null){
            month = currentMonth;
        }

        List<Order> orderList = orderService.ordersByYearAndMonthForShipper(year, month, getUser().getId());
        List<Order> totalPriceOrderList = orderService.totalPriceByYearAndMonthForShipper(year, month, getUser().getId());

        String totalPrice = orderService.totalPriceByYearAndMonthByShipper(year, month, getUser().getId());

        model.addAttribute("orderList", orderList);
        model.addAttribute("totalPriceOrderList", totalPriceOrderList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("currentMonth", currentMonth);
        return "shipper/page-report";
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
        return "shipper/user-profile";
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<?> updateAvatar(){
        return ResponseEntity.ok("Successfully!");
    }
}
