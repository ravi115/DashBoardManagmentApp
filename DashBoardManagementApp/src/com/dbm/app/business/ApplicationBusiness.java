/*
 * (c) copyright, 2017 
 */
package com.dbm.app.business;

import org.apache.log4j.Logger;

import com.dbm.app.entity.Users;
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.hibernate.HibernateEngine;
import com.dbm.app.query.HQLQuery;
import com.dbm.app.querybuilder.HQLQueryBuilder;
import com.dbm.app.response.ApplicationResponse;

/**
 * This is business class of application which responsible to first get the query and invoke required hibernate 
 * API to perform operation.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
public class ApplicationBusiness {

	private Logger LOG = Logger.getLogger(getClass());

	/**
	 * This method is responsible to retrieves all the user data.
	 * 
	 * @param objAppRequest user request object.
	 * @return application response with all user details.
	 * @throws ApplicationException throws exception while performing retrieval operation.
	 */
	public ApplicationResponse getResult(Users objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			final HQLQueryBuilder objHQLQueryBuilder = new HQLQueryBuilder(objAppRequest, HQLQuery.HQL_SELECT_QUERY);
			final String query = objHQLQueryBuilder.query();
			objApplicationResponse = new HibernateEngine().getAllUsers(objAppRequest, query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}

		return objApplicationResponse;
	}



	/**
	 * This method is responsible to add user data.
	 * 
	 * @param objAppRequest user request object.
	 * @return application response with all user details.
	 * @throws ApplicationException throws exception while performing insertion operation.
	 */
	public ApplicationResponse insertQuery(Users objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			objApplicationResponse = new HibernateEngine().insertUsers(objAppRequest);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}
		return objApplicationResponse;
	}


	/**
	 * This method is responsible to update user data.
	 * 
	 * @param objAppRequest user request object.
	 * @return application response with all user details.
	 * @throws ApplicationException throws exception while performing updation operation.
	 */
	public ApplicationResponse updateQuery(Users objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			objApplicationResponse = new HibernateEngine().updateUsers(objAppRequest);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}
		return objApplicationResponse;
	}


	/**
	 * This method is responsible to delete user data.
	 * 
	 * @param objAppRequest user request object.
	 * @return application response with all user details.
	 * @throws ApplicationException throws exception while performing deletion operation.
	 */
	public ApplicationResponse deleteQuery(Users objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		if(null != objAppRequest && objAppRequest.getId() > 0 ) {
			try{
				final HQLQueryBuilder objHQLQueryBuilder = new HQLQueryBuilder(objAppRequest, HQLQuery.HQL_DELETE_QUERY);
				final String query = objHQLQueryBuilder.query();
				objApplicationResponse = new HibernateEngine().deleteUsers(objAppRequest, query);
			}catch(Exception e) {
				LOG.debug("caught Exception : " + e.getLocalizedMessage());
				throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
						ApplicationError.INTERNAL_ERROR.getErrorMessage());
			}	
		}else{
			throw new ApplicationException(ApplicationError.INVALID_INPUT.getErrorCode(), ApplicationError.INVALID_INPUT.getErrorMessage());
		}
		return objApplicationResponse;
	}


}
