package cc.gzvtc.user.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import cc.gzvtc.model.TUser;
import cc.gzvtc.user.service.IUserService;
import cc.gzvtc.vo.PageVO;
import cc.gzvtc.vo.ReturnCodeType;
import cc.gzvtc.vo.ReturnResult;


/**
 * 
 * @author hzc 2017年2月12日
 *
 */
@Controller
@RequestMapping("/user")
@Scope("prototype")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private ReturnResult returnResult = new ReturnResult();

	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * 登录
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public ReturnResult login(TUser user, HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);

		try {
			user = userService.login(user);
			if (user != null) {
				user.setPassword(null);
				session.setAttribute("user", user);
				returnResult.setStatus(ReturnCodeType.SUCCESS);

			}
		} catch (Exception e) {
			logger.error("登录失败" + e);

		}
		return returnResult;

	}

	/**
	 * 从session中获取用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping("getUserInfo")
	@ResponseBody
	public ReturnResult getUserInfo(HttpSession session) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		TUser user = (TUser) session.getAttribute("user");
		if (user != null) {
			returnResult.setStatus(ReturnCodeType.SUCCESS).setData(user);
		} else {
			logger.info("获取用户信息失败：用户未登录");
		}
		return returnResult;
	}

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public ReturnResult register(TUser user) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			if (userService.checkUserByName(user.getName())) {
				if (userService.register(user) >= 0) {
					returnResult.setStatus(ReturnCodeType.SUCCESS);
				}
			}

		} catch (Exception e) {
			logger.error("注册失败" + e);
		}

		return returnResult;

	}

	/**
	 * 检测用户名是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkUserName")
	@ResponseBody
	public ReturnResult checkUserName(String name) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			if (userService.checkUserByName(name)) {
				returnResult.setStatus(ReturnCodeType.SUCCESS);
			}

		} catch (Exception e) {
			logger.error("检测用户名是否存在失败：" + e);
		}

		return returnResult;

	}

	/**
	 * 管理员查看所有的用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping("getAllUserInfo")
	@ResponseBody
	public ReturnResult getAllUserInfo(HttpSession session,PageVO page,String name) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
			if (session.getAttribute("admin") != null) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				StringBuffer sql = new StringBuffer("SELECT DISTINCT * FROM t_user WHERE 1=1");
				if(StringUtils.isNotBlank(name)){
					sql.append(" AND name="+name);
				}
				
				List<Map<String, Object>> results = userService.selectPageBySQL(sql.toString(), page.getPage() - 1,
						page.getRows());
				if (!results.isEmpty() && results != null) {
					int total = userService.selectCount(new TUser());
					int rows = page.getRows();
					rows = rows == 0 ? 10 : rows;
					resultMap.put("total", (total % rows != 0 ? (total / rows + 1) : (total / rows)));
					resultMap.put("page", page.getPage());
					resultMap.put("records", total);
					resultMap.put("rows", results);
					returnResult.setStatus(ReturnCodeType.SUCCESS).setData(resultMap);
				}
			} else {
				logger.info("获取所有的用户信息失败：管理员未登录");
			}
		} catch (Exception e) {
			logger.error("获取所有的用户信息失败：" + e);

		}
		return returnResult;
	}

	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	@ResponseBody
	public ReturnResult logout(HttpSession session) {
		session.invalidate();
		return returnResult.setStatus(ReturnCodeType.SUCCESS);
	}

	/**
	 * 修改密码
	 * @param oldPassword
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePassword")
	@ResponseBody
	public ReturnResult updatePassword(TUser user) {
		returnResult.setStatus(ReturnCodeType.FAILURE);
		try {
				if (userService.updatePassword(user) > 0) {
					returnResult.setStatus(ReturnCodeType.SUCCESS);
				}
		} catch (Exception e) {
			logger.error("修改密码失败：" + e);
		}
		return returnResult;
	}
	
}
