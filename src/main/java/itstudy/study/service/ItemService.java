package itstudy.study.service;

import itstudy.study.domain.item.Item;
import itstudy.study.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    /**
     * 상품 상세보기
     */



    /**
     * 상품 등록
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    /**
     * 
     *상품수정
     */
    @Transactional
    public void updateItem(Long itemId, String name, int stockQuantity ,String host) {
        Item findItem = itemRepository.findOne(itemId); //id를 기반으로 영속상태인 아이템을 찾아왔다

        findItem.setName(name);

        findItem.setStockQuantity(stockQuantity);
        findItem.setHost(host);

        
    }





    /**
     * 상품 목록
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 상품 수정할때 쓴다.
     * id받아서 수정하려고
     */
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}