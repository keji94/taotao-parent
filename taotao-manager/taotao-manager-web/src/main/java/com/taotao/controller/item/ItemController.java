package com.taotao.controller.item;

import com.taotao.pojo.TbItem;
import com.taotao.result.EasyUiResult;
import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/4.
 */
@Slf4j
@Controller
public class ItemController {

    @Resource
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/item/list")
    public EasyUiResult showItem(Integer page,Integer rows){
        EasyUiResult result = null;
        try {
            result = itemService.getItemList(page, rows);
        } catch (Exception e) {
            log.error("itemService.getItemList is error "+ e);
            TaotaoResult.makeFail();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/item/cat/list")
    public List<EasyUiTreeNode> getItemCategory(@RequestParam(value = "id",defaultValue = "0")long parentId){
        List<EasyUiTreeNode> category = itemService.getItemCategory(parentId);
        return category;
    }

    @ResponseBody
    @RequestMapping("/item/save")
    public TaotaoResult saveItem(TbItem item,String desc){
        TaotaoResult result = itemService.saveItem(item, desc);
        return result;
    }

}
