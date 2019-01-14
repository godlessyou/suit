package com.yootii.bdy.customer.model;

import java.util.List;

public class Regions extends Region {
   
   
    public List<Regions> getCitys() {
		return citys;
	}

	public void setCitys(List<Regions> citys) {
		this.citys = citys;
	}

	List <Regions> citys;

    
}