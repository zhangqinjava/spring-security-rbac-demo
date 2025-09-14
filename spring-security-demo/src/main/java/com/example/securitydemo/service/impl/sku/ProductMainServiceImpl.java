package com.example.securitydemo.service.impl.sku;

import com.example.securitydemo.bean.dto.sku.ProductMainDto;
import com.example.securitydemo.bean.vo.sku.ProductMain;
import com.example.securitydemo.mapper.sku.ProductMainMapper;
import com.example.securitydemo.service.sku.ProductMainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service
@Slf4j
public class ProductMainServiceImpl implements ProductMainService {
    @Autowired
    private ProductMainMapper productMainMapper;
    @Override
    public PageInfo<ProductMain> query(ProductMainDto productMainDto) {
       log.info("query productMainDto:{}", productMainDto);
        PageHelper.startPage(productMainDto.getPageNum(), productMainDto.getPageSize());
        List<ProductMain> list = productMainMapper.query(productMainDto);
        return new PageInfo<>(list);
    }

    @Override
    public boolean add(ProductMainDto productMainDto) {
        log.info("add productMainDto:{}", productMainDto);
        return productMainMapper.insert(productMainDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAdd(List<ProductMainDto> productMainDto) {
        log.info("batchAdd productMainDto:{}", productMainDto);
        productMainDto.forEach(productMainDto1 -> {
            productMainMapper.insert(productMainDto1);
        });
        return true;
    }

    @Override
    public boolean update(ProductMainDto productMainDto) {
        log.info("update productMainDto:{}", productMainDto);
        return productMainMapper.update(productMainDto);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("delete id:{}", id);
        return productMainMapper.delete(id);
    }
}
