package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.result.EasyUiResult;
import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.TaotaoResult;

import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/4.
 */
public interface ItemService {
    public EasyUiResult getItemList(int page, int rows);
    public List<EasyUiTreeNode> getItemCategory(long parentId);
    public TaotaoResult saveItem(TbItem item,String desc);
}
