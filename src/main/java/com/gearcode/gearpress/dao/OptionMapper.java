package com.gearcode.gearpress.dao;

import com.gearcode.gearpress.domain.Option;
import com.gearcode.gearpress.domain.OptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OptionMapper {
    int countByExample(OptionExample example);

    int deleteByExample(OptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Option record);

    int insertSelective(Option record);

    List<Option> selectByExampleWithBLOBsWithRowbounds(OptionExample example, RowBounds rowBounds);

    List<Option> selectByExampleWithBLOBs(OptionExample example);

    List<Option> selectByExampleWithRowbounds(OptionExample example, RowBounds rowBounds);

    List<Option> selectByExample(OptionExample example);

    Option selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByExampleWithBLOBs(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByExample(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKeyWithBLOBs(Option record);

    int updateByPrimaryKey(Option record);
}