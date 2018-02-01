package cc.gzvtc.photographer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.common.util.DateFormater;
import cc.gzvtc.model.TSchedule;
import cc.gzvtc.model.TUser;
import cc.gzvtc.photographer.service.IScheduleService;
import cc.gzvtc.vo.PageVO;
import cc.gzvtc.vo.ReturnCodeType;
import cc.gzvtc.vo.ReturnResult;
import cc.gzvtc.vo.ScheduleVO;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Controller
@Scope("prototype")
public class ScheduleController {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "scheduleService")
	private IScheduleService scheduleService;

	/**
	 * 添加预约
	 * 
	 * @param schedule
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "addSchedule", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addSchedule(TSchedule schedule, HttpSession session,String startdate,String enddate) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			schedule.setStart(DateFormater.stringToDate(startdate));
			schedule.setEnd(DateFormater.stringToDate(enddate));
			schedule.setCreatetime(new Date());
			TUser user = (TUser)session.getAttribute("user");
			schedule.setUserid(user.getId());
			if(scheduleService.insert(schedule)>0){
				returnResult.setStatus(ReturnCodeType.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("新增schedule失败" + e);
		}
		return returnResult;

	}

	/**
	 * 修改schedule状态
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "updateScheduleStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateScheduleStatus(TSchedule schedule,HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			session.getAttribute("admin");
			scheduleService.updateBySQL("UPDATE t_schedule SET status=" + schedule.getStatus() + " WHERE id=" + schedule.getId());
			
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("更新schedule状态失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 设置摄影师无档期或有预约
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "setSchedule", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult setSchedule(Integer photographerId,String createTimeRange,HttpSession session,String status) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			if(photographerId!=null&&StringUtils.isNotBlank(createTimeRange)){
				String start = createTimeRange.split(" - ")[0];
				String end = createTimeRange.split(" - ")[1];
				session.getAttribute("admin");
				TSchedule schedule = new TSchedule();
				schedule.setUserid(1);
				schedule.setCreatetime(new Date());
				schedule.setStatus(status);
				schedule.setStart(DateFormater.stringToDate("yyyy/MM/dd",start));
				schedule.setEnd(DateFormater.stringToDate("yyyy/MM/dd",end));
				schedule.setPhotographerid(photographerId);
				scheduleService.insert(schedule);
				returnResult.setStatus(ReturnCodeType.SUCCESS);
			}
			
		} catch (Exception e) {
			logger.error("更新schedule状态失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 根据id获取schedule
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "getScheduleById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getScheduleById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(scheduleService.selectByPrimaryKey(id));
		} catch (Exception e) {
			logger.error("根据id获取schedule失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据摄影师id获取schedule
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "getScheduleByPhotographerId", method = RequestMethod.GET)
	@ResponseBody
	public ScheduleVO getScheduleByPhotographerId(Integer photoer_id,String year,String month) {
		
		return scheduleService.getScheduleByPhotographerId( photoer_id, year, month);
	}
	
	/**
	 * 分页获取schedule
	 * @return
	 */
	@RequestMapping(value = "getScheduleListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getScheduleListByPage(PageVO page,String photographerId,String start,String end) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT a.createTime,a.`status`,a.id,a.`start`,a.`end`,b.`name`,b.tel,c.`name` AS photographer FROM t_schedule a, t_user b ,t_photographer c WHERE a.userId = b.id AND a.photographerId =c.id");
			if(StringUtils.isNotBlank(photographerId)){
				sql.append(" AND a.photographerId="+photographerId);
			}
			if(StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(end)){
				sql.append(" AND a.`start` BETWEEN '"+start+"' AND '"+end+"' OR a.`end` BETWEEN '"+start+"' AND '"+end+"'");
			}
			sql.append(" GROUP BY a.id ORDER BY a.createTime DESC");
			List<Map<String, Object>> results = scheduleService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = scheduleService.selectCount(new TSchedule());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取schedule失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 根据用户获取schedule
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "getSchedule", method = RequestMethod.GET)
	@ResponseBody
	public ReturnResult getSchedule(HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		TUser user = (TUser) session.getAttribute("user");
		return returnResult.setStatus(ReturnCodeType.SUCCESS).setData(scheduleService.selectBySQL("SELECT DATE_FORMAT(a.`start`,'%Y-%m-%d') AS start,DATE_FORMAT(a.`end`,'%Y-%m-%d') AS end,b.name,a.`status`,a.id,a.photographerId FROM t_schedule a,t_photographer b WHERE a.photographerId = b.id AND a.userId="+user.getId()));
	}
	/**
	 *删除
	 * @param schedule
	 * @return
	 */
	@RequestMapping(value = "deleteSchedule", method = RequestMethod.GET)
	@ResponseBody
	public ReturnResult deleteSchedule(HttpSession session,Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			 session.getAttribute("user");
			return returnResult.setStatus(ReturnCodeType.SUCCESS).setData(scheduleService.deleteByPrimaryKey(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}
}
