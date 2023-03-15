package org.example.objectpool.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author lch
 * @date 2023年03月15日 22:41
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
