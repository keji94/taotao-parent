package com.taotao.content.service.content;

import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUiResult;
import com.taotao.result.TaotaoResult;

/**
 * Created by wb-ny291824 on 2017/7/12.
 */
public interface ContentService {
    EasyUiResult showContent(long categoryId, int page, int rows);

    TaotaoResult saveContent(TbContent tbContent);

    TaotaoResult editContent(TbContent tbContent);

    TaotaoResult deleteContents(Long[] ids);
}
