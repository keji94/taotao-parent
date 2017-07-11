package com.taotao.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wb-ny291824 on 2017/7/4.
 */
@Data
public class EasyUiTreeNode implements Serializable{
    private long id;
    private String text;
    private String state;
}
