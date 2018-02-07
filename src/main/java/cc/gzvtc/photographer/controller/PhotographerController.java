package cc.gzvtc.photographer.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.common.util.OperationFileUtil;
import cc.gzvtc.model.TPhotographer;
import cc.gzvtc.model.TPhotographerLabel;
import cc.gzvtc.model.TPhotographerLevel;
import cc.gzvtc.model.TPhotographerSpots;
import cc.gzvtc.photographer.service.IPhotographerLabelService;
import cc.gzvtc.photographer.service.IPhotographerLevelService;
import cc.gzvtc.photographer.service.IPhotographerService;
import cc.gzvtc.photographer.service.IPhotographerSpotsService;
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
public class PhotographerController {

	private static final Logger logger = LoggerFactory.getLogger(PhotographerController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "photographerService")
	private IPhotographerService photographerService;
	@Resource(name = "photographerLabelService")
	IPhotographerLabelService photographerLabelService;
	@Resource(name = "photographerLevelService")
	IPhotographerLevelService photographerLevelService;
	@Resource(name = "photographerSpotsService")
	IPhotographerSpotsService photographerSpotsService;

	/**
	 * 添加摄影师
	 * 
	 * @param photographer
	 * @param HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "addPhotographer", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addPhotographer(TPhotographer photographer, HttpServletRequest request, Integer labelId,
			Integer levelId, Integer spotsId) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			Map<String, String> map = OperationFileUtil.multiFileUpload(request,
					request.getServletContext().getRealPath("/") + "uploads/photographer/");
			String filePath = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				filePath = entry.getValue();
			}
			filePath = filePath.replace(request.getServletContext().getRealPath("/"), "/");
			photographer.setHead(filePath);
			photographer.setCreatetime(new Date());
			photographerService.insert(photographer);
			int id = photographer.getId();
			TPhotographerLabel label = new TPhotographerLabel(labelId, id, new Date(), "0");
			photographerLabelService.insert(label);

			TPhotographerLevel level = new TPhotographerLevel(levelId, id, new Date(), "0");
			photographerLevelService.insert(level);

			TPhotographerSpots spots = new TPhotographerSpots(spotsId, id, new Date(), "0");
			photographerSpotsService.insert(spots);

			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增photographer失败" + e);
		}
		return returnResult;

	}

	/**
	 * 修改photographer状态
	 * 
	 * @param photographer
	 * @return
	 */
	@RequestMapping(value = "updatePhotographerStatus", method = RequestMethod.GET)
	@ResponseBody
	public ReturnResult updatePhotographerStatus(TPhotographer photographer) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			photographerService.updateBySQL("UPDATE t_photographer SET status=" + photographer.getStatus()
					+ " WHERE id=" + photographer.getId());

			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("更新photographer状态失败" + e);
		}
		return returnResult;
	}

	/**
	 * 修改photographer的name和summary
	 * 
	 * @param photographer
	 * @return
	 */
	@RequestMapping(value = "updatePhotographer", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updatePhotographer(TPhotographer photographer, Integer labelId, Integer levelId,
			Integer spotsId) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			photographerService.updateBySQL("UPDATE t_photographer SET name='" + photographer.getName() + "',summary='"
					+ photographer.getSummary() + "',status=" + photographer.getStatus() + " WHERE id="
					+ photographer.getId());
			photographerLabelService.updateBySQL("UPDATE t_photographer_label SET labelId=" + labelId
					+ " WHERE photographerId=" + photographer.getId());
			photographerLevelService.updateBySQL("UPDATE t_photographer_level SET levelId=" + levelId
					+ " WHERE photographer=" + photographer.getId());
			photographerSpotsService.updateBySQL("UPDATE t_photographer_spots SET spotsId=" + spotsId
					+ " WHERE photographerId=" + photographer.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改photographer失败" + e);
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * 根据id获取Photographer
	 * admin
	 * @param Photographer
	 * @return
	 */
	@RequestMapping(value = "getPhotographerById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getPhotographerById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS)
					.setData(photographerService.selectBySQL(
							"SELECT a.*,b.labelId,c.levelId,d.spotsId FROM t_photographer a,t_photographer_label b,t_photographer_level c,t_photographer_spots d WHERE a.id ="
									+ id + " AND b.photographerId=" + id + " AND c.photographer=" + id
									+ " AND d.photographerId=" + id + "")
							.get(0));
		} catch (Exception e) {
			logger.error("根据id获取Photographer失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据id获取Photographer
	 * user
	 * @param Photographer
	 * @return
	 */
	@RequestMapping(value = "getPhotographer")
	@ResponseBody
	public ReturnResult getPhotographer(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS)
			.setData(photographerService.selectBySQL(
					"SELECT a.name,a.head,a.summary,d.`name` AS level,e.`name` AS spots FROM t_photographer a,t_photographer_level b,t_photographer_spots c,t_level d,t_spots e WHERE a.id="+id+" AND b.photographer="+id+" AND c.photographerId="+id+" AND d.id=b.levelId AND e.id = c.spotsId AND a.`status`=0")
					.get(0));
		} catch (Exception e) {
			logger.error("根据id获取Photographer失败" + e);
		}
		return returnResult;
	}

	/**
	 * 分页获取photographer
	 * 
	 * @return
	 */
	@RequestMapping(value = "getPhotographerListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getPhotographerListByPage(PageVO page, String labelId, String levelId, String spotsId,
			String beforeTime, String afterTime, String status, String name) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer(
					"SELECT a.*,f.`name` AS label,g.`name` AS level,h.`name` AS spots FROM t_photographer a,t_photographer_label b,t_photographer_level c ,t_photographer_spots d,t_label f,t_level g,t_spots h WHERE a.id=b.photographerId AND a.id = c.photographer AND a.id = d.photographerId AND f.id=b.labelId AND g.id=c.levelId AND h.id= d.spotsId");

			if (StringUtils.isNotBlank(labelId)) {
				sql.append(" AND b.labelId=" + labelId);
				sql.append(" AND f.id=" + labelId);
			}
			if (StringUtils.isNotBlank(levelId)) {
				sql.append(" AND c.levelId=" + levelId);
				sql.append(" AND g.id=" + levelId);
			}
			if (StringUtils.isNotBlank(spotsId)) {
				sql.append(" AND d.spotsId=" + spotsId);
				sql.append(" AND h.id=" + spotsId);
			}

			if (StringUtils.isNotBlank(status)) {
				sql.append(" AND a.status=" + status);
			}
			if (StringUtils.isNotBlank(name)) {
				sql.append(" AND a.name=" + name);
			}

			List<Map<String, Object>> results = photographerService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = photographerService.selectCount(new TPhotographer());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		} catch (Exception e) {
			logger.error("分页获取photographer失败" + e);
		}
		return returnResult;
	}

	/**
	 * 分页获取启用的photographer
	 * 
	 * @return
	 */
	@RequestMapping(value = "getPhotographerListByPageStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getPhotographerListByPageStatus(PageVO page, String labelId, String levelId, String spotsId,
			String start, String end) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer(
					"SELECT a.*,f.`name` AS label,g.`name` AS level,h.`name` AS spots FROM t_photographer a,t_photographer_label b,t_photographer_level c ,t_photographer_spots d,t_label f,t_level g,t_spots h WHERE a.id=b.photographerId AND a.id = c.photographer AND a.id = d.photographerId AND f.id=b.labelId AND g.id=c.levelId AND h.id= d.spotsId AND a.status=0");

			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) AS total FROM t_photographer a,t_photographer_label b,t_photographer_level c ,t_photographer_spots d,t_label f,t_level g,t_spots h WHERE a.id=b.photographerId AND a.id = c.photographer AND a.id = d.photographerId AND f.id=b.labelId AND g.id=c.levelId AND h.id= d.spotsId AND a.status=0");
			if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
				List<String> list = photographerService.selectByStartEnd(start,end);
				for(String id : list){
					sql.append(" AND a.id!="+id);
					countSql.append(" AND a.id!="+id);
				}
			}
			if (StringUtils.isNotBlank(labelId)) {
				sql.append(" AND b.labelId=" + labelId);
				sql.append(" AND f.id=" + labelId);
				countSql.append(" AND b.labelId=" + labelId);
				countSql.append(" AND f.id=" + labelId);
			}
			if (StringUtils.isNotBlank(levelId)) {
				sql.append(" AND c.levelId=" + levelId);
				sql.append(" AND g.id=" + levelId);
				countSql.append(" AND c.levelId=" + levelId);
				countSql.append(" AND g.id=" + levelId);
			}
			if (StringUtils.isNotBlank(spotsId)) {
				sql.append(" AND d.spotsId=" + spotsId);
				sql.append(" AND h.id=" + spotsId);
				countSql.append(" AND d.spotsId=" + spotsId);
				countSql.append(" AND h.id=" + spotsId);
			}

			List<Map<String, Object>> results = photographerService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = Integer.valueOf( photographerService.selectBySQL(countSql.toString()).get(0).get("total").toString());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		} catch (Exception e) {
			logger.error("分页获取启用的photographer失败" + e);
		}
		return returnResult;
	}

	/**
	 * 获取所有启用的Photographer
	 * 
	 * @param Photographer
	 * @return
	 */
	@RequestMapping(value = "getAllPhotographer", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getAllPhotographer() {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(photographerService.selectBySQL(
					"SELECT a.id,a.name FROM t_photographer a,t_photographer_label b,t_photographer_level c ,t_photographer_spots d,t_label f,t_level g,t_spots h WHERE a.id=b.photographerId AND a.id = c.photographer AND a.id = d.photographerId AND f.id=b.labelId AND g.id=c.levelId AND h.id= d.spotsId AND a.status=0"));
		} catch (Exception e) {
			logger.error("获取所有启用的Photographer失败" + e);
		}
		return returnResult;
	}

}
