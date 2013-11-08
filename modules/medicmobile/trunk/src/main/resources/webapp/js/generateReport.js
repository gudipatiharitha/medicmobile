var data ;
var accordionNumber;
var fullPrint=false;

var dataTables = [];

function getFullPrint()
{
    return fullPrint;
}
function printReport(){
    printWindow=window.open('','');
    var pageContent = "<html>"
        + "<body> "
        + "<h2>Angnawadi Name</h2>"
        + "<h3>Date: 28-10-2014</h3>"
        + "<div><table ><thead><tr><th>Name</th><th>Mother</th><th>Vaccines To Be Given</th><th>Due Date</th><th>Comments</th></tr></thead>"
        + "<tbody>"
        + "<tr><td>diksha</td><td>Anitha</td><td><div>OPT 1 <input type='checkbox'><input type='text'></div><div>DPT II <input type='checkbox'><input type='text'></div></td><td>23-10-2014</td><td>None</td></tr>"
        + "<tr><td>diksha</td><td>Anitha</td><td><div>OPT 1 <input type='checkbox'><input type='text'></div><div>DPT II <input type='checkbox'><input type='text'></div></td><td>23-10-2014</td><td>None</td></tr>"
        +"<tr><td>diksha</td><td>Anitha</td><td><div>OPT 1 <input type='checkbox'><input type='text'></div><div>DPT II <input type='checkbox'><input type='text'></div></td><td>23-10-2014</td><td>None</td></tr>"
        + "</tbody></table><div></body></html>";
    printWindow.document.write(pageContent);
}
function callAjaxForReport()
{       
	$('#showEmptyContent').empty();
	$("#loadingImage").show();
    var anganwadiID = $("#anganwadiID").val();
    var reportDate = $("#reportDate").val();
    if(reportDate == "" || reportDate == null){
          $("#dialogMissingDetails").dialog("open");
          $("#loadingImage").hide();
    }
    else
          $.ajax({
        type : 'POST',
        url : 'weeklyReport' ,
        data : {
            "anganwadiID":anganwadiID,
            "reportsDate":reportDate
        },
        success : function(dataReceived) {
                        $("#loadingImage").hide();
                        $(".hideBefore").show();
                        if(dataReceived.anganwadiName != null || (dataReceived.reportAccordians != null && dataReceived.reportAccordians.length != 0)){
                              $("#updateDetails").show();
                        } else {
                              $("#updateDetails").hide();
                              $("#everyPrint").hide();
                        }
            ajaxSuccess(dataReceived);
        },
        error : function(r, ts, e) {
              $("#loadingImage").hide();
        }
    });
}
function ajaxSuccess(dataReceived)
{
    $('#reportAccordion').empty();
    if(dataReceived.reportAccordians.length==0)
    {
        $('#showEmptyContent').append("No child requires vaccination this week");
    }
    else
    {
        $('#reportAccordion').append("<div class='reportAccordion'></div>");
        for(var i=0;i<dataReceived.reportAccordians.length;i++)
        { accordionNumber=i+1;
        addToReport(dataReceived.reportAccordians[i]);
        }
        $('#updateDetails').bind('submit',function(e){
            if(($(".visitDate").val()==null)||($(".visitDate").val()==''))
            {
                e.preventDefault();
                $("#dialogMissingDetails").dialog("open");
            }
        });
        $(".reportResult").each(function(){
                  var oTable = $(this).dataTable({
                        "aoColumns" : [ {
                              "sType" : "string","sWidth":"17%"
                        }, {"sWidth":"17%"},  {"sWidth":"17%"}, {
                              "sType" : "date-dd-mmm-yyyy", "sWidth":"17%"
                        }, {
                              "sType" : "date-dd-mmm-yyyy","sWidth":"17%"
                        }, {"sWidth":"9%"} ],
                        "aaSorting" : [ [ 3, "asc" ], [ 0, "asc" ] ],
                        "sEmptyTable" : "no child due for vaccination",
                        "bAutoWidth":false,
                        });
              dataTables.push(oTable);
        });
        defaultDataTable();
        $(".reportAccordion").accordion(
                {
                    heightStyle: "content",
                    collapsible: true
                });
        $(".button").button();
        $("#visitDate").val($("#reportDate").val());
        $("#reportsDate").val($("#reportDate").val());
        $("#anganwadi").val($("#anganwadiID").val());
        setVisitDate();
    }
}
//".reportResult"
function defaultDataTable() {
      for(index in dataTables) {
            var oTableTemp = dataTables[index];
            var oSettings = oTableTemp.fnSettings();
            oSettings._iDisplayLength = 10;
            oTableTemp.fnDraw();
      }
}

function fullShowDatatable() {
      for(index in dataTables) {
            var oTableTemp = dataTables[index];
            var oSettings = oTableTemp.fnSettings();
            oSettings._iDisplayLength =  -1 ;
            oTableTemp.fnDraw();
      }
}

function onChangecheckBox(object)
{
    var objectID = $(object).attr("id");
    var objectChangeID = objectID+"d";
    var panel = document.getElementById(objectChangeID);
    panel.checked = !object.checked;
    setBool(false);
}

function addToReport(reportAccordion)
{
    data = '<h3 class="'+accordionNumber+' localVolunteerName printFalse everyPrint">'+reportAccordion.localVolunteerName+'</h3><div class="localVolunteerTable everyPrint printFalse '+accordionNumber+'"> <div style="float: right"><button id="'+accordionNumber+'" type="button" onclick="removePrint('+accordionNumber+');return false;" class="button print printFalse">Print</button></div><div class="clear"></div><table class="reportResult"><thead><tr><th>Name</th><th>CareGiver</th><th>Vaccine</th><th>DueDate</th><th>Administered Date</th><th>Code</th></tr></thead><tbody>' ;
    for(var i=0;i<reportAccordion.rows.length;i++)
    {
        addRowsToReport(reportAccordion.rows[i]);
    }
    data += '</tbody></table><div class="clear"></div></div>';
    $(".reportAccordion").append(data);
}
function addRowsToReport(reportRow)
{
    var dateString = reportRow.administeredDateString;
    var updateString= reportRow.updatedBy;
    if(dateString==null)
    {
        dateString="";
    }
    if(updateString=="")
    {
        updateString = "";
    }
    else
    {
        updateString = "Update done By:"+updateString;
    }
    if(reportRow.vaccineTaken)
    {
        if(reportRow.smsCode != -1 || reportRow.smsCode != "-1")
              data += '<tr class="printFalse"><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName" ><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" class = "checkVaccine" style="float:right;" checked="checked" type="checkbox" onchange="onChangecheckBox(this)">'+updateString+'<input name="updates" type="checkbox" hidden=true id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">'+dateString+'</td><td class="smsCode">'+reportRow.smsCode+'</td></tr>';
        else 
              data += '<tr class="printFalse"><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName" ><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" class = "checkVaccine" style="float:right;" checked="checked" type="checkbox" onchange="onChangecheckBox(this)">'+updateString+'<input name="updates" type="checkbox" hidden=true id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">'+dateString+'</td><td class="smsCode">N/A</td></tr>';
    }
    else if(!reportRow.missed){
          if(reportRow.smsCode != -1 || reportRow.smsCode != "-1")
                data += '<tr><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName"><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" name="updates" class = "checkVaccine" style="float:right;" value = "'+reportRow.vaccineID+'.0" type="checkbox" onchange="onChangecheckBox(this)"><input type="checkbox" name="updates" hidden=true checked="checked" id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">'+dateString+'</td><td class="smsCode">'+reportRow.smsCode+'</td></tr>';
          else 
                data += '<tr><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName"><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" name="updates" class = "checkVaccine" style="float:right;" value = "'+reportRow.vaccineID+'.0" type="checkbox" onchange="onChangecheckBox(this)"><input type="checkbox" name="updates" hidden=true checked="checked" id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">'+dateString+'</td><td class="smsCode">N/A</td></tr>';
    } else {
          if(reportRow.smsCode != -1 || reportRow.smsCode != "-1")
                data += '<tr><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName"><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" name="updates" class = "checkVaccine" style="float:right;" value = "'+reportRow.vaccineID+'.0" type="checkbox" onchange="onChangecheckBox(this)"><input type="checkbox" name="updates" hidden=true checked="checked" id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">missed</td><td class="smsCode">'+reportRow.smsCode+'</td></tr>';
          else
                data += '<tr><td class="childName"><a>'+reportRow.childName+'</a></td><td class="careGiverName"><a>'+reportRow.careGiverName+'</a></td><td class="vaccineName"><div class="clear"></div>'+reportRow.vaccineName+'<input id="a'+reportRow.vaccineID+'" name="updates" class = "checkVaccine" style="float:right;" value = "'+reportRow.vaccineID+'.0" type="checkbox" onchange="onChangecheckBox(this)"><input type="checkbox" name="updates" hidden=true checked="checked" id="a'+reportRow.vaccineID+'d" value="'+reportRow.vaccineID+'.1"><div class="clear"></div></div></td><td class="dueDateString">'+reportRow.dueDateString+'</td><td class="administeredDateString">missed</td><td class="smsCode">N/A</td></tr>';
    }
}
function appendVaccineInfo(name,originalDate,appointmentDate){
    $("#name").text(name);
    $("#dueDate").html(originalDate);
}
function sendAJAXRequest(vaccineName , date , calendarID , visitvalue , childID)
{
    var vaccineID = calendarID + "." + vaccineName + "." + visitvalue ;
    $.ajax({
        type : 'POST',
        url : '../updateVaccination/'+ childID,
        data : {
            "vaccineID":vaccineID,
            "visitDate":date
        },
        success : function(response) {
            window.location.href = "/motech-platform-server/module/medicmobile/api/viewChild/"+ childID;
        },
        error : function(r, ts, e) {
        }
    });
}
function removePrint(element)
{
    $('.print').printPreview();
    var id = element;
    $('.everyPrint').addClass('printFalse');
    $('.'+id).removeClass('printFalse');
    fullPrint=false;
    fullShowDatatable();
}

function removeFullPrint(element)
{
    fullPrint=true;
    $(".reportAccordion .ui-accordion-content").css("display", "block");
    $('.print').printPreview();
    var id = element;
    $('.everyPrint').addClass('printFalse');
    $('.'+id).removeClass('printFalse');
    fullShowDatatable();
} 
var customDateDDMMMYYYYToOrd = function (date) {
      "use strict"; //let's avoid tom-foolery in this function
      var dateParts = date.split(" ");
      return (dateParts[2] * 10000) + ($.inArray(dateParts[1].toUpperCase(), ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"]) * 100) + dateParts[0];
  };
   
  // This will help DataTables magic detect the "dd-MMM-yyyy" format; Unshift so that it's the first data type (so it takes priority over existing)
  jQuery.fn.dataTableExt.aTypes.unshift(
      function (sData) {
          "use strict"; //let's avoid tom-foolery in this function
          if (/^([0-2]?\d|3[0-1])-(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)-\d{4}/i.test(sData)) {
              return 'date-dd-mmm-yyyy';
          }
          return null;
      }
  );
   
  // define the sorts
  jQuery.fn.dataTableExt.oSort['date-dd-mmm-yyyy-asc'] = function (a, b) {
      "use strict"; //let's avoid tom-foolery in this function
      var ordA = customDateDDMMMYYYYToOrd(a),
          ordB = customDateDDMMMYYYYToOrd(b);
      return (ordA < ordB) ? -1 : ((ordA > ordB) ? 1 : 0);
  };
   
  jQuery.fn.dataTableExt.oSort['date-dd-mmm-yyyy-desc'] = function (a, b) {
      "use strict"; //let's avoid tom-foolery in this function
      var ordA = customDateDDMMMYYYYToOrd(a),
          ordB = customDateDDMMMYYYYToOrd(b);
      return (ordA < ordB) ? 1 : ((ordA > ordB) ? -1 : 0);
  };
  
  
  
  $(document).ready(function() { 
        $('.localVolunteerName').click(function() {
        if($(this).next().is(':hidden') != true) {
                      $(this).removeClass('active'); 
          $(this).next().slideUp("normal");
        } else {
          $('.localVolunteerName').removeClass('active');  
           $('.localVolunteerTable').slideUp('normal');
          if($(this).next().is(':hidden') == true) {
          $(this).addClass('active');
          $(this).next().slideDown('normal');
           }   
        }
         });
       
        $('.localVolunteerTable').hide();
      });

  function expandAll() {
        $('.localVolunteerName').next().slideDown('normal');
        {$('.localVolunteerName').addClass('active');}
  }
  
  function collapseAll() {
        $('.localVolunteerName').next().slideUp('normal');
        {$('.localVolunteerName').removeClass('active');}
  }