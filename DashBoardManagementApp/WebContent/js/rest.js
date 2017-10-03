var rootURL = "http://localhost:8080/DashBoardManagementApp/rest/resources";

	console.log("ready!");
	
	loadUser();
	$('#userForm').submit(function() {
		saveUSer();
		loadUser();
	});
	$('#updateForm').submit(function() {
		updateUser();
		loadUser();
	});
	$('#deleteForm').submit(function() {
		deleteUser();
		loadUser();
	});
	$('#searchForm').submit(function() {
		searchUser();
	});

function loadUser() {
	console.log("load all user data");
	console.log("seach for all users has been started");
	var response = invokeAPI(rootURL+'/search', 'GET', "application/x-www-form-urlencoded; charset=UTF-8", '', 'text');
	creataTable(response);
}

function saveUSer() {
	console.log("saveUSer is triggered");
	var name= $('#name').val();
	var city= $('#city').val();
	var dataJSON = JSON.stringify(formJSONData(name,city));
	console.log("json data to be sent over server : " + dataJSON);
	
	var url = rootURL+'/insertdata';
	invokeAPI(url, 'POST', "application/json; charset=UTF-8", dataJSON, 'json');

}

function updateUser() {
	console.log("updateUser is triggered");

	var requestData = requestPayLoad($('#uid').val(), $('#uname').val(), $('#ucity').val());
	var url = rootURL+'/update';
	invokeAPI(url, 'PUT', "application/x-www-form-urlencoded; charset=UTF-8", requestData, 'text');
}

function deleteUser() {
	console.log("deleteUser is triggered");
	var val = $('#dId').val();
	var val = $('#sId').val();
	var column;
	if($("#option1").prop("checked", true)) {
		column="id";
	}else if ($("#option2").prop("checked", true)) {
		column="name";
	}else if ($("#option3").prop("checked", true)) {
		column="city";
	}
}

function searchUser() {
	console.log("searchUser is triggered");
	var val = $('#sId').val();
	var column;
	if($("#option1").prop("checked", true)) {
		column="id";
	}else if ($("#option2").prop("checked", true)) {
		column="name";
	}else if ($("#option3").prop("checked", true)) {
		column="city";
	}
	var url = rootURL + '/search?'+column+'='+val;
	console.log("search url  is : " + url);
	var response = invokeAPI(url, 'GET', "application/x-www-form-urlencoded; charset=UTF-8", '', 'text');
	creataTable(response);
}

function requestPayLoad(column, val) {

}
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


function formJSONData(name, city) {
	
	var user = new Object();
	user.id = 0;
	user.name = name;
	user.city = city;
	return user;
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
	var trStyle = ["bg-success","bg-danger","bg-info","bg-warning"];
	var rows="";
	if(resultJSON != null ) {
		var j = 0;
		for(i = 0 ; i< resultJSON.length; i++ ) {
			var result = resultJSON[i];
			rows += "<tr class="+ trStyle[j++]+ "><td>" + result.id + "</td><td>" + result.name + "</td><td>" + result.city +  "</td></tr>";
			if(j==4){
				j=0;
			}
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
