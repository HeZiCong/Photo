package cc.gzvtc.photographer.service.impl;



import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cc.gzvtc.common.service.BaseService;
import cc.gzvtc.common.util.DateFormater;
import cc.gzvtc.model.TPhotographer;
import cc.gzvtc.photographer.service.IPhotographerService;
import cc.gzvtc.vo.ScheduleTotalVO;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Service(value = "photographerService")
public class PhotographerServiceImpl extends BaseService<TPhotographer> implements IPhotographerService {

	private static final Logger logger = LoggerFactory.getLogger(PhotographerServiceImpl.class);
	
	@Override
	public List<String> selectByStartEnd(String start, String end) throws IllegalArgumentException, ParseException {
		//拆分成每天加进dateList
		Date startTime = DateFormater.stringToDate(start);
		Date endTime = DateFormater.stringToDate(end);
		int day = DateFormater.dValueDate(startTime, endTime);
		List<String> dateList = new ArrayList<String>();
		dateList.add(start);
		for(int i=1;i<day;i++){
			dateList.add(DateFormater.dateToString(DateFormater.getNextUpdateDate(startTime, i)));
		}
		if(!start.equals(end)){
			dateList.add(end);
		}
		logger.info("天数"+dateList.toString());
		//一共查询多少天
		int total = dateList.size();
		//记录每个id查询结果的次数
		List<ScheduleTotalVO> totalList = new ArrayList<ScheduleTotalVO>();
		//循环查询
		for(String date :dateList){
			List<Map<String, Object>> sqlResult = sqlMapper.selectList("SELECT photographerId AS id FROM t_schedule WHERE '"+date+"' BETWEEN `start` AND `end`");
			List<ScheduleTotalVO> copyList = new ArrayList<ScheduleTotalVO>();
			for(Map<String,Object> map:sqlResult){
				logger.info(copyList.toString()+"list");
				//判断该id是否已存在次数结果集，是则+i,否则初始化为1
				if(totalList.size()>0){
					for(ScheduleTotalVO scheduleTotalVO : totalList){
						if(scheduleTotalVO.getId().equals(map.get("id").toString())){
							scheduleTotalVO.setTotal(scheduleTotalVO.getTotal()+1);
							copyList.add(scheduleTotalVO);
						}else{
							ScheduleTotalVO stv = new ScheduleTotalVO();
							stv.setId(map.get("id").toString());
							stv.setTotal(1);
							copyList.add(stv);
						}
					}
				}else{
					ScheduleTotalVO stv = new ScheduleTotalVO();
					stv.setId(map.get("id").toString());
					stv.setTotal(1);
					copyList.add(stv);
				}
			}
			totalList = copyList;
		}
		//有空的id存放List
		List<String> idList = new ArrayList<String>();
		for (ScheduleTotalVO scheduleTotalVO : totalList) {
			logger.info("key "+scheduleTotalVO.getId()+"value "+scheduleTotalVO.getTotal());
			if(scheduleTotalVO.getTotal()>=total){
				idList.add(scheduleTotalVO.getId());
			}
		}
		return idList;
	}
	

}
