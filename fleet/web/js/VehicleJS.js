/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var fleetID = "";
var engineNumber = "";
var chssisNumber = "";
var status = "";
var vehdriver = "";
var authofficer = "";
$(document).ready(function () {

    populateUserData();

    $('#deployed').click(function () {
        var ckbox = $(this);
        if (ckbox.is(':checked')) {
            $('#secid').prop("disabled", false); //Enable the  button
            $('#driverid').prop("disabled", false); //Enable the  button
            $('#authofid').prop("disabled", false); //Enable the  button

        } else {
            $('#secid').prop("disabled", true); //Disable the  button
            $('#driverid').prop("disabled", true); //Disable the  button
            $('#authofid').prop("disabled", true); //Disable the  button

            $('#secid').val(""); //Disable the  button
            $('#driverid').val(""); //Disable the  button
            $('#authofid').val(""); //Disable the  button



        }
    });

    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        var patt = new RegExp(value, "i");

        $('#vehiclesTbl').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });
        //var i = $('#vehiclesTbl tr[visible="true"]').length;
        var i = $('#vehiclesTbltb tr').length;
        $('#count').text("");
    });
    //End search




    $('#export-btn').click(function () {
// e.preventDefault
// 
        swal("Clicked");
        ResultsToTable();
    });
    $('#pdfbtn').click(function () {

//"PDFActions?pdfname=ALLVEHICLES"

        print();



        //  window.open("http://localhost:8280/fleet/PDFActions?" + trans);
    });
    //

    function print() {
        $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",

            success: function (data) {
                console.log(data);
                var ip = data;
                var refno = '';
                var pdfname = "ALLVEHICLES"
                var trans = 'pdfname=' + pdfname + '&refno=' + refno;
                window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
                return data;
            },

            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    }
    function ResultsToTable() {
        $("#table2excel").table2excel({

            // exclude CSS class

//            exclude: ".noExl",

            name: "Worksheet Name",
            filename: "SomeFile" //do not include extension

        });
    }

    $("#datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"
                //  $( "#datepicker" ).datepicker( "option", "dateFormat", $( this ).val() );

    });
    $("#datepicker1").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"
                //  $( "#datepicker" ).datepicker( "option", "dateFormat", $( this ).val() );

    });
    populateActivities();
    populateYears();
    populateModels();
//Form Validate submitveh

    $('#regVehicle').submit(function ()
            //click(function () 
            {

                var regno = $('#f5').val();
                var date = $('#datepicker').val();
                var make = $('#f19').val();
                var model = $('#f18').val();
                var yom = $('#f10').val();
                var enginecc = $('#f22').val();
                var fueltype = $('#f23').val();
                var mileage = $('#f21').val();
                var chassisno = $('#f24').val();
                var engineno = $('#f25').val();
                var classification = $('#f30').val();
                var data = 'regno=' + regno + '&date=' + date + '&make=' + make + '&model=' + model + '&yom=' + yom + '&enginecc=' + enginecc + '&fueltype=' + fueltype + '&mileage=' + mileage + '&chassisno=' + chassisno + '&engineno=' + engineno + '&classification=' + classification;
                $.ajax({
                    type: 'post',
                    data: data,
                    url: 'VehicleDataServlet',
                    dataType: "text",
                    success: function (data) {

                        //     alert("Got this: " + data);
                        //$("#msgPane").html(data);


                    }, error: function (xhr, err) {
                        swal("There has been some problem " + xhr + " Error " + err);
                    }
                });
            });
//    $("#jqueryform-7ae31c").validate({
//        rules: {
//            "f5": {
//                required: true
//                        //minlength: 5
//            },
//            "datepicker": {
//                required: true,
//                date: true
//            },
//            "f19": {
//                required: true,
//                optionLabel: "- Select -"
//            },
//            "f18": {
//                required: true,
//                optionLabel: "- Select -"
//            },
//            "f10": {
//                required: true,
//                optionLabel: "- Select -"
//            },
//            "f22": {
//                required: true
//            },
//            "f23": {
//                required: true
//            },
//            "f21": {
//                required: true
//            },
//            "f24": {
//                required: true,
//                minlength: 5
//            },
//            "f25": {
//                required: true,
//                minlength: 5
//            }
//        },
//        messages: {
//            "f5": {
//                required: "Please, enter a Valid Registration number"
//            },
//            "datepicker": {
//                required: "Please, click here to enter a date",
//                //  email: "Email is invalid"
//                date: "Invalid date"
//            },
//            "f19": {
//                required: "Please Select a Valid Make",
//                //optionLabel: "Select",
//
//                // email: true
//            },
//            "f18": {
//                required: "Please select a valid Model"
//                        // email: true
//            },
//            "f10": {
//                required: "Please select a valid YOM"
//                        // email: true
//            },
//            "f22": {
//                required: "Please type in a Valid Engine CC"
//                        // email: true
//            },
//            "f23": {
//                required: "Please input a valid fuel type"
//                        // email: true
//            },
//            "f21": {
//                required: "Mileage is Mandatory",
//                numbers: "numeric values allowed"
//                        // email: true
//            },
//            "f24": {
//                required: "Chassis No is Mandatory"
//
//            },
//            "f25": {
//                required: "Engine No is Mandatory"
//            }
//
//        },
//        submitHandler: function (form) { // for demo
//            swal('valid form submitted'); // for demo
//            return false; // for demo
//        }
//    });
//Ends here
    $("#vehiclesTbl").on('click', '.btn', function () {
// get the current row
// var clearance = $('#rank').text();
        var currentRow = $(this).closest("tr");
        var fleetno = currentRow.find("td:eq(0)").html(); // get current row 1st table cell TD value
        var regno = currentRow.find("td:eq(1)").html(); // get current row 2nd table cell TD value
        var modelyom = currentRow.find("td:eq(3)").html(); // get current row 3rd table cell  TD value
        var stat = currentRow.find("td:eq(5)").html(); // get current row 3rd table cell  TD value

        var make = currentRow.find("td:eq(2)").html();
        var classification = currentRow.find("td:eq(4)").html();
        //var model

        window.fleetID = fleetno;
        ///Split the Model Variable because it is concatenated with year

        var modeldet = modelyom.split("/");
        var model = modeldet[0]
        var yom = modeldet[1];
        // var make = "ALL"

        populateMakeEdit(make, model);
        populateVehDetails(fleetno);
        var loc = window.location.pathname;
        var dir = loc.substring(0, loc.lastIndexOf('/'));
        var imageURL = dir + "/images/" + model + ".jpg"


        //swal(imageURL);
        swal({
            // title: make,
            text: make + " " + model,
            icon: imageURL,
        });
        $('#RegNoHeader').text(regno);
        $('#fleetreg').text(fleetno);
        $('#f191').val(make);
        // $('#f181').val(model.toString().toUpperCase());


        $('#deployed').prop("checked", false);
        $('#secid').prop("disabled", true); //Disable the cancel button
        $('#driverid').prop("disabled", true); //Disable the cancel button
        $('#authofid').prop("disabled", true); //Disable the cancel button




        $('#secid').val(""); //Disable the  button
        $('#driverid').val(""); //Disable the  button
        $('#authofid').val(""); //Disable the  button


        $('#editModal').modal({
            keyboard: false,
            backdrop: 'static'


        });
    });




    function populateVehDetails(fleetid) {

        // swal(fleetid);
        var fleetno = fleetid;
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


                var status = dataset.status;
                if (status.includes("Deployed")) {
                    $('#deployed').prop("checked", true);
                    $('#secid').prop("disabled", false); //Disable the cancel button
                    $('#driverid').prop("disabled", false); //Disable the cancel button
                    $('#authofid').prop("disabled", false); //Disable the cancel button


                    var driver = dataset.driver;
                    var authority = dataset.authority;
                    var section = dataset.section;

                    $('#secid').val(section); //Disable the cancel button
                    $('#driverid').val(driver); //Disable the cancel button
                    $('#authofid').val(authority); //Disable the cancel button





                } else {
                    $('#deployed').checked = false;
                }


                $('#f51').val(regno);
                $('#datepicker1').val(regdate);
//                $('#f191').val(make);
//                $('#f181').val(model);
                $('#f301').val(classification);
                $('#f101').val(yom);
                $('#f221').val(enginecc);
                $('#f231').val(fuel);
                $('#f241').val(chassis);
                $('#f251').val(engine);

            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    }


//    $("#printf").click(function () {
//// var regno = window.fleetID;
//
//        var james = "God is with you";
//        swal(james);
//        $('#desktopify').trigger('notify', {
//
//            icon: null,
//            title: james
//
//
//
//        })
//        // populateVehDetails(regno);
//
//    });
    $("#vehform").click(function () {
//  $("#newVehicleModal").modal({backdrop: 'static', keyboard: false});
        openModal();
    });
    $("#refrveh").click(function () {
//  $("#newVehicleModal").modal({backdrop: 'static', keyboard: false});
        populateActivities();
    });
    $("#delfile").click(function () {
        var regno = window.fleetID;
        // alert(regno);
        swal({
            title: "Are you sure?",
            text: "Once deleted, you will not be able to recover this file!" + regno,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {

                        deletefile(regno);
                        swal("The file has been deleted! " + regno, {
                            icon: "success",
                        });
                    } else {
                        swal("The file is safe!");
                    }
                });
    });
    function deletefile(fleetno) {
        var fleetid = fleetno;
        var transtype = "delete";
        var data = 'transtype=' + transtype + '&fleetid=' + fleetid;
        $.ajax({
            type: 'GET',
            data: data,
            url: 'VehicleDataServlet',
            dataType: "text",
            success: function (data) {

                //  alert("Got this: " + data);
                populateActivities();
                //$("#msgPane").html(data);


            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });
    }

    $("#updfile").click(function () {
        var fleetid = window.fleetID;
        var transtype = "update";
        var regno = $('#f51').val();
        var date = $('#datepicker1').val();
        var make = $('#f191').val();
        var model = $('#f181').val();
        var yom = $('#f101').val();
        var enginecc = $('#f221').val();
        var fueltype = $('#f231').val();
        var mileage = $('#f211').val();
        var chassisno = $('#f241').val();
        var engineno = $('#f251').val();
        var classification = $('#f301').val();
        //
        var status = "Running";
        var section = "";
        var status = "";
        var authority = "";
        var driver = "";

        var ckbox = $('#deployed');
        if (ckbox.is(':checked')) {
            status = "Deployed"
            section = $('#secid').val();
            authority = $('#authofid').val();
            driver = $('#driverid').val();
        } else {
            status = "Running";
            section = "";
        //    status = "";
            authority = "";
            driver = "";

        }

        console.log('status=' + status);

        var data = 'regno=' + regno + '&date=' + date + '&make=' + make + '&model=' + model + '&yom=' + yom + '&enginecc=' + enginecc + '&fueltype=' + fueltype + '&mileage=' + mileage + '&chassisno=' + chassisno + '&engineno=' + engineno +
                '&classification=' + classification + '&transtype=' + transtype + '&fleetid=' + fleetid + '&status=' + status + '&authority=' + authority + '&section=' + section + '&driver=' + driver;

        //   console.log(data);

        $.ajax({
            type: 'GET',
            data: data,
            url: 'VehicleDataServlet',
            dataType: "text",
            success: function (txt) {


                swal(regno + " Updated Successfully\n");
                alert("Got this: " + txt);
                populateActivities();
                //$("#msgPane").html(data);



            }, error: function (xhr, err) {
                swal("There has been some problem " + xhr + " Error " + err);
            }
        });


    });

});
//
$("#vehdet").click(function () {
//  $("#newVehicleModal").modal({backdrop: 'static', keyboard: false});
    swal("Here is my Data", "Yo Man Small data");
});
$('#f19').on("change", function () {
    var selVal = $(this).val();
    //$("h2").html(selVal);

    //swal(selVal);
    populateMake(selVal);
});
//----
//    $('#f191').on("change", function () {
//        var selVal1 = $(this).val();
//        //$("h2").html(selVal);
//
//        // swal(selVal);
//        populateMakeEdit(selVal1);
//    });
//});
//

function openModal() {
    // window.engineNumber= stringGen(6);
    window.chassisNumber = stringGen(6);
    $('#f25').text(window.chassisNumber);
    $('#f24').text(window.chassisNumber);
    $('#newVehicleModal').modal({
        keyboard: false,
        backdrop: 'static'
    });
}

function populateModels() {
    var dataset = ["TOYOTA", "SUBARU", "NISSAN", "HYUNDAI", "RENAULT", "SUZUKI", "ISUZU", "FIAT", "MITSUBISHI", "MERCEDES BENZ", "RANGE ROVER", "LAND ROVER"];
    $.each(dataset, function (value) {

        //  alert(dataset[value]);
        $('#f19').append('<option value="' + dataset[value] + '">' + dataset[value] + '</option>');
        $('#f191').append('<option value="' + dataset[value] + '">' + dataset[value] + '</option>');
    });
    var dataset1 = ["SALOON", "S. WAGON", "SPECIAL PURPOSE", "PICKUP TRUCK", "LORRY", "SUV", "MINI BUS", "BUS/COACH", "VAN", "MOTORCYCLE"];
    $.each(dataset1, function (value1) {

        //  alert(dataset[value]);
        $('#f30').append('<option value="' + dataset1[value1] + '">' + dataset1[value1] + '</option>');
        $('#f301').append('<option value="' + dataset1[value1] + '">' + dataset1[value1] + '</option>');
    });
    var dataset3 = ["PETROL", "DIESEL", "GASOLINE", "FOSSIL", "ELECTRIC", "HYBRID", "unspecified"];
    $.each(dataset3, function (value3) {
        $('#f23').append('<option value="' + dataset3[value3] + '">' + dataset3[value3] + '</option>');
        $('#f231').append('<option value="' + dataset3[value3] + '">' + dataset3[value3] + '</option>');
    });
}
function populateMake(model) {
//swal("SW "+model);

    var vehmodel = model;
    var acVeh = 'vehmake=' + vehmodel;
    $.ajax({
        type: 'GET',
        data: acVeh,
        url: 'VehicleMakesWithModel',
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //swal(data);
            var i = 0;
            $('#f18').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                //  var keyid = "make"+i;
                // alert(keyid+": "+value.keyid);
                $('#f18').append('<option value="' + value.make1 + '">' + value.make1 + '</option>');
                i++;
            });
        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
}

//--
function populateMakeEdit(make, model) {
//swal("SW "+model);
//var fleetreg = window.fleetID;

    var vehmake = make;
    var acVeh = 'vehmake=' + vehmake;
    $.ajax({
        type: 'GET',
        data: acVeh,
        url: 'VehicleMakesWithModel',
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //swal(data);
            var i = 0;
            $('#f181').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                //  var keyid = "make"+i;
                // alert(keyid+": "+value.keyid);
                $('#f181').append('<option value="' + value.make1 + '">' + value.make1 + '</option>');
                i++;
            });
            $('#f181').val(model);
        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
}


//--
function populateYears() {

// alert("I am inside");
    var minOffset = 0, maxOffset = 30; // Change to whatever you want
    var thisYear = (new Date()).getFullYear();
    var select = $('#f10');
    var select1 = $('#f101');
    for (var i = minOffset; i <= maxOffset; i++) {
        var year = thisYear - i;
        $('<option>', {value: year, text: year}).appendTo(select);
        $('<option>', {value: year, text: year}).appendTo(select1);
    }

// select.appendTo('body');
}
function populateActivities() {

    $.ajax({

        type: 'POST',
        url: 'VehiclesServlet',
        dataType: "json",
        success: function (data) {
            //  alert("I am here");
            var activitydata = '';
            var i = 0;
            //   activitydata += '<tbody>';
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.fleetno + '</td>';
                activitydata += '<td>' + value.regno + '</td>';
                activitydata += '<td>' + value.model + '</td>';
                activitydata += '<td>' + value.makeyom + '</td>';
                activitydata += '<td>' + value.classification + '</td>';
                if (value.status == 'Running') {
                    activitydata += '<td> <label class="badge badge-success">' + value.status + '</td>';
                } else if (value.status == 'Deployed') {
                    activitydata += '<td> <label class="badge badge-danger">' + value.status + '</td>';
                } else {
                    activitydata += '<td><label class="badge badge-warning">' + value.status + '</td>';
                }

                activitydata += '<td ><button id="vehdet" class="btn btn-outline-success btn-sm">View Entire File</button></td>'
                activitydata += '</tr>';
                i += 1;
            });
            $('#count').text("Vehicle Count: " + i);
            // activitydata += '</tbody>';
            $('#vehiclesTbltb').empty().append(activitydata);







        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });




}
function populateUserData() {
    ///
    $.ajax({
        type: 'POST',
        url: 'UserData',
        data: 'transtype=sections',
        dataType: "json",
        success: function (data) {

            var dataset = data;

            console.log(dataset);
            $('#secid').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {

                $('#secid').append('<option value="' + value.sections + '">' + value.sections + '</option>');

            });

        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
    //


    $.ajax({

        type: 'POST',
        url: 'UserData',
        data: 'transtype=drivers',
        dataType: "json",
        success: function (data) {

            var dataset = data;
            $('#driverid').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#driverid').append('<option value="' + value.drivers + '">' + value.drivers + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

    //


    $.ajax({

        type: 'POST',
        url: 'UserData',
        data: 'transtype=managers',
        dataType: "json",
        success: function (data) {

            var dataset = data;
            $('#authofid').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                //  var keyid = "make"+i;
                // alert(keyid+": "+value.keyid);
                $('#authofid').append('<option value="' + value.managers + '">' + value.managers + '</option>');
                //   i++;
            });

        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function stringGen(len) {
    var text = "";
    var charset = "abcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < len; i++)
        text += charset.charAt(Math.floor(Math.random() * charset.length));
    return text;
}

//$(document).ready(function() {
//    swal("Mah Nigga");
//  $('#searchveh').keyup(function () {
//    var searchTerm = $("#searchveh").val();
//   
//    var listItem = $('#vehiclesTbltb').children('tr');
//    // swal (listItem[0]);
//    var searchSplit = searchTerm.replace(/ /g, "'):containsi('")
//    
//  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){
//        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
//    }
//  });
//    
//  $("#vehiclesTbltb tr").not(":containsi('" + searchSplit + "')").each(function(e){
//    $(this).attr('visible','false');
//  });
//
//  $("#vehiclesTbltb tr:containsi('" + searchSplit + "')").each(function(e){
//    $(this).attr('visible','true');
//  });
//
//  var jobCount = $('#vehiclesTbltb tr[visible="true"]').length;
//   // $('.counter').text(jobCount + ' item');
//
//  //if(jobCount == '0') {$('.no-result').show();}
//  //  else {$('.no-result').hide();}
//		  });
//});