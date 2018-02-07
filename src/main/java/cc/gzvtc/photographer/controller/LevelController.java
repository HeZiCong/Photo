package cc.gzvtc.photographer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.model.TLevel;
import cc.gzvtc.photographer.service.ILevelService;
import cc.gzvtc.vo.PageVO;
import cc.gzvtc.vo.ReturnCodeType;
import cc.gzvtc.vo.ReturnResult;

/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Controller
@Scope("prototype")
public class LevelController {

	private static final Logger logger = LoggerFactory.getLogger(LevelController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "levelService")
	private ILevelService levelService;

	/**
	 * 添加级别
	 * 
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "addLevel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addLevel(TLevel level) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			level.setCreatetime(new Date());
			level.setStatus("0");
			levelService.insert(level);
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增level失败" + e);
		}
		return returnResult;

	}

	/**
	 * 修改level状态
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "updatelevelStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updatelevelStatus(TLevel level) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			levelService.updateBySQL("UPDATE t_level SET status=" + level.getStatus() + " WHERE id=" + level.getId());
			
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("更新level状态失败" + e);
		}
		return returnResult;
	}
	
	
	/**
	 * 修改level的name
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "updateLevel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateLevel(TLevel level) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			levelService.updateBySQL("UPDATE t_level SET name='" + level.getName() + "', status="+level.getStatus()+" WHERE id=" + level.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改level失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据id获取level
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "getLevelById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getLevelById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(levelService.selectByPrimaryKey(id));
		} catch (Exception e) {
			logger.error("根据id获取level失败" + e);
		}
		return returnResult;
	}
	/**
	 * 分页获取level
	 * @return
	 */
	@RequestMapping(value = "getLevelListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getLevelListByPage(PageVO page,String Status) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT * FROM t_level WHERE 1=1");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) AS total FROM t_level WHERE 1=1");
			
		    if(StringUtils.isNotBlank(Status)){
		    	sql.append(" AND status="+Status);
		    	countSql.append(" AND status="+Status);
		    }
		   
			
			List<Map<String, Object>> results = levelService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = Integer.valueOf(levelService.selectBySQL(countSql.toString()).get(0).get("total").toString());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取level失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 获取所有启用的level
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "getAllLevel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getAllLevel(){
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(levelService.getAllLevel());
		} catch (Exception e) {
			logger.error("获取所有启用的level" + e);
		}
		return returnResult;
	}
}
