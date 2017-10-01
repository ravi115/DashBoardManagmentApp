/*
 * (c) copyright, 2017 
 * 
 */
package com.dbm.app.services;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.dbm.app.business.ApplicationBusiness;
import com.dbm.app.entity.Users;
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.response.ApplicationResponse;

/**
 * This class provides RESTFul API implementation to provide CRUD operation.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
@Path("/resources")
public class ApplicationService {

	private Logger LOG  = Logger.getLogger(getClass());

	/**
	 * This method implements GET API of RESTFul web services. this method is  responsible for
	 * values available in database. it can return values based on some specific criteria.
	 *  
	 * @param id serves as primary key in the database for each entry. 
	 * @param name name of the requested user.
	 * @param city location of the requested user.
	 * @return return all possible result.
	 */
	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResource(@QueryParam("id") int id,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("city") String city) {

		ApplicationResponse objApplicationResponse = new ApplicationResponse();
		LOG.info("request started for : id : " + id + "& name : " + name + " & city : " + city );
		try{
			final Users objAppRequest = new Users();
			objAppRequest.setId(id);
			objAppRequest.setName(name);
			objAppRequest.setCity(city);
			objApplicationResponse = new ApplicationBusiness().getResult(objAppRequest);
			if(null == objApplicationResponse ) {
				throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
						ApplicationError.INTERNAL_ERROR.getErrorMessage());
			}
		}catch(ApplicationException e) {
			objApplicationResponse.setErrorCode(e.getErrorCode());
			objApplicationResponse.setErrorMessage(e.getErrorMessage());
			LOG.debug("caught Exception is : " + e.getMessage());
		}
		if(objApplicationResponse.getErrorCode() == 1 ) {
			return Response.ok().status(200).entity(objApplicationResponse).build();
		}
		return Response.ok().status(400).entity(objApplicationResponse).build();
	}

	/**
	 * This method implements POST API of RESTFul web services. this method is responsible to save user 
	 * data into database.
	 * 
	 * @param objApplicationRequest user data in object format.
	 * @return success or any error message in case of failure.
	 */
	@Path("/insertdata")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResource(Users objApplicationRequest ) {
		ApplicationResponse objApplicationResponse = new ApplicationResponse();

		try{
			LOG.info("Request started for id : " + objApplicationRequest.getId() + " & name : " + objApplicationRequest.getName()
			+ " & city :" + objApplicationRequest.getCity());
			objApplicationResponse = new ApplicationBusiness().insertQuery(objApplicationRequest);
			if(null == objApplicationResponse ) {
				throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
						ApplicationError.INTERNAL_ERROR.getErrorMessage());
			}
		}catch(ApplicationException e) {
			objApplicationResponse.setErrorCode(e.getErrorCode());
			objApplicationResponse.setErrorMessage(e.getErrorMessage());
			LOG.debug("caught Exception is : " + e.getMessage());
		}
		if(objApplicationResponse.getErrorCode() == 1 ) {
			return Response.ok().status(200).entity(objApplicationResponse).build();
		}
		return Response.ok().status(400).entity(objApplicationResponse).build();
	}

	/**
	 * This method implements PUT API of RESTFul web services. this method update the existing user name 
	 * based on some certain criteria and user new values.
	 * 
	 * @param id primary key of every user data.
	 * @param name name of the user, he wants to update.
	 * @param city city of the user, he wants to update.
	 * @return success or error message in case of failure.
	 */
	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateResource(@FormParam("id") int id,
			@DefaultValue("") @FormParam("name") String name,
			@DefaultValue("") @FormParam("city") String city) {
		ApplicationResponse objApplicationResponse = new ApplicationResponse();

		try{
			LOG.info("Request started for id : " + id + " & name : " + name
					+ " & city :" + city);
			final Users objAppRequest = new Users();
			objAppRequest.setId(id);
			objAppRequest.setName(name);
			objAppRequest.setCity(city);
			objApplicationResponse = new ApplicationBusiness().updateQuery(objAppRequest);
			if(null == objApplicationResponse ) {
				throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
						ApplicationError.INTERNAL_ERROR.getErrorMessage());
			}
		}catch(ApplicationException e) {
			objApplicationResponse.setErrorCode(e.getErrorCode());
			objApplicationResponse.setErrorMessage(e.getErrorMessage());
			LOG.debug("caught Exception is : " + e.getMessage());
		}
		if(objApplicationResponse.getErrorCode() == 1 ) {
			return Response.ok().status(200).entity(objApplicationResponse).build();
		}
		return Response.ok().status(400).entity(objApplicationResponse).build();
	}

	/**
	 * This method implements DELETE API of RESTFul web services. this method is responsible to delete a user 
	 * details from database for which he queried.
	 *   
	 * @param id primary key of the requested data.
	 * @param name name of the requested user.
	 * @param city city of the requested user.
	 * @return success or error message in case of failure.
	 */
	@Path("/remove")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResource(@FormParam("id") int id,
			@DefaultValue("") @FormParam("name") String name,
			@DefaultValue("") @FormParam("city") String city ) {

		ApplicationResponse objApplicationResponse = new ApplicationResponse();

		try{
			LOG.info("Request started for id : " + id + " & name : " + name
					+ " & city :" + city);
			final Users objAppRequest = new Users();
			objAppRequest.setId(id);
			objAppRequest.setName(name);
			objAppRequest.setCity(city);
			objApplicationResponse = new ApplicationBusiness().deleteQuery(objAppRequest);
			if(null == objApplicationResponse ) {
				throw new ApplicationException(ApplicationError.INTERNAL_ERROR.getErrorCode(), 
						ApplicationError.INTERNAL_ERROR.getErrorMessage());
			}
		}catch(ApplicationException e) {
			objApplicationResponse.setErrorCode(e.getErrorCode());
			objApplicationResponse.setErrorMessage(e.getErrorMessage());
			LOG.debug("caught Exception is : " + e.getMessage());
		}
		if(objApplicationResponse.getErrorCode() == 1 ) {
			return Response.ok().status(200).entity(objApplicationResponse).build();
		}
		return Response.ok().status(400).entity(objApplicationResponse).build();
	}
}
