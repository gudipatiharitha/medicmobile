document
            .write('<div class="clear"></div><div id="logo"><img src="/motech-platform-server/module/medicmobile/images/medic-logo.png"/></div><div id="logoTitle"><h1>TIKA TRACKER</h1></div><div class="button printFalse" id="logout" ><a  href="motech-platform-server/j_spring_security_logout">Logout</a></div><div class="clear"></div>');

$(function() {
      $("#accordion").accordion({
            collapsible : true,
            heightStyle : "content"
      });
      $(".button").button();
});

function makeLoginNameAppear(userName) {
      $(
                  "<div class = 'user printFalse'><div id='userText'>Logged in as </div><div id='userName'>"
                              + userName + "</div></div>").insertAfter(
                  "#logout");
}

function sendAjaxForLocalVolunteer(staffID, bool) {
      $.ajax({
                        type : "POST",
                        url : "/motech-platform-server/module/medicmobile/api/findByLocalVolunteerId/",
                        data : {
                              "localVolunteerID" : staffID
                        },
                        success : function(data) {
                              returnOutPut(data, bool);
                        }
                  });
}

function returnOutPut(data, bool) {
      if (bool) {
            if (data > 0) {
                  $("#dialogDeletingLVHavingCareGivers").dialog("open");
            } else {
                  $("#dialog-delete").dialog("open");
            }
      } else {
            if (data > 0) {
                  $("#role").attr('disabled', true);
                  $(
                              "<p style='display:inline; font-size:10px;'>Local volunteer have care givers assigned</p>")
                              .insertAfter("#role");
            }
      }
}

function getBoolean() {
      return bool;
}
$(function(){
      $(".date")
      .datepicker(
                  {
                        dateFormat : 'dd M yy',
                        changeMonth : true,
                        changeYear : true,
                        yearRange : '1900:2050',
                        defaultDate : new Date(),
                        showOn : "button",
                        buttonImage : "/motech-platform-server/module/medicmobile/images/calendar.gif",
                        buttonImageOnly : true,
                        onChangeMonthYear: function(year, month, inst){
                              var date = $(this).datepicker("getDate");
                              if(date != null) {
                                    date.setFullYear(year);
                                    date.setMonth(month - 1);
                                    $(this).datepicker( "setDate", date );
                              }
                           }
                  }); 
});

function getFromLanguage() {
      return "english";
}

function getToLanguage() {
      return "hindi";
}