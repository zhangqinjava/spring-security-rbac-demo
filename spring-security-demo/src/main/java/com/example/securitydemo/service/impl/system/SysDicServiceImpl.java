package com.example.securitydemo.service.impl.system;

import com.example.securitydemo.bean.dto.system.SysDicDto;
import com.example.securitydemo.bean.vo.system.SysDicVo;
import com.example.securitydemo.common.Enums;
import com.example.securitydemo.mapper.system.SysDicMapper;
import com.example.securitydemo.service.system.SysDicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysDicServiceImpl implements SysDicService {
    @Autowired
    private SysDicMapper sysDicMapper;
    @Override
    public List<SysDicVo> query(SysDicDto sysDicdto) {
        /**
         * 字典按照树状返回
         */
        log.info("query sysDicVo:{}", sysDicdto);
        List<SysDicVo> query = sysDicMapper.query(sysDicdto);
        List<SysDicVo> newDicList = query.stream().filter(item -> item.getParentId() == 0).map(item -> {
            item.setChildren(this.getChildrens(item, query));
            item.setTypeName(item.getType() == null ? "" : Enums.getVlaueByGroup(item.getType(), "dic_type"));
            return item;
        }).sorted((item1, item2) -> {
            return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
        }).collect(Collectors.toList());
        return newDicList.size() > 0 ? newDicList : query;
    }

    @Override
    public boolean save(SysDicDto sysDicdto) {
        log.info("save sysDicVo:{}", sysDicdto);
        return sysDicMapper.save(sysDicdto);
    }

    @Override
    public boolean update(SysDicDto sysDicdto) {
        log.info("update sysDicVo:{}", sysDicdto);
        return sysDicMapper.update(sysDicdto);
    }

    @Override
    public boolean delete(SysDicDto sysDicdto) {
        log.info("delete sysDicVo:{}", sysDicdto);
        return sysDicMapper.delete(sysDicdto);
    }

    @Override
    public List<SysDicVo> queryAll(SysDicDto sysDicdto) {
            log.info("queryAll sysDicVo:{}", sysDicdto);
            return sysDicMapper.queryAll(sysDicdto);
    }

    public List<SysDicVo> getChildrens(SysDicVo dic, List<SysDicVo> list) {
        List<SysDicVo> treeDic = list.stream().filter(item -> Objects.equals(item.getParentId(), dic.getId())).map(item -> {
            // 递归添加子数据
            List<SysDicVo> childrens = getChildrens(item, list);
            item.setChildren(childrens);
            item.setTypeName(item.getType() == null ? "" : Enums.getVlaueByGroup(item.getType(), "dic_type"));
            return item;
        }).sorted((item1, item2) -> { // 排序
            return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
        }).collect(Collectors.toList());
        return treeDic;
    }
}
