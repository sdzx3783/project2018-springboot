package com.hotent.platform.service.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.scheduler.SchedulerService;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.MessageLog;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.Resources;
import com.hotent.platform.model.system.SubSystem;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.system.SysUserOrg;

@Service
public class SysAuditLinkService {

	@Resource
	SysOrgService sysOrgService;
	@Resource
	DemensionService demensionService;
	@Resource
	SysUserService sysUserService;
	@Resource
	SchedulerService schedulerService;

	@Resource
	ReportTemplateService reportTemplateService;
	
	@Resource
	IdentityService identityService;
	
	
	@Resource
	SysWsLogService sysWsLogService;

	@Resource
	SubSystemService subSystemService;
	
	@Resource
	SysTypeKeyService sysTypeKeyService;
	@Resource
	DictionaryService dictionaryService;
	@Resource
	ResourcesService resourcesService;
	@Resource
	SysFileService sysFileService;
	
	@Resource
	SysUserOrgService sysUserOrgService;
	@Resource
	SysParamService sysParamService;
	@Resource
	PositionService positionService;
	
	public String getSysUserLink(long id,String name){
		return "<a href='#' hrefLink='../../system/sysUser/getByUserId.ht?userId="+ id +"' class='SysUserLink' SysUserId='"+id+"'>"+name+"</a>";
	}
	public String getSysUserLink(long id){
		SysUser sysUser = sysUserService.getById(id);
		return getSysUserLink(sysUser);
	}
	
	public String getSysUserLink(String account){
		SysUser sysUser = sysUserService.getByAccount(account);
		return getSysUserLink(sysUser);
	}
	
	public String getSysUserLink(SysUser sysUser){
		return  getSysUserLink(sysUser.getUserId(),sysUser.getFullname());
	}
	/**
	 * 根据accId，获取子系统的超链接
	 * @param wsId
	 * @return
	 */
	public String getSubSystemLink(long subId){
		SubSystem subSystem=subSystemService.getById(subId);
		return "<a href='#' hrefLink='../../system/subSystem/get.ht?id="+subId+"' class='SubSystemLink' SubSystemId='"+subId+"' >"+subSystem.getSysName()+"</a>";
	}
	
	/**
	 * 根据gtypeId，获取系统分类的超链接
	 * @param gtypeId
	 * @return
	 *//*
	public String  getGlobalTypeLink(long gtypeId) {
		GlobalType globalType=globalTypeService.getById(gtypeId);
		return "<a href='#' hrefLink='../../system/globalType/get.ht?typeId="+gtypeId+"' class='GlobalTypeLink' GlobalTypeId='"+gtypeId+"' >"+globalType.getTypeName()+"</a>";
	}
	*/
	/**
	 * 获取子系统资源的超链接
	 * @param resId
	 * @return
	 */
	public String getResourcesLink(long resId){
		Resources resources=resourcesService.getById(resId);
		return "<a href='#' hrefLink='../../system/resources/get.ht?resId="+resId+"' class='ResourcesLink' ResourcesId='"+resId+"' >"+resources.getResName()+"</a>";
	}
	
	/**
	 * 获取附件的超链接
	 * @param fileId
	 * @return
	 */
	public String getSysFileLink(long fileId){
		SysFile sysFile=sysFileService.getById(fileId);
		return "<a href='#' hrefLink='../../system/sysFile/get.ht?fileId="+fileId+"' class='SysFileLink' SysFileId='"+fileId+"' >"+sysFile.getFileName()+"</a>";
	}
	
	
	/**
	 * 向组织添加人员
	 * @param orgId
	 * @param userIds
	 * @return
	 */
	public String  getOrgUserName(long orgId , String userIds) {
		SysOrg sysOrg=sysOrgService.getById(orgId);
		String[] userids=userIds.split(",");
		String userName="";
		for (int i = 0; i < userids.length; i++) {
			SysUser sysUser=sysUserService.getById(Long.valueOf(userids[i]));
			userName+=sysUser.getFullname()+",";
		}
		return "向组织【"+sysOrg.getOrgName()+"】添加人员【"+userName+"】";
	}
	/**
	 * 
	 * @param userPosId
	 * @return
	 */
	public SysUserOrg getByUserPosId(long userPosId){
		SysUserOrg sysUserOrg=sysUserOrgService.getById(userPosId);
		SysOrg sysOrg=sysOrgService.getById(sysUserOrg.getOrgId());
		SysUser sysUser=sysUserService.getById(sysUserOrg.getUserId());
		sysUserOrg.setOrgName(sysOrg.getOrgName());
		sysUserOrg.setUserName(sysUser.getFullname());
		return sysUserOrg;
	}
	
	
	/**
	 * 获取SYS_DEMENSION的超链接
	 * @param demId
	 * @return
	 */
	public String getDemensionLink(long demId) {
		Demension demension=demensionService.getById(demId);
		return "<a href='#' hrefLink='../../system/demension/get.ht?demId="+demId+"' class='DemensionLink' DemensionId='"+demId+"' >"+demension.getDemName()+"</a>";
	}
	
	public String getPositionLink(long posId){
		Position position=positionService.getById(posId);
		return "<a href='#' hrefLink='../../system/position/getByPosId.ht?posId="+posId+"' class='PositionLink' PositionId='"+posId+"' >"+position.getPosName()+"</a>";
	}
	/**
	 * 根据组织ID，取得组织的超连接
	 * @param orgId
	 * @return
	 */
	public  String getSysOrgLink(Long orgId){
		SysOrg sysOrg = sysOrgService.getById(orgId);
		return getSysOrgLink(sysOrg);
		
	}
	
	/**
	 * 根据组织，取得组织的超连接
	 * @param sysOrg
	 * @return
	 */
	public  String getSysOrgLink(SysOrg sysOrg){
		return getSysOrgLink(sysOrg.getOrgId(),sysOrg.getOrgName());
	}
	
	
	/**
	 * 根据角色，取得角色的超连接
	 * @param sysOrg
	 * @return
	 */
	public  String getSysRoleLink(SysRole sysRole){
		return "<a  href='#'  hrefLink='../../system/sysRole/getByRoleId.ht?roleId="+sysRole.getRoleId()+"' class='SysRoleLink' SysRoleId='"+sysRole.getRoleId()+"'>"+sysRole.getRoleName()+"</a>";
	}
	
	
	/**
	 * 根据组织ID、组织名称，取得组织的超连接
	 * @param orgId
	 * @param orgName
	 * @return
	 */
	public  String getSysOrgLink(Long orgId,String orgName){
		return "<a href='#'  hrefLink='../../system/sysOrg/getByLink.ht?orgId="+orgId+"' class='SysOrgLink' SysOrgId='"+orgId+"'>"+orgName+"</a>";
	}
}
