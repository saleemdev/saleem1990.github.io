/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var rqType = "";
var rqNo = "";
var mtceList = "";
var vehList = "";


function getServiceList() {
    var maintenancetypes;
    $.ajax({
        type: 'POST',
        url: 'MaintenanceRequests',
        dataType: "json",
        success: function (response) {
            maintenancetypes = JSON.stringify(response);
            window.mtceList = maintenancetypes;
            console.log("Fucking thing: " + window.mtceList);

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
}


$(document).ready(function () {


    getVehData();
    $("#autocomplete").focusin(
            function (event) {
                getServiceList();

            }
    );


    $("#vehregid").focusin(
            function (event) {
                //        getVehData();

            }
    );

    var vehicleList = window.vehList;
    console.log(vehicleList)
    $('#vehregid').autocomplete({
        lookup: vehicleList,
        onSelect: function (suggestion) {
            alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
        }
    });


    var list = [{"value": "OIL CHANGE"},
        {"value": "TIRE ROTATION"},
        {"value": "Replace the air filter"},
        {"value": "Replace the fuel filter"},
        {"value": "Replace the cabin filter"},
        {"value": "Replace the spark plugs"},
        {"value": "Check level and refill brake fluid/clutch fluid"}];

    console.log(list + "/" + window.mtceList);

    $('#autocomplete').
            autocomplete({
                lookup: list,
                onSelect: function (suggestion) {

                    $('#autocomplete').val("");
                    var itm = suggestion.value;
                    var quantity = 1;
                    var total = 0.0;
                    var supplier = "";
                    // var qty;
                    var cost = prompt("Please enter cost of service:");
                    if (cost == null || cost == "") {
                        cost = "User cancelled the prompt.";
                    } else {
                        total = cost * quantity;

                        supplier = prompt("Please Enter the Service Entity");


                    }
                    var activitydata = '';
                    var i = 0;

                    activitydata += '<tr>';
                    activitydata += '<td>' + itm + '</td>';
                    activitydata += '<td>' + cost + '</td>';
                    // activitydata += '<td>' + total + '</td>';
                    activitydata += '<td>' + supplier + '</td>';
                    activitydata += '<td ><button type ="button" class="btn btn-outline-danger btn-sm" onclick="getId(this)">Remove</button></td>'


                    activitydata += '</tr>';
                    i += 1;
                    //});
                    $('#mtceTblbody').append(activitydata);

                }
            });

    $("#driveremail").prop("readonly", true);
    $("#fueltypeid").prop("readonly", true);

    function getVehData() {
        //console.log($('#vehregid').val());
        var data2req = 'vehreg=' + $('#vehregid').val();
        $.ajax({
            type: 'GET',
            url: 'MaintenanceRequests',
            dataType: "json",
            data: data2req,
            success: function (data) {
                var items = data;
                //console.log(items);
                window.vehList = items;

                ////////////

                var dataset = window.vehList;
                $.each(dataset, function (value) {


                    $('#vehregid').append('<option value="' + dataset[value].value + '">' + dataset[value].value + '</option>');
                });
                ///////////////

                console.log(window.vehList);
            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    }
    function generatePDF() {
        var content = document.getElementById('txtContent');
        var doc = new jsPDF();

        doc.setFontSize(14);
        doc.text(20, 20, content.value);
        //doc.text(35, 25, "Paranyan loves jsPDF");
        //doc.addImage(imgData, 'JPEG', 15, 40, 180, 160);
        doc.save('my.pdf');
    }

    // button.addEventListener('click', generatePDF);
    ///////////////////


    $('#configreset').click(function () {
        // $('#fuelreqformid')[0].reset();
    });

    $("#tabs").tabs({
        collapsible: true
    });

    enterUserDetails();

    var tbody = $("#InvItemsTblbody");

    if (tbody.children().length == 0) {


//        var data = "no items";
//        var empty =""
//
//        var activitydata = ""
//        activitydata += '<tr>';
//         activitydata += '<td>' + empty + '</td>';
//        activitydata += '<td>' + data + '</td>';
//         activitydata += '<td>' + empty + '</td>';
//          activitydata += '<td>' + empty + '</td>';
//        activitydata += '</tr>';

        //tbody.append(activitydata);

        // alert("Empty");
    }


//    $('#rmv').on("click", function () {
//      //  $(this).parents("tr").remove();
//        $(this).closest('tr').remove();
//        
//      //  swal("I won't refresh");
//
//    });
    $('#resetTbl').on("click", function () {
        //  $(this).parents("tr").remove();
        $('#InvItemsTblbody').empty();

        //  swal("I won't refresh");

    });




    populateItems();

    $("#closenav").trigger("click");

    // $('#closenav').prop("disabled", true);



    $('#f10').on("change", function () {
        var selVal = $(this).val();

        Promt4Qty(selVal, 50);

    });

    $('#vehregid').on("change", function () {
        var selVal = $(this).val();

        if (selVal !== null) {
            populateAttributes(selVal);
        }

        //Promt4Qty(selVal, 50);

    });


    $('#vreg').on("change", function () {

        // swal("::");

        var vehreg = $(this).val();
        // swal(vehreg);
        populateVehDetails(vehreg);

    });



    $('#btnfinish').prop("disabled", true);
    // $('#btnfinish').prop("disabled", true);

    $('input[name=rqtype]').on('change', function () {
        var rq = $(this).val();

        rqType = rq;

        var dataset = [rqType, "100", "1", "100"];



        //$("#place").val( gender );
    });
    //updfile

    $("#updfile").click(function () {
        console.log("Meeeeh");


        $('#btnfinish').prop("disabled", false); //enable the submit button
    });

    $('#saveMtcbtn').click(function () {
        $('#saveMtcbtn').prop("disabled", true);

        var regno = $('#vehregid').val();
        var requestedby = $('#uName1').text();
        var purpose = "Maintenance Request"
        var mileage = $("#mileageid").val();
        var transtype = "mtce";

        var data = 'regno=' + regno + '&requestedby=' + requestedby + '&purpose=' + purpose + '&mileage=' + mileage + '&transtype=' + transtype;

        console.log(data)
        var requestno;
        $.ajax({
            type: 'POST',
            data: data,
            url: 'RequestDataServlet',
            dataType: "text",
            success: function (text) {

                requestno = text.toString();

                printMaintenanceInvoice(requestno, regno);

                swal({
                    icon: "success",
                    title: "Success!",
                    text: "Data saved successfully,\nReg No:" + text + " this " + regno
                });
                window.rqNo = requestno;

                $('#saveMtcbtn').prop("disabled", false);


            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    });


    $('#btnsavefuel').click(function () {
        $('#btnsavefuel').prop("disabled", true);

        var cardno = $('#cardnoid').val();
        var driverref = $('#driverref').val();
        var vehreg = $('#vreg').val();
        var quantity = $('#qtyid').val();
        var fueltype = $('#fueltypeid').val();
        var amount = $('#costid').val();
        var comments = $('#commentsid').val();
        var transtype = "fuel";
        var data = 'cardno=' + cardno + '&driverref=' + driverref + '&vehreg=' + vehreg + '&quantity=' + quantity + '&fueltype=' + fueltype + '&amount=' + amount + '&comments=' + comments + '&transtype=' + transtype;

        console.log(data);
        $.ajax({
            type: 'POST',
            data: data,
            url: 'RequestDataServlet',
            dataType: "text",
            success: function (text) {

                requestno = text.toString();

                swal({
                    icon: "success",
                    title: "Success!",
                    text: "Data saved successfully,\nReg No:" + text
                });

            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });




        $('#btnsavefuel').prop("disabled", false); //the end

    });

    $("#btnfinish")

            .click(function () {

                $('#btnfinish').prop("disabled", true); //Disable the submit button


                //Insert Form Data First

                $("#wait").css("display", "block");

                var transtype = "insert";

                var fullname = $('#requestedby').val();
                var refno = $('#refid').val();
                var emailid = $('#emailid').val();
                var telno = $('#telno').val();
                var rqtype = window.rqType;
                var organization = $('#orgname').val();
                var dept = $('#f102').val();
                var from = $('#txtSource').val();
                var destination = $('#txtDestination').val();
                var distance = $('#f9').val();
                var pax = $('#passno').val();

                var vehrequested = $('#f103').val();
                var purpose = $('#f101').val();
                var remarks = $('#exampleTextarea').val();

                var enteredby = $('#uName').text();

                var requestno = "";

                if (vehrequested.length < 2 || destination.length < 2 || distance.length < 1) { //vehreq is Mandatory
                    swal("Missing Entry", "Vehicle Requested, destination or distance Cannot be Empty");

                    $('#reqmodal').modal({
                        backdrop: 'static',
                        keyboard: false
                    });
                } else {
                    var data = 'fullname=' + fullname + '&refno=' + refno + '&emailid=' + emailid + '&telno=' + telno + '&rqtype=' + rqtype + '&organization=' + organization + '&dept=' + dept + '&from=' + from +
                            '&destination=' + destination + '&distance=' + distance + '&pax=' + pax + '&vehrequested=' + vehrequested + '&purpose=' + purpose + '&remarks=' + remarks + '&transtype=' + transtype + '&enteredby=' + enteredby + '&driver=&vehicle=';
                    //ajax to insert form data
                    //   alert(data);
                    //swal (data);
                    $.ajax({
                        type: 'POST',
                        data: data,
                        url: 'RequestDataServlet',
                        dataType: "text",
                        success: function (text) {




                            //   alert("Text: " + text);

                            requestno = text.toString();

                            //   alert("Parsed: " + requestno);


                            window.rqNo = requestno;


                            insertTable(window.rqNo)





                            //     alert("Got this: " + text);
                            //$("#msgPane").html(data);


                        }, error: function (xhr, err) {
                            swal("There has been some problem " + xhr + " Error " + err);
                        }
                    });



                    window.rqNo = requestno;
                }
            });
    $("#btnproceed")
            .click(function () {

                //var selValue = $('input[name=rqtype]:checked').val();
                //       $("#basicdataform").valid(); //validate form 1
                if (rqType.length < 2) {
                    swal("Please ensure that you have clicked appropriate request type");
                } else {

                    $('#rqtype').text(rqType);
                    $('#reqmodal').modal({
                        keyboard: false,
                        backdrop: 'static'
                    });
                }
            });
});

function populateItems() {
    var dataset = ["Loading Jack", "Lowering Gear"];
    $.each(dataset, function (value) {


        $('#f10').append('<option value="' + dataset[value] + '">' + dataset[value] + '</option>');
    });



    var dataset1 = ["SALOON", "S. WAGON", "SPECIAL PURPOSE", "PICKUP TRUCK", "LORRY", "SUV", "MINI BUS", "BUS/COACH", "VAN", "MOTORCYCLE"];
    $.each(dataset1, function (value1) {
        //  alert(dataset[value]);        
        $('#f103').append('<option value="' + dataset1[value1] + '">' + dataset1[value1] + '</option>');
    });
}


function Promt4Qty(item, cost) {
    var itm = item;
    var cost = cost;
    var total = 0.0;
    // var qty;
    var quantity = prompt("Please enter units:", "0");
    if (quantity == null || quantity == "") {
        quantity = "User cancelled the prompt.";
    } else {
        //  quantity = qty ;
        total = cost * quantity;


        var activitydata = '';
        var i = 0;

        activitydata += '<tr>';
        activitydata += '<td>' + itm + '</td>';
        activitydata += '<td>' + cost + '</td>';
        activitydata += '<td>' + quantity + '</td>';
        activitydata += '<td>' + total + '</td>';


        activitydata += '</tr>';
        i += 1;
        //});
        $('#InvItemsTblbody').append(activitydata);
    }
}






function insertTable(rqno) {

    if (rqno.startsWith("Error") || rqno == "") {
        swal({
            icon: "warning",
            title: "Error",
            text: "There has been an error in your transaction\nPlease check your entries"
        });
    } else {

        var tbody = $("#InvItemsTblbody");

        if (tbody.children().length > 0) {

            $('#btnfinish').prop("disabled", true); //Reset Button to disabled

            var referenceno = window.rqNo;
            //Then insert Invoice Data
            var ary = [];

            $('#InvItemsTblbody tr').each(function () {
                var name = "Item";
                var unitcost = "cost";
                var qty = "qty";
                var total = "total"

                var value1 = $(this).find("td:eq(0)").html();
                var value2 = $(this).find("td:eq(1)").html();
                var value3 = $(this).find("td:eq(2)").html();
                var value4 = $(this).find("td:eq(3)").html();

                var transtype = "insertinvoice";
                var rowdata = 'item=' + value1 + '&cost=' + value2 + '&qty=' + value3 + '&transtype=' + transtype + '&rqid=' + referenceno;




                $.ajax({
                    type: 'POST',
                    data: rowdata,
                    url: 'RequestDataServlet',
                    dataType: "text",
                    success: function (data) {


                    }, error: function (xhr, err) {
                        swal("There has been some problem " + xhr + " Error " + err);
                    }
                });

                ary.push(value1, value2, value3, value4);
            });
        }



        var requestid = window.rqNo;




        updateImage(requestid);



        swal({
            icon: "success",
            title: "Request Created Successfully: " + window.rqNo,
            text: "Request Printing shortly"
        })


        //       window.open("http://localhost:8280/fleet/PDFActions?" + trans);


        $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",

            success: function (data) {
                console.log(data);
                var ip = data;
                var reqid = window.rqNo
                var doctype = "REQUEST FOR TRANSPORT";
                var trans = "pdfname=" + doctype + "&refno=" + reqid;
                window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
                return data;
            },

            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });




        //Finish the else here
    }//finish rable empty verification
}
function generateQR(reqid) {

// Clear Previous QR Code
    $('#qrcode').empty();

// Set Size to Match User Input
    $('#qrcode').css({
        'width': $('.qr-size').val(),
        'height': $('.qr-size').val()
    })


    $('#qrcode').qrcode({width: $('.qr-size').val(), height: $('.qr-size').val(), text: reqid});
}
function generateAndSaveQR() {
    var rq = "TR26WQAHZ";

    generateQR(rq);

    var canvasServer = document.getElementById("canvasThumbResult");
    var context = canvasServer.getContext("2d");
    var imageDataURL = canvasServer.toDataURL('image/png');
    var request = 'rq=' + rq + '&image=' + imageDataURL;
    $.ajax({
        type: 'POST',
        data: request,
        url: 'TestQR',

        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-Type", "application/upload");
        },
        success: function (data) {
            swal("QR Uploaded");
        }, error: function (xhr, err) {
            swal("There has been some problem with QR Code" + xhr + " Error " + err);
        }
    });


}
function updateImage(refid) {

    var element = $("#qrcode"); // global variable
    var getCanvas; // global variable


    html2canvas(element, {
        onrendered: function (canvas) {
            // $("#previewImage").append(canvas);
            getCanvas = canvas;
        }
    });

    var qrcode = getCanvas;

    //var qrcode = getCanvas.toDataURL("image/png");

    var request = 'refid=' + refid + '&qrcode=' + qrcode;


//817954
    $.ajax({
        type: 'POST',
        data: request,
        //enctype: "multipart/form-data",
        url: 'RequestQR',
        success: function (data) {
            swal("QR Uploaded");
        }, error: function (xhr, err) {
            swal("There has been some problem with QR Code" + xhr + " Error " + err);
        }
    });

}
function enterUserDetails() {
    var name = prompt("Please enter your PF or ID No:", "0");
    if (name == null || name == "" || name.length < 4) {
        name = "User cancelled the prompt.";
        window.location.href = "500.html"
    } else {

        verifyIDInitially(name);
        // $('#uName').text(name);
    }
}
//https://jsfiddle.net/jamiguel77/44avhkas/

function verifyCard() {
    var cardno = $("#cardnoid").val();
    swal("Are you sure to proceed with this card?", cardno);
    //$('#overlay').fadeIn('slow').delay(6000).fadeOut('slow');
    //$("#overlay").css("display", "block").fadeIn('slow').delay(6000).fadeOut('slow');
    $("#overlay").css("display", "block");
    $("#wait").css("display", "block");
//    var exists = Boolean(userIDexists(cardno));
//
//


    var data = "cardno=" + cardno;

    var response = 0;
    $.ajax({
        type: 'GET',
        data: data,
        url: 'VerifyFuelCard',
        dataType: "text",
        success: function (text) {

            response = text;

            if (response == "0") {
                swal({
                    icon: "warning",
                    title: "Invalid entry",
                    text: "Card No. Does not Exist"
                })
                //   $('#fuelreqformid')[0].reset();
                $("#overlay").css("display", "none");
                $("#wait").css("display", "none");

                $("#cardnoid").val() == '';

            } else {
                swal({
                    icon: "success",
                    title: "Successfully verified: ",
                    text: "You are cleared"
                })
                populateCardItems(cardno);
                populateCardAmt(cardno);
                $("#overlay").css("display", "none");
                $("#wait").css("display", "none");

                var input = $("driverref");
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

            $("#cardbal").val(item);

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
            $('#vreg').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {

                $('#vreg').append('<option value="' + value.make1 + '">' + value.make1 + '</option>');
                i++;
            });
            document.getElementById("vreg").selectedIndex = "1";


            var vehreg = $('#vreg').val();

            populateVehDetails(vehreg);



        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });

}





function verifyID() {
    var refid = $("#driverref").val();


    $("#overlay").css("display", "block");
    $("#wait").css("display", "block");

    var usertype = "TRANSPORT";
    var data = 'userid=' + refid + '&usertype=' + usertype;
    var response = 0;
    $.ajax({
        type: 'GET',
        data: data,
        url: 'VerifyUser',
        dataType: "text",
        success: function (text) {

            response = text;



            if (response == "0") {
                swal({
                    icon: "warning",
                    title: "Invalid entry",
                    text: "ID No. Does not Exist"

                })

                //    $('#fuelreqformid')[0].reset();
                $("#overlay").css("display", "none");
                $("#wait").css("display", "none");

                $("#driverref").text() == '';

            } else {
                swal({
                    icon: "success",
                    title: "Welcome\nSuccessful verification: ",
                    text: "You are cleared"
                })
                UserDetails(refid);

                $("#overlay").css("display", "none");
                $("#wait").css("display", "none");



            }

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
    $("#ovelay").css("display", "none");
}

function verifyIDInitially(refno) {
    var refid = refno;


    $("#overlay").css("display", "block");
    $("#wait").css("display", "block");

    var usertype = "ALL";
    var data = 'userid=' + refid + '&usertype=' + usertype;
    var response = 0;
    $.ajax({
        type: 'GET',
        data: data,
        url: 'VerifyUser',
        dataType: "text",
        success: function (text) {

            response = text;

            console.log(response)


            if (response == "0") {
                swal({
                    icon: "warning",
                    title: "Invalid entry",
                    text: "ID No. Does not Exist"

                })

                window.location.href = "500.html"



            } else {

                UserDetails(refid);

                $("#overlay").css("display", "none");
                $("#wait").css("display", "none");



            }

        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
    $("#ovelay").css("display", "none");
}

function UserDetails(refid) {
    var request = 'userid=' + refid + '&transtype=ALL'
    $.ajax({
        type: 'POST',
        data: request,
        url: 'VerifyUser',
        dataType: "json",
        success: function (response) {
            //    response = text;


            var dataset = response;
            //swal(data);
//{"empno", "name", "designation", "email", "phone"};           

            var email = response.email;
            var name = response.name;
            var rank = response.designation;
            console.log(email);

            $("#driveremail").val(email);

            $("#uName1").text(name);
            $("#uName2").text(name);
            $("#uName3").text(name);
            $("#uName4").text(name);

            $("#rank1").text(rank);
            $("#rank2").text(rank);
            $("#rank3").text(rank);
            $("#rank4").text(rank);


            swal({
                icon: "success",
                title: "Welcome " + name + ",\nSuccessful verification: ",
                text: "You are cleared"
            });

        }, error: function (xhr, err) {
            swal("Sorry, you do not have access to the system.\nSystem Error: " + xhr + " Error " + err);
            // window.location.href = "index.jsp"
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

            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    }
}

function generatePDF() {
    var content = document.getElementById('fuelreqformid');
    var doc = new jsPDF();

    doc.setFontSize(14);
    doc.text(20, 20, content);
    //doc.text(35, 25, "Paranyan loves jsPDF");
    //doc.addImage(imgData, 'JPEG', 15, 40, 180, 160);
    doc.save('my.pdf');
}

function showResult(str) {
    if (str.length == 0) {



    }
}

function focusFn() {
    $('#searchb').focus();
}
function populatesearchitems(str) {

    var request = 'searchkey=' + str;


    $.ajax({

        type: 'POST',
        url: 'SearchRequest',
        data: request,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.rqid + '</td>';
                activitydata += '<td>' + value.rqtype + '</td>';
                activitydata += '<td>' + value.rqby + '</td>';
                activitydata += '<td>' + value.rqon + '</td>';


                activitydata += '<td ><button id="vehdet" class="btn btn-outline-success btn-sm">View Entire File</button></td>'
                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#vehiclesTbltb').empty().append(activitydata);
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}




function  getId(element) {
    var row = element.parentNode.parentNode.rowIndex;
    document.getElementById("mtceTbl").deleteRow(row);
}

function populateAttributes(selval) {
    var regid = selval;
    // swal(regid);



    // swal(fleetid);
    var fleetno = regid;
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

            $('#makeid').val(make);
            $('#modelid').val(model);

            populateLastMaint(regno);
            populateFleetMileage(regno);


        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });



}
function printMaintenanceInvoice(mtceRequestId, regno) {


    var tbody = $("#mtceTblbody");

    if (tbody.children().length > 0) {

        //   $('#btnfinish').prop("disabled", true); //Reset Button to disabled

        var referenceno = mtceRequestId;
        //Then insert Invoice Data
        var ary = [];

        $('#mtceTblbody tr').each(function () {
            var name = "Item";
            var unitcost = "cost";
            // var qty = "qty";
            var total = "total"

            var value1 = $(this).find("td:eq(0)").html();
            var value2 = $(this).find("td:eq(1)").html();
            var value3 = 1;
            var value4 = $(this).find("td:eq(2)").html();

            var transtype = "insertinvoice";
            var rowdata = 'item=' + value1 + '/' + value4 + '&cost=' + value2 + '&qty=' + value3 + '&transtype=' + transtype + '&rqid=' + referenceno;
            $.ajax({
                type: 'POST',
                data: rowdata,
                url: 'RequestDataServlet',
                dataType: "text",
                success: function (data) {
                    $('#saveMtcbtn').prop("disabled", false);

                }, error: function (xhr, err) {
                    swal("There has been some problem " + xhr + " Error " + err);
                }
            });

            ary.push(value1, value2, value3, value4);
        });
    }
//
    swal({
        icon: "success",
        title: "Request Created Successfully: " + referenceno,
        text: "Request Printing shortly"
    })
    var reqid = referenceno
    var doctype = "REQUEST FOR MAINTENANCE";
    var trans = "pdfname=" + doctype + "&refno=" + reqid;

    $('#mtcerqform').reset();

    print(reqid, doctype);

//      window.open("http://localhost:8280/fleet/PDFActions?" + trans);
}

function print(reqid, doctype) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            var ip = data;
            //  var doctype = doctype;
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function populateLastMaint(regno) {

}

function populateFleetMileage(regno) {

}




