/**
 * @author beehyv04
 */



function getJson()
{
	var idCardNumber = $("#idCardNumber").val();
	var careGiverName = $("#careGiverName").val();
	var startDate = $("#startDate").val();
	var endDate   = $("#endDate").val();
	var childName = $("#childName").val();
	var anganwadiID = $("#anganwadiID :selected").val();
	var localVolunteerID = $("#localVolunteerID :selected").val();
	
	var jsonObject = {};
	
	if(startDate > endDate)
	{
		endDate =  startDate;
	}
	
	if(idCardNumber.length > 0)
	{
		jsonObject["idCardNumber"] = idCardNumber;
	}
	if(careGiverName.length > 0)
	{
		jsonObject["careGiverName"] = careGiverName;
	}
	if(childName.length > 0)
	{
		jsonObject["childName"] = childName;
	}
	if(startDate.length > 0)
	{
		jsonObject["startDate"] = startDate;
	}
	if(localVolunteerID.length > 0 && localVolunteerID != "all")
	{
		jsonObject['localVolunteerID'] = localVolunteerID;
	}
	if(anganwadiID.length > 0 && anganwadiID !="all"){
		jsonObject['anganwadiID'] = anganwadiID;
	}
	if(endDate.length > 0)
	{
		jsonObject["endDate"] = endDate;
	}
	return jsonObject;
}


function sendAjax()
{
	var startDate = $("#startDate").val();
	var endDate   = $("#endDate").val();
	if(new Date(startDate) > new Date(endDate)) {
		$("#dialogMissingDetails").dialog("open");
	} else {
		$.ajax({
			type:"POST",
			url : "/motech-platform-server/module/medicmobile/api/viewCareGiver/",
			data: getJson(),
			success: function(data)
			{
				displayDataTable(data.careGiverList);
			}
		});
	}
}

function initDataTables()
{
    $("#listOfCareGiversTable").dataTable({
    	"bAutoWidth": false,
    	"bPaginate":true,
    	"bLengthChange": false, 
    	"bFilter":false,
    	"aoColumns": [	{ "sWidth": "20px" }, // 1st column width
						null, // 2nd column width
						{ "sWidth": "125px" }, // 3rd column width and so on
						{ "sWidth": "125px" },
						{ "sWidth": "125px" },
						{ "sWidth": "125px" }
					],
		"aaSorting": [[ 4, "asc" ],[2,"asc"],[3,"asc"]],
    	"fnRowCallback" : function(nRow, aData, iDisplayIndex){
    		 var index = iDisplayIndex +1;
    		 $("td:first", nRow).html("<div>"+index+"</div>");
             return nRow;
    	 },
    });
}

function displayDataTable(careGiverListJson)
{
	var table = "<table id='listOfCareGiversTable' style='width:660px;table-layout:'fixed''><thead><tr><th>SNo</th><th>ID Number</th><th>Name</th><th>Anganwadi Center</th><th>Children</th><th>Local Volunteer</th></tr></thead><tbody id = 'careGiverTableBody'>";
	
	for(var careGiverIndex = 0; careGiverIndex < careGiverListJson.length; careGiverIndex++)
	{
		var careGiverJson = careGiverListJson[careGiverIndex];
		var careGiverID = careGiverJson.careGiver.careGiverID;
		var careGiverName = careGiverJson.careGiver.name;
		var anganwadiID = careGiverJson.careGiver.anganwadiID;
		var anganwadiName;
		$("#anganwadiID option").filter(function(){
		      if ($(this).val() == anganwadiID) {
		            anganwadiName = $(this).text();
		      }
		});
		var localVolunteerName = careGiverJson.localVolunteerName;
		var localVolunteerPhoneNumber = careGiverJson.localVolunteerPhoneNumber;
		var localVolunteerID = careGiverJson.careGiver.localVolunteerID;
		var careGiverIDCardNumber = careGiverJson.careGiver.idCardNumber;
		
		var childArray = new Array();
		for(var childIndex in careGiverJson.careGiver.children)
		{
			var childJson = careGiverJson.careGiver.children[childIndex];
			var childName = childJson.name;
			if(childName == null || childName == '')
			{
				childName = "Unknown";
			}
			var childID = childJson.childID;
			if (typeof childID != "undefined") {
			      childArray.push({'childName':childName, 'childID':childID});
			}
		}
		table += constructDOM(careGiverIDCardNumber ,careGiverID, careGiverName, anganwadiID, anganwadiName, childArray, localVolunteerID, localVolunteerName, localVolunteerPhoneNumber);
	}
	table += "</tbody></table>"
	reDraw(table);
}

function reDraw(table)
{
    $("#listOfCareGiversTable").parent().replaceWith(table);
    initDataTables();
    //commaSeperatedChild();
}

function constructDOM(idCardNumber ,careGiverID, careGiverName, anganwadiID, anganwadiName, childArray, localVolunteerID, localVolunteerName, localVolunteerPhoneNumber)
{
//	$("careGiverTableBody")
	var childrenDOM = "<td class='children'>";
	for(var childIndex = 0; childIndex < childArray.length; childIndex++)
	{
		if(childIndex != childArray.length - 1){
		    var childDOM;
		    if (childArray[childIndex]['childName'] != ""){
		          childDOM = "<span class='child'><a href=/motech-platform-server/module/medicmobile/api/viewChild/"+childArray[childIndex]['childID']+">"+childArray[childIndex]['childName']+"</a>";  
		    } else {
		          childDOM = "<span class='child'><a href=/motech-platform-server/module/medicmobile/api/viewChild/"+childArray[childIndex]['childID']+">Unknown</a>";
		    }
		    childrenDOM += childDOM+" , </span>"
			/*if (childArray.length > 0  && !childIndex == childArray.length - 1) {
			      childrenDOM += childDOM+"&nbsp;,&nbsp;</span>";
			} else {
			      childrenDOM += childDOM+"</span>";
			}*/
			if((childIndex+1)%2 == 0)
			      childrenDOM += "<div></div>"
		} else {
			var childDOM;
		    if (childArray[childIndex]['childName'] != ""){
		          childDOM = "<span class='child'><a href=/motech-platform-server/module/medicmobile/api/viewChild/"+childArray[childIndex]['childID']+">"+childArray[childIndex]['childName']+"</a>";  
		    } else {
		          childDOM = "<span class='child'><a href=/motech-platform-server/module/medicmobile/api/viewChild/"+childArray[childIndex]['childID']+">Unknown</a>";
		    }
		    childrenDOM += childDOM+"</span>"
		}
	}
	childrenDOM = childrenDOM +"</td>";
	
	var careGiverDOM;
	if (careGiverName != "") {
	      careGiverDOM = "<td class='careGiver'><div><a href=/motech-platform-server/module/medicmobile/api/CareGiver/"+careGiverID+">"+careGiverName+"</a></div></td>";  
	} else {
	      careGiverDOM = "<td class='careGiver'><div><a href=/motech-platform-server/module/medicmobile/api/CareGiver/"+careGiverID+">Unknown</a></div></td>";
	}
	
	
	var anganwadiDOM = "<td class='anganwadi'><div><a href=/motech-platform-server/module/medicmobile/api/AnganwadiCenter/"+anganwadiID+">"+anganwadiName+"</a></div></td>";
	
	var localVolunteerDOM;
	if (localVolunteerName != "") {
	      localVolunteerDOM = "<td class='careGiver'><div><a href=/motech-platform-server/module/medicmobile/api/staff/"+localVolunteerID+">"+localVolunteerName+"</a></div></td>"; 
	} else {
	      localVolunteerDOM = "<td class='careGiver'><div><a href=/motech-platform-server/module/medicmobile/api/staff/"+localVolunteerID+">Unknown</a></div></td>";
	}
	var careGiverIDCareNumberDOM = "<td class='idCardNumber'><div><a href=/motech-platform-server/module/medicmobile/api/CareGiver/"+careGiverID+">"+idCardNumber+"</a></div></td>";
	
	var finalDOM = "<tr><td class='serialNumber'></td>"+careGiverIDCareNumberDOM+careGiverDOM+anganwadiDOM+childrenDOM+localVolunteerDOM+"</tr>";
	
	return finalDOM;
	
}

function enableLocalVolunteerLabel(localVolunteerID){
	$.ajax({
		method : "POST",
		url : "/motech-platform-server/module/medicmobile/api/findStaff/",
		data : {"staffID":localVolunteerID},
		success : function(){
			processPersonnelJSON(data);
		}
	});
}

function processPersonnelJSON(receivedData){
	$("#localVolunteerLabel").text(receivedData.personnelList[0].name);
}
