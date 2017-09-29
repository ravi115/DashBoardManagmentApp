package com.dbm.app.backend;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.response.ApplicationResponse;

/**
 * 
 * @author raviranjan
 *
 */
public class ExecuteQuery extends DBUtil {

	public ExecuteQuery() throws ApplicationException {
		super();
		// TODO Auto-generated constructor stub
	}

	private Logger LOG = Logger.getLogger(getClass());

	public ApplicationResponse getResult(final String query ) throws ApplicationException {

		final ApplicationResponse objAppResponse = new ApplicationResponse();
		try{
			if(null != query && query.length() > 0 ) {
				boolean bIsResult = false;
				final ResultSet rs = statement.executeQuery(query);
				final List<Map<String, String>> result = new ArrayList<>();
				while(rs.next() ) {
					Map<String, String> resultMap = new HashMap<>();
					resultMap.put("id", String.valueOf(rs.getInt("id")));
					resultMap.put("name", rs.getString("name"));
					resultMap.put("city", rs.getString("city"));
					result.add(resultMap);
					bIsResult = true;
				}
				if(bIsResult) {
					objAppResponse.setResult(result);
					objAppResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
					objAppResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
				}else {
					throw new ApplicationException(ApplicationError.INTERNAL_SERVER_ERROR.getErrorCode(),
							ApplicationError.INTERNAL_SERVER_ERROR.getErrorMessage());
				}
			}
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.INTERNAL_SERVER_ERROR.getErrorCode(),
					ApplicationError.INTERNAL_SERVER_ERROR.getErrorMessage());
		}
		return objAppResponse;
	}
	public ApplicationResponse insertQuery(final String query) throws ApplicationException {

		final ApplicationResponse objApplicationResponse = new ApplicationResponse();
		try{
			if(null != query && query.length() > 0 ) {
				statement.executeUpdate(query);
				objApplicationResponse.setErrorCode(ApplicationError.SUCCESS.getErrorCode());
				objApplicationResponse.setErrorMessage(ApplicationError.SUCCESS.getErrorMessage());
			}
		}catch(Exception e) {
			LOG.debug("caught Exception : " + e.getLocalizedMessage());
			throw new ApplicationException(ApplicationError.DATA_EXISTS.getErrorCode(), ApplicationError.DATA_EXISTS.getErrorMessage());
		}
		return objApplicationResponse;
	}
}
