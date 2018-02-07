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
import cc.gzvtc.model.TAttachment;
import cc.gzvtc.model.TWorks;
import cc.gzvtc.photographer.service.IAttachmentService;
import cc.gzvtc.photographer.service.IWorksService;
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
public class WorksController {

	private static final Logger logger = LoggerFactory.getLogger(WorksController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "worksService")
	private IWorksService worksService;
	
	@Resource(name = "attachmentService")
	private IAttachmentService attachmentService;

	/**
	 * 添加作品
	 * 
	 * @param works
	 * @param HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "addWorks", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addWorks(TWorks works, HttpServletRequest request) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			Map<String, String> map = OperationFileUtil.multiFileUpload(request,
					request.getServletContext().getRealPath("/") + "uploads/images/");
			
			works.setCreatetime(new Date());
			worksService.insert(works);
			//插入附件
			int worksId = works.getId();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				TAttachment attachment = new TAttachment();
				attachment.setWorksid(worksId);
				attachment.setPath(entry.getValue().replace(request.getServletContext().getRealPath("/"), "/"));
				attachment.setStatus("0");
				attachment.setCreatetime(new Date());
				attachmentService.insert(attachment);
			}
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增works失败" + e);
			e.printStackTrace();
		}
		return returnResult;

	}

	/**
	 * 修改works状态
	 * @param works
	 * @return
	 */
	@RequestMapping(value = "updateWorksStatus", method = RequestMethod.GET)
	@ResponseBody
	public ReturnResult updateWorksStatus(TWorks works) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			worksService.updateBySQL("UPDATE t_works SET status=" + works.getStatus() + " WHERE id=" + works.getId());
			
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("更新works状态失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 修改works封面
	 * @param works
	 * @return
	 */
	@RequestMapping(value = "updateWorksPath", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateWorksPath(TWorks works,HttpServletRequest request) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, String> map = OperationFileUtil.multiFileUpload(request,
					request.getServletContext().getRealPath("/") + "uploads/images/");
			String filePath = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				filePath = entry.getValue();
			}
			filePath = filePath.replace(request.getServletContext().getRealPath("/"), "/").replace("\\", "/");
			worksService.updateBySQL("UPDATE t_works SET path='" + filePath + "' WHERE id=" + works.getId());
			
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error(" 修改works封面失败" + e);
		}
		return returnResult;
	}
	
	
	/**
	 * 修改works的title和content
	 * @param works
	 * @return
	 */
	@RequestMapping(value = "updateWorks", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateWorks(TWorks works) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			worksService.updateBySQL("UPDATE t_works SET title='" + works.getTitle() + "',content='"+works.getContent()+"' WHERE id=" + works.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改works失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据id获取Works 的照片
	 * @param Works
	 * @return
	 */
	@RequestMapping(value = "getWorksImgById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getWorksImgById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(worksService.selectBySQL("SELECT path FROM t_attachment WHERE worksId="+id));
		} catch (Exception e) {
			logger.error("根据id获取Works 的照片 失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 根据id获取Works 
	 * @param Works
	 * @return
	 */
	@RequestMapping(value = "getWorksById")
	@ResponseBody
	public ReturnResult getWorksById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(worksService.selectByPrimaryKey(id));
		} catch (Exception e) {
			logger.error("根据id获取Works失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据id获取Works 用户视图
	 * @param Works
	 * @return
	 */
	@RequestMapping(value = "getWorks")
	@ResponseBody
	public ReturnResult getWorks(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(worksService.getWorks(id));
		} catch (Exception e) {
			logger.error("根据id获取Works失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据photographerid获取Works 
	 * @param Works
	 * @return
	 */
	@RequestMapping(value = "getWorksByPhotographerId", method = RequestMethod.GET)
	@ResponseBody
	public ReturnResult getWorksByPhotographerId(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(worksService.getWorksByPhotographerId(id));
		} catch (Exception e) {
			logger.error("根据photographerid获取Works失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 分页获取works
	 * @return
	 */
	@RequestMapping(value = "getWorksListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getWorksListByPage(PageVO page,String photographerId,String spotsId,String status) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT a.*,b.`name` AS photographer,c.`name` AS spots FROM t_works a,t_photographer b,t_spots c WHERE a.photographerId=b.id AND a.spotsId=c.id");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) AS total FROM t_works a,t_photographer b,t_spots c WHERE a.photographerId=b.id AND a.spotsId=c.id");
			if(StringUtils.isNotBlank(photographerId)){
				sql.append(" AND b.id="+photographerId);
				countSql.append(" AND b.id="+photographerId);
			}
			if(StringUtils.isNotBlank(spotsId)){
				sql.append(" AND c.id="+spotsId);
				countSql.append(" AND c.id="+spotsId);
			}
			if(StringUtils.isNotBlank(status)){
				sql.append(" AND a.status="+status);
				countSql.append(" AND a.status="+status);
			}
			List<Map<String, Object>> results = worksService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = Integer.valueOf(worksService.selectBySQL(countSql.toString()).get(0).get("total").toString());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取works失败" + e);
		}
		return returnResult;
	}
	/**
	 * 分页获取启用的works
	 * @return
	 */
	@RequestMapping(value = "getWorksListByPageStatus", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getWorksListByPageStatus(String sord,String spotsId) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			String sql = "SELECT * FROM t_works WHERE status=0";
			if(StringUtils.isNotBlank(sord)){
				sql="SELECT * FROM t_works WHERE status=0 ORDER BY id DESC";
			}
			if(StringUtils.isNotBlank(spotsId)){
				sql = "SELECT * FROM t_works WHERE status=0 AND spotsId="+spotsId;
			}
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(worksService.selectBySQL(sql));
		}catch (Exception e) {
			logger.error("分页获取启用的works失败" + e);
		}
		return returnResult;
	}
}
