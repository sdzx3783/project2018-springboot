package com.hotent.core.jms;

import com.hotent.core.util.AppConfigUtil;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.pool.PooledSession;
import org.apache.activemq.web.MessageQuery;
import org.apache.activemq.web.QueueBrowseQuery;
import org.apache.activemq.web.RemoteJMXBrokerFacade;
import org.apache.activemq.web.SessionPool;
import org.apache.activemq.web.config.SystemPropertiesConfiguration;

public class QueuesService {
	@Resource
	protected ConnectionFactory simpleJmsConnectionFactory;
	private RemoteJMXBrokerFacade brokerFacade;
	private String jmxHostIp = "127.0.0.1";
	private String jmxPort = "1099";

	private RemoteJMXBrokerFacade getBrokerFacade() {
		if (this.brokerFacade == null) {
			this.brokerFacade = new RemoteJMXBrokerFacade();
			this.jmxHostIp = AppConfigUtil.get("jms.ip");
			this.jmxPort = AppConfigUtil.get("jmx.port", this.jmxPort);
			System.setProperty("webconsole.jmx.url",
					"service:jmx:rmi:///jndi/rmi://" + this.jmxHostIp + ":" + this.jmxPort + "/jmxrmi");
			SystemPropertiesConfiguration configuration = new SystemPropertiesConfiguration();
			this.brokerFacade.setConfiguration(configuration);
		}

		return this.brokerFacade;
	}

	private QueueViewMBean getQueue(String name) throws Exception {
		QueueViewMBean qvb = null;
		qvb = (QueueViewMBean) this.getDestinationByName(this.getQueues(), name);
		return qvb;
	}

	private DestinationViewMBean getDestinationByName(Collection<? extends DestinationViewMBean> collection,
			String name) {
		Iterator iter = collection.iterator();

		DestinationViewMBean destinationViewMBean;
		do {
			if (!iter.hasNext()) {
				return null;
			}

			destinationViewMBean = (DestinationViewMBean) iter.next();
		} while (!name.equals(destinationViewMBean.getName()));

		return destinationViewMBean;
	}

	private SessionPool getSessionPool() throws Exception {
		SessionPool sp = new SessionPool();
		sp.setConnectionFactory(this.simpleJmsConnectionFactory);
		sp.setConnection(sp.getConnection());
		Session session = sp.borrowSession();
		ActiveMQSession am = null;
		if (session instanceof ActiveMQSession) {
			am = (ActiveMQSession) ((ActiveMQSession) session);
		}

		if (session instanceof PooledSession) {
			PooledSession pooledSession = (PooledSession) session;
			am = pooledSession.getInternalSession();
		}

		sp.returnSession(am);
		return sp;
	}

	public Collection<QueueViewMBean> getQueues() throws Exception {
		return this.getBrokerFacade().getQueues();
	}

	public void purgeDestination(String JMSDestination) throws Exception {
		this.getBrokerFacade().purgeQueue(ActiveMQDestination.createDestination(JMSDestination, 1));
	}

	public void removeMessage(String JMSDestination, String messageId) throws Exception {
		QueueViewMBean queueView = this.getQueue(JMSDestination);
		queueView.removeMessage(messageId);
	}

	public QueueBrowseQuery getQueueBrowseQuery(String JMSDestination) throws Exception {
		QueueBrowseQuery qbq = new QueueBrowseQuery(this.getBrokerFacade(), this.getSessionPool());
		qbq.setJMSDestination(JMSDestination);
		return qbq;
	}

	public MessageQuery getMessageQuery(String id, String JMSDestination) throws Exception {
		MessageQuery mq = new MessageQuery(this.getBrokerFacade(), this.getSessionPool());
		mq.setJMSDestination(JMSDestination);
		mq.setId(id);
		return mq;
	}

	public void removeDestinationQueue(String JMSDestination) throws Exception {
		this.getBrokerFacade().getBrokerAdmin().removeQueue(JMSDestination);
	}
}