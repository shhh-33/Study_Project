package itstudy.study.controller;

import itstudy.study.domain.item.Study;
import itstudy.study.domain.item.Item;
import itstudy.study.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;


    /**
     * 상품 상세보기
     */
    @RequestMapping("/study/detail")
    public String itemDetail(){


        return "items/itemDetail";
    }




    /**
     * 상품 등록 클릭시
     */
    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new StudyForm());  //이렇게 넘겨줘서 html에서 추적가능
        return "items/createItemForm";
    }


    /**
     * 상품 등록 (submit)
     */
    @PostMapping(value = "/items/new")
    public String create(StudyForm form) {
        Study study = new Study();

        study.setName(form.getName());

        study.setStockQuantity(form.getStockQuantity());
        study.setSubject(form.getSubject());
        study.setHost(form.getHost());

        itemService.saveItem(study);

        return "redirect:/items"; //저장된 책 목록으로
    }

    /**
     * 상품 목록
     */
    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     * 폼에서 수정데이터 입력 후 ->수정되어야하니 form 있어야함
     */
    @GetMapping("items/{itemID}/edit")
    public String updateItemForm(@PathVariable("itemID") Long itemId, Model model) {

        Study item = (Study) itemService.findOne(itemId); //item Id 받아서 수정

        StudyForm form = new StudyForm(); //form을 수정할때 study엔티티가 아니라 form을 보낼거임

        form.setId(item.getId());
        form.setName(item.getName());

        form.setStockQuantity(item.getStockQuantity());
        form.setSubject(item.getSubject());
        form.setHost(item.getHost());

        model.addAttribute("form", form);  //updateItemForm으로 보낸다.

        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     */
    @PostMapping(value = "/items/{itemId}/edit") //post로 받았다.
    public String updateItem(@ModelAttribute("form") StudyForm form) { //th:object="${form}" 넘어오게 (이름은 상관없)

        itemService.updateItem(form.getId(),form.getName(),form.getStockQuantity(),form.getHost());

        return "redirect:/items";



    }


}