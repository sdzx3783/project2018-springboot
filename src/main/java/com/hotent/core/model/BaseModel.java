package com.hotent.core.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {
	protected Long createBy;
	protected Date createtime;
	protected Date updatetime;
	protected Long updateBy;

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
}