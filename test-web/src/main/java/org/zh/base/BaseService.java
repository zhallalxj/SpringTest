package org.zh.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BaseService接口
 * @author  level.meng.
 */
public interface BaseService<Record, Example,ID> {

	long countByExample(Example example);

	int deleteByExample(Example example);

	int deleteByPrimaryKey(ID id);

	int insert(Record record);

	int insertSelective(Record record);

	List<Record> selectByExampleWithBLOBs(Example example);

	List<Record> selectByExample(Example example);

	Record selectFirstByExample(Example example);

	Record selectByPrimaryKey(ID id);

	int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

	int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example);

	int updateByExample(@Param("record") Record record, @Param("example") Example example);

	int updateByPrimaryKeySelective(Record record);

	int updateByPrimaryKeyWithBLOBs(Record record);

	int updateByPrimaryKey(Record record);

	int deleteByPrimaryKeys(String ids);

	void initMapper();



}