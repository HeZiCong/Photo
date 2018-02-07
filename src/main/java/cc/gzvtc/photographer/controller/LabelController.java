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

import cc.gzvtc.model.TLabel;
import cc.gzvtc.photographer.service.ILabelService;
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
public class LabelController {

	private static final Logger logger = LoggerFactory.getLogger(LabelController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "labelService")
	private ILabelService labelService;

	/**
	 * 添加标签
	 * 
	 * @param Label
	 * @return
	 */
	@RequestMapping(value = "addLabel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addLabel(TLabel label) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {

			label.setCreatetime(new Date());
			label.setStatus("0");
			labelService.insert(label);
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("新增label失败" + e);
		}
		return returnResult;

	}

	
	
	
	/**
	 * 修改label
	 * @param label
	 * @return
	 */
	@RequestMapping(value = "updateLabel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateLabel(TLabel label) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			labelService.updateBySQL("UPDATE t_label SET name='" + label.getName() + "', status="+label.getStatus()+" WHERE id=" + label.getId());
			returnResult.setStatus(ReturnCodeType.SUCCESS);
		} catch (Exception e) {
			logger.error("修改label失败" + e);
		}
		return returnResult;
	}
	
	
	/**
	 * 根据id获取label
	 * @param label
	 * @return
	 */
	@RequestMapping(value = "getLabelById", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getLabelById(Integer id) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(labelService.selectByPrimaryKey(id));
		} catch (Exception e) {
			logger.error("根据id获取label失败" + e);
		}
		return returnResult;
	}
	/**
	 * 分页获取label
	 * @return
	 */
	@RequestMapping(value = "getLabelListByPage", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getLabelListByPage(PageVO page,String Status) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuffer sql = new StringBuffer("SELECT * FROM t_label WHERE 1=1");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) AS total FROM t_label WHERE 1=1");
			
		    if(StringUtils.isNotBlank(Status)){
		    	sql.append(" AND status="+Status);
		    	countSql.append(" AND status="+Status);
		    }
		   
			
			List<Map<String, Object>> results = labelService.selectPageBySQL(sql.toString(), page.getPage() - 1,
					page.getRows());
			if (!results.isEmpty() && results != null) {
				int total =Integer.valueOf(labelService.selectBySQL(countSql.toString()).get(0).get("total").toString());
				int rows = page.getRows();
				rows = rows == 0 ? 10 : rows;
				resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
				resultMap.put("page", page.getPage());
				resultMap.put("records", total);
				resultMap.put("rows", results);
				returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
			}
		}catch (Exception e) {
			logger.error("分页获取label失败" + e);
		}
		return returnResult;
	}
	/**
	 * 获取所有启用的label
	 * @param label
	 * @return
	 */
	@RequestMapping(value = "getAllLabel", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getAllLabel(){
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(labelService.getAllLabel());
		} catch (Exception e) {
			logger.error("获取所有启用的label" + e);
		}
		return returnResult;
	}
}
