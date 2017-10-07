/*
 * (c) copyright, 2017 
 */
package com.dbm.app.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
					(null != objUser.getDescription()) && (objUser.getDescription().length() > 0) ) {
				//add user created date.
				objUser.setDateCreated(getCurrentTimeStampe());
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
					//add date to user
					user.setDateUpdated(getCurrentTimeStampe());
					if(null != objUser.getName() && objUser.getName().length() > 0 ) {
						user.setName(objUser.getName());
					}
					if(null != objUser.getDescription() && objUser.getDescription().length() > 0 ) {
						user.setDescription(objUser.getDescription());
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
				boolean bIsAnyColumn = false;
				if(null != query ){
					if(objUser.getId() > 0 ) {
						bIsAnyColumn = true;
						query.setParameter("id", objUser.getId());
					}
					if(null != objUser.getName() && objUser.getName().length() > 0 ) {
						bIsAnyColumn = true;
						query.setParameter("name", objUser.getName());
					}
					if(null != objUser.getDescription() && objUser.getDescription().length() > 0 ) {
						bIsAnyColumn = true;
						query.setParameter("description", objUser.getDescription());
					}
					if(bIsAnyColumn) {
						nRowDeleted = query.executeUpdate();
						objResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
						objResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
						LOG.info("deleted rows : " + nRowDeleted);
					}else{
						objResponse.setErrorCode(ApplicationError.INVALID_INPUT.getErrorCode());
						objResponse.setErrorMessage(ApplicationError.INVALID_INPUT.getErrorMessage());
						LOG.info("can not delete all the rows : ");

					}
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
					if(null != objUser.getDescription() && objUser.getDescription().length() > 0 ) {
						query.setParameter("description", objUser.getDescription());
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
	private String getCurrentTimeStampe() throws ApplicationException {

		String currentDate = null;
		try{
			Calendar cal = new GregorianCalendar();
			TimeZone timeZone = TimeZone.getTimeZone("UTC");
			cal.setTimeZone(timeZone);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YYYY");
			simpleDateFormat.setTimeZone(timeZone);
			currentDate = simpleDateFormat.format(cal.getTime());

		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.DATE_ERROR.getErrorCode(), 
					ApplicationError.DATE_ERROR.getErrorMessage());
		}
		return currentDate;
	}
}
