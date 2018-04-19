/**
 * 监听器，
 */
package com.hotent.platform.event.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.platform.event.def.UserEvent;
import com.hotent.platform.model.system.SysPropertyConstants;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

@Service
public class UserEventListener implements ApplicationListener<UserEvent> {
@Resource 
SysUserService userService;

	@Override
	public void onApplicationEvent(UserEvent ev) {
		boolean isSupportWx = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT,false);
		int action=ev.getAction();
		Long userId=ev.getUserId();
		SysUser user = ev.getUser();
		//用户添加事件
		if(action ==UserEvent.ACTION_ADD) {
			//do someThing
			
		}else if(action ==UserEvent.ACTION_UPD){
			removeFromCache(userId);
		}else{
			removeFromCache(userId);
			
		}
	}

	private void removeFromCache(Long userId){
		
		ICache iCache=(ICache)AppUtil.getBean(ICache.class);
		
		String companyKey=ContextUtil.getCompanyKey(userId);
		
		
		String orgKey=ContextUtil.getOrgKey(userId);
		String positionKey=ContextUtil.getPositionKey(userId);
		
		//删除缓存数据
		iCache.delByKey(companyKey);
		iCache.delByKey(orgKey);
		iCache.delByKey(positionKey);
		
	}
	
}
