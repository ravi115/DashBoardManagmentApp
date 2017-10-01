/*
 * (c) copyright, 2017 
 */
package com.dbm.app.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.dbm.app.entity.Users;
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.response.ApplicationResponse;

/**
 * This class is responsible to perform CRUD operation.
 * @author ravi ranjan kumar
 *
 */
@SuppressWarnings("deprecation")
public class HibernateEngine extends HibernateProcessorAbstract {

	private Logger LOG = Logger.getLogger(getClass());

	public HibernateEngine() throws ApplicationException {
		super();
	}

	/**
	 * insert all user into database.
	 */
	@Override
	public ApplicationResponse insertUsers(final Users objUser) throws ApplicationException {
		LOG.info("insert user triggred:");
		final ApplicationResponse objResponse = new ApplicationResponse();
		try{
			if((null != objUser) && (null != objUser.getName()) && (objUser.getName().length() > 0) && 
					(null != objUser.getCity()) && (objUser.getCity().length() > 0) ) {
				if( null != session.save(objUser) ) {
					objResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
					objResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
				}
				transaction.commit();
			}
		}catch(Exception e) {
			LOG.debug("Caught Exception while saving user details " + e.getMessage());
			e.printStackTrace();
			if(null != transaction) {
				transaction.rollback();
			}
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}finally{
			session.close();
			//shutdown();
		}
		return objResponse;
	}

	/**
	 * update existing user data into database.
	 */
	@Override
	public ApplicationResponse updateUsers(final Users objUser)throws ApplicationException  {
		LOG.info("update user triggered:");
		final ApplicationResponse objResponse = new ApplicationResponse();
		try{
			if(null != objUser && objUser.getId() > 0 ) {
				Users user = (Users) session.get(Users.class, objUser.getId());
				if(null != user ) {
					if(null != objUser.getName() && objUser.getName().length() > 0 ) {
						user.setName(objUser.getName());
					}
					if(null != objUser.getCity() && objUser.getCity().length() > 0 ) {
						user.setCity(objUser.getCity());
					}
				}
				if(null != session.save(user) ) {
					objResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
					objResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
				}
				transaction.commit();
			}
		}catch(Exception e) {
			LOG.debug("Caught Exception while updating user details " + e.getMessage());
			e.printStackTrace();
			if(null != transaction) {
				transaction.rollback();
			}
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}finally{
			session.close();
			//shutdown();
		}
		return objResponse;
	}

	/**
	 * delete the user from database.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ApplicationResponse deleteUsers(final Users objUser, final String hqlQuery) throws ApplicationException {
		LOG.info("delete triggered:");
		final ApplicationResponse objResponse = new ApplicationResponse();
		try{
			if(null != objUser  && null != hqlQuery && hqlQuery.length() > 0) {
				Query query = session.createQuery(hqlQuery);
				int nRowDeleted;
				if(null != query ){
					if(objUser.getId() > 0 ) {
						query.setParameter("id", objUser.getId());
					}
					if(null != objUser.getName() && objUser.getName().length() > 0 ) {
						query.setParameter("name", objUser.getName());
					}
					if(null != objUser.getCity() && objUser.getCity().length() > 0 ) {
						query.setParameter("city", objUser.getCity());
					}
					nRowDeleted = query.executeUpdate();
					objResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
					objResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
					LOG.info("deleted rows : " + nRowDeleted);
				}else {
					LOG.debug("Failed create the query");
				}
			}
			transaction.commit();
		}catch(Exception e) {
			LOG.debug("Caught Exception while deleting user details " + e.getMessage());
			if(null != transaction) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}finally{
			session.close();
			//shutdown();
		}
		return objResponse;
	}

	/**
	 * retrieves all the users from database.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ApplicationResponse getAllUsers(final Users objUser, final String hqlQuery) throws ApplicationException {
		LOG.info("List out all the user");
		final ApplicationResponse objResponse = new ApplicationResponse();
		try{
			if(null !=objUser && null != hqlQuery && hqlQuery.length() > 0 ) {
				Query query = session.createQuery(hqlQuery);
				if(null != query ) {
					if(objUser.getId() > 0 ) {
						query.setParameter("id", objUser.getId());
					}
					if(null != objUser.getName() && objUser.getName().length() > 0 ) {
						query.setParameter("name", objUser.getName());
					}
					if(null != objUser.getCity() && objUser.getCity().length() > 0 ) {
						query.setParameter("city", objUser.getCity());
					}
					List<Users> userList = query.list(); 
					objResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
					objResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
					objResponse.setResult(userList);
				}else {
					LOG.debug("Failed to create the query");
				}
			}
			transaction.commit();
		}catch(Exception e) {
			LOG.debug("Caught Exception while retrieving all user details " + e.getMessage());
			if(null != transaction) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException(ApplicationError.Hibernate_Exception.getErrorCode(), ApplicationError.Hibernate_Exception.getErrorMessage());
		}finally{
			session.close();
			//shutdown();
		}
		return objResponse;
	}
}
