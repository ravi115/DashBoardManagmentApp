/*
 * (c) copyright 2017 
 */
package com.dbm.app.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;

/**
 * This class provides the session factory object to process hibernate operation.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 */
public class HibernateUtil {

	private static Logger LOG = Logger.getLogger(HibernateUtil.class);
	private static SessionFactory sessionFactory = null;

	/**
	 * creates session factory by loading hibernate.cfg.xml file.
	 * 
	 * @return session factory object
	 * @throws ApplicationException throws Hibernate Exception while creating the session factory.
	 */
	private static SessionFactory buildSessionFactory() throws ApplicationException {
		LOG.info("Build session factory");
		try{
			return new Configuration().configure().buildSessionFactory();
		}catch(Exception e) {
			LOG.debug("caught exception in building hibernate session factory : " + e);
			e.printStackTrace();
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}
	}

	/**
	 * responsible to call required method to get session factory object.
	 * @return session factory object.
	 * @throws ApplicationException throws Hibernate Exception while creating the session factory.
	 */
	public static SessionFactory getCurrentSessionFactory() throws ApplicationException {
		if(null == sessionFactory ) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}
	
	/**
	 * close the session factory object, meand destroy the session factory object.
	 */
	public static void shutdown() {
		if(null != sessionFactory ) {
			sessionFactory.close();
			LOG.info("successfully closed the sessionFactory");
		}
	}
}
