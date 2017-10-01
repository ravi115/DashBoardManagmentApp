/*
 * (c) copyright, 2017 
 */
package com.dbm.app.query;

/**
 * This class provides row query. which has to builded based on the user input request.
 * @author ravi ranjan kumar
 * @since 2017 - 10- 01
 *
 */
public class HQLQuery {

	private HQLQuery(){

	}
	private static final String TABLE_NAME = " Users "; //in hibernate the table name should be model class name.
	//HQL Query
	//public static final String WHERE_CLOUSE_ALL_COLUMN = "where id = :id and user_name= :name and user_city= :city";
	public static final String ID_COLUMN =   " id = :id ";
	public static final String NAME_COLUMN = " user_name= :name ";
	public static final String CITY_COLUMN = " user_city= :city ";
	public static final String HQL_SELECT_QUERY = "FROM" + TABLE_NAME;
	public static final String HQL_DELETE_QUERY = "DELETE FROM" + TABLE_NAME; 

}
