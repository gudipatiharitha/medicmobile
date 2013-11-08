var anganwadiCenterRelation;
function initialize()
{
	$('#searchPersonnel').bind("click",function(dataObj){
		$('#personnelList').empty();
		ajaxRequest();
		});
}
function ajaxRequest()
{
	var dataSend={};
		if(($('input#staffID').val()!=null)&&($('input#staffID').val()!=""))
			dataSend['employeeID'] = $('input#staffID').val();
		if(($('#staffCenterId option:selected').val()!=null)&&($('#staffCenterId option:selected').val()!=""))
			dataSend['anganwadiID'] =$('#staffCenterId option:selected').val();
		if(($('#staffRole option:selected').val()!=null)&&($('#staffRole option:selected').val()!=""))
			dataSend['staffRole']=$('#staffRole option:selected').val();
		if(($('input#staffName').val()!=null)&&($('input#staffName').val()!=""))
				dataSend['staffName']=$('input#staffName').val();
	
	$.ajax({
		type : 'POST',
		url : '/motech-platform-server/module/medicmobile/api/findStaff/' ,
		data : dataSend,
		success : function(dataReceived) { 
			ajaxSuccess(dataReceived);
		},
		error : function(r, ts, e) {
			
		}
	});
}
function ajaxSuccess(dataReceived)
{
	$('#emptyFullPersonnel').empty();
	$('#emptyFullPersonnel').append('<table id="listPersonnelTable" style="float:left"><thead><tr><th>Role</th><th>Employee Id</th><th>Name</th><th>Anganwadi Center</th><th>View Caregivers</th></tr></thead><tbody id="personnelList"></tbody></table>');
	if(dataReceived != null)
	for(var i=0;i<dataReceived.personnelList.length;i++)
	{
		addToPersonnelList(dataReceived.personnelList[i]);
	}
	 $("#listPersonnelTable").dataTable({"bPaginate":true, "bLengthChange": false, "bFilter":false});
}
function addToPersonnelList(personnel)
{
	var anganwadiCenterString="";
	var anganwadiIdString;
	if(personnel.anganwadiID.length>0)
	{	
		if(personnel.anganwadiID.length>0)
		{	anganwadiIdString=personnel.anganwadiID[0];
			anganwadiCenterString = "<a href='/motech-platform-server/module/medicmobile/api/AnganwadiCenter/"+anganwadiIdString+"'>"+getCenterName(anganwadiIdString)+"</a>";
		}
		for(var i=1;i<personnel.anganwadiID.length;i++)
		{	
			anganwadiIdString=personnel.anganwadiID[0];
			anganwadiCenterString = anganwadiCenterString + ", "+"<a href='/motech-platform-server/module/medicmobile/api/AnganwadiCenter/"+anganwadiIdString+"'>"+getCenterName(personnel.anganwadiID[i])+"</a>";
		}
	}
	var staffID = personnel.staffID;
	var employeeId = personnel.employeeID;
	var staffRole = makeRolesMap()[personnel.role];
	var staffName=personnel.name;
	if(staffRole != "Local Volunteer"){
		$('#personnelList').append("<tr><td>"+staffRole+"</td><td><a href='/motech-platform-server/module/medicmobile/api/staff/"+staffID+"'>"+employeeId+"</td><td><a href='/motech-platform-server/module/medicmobile/api/staff/"+staffID+"'>"+staffName+"</a><td>"+anganwadiCenterString+"</td><td>N/A</td></tr>");
	}
	else{
		$('#personnelList').append("<tr><td>"+staffRole+"</td><td><a href='/motech-platform-server/module/medicmobile/api/staff/"+staffID+"'>"+employeeId+"</td><td><a href='/motech-platform-server/module/medicmobile/api/staff/"+staffID+"'>"+staffName+"</a><td>"+anganwadiCenterString+"</td><td><a href='/motech-platform-server/module/medicmobile/api/viewCareGivers?anganwadiID="+personnel.anganwadiID[0]+"&localVolunteerID="+staffID+"'>View</a></td></tr>");
	}
}
function getCenterName(anganwadiId)
{
	return anganwadiCenterRelation[anganwadiId];
}