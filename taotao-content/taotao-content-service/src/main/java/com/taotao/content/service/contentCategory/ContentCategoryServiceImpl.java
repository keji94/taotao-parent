package com.taotao.content.service.contentCategory;

import com.taotao.constant.TTConstants;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.TaotaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/10.
 */
@Service
@Slf4j
public class ContentCategoryServiceImpl implements IContentCategoryService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUiTreeNode> getContentCatList(long parentId) {
        //取查询参数parentID
        List<TbContentCategory> list = getTbContentCategoriesByParentId(parentId);
        List<EasyUiTreeNode> easyUiTreeNodeList = new ArrayList<>();
        for (TbContentCategory category : list) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            easyUiTreeNodeList.add(node);
        }
        return easyUiTreeNodeList;
    }

    @Override
    public TaotaoResult createCategory(long parentId, String name) {
        //填充category属性
        TbContentCategory category = initCategory(parentId, name);
        int i = 0;
        try {
            i = tbContentCategoryMapper.insert(category);
        } catch (Exception e) {
            log.error("tbContentCategoryMapper.insert is error ",e);
            return TaotaoResult.makeFail();
        }
        if (i==1){
            //插入成功,更新父节点
            TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            if (!parentNode.getIsParent()) {
                TaotaoResult result = updateParentNode(parentNode, parentId);
                if (result.getStatus() != 200){
                    return TaotaoResult.makeFail();
                }
            }
            return TaotaoResult.ok(category);
        }else {
            return TaotaoResult.makeFail();
        }
    }

    @Override
    public TaotaoResult updateCategory(long id, String name) {
        TbContentCategory category = null;
        int count = 0;
        try {
            category = tbContentCategoryMapper.selectByPrimaryKey(id);
            category.setName(name);
            count = tbContentCategoryMapper.updateByPrimaryKey(category);
        } catch (Exception e) {
            log.error("tbContentCategoryMapper.selectByPrimaryKey is error",e);
            return TaotaoResult.makeFail();
        }
        if (count==1){
            return TaotaoResult.ok();
        }else {
            return TaotaoResult.makeFail();
        }
    }

    @Override
    public TaotaoResult deleteCategory(long id) {
        /**
         * 1.根据id查询isParent
         * 2.是-->查询子节点,判断是否是parent   递归
         * 3.否-->删除,查询父节点下面是否还有其他子节点.
         * 4.有-->不做操作
         * 5.无-->更改isParent
         */
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (category == null){
            log.error("TbContentCategory is null...Id = "+id);
            return TaotaoResult.makeFail();
        }

        deleteAllNodes(category,id);
        return TaotaoResult.ok();
    }

    private TaotaoResult deleteAllNodes(TbContentCategory category,Long id) {
        if (category.getIsParent()){//是父节点,递归删除
            List<TbContentCategory> list = getTbContentCategoriesByParentId(id);
            //根据parentID查询所有的子节点.
            for (TbContentCategory contentCategory : list) {
                Long contentCategoryId = contentCategory.getId();
                deleteAllNodes(contentCategory,contentCategoryId);
            }
            //删除当前节点
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }else {//不是父节点,根据id删除
            Long parentId = category.getParentId();
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            //查询该节点的父节点下是否还有其他节点,如果没有,更改isParent状态
            List<TbContentCategory> categoryList = getTbContentCategoriesByParentId(parentId);
            if (categoryList.size() == 0){//没有其他节点
                //获得父节点
                TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
                parentCategory.setIsParent(false);
                //更新
                tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
            }
        }
        return TaotaoResult.ok();
    }

    private List<TbContentCategory> getTbContentCategoriesByParentId(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        return tbContentCategoryMapper.selectByExample(example);
    }

    private TbContentCategory initCategory(long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setIsParent(false);
        category.setParentId(parentId);
        category.setName(name);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        //id
        //status
        category.setStatus(1);
        //sort_order
        category.setSortOrder(1);
        return category;
    }

    private TaotaoResult updateParentNode(TbContentCategory parentNode,long parentId) {
            parentNode.setIsParent(true);
            try {
                tbContentCategoryMapper.updateByPrimaryKey(parentNode);
            } catch (Exception e) {
                log.error("tbContentCategoryMapper.updateByPrimaryKey is error",e);
                return TaotaoResult.makeFail(TTConstants.updateFailed);
            }
        return TaotaoResult.ok(TTConstants.updateSuccess);
    }
}
