package com.hotent.makshi.service.userinfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.userinfo.UserInfomationDao;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysUser;



@Service
public class UserInfomationService extends BaseService<UserInfomation>
{
	@Resource
	private UserInfomationDao dao;
	
	
	@Resource
	SysUserDao sysUserDao;

	
	public UserInfomationService()
	{
	}
	
	@Override
	protected IEntityDao<UserInfomation,Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 更新sysuer以及userinfomation
	 * @param sysUser
	 * @param userInfomation
	 */
	@Transactional
	public void updateSysuserAndUserInfomation(SysUser sysUser,UserInfomation userInfomation){
		sysUserDao.updateCommon(sysUser);
		if(userInfomation!=null){
			dao.update("updateSjdh", userInfomation);
		}
	}
	
	
	/**
	 * 删除数据 包含相应子表记录
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			dao.delById(id);	
		}	
	}
	
	/**
	 * 添加数据 
	 * @param userInfomation
	 * @throws Exception
	 */
	public void addAll(UserInfomation userInfomation) throws Exception{
		super.add(userInfomation);
	}
	
	/**
	 * 更新数据
	 * @param userInfomation
	 * @throws Exception
	 */
	public void updateAll(UserInfomation userInfomation) throws Exception{
		super.update(userInfomation);
	}
	
	/**
	 * 添加子表记录
	 * @param userInfomation
	 * @throws Exception
	 */
	
	
	
	
	/**
	 * 保存 用户信息档案表 信息
	 * @param userInfomation
	 */

	public void save(UserInfomation userInfomation) throws Exception{
		Long id=userInfomation.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			userInfomation.setId(id);
			this.addAll(userInfomation);
		}
		else{
		    this.updateAll(userInfomation);
		}
	}
	
	
	
	public UserInfomation getUserInfomationByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<UserInfomation> userInfomationList = dao.getBySqlKey("getUserDataByYgbh", params);
		if(userInfomationList!=null && userInfomationList.size()>0){
			UserInfomation userInfo = userInfomationList.get(0);
			return userInfo;
		}
		
		return null;
	}
	
	
	
	public UserInfomation getUserInfomationById(Long id){
		UserInfomation userInfomation = dao.getById(id);
		return userInfomation;
	}
	
	public UserInfomation getUserInfomationByAccount(String account){
		List<UserInfomation> userInfomationList = dao.getBySqlKey("getByAccount", account);
		if(userInfomationList!=null && userInfomationList.size()>0){
			UserInfomation userInfo = userInfomationList.get(0);
			return userInfo;
		}
		return null;
	}
	/**
	 * 更新手机短号 以及紧急联系人和紧急联系电话
	 * 
	 * @param userInfomation
	 */
	public void updateSjdh(UserInfomation userInfomation) {
		dao.update("updateSjdh", userInfomation);
	}
	
	
}
