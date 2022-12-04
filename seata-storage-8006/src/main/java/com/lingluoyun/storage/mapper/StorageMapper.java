package com.lingluoyun.storage.mapper;

import com.lingluoyun.storage.entity.Storage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageMapper {
    Storage selectByProductId(Long productId);
    int decrease(Storage record);
}
