package com.dbm.app.business;

import org.apache.log4j.Logger;

import com.dbm.app.backend.ExecuteQuery;
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.querybuilder.IQueryBuilder;
import com.dbm.app.querybuilder.QueryBuilder;
import com.dbm.app.response.ApplicationResponse;
import com.dbm.app.resquest.ApplicationRequest;

public class ApplicationBusiness {

	private Logger LOG = Logger.getLogger(getClass());

	public ApplicationResponse getResult(ApplicationRequest objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			IQueryBuilder objQueryBuilder = new QueryBuilder(objAppRequest);
			final String query = objQueryBuilder.selectQuery();
			objApplicationResponse = new ExecuteQuery().getResult(query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}

		return objApplicationResponse;
	}

	public ApplicationResponse insertQuery(ApplicationRequest objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			IQueryBuilder objQueryBuilder = new QueryBuilder(objAppRequest);
			final String query = objQueryBuilder.insertQuery();
			objApplicationResponse = new ExecuteQuery().insertQuery(query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}
		return objApplicationResponse;
	}
	public ApplicationResponse updateQuery(ApplicationRequest objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			IQueryBuilder objQueryBuilder = new QueryBuilder(objAppRequest);
			final String query = objQueryBuilder.updateQuery();
			objApplicationResponse = new ExecuteQuery().insertQuery(query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}
		return objApplicationResponse;
	}
	public ApplicationResponse deleteQuery(ApplicationRequest objAppRequest ) throws ApplicationException{

		ApplicationResponse objApplicationResponse= null;
		try{
			IQueryBuilder objQueryBuilder = new QueryBuilder(objAppRequest);
			final String query = objQueryBuilder.deleteQuery();
			objApplicationResponse = new ExecuteQuery().insertQuery(query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
					ApplicationError.INTERNAL_ERROR.getErrorMessage());
		}
		return objApplicationResponse;
	}
}
