package cc.gzvtc.spots.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.common.util.OperationFileUtil;
import cc.gzvtc.model.TSpots;
import cc.gzvtc.spots.service.ISpotsService;
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
public class SpotsController {

	private static final Logger logger = LoggerFactory.getLogger(SpotsController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "spotsService")
	private ISpotsService spotsService;

	/**
	 * 添加拍摄景点
	 * 
	 * @param spots
	 * @param HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "addSpots", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addSpots(TSpots spots, HttpServletRequest request) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			Map<String, String> map = OperationFileUtil.multiFileUpload(request,
					request.getServletContext().getRealPath("/") + "uploads/spots/");
			String filePath = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				filePath = entry.getValue();
			}
			filePath = filePath.replace(request.getServletContext().getRealPath("/"), "/");
			spots.setPath(filePath);
			spots.setCreatetime(new Date());
			spotsService.insert(spots);
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增spots失败" + e);
		}
		return returnResult;

	}

	
	/**
	 * 修改spots
	 * @param spots
	 * @return
	 */
	@RequestMapping(value = "updateSpots", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateSpots(TSpots spots) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			spotsService.updateBySQL("UPDATE t_spots SET name='" + spots.getName() + "',content='"+spots.getContent()+"', status="+spots.getStatus()+" WHERE id=" + spots.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改spots失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 分页获取spots
	 * @return
	 */
	@RequestMapping(value = "getSpotsListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getSpotsListByPage(PageVO page) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT DISTINCT * FROM t_spots WHERE 1=1");
		
			
			List<Map<String, Object>> results = spotsService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = spotsService.selectCount(new TSpots());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取spots失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据获取id spots
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getSpotsById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getSpotsById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(spotsService.selectByPrimaryKey(id));
		}catch (Exception e) {
			logger.error("根据获取spots失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 获取所有启用的spots
	 * @return
	 */
	@RequestMapping(value = "getAllSpots", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getAllSpots() {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(spotsService.getAllSpots());
		} catch (Exception e) {
			logger.error("获取所有启用spots失败" + e);
		}
		return returnResult;
	}
	/**
	 * 获取所有5条启用的spots
	 * @return
	 */
	@RequestMapping(value = "getFiveSpots", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getFiveSpots() {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(spotsService.selectBySQL("select * from t_spots  ORDER BY id DESC limit 0,5"));
		} catch (Exception e) {
			logger.error("获取所有5条启用的spots失败" + e);
		}
		return returnResult;
	}

}
