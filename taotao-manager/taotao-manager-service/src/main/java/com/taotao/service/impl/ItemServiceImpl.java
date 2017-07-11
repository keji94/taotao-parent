package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.*;
import com.taotao.result.EasyUiResult;
import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.IDUtils;
import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/4.
 */
@Service
public class ItemServiceImpl implements ItemService{
    @Resource
    private TbItemMapper itemMapper;
    @Resource
    private TbItemCatMapper tbItemCatMapper;
    @Resource
    private TbItemDescMapper itemDescMapper;

    public EasyUiResult getItemList(int page,int rows){
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        example.setOrderByClause("created desc");
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list.size()>0){
            //取分页信息
            PageInfo<TbItem> pageInfo = new PageInfo<>(list);
            //创建返回结果对象
            EasyUiResult easyUiResult = new EasyUiResult();
            easyUiResult.setTotal(pageInfo.getTotal());
            easyUiResult.setRows(list);
            return easyUiResult;
        }
        return null;
    }

    @Override
    public List<EasyUiTreeNode> getItemCategory(long parentId) {
        TbItemCatExample catExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = catExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(catExample);
        //转换成EasyUiTreeNode列表
        ArrayList<EasyUiTreeNode> treeNodeArrayList = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCatList) {
            EasyUiTreeNode treeNode = new EasyUiTreeNode();
            treeNode.setId(tbItemCat.getId());
            treeNode.setText(tbItemCat.getName());
            treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到列表
            treeNodeArrayList.add(treeNode);
        }
        return treeNodeArrayList;
    }

    @Override
    public TaotaoResult saveItem(TbItem item, String desc) {
        //1.生成商品id
        long itemId = IDUtils.genItemId();
        //2.补全TbItem对象属性
        item.setId(itemId);
        //商品状态,1.正常2.下架,3.删除
        item.setStatus((byte)1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        itemMapper.insert(item);
        //4.创建TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        //5.补全TbItemDesc属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //6.像商品描述表插入数据
        int i = itemDescMapper.insert(itemDesc);
        if (i>0){
            return TaotaoResult.ok();
        }
        return null;
    }
}
