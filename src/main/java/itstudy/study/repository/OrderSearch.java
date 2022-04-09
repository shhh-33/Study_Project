package itstudy.study.repository;

import itstudy.study.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String userName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]
}
