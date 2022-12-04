package com.lingluoyun.storage.service.impl;

import com.lingluoyun.storage.entity.Storage;
import com.lingluoyun.storage.mapper.StorageMapper;
import com.lingluoyun.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Resource
    StorageMapper storageMapper;
    @Override
    public int decrease(Long productId, Integer count) {
        log.info("------->storage-service 中扣减库存开始");
        log.info("------->storage-service 开始查询商品是否存在");
        Storage storage = storageMapper.selectByProductId(productId);
        if (storage != null && storage.getResidue() >= count) {
            Storage storage2 = new Storage();
            storage2.setProductId(productId);
            storage.setUsed(storage.getUsed() + count);
            storage.setResidue(storage.getTotal() - storage.getUsed());
            int decrease = storageMapper.decrease(storage);
            log.info("------->storage-service 扣减库存成功");
            return decrease;
        } else {
            log.info("------->storage-service 库存不足，开始回滚！");
            throw new RuntimeException("库存不足，扣减库存失败！");
        }
    }
}