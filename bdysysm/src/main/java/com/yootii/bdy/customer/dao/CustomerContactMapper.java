package com.yootii.bdy.customer.dao;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.CustomerContact;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerContactMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerContact record);

    int insertSelective(CustomerContact record);

    CustomerContact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerContact record);

    int updateByPrimaryKey(CustomerContact record);

	List<CustomerContact> selectByCustomerContact(@Param("customerContact")CustomerContact customerContact, @Param("gcon")GeneralCondition gcon);

	Long selectCountByCustomerContact(@Param("customerContact")CustomerContact customerContact, @Param("gcon")GeneralCondition gcon);
}