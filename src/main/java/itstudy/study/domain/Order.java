package itstudy.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;


@Entity
@Table(name = "orders") //order가 sql 예약어라 s붙임
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //new Order()로 생성x , 생성메서드의 틀에서만 생성
public class Order { //주문엔티티

    @Id
    @GeneratedValue
    @Column(name = "order_id") //테이블 컬럼 이름
    private Long id;

    //테이블 컬림들

    @ManyToOne(fetch = LAZY) //order랑 member는 다대일 (서로반대) , 여러개 주문<-하나의 회원
    @JoinColumn //매핑을 어 떻게 할건지 : 연관관계 주인으로 fk
    private User user; //주문회원



    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //order에 의해서 매핑됐다 , 주인아님
    private List<OrderItem> orderItems = new ArrayList<>();



    private LocalDateTime orderDate; //주문시간 @Date 안써도 자바8이상에선 지원 //order_date로 바꿔짐 sql에서

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER ,CANCEL}





    //==연관관계 메서드== 양방향 연관관계일때
    public void setUser(User user) {
        this.user = user; //member setting할때 order 입장에서 이렇게 멤버를 넣는다
        user.getOrders().add(this);
    }



    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }



    //==생성메서드==//
    //회원,배송,주문내역 넘기기 (...: 여러개)
    public static Order createOrder(User user,  OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);
       // order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) { // 위에 선언 private List<OrderItem> orderItems = new ArrayList<>();
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); //초기값을 order로 강제해둔다
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //==비즈니스로직==//

    /**주문 취소
     *
     */
    public void cancel() {
        //배송이 끝나면 취소 불가
     /*   if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }*/

        //vaildation을 통과하면 주문 상태 바꿀 수 있다.
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); //

        }
    }
    
    //==조회 로직==//
    
    /*//전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice =0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }*/
}
