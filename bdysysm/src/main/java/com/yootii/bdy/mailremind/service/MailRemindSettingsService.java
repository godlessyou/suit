package com.yootii.bdy.mailremind.service;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.mailremind.model.MailRemindSettings;

public interface MailRemindSettingsService {
	public ReturnInfo createMailRemindSettings(MailRemindSettings settings);
	public ReturnInfo deleteMailRemindSettings(Integer setId);
	public ReturnInfo createExistCustDefaultSettings(Integer custId);
	public ReturnInfo queryMailRemindSettings(Integer custId);
}
