package itstudy.study.repository;

import itstudy.study.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserInterface extends JpaRepository<User, Long> {



    User findByUsername(String name);



}