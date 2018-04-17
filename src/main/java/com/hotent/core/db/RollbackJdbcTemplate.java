package com.hotent.core.db;

import com.hotent.core.db.IRollBack;
import com.hotent.core.db.RollbackJdbcTemplate.1;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.transaction.support.TransactionTemplate;

public class RollbackJdbcTemplate {
	@Resource
	private TransactionTemplate txTemplate;

	public Object executeRollBack(IRollBack rollBack, String script, Map<String, Object> map) {
      return this.txTemplate.execute(new 1(this, rollBack, script, map));
   }
}