package cc.gzvtc.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.admin.service.IAdminService;
import cc.gzvtc.model.TAdmin;
import cc.gzvtc.vo.ReturnCodeType;
import cc.gzvtc.vo.ReturnResult;


/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Controller
@RequestMapping("/admin")
@Scope("prototype")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "adminService")
	private IAdminService adminService;

	/**
	 * 管理员登录
	 * @param admin
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult login(TAdmin admin, HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			admin = adminService.login(admin);
			if (admin != null) {
				admin.setPassword(null);
				session.setAttribute("admin", admin);
				returnResult.setStatus(ReturnCodeType.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("登录失败：" + e);

		}
		return returnResult;

	}

	/**
	 * 从session获取管理员信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getAdminInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult getAdminInfo(HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		TAdmin admin = (TAdmin) session.getAttribute("admin");
		if (admin != null) {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(admin);
		} else {
			logger.info("获取管理员信息失败：管理员未登录");
		}
		return returnResult;
	}
	
	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="logout", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult logout(HttpSession session) {
		session.invalidate();
		return returnResult.setStatus(ReturnCodeType.SUCCESS);
	}
	
}
