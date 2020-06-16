/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//var myBackup = $('#mbodyid').clone();


$(document).ready(function () {
    window.setInterval(ajaxd, 10000);


    ajaxd();


    $('#apprvtck').click(function () {
        wrkTicketLog();
    });
    $.ajax({
        type: 'POST',
        url: 'UserData',
        data: 'transtype=drivers',
        dataType: "json",
        success: function (data) {
            var dataset = data;
            console.log(dataset);
            $('#vehalloc').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#vehalloc').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

    /////////////////////////////

    $.ajax({

        type: 'POST',
        url: 'UserData',
        data: 'transtype=drivers',
        dataType: "json",
        success: function (data) {

            var dataset = data;
            $('#drvallocid').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#drvallocid').append('<option value="' + value.drivers + '">' + value.drivers + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

////////////////

    /////////////////////////////

    $.ajax({

        type: 'POST',
        url: 'UserData',
        data: 'transtype=free_v', //free_v
        dataType: "json",
        success: function (data) {

            var dataset = data;
            $('#vehreq1Av').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#vehreq1Av').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

////////////////


    var dataset1 = ["SALOON", "S. WAGON", "SPECIAL PURPOSE", "PICKUP TRUCK", "LORRY", "SUV", "MINI BUS", "BUS/COACH", "VAN", "MOTORCYCLE"];
    $.each(dataset1, function (value1) {
        //  alert(dataset[value]);        
        $('#vehreq1').append('<option value="' + dataset1[value1] + '">' + dataset1[value1] + '</option>');
    });

    $('#logoutBtn').click(function () {
        $('#logoutModal').modal({
            backdrop: 'static',
            keyboard: false

        });
    });

    $('#depfleetid').click(function () {
        var driver = $('#uName').text();

        var rank = $('#rank').text();

        if (rank !== "DRIVER") {
            $('#apprvtck').prop("disabled", true);
        }
        //    swal(driver);
        $.ajax({
            type: 'POST',
            url: 'UserData',
            data: 'transtype=alloc&driver=' + driver,
            dataType: "json",
            success: function (data) {
                var dataset = data;
                console.log(dataset);
                $('#vehalloc').empty().append('<option value="">--Select--</option>');
                $.each(dataset, function (key, value) {
                    $('#vehalloc').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
                });


                getAllocatedRequests(driver);




//get allocated requests

                //--------------------------//
                $('#wrkTicketModal').modal({
                    backdrop: 'static',
                    keyboard: false
                });


            },
            error: function (xhr, err) {
                swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });


    });
    $('#profilePic').click(function () {

        var name = $('#uName').text();
        swal({
            title: "Exit options",
            text: "Sure to exit " + name + " ?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {
                        $.ajax({
                            type: 'GET',
                            url: 'LoginController',
                            //dataType: "json",
                            success: function () {

                                window.location.href = "/fleet/";
                            }
                        });
                    } else {
                        swal("Okay, proceed!");
                    }
                });



    });

    $("#phoneid").prop("readonly", true);
    $("#reqby").prop("readonly", true);
    $("#email").prop("readonly", true);
    $("#orgname").prop("readonly", true);
    $("#dept").prop("readonly", true);

    $("#from").prop("readonly", true);
    $("#dest").prop("readonly", true);
    $("#vehreq1").prop("readonly", true);
    $("#purposeTxt").prop("readonly", true);


    $("#avd").prop("readonly", true);


    $.ajax({
        type: 'GET',
        url: 'PopulatePage',
        dataType: "json",
        success: function (jsonobject) {
            var username = jsonobject.usr;
            var loginid = jsonobject.login;
            var designation = jsonobject.designation;
            //
            var drivers = jsonobject.drivers;
            var vehicles = jsonobject.vehicles;
            var reqs = jsonobject.reqs;
            var tickets = jsonobject.tickets;
//THis jSonObject has more than 5 attributes..I chose to take just a few
            $('#uName').text(username);
            $('#myloginId').text(loginid);

            $('#rank').text(designation);
            $('#drivers').text(drivers);
            $('#vehicles').text(vehicles);
            $('#requisitions').text(reqs);
            $('#tickets').text(tickets);



            //  swal("A driver inddeed");



            populateActivities();
        }

    });

    $("#ticketTbl").on('click', '.btn', function () {
        var currentRow = $(this).closest("tr");

        var rqId = currentRow.find("td:eq(4)").html();
        var reqid = currentRow.find("td:eq(0)").html();
        //  swal(rqId);


        if (rqId.includes("start trip")) {
            updateReq(reqid, "PRINT TICKET");
            var d = new Date();
            swal({
                icon: "success",
                title: "Trip Started Successfully at " + d,
                text: reqid
            });
            //   print(reqid);



        } else {

            updateReq(reqid, "END TRIP");
            var d = new Date();
            swal({
                icon: "success",
                title: "Trip Stopped Successfully at " + d,
                text: reqid
            });
        }


    });




});


function ajaxd() {

    $.ajax({
        type: 'GET',
        url: 'ActiveSession',
        dataType: "text",
        success: function (data) {
            if (data == "active") {
                populateActivities();
            } else {
                window.location.href = "/fleet/";
            }
        }
    });
}

function getAllocatedRequests(driver) {
    $('#ticketTbl tbody').empty();
    var request = 'driver=' + driver;
    $.ajax({
        type: 'GET',
        url: 'MyTrips',
        data: request,
        dataType: "json",
        success: function (data) {
            var response = data;
            var activitydata = '';
            var i = 0;
            activitydata += '<tbody>';
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.rqid + '</td>';
                // activitydata += '<td>' + value.request_type + '</td>';
                // activitydata += '<td>' + value.requestedon + '</td>';
                activitydata += '<td>' + value.rqby + '</td>';
                activitydata += '<td>' + value.destination + '</td>';

                //   console.log(value.status);
                if (value.status == 'enroute') {
                    activitydata += '<td> <label class="badge badge-success">' + value.status + '</td>';
                }
                if (value.status == 'not started') {
                    activitydata += '<td><label class="badge badge-danger">' + value.status + '</td>';
                }
                if (value.status == 'pending') {
                    activitydata += '<td><label class="badge badge-warning">' + value.status + '</td>';
                }

                activitydata += '<td ><button class="btn btn-outline-success btn-sm" onclick()=getRQ(this)>' + value.action + '</button></td>'
                activitydata += '</tr>';
                i += 1;

            });
            activitydata += '</tbody>';
            $('#ticketTbl').append(activitydata);
        }
    });
}


function populateActivities() {
    $('#activitiesTbl tbody').remove();


    //$('#activitiesTbl').DataTable();

    var rank = $('#rank').text();

    var data = 'rank=' + rank;

    $.ajax({

        type: 'GET',
        url: 'ActivityServlet',
        data: data,
        dataType: "json",

        success: function (data) {


            //  alert("I am here");
            var activitydata = '';
            var i = 0;
            activitydata += '<tbody>';
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.request_id + '</td>';
                activitydata += '<td>' + value.request_type + '</td>';
                activitydata += '<td>' + value.requestedon + '</td>';
                activitydata += '<td>' + value.requestedby + '</td>';

                //   console.log(value.status);
                if (value.status == 'approved') {
                    activitydata += '<td> <label class="badge badge-success">' + value.status + '</td>';
                }
                if (value.status == 'rejected') {
                    activitydata += '<td><label class="badge badge-danger">' + value.status + '</td>';
                }
                if (value.status == 'pending') {
                    activitydata += '<td><label class="badge badge-warning">' + value.status + '</td>';
                }

                activitydata += '<td ><button class="btn btn-outline-success btn-sm">View Order</button></td>'
                activitydata += '</tr>';
                i += 1;

            });
            activitydata += '</tbody>';
            $('#activitiesTbl').append(activitydata);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
$(document).ready(function () {  ///action performed for buttons
    $('#refreshactivities').click(function () {
        populateActivities();
        //   alert("sucess yolii0");
    });

    $("#activitiesTbl").on('click', '.btn', function () {
        // get the current row
        var clearance = $('#rank').text();
        var currentRow = $(this).closest("tr");

        var rqId = currentRow.find("td:eq(0)").html(); // get current row 1st table cell TD value
        var rqType = currentRow.find("td:eq(1)").html(); // get current row 2nd table cell TD value
        var col3 = currentRow.find("td:eq(2)").html(); // get current row 3rd table cell  TD value
        var stat = currentRow.find("td:eq(4)").html(); // get current row 3rd table cell  TD value
        var rqby = currentRow.find("td:eq(3)").html(); // get current row 3rd table cell  TD value
        var data = rqId + " is the selected Request!!!";

        $('#apprvhd').text(rqId);

        $('#reqby').val(rqby);


        if (rqId.includes("/TR/")) {
            $('#vehreq1Av').prop('disabled', false);

            $('#drvallocid').prop('disabled', false);

        } else {
            $('#vehreq1Av').prop('disabled', true);

            $('#drvallocid').prop('disabled', true);
        }

        //$().text();
        //        //Get the rest of the data
        var transtype = "approvstage";
        var request = 'rqid=' + rqId + '&transtype=' + transtype;
        // swal(clearance);

        if (clearance.includes("MANAGER") || clearance.includes("EXECUTIVE")) {

        } else {
            $('#aprvBtn').prop("disabled", true); //Disable the submit button

            $('#rejectbtn').prop("disabled", true); //Disable the cancel button
        }
        $.ajax({

            type: 'GET',
            url: 'RequestDataServlet',
            data: request,
            dataType: "json",

            success: function (response) {
//{"transport", "snradmin", "ticket", "security"};
//first set the stage color     
                var transport = response.transport;
                var snradmin = response.snradmin;
                var ticket = response.ticket;
                var security = response.security;

                //Approve Transport
                //Authorize Request
                //Print Ticket
                //Print Security Release


                document.getElementById('ticket').className = "badge badge-secondary"; //Default

                document.getElementById('tickchk').className = ''; //Default
/////////////////////////////////////////////////////////////////////////////////////////////////////
                if (transport == "approved") {                                                            //approved transport

                    document.getElementById('trav').className = "badge badge-success";

                    document.getElementById('trachk').className = "fa fa-check-circle";

                } else if (transport == "rejected") {
                    document.getElementById('trav').className = "badge badge-danger";//0732

                    document.getElementById('trachk').className = "fa fa-times-circle";

                } else {
                    document.getElementById('trav').className = "badge badge-secondary"; //Default

                    document.getElementById('trachk').className = ''; //Default


                    $('#aprvBtn').text('Approve Transport'); //Change text of the submit button

                    $('#rejectbtn').text('Decline Transport'); //Change text of the submit button

                    if (clearance == "MANAGER") {
                        $('#aprvBtn').prop("disabled", false); //Enable the submit button

                        $('#rejectbtn').prop("disabled", false); //Enable the cancel button


                    }


                }
////////////////////////////////////////////////////////////////////////////////////////////////////
                if (snradmin == "approved") {                                                                 //approved snradmin
                    document.getElementById('snr').className = "badge badge-success";

                    document.getElementById('snrchk').className = "fa fa-check-circle";

                } else if (snradmin == "rejected") {

                    document.getElementById('snr').className = "badge badge-danger";//0732

                    document.getElementById('snrchk').className = "fa fa-times-circle";

                } else {
                    document.getElementById('snr').className = "badge badge-secondary"; //Default

                    document.getElementById('snrchk').className = ''; //Default


                    if (transport == "approved") {
                        if (clearance == "SENIOR MANAGER") {
                            $('#aprvBtn').prop("disabled", false); //Disable the submit button

                            $('#rejectbtn').prop("disabled", false); //Disable the cancel button


                        }
                        $('#aprvBtn').text('Authorize Request'); //Change text of the submit button
                        $('#rejectbtn').text('Decline Request'); //Change text of the submit button
                    }

                }
///////////////////////////////////////////////////////////////////////////////////////////////////////                

                if (ticket == "approved") {                                                                   //approved ticket 
                    document.getElementById('ticket').className = "badge badge-success";

                    document.getElementById('tickchk').className = "fa fa-check-circle";

                } else if (ticket == "rejected") {
                    document.getElementById('ticket').className = "badge badge-danger";//0732

                    document.getElementById('tickchk').className = "fa fa-times-circle";

                } else {

                    document.getElementById('ticket').className = "badge badge-secondary"; //Default

                    document.getElementById('tickchk').className = ''; //Default


                    if (snradmin == "approved") {
                        $('#aprvBtn').text('Print Ticket');
                        //    ('#rejectbtn').text('Decline Transport'); //Change text of the submit button
                        $('#aprvBtn').prop("disabled", false); //Enable the submit button

                        $('#rejectbtn').prop("disabled", true); //Disable the cancel button

                    }

                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////                
                if (security == "approved") {                                                                //approved security
                    document.getElementById('secrel').className = "badge badge-success";

                    document.getElementById('securityr').className = "fa fa-check-circle";

                } else if (security == "rejected") {
                    document.getElementById('secrel').className = "badge badge-danger";//0732

                    document.getElementById('securityr').className = "fa fa-times-circle";

                } else {

                    document.getElementById('secrel').className = "badge badge-secondary"; //Default

                    document.getElementById('securityr').className = ''; //Default

                    if (ticket == "approved") {

                        $('#aprvBtn').text('Print Security Release'); //Change text of the submit button

                        $('#aprvBtn').prop("disabled", false); //Enable the submit button

                        $('#rejectbtn').prop("disabled", false); //Enable  the cancel button

                    }
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////                

                populateReqDetails(rqId);

                $('#apprVrqModal').modal({

                    keyboard: false,
                    backdrop: 'static',
                    refresh: true

                });


            },
            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });



    });
    $("#showdrv").click(function () {

        //alert("Drivers 1")
    });

    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        var patt = new RegExp(value, "i");

        $('#activitiesTbl').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });


    $('#searchfuel').on('keyup', function () {
        var value = $(this).val();
        var patt = new RegExp(value, "i");

        $('#mtcefueltbl').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });


    $("#showreqs").click(function () {

        // alert("Requests")
        populateActivities();
    });
    $("#showticket").click(function () {
        // $("#wait").css("display", "block");
        populateMtceAndFuelingData();
        $('#fuelAndMtcemodal').modal({
            keyboard: true,
            backdrop: 'static'
        })
    });
});
$(document).ready(function () {

    $('#closeverbtn').click(function () {
        //  $("#verRcpt").remove();
        // $('div.modal-backdrop').remove();

        //$('#verRcpt .modal-backdrop').remove();

        // $('#verRcpt').removeClass('modal fade in backdrop');

    });
    $("#aprvBtn").click(function () {
        var transtype = $("#aprvBtn").text().toString().toUpperCase();
        var rqid = $("#apprvhd").text();

        var vehissued = $('#vehreq1Av').val();
        var driver = $('#drvallocid').val();
        var officer = $('#reqby').val();


        var amt = $('#invamt').val().replace(",", "");

        var threshhold = parseFloat(amt);

        if (transtype == "APPROVE TRANSPORT" && rqid.includes("/TR/")) {   //verify receipt 
            if (vehissued === "" || driver === "") {

                swal({
                    icon: "warning",
                    title: "Vehicle issued and driver allocated cannot be empty",
                    text: "Check again"
                });

            } else {
                if (threshhold > 0) {                // but when amount is zero or more
                    $('#rcptver').val("");
                    $('#amtfld').text(amt);
                    document.getElementById("rcptver").focus();
                    $('#verRcpt').modal({
                        backdrop: 'static',
                        keyboard: false
                    });
                } else {                                    //else just do stuff without verifying
                    updateReq(rqid, transtype);
                }
            }
        } else {                                         //do stuff anyway
            updateReq(rqid, transtype);
        }


    });

    $("#rejectbtn").click(function () {
        var transtype = $("#rejectbtn").text().toString().toUpperCase();
        var rqid = $("#apprvhd").text();
        swal(transtype, rqid);
    });
    $('#verbtn').click(function () {
        var rcptno = $('#rcptver').val();
        alert(rcptno);
        var request = 'receiptno=' + rcptno;
        $.ajax({
            type: 'POST',
            url: 'RequestTransactions',
            data: request,
            dataType: "text",

            success: function (data) {
                var response = data;
                //swal(response);
                if (response == "Verified") {
                    var transtype = $("#aprvBtn").text().toString().toUpperCase();
                    var rqid = $("#apprvhd").text();
                    //  $('#verRcpt').dialog('close');
                    //console.log("done");
                    updateReq(rqid, transtype);


                    // $('#verRcpt').hide();
                } else {
                    swal("Invalid or Wrong Entry", response);
                }

            },

            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });

});
function populateReqDetails(rqid) {

    var requestid = rqid;
    var transtype = "reqinfo";
    var request = 'rqid=' + rqid + '&transtype=' + transtype;

    $.ajax({

        type: 'GET',
        url: 'RequestDataServlet',
        data: request,
        dataType: "json",

        success: function (response) {

            var organization = response.organization;
            var department = response.department;
            var from = response.from;
            var destination = response.destination;
            var vehiclereq = response.vehiclereq;
            var pax = response.pax;
            var tel = response.tel;
            var email = response.email;
            var purpose = response.purpose;

            //
            var driver = response.driver;
            var vehicleissued = response.vehicleissued;

//vehreq1Av
//drvallocid
            console.log(vehicleissued);
            $('#orgname').val(organization);
            $('#dept').val(department);
            $('#from').val(from);
            $('#dest').val(destination);
            $('#vehreq1').val(vehiclereq);

            $('#phoneid').val(tel);
            $('#email').val(email);

            $('#purposeTxt').val(purpose);

            $('#vehreq1Av').val(vehicleissued);
            $('#drvallocid').val(driver);


            populateInvoice(requestid);





            // $('#orgname').text(organization);
            // $('#orgname').text(organization);                                            
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });


}




function  populateMtceAndFuelingData() {


    $.ajax({
        type: 'GET',
        url: 'FuelAndMTCE',
        //data: request,
        dataType: "json",

        success: function (data) {

            $('#mtcefueltbl tbody').remove();
            var activitydata = '';

            activitydata += '<tbody>';
            $.each(data, function (key, value) {


                activitydata += '<tr>';

                activitydata += '<td>' + value.vehicle + '</td>';
                activitydata += '<td>' + value.servicetype + '</td>';
                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td>  ' + value.planned + '</td>';


                var status = value.status;

                var action = value.action;


//Status
                if (value.status == 'completed') {
                    activitydata += '<td><label class="badge badge-success">' + value.status + '</td>';
                } else {
                    activitydata += '<td><label class="badge badge-warning">' + value.status + '</td>';
                }
//Next column---Preview Button
                activitydata += '<td ><button onclick="previewPDF(this)" class="btn btn-outline-primary btn-sm ">Preview Ticket</button></td>'

                //Next column---Action   //
                if (value.action == 'completed') {
                    activitydata += '<td ><button  class="btn btn-outline-success btn-sm" disabled>Service Completed</button></td>'

                } else {
                    activitydata += '<td ><button onclick="upd(this)"  class="btn btn-outline-danger btn-sm">Mark As Complete</button></td>'
                }





                activitydata += '</tr>';
                // i += 1;


            });
            activitydata += '</tbody>';
            $('#mtcefueltbl').append(activitydata);

        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function previewPDF(element) {

    var row = element.parentNode.parentNode.rowIndex;

    var tbl = document.getElementById("mtcefueltbl");
    var col = 0;

    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    col = 2;
    var cellvalue2 = tbl.rows[row].cells[col].innerHTML;


//    swal(cellvalue);
  //  alert(cellvalue + " " + cellvalue2)
    printMTCE(cellvalue2, cellvalue);
}

function upd(element) {

    var row = element.parentNode.parentNode.rowIndex;

    var tbl = document.getElementById("mtcefueltbl");



    var col = 2;
    var requestid = tbl.rows[row].cells[col].innerHTML;


//    swal(cellvalue);
    //  alert(cellvalue + " " + cellvalue2)


    var request = "rqid=" + requestid;


    $.ajax({
        type: 'POST',
        url: 'FuelAndMTCE',
        data: request,
        dataType: "text",
        success: function (data) {
            swal({
                icon: "success",
                title: requestid + " has been Marked as Complete"
            })
            populateMtceAndFuelingData();
        }
    });
}

function printMTCE(reqid, vehreg) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            var ip = data;
            var doctype = "ALLMTCE";
            var trans = "pdfname=" + doctype + "&refno=" + vehreg + "&reqid=" + reqid;
            window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function  populateInvoice(requestid) {
    var rqid = requestid;
    var transtype = "invoice";
    var request = 'transtype=' + transtype + '&rqid=' + rqid;

    $.ajax({
        type: 'GET',
        url: 'RequestDataServlet',
        data: request,
        dataType: "json",

        success: function (data) {
            var activitydata = '';
            var i = 0;
            var total = 0.0;
            activitydata += '<tbody>';
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.particulars + '</td>';
                activitydata += '<td>' + value.unitcost + '</td>';
                activitydata += '<td>' + value.total + '</td>';

                var lnetot = parseFloat(value.total);

                console.log(lnetot);



                activitydata += '</tr>';
                // i += 1;
                total += lnetot;

            });
            activitydata += '</tbody>';
            $('#invoiceTbl').empty().append(activitydata);
            console.log(total);
            $('#invamt').val(addCommas(Decimal.convert(total, 2)));
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function alertMe() {
    swal("Hello");
}
function addCommas(num) {
    var str = num.toString().split('.');
    if (str[0].length >= 4) {
        //add comma every 3 digits befor decimal
        str[0] = str[0].replace(/(\d)(?=(\d{3})+$)/g, '$1,');
    }
    /* Optional formating for decimal places
     if (str[1] && str[1].length >= 4) {
     //add space every 3 digits after decimal
     str[1] = str[1].replace(/(\d{3})/g, '$1 ');
     }*/
    return str.join('.');
}

var Decimal = {
    convert: function (number, decimal_places) {
        if (typeof number === 'number' && typeof decimal_places === 'number') {
            var denominator = Math.pow(10, decimal_places),
                    rounded_number = Math.round(number * denominator) / denominator;

            return rounded_number;
        } else {
            return number;
        }
    }
};

function printElement(elem) {
    var domClone = elem.cloneNode(true);

    var $printSection = document.getElementById("printSection");

    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }

    $printSection.innerHTML = "";
    $printSection.appendChild(domClone);
    window.print();
}


function updateReq(reqid, transtype) {
    $("#overlay").css("display", "block");

    var vehreq = $('#vehreq1').val();

    var vehissued = $('#vehreq1Av').val();
    var driver = $('#drvallocid').val();
    var officer = $('#reqby').val();

    var request = 'transtype=' + transtype + '&rqid=' + reqid + '&vehreq=' + vehreq + '&vehissued=' + vehissued + '&driver=' + driver + '&officer=' + officer;
    var req = reqid;
    $.ajax({
        type: 'GET',
        url: 'RequestTransactions',
        data: request,
        dataType: "text",

        success: function (data) {
            var response = data;

            // swal(transtype);

            if (transtype.toString() == "PRINT TICKET") {
                var path = response.toString().trim();

                //  changePath(path);
                document.getElementById("embedpath").src = path;

                var output = document.getElementById("embedpath").src;
                //$('#embedpath').EZView(); 
                print(reqid);



            } else {
                swal("Done process...", response);
            }

            populateActivities();
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
    $("#overlay").css("display", "none");

}





function print(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            var ip = data;
            var doctype = "TICKET";
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function sendMsg() {
    swal("McKenzie", "Said Hello");
}


function refreshApproval() {

}

function changePath(path2file) {
    var path = path2file;
    console.log(path);

    document.getElementById("embedpath").src = path;
    $('#embedpath').attr('src', path);

    $('#pdfModal').modal({

    });
}


function wrkTicketLog() {
    var transtype = "insert";

    var fullname = $('#reqbyid').val();
    var refno = $('#reqbyid').val();
    var emailid = "";
    var telno = "";
    var rqtype = "REQUEST FOR INTERNAL USE";
    var organization = $('#orgnameid').val();
    var dept = $('#deptid1').val();
    var from = $('#fromid').val();
    var destination = $('#destid').val();
    var distance = $('#avd1').val();
    var pax = "";

    var vehrequested = "";
    var purpose = $('#purposeTxt1').val();
    var remarks = $('#purposeTxt1').val();

    var enteredby = $('#uName').text();

    var driver = $('#uName').text();
    var vehicle = $('#allochehregid').val();//allochehregid

    var requestno = "";

    if (destination.length < 2 || distance.length < 1) { //vehreq is Mandatory
        swal("Missing Entry", "Destination or distance Cannot be Empty");

    } else {


        var data = 'fullname=' + fullname + '&refno=' + refno + '&emailid=' + emailid + '&telno=' + telno + '&rqtype=' + rqtype + '&organization=' + organization + '&dept=' + dept + '&from=' + from +
                '&destination=' + destination + '&distance=' + distance + '&pax=' + pax +
                '&vehrequested=' + vehrequested + '&purpose=' + purpose + '&remarks=' + remarks +
                '&transtype=' + transtype + '&enteredby=' + enteredby + '&vehicle=' + vehicle + '&driver=' + driver;
        //ajax to insert form data
        //   alert(data);
        //swal (data);
        console.log(data);
        $.ajax({
            type: 'POST',
            data: data,
            url: 'RequestDataServlet',
            dataType: "text",
            success: function (text) {




                //   alert("Text: " + text);

                requestno = text.toString();



                swal({
                    icon: "success",
                    title: requestno + " Sent for Approval",
                    text: "Success"
                });
                //   alert("Parsed: " + requestno);


                //   window.rqNo = requestno;


                // insertTable(window.rqNo)





                //     alert("Got this: " + text);
                //$("#msgPane").html(data);


            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });



        //  window.rqNo = requestno;
    }
}