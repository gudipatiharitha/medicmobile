var oTable;
var num=0;
function addChildVariablesExisting(name, nameinHindiFunction,gender, dateOfBirth ,bloodGroup, calenderId){
    if(dateOfBirth != ""){
    
    $("#childData").append(' <input type="text" name="childName'+num+'" id="childName'+num+'"/>'+
                            '<input type="text" name="childNameInHindi'+num+'" id="childNameInHindi'+num+'"/>'+
                            '<input type="text" name="childGender'+num+'" id="childGender'+num+'"/>'+
                                '<input type="text" name="childDateOfBirth'+num+'" id="childDateOfBirth'+num+'" />'+
                                '<input type="text" name="childBloodGroup'+num+'" id="childBloodGroup'+num+'" />',
                                '<input type="text" name="childCalendarID'+num+'" id="childCalendarID'+num+'" />');
    
    $("#childName"+num).attr("value",name);
    $("#childNameInHindi"+num).attr("value", nameinHindiFunction);
    $("#childDateOfBirth"+num).attr("value",dateOfBirth);
    $("#childBloodGroup"+num).attr("value",bloodGroup);
    $("#childGender"+num).attr("value",gender);
    $("#childCalendarID"+num).attr("value",calenderId);
    var id = $("#childID"+num).val();
    num = num + 1;
    $("#childTable").show();
    if (typeof id != 'undefined') {
          var button = "<button type='button' class='button'  id='child"+num+"' onclick='location.href=\"/motech-platform-server/module/medicmobile/api/viewChild/"+id +"\"'>View/Edit</button>";
    } else {
          var button = "<button type='button' class='button'  id='child"+num+"' onclick='alert_view_child()'>View/Edit</button>";
    }
            if (num == 1) {

                  oTable = $("#childTable").dataTable();
                  oTable.fnAddData([ num, name, dateOfBirth, button ]);
            } else {

                  oTable.fnAddData([ num, name, dateOfBirth, button ]);
            }
      }
}

function addChildVariables(name, nameinHindiFunction,gender, dateOfBirth ,bloodGroup){
      if(dateOfBirth != ""){
      
      $("#childData").append(' <input type="text" name="childName'+num+'" id="childName'+num+'"/>'+
                              '<input type="text" name="childNameInHindi'+num+'" id="childNameInHindi'+num+'"/>'+
                              '<input type="text" name="childGender'+num+'" id="childGender'+num+'"/>'+
                                  '<input type="text" name="childDateOfBirth'+num+'" id="childDateOfBirth'+num+'" />'+
                                  '<input type="text" name="childBloodGroup'+num+'" id="childBloodGroup'+num+'" />');
      
      $("#childName"+num).attr("value",name);
      $("#childNameInHindi"+num).attr("value", nameinHindiFunction);
      $("#childDateOfBirth"+num).attr("value",dateOfBirth);
      $("#childBloodGroup"+num).attr("value",bloodGroup);
      $("#childGender"+num).attr("value",gender);
      var id = $("#childID"+num).val();
      num = num + 1;
      $("#childTable").show();
      if (typeof id != 'undefined') {
            var button = "<button type='button' class='button'  id='child"+num+"' onclick='location.href=\"/motech-platform-server/module/medicmobile/api/viewChild/"+id +"\"'>View/Edit</button>";
      } else {
            var button = "<button type='button' class='button'  id='child"+num+"' onclick='alert_view_child()'>View/Edit</button>";
      }
      if(num == 1)
          {   
              
              oTable = $("#childTable").dataTable();
              oTable.fnAddData([num ,name , dateOfBirth,button]);
          }
       else{
           
           oTable.fnAddData([num ,name , dateOfBirth,button]);
       }   
      }
  }

function alert_view_child() {
      $("#view_edit_child").dialog({autoOpen: false});
      $("#view_edit_child").dialog("open");
}




