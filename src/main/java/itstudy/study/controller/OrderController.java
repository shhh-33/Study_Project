package itstudy.study.controller;

import itstudy.study.domain.Order;
import itstudy.study.domain.item.Item;
import itstudy.study.repository.OrderSearch;
import itstudy.study.service.ItemService;
import itstudy.study.service.OrderService;
import itstudy.study.domain.User;
import itstudy.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        //모든 회원,상품 가져옴
       List<User> users = userService.findMembers();
       List<Item> items =itemService.findItems();

       //모델에담아서
       model.addAttribute("users",users);
       model.addAttribute("items", items);

       //view로 넘김
       return "order/orderForm";

    }


    //@RequestParam("가져올 데이터의 이름") [데이터타입] [가져온데이터를 담을 변수] html에서 submit했을때 name값이 넘어온다
    @PostMapping(value = "/order")
    public String order(@RequestParam("userId") Long userId,
                        @RequestParam("count") int count,
                        @RequestParam("itemId") Long itemId) {

        orderService.order(userId, itemId, count);

        return "redirect:/orders";

        /*
        주문결과 창 나중에 만들거면
        Long orderID = orderService.order(memberId, itemId, count);
        return "redirect:/orders" +orderID;
         */
    }

    @GetMapping("/orders")
    //@ModelAttribute : html에서 th:object="${orderSearch} 받아옴 model박스에 자동으로 담기고 뿌릴 수 있다.
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch , Model model){

        List<Order> orders = orderService.findOrders(orderSearch);
        //form sumit 후 값이 있는 상태로 OrderSearch에 바인딩
        model.addAttribute("orders",orders);
       // model.addAttribute("orderSearch",orderSearch); ModelAttribute: 생략되어있는거라 생각하면 된다.

        return "redirect:/items";


    }


    @GetMapping("/orders2")
    //@ModelAttribute : html에서 th:object="${orderSearch} 받아옴 model박스에 자동으로 담기고 뿌릴 수 있다.
    public String orderList2(@ModelAttribute("orderSearch")OrderSearch orderSearch , Model model){

        List<Order> orders = orderService.findOrders(orderSearch);
        //form sumit 후 값이 있는 상태로 OrderSearch에 바인딩
        model.addAttribute("orders",orders);
        // model.addAttribute("orderSearch",orderSearch); ModelAttribute: 생략되어있는거라 생각하면 된다.

        return "order/orderList";


    }


    @PostMapping(value = "/orders/{orderId}/cancel") //url경로에 변수 넣어주기
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }



}
