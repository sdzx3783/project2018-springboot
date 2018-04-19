package com.hotent.core.db;

import com.hotent.core.db.IRollBack;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class RollbackJdbcTemplate {
	@Resource
	private TransactionTemplate txTemplate;

	public Object executeRollBack(final IRollBack rollBack, final String script, final Map<String, Object> map) {
      return this.txTemplate.execute(new TransactionCallback(){
    	  public Object doInTransaction(org.springframework.transaction.TransactionStatus arg0) {
    		  try
    		    {
    		      return rollBack.execute(script, map);
    		    }
    		    catch (Exception ex)
    		    {
    		      throw new RuntimeException(ex.getMessage());
    		    }
    		    finally
    		    {
    		    	arg0.setRollbackOnly();
    		    }
    	  };
      });
   }
}