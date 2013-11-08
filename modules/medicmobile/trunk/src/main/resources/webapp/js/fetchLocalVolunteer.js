var localVolunteerId;
function setLocalVolunteer(localVolunteerID)
{
    localVolunteerId = localVolunteerID;
}
function fetchLocalVolunteers(){
	
	var anganwadiID = $("#anganwadiID").val();
	if(anganwadiID == "all"){
		var box = $("#localVolunteerID");
		$("#localVolunteerID option").each(function(){
		    $(this).remove();
		});
		sendAjaxToGetLocalVolunteers(anganwadiID, true);
	}
	else if(anganwadiID == "no_AWC_selected"){
		var box = $("#localVolunteerID");
		$("#localVolunteerID option").each(function(){
		    $(this).remove();
		    
		});
		box.append("<option>Select AWC above</option>")
	}
	else{
		$("#localVolunteerID option").each(function(){
		    $(this).remove();
		});
		sendAjaxToGetLocalVolunteers(anganwadiID, true);
	}
}

function sendAjaxToGetLocalVolunteers(anganwadiID, fromAll){
	
	$.ajax({
		type:"POST",
		url : "/motech-platform-server/module/medicmobile/api/getLocalVolunteers/",
		data: {"anganwadiID":anganwadiID},
		success: function(data)
		{
			generateLocalVolunteerList(data.localVolunteerList, fromAll);
		}
	});
	
}

function generateLocalVolunteerList(staffListJson, fromAll){
	var box = $("#localVolunteerID");
	if(staffListJson.length == 0){
		box.append("<option value=''>No Volunteer</option");
	}
	else if(fromAll){
		box.append("<option value='all'>All Local Volunteers</option")
		for(var staffIndex = 0; staffIndex < staffListJson.length; staffIndex++){
			var staffJson = staffListJson[staffIndex];
			var staffName = staffJson.name;
			var staffID = staffJson.staffID;
			box.append("<option value='"+staffID+"'>"+staffName+"</option");
		}
	}

	else {
		for(var staffIndex = 0; staffIndex < staffListJson.length; staffIndex++){
			var staffJson = staffListJson[staffIndex];
			var staffName = staffJson.name;
			var staffID = staffJson.staffID;
			box.append("<option value='"+staffID+"'>"+staffName+"</option");
		}
	}

        if(localVolunteerId != null || localVolunteerId !="")
            $("#localVolunteerID").val(localVolunteerId);

}

function enableLocalVolunteerLabel(localVolunteerID){
	$.ajax({
		method : "POST",
		url : "/motech-platform-server/module/medicmobile/api/findStaffById/",
		data : {"staffID":localVolunteerID},
		success : function(data){
			processPersonnelJSON(data, localVolunteerID);
		}
	});
}

function processPersonnelJSON(receivedData, localVolunteerID){
	if(receivedData.personnelList.length>0){
		$("#localVolunteerLabel").html("<a href='/motech-platform-server/module/medicmobile/api/staff/"+localVolunteerID+"'>"+receivedData.personnelList[0].name+"</a>");
	}        
}

function fetchLocalVolunteersWithOutAll(){
	var anganwadiID = $("#anganwadiID").val();
	if(anganwadiID == "no_AWC_selected"){
		var box = $("#localVolunteerID");
		$("#localVolunteerID option").each(function(){
		    $(this).remove();
		    
		});
		box.append("<option>Select AWC above</option>")
	}else{
		$("#localVolunteerID option").each(function(){
		    $(this).remove();
		});
		sendAjaxToGetLocalVolunteers(anganwadiID, false);
	}
}