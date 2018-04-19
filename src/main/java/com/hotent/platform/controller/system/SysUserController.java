package com.hotent.platform.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.encrypt.EncryptUtil;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.ldap.model.LdapUser;
import com.hotent.core.log.SysAuditThreadLocalHolder;
import com.hotent.core.model.OnlineUser;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.event.def.EventUtil;
import com.hotent.platform.event.def.UserEvent;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.OrgAuth;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.PwdStrategy;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysPropertyConstants;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.model.system.UserPosition;
import com.hotent.platform.model.system.UserRole;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.IdentityService;
import com.hotent.platform.service.system.OrgAuthService;
import com.hotent.platform.service.system.PositionService;
import com.hotent.platform.service.system.PwdStrategyService;
import com.hotent.platform.service.system.SubSystemService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserOrgService;
import com.hotent.platform.service.system.SysUserParamService;
import com.hotent.platform.service.system.SysUserService;
import com.hotent.platform.service.system.UserPositionService;
import com.hotent.platform.service.system.UserRoleService;
import com.hotent.platform.service.system.UserSyncService;
import com.hotent.platform.service.system.UserUnderService;
import com.hotent.platform.service.system.impl.OrgServiceImpl;
import com.hotent.platform.service.util.MessageUtil;
import com.hotent.platform.web.listener.UserSessionListener;

import net.sf.json.JSONObject;

/**
 * 对象功能:用户表 控制器类 开发公司:广州宏天软件有限公司 开发人员:csx 创建时间:2011-11-28 10:17:09
 */
@Controller
@RequestMapping("/platform/system/sysUser/")
@Action(ownermodel = SysAuditModelType.USER_MANAGEMENT)
public class SysUserController extends BaseController {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private PositionService positionService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource	
	private SysUserService sysUserService;
	@Resource
	private DemensionService demensionService;
	@Resource
	private SubSystemService subSystemService;
	@Resource
	private SysUserParamService sysUserParamService;
	@Resource
	private SysUserOrgService sysUserOrgService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserPositionService userPositionService;
	@Resource
	private UserSyncService userSyncService;
	@Resource
	private OrgAuthService orgAuthService;
	@Resource
	private OrgServiceImpl orgServiceImpl;
	@Resource
	private UserUnderService userUnderService;
	@Resource
	private PwdStrategyService pwdStrategyService;
	@Resource
	private Properties configproperties;
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private IdentityService identityService;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	private final String defaultUserImage = "/commons/image/default_image_male.jpg";

	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看用户表分页列表", execOrder = ActionExecOrder.AFTER, detail = "查看用户表分页列表", exectype = "管理日志")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		boolean isSupportWeixin = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT, false);
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		Long currentUserId = ContextUtil.getCurrentUserId();
		Long chooseOrgId = (Long) request.getSession().getAttribute("chooseOrgId");
		List<SysUser> list = sysUserService.getUsersByQuery(queryFilter);
		for (SysUser user : list) {
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(user.getUserId());
			Position position = positionService.getById(user.getPosId1());
			// Job job = jobService.getById(user.getJobId());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				user.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				user.setJobName(position.getPosName());
			}
		}
		boolean superAdmin = ContextUtil.isSuperAdmin();
		List<SysOrg> sysOrgs = sysOrgService.getOrgsAll();
		Iterator<SysOrg> iterator = sysOrgs.iterator();
		while(iterator.hasNext()){
			SysOrg next = iterator.next();
			if (next.getOrgName().equals("深水咨询")) {
				iterator.remove();
			}
		}
		
		ModelAndView mv = this.getAutoView().addObject("sysUserList", list).addObject("userId", userId).addObject("isSupportWeixin", isSupportWeixin).addObject("superAdmin", superAdmin) // 超级管理员
				.addObject("sysOrgs", sysOrgs); // 部门人事管理员
		return mv;
	}

	@RequestMapping("getSysOrgByOrgId")
	@ResponseBody
	public SysOrg getOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String selectOrgId = request.getParameter("orgId");
		Long orgId = null;
		if (!StringUtil.isEmpty(selectOrgId)) {
			orgId = Long.valueOf(selectOrgId);
		}
		SysOrg sysOrg = sysOrgService.getById(orgId);
		return sysOrg;
	}

	/**
	 * 删除用户表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除用户表", execOrder = ActionExecOrder.BEFORE, detail = "删除用户表" + "<#list StringUtils.split(userId,\",\") as item>"
			+ "<#assign entity=sysUserService.getById(Long.valueOf(item))/>" + "【${entity.fullname}】" + "</#list>", exectype = "管理日志")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String preUrl = RequestUtil.getPrePage(request);
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "userId");
			delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除用户成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.Fail, "删除用户失败:" + e.getMessage());
			log.error("错误信息", e);
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	private void delByIds(Long[] lAryId) {
		if (BeanUtils.isEmpty(lAryId))
			return;
		for (Long id : lAryId) {
			SysUser user = sysUserService.getById(id);
			EventUtil.publishUserEvent(id, UserEvent.ACTION_DEL, user);
			sysUserService.delById(id);
			sysUserOrgService.delByUserId(id);
			userPositionService.delByUserId(id);
			userRoleService.delByUserId(id);
			orgAuthService.delByUserId(id);
		}
	}


	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		ModelAndView mv = getAutoView();
		mv.addObject("action", "global");
		List<Demension> demensionList = demensionService.getAll();
		Long userId = RequestUtil.getLong(request, "userId");
		// 获取所属组织角色
		Map<SysOrg, List<SysRole>> roles = getOrgRoles(userId);
		mv.addObject("sysOrgRoles", roles);
		mv.addObject("userId", userId);
		return getEditMv(request, mv).addObject("demensionList", demensionList);
	}

	/**
	 * 普通用户修改个人信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editCommon")
	public ModelAndView editCommon(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		ModelAndView modal = getAutoView();
		SysUser sysUser = sysUserService.getById(ContextUtil.getCurrentUserId());
		if (sysUser != null) {
			UserInfomation infomation = userInfomationService.getUserInfomationByUid(sysUser.getUserId());
			if (infomation != null) {
				sysUser.setSjdh(infomation.getSjdh());
				modal.addObject("infomation", infomation);
			}
		}
		String pictureLoad = defaultUserImage;
		if (sysUser != null) {
			if (StringUtil.isNotEmpty(sysUser.getPicture())) {
				pictureLoad = sysUser.getPicture();
			}
		}
		return modal.addObject("sysUser", sysUser).addObject("returnUrl", returnUrl).addObject("pictureLoad", pictureLoad);
	}

	/**
	 * 部门个人信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editDepartment")
	public ModelAndView editDepartment(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getAutoView();
		long userId = RequestUtil.getLong(request, "userId");
		String returnUrl = RequestUtil.getPrePage(request);
		// UserInfomation infomation = userInfomationService.getUserInfomationByUid(userId);
		// 获取组织岗位选择和上级配置
		// SysUser sysUser = sysUserService.getById(ContextUtil.getCurrentUserId());
		// 获取组织部门id
		SysOrg sysOrg = sysOrgService.getPrimaryOrgByUserId(userId);
		Long orgId = sysOrg.getOrgId();
		Long departmentOrgId = orgId;
		if (sysOrg != null && sysOrg.getOrgType() != null && sysOrg.getOrgType() > 3) {
			departmentOrgId = sysOrg.getOrgSupId();
		}
		List<UserPosition> userPosList = userPositionService.getByUserId(userId);
		List<SysUser> userUnders = userUnderService.getMyLeaders(userId);
		mv.addObject("action", "global");
		List<Demension> demensionList = demensionService.getAll();
		return mv.addObject("userPosList", userPosList).addObject("userUnders", userUnders).addObject("orgId", orgId).addObject("departmentOrgId", departmentOrgId)
				.addObject("demensionList", demensionList).addObject("returnUrl", returnUrl).addObject("userId", userId);
	}

	@RequestMapping("editGrade")
	public ModelAndView editGrade(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 获取当前用户所能管理的组
		long userId = ContextUtil.getCurrentUserId();
		List<OrgAuth> orgAuthList = orgAuthService.getByUserId(userId);

		mv.setViewName("/platform/system/sysUserEdit.jsp");
		mv.addObject("action", "grade");
		mv.addObject("orgAuthList", orgAuthList);
		return getEditMv(request, mv);
	}

	public ModelAndView getEditMv(HttpServletRequest request, ModelAndView mv) {
		boolean isSupportWeixin = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT, false);
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		int bySelf = RequestUtil.getInt(request, "bySelf");
		SysUser sysUser = null;
		if (userId != 0) {
			sysUser = sysUserService.getById(userId);
			List<UserRole> roleList = userRoleService.getByUserId(userId);
			List<UserPosition> userPosList = userPositionService.getByUserId(userId);
			UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);

			List<String> orgIdList = new ArrayList<String>();
			String orgIds = "";
			for (UserPosition up : userPosList) {
				orgIdList.add(up.getOrgId().toString());
			}
			orgIds = StringUtil.getArrayAsString(orgIdList);

			mv.addObject("roleList", roleList).addObject("userPosList", userPosList).addObject("orgIds", orgIds).addObject("userInfomation", userInfomation);
			
		} else {// 新增用户界面
			sysUser = new SysUser();
			String nextId = identityService.preview("员工编号");// 新员工编号生成
			sysUser.setAccount(nextId);
			// 密码策略的初始化密码
			sysUser.setPassword(pwdStrategyService.getUsingInitPwd());
		}
		String pictureLoad = defaultUserImage;
		if (sysUser != null) {
			if (StringUtil.isNotEmpty(sysUser.getPicture())) {
				pictureLoad = sysUser.getPicture();
			}
		}

		return mv.addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl).addObject("pictureLoad", pictureLoad).addObject("bySelf", bySelf)
				.addObject("isSupportWeixin", isSupportWeixin);
	}

	@RequestMapping("modifyPwdView")
	public ModelAndView modifyPwdView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		SysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId);
	}

	@RequestMapping("modifyPwd")
	@Action(description = "修改密码", execOrder = ActionExecOrder.AFTER, detail = "<#assign entity=sysUserService.getById(Long.valueOf(userId))/>"
			+ "【${entity.fullname}】修改密码<#if isSuccess> 成功<#else>失败</#if>", exectype = "管理日志")
	public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String primitivePassword = RequestUtil.getString(request, "primitivePassword");
		String enPassword = EncryptUtil.encryptSha256(primitivePassword);
		String newPassword = RequestUtil.getString(request, "newPassword");
		Long userId = RequestUtil.getLong(request, "userId");
		SysUser sysUser = sysUserService.getById(userId);
		String password = sysUser.getPassword();
		boolean issuccess = false;
		if (StringUtil.isEmpty(newPassword) || StringUtil.isEmpty(primitivePassword)) {
			writeResultMessage(response.getWriter(), "输入的密码不能为空", ResultMessage.Fail);
		} else if (!enPassword.equals(password)) {
			writeResultMessage(response.getWriter(), "你输入的原始密码不正确", ResultMessage.Fail);
		} else if (primitivePassword.equals(newPassword)) {
			writeResultMessage(response.getWriter(), "你修改的密码和原始密码相同", ResultMessage.Fail);
		} else {
			try {
				sysUserService.updPwd(userId, newPassword);
				writeResultMessage(response.getWriter(), "修改密码成功", ResultMessage.Success);
				issuccess = true;
			} catch (Exception ex) {
				String str = MessageUtil.getMessage();
				if (StringUtil.isNotEmpty(str)) {
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "修改密码失败:" + str);
					response.getWriter().print(resultMessage);
				} else {
					String message = ExceptionUtil.getExceptionMessage(ex);
					ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
					response.getWriter().print(resultMessage);
				}
			}
		}
		SysAuditThreadLocalHolder.putParamerter("isSuccess", issuccess);
	}

	@RequestMapping("resetPwdView")
	public ModelAndView resetPwdView(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		if (userId == 0) {
			userId = ContextUtil.getCurrentUserId();
		}
		SysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("resetPwd")
	@Action(description = "重置密码", execOrder = ActionExecOrder.AFTER, detail = "<#assign entity=sysUserService.getById(Long.valueOf(userId))/>"
			+ "【${entity.fullname}】重置密码<#if isSuccess> 成功<#else>失败</#if>", exectype = "管理日志")
	public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String password = RequestUtil.getString(request, "password");
		Long userId = RequestUtil.getLong(request, "userId");
		boolean issuccess = true;
		try {
			// 检验新密码是否通过策略
			SysUser sysUser = sysUserService.getById(userId);
			JSONObject json = pwdStrategyService.checkUser(sysUser, password);
			short status = Short.parseShort(json.getString("status"));
			if (status != PwdStrategy.Status.SUCCESS) {
				String msg = json.getString("msg");
				MessageUtil.addMsg(msg);
				throw new Exception(msg);
			}
			sysUserService.updPwd(userId, password);
			writeResultMessage(response.getWriter(), "重置密码成功!", ResultMessage.Success);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "重置密码失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
			issuccess = false;
		}
		SysAuditThreadLocalHolder.putParamerter("isSuccess", issuccess);
	}

	@RequestMapping("commonResetPwd")
	@Action(description = "重置密码", execOrder = ActionExecOrder.AFTER, detail = "<#assign entity=sysUserService.getByAccount(Long.valueOf(userId))/>"
			+ "【${entity.fullname}】重置密码<#if isSuccess> 成功<#else>失败</#if>", exectype = "管理日志")
	public void commonResetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();

		boolean issuccess = true;
		String account = RequestUtil.getString(request, "account");
		String oldPassword = RequestUtil.getString(request, "oldPassword");
		String newPassword = RequestUtil.getString(request, "newPassword");
		SysUser sysUser = sysUserService.getByAccount(account);
		if (sysUser == null) {
			result.put("msg", "账号不存在");
			result.put("state", "-1");
			issuccess = false;
		} else if (!sysUser.getPassword().equals(EncryptUtil.encryptSha256(oldPassword))) {
			result.put("msg", "旧密码错误");
			result.put("state", "-2");
			issuccess = false;
		} else {
			// 先验证密码是否符合密码规则
			JSONObject json = pwdStrategyService.checkUser(sysUser, newPassword);
			short status = Short.parseShort(json.getString("status"));
			if (status != PwdStrategy.Status.SUCCESS) {
				String msg = json.getString("msg");
				result.put("msg", msg);
				result.put("state", "-3");
			} else {
				// 更新密码
				sysUserService.updPwd(sysUser.getUserId(), newPassword);
				result.put("msg", "修改密码成功,跳转回登录页面");
				result.put("state", "0");
			}
		}
		response.getWriter().print(result);
		SysAuditThreadLocalHolder.putParamerter("isSuccess", issuccess);
	}

	@RequestMapping("editStatusView")
	public ModelAndView editStatusView(HttpServletRequest request) throws Exception {
		String returnUrl = RequestUtil.getPrePage(request);
		Long userId = RequestUtil.getLong(request, "userId");
		SysUser sysUser = sysUserService.getById(userId);
		return getAutoView().addObject("sysUser", sysUser).addObject("userId", userId).addObject("returnUrl", returnUrl);
	}

	@RequestMapping("editStatus")
	@Action(description = "设置用户状态", execOrder = ActionExecOrder.AFTER, detail = "设置用户状态", exectype = "管理日志")
	public void editStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long userId = RequestUtil.getLong(request, "userId");
		int isLock = RequestUtil.getInt(request, "isLock");
		int status = RequestUtil.getInt(request, "status");
		try {
			sysUserService.updStatus(userId, (short) status, (short) isLock);
			writeResultMessage(response.getWriter(), "修改用户状态成功!", ResultMessage.Success);
		} catch (Exception ex) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, "修改用户状态失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				ResultMessage resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 取得用户表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		return getView(request, response, mv, 0);
	}

	@RequestMapping("detail")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		return getView(request, response, mv, 0);
	}

	/**
	 * 取得用户表明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByUserId")
	public ModelAndView getByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/platform/system/sysUserGet.jsp");
		mv = getView(request, response, mv, 1);
		return mv;
	}

	/**
	 * 获取单独的经手合同
	 */
	@RequestMapping("getContractByUserId")
	public ModelAndView getContractByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/platform/system/sysUserContract.jsp");
		mv = getView(request, response, mv, 1);
		return mv;
	}

	/**
	 * 取得用户表明细
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@Action(description = "查看用户表明细", execOrder = ActionExecOrder.AFTER, detail = "查看用户表明细", exectype = "管理日志")
	public ModelAndView getView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		long userId = RequestUtil.getLong(request, "userId");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		// 查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		// 获取用户基本信息
		SysUser sysUser = sysUserService.getById(userId);
		String pictureLoad = defaultUserImage;
		if (sysUser != null) {
			if (StringUtil.isNotEmpty(sysUser.getPicture())) {
				pictureLoad = sysUser.getPicture();
			}
		}
		// 获取所属组织角色
		Map<SysOrg, List<SysRole>> roles = getOrgRoles(userId);
		// 获取用户角色
		List<UserRole> roleList = userRoleService.getByUserId(userId);
		// 获取用户所属岗位
		List<UserPosition> posList = userPositionService.getByUserId(userId);
		// 获取所属组织
		List<UserPosition> userPosList = userPositionService.getOrgListByUserId(userId);
		UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);

		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("sysUser", sysUser).addObject("roleList", roleList).addObject("posList", posList).addObject("orgList", userPosList).addObject("pictureLoad", pictureLoad)
				.addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType)
				.addObject("sysOrgRoles", roles).addObject("userInfomation", userInfomation);
			
	}


	@RequestMapping("levaeByUserId")
	public void levaeByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long id = RequestUtil.getLong(request, "userId");
			SysUser sysUser = sysUserService.getById(id);
			sysUser.setUserStatus("离职");
			sysUser.setResignationDate(new Date());
			sysUser.setIsLock((short) 1);
			sysUserService.update(sysUser);
			message = new ResultMessage(ResultMessage.Success, "离职成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "离职失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}



	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = this.getAutoView();

		List<Demension> demensionList = demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List<SubSystem> subSystemList = subSystemService.getAll();
		mv.addObject("subSystemList", subSystemList);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		mv.addObject("isSingle", isSingle);
		handelUserSoruce(mv);
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		String currentOrgId = "-1";
		if (currentOrg != null) {
			String orgPath = currentOrg.getPath();
			if (StringUtils.isNotEmpty(orgPath)) {
				String[] split = orgPath.trim().split("\\.");
				if (split.length >= 0) {
					currentOrgId = split[1];
					if (split.length >= 3 && StringUtils.isNotEmpty(split[2])) {
						currentOrgId = split[2];
					}
				}
			}
		}
		return mv.addObject("currentOrgId", currentOrgId);
	}

	private void handelUserSoruce(ModelAndView mv) {
		boolean isShowPos = PropertyUtil.getBooleanByAlias("userDialog.showPos", true);
		boolean isShowRole = PropertyUtil.getBooleanByAlias("userDialog.showRole", true);
		boolean isShowOnlineUser = PropertyUtil.getBooleanByAlias("userDialog.showOnlineUser", true);

		mv.addObject("isShowPos", isShowPos);
		mv.addObject("isShowRole", isShowRole);
		mv.addObject("isShowOnlineUser", isShowOnlineUser);
	}

	/**
	 * 取得用户表分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("gradeDialog")
	public ModelAndView GradeDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();

		long userId = ContextUtil.getCurrentUserId();
		List<OrgAuth> orgAuthList = orgAuthService.getByUserId(userId);
		mv.addObject("orgAuthList", orgAuthList);

		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		mv.addObject("isSingle", isSingle);
		handelUserSoruce(mv);
		return mv;
	}

	@RequestMapping("flowDialog")
	public ModelAndView flowDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getAutoView();
		List<Demension> demensionList = demensionService.getAll();
		mv.addObject("demensionList", demensionList);
		List<SubSystem> subSystemList = subSystemService.getAll();

		mv.addObject("isSingle", "false");
		mv.addObject("subSystemList", subSystemList);

		handelUserSoruce(mv);
		return mv;
	}

	@RequestMapping("selector")
	@Action(description = "用户选择器", execOrder = ActionExecOrder.AFTER, detail = "用户选择器", exectype = "管理日志")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysUser> list = null;
		ModelAndView result = getAutoView();
		String searchBy = RequestUtil.getString(request, "searchBy");
		String type = RequestUtil.getString(request, "type");
		String script = RequestUtil.getString(request, "script");
		String typeVal = RequestUtil.getString(request, "typeVal");
		int includSub = RequestUtil.getInt(request, "includSub", 0);
		String isSingle = RequestUtil.getString(request, "isSingle", "false");
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		if (SystemConst.SEARCH_BY_ONL.equals(searchBy)) {
			String demId = RequestUtil.getString(request, "path");
			if (demId.equals("-1")) {// 未分配组织的或者没有主组织的用户
				list = sysUserService.getUserNoOrg(queryFilter);
			} else {
				queryFilter.addFilter("isPrimary", 1);
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			}
			list = sysUserService.getOnlineUser(list);
			// 按组织
		} else if (SystemConst.SEARCH_BY_ORG.equals(searchBy)) {
			if (includSub == 0) {
				list = sysUserService.getDistinctUserByOrgId(queryFilter);
			} else {
				list = sysUserService.getDistinctUserByOrgPath(queryFilter);
			}
			// 按岗位
		} else if (SystemConst.SEARCH_BY_POS.equals(searchBy)) {
			if (includSub == 0) {
				list = sysUserService.getDistinctUserByPosId(queryFilter);
			} else {
				list = sysUserService.getDistinctUserByPosPath(queryFilter);
			}
			// 按角色
		} else if (SystemConst.SEARCH_BY_ROL.equals(searchBy)) {
			list = sysUserService.getUserByRoleId(queryFilter);
		} else {
			if ("script".equals(type)) {
				Set<String> uIds = (Set<String>) groovyScriptEngine.executeObject(typeVal, null);
				list = sysUserService.getByIdSet(uIds);
			} else {
				SysOrg sysOrg = (SysOrg) ContextUtil.getCurrentOrg();
				if (StringUtil.isNotEmpty(type) && !"all".equals(typeVal) && BeanUtils.isNotEmpty(sysOrg)) {
					String path = orgServiceImpl.getSysOrgByScope(type, typeVal).getPath();
					queryFilter.addFilter("path", path + "%");
					list = sysUserService.getDistinctUserByOrgPath(queryFilter);
				} else {
					list = sysUserService.getUserByQuery(queryFilter);
				}

			}
		}
		List<SysUser> userList = new ArrayList<SysUser>();
		String orgNames = "";
		// 循环用户
		for (SysUser user : list) {
			// 获取某用户的组织列表字符串（可能多个组织）
			if (user.getUserStatus().equals("离职")) {
				continue;
			}
			orgNames = userPositionService.getOrgnamesByUserId(user.getUserId());
			user.setOrgName(orgNames.toString());
			userList.add(user);
		}

		result.addObject("sysUserList", userList);
		result.addObject("isSingle", isSingle);
		result.addObject("type", type);
		result.addObject("typeVal", typeVal);

		return result;
	}

	/**
	 * 获取在线用户树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getTreeData")
	@ResponseBody
	@Action(description = "用户选择器查询", execOrder = ActionExecOrder.AFTER, detail = "用户选择器查询", exectype = "管理日志")
	public List<OnlineUser> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Long, OnlineUser> onlineUsers = UserSessionListener.getOnLineUsers();
		List<OnlineUser> onlineList = new ArrayList<OnlineUser>();
		for (Long sessionId : onlineUsers.keySet()) {
			OnlineUser onlineUser = new OnlineUser();
			onlineUser = onlineUsers.get(sessionId);
			onlineList.add(onlineUser);
		}
		return onlineList;
	}


	@RequestMapping("getUserListByJobId")
	@ResponseBody
	@Action(description = "根据职务ID取得用户List")
	public List<SysUser> getUserListByJobId(HttpServletRequest request) throws Exception {
		Long jobId = RequestUtil.getLong(request, "jobId");
		List<SysUser> list = sysUserService.getUserListByJobId(jobId);
		return list;
	}

	@RequestMapping("getUserListByPosId")
	@ResponseBody
	@Action(description = "根据岗位ID取得用户List")
	public List<SysUser> getUserListByPosId(HttpServletRequest request) throws Exception {
		Long posId = RequestUtil.getLong(request, "posId");
		List<SysUser> list = sysUserService.getUserListByPosId(posId);
		return list;
	}

	/**
	 * 
	 * 获取所属组织角色
	 * 
	 * @param userId
	 * @return Map&lt;SysOrg,List&lt;SysRole>>
	 */
	public Map<SysOrg, List<SysRole>> getOrgRoles(Long userId) {
		List<SysOrg> sysOrgs = sysOrgService.getOrgsByUserId(userId);
		Map<SysOrg, List<SysRole>> roles = new HashMap<SysOrg, List<SysRole>>();
		if (BeanUtils.isNotEmpty(sysOrgs)) {
			for (SysOrg sysOrg : sysOrgs) {
				Long orgId = sysOrg.getOrgId();
				List<String> roleList = sysRoleService.getOrgRoles(orgId);
				List<SysRole> sysRoles = new ArrayList<SysRole>();
				for (String role : roleList) {
					SysRole sysRole = sysRoleService.getByRoleAlias(role);
					sysRoles.add(sysRole);
					roles.put(sysOrg, sysRoles);
				}
			}
		}
		return roles;
	}

	@RequestMapping("getCurrentUserRoles")
	@ResponseBody
	public boolean getCurrentUserRoles(HttpServletRequest request) throws Exception {
		String roleName = request.getParameter("roleName");
		Long currentUserId = ContextUtil.getCurrentUserId();
		SysRole sysRole = sysRoleService.getByRoleAlias(roleName);
		List<SysUser> sysUserList = sysUserService.getByRoleId(sysRole.getRoleId());
		if (sysUserList == null || sysUserList.size() == 0) {
			return false;
		}
		for (SysUser sysUser : sysUserList) {
			if (sysUser.getUserId().equals(currentUserId)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping("getChargerListByCurrentUser")
	@ResponseBody
	@Action(description = "根据当前用户ID取负责人")
	public List<TaskExecutor> getChargerListByCurrentUser(HttpServletRequest request) throws Exception {
		Long currentUserId = ContextUtil.getCurrentUserId();
		List<TaskExecutor> executors = new ArrayList<>();
		List<SysOrg> orgs = sysOrgService.getOrgsByUserId(currentUserId);
		Long len = 0l;
		Long record = null;
		for (SysOrg sysOrg : orgs) {
			Long orgType = sysOrg.getOrgType();
			if (orgType > len) {
				len = orgType;
				record = sysOrg.getOrgId();
			}
		}
		if (record != 0l) {
			executors = getExecutor(record);
		}
		return executors;
	}

	@RequestMapping("judgeChargerList")
	@ResponseBody
	@Action(description = "判断当前用户是否为部门负责人")
	public Integer judgeChargerList(HttpServletRequest request) throws Exception {
		try {
			Long currentUserId = ContextUtil.getCurrentUserId();
			List<TaskExecutor> list = getChargerListByCurrentUser(request);
			String executeId = null;
			for (TaskExecutor taskExecutor : list) {
				executeId = taskExecutor.getExecuteId();
				if (executeId.equals(currentUserId.toString())) {
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			return 2;
		}
	}

	/**
	 * 获取组织负责人。
	 * 
	 * @param orgId
	 * @return
	 */
	private List<TaskExecutor> getExecutor(Long orgId) {
		List<UserPosition> userPositionList = sysUserOrgService.getChargeByOrgId(orgId);
		if (BeanUtils.isEmpty(userPositionList))
			return null;
		List<TaskExecutor> executors = new ArrayList<TaskExecutor>();
		for (UserPosition userPosition : userPositionList) {
			TaskExecutor taskExecutor = TaskExecutor.getTaskUser(userPosition.getUserId().toString(), userPosition.getUserName());
			executors.add(taskExecutor);
		}
		return executors;
	}

	@RequestMapping("getInfomationsByUserId")
	@ResponseBody
	public Map<String, Object> getInfomationsByUserId() {
		Map<String, Object> result = new HashMap<String, Object>();
		Long userId = ContextUtil.getCurrentUserId();
		SysUser sysUser = sysUserService.getById(userId);
		result.put("sysUser", sysUser);
		UserInfomation userInfomation = userInfomationService.getUserInfomationByUid(userId);
		
		return result;
	}

	@RequestMapping("checkUserNameAndUserId")
	@ResponseBody
	public Map<String, Object> checkUserNameAndUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		if (null != userId && userId.length() > 0) {
			SysUser sysUser = sysUserService.getUserById(Long.valueOf(userId));
			if (null != sysUser) {
				String name = sysUser.getFullname();
				if (name.equals(userName)) {
					result.put("Ok", 1);
					return result;
				}
			}
			result.put("Ok", 0);
			result.put("msg", "未找到该员工,请清除后重新输入或选择");
			return result;
		}
		result.put("Ok", 2);
		return result;
	}

	@RequestMapping("getUserByName")
	@ResponseBody
	public List<Map<String, Object>> getUserByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		QueryFilter queryFilter = new QueryFilter(request);
		String string = RequestUtil.getString(request, "Q_fullname_SL");
		if (StringUtils.isEmpty(string)) {
			return list;
		}
		List<SysUser> all = sysUserService.getAll(queryFilter);
		for (SysUser sysUser : all) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("username", sysUser.getFullname());
			map.put("userid", sysUser.getUserId());
			String orgName = "";
			List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(sysUser.getUserId());
			if (orgsByUserId != null && orgsByUserId.size() > 0) {
				String temp = orgsByUserId.get(0).getOrgPathname();
				if (StringUtils.isNotEmpty(temp)) {
					if ("/深水咨询".equals(temp)) {
						orgName = "深水咨询";
					} else {
						temp = temp.replace("/深水咨询/", "");
					}
					if (temp.startsWith("/")) {
						temp = temp.substring(1);
					}
					orgName = temp;
				}
			}
			map.put("orgName", orgName);
			list.add(map);
		}
		return list;
	}

	@RequestMapping("exportBirthExcel")
	@Action(description = "用户信息统计表")
	public void exportBirthExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> dataList = new ArrayList<>();
		String[] rowName = new String[] { "序号", "账号", "用户姓名", "用户性别", "生日", "在职状态", "年龄", "手机号码" };
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		String[] monbirth = request.getParameterValues("monbirth");
		String[] userstatus = request.getParameterValues("Q_userstatus_S");
		queryFilter.setPageBean(null);
		queryFilter.addFilter("orderField", "account");
		queryFilter.addFilter("orderSeq", "asc");
		queryFilter.addFilter("monbirth", monbirth);
		queryFilter.addFilter("userstatus", userstatus);
		Object orgID = queryFilter.getFilters().get("orgID");
		if (orgID != null) {
			queryFilter.getFilters().remove("orgID");
			SysOrg byId = sysOrgService.getById(Long.valueOf((String) orgID));
			if (byId != null) {
				String path = byId.getPath();
				queryFilter.addFilter("exportOrgPath", path);
				queryFilter.addFilter("exportOrgId", orgID);
			}
		}
		List<SysUser> list = sysUserService.getUsersByQuery(queryFilter);
		for (SysUser user : list) {
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(user.getUserId());
			Position position = positionService.getById(user.getPosId1());
			// Job job = jobService.getById(user.getJobId());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				user.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				user.setJobName(position.getPosName());
			}
			UserInfomation infomation = userInfomationService.getUserInfomationByUid(user.getUserId());
			if (infomation == null) {
				infomation = new UserInfomation();
			}

			// 填充数据
			int count = 0;
			Object[] object = new Object[rowName.length];
			object[++count] = user.getAccount();
			object[++count] = user.getFullname();
			object[++count] = ("1".equals(user.getSex())) ? "男" : "女";
			object[++count] = infomation.getBirthday() == null ? "" : DateUtils.formatDateS(infomation.getBirthday());
			object[++count] = user.getUserStatus();
			// 通过出生日期计算年龄
			Date birthDay = infomation.getBirthday();
			object[++count] = getAge(birthDay);
			object[++count] = user.getMobile();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "用户生日表");
		String filename = "用户生日表" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		BufferedInputStream bis = new BufferedInputStream(export);
		// 输出流
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

	public int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException("年龄不能超过当前日期");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
			if (nowDayOfYear < bornDayOfYear) {
				age -= 1;
			}
		}
		return age;
	}

	@RequestMapping("exportuserinfo")
	@Action(description = "用户信息统计表")
	public void exportContractExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> dataList = new ArrayList<>();
		String[] rowName = new String[] { "序号", "账号", "用户姓名", "用户性别", "电话", "入职时间", "离职时间", "转正时间", "邮箱地址", "手机", "微信", "专业", "职称专业", "职称", "出生日期", "年龄", "婚姻状况", "曾用名", "民族", "籍贯", "文化程度", "参加工作时间",
				"毕业院校", "毕业时间", "身份证号码", "政治面貌", "户籍", "剩余年假", "是否有传染病史", "是否有遗传病史", "社会保险电脑号", "社会登记编号", "月工资总额", "利手", "医疗险一档", "医疗险二挡", "工商险", "失业险", "养老险", "特长爱好", "户籍所在地", "配偶姓名", "配偶省份证号码",
				"配偶居住地", "父母居住地", "通讯地址", "手机短号", "紧急联系人", "交行卡号", "工行卡号", "紧急联系人电话", "QQ号码" };
		QueryFilter queryFilter = new QueryFilter(request, "sysUserItem");
		queryFilter.setPageBean(null);
		Long chooseOrgId = (Long) request.getSession().getAttribute("chooseOrgId");
		queryFilter.addFilter("orderField", "account");
		queryFilter.addFilter("orderSeq", "asc");
		List<SysUser> list = sysUserService.getUsersByQuery(queryFilter);
		for (SysUser user : list) {
			SysOrg org = sysOrgService.getPrimaryOrgByUserId(user.getUserId());
			Position position = positionService.getById(user.getPosId1());
			// Job job = jobService.getById(user.getJobId());
			if (org != null && org.getOrgName() != null) {
				String orgpath = org.getOrgPathname();
				user.setOrgName(orgpath.replaceAll("/深水咨询/", ""));
			}
			if (position != null && position.getPosName() != null) {
				user.setJobName(position.getPosName());
			}
			UserInfomation infomation = userInfomationService.getUserInfomationByUid(user.getUserId());
			if (infomation == null) {
				infomation = new UserInfomation();
			}
			// 填充数据
			int count = 0;
			Object[] object = new Object[rowName.length];
			object[++count] = user.getAccount();
			object[++count] = user.getFullname();
			object[++count] = ("1".equals(user.getSex())) ? "男" : "女";
			object[++count] = user.getPhone();
			object[++count] = user.getEntryDate() == null ? "" : DateUtils.formatDateS(user.getEntryDate());
			object[++count] = user.getPhone();
			object[++count] = user.getResignationDate() == null ? "" : DateUtils.formatDateS(user.getResignationDate());
			object[++count] = user.getFormalDate() == null ? "" : DateUtils.formatDateS(user.getFormalDate());
			object[++count] = user.getEmail();
			object[++count] = user.getMobile();
			object[++count] = user.getWeixinid();
			object[++count] = infomation.getMajor();
			object[++count] = infomation.getPositional_major();
			object[++count] = infomation.getPositional();
			object[++count] = infomation.getBirthday() == null ? "" : DateUtils.formatDateS(infomation.getBirthday());
			object[++count] = getAge(infomation.getBirthday());
			object[++count] = infomation.getMarriage_state();
			object[++count] = infomation.getUsed_name();
			object[++count] = infomation.getNation();
			object[++count] = infomation.getAddress();
			object[++count] = infomation.getEducation();
			object[++count] = infomation.getStart_work_time() == null ? "" : DateUtils.formatDateS(infomation.getStart_work_time());
			object[++count] = infomation.getGraduate_school();
			object[++count] = infomation.getGraduate_time() == null ? "" : DateUtils.formatDateS(infomation.getGraduate_time());
			object[++count] = infomation.getIdentification_id();
			object[++count] = infomation.getPolitical_status();
			object[++count] = infomation.getAddress_type();
			object[++count] = infomation.getYearVacation();
			object[++count] = ("1".equals(infomation.getInfection_history())) ? "是" : "否";
			object[++count] = ("1".equals(infomation.getDisorders_history())) ? "是" : "否";
			object[++count] = infomation.getSocial_security_computer_id();
			object[++count] = infomation.getSocial_security_num();
			object[++count] = infomation.getMonthly_wages();
			object[++count] = infomation.getHandedness();
			object[++count] = ("1".equals(infomation.getMedical_insurance_first())) ? "是" : "否";
			object[++count] = ("1".equals(infomation.getMedical_insurance_second())) ? "是" : "否";
			object[++count] = ("1".equals(infomation.getInjury_insurance())) ? "是" : "否";
			object[++count] = ("1".equals(infomation.getUnemployment_insurance())) ? "是" : "否";
			object[++count] = ("1".equals(infomation.getEndowment_insurance())) ? "是" : "否";
			object[++count] = infomation.getHobby();
			object[++count] = infomation.getHome_address();
			object[++count] = infomation.getSpouse_name();
			object[++count] = infomation.getSpouse_identification_id();
			object[++count] = infomation.getSpouse_address();
			object[++count] = infomation.getParents();
			object[++count] = infomation.getLink_address();
			object[++count] = infomation.getSjdh();
			object[++count] = infomation.getEmergency_link_person();
			object[++count] = infomation.getBOC_id();
			object[++count] = infomation.getICBC_id();
			object[++count] = infomation.getEmergency_link_person_phone();
			object[++count] = infomation.getQQ();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "用户信息详情表");
		String filename = "用户信息详情表" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		BufferedInputStream bis = new BufferedInputStream(export);
		// 输出流
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

	@RequestMapping("isBgsOrg")
	@Action(description = "判断是否为办公室的人员")
	public void isBgsOrg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid = RequestUtil.getString(request, "userid");
		if (StringUtils.isNotEmpty(userid)) {
			List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(Long.valueOf(userid));
			for (SysOrg sysOrg : orgsByUserId) {
				if ("10000007780616".equals(sysOrg.getOrgId().toString())) {
					response.getWriter().write("{\"code\":\"1\"}");
					return;
				}
			}
		}
		response.getWriter().write("{\"code\":\"0\"}");
	}

}
