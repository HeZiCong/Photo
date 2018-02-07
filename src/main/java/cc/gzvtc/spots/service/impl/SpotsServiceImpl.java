package cc.gzvtc.spots.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cc.gzvtc.common.service.BaseService;
import cc.gzvtc.model.TSpots;
import cc.gzvtc.spots.service.ISpotsService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Service(value = "spotsService")
public class SpotsServiceImpl extends BaseService<TSpots> implements ISpotsService {

	@Override
	public List<TSpots> getAllSpots() {
		Example example = new Example(TSpots.class);
		example.createCriteria().andCondition("status=",0);
		
		return mapper.selectByExample(example);
	}

	
}
