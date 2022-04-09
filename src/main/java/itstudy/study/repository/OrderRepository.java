package itstudy.study.repository;

import itstudy.study.domain.Order;
import itstudy.study.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    //단건 조회
    public Order findOne(Long id) {

        return em.find(Order.class, id);
    }

    //주문내역 검색 -> 동적쿼리 jpql로 작성시 오류발생확률 높고... 실무에서 안씀!!
    public List<Order> findByString(OrderSearch orderSearch) {


        String jpql = "select o from Order o join o.user m";  //order를 조회하고 order랑 order랑 연관된 member를 join한다.

        boolean isFirstCondition = true;


        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) { //값이 있으면
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getUserName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건


        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getUserName())) {
            query = query.setParameter("name", orderSearch.getUserName());
        }
        return query.getResultList();
    }


    //JPA가 제공하는 표준 동적쿼리 (JPA Criteria)
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, User> m = o.join("user", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getUserName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getUserName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000 건
        return query.getResultList();
    }


    }

