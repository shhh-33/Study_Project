package itstudy.study.service;


import itstudy.study.domain.Order;
import itstudy.study.domain.OrderItem;
import itstudy.study.domain.User;
import itstudy.study.domain.item.Item;
import itstudy.study.repository.ItemRepository;
import itstudy.study.repository.OrderRepository;
import itstudy.study.repository.OrderSearch;
import itstudy.study.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    //엔티티 조회하느라 의존관계 넣음
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    /*
    주문
     */
    @Transactional   //ID로 member,item 정보 받아옴 , 주문수량
    public Long order(Long UserId, Long itemId, int count) {

        //member를 찾자 엔티티 조회
      //  Member member = memberRepository.fineOne(memberId);


        User user = userRepository.fineOne(UserId);

        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        //Delivery delivery = new Delivery();
        //delivery.setAddress(user.getAddress()); //회원의 주소를 배송지로 설정 (간단하게 하기위해...)


        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count);

        //주문 생성
        Order order = Order.createOrder(user, orderItem);
        //new Order();

        //주문 저장
        orderRepository.save(order);
        return order.getId(); //식별자(@id)값만 반환


    }


    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문취소
        order.cancel();
    }


    //주문 검색

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findByString(orderSearch);
    }


}
