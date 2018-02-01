package cc.gzvtc.photographer.service;

import java.util.List;
import java.util.Map;

import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TWorks;
import cc.gzvtc.vo.WorksVO;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface IWorksService extends IService<TWorks> {

	List<Map<String,Object>> getWorksByPhotographerId(Integer id);

	WorksVO getWorks(Integer id);
	

	
}
