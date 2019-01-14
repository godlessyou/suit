package com.yootii.bdy.preference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.preference.model.PreferenceField;

public interface PreferenceFieldMapper {
    int deleteByPrimaryKey(Integer preferenceId);

    int insert(PreferenceField record);

    int insertSelective(PreferenceField record);

    PreferenceField selectByPrimaryKey(Integer preferenceId);

    int updateByPrimaryKeySelective(PreferenceField record);

    int updateByPrimaryKey(PreferenceField record);

	List<PreferenceField> selectByPreferenceField(@Param("preferenceField")PreferenceField preferenceField, @Param("gcon")GeneralCondition gcon);

	Long selectCountByPreferenceField(@Param("preferenceField")PreferenceField preferenceField);
}