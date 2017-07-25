package com.taotao.content.service.content;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.result.EasyUiResult;
import com.taotao.result.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/12.
 */
@Service
@Slf4j
public class ContentServiceImpl implements ContentService{
    @Resource
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUiResult showContent(long categoryId, int page, int rows) {
        PageHelper.startPage(page,rows);
        List<TbContent> list = null;
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        if (categoryId == 0 ){
            list = tbContentMapper.selectByExample(example);
        }else {
            criteria.andCategoryIdEqualTo(categoryId);
            list = tbContentMapper.selectByExample(example);
        }
        if (list.size()>0){
            PageInfo<TbContent> pageInfo = new PageInfo<>(list);
            EasyUiResult easyUiResult = new EasyUiResult();
            easyUiResult.setTotal(pageInfo.getTotal());
            easyUiResult.setRows(list);
            return easyUiResult;
        }
        return null;
    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        int i = 0 ;
        try {
             i = tbContentMapper.insert(tbContent);
        } catch (Exception e) {
            log.error("tbContentMapper.insert is error...",e);
            return TaotaoResult.makeFail();
        }
        if (i>0){
            return TaotaoResult.ok();
        }
        return TaotaoResult.makeFail();
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(tbContent.getCategoryId());
        criteria.andIdEqualTo(tbContent.getId());
        tbContent.setUpdated(new Date());
        try {
            tbContentMapper.updateByExample(tbContent,example);
        } catch (Exception e) {
            log.error("tbContentMapper.updateByExample is error...",e);
            return TaotaoResult.makeFail();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContents(Long[] ids) {
        for (Long id : ids) {
            try {
                tbContentMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                log.error("tbContentMapper.deleteByPrimaryKey is error...id="+id);
                return TaotaoResult.makeFail();
            }
        }
        return TaotaoResult.ok();
    }
}
