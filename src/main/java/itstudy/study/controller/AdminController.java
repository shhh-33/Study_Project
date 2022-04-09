package itstudy.study.controller;

import itstudy.study.domain.Note;
import itstudy.study.service.NoteService;
import itstudy.study.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j //logger(lombok)
@RequestMapping("/admin")
public class  AdminController {

    private final NoteService noteService;

    /**
     * 어드민인 경우 노트 조회
     *
     * @return admin/index
     */
    @GetMapping
    public String getNoteForAdmin(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);
        return "admin/index";
    }


    @RequestMapping("/page") //주소경로
    public String home(){

        return "/admin/adminPage"; //html 파일 경로 앞에 / 붙일것..제발
    }
}
