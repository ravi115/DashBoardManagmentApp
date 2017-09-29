package com.dbm.app.query;

/**
 * 
 * @author raviranjan
 *
 */
public class Query {

	private Query(){
		
	}
	private static final String TABLE_NAME = " user ";
	public static final String SELECT_QUERY = "select * from " + TABLE_NAME + "where $condition";
	public static final String UPDATE_QUERY = "update " + TABLE_NAME + "set $column where $condition" ;
	public static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where $condition" ;
	public static final String INSERT_QUERY = "insert into " + TABLE_NAME + "(id,name,city)" + "values(" + "$values" + ")";  
	
}
