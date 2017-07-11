package com.taotao.content.service;

import com.taotao.result.EasyUiTreeNode;
import com.taotao.result.TaotaoResult;

import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/10.
 */
public interface IContentCategoryService {
    List<EasyUiTreeNode> getContentCatList(long parentId);

    TaotaoResult createCategory(long parentId, String name);

    TaotaoResult updateCategory(long id, String name);

    TaotaoResult deleteCategory(long id);
}
