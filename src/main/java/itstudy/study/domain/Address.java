package itstudy.study.domain;

import lombok.Getter;

//주소
//@Embeddable //JPA의 내장 타입 Member에 썼으니 안써도..
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
