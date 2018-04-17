package com.hotent.core.engine;

import com.hotent.core.engine.GroovyBinding;
import com.hotent.core.engine.IScript;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import groovy.lang.GroovyShell;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class GroovyScriptEngine implements BeanPostProcessor {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);
	private GroovyBinding binding = new GroovyBinding();

	public void execute(String script, Map<String, Object> vars) {
		this.executeObject(script, vars);
	}

	private void setParameters(GroovyShell shell, Map<String, Object> vars) {
		if (vars != null) {
			Set set = vars.entrySet();
			Iterator it = set.iterator();

			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				shell.setVariable((String) entry.getKey(), entry.getValue());
			}

		}
	}

	public boolean executeBoolean(String script, Map<String, Object> vars) {
		Boolean rtn = (Boolean) this.executeObject(script, vars);
		return rtn.booleanValue();
	}

	public String executeString(String script, Map<String, Object> vars) {
		String str = (String) this.executeObject(script, vars);
		return str;
	}

	public int executeInt(String script, Map<String, Object> vars) {
		Integer rtn = (Integer) this.executeObject(script, vars);
		return rtn.intValue();
	}

	public float executeFloat(String script, Map<String, Object> vars) {
		Float rtn = (Float) this.executeObject(script, vars);
		return rtn.floatValue();
	}

	public Object executeObject(String script, Map<String, Object> vars) {
		this.logger.debug("执行:" + script);
		GroovyShell shell = new GroovyShell(this.binding);
		this.setParameters(shell, vars);
		script = script.replace("&apos;", "\'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<")
				.replace("&nuot;", "\n").replace("&amp;", "&");
		Object rtn = shell.evaluate(script);
		this.binding.clearVariables();
		return rtn;
	}

	public Object getVariable(String key) {
		return this.binding.getVariable(key);
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		boolean rtn = BeanUtils.isInherit(bean.getClass(), BaseService.class);
		boolean isImplScript = BeanUtils.isInherit(bean.getClass(), IScript.class);
		if (rtn || isImplScript) {
			this.binding.setProperty(beanName, bean);
		}

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}