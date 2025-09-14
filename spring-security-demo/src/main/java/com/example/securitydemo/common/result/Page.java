package com.example.securitydemo.common.result;

import org.springframework.util.CollectionUtils;

import java.util.List;

public class Page<T> {
    private Integer code;
    private String msg;
    private T data;
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;

    public Page(){}
    public Page(Integer code, String msg, T data, Integer pageNum, Integer pageSize, Integer total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }
    public static  <T> Page<T> ok(List<T> list,int pageNum,int pageSize){
        int start = (pageNum-1)*pageSize;
        if(CollectionUtils.isEmpty(list) || list.size()<=start){
            return new Page(200,"查询成功",list,pageNum,pageSize,list==null?0:list.size());
        }
        int end = list.size()<start+pageSize?list.size():start*pageSize;
        return new Page(200,"查询成功",list.subList(start, end),pageNum,pageSize,list.size());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
