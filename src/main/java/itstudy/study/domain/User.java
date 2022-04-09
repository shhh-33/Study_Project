package itstudy.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table
@Getter

@Setter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    /*
   userDetails : 인증,인가할때 사용 (내장)


     */

    @GeneratedValue
    @Id
    @Column(name = "user_id") //pk 컬럼명 //엔티티의 식별자
    private Long id;


    private String username;
    private String password;
    private String authority;



    @OneToMany(mappedBy = "user") //하나의 회원, 여러개 상품 주문 : 일대다
    //mappedBy : 연관관계 주인이 아니다. order 테이블에 있는 member필드에 의해 매핑된것
    //읽기전용 : 여기에 값을 넣는다해서 외래키 값이 변경되지 않음
    private List<Order> orders = new ArrayList<>(); //null pointer exception 안나게



    public User(
            String username,
            String password,

            String authority
    ) {
        this.username = username;
        this.password = password;

        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    public Boolean isAdmin() {
        return authority.equals("ROLE_ADMIN");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
