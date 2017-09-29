/*
 * (c) copyright 2017. 
 */
package com.dbm.app.configurations;

import com.dbm.app.exception.ApplicationException;

/**
 * This class is responsible to provide root element name to read database
 * property from configuration file.
 * 
 * @author ravi ranjan kumar
 * @since 2017-08-28
 */
public class DBConfig extends Config {

	/**
	 * Default constructor to initialize the XML configuration file.
	 * 
	 * @throws ApplicationException
	 *             throws any checked or unchecked exception while loading XML
	 *             configuration file.
	 * 
	 */
	public DBConfig() throws ApplicationException {
		super();
		setSettingName("database");
	}
}
