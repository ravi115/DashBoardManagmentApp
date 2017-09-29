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
import com.dbm.app.error.ApplicationError;
import com.dbm.app.exception.ApplicationException;
import com.dbm.app.response.ApplicationResponse;
import com.dbm.app.resquest.ApplicationRequest;

@Path("/resources")
public class ApplicationService {

	private Logger LOG  = Logger.getLogger(getClass());

	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResource(@QueryParam("id") int id,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("city") String city) {

		ApplicationResponse objApplicationResponse = new ApplicationResponse();
		LOG.info("request started for : id : " + id + "& name : " + name + " & city : " + city );
		try{
			final ApplicationRequest objAppRequest = new ApplicationRequest();
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

	@Path("/insertdata")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResource(ApplicationRequest objApplicationRequest ) {
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
			final ApplicationRequest objAppRequest = new ApplicationRequest();
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
			final ApplicationRequest objAppRequest = new ApplicationRequest();
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
	public static void main(String[] args) {
		ApplicationService obj = new ApplicationService();
		Response r = obj.getResource(3, "monu", "patna");
		System.out.println(r.getEntity());
	}
}
