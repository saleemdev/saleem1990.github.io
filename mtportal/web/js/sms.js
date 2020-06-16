/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    loadTable();


//
    $('#pdf').on('click', function () {
        $("#MsgTrailTbl").tableHTMLExport({type: 'pdf', filename: 'sample.pdf'});
    });

    $('#csv').on('click', function () {
        $("#MsgTrailTbl").tableHTMLExport({type: 'csv', filename: 'sample.csv'});
    });

    $('#chckmail').click(function () {
        var ckbox = $('#chckmail');


        if (ckbox.is(':checked')) {
            //alert('You have Checked it');
            $('#sendTxtid').text("Send SMS");
            $('#ccdiv').css('display', 'none');
            $('#bccdiv').css('display', 'none');
        } else {
            $('#sendTxtid').text("Send Email");
            //alert('You Un-Checked it');
        }
    });

    populateAllStaffGroups();
    $('#contactslist').select2({

    });

    $('#cctxt').select2({
//data-placeholder:"Select"
        placeholder: "Select CC",
        hidden: true
    });

    $('#bcctxt').select2({

    });

    $('#contactgroup').select2({

    });
    $('#contactgroup').on('change', function () {
        var groupname = this.value;
        //populate members
    });
    $('#addnewgrpnamebutton').click(function () {
        $('#nwgrpnamemodal').modal({
            backdrop: "static",
            keyboard: false
        })
    });
    $('#messagingGrps').click(function () {
        $('#groupModal').modal({
            backdrop: "static",
            keyboard: false
        });
    });

//    $(".modal").on("hidden.bs.modal", function(){
//    $(".modal-body").html("");
//});

    $('#sendTxtid').click(function () {

        $('#sendTxtid').button('loading');
        var refno = $('#refnoid').val().toString().toUpperCase();
        var items = $('#contactslist').val();
        var message = $('#commentboxid').val()

        //alert(items)
        if (refno.length < 1 || items.length < 1 || message.length < 3) {

            $('#sendTxtid').button('reset');
            var message = "";
            if (refno.length < 1) {
                $('#refnoid').css("border", "2px solid red");
                message = "Reference number cannot be empty";
            }
            if (items.length < 1) {
                $('#contactslist').css("border", "2px solid red");
                message = "Contact field cannot be empty";
            }
            if (message.length < 3) {
                $('#commentboxid').css("border", "2px solid red");
                message = "Message field cannot be less than 3 characters";

            }

            swal({
                icon: "warning",
                text: message
            });

        } else {
            //Do the Actual insert now..
            var transtype = $('#sendTxtid').text();
            if (transtype.toString().includes("SMS")) { //Send an SMS
                swal({
                    icon: "warning",
                    title: "SMS Confirmation:",
                    text: "Subject: " + refno + "\nContacts: " + items + "\nMessage: " + message,
                    buttons: true,
                    dangerMode: true,
                }).then((willDelete) => {
                    if (willDelete) {

                        sendMsg(items, refno, message);
                    } else {
                        swal("Transaction aborted!");
                    }
                });
            } else { //Send An Email
                var cc = $('#cctxt').val();
                var bcc = $('#bcctxt').val();
                swal({
                    icon: "warning",
                    title: "Email Confirmation:",
                    text: "Subject: " + refno + "\nContacts: " + items + "\nCC: " + cc + "\nBCC: " + bcc + "\nMessage: " + message,
                    buttons: true,
                    dangerMode: true,
                }).then((willDelete) => {
                    if (willDelete) {

                        sendEmail(items, cc, bcc, refno, message);
                    } else {
                        swal("Transaction aborted!");
                    }
                });
            }
        }
    });






    $('#openMsgModal').click(function () {

        $.ajax({
            type: 'POST',
            url: 'RegistryRights',
            //data: "leaveyear=" + lvyr,
            dataType: "text",
            success: function (data) {

                if (data == "ok") {

                    $('#newMsg').modal({
                        backdrop: "static",
                        keyboard: false
                    });

                } else {
                    swal({
                        icon: "warning",
                        title: "Sorry, you are not authorized to access this feature",
                        text: "Please contact System Administrator"
                    })
                }
            }
        });



    });

    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

        $('#msgTrail').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });


});

function showcc() {
    $('#ccdiv').css('display', 'block');
}
function showbcc() {
    $('#bccdiv').css('display', 'block');
}
function loadTable() {
    console.log("Getting table...")
    //   var lvyr = $('#lvyrid').text();

//    console.log(lvyr)
    $.ajax({
        type: 'POST',
        url: 'SMSReports',
        //data: "leaveyear=" + lvyr,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.messageid + '</td>';
                activitydata += '<td>' + value.recepient + '</td>';

                activitydata += '<td>' + value.timestamp + '</td>';
                activitydata += '<td>' + value.transactedby + '</td>';
///Version 2
                var action = value.stat.toString().toUpperCase();
                console.log(action)
                if (action.toString().includes("SUCCESS")) {
                    activitydata += '<td><span class="label label-primary">' + action + '</span></td>'; //5
                } else {
                    activitydata += '<td><span class="label label-danger">' + action + '</span></td>';//5
                }

               



                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#msgTrail').empty().append(activitydata);
        }
    });
}

function sendEmail(items, cc, bcc, refno, message) {
    $('#sendTxtid').button('loading');
    $('#sendTxtid').prop('disabled', true);
    var request = "refid=" + refno + "&staffid=" + items + "&cc=" + cc + "&bcc=" + bcc + "&message=" + message;

    $.ajax({
        type: 'GET',
        url: 'EmailServlet',
        data: request,
        dataType: "text",
        success: function (data) {
            document.getElementById('commentboxid').value = "";
            swal({
                icon: "success",
                title: "Transaction completed successfully"
            });
            $('#sendTxtid').button('reset');
            $('#sendTxtid').prop('disabled', false);
            $('#closeMsgr').click();
        },
        error: function (xhr, err) {
            $('#sendTxtid').button('reset');
            $('#sendTxtid').prop('disabled', false);
            swal({
                icon: "warning",
                title: "Problem completing transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
    //sendEmail(items,cc,bcc, refno, message);
////    console.log("I am sending email");
////    var ckbox = $('#chckmail');
////
////    if (ckbox.is(':checked')) {
////        alert('You have Checked it');
////    } else {
////        alert('You Un-Checked it');
////    }
////    var url = "https://script.google.com/a/mtrh.go.ke/macros/s/AKfycbzzPQldFp8NaKwONFKcaUaGcBDXaj1YHhMOwxNPnTsqced2GRg/exec"
////    //  var url = "https://script.google.com/macros/s/123/exec?callback=ctrlq&name=";
////
////    var name = "Salim Mwaura"
////
////    var request = jQuery.ajax({
////        crossDomain: true,
////        // url: url ,
////        url: url,
////        method: "GET",
////        dataType: "jsonp"
////    });
//
//    Email.send({
//        Host: "41.89.183.8",
//        Username: "registry_alerts",
//        Password: "registry2019",
//        To: 'salimmwaura@mtrh.go.ke',
//        From: "registry_alerts@mtrh.go.ke",
//        Subject: "This is the subject",
//        Body: "And this is the body",
//        EnableSsl: false
//    }).then(
//            message => alert(message)
//    );
}
function sendMsg(pfno, refid, message) {

    console.log("I am sending email");
    var ckbox = $('#chckmail');

    if (ckbox.is(':checked')) {
        // alert('You have Checked it');



        $('#sendTxtid').button('loading');
        $('#sendTxtid').prop('disabled', true);
        var request = "refid=" + refid + "&staffid=" + pfno + "&message=" + message;
        $.ajax({
            type: 'GET',
            url: 'MessagingServlet',
            data: request,
            dataType: "text",
            success: function (data) {
                document.getElementById('commentboxid').value = "";
                swal({
                    icon: "success",
                    title: "Transaction completed successfully"
                });
                $('#sendTxtid').button('reset');
                $('#sendTxtid').prop('disabled', false);
                $('#closeMsgr').click();
            },
            error: function (xhr, err) {
                $('#sendTxtid').button('reset');
                $('#sendTxtid').prop('disabled', false);
                swal({
                    icon: "warning",
                    title: "Problem completing transaction",
                    text: "Reload page or seek for assistance from the Systems Administrator"
                });
            }
        });

    } else {
        alert('You Un-Checked it');
    }
}



function populateAllStaffGroups() {
    // $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'POST',
        url: 'GroupDynamics',
        dataType: "json",
        success: function (data) {

            console.log("data here");
            $("#contactslist").empty();
            $.each(data, function (key, value) {
                $("#contactslist").append('<option>' + value.staffdet + '</option>');
                $("#cctxt").append('<option>' + value.staffdet + '</option>');
                $("#bcctxt").append('<option>' + value.staffdet + '</option>');

            });
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}
function populateAllStaffGroups() {
    // $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'POST',
        url: 'AllStaff',

        dataType: "json",
        success: function (data) {

            console.log("data here");
            $("#contactslist").empty();
            $.each(data, function (key, value) {
                $("#contactslist").append('<option>' + value.staffdet + '</option>');
                $("#cctxt").append('<option>' + value.staffdet + '</option>');
                $("#bcctxt").append('<option>' + value.staffdet + '</option>');

            });
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}