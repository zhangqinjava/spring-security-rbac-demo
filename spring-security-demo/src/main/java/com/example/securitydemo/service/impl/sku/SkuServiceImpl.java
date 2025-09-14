package com.example.securitydemo.service.impl.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import com.example.securitydemo.bean.vo.sku.Category;
import com.example.securitydemo.bean.vo.sku.CategoryExcel;
import com.example.securitydemo.mapper.sku.SkuMapper;
import com.example.securitydemo.service.sku.SkuService;
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
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Override
    public List<Category> query(CategoryDto categoryDto) {
        log.info("query  sku type:{}", categoryDto);
        List<Category> list = skuMapper.query(categoryDto);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        if(Stream.of(categoryDto.getId(),categoryDto.getParentId()).allMatch(Objects::isNull)){
            return builderAll(list);
        }
        return builderSub(list);
    }

    @Override
    public boolean add(Category category) {
       log.info("add sku type:{}", category);
        return  skuMapper.add(category);
    }

    @Override
    public boolean update(Category category) {
        log.info("update  sku type:{}", category);
        return skuMapper.update(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        log.info("delete  sku type:{}", id);
        //删除我们需要查询当前大类下面的所有的子类，并且将其删除
        List<Category> list = skuMapper.query(CategoryDto.builder().build());
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        Map<Integer, List<Category>> map = list.stream().filter(Objects::nonNull).filter(c -> Objects.nonNull(c.getParentId())).collect(Collectors.groupingBy(Category::getParentId, Collectors.toList()));
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
    public List<Category> list() {
        List<Category> list = skuMapper.query(CategoryDto.builder().build());
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }

    @Override
    public List<CategoryExcel> listExcel(Category category) {
        log.info("listExcel  sku type:{}", category);
        List<Category> list = this.query(CategoryDto.builder().id(category.getId()).name(category.getName()).parentId(category.getParentId()).build());
        List<CategoryExcel> categoryExcelList = new ArrayList<>();
        this.flattenTree(list, categoryExcelList ,list.get(0).getParentId());
        return categoryExcelList;
    }
    public void flattenTree(List<Category> list,List<CategoryExcel> flat,int level) {
        for (Category node : list) {
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
    public Set<Integer> builder(Integer parentId ,Map<Integer,List<Category>> map){
        if(parentId == null){
            return null;
        }
        Set<Integer> ids = new HashSet<>();
        List<Category> childer = map.get(parentId);
        if(!CollectionUtils.isEmpty(childer)){
            for (Category category : childer) {
                ids.add(category.getId());
                ids.addAll(builder(category.getId(),map));
            }
        }
        return ids;
    }
    /**
     * 直接返回根节点的目录
     */
    public List<Category> builderAll(  List<Category> list){
        List<Category> collect = list.stream().sorted(Comparator.comparingInt(Category::getParentId)).collect(Collectors.toList());
        Map<Integer, List<Category>> map = list.stream().filter(Objects::nonNull).filter(c ->Objects.nonNull(c.getParentId()) ).collect(Collectors.groupingBy(Category::getParentId, Collectors.toList()));
        log.info("query  sku count:{}", map.size());
        for (Category item : collect) {
            List<Category> items = map.get(item.getId());
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
    public List<Category> builderSub( List<Category> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        list.stream().filter(Objects::nonNull).filter(c->Objects.nonNull(c.getId())).forEach(category -> {
            category.setChildren(builderSub(skuMapper.query(CategoryDto.builder().parentId(category.getId()).build())));
        });
        return list;

    }
}
