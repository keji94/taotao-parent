package com.taotao.controller.content;

import com.taotao.content.service.content.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUiResult;
import com.taotao.result.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wb-ny291824 on 2017/7/12.
 */
@Controller
@Slf4j
@RequestMapping("/content")
public class ContentController {

    @Resource
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUiResult showContent(long categoryId, int page, int rows){
        EasyUiResult result = null;
        try {
           result = contentService.showContent(categoryId, page, rows);
        } catch (Exception e) {
            log.error("contentService.showContent is error",e);
            return null;
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent tbContent){
        try {
            contentService.saveContent(tbContent);
        } catch (Exception e) {
            log.error("contentService.saveContent is error...",e);
            return TaotaoResult.makeFail();
        }
        return TaotaoResult.ok();
    }

    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult editContent(TbContent tbContent){
        TaotaoResult result =null;
        try {
            result = contentService.editContent(tbContent);
        } catch (Exception e) {
            log.error("contentService.editContent is error...");
            return TaotaoResult.makeFail();
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(Long[] ids){
        TaotaoResult result = contentService.deleteContents(ids);
        return TaotaoResult.ok();
    }
}
