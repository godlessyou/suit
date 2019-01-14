package com.yootii.bdy.preference.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.preference.model.PreferenceValue;

public interface PreferenceValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PreferenceValue record);

    int insertSelective(PreferenceValue record);

    PreferenceValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PreferenceValue record);

    int updateByPrimaryKey(PreferenceValue record);

	List<PreferenceValue> selectByPreferenceValue(@Param("preferenceValue")PreferenceValue preferenceValue, @Param("gcon")GeneralCondition gcon);

	Long selectCountByPreferenceValue(@Param("preferenceValue")PreferenceValue preferenceValue);
}