package com.taotao.controller.content;

import com.taotao.content.service.contentCategory.IContentCategoryService;
import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/10.
 */
@Controller
@Slf4j
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Resource
    private IContentCategoryService iContentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0")long parentId){
        List<EasyUiTreeNode> catList = null;
        try {
            catList = iContentCategoryService.getContentCatList(parentId);
        } catch (Exception e) {
            log.error("iContentCategoryService.getContentCatList is error"+e);
        }
        if (catList.size()>0){
            return catList;
        }else {
            log.error("catList.size=0");
            return null;
        }
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createCategory(long parentId,String name){
        TaotaoResult result = null;
        try {
            result = iContentCategoryService.createCategory(parentId, name);
        } catch (Exception e) {
            log.error("iContentCategoryService.createCategory",e);
            return TaotaoResult.makeFail();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public TaotaoResult updateCategory(long id,String name){



        TaotaoResult result = null;
        try {
             result = iContentCategoryService.updateCategory(id, name);
        } catch (Exception e) {
            log.error("iContentCategoryService.updateCategory is error",e);
            return TaotaoResult.makeFail();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public TaotaoResult deleteCategory(long id){
        TaotaoResult result = iContentCategoryService.deleteCategory(id);
        return result;
    }
}
