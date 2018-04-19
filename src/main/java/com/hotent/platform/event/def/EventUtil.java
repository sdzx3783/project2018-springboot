package com.hotent.platform.event.def;

import com.hotent.core.util.AppUtil;
import com.hotent.platform.model.system.SysUser;

/**
 * <pre>
 * 描述：为事件生成的工具类
 * 构建组：bpm33
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2015-3-10-下午5:41:55
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class EventUtil {
	
	/**
	 *  发布用户事件。
	 * @param userId	用户ID
	 * @param action	动作。
	 */
	public static void publishUserEvent(Long userId,int action,SysUser user){
		UserEvent ev=new UserEvent(user.getUserId());
		ev.setAction(action);
		ev.setUser(user);
		AppUtil.publishEvent(ev);
	}
}
