/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    myColleagues1();

    $('#homeid').click(function () {
        window.location.href = "/mtportal/";
    });


    $('#wkldid').click(function () {
        populateDoneActivities();
        window.location.href = "#threet";
    });



    $('#backid').click(function () {
        window.location.href = "/mtportal/maintenance/mainpage.html";
    });

    ///maintenance/mainpage.html

    $('#typeofissueid').select2({

    })
    
    $('#staffAdditionid').select2({
        
    }).on('change', function () {
        
        var selectedstaff = $(this).val();
        var selected = $('#locid').val();
        //swal(selected);
        if (selected.length > 2) {
            var arr = selected.toString().split("/");


            var section = arr[1];
            var station = arr[0];
            actualTrans(selectedstaff, selected, "addition");
            
            

           // listActiveStaff(section, station);
        }else{
            swal("No section selected")
        }

    });;
    
    $('#locid').select2({

    }).on('change', function () {
        var selected = $(this).val();
        //swal(selected);
        if (selected.length > 0) {
            var arr = selected.toString().split("/");

            var section = arr[1];
            var station = arr[0];

            listActiveStaff(section, station);
        }

    });
    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

        $('#taskpendingtbd').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });




    $('#searchdb').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

//        $('#taskpendingtbd').find('tr').each(function () {
//            var $table = $(this);
//
//            if (!($table.find('td').text().search(patt) >= 0)) {
//                $table.not('.t_head').hide();
//            }
//            if (($table.find('td').text().search(patt) >= 0)) {
//                $(this).show();
//            }
//
//        });

    });

    populatePendingWorkorders();

    populateRequests()
    appendLocations()
    window.setInterval(appendLocations, 100000);
    window.setInterval(populateRequests, 100000);
    //populateRequests();

    $('#raiseirqid').click(function () {
        var input1 = $('#requesterid');
        var input2 = $('#typeofissueid');
        var input3 = $('#locid');
        var input4 = $('#comments');

        if (parseInt(input1.val().length) > 1 && parseInt(input2.val().length) > 1 && parseInt(input3.val().length) > 1) {

            var request = "contactperson=" + input1.val() + "&issuetype=" + input2.val() + "&location=" + input3.val() + "&desc=" + input4.val();
            //      swal(request+"\n"+input1.val().length+","+input2.val().length+","+input3.val().length);
            postRequest(request);
        } else {
            input1.css("border", "2px solid red");
            input2.css("border", "2px solid red");
            input3.css("border", "2px solid red");
            input4.css("border", "2px solid red");

            swal({
                icon: "warning",
                title: "Error in the details provided"
            })
            $('#newirid').click();

            //var request = "contactperson=" + input1.val() + "&issuetype=" + input2.val() + "&location=" + input3.val() + "&desc=" + input4.val();
            //    swal(request);
            //  swal(request+"\n"+input1.val().length+","+input2.val().length+","+input3.val().length);
        }
    })
});

function appendLocations() {
    $.ajax({
        type: 'POST',
        url: '../ServiceLocations',
        //data: request,
        dataType: "json",
        success: function (data) {
            $('#locid').empty();
            $('#locid').append('<option>-</option>');
            console.log("I am here")
            $.each(data, function (key, value) {

                var name = value.name;
                $('#locid').append('<option value="' + name + '">' + name + '</option>');

                //    $('#example').append('<option value="' + data[value+ '">' + data.name + '</option>');
            });
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data"
            });
        }
    });
}





function postRequest(request) {

    $.ajax({
        type: 'POST',
        url: '../ServiceRequests',
        data: request,
        dataType: "text",
        success: function (data) {
            if (data.toString().includes("Error")) {
                swal({
                    icon: "warning",
                    title: data
                })
            } else {
                swal({
                    icon: "success",
                    title: data
                });


            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data"
            });
        }
    });


}


function populateRequests() {
    $.ajax({
        type: 'GET',
        url: '../ServiceRequests',
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 1;
            $('#taskpendingtbd').empty();
            //{"rqid","rqtype","contactperson","location","description","tstamp","uname","percent"};
            $.each(data, function (key, value) {
                activitydata += '<tr>';
                activitydata += '<td>' + i + '.<strong>' + value.rqid + '</strong></td>'; //Will loop through specifics below here
                activitydata += '<td><br><strong>Request Type:</strong> ' + value.rqtype + '<br><strong>Location:</strong> ' + value.location + '<br><strong>Raised on:</strong> ' + value.tstamp + '<br><strong>Contact Person: </strong>' + value.contactperson + '</td>'; //1
                //I have looped through specifics

                var comp = parseInt(value.percent);


                if (comp < 40) {
                    activitydata += '<td><div class="progress progress-xs"><div class="progress-bar progress-bar-danger" style="width: ' + value.percent + '%"></div></div> </td>';
                } else if (comp >= 40 && comp < 60) {
                    activitydata += '<td><div class="progress progress-xs"><div class="progress-bar progress-bar-warning" style="width: ' + value.percent + '%"></div></div> </td>';
                } else if (comp >= 60 & comp < 100) {
                    activitydata += '<td><div class="progress progress-xs"><div class="progress-bar progress-bar-primary" style="width: ' + value.percent + '%"></div></div> </td>';
                } else {
                    activitydata += '<td><div class="progress progress-xs"><div class="progress-bar progress-bar-success" style="width: ' + value.percent + '%"></div></div> </td>';
                }
//last column



                if (comp < 40) {
                    activitydata += '<td><span class="badge bg-red">' + value.percent + '%</span></td>'; //3F
                } else if (comp >= 40 && comp < 60) {
                    activitydata += '<td><span class="badge bg-yellow">' + value.percent + '%</span></td>'; //3F
                } else if (comp >= 60 & comp < 100) {
                    activitydata += '<td><span class="badge bg-blue">' + value.percent + '%</span></td>'; //3F
                } else {
                    activitydata += '<td><span class="badge bg-green">' + value.percent + '%</span></td>'; //3F
                }

                activitydata += '</tr>';
                i += 1;
            });

            $('#taskpendingtbd').empty().append(activitydata);


        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem loading transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });

}



function populatePendingWorkorders() {

    console.log("I am here this minute...")
    $.ajax({
        type: 'POST',
        url: '../WorkOrders',
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 1;
            $('#table-custom-2bd').empty();
            //{"rqid","rqtype","contactperson","location","description","tstamp","uname","percent"};
            $.each(data, function (key, value) {
                console.log("value.rqid.....")
                activitydata += '<tr>';
                activitydata += '<td>' + i + '.</td>'; //Index
                activitydata += '<td>' + value.rqid + '</td>'; //RQID
                // activitydata += '<td><div data-role="collapsible" data-mini="true"> <h4>'+value.rqtype+'</h4><p><strong>Location:</strong> ' + value.location + '<br><strong>Raised on:</strong> ' + value.tstamp + '<br><strong>Contact Person: </strong>' + value.contactperson + '<br> </p></div></td>'
                //activitydata += '<td><strong>Request Type:</strong> ' + value.rqtype + '<br><strong>Location:</strong> ' + value.location + '<br><strong>Raised on:</strong> ' + value.tstamp + '<br><strong>Contact Person: </strong>' + value.contactperson + '<br><br><button type="button" data-icon="arrow-r" onclick="openmodal(this)">Process Order</button></td>'; //1
                activitydata += '<td><strong>Request Type:</strong> ' + value.rqtype + ' <br><strong>Location:</strong> ' + value.location + ' <br><strong>Raised on:</strong> ' + value.tstamp + ' <br><strong>Contact Person: </strong>' + value.contactperson + ' <br><br><a href="#two" data-icon="arrow-r" onclick="openmodal(this)">Process Order</a></td>'; //1
                //  activitydata += '<td><button type="button" class ="btn btn-link" onclick="openmodal(this)">Process Order</button></td>'; //Index
                //  
                // data-rel="dialog"
                //I have looped through specifics     
                activitydata += '<td></td>'; //Index




                activitydata += '</tr>';
                i += 1;
            });

            $('#table-custom-2bd').empty().append(activitydata);


        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem loading transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });

}


function openmodal(element) {

    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("table-custom-2");
    var col = 1;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

//    swal(cellvalue);
    //alert(cellvalue)

    $("#contentid").text(cellvalue)

    populateActivities(cellvalue);

    var col = 2;

    var content = tbl.rows[row].cells[col].innerText;


    $("#wholeinfo").text(content.replace("Process Order", ""));





}


function addact() {
    var infotoadd = $('#activtoadd').val();

    if (infotoadd.toString().length > 2) {

        var activitydata = '';
        var i = 1;
        //  $('#table-custom-2bd').empty();

        console.log("value.rqid.....")
        activitydata += '<tr>';
        activitydata += '<td>' + infotoadd + '.</td>'; //Index


        activitydata += '<td>    <a href="#" class="ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all" onclick="delrow(this)">No text</a></td>'; //Index

        activitydata += '<td>  <label>       <input type="checkbox" name="checkbox-0 " checked disabled >Completed </label></td>'; //Index




        activitydata += '</tr>';
        i += 1;


        $('#activitiestblm').append(activitydata);


        $('#activtoadd').val("");

        $('#activtoadd').focus();


        var refid = $("#contentid").text();
        var issue = "default";
        updateWorkorder(infotoadd, refid, issue);

        populateActivities(refid);
    } else {
        swal({icon: "warning", title: "Sorry, you cannot add an empty activity"});
    }


}

function delrow(element) {
    var row = element.parentNode.parentNode.rowIndex;
    document.getElementById("activitiesTbl").deleteRow(row);
}

function updateWorkorder(activity, refid, issue) {
    var request = "activity=" + activity + "&refid=" + refid + "&issue=" + issue;

    //   alert(request)
    $.ajax({
        type: 'GET',
        url: '../WorkOrders',
        data: request,
        // dataType: "text/plain",
        success: function (data) {
            swal(data)
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data " + err
            });
        }
    });

}


function populateActivities(refid) {
    var request = "&refid=" + refid;


//swal("Populating ...."+refid)
    //   alert(request)
    $.ajax({
        type: 'POST',
        url: '../PopulateWorkorder',
        data: request,
        dataType: "json",
        success: function (data) {


            var activitydata = '';
            var i = 1;


            // alert(data);

            $.each(data, function (key, value) {


                activitydata += '<tr>';
                activitydata += '<td>' + value.action + '.</td>'; //Index


                activitydata += '<td>    <a href="#" class="ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all" onclick="delrow(this)">No text</a></td>'; //Index

                activitydata += '<td>  <label>       <input type="checkbox" name="checkbox-0 " checked disabled >Completed </label></td>'; //Index




                activitydata += '</tr>';
                i += 1;

            });
            $('#activitiestblm').empty().append(activitydata);


            $('#activtoadd').val("");

            $('#activtoadd').focus();
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data " + err
            });
        }
    });

}



function populateDoneActivities() {

    $.ajax({
        type: 'GET',
        url: '../PopulateWorkorder',
        // data: request,
        dataType: "json",
        success: function (data) {


            var activitydata = '';
            var i = 1;


            // alert(data);

            $.each(data, function (key, value) {


                activitydata += '<tr>';
                activitydata += '<td>' + value.refid + '</td>'; //Index

                activitydata += '<td>' + value.location + '</td>';

                activitydata += '<td>' + value.requesttype + '.</td>';

                activitydata += '<td> <button type="button" class ="btn btn-sm" onclick=printwslip(this)>Print</button></td>'; //Index --WORKORDERPDF


                activitydata += '</tr>';
                i += 1;

            });
            $('#taskshandledbd').empty().append(activitydata);
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data " + err
            });
        }
    });

}
function printwslip(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("taskshandled");

    var col = 0;//Refid
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    print2(cellvalue, "WORKORDERPDF");
}

function closejob(transtype) {

    var refid = $('#contentid').text();

    //alert(refid + "-" + transtype);

    if (refid.toString().length < 2) {
        alert("No work order selected!")
    } else {

        var rowcount = document.getElementById('activitiesTbl').getElementsByTagName("tbody")[0].getElementsByTagName("tr").length;

        //document.getElementById('activitiesTbl').rows.length;

//        alert(rowcount)
        if (parseInt(rowcount) > 0) {

            var request = "transtype=" + transtype + "&refid=" + refid;

            //   alert(request)
            $.ajax({
                type: 'POST',
                url: '../CloseJob',
                data: request,
                dataType: "text",
                success: function (data) {
                    swal(data);
                    populatePendingWorkorders()
                    window.location.href = "#one"
                },
                error: function (xhr, err) {

                    swal({
                        icon: "warning",
                        title: "Error loading data " + err
                    });
                }
            });
        } else {
            swal({
                icon: "warning",
                title: "Operation permitted:",
                text: "You cannot close a job without logging the activities involved"
            });
        }
    }

}

function print2(reqid, typeadoc) {
    $.ajax({
        type: 'GET',
        url: '../HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = typeadoc.toString().toUpperCase();
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function actualTrans(staffname, selected, transtype) {

    var arr = selected.toString().split("/");

    var section = arr[1];
    var station = arr[0];

    var request = "staffname=" + staffname + "&section=" + section + "&transtype="+transtype+"&station="+station;
    $.ajax({
        type: 'POST',
        url: '../AllocatedStaff',
        data: request,
        dataType: "text",
        success: function (data) {
            swal(data);

            listActiveStaff(section, station)
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function deleteAlloc(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("allocatedUsers");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    var selectedSection = $('#locid').val();


    swal({
        title: "Please Confirm this Information to be correct before deleting: ",
        text: "Staff Name/No: " + cellvalue + "\nCoverage Area: " + selectedSection,
        icon: "info",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {

                    actualTrans(cellvalue, selectedSection,"deletion");
                } else {
                    swal("Transaction aborted!");
                }
            });



}

function listActiveStaff(section, station) {
    var request = "section=" + section + "&station=" + station;
    $.ajax({
        type: 'GET',
        url: '../AllocatedStaff',
        data: request,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 1;


            // alert(data);

            $.each(data, function (key, value) {


                activitydata += '<tr>';
                activitydata += '<td>' + value.staffname + '</td>'; //Index



                activitydata += '<td> <button type="button" class ="btn btn-link btn-danger" onclick=deleteAlloc(this)>Delete</button></td>'; //Index --WORKORDERPDF


                activitydata += '</tr>';
                i += 1;

            });
            $('#allocatedUsersbd').empty().append(activitydata);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function myColleagues1() {

    // var request = "department=" + department;
    $.ajax({
        type: 'POST',
        url: '../AskHRC',
        //    data: request,
        dataType: "json",
        success: function (data) {
            $('#staffAdditionid').empty();
            $('#staffAdditionid').append('<option>-Select-</option>');
            console.log("I am here")
            $.each(data, function (key, value) {

                var name = value.name;
                $('#staffAdditionid').append('<option value="' + name + '">' + name + '</option>');
                //    $('#example').append('<option value="' + data[value+ '">' + data.name + '</option>');
            });
        },
        error: function (xhr, err) {

            console.log(xhr, err)
//            swal({
//                icon: "warning",
//                title: "Error loading data"
//            });
        }
    });
}
