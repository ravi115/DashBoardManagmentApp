package com.dbm.app.querybuilder;

import org.apache.log4j.Logger;

import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.query.Query;
import com.dbm.app.resquest.ApplicationRequest;

public class QueryBuilder implements IQueryBuilder {

	private Logger LOG = Logger.getLogger(getClass());
	private ApplicationRequest objAppReq;

	public QueryBuilder(final ApplicationRequest objAppReq) {
		this.objAppReq = objAppReq;
	}
	/**
	 * 
	 */
	@Override
	public String selectQuery() throws ApplicationException {

		String query = null;
		String selectAllColumn = null;
		try{
			final StringBuilder objCondition = new StringBuilder();
			if(null != objAppReq) {

				final String queryTemplate = Query.SELECT_QUERY;
				if((objAppReq.getId()>0 ) ) {
					objCondition.append("id=").append(String.valueOf(objAppReq.getId()));

				}else if(null != objAppReq.getName() && objAppReq.getName().length() > 0 ) {
					objCondition.append("name=").append("\"").append(objAppReq.getName()).append("\"");

				}else if(null != objAppReq.getCity() && objAppReq.getCity().length() > 0 ) {
					objCondition.append("city=").append("\"").append(objAppReq.getCity()).append("\"");
				}else{
					selectAllColumn = queryTemplate.replace("where $condition", "");
				}
				query = ((null == selectAllColumn) ?queryTemplate.replace("$condition", objCondition.toString()) : selectAllColumn);
			}
			LOG.info("select query - " + query);
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.Query_Build_ERROR.getErrorCode(), ApplicationError.Query_Build_ERROR.getErrorMessage());
		}
		return query;
	}
	/**
	 * 
	 */
	@Override
	public String insertQuery() throws ApplicationException{

		String query = null;
		try{
			if((null != objAppReq) && (objAppReq.getId() > 0 ) && (null != objAppReq.getName()) && (objAppReq.getName().length() > 0) && 
					(null != objAppReq.getCity()) && (objAppReq.getCity().length() > 0) ) {

				final String queryTemplate = Query.INSERT_QUERY;
				final StringBuilder objBuilder = new StringBuilder();
				objBuilder.append(objAppReq.getId()).append(",").append("\"").append(objAppReq.getName()).append("\"")
				.append(",").append("\"").append(objAppReq.getCity()).append("\"");

				query = queryTemplate.replace("$values", objBuilder.toString());
				LOG.info("insert query - " + query);
			}else{
				throw new ApplicationException(ApplicationError.INVALID_INPUT.getErrorCode(), ApplicationError.INVALID_INPUT.getErrorMessage());
			}
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.Query_Build_ERROR.getErrorCode(), ApplicationError.Query_Build_ERROR.getErrorMessage());
		}
		return query;
	}

	@Override
	public String updateQuery() throws ApplicationException {

		String query = null;
		try{
			if(null != objAppReq && objAppReq.getId() > 0 ) {
				String queryTemplate = Query.UPDATE_QUERY;
				StringBuilder objColumn = new StringBuilder();
				objColumn.append("id=").append(String.valueOf(objAppReq.getId()));
				queryTemplate = queryTemplate.replace("$condition",objColumn.toString());
				objColumn.setLength(0);
				if(null != objAppReq.getName() && objAppReq.getName().length() > 0 ) {
					objColumn.append("name=").append("\"").append(objAppReq.getName()).append("\"");
				}
				if(null != objAppReq.getCity() && objAppReq.getCity().length() > 0 ) {
					if(objColumn.length()> 0 ) {
						objColumn.append(" , ").append("city=").append("\"").append(objAppReq.getCity()).append("\"");
					}else{
						objColumn.append("city=").append("\"").append(objAppReq.getCity()).append("\"");
					}
				}
				query = queryTemplate.replace("$column", objColumn.toString());
				LOG.info("update query is : " + query);
			}else{
				throw new ApplicationException(ApplicationError.Query_Build_ERROR.getErrorCode(), ApplicationError.Query_Build_ERROR.getErrorMessage());
			}
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.Query_Build_ERROR.getErrorCode(), ApplicationError.Query_Build_ERROR.getErrorMessage());
		}
		return query;
	}

	@Override
	public String deleteQuery() throws ApplicationException {

		String query = null;
		try{
			if(null != objAppReq ) {

				final String queryTemplate = Query.DELETE_QUERY;
				final StringBuilder objConditon = new StringBuilder();
				if(objAppReq.getId() > 0 ) {
					objConditon.append("id=").append(objAppReq.getId());
				}
				if(null != objAppReq.getName() && objAppReq.getName().length() > 0 ) {
					if(objConditon.length() > 0 ) {
						objConditon.append(" ").append(" and ").append("name=").append("\"").append(objAppReq.getName()).append("\"");
					}else{
						objConditon.append("name=").append("\"").append(objAppReq.getName()).append("\"");
					}
				}
				if(null != objAppReq.getCity() && objAppReq.getCity().length() > 0 ) {
					if(objConditon.length() > 0 ) {
						objConditon.append(" ").append(" and ").append("city=").append("\"").append(objAppReq.getCity()).append("\"");
					}else{
						objConditon.append("city=").append("\"").append(objAppReq.getCity()).append("\"");
					}
				}
				query = queryTemplate.replace("$condition", objConditon.toString());
				LOG.info("delete query is : " + query);
			}
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.Query_Build_ERROR.getErrorCode(), ApplicationError.Query_Build_ERROR.getErrorMessage());
		}
		return query;
	}

	public static void main(String[] args) throws ApplicationException {
		ApplicationRequest obj = new ApplicationRequest();
		obj.setId(0);
		obj.setName("mon");
		obj.setCity("mum");

		IQueryBuilder q = new QueryBuilder(obj);
		System.out.println(q.updateQuery());
	}
}
