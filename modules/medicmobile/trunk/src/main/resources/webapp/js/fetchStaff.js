$('.whomClass').change(function() {
      var checkedValue = $('.whomClass:checked').val();
      if (checkedValue == 'staff') {
            fetchAll();
      } else if (checkedValue == 'careGiver') {
            fetchcareGivers();
      }  
});

function checkRadio() {
      var checkedValue = $('.whomClass:checked').val();
      if (checkedValue == 'staff') {
            fetchAll();
      } else if (checkedValue == 'careGiver') {
            fetchcareGivers();
      }
}

function fetchStaffWithAll(){
      var checkedValue = $('input:radio[name=toWhomInAnganwadi]:checked').val();
      if (checkedValue == 'staff') {
            fetchAll();
      } else if (checkedValue == 'careGiver') {
            fetchcareGivers();
      }
      
//    fetchcareGivers();
}
function fetchcareGivers(){
    var anganwadiID = $("#anganwadiID").val();
    if(anganwadiID != "no_AWC_selected"){
        $("#careGiverID option").each(function(){
            $(this).remove();
        });
    }
    sendAjaxToFetchCareGivers(anganwadiID);   
}
function fetchAll(){
    var anganwadiID = $("#anganwadiID").val();
    if(anganwadiID != "no_AWC_selected"){
        $("#staffID option").each(function(){
            $(this).remove();
        });
        var staffRole = $("#staffRole").val();
        if(staffRole != "allRoles"){
            sendAjaxToFetchStaffWithStaffRole(anganwadiID, staffRole);
        }
        else if(staffRole == "allRoles"){
            sendAjaxToFetchStaffWithAnganwadiID(anganwadiID);
        }
    }
    else if(anganwadiID == "no_AWC_selected"){
        var box = $("#localVolunteer");
        $("#localVolunteer option").each(function(){
            $(this).remove();
		    
        });
        box.append("<option>Select AWC above</option>");
    }
}

function fetchStaffWithStaffRole(){
    var anganwadiID = $("#anganwadiID").val();
    if(anganwadiID != "no_AWC_selected"){
        $("#staffID option").each(function(){
            $(this).remove();
        });
        var staffRole = $("#staffRole").val();
        if(staffRole != "allRoles"){
            sendAjaxToFetchStaffWithStaffRole(anganwadiID, staffRole);
        }

    }
}
function sendAjaxToFetchCareGivers(anganwadiID){
     $.ajax({
        type:"POST",
        url:"/motech-platform-server/module/medicmobile/api/getCareGivers/",
        data:{
            "anganwadiID":anganwadiID
        },
        success:function(data){
            populateCareGiver(data.careGiverList);
            
        }
    });
}


function sendAjaxToFetchStaffWithStaffRole(anganwadiID, staffRole){
    $.ajax({
        type:"POST",
        url:"/motech-platform-server/module/medicmobile/api/findStaff/",
        data:{
            "anganwadiID":anganwadiID, 
            "staffRole":staffRole
        },
        success:function(data){
            generateStaffList(data.personnelList);
        }
    });
}

function sendAjaxToFetchStaffWithAnganwadiID(anganwadiID){
    $.ajax({
        type:"POST",
        url:"/motech-platform-server/module/medicmobile/api/findStaff/",
        data:{
            "anganwadiID":anganwadiID
        },
        success:function(data){
            generateStaffList(data.personnelList);
        }
    });
}

function sendAjaxToFetchStaff(){
    $.ajax({
        type:"POST",
        url:"/motech-platform-server/module/medicmobile/api/findStaff/",
        data:{
            "anganwadiID":anganwadiID
        },
        success:function(data){
            generateStaffList(data.personnelList);
        }
    });
}

function generateStaffList(staffListJson){
    var box = $("#staffID");
    if(staffListJson.length == 0){
        box.append("<option>No Staff</option");
    }
    else{
        box.append("<option value='all'>All Staff</option")
        for(var staffIndex = 0; staffIndex < staffListJson.length; staffIndex++){
            var staffJson = staffListJson[staffIndex];
            var staffName = staffJson.name;
            var staffID = staffJson.staffID;
            box.append("<option value='"+staffID+"'>"+staffName+"("+staffJson.phoneNumber+")"+"</option>");
        }
    }
}

function changePhoneNumber(){
    var staffID = $("#staffID").val();
    if(staffListFromAjax != null){
        for(var staffIndex = 0; staffIndex < staffListFromAjax.length; staffIndex++){
            if(staffListFromAjax[staffIndex].staffID == staffID){
                $("#phoneNumber").val(staffListFromAjax[staffIndex].phoneNumber);
                $("#phoneNumber").attr("disabled", "disabled");
            }
        }
    }
}

function populateCareGiver(data){
    var box = $("#careGiverID");
    if(data.length == 0){
        box.append("<option>No CareGiver</option");
    }
    else{
        box.append("<option value='all'>All CareGiver</option")
        for(var careGiverLength = 0; careGiverLength < data.length; careGiverLength++){
            var careGiverView = data[careGiverLength];
            var careGiverName = careGiverView.careGiver.name;
            var careGiverID = careGiverView.careGiver.careGiverID;
            box.append("<option value='"+careGiverID+"'>"+careGiverName+"("+careGiverView.careGiver.phoneNumber+")"+"</option>");
        }
    }
}