package com.yootii.bdy.remind.service;

import java.util.Date;
import java.util.List;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.remind.model.Remind;
import com.yootii.bdy.taskremind.model.TaskRemind;

public interface RemindService {
	
	public Boolean deleteRemind(Integer remindId);
	
	public Boolean insertRemind(Remind remind);
	
	Long selectRemindListCount(Remind remind, GeneralCondition gcon);

	List<Remind> selectRemindLists(Remind remind, GeneralCondition gcon);

	ReturnInfo queryRemindList(Remind remind, GeneralCondition gcon);

	Boolean insertRemindByType(Integer type,Date date,Integer agencyId,Integer custId,Integer caseId);

	
}