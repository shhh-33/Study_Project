package itstudy.study.service;

import itstudy.study.domain.User;
import itstudy.study.repository.UserInterface;
import itstudy.study.repository.UserRepository;
import itstudy.study.exception.AlreadyRegisteredUserException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInterface userInterface;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;



    /**
     * 회원 가입 회원 등록
     *
     * @param username username
     * @param password password
     * @return 유저 권한을 가지고 있는 유저
     */
    public User signup(
            String username,
            String password
    ) {
        if (userInterface.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }
        return userInterface.save(new User(username, passwordEncoder.encode(password), "ROLE_USER"));
    }

    /**
     * 관리자 등록
     *
     * @param username username
     * @param password password
     * @return 관리자 권한을 가지고 있는 유저
     */
    public User signupAdmin(
            String username,
            String password
    ) {
        if (userInterface.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }
        return userInterface.save(new User(username, passwordEncoder.encode(password), "ROLE_ADMIN"));
    }

    public User findByUsername(String username) {
        return userInterface.findByUsername(username);
    }


    public List<User> findMembers() {
        return userRepository.findAll();
    }

    //단건 조회 (id로)
    public User findOne(Long memberId) {
        return userRepository.fineOne(memberId);
    }




}