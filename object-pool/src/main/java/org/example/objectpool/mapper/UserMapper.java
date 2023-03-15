package org.example.objectpool.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.objectpool.pojo.User;

/**
 * @author lch
 * @date 2023年03月15日 22:42
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
