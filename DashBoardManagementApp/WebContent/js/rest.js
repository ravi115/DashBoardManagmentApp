var rootURL = "http://localhost:8080/DashBoardManagementApp/rest/resources";

$( document).ready(function() {
	console.log("ready!");
	loadUser();
	$('#userForm').submit(function() {
		saveUSer();
	});
	$('#updateForm').submit(function() {
		updateUser();
	});
	$('#deleteForm').submit(function() {
		deleteUser();
	});
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
	var id = $('#did').val();
	var name = $('#dname').val();
	var city = $('#dcity').val();
	var searchColumn;
	if(id!='') {
		searchColumn= "id="+id;
	}
	if(name!= '') {
		if(id!='') {
			searchColumn+="&name="+name;
		}else{
			searchColumn="name="+name;
		}
	}
	if(city!='') {
		if(id!='' && name=='' || name!='' && id=='' || id!='' && name!= '') {
			searchColumn+="&city="+city;
		}
		else{
			searchColumn="city="+city;
		}
	}
	console.log("delete data is : " + searchColumn);
	var deleteURL = rootURL+'/remove';
	invokeAPI(deleteURL, 'DELETE', "application/x-www-form-urlencoded; charset=UTF-8", searchColumn, 'text');
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
		         alert("Invalid Input");
		         return;
		      },
		      404: function (response) {
		    	  alert("something went wrong 404");
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

function doSearch() {
	console.log("search function called on keypress!");
    var searchText = document.getElementById('searchTerm').value.toUpperCase();
    var targetTable = document.getElementById('itemList');
    var targetTableColCount;
            
    //Loop through table rows
    for (var rowIndex = 0; rowIndex < targetTable.rows.length; rowIndex++) {
        var rowData = '';

        //Get column count from header row
        if (rowIndex == 0) {
           targetTableColCount = targetTable.rows.item(rowIndex).cells.length;
           continue; //do not execute further code for header row.
        }
                
        //Process data rows. (rowIndex >= 1)
        for (var colIndex = 0; colIndex < targetTableColCount; colIndex++) {
            rowData += targetTable.rows.item(rowIndex).cells.item(colIndex).innerText.toUpperCase();
        }

        //If search term is not found in row data
        //then hide the row, else show
        if (rowData.indexOf(searchText) == -1)
            targetTable.rows.item(rowIndex).style.display = 'none';
        else
            targetTable.rows.item(rowIndex).style.display = 'table-row';
    }
}