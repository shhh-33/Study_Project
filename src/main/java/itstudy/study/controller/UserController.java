package itstudy.study.controller;

import itstudy.study.domain.User;
import itstudy.study.dto.UserRegisterDto;
import itstudy.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 회원가입 Controller
 */
@Controller
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    /**
     * @return 회원가입 페이지 리소스
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }


    @PostMapping("/signup")
    public String signup(
            @ModelAttribute UserRegisterDto userDto
    ) {
        userService.signup(userDto.getUsername(), userDto.getPassword());
        // 회원가입 후 로그인 페이지로 이동
        return "redirect:login";
    }


    /*
회원목록
 */
    @GetMapping("/members")
    public String list(Model model){
        List<User> users = userService.findMembers(); //ctrl +alt + v
        model.addAttribute("users", users); //(키,값)
        return "members/memberList";

    }
}
