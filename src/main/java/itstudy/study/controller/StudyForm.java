package itstudy.study.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudyForm {

    //상품 공통 속성
    private Long id; //상품 수정해야해서

    private String name;

    private int stockQuantity; //재고

    //속성

    private String subject; //과목
    private String host; //주최자
}