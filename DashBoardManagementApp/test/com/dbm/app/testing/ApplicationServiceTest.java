package com.dbm.app.testing;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.dbm.app.entity.Users;
import com.dbm.app.services.ApplicationService;

public class ApplicationServiceTest {

	@Test
	public void testCreateResource() throws JSONException {
		Users user = new Users();
		user.setId(1);
		user.setName("ravi");
		user.setDescription("this is my first entry");
		
		ApplicationService service = new ApplicationService();
		Response response = service.createResource(user);
		/*String responseData = (String) response.getEntity();
		JSONObject json = null;
		if(null != responseData ) {
			 json = new JSONObject(responseData);
		}
		assertEquals(1,json.getInt("id"));*/
	}
	
	@Test
	public void testGetResource() {
		
		ApplicationService service = new ApplicationService();
		Response response = service.getResource(1, "", "");
		
	}
}
