/*
 * (c) copyright, 2017 
 */
package com.dbm.app.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dbm.app.entity.Users;
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.response.ApplicationResponse;
import com.dbm.app.util.HibernateUtil;

/**
 * This class initializes the session and transaction object using SessionFactory Object.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
public abstract class HibernateProcessorAbstract {

	private Logger LOG = Logger.getLogger(getClass());
	protected Session session;
	protected Transaction transaction;

	public HibernateProcessorAbstract() throws ApplicationException {
		init();
	}
	/**
	 * initializes the session and transaction.
	 * 
	 * @throws ApplicationException throws all kind of exception while initializing session and transaction object.
	 */
	private void init() throws ApplicationException {
		LOG.info("initialize session and transaction for current request");
		try{
			session = HibernateUtil.getCurrentSessionFactory().openSession();
			transaction = (Transaction ) session.beginTransaction();

		}catch(Exception e) {
			LOG.debug("Caught Exception while session and transaction " + e.getMessage());
			e.printStackTrace();
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}
	}

	public abstract ApplicationResponse insertUsers(final Users objUser) throws ApplicationException;
	public abstract ApplicationResponse updateUsers(final Users objUser) throws ApplicationException;
	public abstract ApplicationResponse deleteUsers(final Users objUser, String query) throws ApplicationException;
	public abstract ApplicationResponse getAllUsers(final Users objUser, String query) throws ApplicationException;

	/**
	 * responsible to shutdown the session factory object.
	 */
	protected void shutdown() {
		LOG.info("close the session factory");
		if(null != session ) {
			HibernateUtil.shutdown();
			LOG.info("successfully closed session factory");
		}
	}

}
