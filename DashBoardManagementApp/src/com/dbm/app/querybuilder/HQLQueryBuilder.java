/*
 * (c) copyright, 2017 
 */
package com.dbm.app.querybuilder;

import org.apache.log4j.Logger;

import com.dbm.app.constant.ApplicationConstant;
import com.dbm.app.entity.Users;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.query.HQLQuery;

/**
 * This class is responsible to build HQL query for delete and select operation performed by Hibernate.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
public class HQLQueryBuilder {

	private final Logger LOG = Logger.getLogger(getClass());
	private Users user;
	private String queryTemplate;

	public HQLQueryBuilder(final Users user, final String query) {
		this.user = user;
		this.queryTemplate = query;
	}

	/**
	 * builds the query.
	 * @return query.
	 * @throws ApplicationException throws any kind of exception while building query.
	 */
	public String query() throws ApplicationException {
		LOG.info("prepare Query");
		final StringBuilder query = new StringBuilder(queryTemplate);
		try{
			boolean bIsIdPresent = false;
			boolean bIsNamePresent = false;

			if(null != user && null != queryTemplate && queryTemplate.length() > 0 ) {
				if(user.getId() > 0 ) {
					query.append(ApplicationConstant.SPACE).append(ApplicationConstant.WHERE).append(ApplicationConstant.SPACE)
							.append(HQLQuery.ID_COLUMN);
					bIsIdPresent = true;
				}
				if(null != user.getName() && user.getName().length() > 0 ) {
					if(bIsIdPresent) {
						query.append(ApplicationConstant.SPACE).append(ApplicationConstant.AND).append(ApplicationConstant.SPACE)
								.append(HQLQuery.NAME_COLUMN);
					}else {
						query.append(ApplicationConstant.SPACE).append(ApplicationConstant.WHERE).append(ApplicationConstant.SPACE)
								.append(HQLQuery.NAME_COLUMN);
					}
					bIsNamePresent = true;
				}
				if(null != user.getCity() && user.getCity().length() > 0 ) {

					if(bIsIdPresent) {
						query.append(ApplicationConstant.SPACE).append(ApplicationConstant.AND).append(ApplicationConstant.SPACE)
								.append(HQLQuery.CITY_COLUMN);
					}else if(bIsNamePresent) {
						query.append(ApplicationConstant.SPACE).append(ApplicationConstant.AND).append(ApplicationConstant.SPACE)
								.append(HQLQuery.CITY_COLUMN);
					}else {
						query.append(ApplicationConstant.SPACE).append(ApplicationConstant.WHERE).append(ApplicationConstant.SPACE)
								.append(HQLQuery.CITY_COLUMN);
					}
				}
			}
			LOG.info("query is : " + query.toString());
		}catch(Exception e) {
			LOG.debug("caught exception while building query" + e.getMessage());
		}
		return query.toString();
	}
}
