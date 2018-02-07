package cc.gzvtc.banner.controller;

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

import cc.gzvtc.banner.service.IBannerService;
import cc.gzvtc.common.util.OperationFileUtil;
import cc.gzvtc.model.TBanner;
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
public class BannerController {

	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "bannerService")
	private IBannerService bannerService;

	/**
	 * 添加banner图
	 * 
	 * @param banner
	 * @param HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "addBanner", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addBanner(TBanner banner, HttpServletRequest request) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			Map<String, String> map = OperationFileUtil.multiFileUpload(request,
					request.getServletContext().getRealPath("/") + "uploads/banner/");
			String filePath = "";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				filePath = entry.getValue();
			}
			filePath = filePath.replace(request.getServletContext().getRealPath("/"), "/");
			banner.setPath(filePath);
			banner.setCreatetime(new Date());
			bannerService.insert(banner);
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增banner失败" + e);
		}
		return returnResult;

	}

	/**
	 * 删除banner
	 * @param banner
	 * @return
	 */
	@RequestMapping(value = "deleteBannerById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult deleteBannerById(Integer id,HttpServletRequest request) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			String filePath = request.getServletContext().getRealPath("/");
			TBanner banner = bannerService.selectByPrimaryKey(id);
			filePath +=banner.getPath();
			OperationFileUtil.deleteFile(filePath);
			bannerService.deleteByPrimaryKey(id);
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("删除banner失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 修改banner的title
	 * @param banner
	 * @return
	 */
	@RequestMapping(value = "updateBanner", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateBanner(TBanner banner) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			bannerService.updateBySQL("UPDATE t_banner SET title='" + banner.getTitle() + "' WHERE id=" + banner.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改banner失败" + e);
		}
		return returnResult;
	}
	/**
	 * 根据id获取banner
	 * @param banner
	 * @return
	 */
	@RequestMapping(value = "getBannerById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getBannerById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(bannerService.selectByPrimaryKey(id));
		} catch (Exception e) {
			logger.error("根据id获取banner失败" + e);
		}
		return returnResult;
	}
	
	/**
	 * 获取所有的banner
	 * @return
	 */
	@RequestMapping(value = "getAllBanner")
	@ResponseBody
	public ReturnResult getAllBanner(PageVO page) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT DISTINCT * FROM t_banner WHERE 1=1");
		
			
			List<Map<String, Object>> results = bannerService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total = bannerService.selectCount(new TBanner());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取banner失败" + e);
		}
		return returnResult;
	}

}
