package com.example.securitydemo.service.impl.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import com.example.securitydemo.bean.vo.sku.CategoryExcel;
import com.example.securitydemo.mapper.sku.CategoryMapper;
import com.example.securitydemo.service.sku.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper skuMapper;
    @Override
    public List<com.example.securitydemo.bean.vo.sku.Category> query(CategoryDto categoryDto) {
        log.info("query  sku type:{}", categoryDto);
        List<com.example.securitydemo.bean.vo.sku.Category> list = skuMapper.query(categoryDto);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        if(Stream.of(categoryDto.getId(),categoryDto.getParentId()).allMatch(Objects::isNull)){
            return builderAll(list);
        }
        return builderSub(list);
    }

    @Override
    public boolean add(com.example.securitydemo.bean.vo.sku.Category category) {
       log.info("add sku type:{}", category);
        return  skuMapper.add(category);
    }

    @Override
    public boolean update(com.example.securitydemo.bean.vo.sku.Category category) {
        log.info("update  sku type:{}", category);
        return skuMapper.update(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        log.info("delete  sku type:{}", id);
        //删除我们需要查询当前大类下面的所有的子类，并且将其删除
        List<com.example.securitydemo.bean.vo.sku.Category> list = skuMapper.query(CategoryDto.builder().build());
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        Map<Integer, List<com.example.securitydemo.bean.vo.sku.Category>> map = list.stream().filter(Objects::nonNull).filter(c -> Objects.nonNull(c.getParentId())).collect(Collectors.groupingBy(com.example.securitydemo.bean.vo.sku.Category::getParentId, Collectors.toList()));
        //获取到了所有的子类
        Set<Integer> sets = builder(id, map);
        sets.add(id);
        log.info("delete sku detail:{}", sets);
        sets.forEach(ids -> {
            skuMapper.delete(ids);
        });
        return true;
    }

    @Override
    public List<com.example.securitydemo.bean.vo.sku.Category> list() {
        List<com.example.securitydemo.bean.vo.sku.Category> list = skuMapper.query(CategoryDto.builder().build());
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }

    @Override
    public List<CategoryExcel> listExcel(com.example.securitydemo.bean.vo.sku.Category category) {
        log.info("listExcel  sku type:{}", category);
        List<com.example.securitydemo.bean.vo.sku.Category> list = this.query(CategoryDto.builder().id(category.getId()).name(category.getName()).parentId(category.getParentId()).build());
        List<CategoryExcel> categoryExcelList = new ArrayList<>();
        this.flattenTree(list, categoryExcelList ,list.get(0).getParentId());
        return categoryExcelList;
    }
    public void flattenTree(List<com.example.securitydemo.bean.vo.sku.Category> list, List<CategoryExcel> flat, int level) {
        for (com.example.securitydemo.bean.vo.sku.Category node : list) {
            flat.add(new CategoryExcel(node.getId(),node.getName(), node.getParentId(),  level));
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                flattenTree(node.getChildren(), flat, level + 1);
            }
        }
    }

    /**
     * 递归获取所有的子类
     * @param parentId
     * @param map
     * @return
     */
    public Set<Integer> builder(Integer parentId ,Map<Integer,List<com.example.securitydemo.bean.vo.sku.Category>> map){
        if(parentId == null){
            return null;
        }
        Set<Integer> ids = new HashSet<>();
        List<com.example.securitydemo.bean.vo.sku.Category> childer = map.get(parentId);
        if(!CollectionUtils.isEmpty(childer)){
            for (com.example.securitydemo.bean.vo.sku.Category category : childer) {
                ids.add(category.getId());
                ids.addAll(builder(category.getId(),map));
            }
        }
        return ids;
    }
    /**
     * 直接返回根节点的目录
     */
    public List<com.example.securitydemo.bean.vo.sku.Category> builderAll(List<com.example.securitydemo.bean.vo.sku.Category> list){
        List<com.example.securitydemo.bean.vo.sku.Category> collect = list.stream().sorted(Comparator.comparingInt(com.example.securitydemo.bean.vo.sku.Category::getParentId)).collect(Collectors.toList());
        Map<Integer, List<com.example.securitydemo.bean.vo.sku.Category>> map = list.stream().filter(Objects::nonNull).filter(c ->Objects.nonNull(c.getParentId()) ).collect(Collectors.groupingBy(com.example.securitydemo.bean.vo.sku.Category::getParentId, Collectors.toList()));
        log.info("query  sku count:{}", map.size());
        for (com.example.securitydemo.bean.vo.sku.Category item : collect) {
            List<com.example.securitydemo.bean.vo.sku.Category> items = map.get(item.getId());
            if(CollectionUtils.isEmpty(items)){
                continue;
            }
            item.setChildren(items);
        }
        return map.get(collect.get(0).getParentId());
    }
    /**
     * 获取当前条件查询的所有的子类
     */
    public List<com.example.securitydemo.bean.vo.sku.Category> builderSub(List<com.example.securitydemo.bean.vo.sku.Category> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        list.stream().filter(Objects::nonNull).filter(c->Objects.nonNull(c.getId())).forEach(category -> {
            category.setChildren(builderSub(skuMapper.query(CategoryDto.builder().parentId(category.getId()).build())));
        });
        return list;

    }
}
