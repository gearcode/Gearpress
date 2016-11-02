package com.gearcode.gearpress.dao;

import com.gearcode.gearpress.domain.Column;
import com.gearcode.gearpress.domain.ColumnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ColumnMapper {
    int countByExample(ColumnExample example);

    int deleteByExample(ColumnExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Column record);

    int insertSelective(Column record);

    List<Column> selectByExampleWithRowbounds(ColumnExample example, RowBounds rowBounds);

    List<Column> selectByExample(ColumnExample example);

    Column selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByExample(@Param("record") Column record, @Param("example") ColumnExample example);

    int updateByPrimaryKeySelective(Column record);

    int updateByPrimaryKey(Column record);
}