package com.yootii.bdy.preference.service;

import java.util.List;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.preference.model.PreferenceField;
import com.yootii.bdy.preference.model.PreferenceValue;

public interface PreferenceService {

	ReturnInfo queryPreferenceField(PreferenceField preferenceField, GeneralCondition gcon);

	ReturnInfo createPreferenceField(PreferenceField preferenceField);

	ReturnInfo deletePreferenceField(PreferenceField preferenceField);

	ReturnInfo modifyPreferenceField(PreferenceField preferenceField);

	ReturnInfo queryPreferenceValue(PreferenceValue preferenceValue, GeneralCondition gcon);

	ReturnInfo createPreferenceValue(PreferenceValue preferenceValue);

	ReturnInfo deletePreferenceValue(PreferenceValue preferenceValue);

	ReturnInfo modifyPreferenceValue(List<PreferenceValue> preferenceValue);

}
