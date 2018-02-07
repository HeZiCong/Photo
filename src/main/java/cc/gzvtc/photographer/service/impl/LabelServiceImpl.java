package cc.gzvtc.photographer.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cc.gzvtc.common.service.BaseService;
import cc.gzvtc.model.TLabel;
import cc.gzvtc.photographer.service.ILabelService;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Service(value = "labelService")
public class LabelServiceImpl extends BaseService<TLabel> implements ILabelService {

	@Override
	public List<TLabel> getAllLabel() {
		Example example = new Example(TLabel.class);
		example.createCriteria().andCondition("status=",0);
		return mapper.selectByExample(example);
	}

}
