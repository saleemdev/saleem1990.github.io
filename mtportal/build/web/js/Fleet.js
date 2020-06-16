/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var approvlevel = "";
var cardtobealloc = "";
$(document).ready(function () {


    bioData();


    // === Sidebar navigation === //
    // window.setInterval(ajaxd, 10000);

    $('#showlogsid').click(function () {
        var stat = $(this).text();

        if (stat.toString().includes("Show")) {
            allTransportLogs();
            $(this).text("Hide Logs");
        }else{
            
           $('#fleetrqtblbd').empty();
           $(this).text("Show Previous Logs");
        }
    })

    window.setInterval(validate("SENIOR MANAGEMENT"), 10000);
    window.setInterval(validate("TRANSPORT MANAGER"), 10000);



    $('#addnewcardbtn').click(function () {
        var cardtypeselect = $('#cardtypeselect').val();
        var cardno = $('#newcardnoreg').val();
        var openingamt = $('#openingcardbaltext').val();

        if (!cardno || !openingamt) {
            $('#newcardnoreg').css('border', '2px solid red');
            $('#openingcardbaltext').css('border', '2px solid red')
        } else {
            if (parseInt(openingamt) > 0) {

                if (cardtypeselect.toString().includes("INVOICE")) {
                    cardno = 'INV-' + cardno;
                }
                swal({
                    title: "Please Confirm this Information to be correct before clicking [OK]: ",
                    text: "Card No: " + cardno + "\nOpening Balance: " + openingamt,
                    icon: "info",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                allocateCard("allocate", cardno, "-");
                                allocateCardAmt(cardno, 0, openingamt);
                                //saveEntitleMent(request, pf, "");
                            } else {
                                swal("Transaction aborted!");
                            }
                        });
            } else {
                swal("Zero values not allowed");
            }
        }
    })
    $('#nwfuelcard').click(function () {
        $('#cardnewtab').click();
    });

//    $('#fuelrqbtn').click(function(){
//        $('#fuelrqsmodal').modal({
//            backdrop:'static'
//        });
//    })
    $("#reconcilecardbal").click(function () {
        var cardno = $('#cardnorecontxt').val();
        var oldamt = $('#cardbalrecon1').val();
        var newamt = $('#cardbalrecon2').val();

        if (parseInt(newamt) > 0) {


            swal({
                title: "Please Confirm this Information to be correct before clicking [OK]: ",
                text: "Card No: " + cardno + "\nCurrent Balance: " + oldamt + "\nNew Balance " + newamt,
                icon: "info",
                buttons: true,
                dangerMode: true,
            })
                    .then((willDelete) => {
                        if (willDelete) {

                            allocateCardAmt(cardno, oldamt, newamt);
                            //saveEntitleMent(request, pf, "");
                        } else {
                            swal("Transaction aborted!");
                        }
                    });

        } else {
            swal("Zero Balance Not allowed");
        }
    });

    $('#fuelcardbtn').click(function () {
        populateCards();
    });

    $('#vehfcard').on('change', function () {
        var vehicle = $('#vehfcard').val();
        var card = window.cardtobealloc;

        if (card.length > 2) {

            swal({
                title: "Please Confirm this Information to be correct before clicking [OK]: ",
                text: "Vehicle Registration: " + vehicle + "\nCard No: " + card,
                icon: "info",
                buttons: true,
                dangerMode: true,
            })
                    .then((willDelete) => {
                        if (willDelete) {
                            allocateCard("allocate", card, vehicle);
                            //saveEntitleMent(request, pf, "");
                        } else {
                            swal("Transaction aborted!");
                        }
                    });
        } else {
            swal("No card Selected!!")
        }
    })
    $('#postfrq').click(function () {
        var cardno = $('#cardnoid').val()
        var cardbal = $('#cardbalid').val()
        var vehreg = $('#vehfuelreg').val()
        var fueltype = $('#fueltypeid').val()
        var lastfuelled = $('#lastfuelled').val()
        var previousmileage = $('#prvmileage').val()
        var currmileage = $('#currentmileageid').val()
        var amountrq = $('#litresid').val()
        var amtperlitre = $('#amtperlitre').val()

        if (vehreg.length < 3 || currmileage.length < 2 || amountrq.length < 2 || amtperlitre.length < 2) {
            swal({
                icon: "warning",
                timer: 3000,
                title: "Please Check that all Mandatory fields are entered"
            })
        } else {
            var actualbal = parseFloat(cardbal);


            var litresrequested = parseFloat(amountrq);

            var priceperlitre = parseFloat(amtperlitre);
            var total = litresrequested * priceperlitre;

            if (total > actualbal) {
                swal({
                    icon: "warning",
                    title: "You cannot order fuel exceeding your card balance. Please top up your fuel card and try again"})
            } else {
                //swal("good to go!")
                //postfrq

                var request = 'cardno=' + cardno + "&vehreg=" + vehreg + "&quantity=" + amountrq + "&fueltype=" + fueltype + "&amount=" + priceperlitre + "&comments=FUEL REFILL&transtype=fuel&mileage=" + currmileage;


                // swal(request);

                swal({
                    title: "Please Confirm this Information to be correct before clicking [OK]: ",
                    text: "Vehicle Registration: " + vehreg + "\nCard No: " + cardno + "\nFuel Type: " + fueltype + "\nLitres Requested" + amountrq + "\n\nPrice Per Litre: " + amtperlitre + "\n\nCurrent Mileage" + currmileage + " ",
                    icon: "info",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                postFleetRQ(request);
                                //saveEntitleMent(request, pf, "");
                            } else {
                                swal("Transaction aborted!");
                            }
                        });
            }


        }


    });


    $('#vehfuelreg').on("change", function () {

        // swal("::");

        var vehreg = $(this).val();
        // swal(vehreg);
        populateVehDetails(vehreg);

    });

    $("#vehiclerequestedid").select2({

    }).on("change", function () {
        var name = $('#vehiclerequestedid').val();
        // alert(name);
    });

    $('#contactslist').select2({

    }).on("change", function () {
        var name = $('#contactslist').val();
        //alert(name);
        var arr = name.toString().split("/");
        var staffid = arr[1];

        loadInManifest(staffid);
    });

    populateStaff();

    populateVevicleClasses();
    var dates2 = $('#fleetdept').datepicker({
        autoclose: true,
        // minDate: dateToday,
        format: 'yyyy/mm/dd',
        // minDate: dateToday,

    });



    $('#savefleetrq').click(function () {
        var rqtype = $('#rqtypeselectid').val();
        var org = $('#organizationreqid').val();
        var from = $('#fromid').val();
        var dest = $('#destid').val();
        var departuredate = $('#fleetdept').val();
        var departuretime = $('#depttime').val();
        var vehreq = $('#vehiclerequestedid').val();
        var purpose = $('#purposeid').val();


        var request = 'rqtype=' + rqtype + "&organization=" + org + "&dept=&from=" + from + "&destination=" + dest + "&vehrequested=" + vehreq + "&purpose=" + purpose + "&transtype=insert&depdate=" + departuredate + "&deptime=" + departuretime;
        var rowCount = $('#manifesttbl tr').length;


        if (rqtype.toString().length < 2 || !purpose || !vehreq || !from || !dest) {
            $('#rqtypeselectid').css("border", "2px solid red");
            $('#organizationreqid').css("border", "2px solid red");
            $('#fromid').css("border", "2px solid red");
            $('#destid').css("border", "2px solid red");
            $('#fleetdept').css("border", "2px solid red");
            $('#depttime').css("border", "2px solid red");
            $('#vehiclerequestedid').css("border", "2px solid red");
            $('#purposeid').css("border", "2px solid red");

            swal({
                icon: "warning",
                title: "Please check your details for missing information",
                text: request
            })
        } else {
            if (parseInt(rowCount) != parseInt(1)) {

                swal({
                    title: "Please Confirm this Information to be correct before clicking [OK]: ",
                    text: "Request type: " + rqtype + "\nDestination: " + dest + "\nDeparture: " + departuredate + " " + departuretime + "\n\nPurpose: " + purpose + "\n\n" + (parseInt(rowCount) - parseInt(1)) + " passengers",
                    icon: "info",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                postFleetRQ(request);
                                //saveEntitleMent(request, pf, "");
                            } else {
                                swal("Transaction aborted!");
                            }
                        });

            } else {
                swal({
                    icon: "warning",
                    title: "Please add atleast 1 passenger to the manifest ",

                })
            }
        }
    })

    $('#depttime').timepicker({'scrollDefault': 'now'});


    $('#fleetrqbtn').click(function () {

        //  bioData();
        $('#fuelrqmodal').modal({
            backdrop: 'static',
            keyboard: false

        })
    })



    $('#transprqid').click(function () {
        //   swal("Not yet deployed")
        console.log("I am here")
        $('#flrqid').modal({
            backdrop: 'static',
            keyboard: false
        });
    });


    $('#nonmemberbtnid').click(function () {
        $('#nonmemberadditionid').modal({
            backdrop: 'static',
            keyboard: false
        })
    });

    $('#addmemerid').click(function () {
        var fullname = $('#nonmemberfullnameid').val();
        var id = $('#nonmemberid').val();
        var contact = $('#nonmembercontactid').val();

        if (!fullname || !id || !contact) {
            $('#nonmemberfullnameid').css("border", "2px solid red");
            $('#nonmemberid').css("border", "2px solid red");
            $('#nonmembercontactid').css("border", "2px solid red");
        } else {
            addPassenger(fullname, id, contact);
            $('#nonmemberfullnameid').val('');
            $('#nonmemberid').val('');
            $('#nonmembercontactid').val('');
        }

    })



    $('#trmgrapprid').click(function () {
        window.approvlevel = "TRANSPORT APPROVAL";
        populateWaitingList('TRANSPORT APPROVAL')
    });
//--//
    $('#snrapprid').click(function () {
        window.approvlevel = "SENIOR MANAGEMENT";
        populateWaitingList('SENIOR MANAGEMENT')
    });



    $('#apprvid').click(function () {
        $.ajax({
            type: 'POST',
            url: 'CheckApproval',
            dataType: "text",
            data: "rqtype=no",
            success: function (data) {
                if (data.toString().includes("YES")) {



                    $('#realapprovalid').modal({
                        keyboard: false,
                    });

                } else {
                    swal("Sorry, you are not authorized to approve transport requests\n")
                }

            }
        });
    });


    $('#fuelid').click(function () {
        swal("Not yet deployed")
    });
    $('#mtceid').click(function () {
        swal("Not yet deployed")
    });
    $('#incidencerpt').click(function () {
        swal("Not yet deployed")
    });

    $('#apprvdeployid').click(function () {
        if ($('#vehselid').val().toString().length < 2 || $('#drivselid').val().toString().length < 2) {
            alert("Driver and vehicle selected cannot be empty")
        } else {
            var arr_concat = $('#titleid').text().toString().split("_");
            var refid = arr_concat[1];
            var transtype = "";

            if (window.approvlevel.toString().includes("TRANSPORT")) {
                transtype = "APPROVE TRANSPORT";
            } else {
                transtype = "AUTHORIZE REQUEST";
            }
            // console.log(refid + ", " + transtype)

            //   alert(refid+"---"+transtype);

            updateReq(refid, transtype);



        }
    })

});


//    function ajaxd() {
//
//    console.log("Reloading dash")
//    $.ajax({
//        type: 'GET',
//        url: '../ActiveSession',
//        dataType: "text",
//        success: function (data) {
//            if (data === "active") {
//                //  dashboard();
//
//            } else {
//                window.location.href = "/mtportal/"
//                //window.location.reload(1);
//                //    swal("uLELE");
//
//            }
//
//        }, error: function (data) {
//            console.log("Error: " + data)
//            window.location.href = "/mtportal/"
//        }
//    });
//
//
//
//}


function populateWaitingList(level) {
    $.ajax({
        type: 'GET',
        url: 'CheckApproval',
        data: "level=" + level,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.request_id + '</td>';
                activitydata += '<td>' + value.request_type + '</td>';
                //   activitydata += '<td><strong>Entered on:</strong>' + value.requestedon + '<br>Requested by: '+value.requestedby+'<br>From: '+value.from+'<br>Destination: '+value.to+'<br>Purpose: '+value.purpose+'</td>';

                activitydata += '<td><strong>Entered on:</strong>' + value.requestedon + '<br><strong>Requested by:</strong> ' + value.requestedby + '<br><strong>Purpose: </strong>' + value.purpose + '<br><button onclick=printRequest(this) class="btn btn-link">View Details</button></td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                /*                var stat = value.status;
                 if (stat === "approved") {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-success">' + value.status + '</button></td>'
                 } else if (stat === "rejected") {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-danger">' + value.status + '</button></td>'
                 } else {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-warning">' + value.status + '</button></td>'
                 }
                 
                 */
                activitydata += '<td ><button onclick="approvereq(this)" class="btn btn-success">Approve</button><br><br><button onclick="rejectreq(this)" class="btn btn-danger"> Reject </button><br></td>'

                /*activitydata += '<td ><button onclick="approvereq(this)" class="btn btn-success">Approve</button><br><br><button onclick="rejectreq(this)" class="btn btn-danger"> Reject </button><br><br><button onclick="addcomment(this)" class="btn btn-warning fa-envelope"> Add/View comments </button></td>'*/

//  activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-danger">Reject</button></td>'

                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#pendingidtbltbd').empty().append(activitydata);

        }
    });
}

function rejectreq(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var rank = window.approvlevel;

    var typeofreq = tbl.rows[row].cells[1].innerHTML;

    var transtype = "";

    $('#closeformodal').click();
    swal({
        title: "Are you sure to reject? Please confirm before proceeding",
        text: "Request type: " + typeofreq + "\nRef Number:" + cellvalue,
        icon: "info",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {
                    if (window.approvlevel.toString().includes("TRANSPORT")) {
                        transtype = "DECLINE TRANSPORT";
                    } else {
                        transtype = "DECLINE REQUEST";
                    }

                    updateReq(cellvalue, transtype)

                    //saveEntitleMent(request, pf, "");
                } else {
                    swal("Transaction aborted!");
                }
            });
}
function approvereq(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var rank = window.approvlevel;

    var typeofreq = tbl.rows[row].cells[1].innerHTML;

    alert(cellvalue)
    var transtype = "";
    if (cellvalue.toString().includes("/TR/") && window.approvlevel.toString().includes("TRANSPORT")) {
        freev();
        drivers();
        $('#titleid').text(typeofreq + "_" + cellvalue);
        $('#realtransportappr').modal({
            keyboard: false,

        })
    } else {
        $('#closeformodal').click();
        swal({
            title: "Please Confirm this Information to be correct before clicking [OK]: ",
            text: "Request type: " + typeofreq + "\nRef Number:" + cellvalue,
            icon: "info",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {
                        if (window.approvlevel.toString().includes("TRANSPORT")) {
                            transtype = "APPROVE TRANSPORT";
                        } else {
                            transtype = "AUTHORIZE REQUEST";
                        }

                        console.log(cellvalue + ", " + transtype);
                        updateReq(cellvalue, transtype)

                        //saveEntitleMent(request, pf, "");
                    } else {
                        swal("Transaction aborted!");
                    }
                });
    }

    //swal(cellvalue+" "+rank);

}
function printRequest(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
    swal(cellvalue);
    print(cellvalue)

}

function printorder(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("fleetrqtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
    swal(cellvalue);
    print(cellvalue)
}

function print(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            console.log(data);
            var ip = data;
            // var reqid = window.rqNo
            var doctype = "";
            if (reqid.toString().includes("/TR/")) {
                doctype = "REQUEST FOR TRANSPORT";
            } else if (reqid.toString().includes("/MTR/")) {
                doctype = "REQUEST FOR MAINTENANCE";
            } else if (reqid.toString().includes("/FR/")) {
                doctype = "REQUEST FOR FUEL";
            }



            var trans = "pdfname=" + doctype + "&refno=" + reqid;

            // swal(trans)
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
            //  return data;
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function validate(level) {

    var rank = level;
    // window.approvlevel = rank;

    $.ajax({
        type: 'POST',
        url: 'CheckApproval',
        dataType: "text",
        data: "rqtype=validate&level=" + rank,
        success: function (data) {

            if (rank.toString().includes("SENIOR")) {
                if (data.toString().includes("YES")) {


                    $('#snrapprid').prop("disabled", false);

                } else {
                    $('#snrapprid').prop("disabled", true);
                }
            } else {

                if (data.toString().includes("YES")) {
                    console.log("It is a YES for transport");
                    $('#trmgrapprid').prop("disabled", false);
                    $('#fuelcardbtn').prop("disabled", false);

                } else {
                    console.log("It is a NO for transport")
                    $('#trmgrapprid').prop("disabled", true);
                    $('#fuelcardbtn').prop("disabled", true);
                }
            }

        }
    });
}

function updateReq(reqid, transtype) {
    // $("#overlay").css("display", "block");

    var vehreq = "";

    var vehissued = $('#vehselid').val();
    var driver = $('#drivselid').val();
    var officer = "";

    var request = 'transtype=' + transtype + '&rqid=' + reqid + '&vehreq=' + vehreq + '&vehissued=' + vehissued + '&driver=' + driver + '&officer=' + officer;
    var req = reqid;
    $.ajax({
        type: 'GET',
        url: 'TrequestApproval',
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
                $('#trmgrapprid').click();
                $('#snrapprid').click();
                $('#apprvid').click();
                swal({icon: "success",
                    title: "Done successfully...", response});
            }

            // populateActivities();
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
    //  $("#overlay").css("display", "none");

}
function freev() {
    $.ajax({

        type: 'POST',
        url: 'VehUserData',
        data: 'transtype=free_v', //free_v
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //   $('#vehreq1Av').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#vehselid').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
                $('#vehfcard').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function drivers() {
    $.ajax({

        type: 'POST',
        url: 'VehUserData',
        data: 'transtype=drivers', //free_v
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //   $('#vehreq1Av').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#drivselid').append('<option value="' + value.drivers + '">' + value.drivers + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function allTransportLogs() {
    console.log("...")

    var rank = "";

    var data = 'rank=' + rank;

    $.ajax({
        type: 'GET',
        url: 'FleetActivities',
        data: data,
        dataType: "json",

        success: function (data) {


            //  alert("I am here");
            var activitydata = '';
            var i = 0;
            // activitydata += '<tbody>';
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

                activitydata += '<td ><button class="btn btn-outline-success btn-sm" onclick="printorder(this)">View Order</button></td>'
                activitydata += '</tr>';
                i += 1;

            });
            //activitydata += '</tbody>';
            $('#fleetrqtblbd').empty().append(activitydata);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function bioData() {
    // swal("Getting bio..")
    var request = "staffid=";
    $.ajax({
        type: 'GET',
        url: 'VerifyUser',
        data: request,
        dataType: "json",
        success: function (data) {
//               /new String[]{"empno", "name", "designation", "email", "phone", "account","department"};
            var fullname = data.name;
            var rank = data.designation;
            var staffid = data.empno;
            var department = data.department
            var email = data.email;
            var phone = data.phone;
            $('#fullnameid').val(fullname);
            $('#designationid').val(rank);
            $('#empnoid').val(staffid);
            $('#sectionid').val(department);
            $('#dept2id').val(department);
            $('#emailid').val(email);

            $('#verid').val(staffid);
            // askHRVer(staffid);
            $('#verbtnid').click();

            $('#currDeptid').text(department);
            window.myDept = department;
            //  myColleagues(department);
            //emailid
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data"
            });
        }
    });
}

function populateStaff() {
    // $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'POST',
        url: 'AllStaff',

        dataType: "json",
        success: function (data) {
            console.log("data here");
            $("#contactslist").empty();
            $("#contactslist").append('<option>-</option>');
            $.each(data, function (key, value) {
                $("#contactslist").append('<option>' + value.staffdet + '</option>');
                //  $("#cctxt").append('<option>' + value.staffdet + '</option>');
                //$("#bcctxt").append('<option>' + value.staffdet + '</option>');

            });
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}


function populateVevicleClasses() {
    // $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'GET',
        url: 'VehicleList',

        dataType: "json",
        success: function (data) {
            console.log("data here");
            $("#vehiclerequestedid").empty();

            $("#vehiclerequestedid").append('<option>-</option>');
            $.each(data, function (key, value) {
                $("#vehiclerequestedid").append('<option>' + value.classification + '</option>');
                //  $("#cctxt").append('<option>' + value.staffdet + '</option>');
                //$("#bcctxt").append('<option>' + value.staffdet + '</option>');

            });
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}


function postFleetRQ(request) {
    // swal("Info posted"+request)
    $.ajax({
        type: 'POST',
        url: 'RequestDataServlet', //TransportRequest.java
        data: request,
        dataType: "text",
        success: function (data) {
            console.log("data here");

            swal({
                icon: "success",
                title: "Data Saved Successfully",
                timer: 5000,
                text: "Reference Number " + data
            });

            if (data.toString().includes("/TR/")) {
                postManifest(data);
            }

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });

}
function postManifest(refno) {
    $('#manifesttblbd tr').each(function () {
        var fullname = $(this).find("td:eq(0)").html();
        var identification = $(this).find("td:eq(1)").html();
        var contacts = $(this).find("td:eq(2)").html();


        //var transtype = "insertinvoice";
        var rowdata = 'fullname=' + fullname + '&identification=' + identification + '&contacts=' + contacts + "&refno=" + refno;

        manifestAjax(rowdata)



        //  ary.push(value1, value2, value3, value4);
    });
}



function manifestAjax(request) {
    console.log("Inserting row " + request)
    $.ajax({
        type: 'POST',
        data: request,
        url: 'Manifest',
        dataType: "text",
        success: function (data) {

            console.log(data);
        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
}
function addPassenger(fullname, idno, contact) {
    var activitydata = '';
    var i = 0;

    activitydata += '<tr>';
    activitydata += '<td>' + fullname + '</td>';
    activitydata += '<td>' + idno + '</td>';
    activitydata += '<td>' + contact + '</td>';
    activitydata += '<td><button class="btn-danger btn-sm" onclick="removerow(this)">Delete</button></td>';




    activitydata += '</tr>';
    i += 1;
    //});
    $('#manifesttblbd').append(activitydata);


}


function loadInManifest(staffid) {
    var request = "staffid=" + staffid;
    $.ajax({
        type: 'GET',
        url: 'VerifyUser',
        data: request,
        dataType: "json",
        success: function (data) {


//               /new String[]{"empno", "name", "designation", "email", "phone", "account","department"};
            var fullname = data.name;
            var rank = data.designation;
            var staffid = data.empno;
            var department = data.department
            var email = data.email;
            var phone = data.phone;
            addPassenger(fullname, staffid, phone)

        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data"
            });
        }
    });
}
function  removerow(element) {
    var row = element.parentNode.parentNode.rowIndex;
    document.getElementById("manifesttbl").deleteRow(row);
}








function vehcard() {
    var cardno = $("#cardnoid").val();

    var data = "cardno=" + cardno;

    var response = 0;
    $.ajax({
        type: 'GET',
        data: data,
        url: 'VerifyFuelCard',
        dataType: "text",
        success: function (text) {

            response = text;

            console.log("Heres my response " + response)
            if (response == "0") {
                swal({
                    icon: "warning",
                    title: "Invalid entry",
                    text: "Card No. Does not Exist"
                })

                $("#cardnoid").val() == '';
                $('#postfrq').prop('disabled', true);

            } else {
                swal({
                    icon: "success",
                    title: "Successfully verified: ",
                    text: "You are cleared"
                })

                $('#postfrq').prop('disabled', false);
                populateCardItems(cardno);
                populateCardAmt(cardno);

                //var input = $("driverref");
                //    input[0].selectionStart = input[0].selectionEnd = input.val().length;
            }

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
    $("#ovelay").css("display", "none");
}
function populateCardAmt(cardno) {
    var card = cardno;
    var transtype = "CARD AMOUNT"
    var data = 'cardno=' + cardno + '&transtype=' + transtype;
    $.ajax({
        type: 'POST',
        data: data,
        url: 'VerifyFuelCard',
        dataType: "text",
        success: function (value) {
            var item = value;

            $("#cardbalid").val(item);

            $('#cardbalrecon1').val(item);

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });


}
function populateCardItems(cardno) {
    var transtype = "CARD VEHICLES"
    var request = 'cardno=' + cardno + '&transtype=' + transtype;
    $.ajax({
        type: 'POST',
        data: request,
        url: 'VerifyFuelCard',
        dataType: "json",
        success: function (response) {
            //    response = text;


            var dataset = response;
            //swal(data);
            var i = 0;
            $('#vehfuelreg').empty().append('<option value="">--Select--</option>');

            $.each(dataset, function (key, value) {

                $('#vehfuelreg').append('<option value="' + value.make1 + '">' + value.make1 + '</option>');
                i++;
            });
            document.getElementById("vehfuelreg").selectedIndex = "1";


            var vehreg = $('#vehfuelreg').val();

            populateVehDetails(vehreg);



        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });

}


function populateVehDetails(fleetid) {

    // swal(fleetid);
    var fleetno = fleetid;

    if (fleetid != null) {
        var acVeh = 'fleetno=' + fleetno;
        $.ajax({
            type: 'GET',
            data: acVeh,
            url: 'VehiclesServlet',
            dataType: "json",
            success: function (data) {

                //  swal(data);
                var dataset = data;
                var regno = dataset.regno;
                var regdate = dataset.regdate;
                var make = dataset.make;
                var model = dataset.model;
                var classification = dataset.classification;
                var yom = dataset.yom;
                var enginecc = dataset.enginecc;
                var fuel = dataset.fuel;
                var chassis = dataset.chassisno;
                var engine = dataset.engineno;

                $('#fueltypeid').val(fuel);


                populatelastfuelled(fleetid);

            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    }
}


function populatelastfuelled(regno) {
    var regnumber = regno;
    var transtype = ""
    var data = 'regno=' + regnumber + '&transtype=' + transtype;
    $.ajax({
        type: 'GET',
        data: data,
        url: 'MileageDB',
        dataType: "text",
        success: function (value) {
            var item = value;

            $("#lastfuelled").val(item);

            populatelastmileage();

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });


}



function populatelastmileage() {
    var lastfuelled = $('#lastfuelled').val();
    if (lastfuelled.length > 4) {
        var arr = lastfuelled.toString().split("-");
        var refno = arr[0];
        $.ajax({
            type: 'POST',
            data: "refno=" + refno,
            url: 'MileageDB',
            dataType: "text",
            success: function (value) {
                var item = value;

                $("#prvmileage").val(item);

            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    } else {
        $("#prvmileage").val("n/a");
    }

}

function populateCards() {
    //  var transtype = "CARD VEHICLES"
//    var request = 'cardno=' + cardno + '&transtype=' + transtype;
    $.ajax({
        type: 'POST',
        //   data: request,
        url: 'CardDynamics',
        dataType: "json",
        success: function (response) {
            //    response = text;
            console.log("Just Downloaded cards")

            var activitydata = '';
            var i = 0;
            $.each(response, function (key, value) {



                activitydata += '<tr>';
                activitydata += '<td>' + value.cardno + '</td>';
                activitydata += '<td>' + value.amount + '</td>';

                activitydata += '<td ><button onclick="allocate(this)" class="btn btn-success">Allocate Fuel Card</button><br><br><button onclick="reconcile(this)" class="btn btn-danger"> Reconcile Balance </button><br><br><button onclick="showtransactions(this)" class="btn btn-link"> Show Card Transactions</button><br></td>'


                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#myfuelcardstblbd').empty().append(activitydata);




        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });

}

function showtransactions(element) {
    //allocate(element)

    freev();
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myfuelcardstbl");
    var col = 0;
    var cardno = tbl.rows[row].cells[col].innerHTML;
    populateCardTransactions(cardno)
}
function reconcile(element) {
    //allocate(element)

    freev();
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myfuelcardstbl");
    var col = 0;
    var cardno = tbl.rows[row].cells[col].innerHTML;

    window.cardtobealloc = cardno;
    $('#cardnorecontxt').val(cardno);
    $("#cardmoney").click();

    populateCardAmt(cardno);
    //nwfuelcard
}

function allocate(element) {

    freev();
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myfuelcardstbl");
    var col = 0;
    var cardno = tbl.rows[row].cells[col].innerHTML;

    $('#cardnorecontxt').val(cardno);

    populateCardAmt(cardno)

    var transtype = "CARD VEHICLES"
    showallocation(cardno, transtype)

}

function showallocation(cardno, transtype) {


    var request = 'cardno=' + cardno + '&transtype=' + transtype;

    $.ajax({
        type: 'POST',
        data: request,
        url: 'VerifyFuelCard',
        dataType: "json",
        success: function (response) {
            //    response = text;

            window.cardtobealloc = cardno;
            var activitydata = '';

            $.each(response, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.make1 + '</td>';
                activitydata += '<td ><button onclick="removealloc(this)" class="btn btn-danger">Remove</button></td>'
                activitydata += '</tr>';

            });




            $('#myallocatedcarstblbd').empty().append(activitydata)
            $('#cardalloctab').click();


        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });

}

function removealloc(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myallocatedcarstbl");
    var col = 0;
    var regno = tbl.rows[row].cells[col].innerHTML;
    var cardno = window.cardtobealloc;

    if (cardno.length > 2) {

        swal({
            title: "Please Confirm this Information to be correct before clicking [OK]: ",
            text: "Vehicle Registration: " + regno + "\nCard No: " + cardno,
            icon: "info",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {
                        allocateCard("delete", cardno, regno);
                        //saveEntitleMent(request, pf, "");
                    } else {
                        swal("Transaction aborted!");
                    }
                });
    }
}

function allocateCard(transtype, cardno, regno) {
    var request = "transtype=" + transtype + "&cardno=" + cardno + "&regno=" + regno;
    $.ajax({
        type: 'GET',
        data: request,
        url: 'CardDynamics',
        dataType: "text",
        success: function (data) {
            //    response = text;

            showallocation(cardno, "CARD VEHICLES");
            swal(data);


        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });


}


function allocateCardAmt(cardno, oldamt, newamt) {
    var request = "cardno=" + cardno + "&oldamt=" + oldamt + "&newamt=" + newamt;
    $.ajax({
        type: 'GET',
        data: request,
        url: 'CardBalances',
        dataType: "text",
        success: function (data) {
            //    response = text;
            populateCards();
            // showallocation(cardno, "CARD VEHICLES");
            swal(data);


        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });


}


function populateCardTransactions(cardno) {
    $.ajax({
        type: 'POST',
        url: 'CardTransactions',
        data: "cardno=" + cardno,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {
//String[] columns = new String[]{"cardno", "debit", "credit", "narration", "enteredby", "enteredon"};
                activitydata += '<tr>';
                activitydata += '<td>' + value.narration + '</td>';
                activitydata += '<td>' + value.debit + '</td>';
                activitydata += '<td>' + value.credit + '</td>';
                activitydata += '<td>' + value.enteredby + '</td>';
                activitydata += '<td>' + value.enteredon + '</td>';

                activitydata += '</tr>';
                i += 1;
            });

            $('#cardtranstab').click();
            // activitydata += '</tbody>';
            $('#cardtranstblbd').empty().append(activitydata);

        }
    });
}


