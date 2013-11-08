function dialogueChild() {
    var name = $( "#name" ),
    dateOfBirth = $( "#dateOfBirth" ),
    height = $( "#height" ),
    weight = $( "#weight" ),
    bloodGroup = $( "#bloodGroup" ),
    gender = $("input:radio[name=gender]:checked"),
    nameInHindiChild = $("#nameInHindi"),
    allFields = $( [] ).add( name ).add( dateOfBirth ).add( height ).add( weight ).add( bloodGroup ).add(nameInHindiChild),
    tips = $( ".validateTips" );
    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        buttons: {
            "Save Child": function() {
                var bValid = true;
                allFields.removeClass( "ui-state-error" );
                bValid = bValid && checkLength( name, "name", 3, 15 );
                bValid = bValid && dateOfBirthRegexp( dateOfBirth, /^(0?[1-9]|[12][0-9]|3[01])[\s]([A-Z]+[a-z]{2})[\s]([0-9]{4})$/i, "Date Of Birth dd MMM yyyy" );
                bValid = bValid && checkRegexp(height,/^[0-9]+$/i,"Height should be integer");
                bValid = bValid && checkRegexp(weight,/^[0-9]+(\.[0-9]{1,2})?$/i,"Weight should be integer");
                
                if(bValid){
                	console.log($("input:radio[name=gender]:checked").val());
                    addChildVariables($("#name").val(),$("#nameInHindi").val(),$("input:radio[name=gender]:checked").val(), $("#dateOfBirth ").val(), $("#bloodGroup :selected").val());
                    $( this ).dialog( "close" );

                }
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            tips.text("")	;
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
}
function updateTips( t ) {
    tips.text( t ).addClass( "ui-state-highlight" );
    setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 0 );
    }, 0 );
}
    
function checkLength( o, n, min, max ) {
    if(!o.val()==""){
        if ( o.val().length > max || o.val().length < min ) {
            o.addClass( "ui-state-error" );
            updateTips( "Length of " + n + " must be between " +
                min + " and " + max + "." );
            return false;
        } else {
            return true;
        }
    }
    else
        return true;
    
}
    
function dateOfBirthRegexp( o, regexp, n ) {
    if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
    } else {
        return true;
    }
}
    
function checkRegexp( o, regexp, n ) {
    if(!o.val()==""){
        if ( !( regexp.test( o.val() ) ) ) {
            o.addClass( "ui-state-error" );
            updateTips( n );
            return false;
        } else {
            return true;
        }
    }
    else 
        return true;
}

function dialogueVaccine() {
    var date = $( "#date" ),
    allFields = $( [] ).add( date ),
    tips = $( ".validateTips" );
    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Update": function() {
                var bValid = true;
                allFields.removeClass( "ui-state-error" );
                var typeOfRequest;
                    if ($("#taken").is(":checked")){
                        typeOfRequest = 0;
                        bValid = bValid && dateOfBirthRegexp( date, /^(0?[1-9]|[12][0-9]|3[01])[\s]([A-Z]+[a-z]{2})[\s]([0-9]{4})$/i, "Date should be in dd MMM yyyy" );
                        
                    }else {
                        typeOfRequest = 1;
                        $("#date").val("01 Feb 2013");
                    }
                if(bValid){
                    
                    sendAJAXRequest($("#name").text() ,$("#date").val(),$("#childCalendarID").val(),typeOfRequest,$("#childID").val() );   
                    $( this ).dialog( "close" );
                
                }
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });

}
function dialogueBoxIfNotSaved(){
   
      $( "#dialog-confirm" ).dialog({
      autoOpen: false,
      resizable: false,
      height:200,
      modal: true,
      buttons: {
        "Confirm": function() {
          $( this ).dialog( "close" );
          window.location.href = $("#hiddenConfirmAnchor").text();
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
    });
   
}

/*function dialogueBoxDeleteData(link){
      
      $( "#dialog-delete" ).dialog({
      autoOpen: false,
      resizable: false,
      height:200,
      modal: true,
      buttons: {
        "Confirm": function() {
          $( this ).dialog( "close" );
          window.location.href = link;
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
    });
   
}*/
function dialogueBoxDeleteData(link, data, redirectLink) {
      $( "#dialog-delete" ).dialog({
            autoOpen: false,
            resizable: false,
            height:200,
            modal: true,
            buttons: {
              "Confirm": function() {
                $( this ).dialog( "close" );
                $.ajax({
                      type:"POST",
                      url : link,
                      data: data,
                      success: function(data)
                      {
                            window.location.href = redirectLink;
                      }
                  });
              },
              Cancel: function() {
                $( this ).dialog( "close" );
              }
            }
          });  
}
