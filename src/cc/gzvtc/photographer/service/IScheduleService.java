package cc.gzvtc.photographer.service;


import cc.gzvtc.common.service.IService;
import cc.gzvtc.model.TSchedule;
import cc.gzvtc.vo.ScheduleVO;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
public interface IScheduleService extends IService<TSchedule> {

	ScheduleVO getScheduleByPhotographerId(Integer photoer_id, String year, String month);
	

}
