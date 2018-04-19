package com.hotent.core.bpm.cache;

import com.hotent.core.cache.ICache;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.FileUtil;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

public class ActivitiDefCache implements DeploymentCache<ProcessDefinitionEntity> {
	private ThreadLocal<Map<String, ProcessDefinitionEntity>> processDefinitionCacheLocal = new ThreadLocal();
	@Resource
	ICache iCache;

	public static void clearLocal() {
		ActivitiDefCache cache = (ActivitiDefCache) AppUtil.getBean(ActivitiDefCache.class);
		cache.clearProcessCache();
	}

	public static void clearByDefId(String actDefId) {
		ActivitiDefCache cache = (ActivitiDefCache) AppUtil.getBean(ActivitiDefCache.class);
		cache.clearProcessDefinitionEntity(actDefId);
		cache.clearProcessCache();
	}

	private void clearProcessDefinitionEntity(String defId) {
		this.remove(defId);
		this.processDefinitionCacheLocal.remove();
	}

	private void clearProcessCache() {
		this.processDefinitionCacheLocal.remove();
	}

	private void setThreadLocalDef(ProcessDefinitionEntity processEnt) {
		if (this.processDefinitionCacheLocal.get() == null) {
			HashMap map = new HashMap();
			map.put(processEnt.getId(), processEnt);
			this.processDefinitionCacheLocal.set(map);
		} else {
			Map map1 = (Map) this.processDefinitionCacheLocal.get();
			map1.put(processEnt.getId(), processEnt);
		}

	}

	private ProcessDefinitionEntity getThreadLocalDef(String processDefinitionId) {
		if (this.processDefinitionCacheLocal.get() == null) {
			return null;
		} else {
			Map map = (Map) this.processDefinitionCacheLocal.get();
			return !map.containsKey(processDefinitionId)
					? null
					: (ProcessDefinitionEntity) map.get(processDefinitionId);
		}
	}

	public ProcessDefinitionEntity get(String id) {
		ProcessDefinitionEntity ent = (ProcessDefinitionEntity) this.iCache.getByKey(id);
		if (ent == null) {
			return null;
		} else {
			ProcessDefinitionEntity cloneEnt = null;

			try {
				cloneEnt = (ProcessDefinitionEntity) FileUtil.cloneObject(ent);
			} catch (Exception arg4) {
				arg4.printStackTrace();
			}

			ProcessDefinitionEntity p = this.getThreadLocalDef(id);
			if (p == null) {
				this.setThreadLocalDef(cloneEnt);
			}

			p = this.getThreadLocalDef(id);
			return p;
		}
	}

	public void add(String id, ProcessDefinitionEntity object) {
		this.iCache.add(id, object);
	}

	public void remove(String id) {
		this.iCache.delByKey(id);
	}

	public void clear() {
		this.iCache.clearAll();
	}
}