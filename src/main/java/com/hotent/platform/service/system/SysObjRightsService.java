package com.hotent.platform.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.SysObjRightsDao;
import com.hotent.platform.model.system.SysObjRights;
import com.hotent.platform.service.util.ServiceUtil;

/**
 * <pre>
 * 对象功能:对象权限表 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-04-09 17:19:22
 * </pre>
 */
@Service
public class SysObjRightsService extends BaseService<SysObjRights> {
	@Resource
	private SysObjRightsDao dao;
	
	@Resource
	private CurrentUserService currentUserService;

	public SysObjRightsService() {
	}

	@Override
	protected IEntityDao<SysObjRights, Long> getEntityDao() {
		return dao;
	}

	public void deleteByObjTypeAndObjectId(String objType, String objectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objType", objType);
		map.put("objectId", objectId);
		dao.delBySqlKey("deleteByObjTypeAndObjectId", map);
	}

	/**
	 * 保存 对象权限表 信息
	 * 
	 * @param sysObjRights
	 */
	public void save(SysObjRights sysObjRights) {
		Long id = sysObjRights.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			sysObjRights.setId(id);
			this.add(sysObjRights);
		} else {
			this.update(sysObjRights);
		}
	}

	public List<SysObjRights> getByObjTypeAndObjectId(String objType, String objectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objType", objType);
		map.put("objectId", objectId);
		return dao.getBySqlKey("getObject", map);
	}
	
	public List<SysObjRights> getHashRights(String objType) {
		
		Map<String, List<Long>> userMap= currentUserService.getUserRelation(ServiceUtil.getCurrentUser());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objType", objType);
		
		map.put("userMap", userMap);
		
		return dao.getBySqlKey("getHashRights", map);
	}
	
	
}
