package com.example.securitydemo.service.impl.sku;

import com.example.securitydemo.bean.dto.sku.SkuDto;
import com.example.securitydemo.bean.vo.sku.Sku;
import com.example.securitydemo.mapper.sku.SkuMapper;
import com.example.securitydemo.service.sku.SkuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Override
    public PageInfo<Sku> queryAll(SkuDto skuDto) {
        log.info("query skuDto:{}", skuDto);
        PageHelper.startPage(skuDto.getPageNum(), skuDto.getPageSize());
        List<Sku> list = skuMapper.query(skuDto);
        return new PageInfo<Sku>(list);
    }

    @Override
    public boolean delete(SkuDto skuDto) {
        log.info("delete skuDto:{}", skuDto);
        if (Objects.isNull(skuDto.getId())) {
            return false;
        }
        return skuMapper.delete(skuDto.getId());
    }

    @Override
    public boolean save(SkuDto skuDto) {
        log.info("save skuDto:{}", skuDto);
        skuDto.setPrice(skuDto.getPrice().multiply(new BigDecimal(100)));
        return skuMapper.insert(skuDto);
    }

    @Override
    public boolean update(SkuDto skuDto) {
        log.info("update skuDto:{}", skuDto);
        if (Objects.isNull(skuDto.getId())) {
            return false;
        }
        skuDto.setPrice(skuDto.getPrice().multiply(new BigDecimal(100)));
        return skuMapper.update(skuDto);
    }

    @Override
    public List<Sku> export(SkuDto skuDto) {
        log.info("export sku:{}", skuDto);
        List<Sku> list = skuMapper.query(skuDto);
        return list;
    }
}
