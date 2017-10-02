var rootURL = "http://localhost:8080/DashBoardManagementApp/rest/resources";
/**
 * all the method inside will get invoked once document is ready.
 */
$(document).ready(function(){
	loadUsers();
	$('#btnSave').bind('click', function(event) {
		saveUser();
		loadUsers();
	});
	$('#btnSearch').bind('click', function(event) {
		var searchData = $('#searchKey').val();
		var condition = $("#elementId :selected").text();
		console.log("user shuold be searched on : " + searchData);
		searchUser(condition, searchData);
	});
	$('#btnUpdate').bind('click', function(event) {
		console.log("update is clicked");
		updateUser();
		loadUsers();
	});
	$('#btnDelete').bind('click', function(event) {
		console.log("delete is clicked");
		deleteUser();
		loadUsers();
	});
});

/**
 * this method constructs request payload for update and delete API call.
 * @param Id
 * @param Name
 * @param City
 * @returns
 */
function requestPayLoad(Id, Name, City) {
	//construct application/x-www-form-urlencoded data.
	var requestData= 'id='+Id;
	if(Name != '') {
		requestData = requestData + '&name='+Name;
	}
	if(City != '') {
		requestData = requestData + '&city='+City;
	}
	console.log("request payload is : " + requestData);
	return requestData;
}

/**
 * This method is to delete the user information based on the id and other information.
 * @returns
 */
function deleteUser() {
	var deleteId = $('#deleteId').val();
	var deleteName = $('#deleteName').val();
	var deleteCity = $('#deleteCity').val();
	var requestData = requestPayLoad(deleteId, deleteName, deleteCity);
	var url = rootURL+'/remove';
	invokeAPI(url, 'DELETE', "application/x-www-form-urlencoded; charset=UTF-8", requestData, 'text');
}
/**
 * this method is to update the user information based on the id.
 * @returns
 */
function updateUser(){
	var updateId = $('#updateId').val();
	var updateName = $('#updateName').val();
	var updateCity = $('#updateCity').val();
	var requestData = requestPayLoad(updateId, updateName, updateCity);
	var url = rootURL+'/update';
	invokeAPI(url, 'PUT', "application/x-www-form-urlencoded; charset=UTF-8", requestData, 'text');
}
/**
 * search user based on some conditon such as id=2, or name= ravi, or city= bangalore.
 * @param condition
 * @param searchData
 * @returns
 */
function searchUser(condition, searchData) {
	console.log("conditon is : " + condition + " : " + searchData);
	var url = rootURL + '/search?'+condition+'='+searchData;
	console.log("search url  is : " + url);
	var response = invokeAPI(url, 'GET', "application/x-www-form-urlencoded; charset=UTF-8", '', 'text');
	creataTable(response);
}

/**
 * construct the url to make GET API call.
 * @returns
 */
function loadUsers() {
	console.log("seach for all users has been started");
	var response = invokeAPI(rootURL+'/search', 'GET', "application/x-www-form-urlencoded; charset=UTF-8", '', 'text');
	creataTable(response);
}
/**
 * parse the response json to display the result in table
 * @param response
 * @returns
 */
function creataTable(response) {

	var resultResponse = $.parseJSON(response);
	var resultJSON = resultResponse['result'];
	console.log("result of json is : " + resultJSON);
	//clear the table first
	$('#itemList').find('tbody').empty();
	var rows="";
	if(resultJSON != null ) {
		for(i = 0 ; i< resultJSON.length; i++ ) {
			var result = resultJSON[i];
			rows += "<tr><td>" + result.id + "</td><td>" + result.name + "</td><td>" + result.city +  "</td></tr>";
		}
		$( rows ).appendTo( "#itemList tbody" );
		console.log(rows);
	}
}
/**
 * make GET call to get the users
 * @param serverUrl
 * @param method
 * @param contentType
 * @param data
 * @param dataType
 * @returns
 */
function invokeAPI(serverUrl, method, contentType, data, dataType) {
	var responseData;
	$.ajax({
		url: serverUrl,
		type: method,
		contentType: contentType,
		data: data,
		dataType: dataType,
		async:false,
		statusCode: {
		      400: function (response) {
		         alert("something went wrong");
		         return;
		      },
		      404: function (response) {
		    	  alert("something went wrong");
		    	  return;
		      }
		   },
		   success: function(response) {
			responseData=response;
			console.log("received data"+response);
			console.log("responseData received data"+responseData);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ': ' + errorThrown);
		}
	});
	return responseData;
}
/**
 * make post call to insert the users.
 * @param serverUrl
 * @param method
 * @param contentType
 * @param dataType
 * @param data
 * @param parasParam
 * @param async
 * @returns
 */
function ajaxCall(serverUrl, method, contentType, dataType, data, parasParam, async) {
	var responseData;
	$.ajax({
		url: serverUrl,
		type: method,
		contentType: contentType,
		dataType: dataType,
		data: data,
		success: function(response) {
			responseData=response;
			console.log("received data"+responseData);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ': ' + errorThrown);
		}
	});
	return responseData;
}

function formJSONData() {
	
	var user = new Object();
	user.id = 0;
	user.name = $('#name').val();
	user.city = $('#city').val();
	return user;
}
function saveUser() {
	console.log("new user is being added");
	var dataJSON = JSON.stringify(formJSONData());
	console.log("json data to be sent over server : " + dataJSON);
	
	var url = rootURL+'/insertdata';
	invokeAPI(url, 'POST', "application/json; charset=UTF-8", dataJSON, 'json');

	//var addUser = ajaxCall(rootURL+'/insertdata', 'POST', "application/json; charset=UTF-8", 'json', dataJSON,'','');
}