// The root URL for the RESTful services
var rootURL = "http://localhost:8080/DashBoardManagementApp/rest/resources";
findALL();

$('#btnSave').click(
	function() {
		if($('#id').val() == '')
		addUser();
	return false;
	});
$('#btnSearch').click(function() {
	findALL();
	return false;
});

ajaxCall();
// General Call
function ajaxCall(serverUrl, data, method, contentType, dataType, async, parseParams) {
   var responseData;
	$.ajax({
		url: serverUrl +'/search',
		method: method,
		contentType: contentType,
		dataType: dataType,
		data: data,
		parseParams: parseParams,
		async: async,
		success: function (response) {
			responseData = response;
			console.log(responseData);
		},
		error: function (jqXHR, textStatus, errorThrown) {
			console.log(textStatus + ': ' + errorThrown);
		}
	});
	return responseData;
}

// Usage  -- assetdata
//var url = "http://23.22.220.162/dmserver/rest/assetinfo/assetdata" + "?sessionid=" + "0de8de46-dba7-40eb-bde5-868e4021d7a6" +"&assetid=1";
var response = ajaxCall(rootURL, '', 'GET', 'application/x-www-form-urlencoded; charset=UTF-8', '', '', '');

function search() {
	findALL();
}

function findALL() {
	console.log("find all user");
	$.ajax({
		type: 'GET',
		url: rootURL +'/search',
        contentType: "application/json; charset=utf-8",
		dataType: "json",
		success : renderUser
	});
}

function addUser() {
	console.log("adding new user");
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL + '/insertdata',
		dataType: "json",
		data: formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('successfully added a new user');
			$('#id').val(data.id);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('unable to add new user' + textStatus);
		}
	});
}

function updateUser() {
	console.log("update user");
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/update',
		dataType : "json",
		data: formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('successfully updated the user');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('unable to update user' + textStatus);
		}
	});
}
function deleteUser() {
	console.log("delete user");
	$.ajax({
		type : 'DELETE',
		contentType : 'application/json',
		url : rootURL + '/remove',
		dataType : "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('successfully deleted user');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('unable to delete user' + textStatus);
		}
	});
}

//function to display all user
function renderUser(data) {
	var list = data == null ?[] : (data instanceof Array ? data : [data]);
	$('#userList').remove();
	$.each(list, function(index, user) {
		$('#userList').append('<li><a href="#" data-identity="' + user.id + '">'+user.name+ user.city+'</a></li>');
	});
}

//function to render the output
function renderDetails(user) {
	$('#id').val(user.id);
	$('#name').val(user.name);
	$('#city').val(user.city);
}


//helper function to construct form input to JSON string.
function formToJSON() {
	var userId = $('#id').val();
	return JSON.stringify({
		"id": userId==""?null :userId,
		"name": $('#name').val(),
		"city": $('#city').val()
	});
}