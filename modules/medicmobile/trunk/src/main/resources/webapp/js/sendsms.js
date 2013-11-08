function sendSMS() {
      if($("#sendSMSForm").validationEngine("validate"))
            sendSMSAjax();
}

function getJson() {
      var json = {};
      var language = $("input[name=language]:checked").val();
      var message = $("#message").val();
      var toWhom = $("input[name=toWhom]:checked").val();
      if (toWhom == "notRegistered") {
            var newPhoneNumber = $("#phoneNumber").val();
            json['newPhoneNumber'] = newPhoneNumber;
      } else if (toWhom == "registered") {
            json['anganwadiID'] = $("#anganwadiID").val();
            json['careGiverID'] = $("#careGiverID").val();
            json["staffID"] = $("#staffID").val();
            json["staffRole"] = $("#staffRole").val();
            json["toWhomInAnganwadi"] = $("input[name=toWhomInAnganwadi]:checked").val();
      }
      json['message'] = message;
      json['language'] = language;
      json['toWhom'] = toWhom;
      return json;
}

function sendSMSAjax() {
      $.ajax({
            type:"POST",
            url : "/motech-platform-server/module/medicmobile/api/sendSMS/",
            data: getJson(),
            headers:{"Content-Type":"application/x-www-form-urlencoded", "charset":"UTF-8"},
            success: function(data)
            {
                  $("#previousMessageData").show();
                  $("#message").val("");
                  $("#previousMessage").text("");
                  $("#previousRecipients").text("");
                  var name = data.previousMessage[0];
                  if(name.length >= 60){
                      var temp = name.substring(0,57) +"...";
                      $("#previousMessage").attr("title",name);
                      $("#previousMessage").append(temp);
                  }
                  else
                      $("#previousMessage").append(name);
                  for(var i in data.previousPersonList) {
                        if(i < data.previousPersonList.length - 1){
                              $("#previousRecipients").append(data.previousPersonList[i]+",");
                        } else {
                              $("#previousRecipients").append(data.previousPersonList[i]);
                        }
                  }
            }
        });
}