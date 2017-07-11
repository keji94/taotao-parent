package com.taotao.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wb-ny291824 on 2017/7/4.
 */
@Data
public class EasyUiResult implements Serializable{
    private Long total;
    private List<?> rows;

    public  EasyUiResult(){

    }
    public EasyUiResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

}
