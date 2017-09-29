package com.dbm.app.querybuilder;

import com.dbm.app.exception.ApplicationException;

public interface IQueryBuilder {

	public String selectQuery() throws ApplicationException;
	public String insertQuery() throws ApplicationException;
	public String updateQuery() throws ApplicationException;
	public String deleteQuery() throws ApplicationException;

}
