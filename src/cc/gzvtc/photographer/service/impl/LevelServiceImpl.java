package cc.gzvtc.photographer.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cc.gzvtc.common.service.BaseService;
import cc.gzvtc.model.TLevel;
import cc.gzvtc.photographer.service.ILevelService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Service(value = "levelService")
public class LevelServiceImpl extends BaseService<TLevel> implements ILevelService {

	@Override
	public List<TLevel> getAllLevel() {
		Example example = new Example(TLevel.class);
		example.createCriteria().andCondition("status=",0);
		return mapper.selectByExample(example);
	}

}
