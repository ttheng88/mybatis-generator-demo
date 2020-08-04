package com.demo.mapper.basemapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * @author liangsy
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
