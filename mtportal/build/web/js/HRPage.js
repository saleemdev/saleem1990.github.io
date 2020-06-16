/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var approvalLevel = "";
var myDept = ""
var UserDept = ""

var threshold = 0;

var msgrefno = "";


var askhrveruser = "";

var loggedinuser = "";
$(document).ready(function () {
//https://codepen.io/johnraiz/pen/jvYRey/ Editable

//https://codepen.io/amortka/pen/xAcHi GLyphicons

    bioData();

    window.setInterval(fetchMsgs, 3000);
    
    
    

    $('#removewg').click(function () {
        $('#myForm').hide();
    });

    $('#askhrsendmsgbtn').click(function () {
        sendMsg();


    });

    $('#verbtnid').click(function () {
        //  sendMsg();
        var pf = $('#verid').val();

        askHRVer(pf);
    });



    document.getElementById("coveringoffid1").style.visibility = "hidden";
    document.getElementById("immdsupervid1").style.visibility = "hidden";
    document.getElementById("resourcingid1").style.visibility = "hidden";
    document.getElementById("snrmgid1").style.visibility = "hidden";


//mywkld
    $('#mywkld').click(function () {
        var lvyr = $('#lvyrid').text();


        $('#lvyrid3').text(lvyr);

        ShowMyApprovals();

        $('#myworkloadid').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

//newmsgmodal

    $('#sendmsgbtn').click(function () {
        var text = $('#messageboxid').val();
        var usr = 'Salim';


        var msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + usr + ' @ 23 Jan 2:00 pm</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'
        if (text.toString().length > 2) {
            $('#parent').append(msg2append);
        }
        $('#messageboxid').val('');
    });

//    $('#mywkld').click(function () {
//        $('#newmsgmodal').modal({
//            keyboard: false,
//            backdrop: 'static'
//        });
//    });

    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

        $('#activitiesTblbd').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });
    $('#level1id').click(function () {
        //     $("coveringoffid1").click();
        populate("1.Confirmation of Covering officer");//1.Confirmation of Covering officer
        window.approvalLevel = "1.Confirmation of Covering officer";
    });

    $('#level2id').click(function () {
        populate("2.HOD/Immediate Supervisor");//2.HOD/Immediate Supervisor
        window.approvalLevel = "2.HOD/Immediate Supervisor";
    });
    $('#level3id').click(function () {
        populate("3.Resourcing Approval");//3.Resourcing Approval
        window.approvalLevel = "3.Resourcing Approval";
    });
    $('#level4id').click(function () {
        populate("4.Senior Management Approval");//4.Senior Management Approval
        window.approvalLevel = "4.Senior Management Approval";
    });

    //
    $('#pdf').on('click', function () {
        $("#activitiesTbl").tableHTMLExport({type: 'pdf', filename: 'sample.pdf'});
    });

    $('#csv').on('click', function () {
        $("#activitiesTbl").tableHTMLExport({type: 'csv', filename: 'sample.csv'});
    });
    //


    leavetypes();
    bioData();


    managerEntitled("3.Resourcing Approval");
    managerEntitled1("4.Senior Management Approval");


    var buttonStat = document.getElementById("updEntitlement").disabled;


    console.log(buttonStat);
    if (buttonStat == true) {
        console.log("yes done");
    } else {
        console.log("nope nope");//console.log("yes done");
    }

    // managerEntitled2("2.HOD/Immediate Supervisor");
    ///////////////
    var input7 = $('#leaveaddressid');
    input7.css("border", "2px solid red");
    $('.textarea').wysihtml5();
    ctmmonth();
    leaveyr();
    //  $('.select2').select2()
    //https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_password_val

    $('#changePwd').click(function () {
        $('#chPwdModal').modal({
            backdrop: 'static',
            keyboard: false
        });
    });
    $('#analyticsID').click(function () {
        var win = window.open('leaveanalytics.html', '_blank');
    });
    //


    $('#chPWBtn').click(function () {
        var pwd = $('#nwPw').val();
        $.ajax({
            type: 'POST',
            url: 'ChangePWD',
            data: "pwd=" + pwd,
            //  dataType: "text",
            success: function (data) {
                swal({
                    icon: "success",
                    title: "Password changed successfully"
                })

            }
        });
    });
    $('#nwPw').keyup(function () {
        var passwordentered = $('#oldPw').val();
        var value = $(this).val();
        if (value.length > 5) {

            if (value == passwordentered) {
                $("#nwPw").css("border", "2px solid green");
                $('#chPWBtn').prop("disabled", false);
            } else {
                $("#nwPw").css("border", "2px solid red");
                $('#chPWBtn').prop("disabled", true);
            }

        }
    })

    //savedefid
    $('#savedefid').click(function () {

        var reason = $('#reasoniddef').val();

        if (reason.length > 5) {
            var lvid = $('#lvid').val();
            var staffid = $('#staffiddef').val();

            var leavestart = $('#appldate1').val();

            //daysDeffered
            var days = $('#daysDeffered').val();

            var reason = $('#reasoniddef').val();

            var request = "leaveid=" + lvid + "&staffid=" + staffid + "&leavestart=" + leavestart + "&days=" + days + "&reason=" + reason;

            $.ajax({
                type: 'POST',
                url: 'Defferment',
                data: request,
                dataType: "text",
                success: function (data) {

                    swal({
                        icon: "success",
                        title: data,
                        text: "Deferrment saved successfully\nPlease forward application " + lvid + " now"



                    });


                    $('#defclodebtn').click();
                },
                error: function (xhr, err) {
                    alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                }
            });


        } else {
            swal({
                icon: "warning",
                title: "You cannot save without entering the reason!"
            })
        }
    });


    $('#savedataid')

            .click(function () {
                //Confirm if




                $('#savedataid').prop('disabled', true);
                var pfno = $('#empnoid').val();
                var leavetype = $('#lvtypeselectid').val();
                var daysapplied = $('#daysAppliedid').val();
                var appldate = $('#appldate').val();
                var officer = $('#example').val();
                var officerphone = $('#cvphoneid').val();
                var leaveaddr = $('#leaveaddressid').val();
                var officeremail = $('#cvemail').val();
                var comments = $('#commentboxid').val();
                var myMail = $('#emailid').val();
                var fullname = $('#fullnameid').val();
                var input = $('#lvtypeselectid');
                var input1 = $('#daysAppliedid');
                var input2 = $('#appldate');
                var input7 = $('#leaveaddressid');
                var input4 = $('#example');
                var input5 = $('#commentboxid');
                input4.css("border", "2px solid red");
                if (!input.val() || !input1.val() || !input2.val() || !input4.val() || !input5.val() || !input7) {
                    swal({
                        icon: "warning",
                        title: "Ensure all fields marked in red are entered"
                    });
                    input.css("border", "2px solid red");
                    input1.css("border", "2px solid red");
                    input2.css("border", "2px solid red");
                    input4.css("border", "2px solid red");
                    input5.css("border", "2px solid red");
                    input7.css("border", "2px solid red");
                } else {

                    var transtype = "enterdata";
                    var request = "transtype=" + transtype + "&pfno=" + pfno + "&leavetype=" + leavetype + "&daysapplied=" + daysapplied
                            + "&appldate=" + appldate + "&officer=" + officer + "&officeremail=" + officeremail + "&officerphone=" + officerphone +
                            "&comments=" + comments + "&myMail=" + myMail + "&fullname=" + fullname + "&leaveaddress=" + leaveaddr;
                    ///----------------------------

                    var verif = "staffid=" + pfno + "&lvtype=" + leavetype;
                    $.ajax({
                        type: 'POST',
                        url: 'VerifyDoubleLeave',
                        data: verif,
                        dataType: "text",
                        success: function (data) {
                            var ip = data;
                            if (ip.toString().includes("not actioned")) {
                                swal({
                                    icon: "warning",
                                    title: "Sorry, You have (a) pending " + leavetype + " leave application(s) and cannot proceed with this application before action is taken on them",
                                    text: "Please check your leave history."
                                });
                            } else {
                                applyLeave(request);
                            }
                        },
                        error: function (xhr, err) {
                            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                        }
                    });


                    //---------------------------------
                }
            });
//lvEntitlementid

    $('#uplRota').click(function () {
        $('#cvoffid').prop('disabled', true);
        $('#cvoffid').val('-');
        $('#lvEntitlementid').modal({

            keyboard: true,
            backdrop: 'static'
        });
    })


    $('#coveringoffid1').click(function () {
        populate($(this).text())
    });
    $('#immdsupervid1').click(function () {
        populate($(this).text())
    });
    $('#resourcingid1').click(function () {
        populate($(this).text())
    });
    $('#snrmgid1').click(function () {
        populate($(this).text())
    });
    $('#example').select2({
//   placeholder: 'Select a month'
    });
    $('#cvoffid1').select2({
//   placeholder: 'Select a month'
    });
    $('#cvoffid').select2({
//   placeholder: 'Select a month'
    });

    //  $('#appldate').datepicker("option", "dateFormat", 'yy-mm-dd');

    //cvoffid1
    //cvoffid
    var dateToday = new Date();
    dates = $('#appldate').datepicker({
        autoclose: true,
        minDate: dateToday,
        format: 'yyyy/mm/dd',
        minDate: dateToday,

//        option: {
//            dateFormat: 'yyyy-mm-dd'
//        },

//        onSelect: function (selectedDate) { 
//
//            var date = this.value;
//
//            alert(date)
//
//        }

    }).on("changeDate", function (date) {
//alert("Working" + this.value.getMonth());

        var date = this.value;
        //  var sqldate = $("#appldate").datepicker("option", "dateFormat", "yyyy-mm-dd" ).val()//$('#appldate').datepicker({ dateFormat: 'yy-mm-dd' }).val();

        //  swal(date);
        var objDate = new Date(date),
                locale = "en-us",
                month = objDate.toLocaleString(locale, {month: "long"});
        //alert(month);

        var leavetype = $('#lvtypeselectid').val();
        confirmLeaveMonth(leavetype, month);
    });
    ;
    /////////////
    dates2 = $('#appldate1').datepicker({
        autoclose: true,
        minDate: dateToday,
        format: 'yyyy/mm/dd',
        minDate: dateToday,

    });

    dates4 = $('#grantdate').datepicker({
        autoclose: true,
        minDate: dateToday,
        format: 'yyyy/mm/dd',
        minDate: dateToday,

    });
    ////////////
    $('#showApprovals').click(function () {
// alert("I was clicked");
        $('#myLeaveBalances').click(); //leavehistid
        $('#leavehistid').click();
    });
    $("#myLeaveTbl").on('click', '.btn', function () {
        var currentRow = $(this).closest("tr");
        //var rqId = currentRow.find("td:eq(4)").html();
        var leavetype = currentRow.find("td:eq(0)").html();
        //swal(leavetype);
        moreInfo(leavetype);
    });
//$('#savedataid').prop('disabled', false);
    $('#newapplicationid').click(function () {
        //  alert("I was clicked...")
        $('#savedataid').prop('disabled', false);
        var lvyr = $('#lvyrid').text();
        if (lvyr == '0') {
            swal({
                icon: "warning",
                title: "Leave year is not yet set up",
                text: "You cannot make an application until this is set up"
            });
        } else {
            //alert("In here")
            bioData();
            $('#leaveYridModal').text(lvyr);
            $('#applicationmodalid').modal({
                backdrop: 'static',
                keyboard: false

            });
        }
    });
    $('#example').on("change", function () {
        var name = $('#example').val();
        getUserBio(name);
    });
    $('#lvtypeselectid').on("change", function () {
// alert($('#lvtypeselectid').val());


        var selectedLvtype = $('#lvtypeselectid').val();
        var index = $("lvtypeselectid").index(this);
        if (index === 0) {
            $('#leavebalanceid').val("0");
        } else {
            getLeaveBal(selectedLvtype);

            getLeaveThreshold(selectedLvtype);
        }
    });

    $('#grantdate').on("changeDate", function (date) {
//alert("Working" + this.value.getMonth());

        var date = this.value;
        //  var sqldate = $("#appldate").datepicker("option", "dateFormat", "yyyy-mm-dd" ).val()//$('#appldate').datepicker({ dateFormat: 'yy-mm-dd' }).val();

        //  swal(date);
        var objDate = new Date(date),
                locale = "en-us",
                month = objDate.toLocaleString(locale, {month: "long"});
        //alert(month);

        var leavetype = $('#lvtypeselectid').val();

        var selectedmonth = $('#monthid1').val();

        if (month.toUpperCase().includes(selectedmonth.toUpperCase())) {
            //  swal("Horay")
        } else {
            $(this).val('');
            swal({icon: "warning",
                title: "Error!",
                text: "Sorry, the selected date is not in the month of " + selectedmonth + "\n\nYou have selected a date in " + month});
        }
        ;
        // confirmLeaveMonth(leavetype, month);
    });

    $('#lvtypeselectid1').on("change", function () {
        //exhaust pipe really messed me today, shit is expensive
        var selectedLvtype = $('#lvtypeselectid1').val();

        //   alert(selectedLvtype);
        var request = "leavetype=" + selectedLvtype;
        $.ajax({
            type: 'GET',
            url: 'LeaveProp',
            dataType: "text",
            data: request,
            success: function (data) {
                $('#permtypeid').val(data);

                if (data.toString().includes("GRANTED")) {
                    swal({
                        icon: "warning",
                        title: "Reminder!",
                        text: "For granted leave, please ensure to set the approved start date"

                    })

                    //  $('#grantdate').focus();


                }

            }
        });
        //Populate Depends on
        var stfid = $("#pfnoToSearch").val();
        if (stfid.length > 2) {
            $.ajax({
                type: 'POST',
                url: 'LeaveProp',
                dataType: "text",
                data: request + "&staffid=" + stfid,
                success: function (data) {
                    $('#dependsonid').val(data);



                }
            });


        } else {
            swal("Please enter a staff id first")

        }


    });

    $('#sendMsgid').click(function () {

        var message = $('#commentboxid1').val();
        var title = $('#communicationTitleID').text(); //

        var items = title.toString().split("-");
        var refid = items[0];
        var pfno = items[1];
        sendMsg(pfno, refid, message);
        //     swal(pfno+"-----"+ refid, message);

    });
    $('#saveEntitlementid').click(function () {
        var input1 = $('#pfnoToSearch');
        var input2 = $('#lvtypeselectid1');
        var input3 = $('#daysEntitled');
        var input4 = $('#cvoffid');
        var input5 = $('#monthid1');
        if (!input1.val() || !input2.val() || !input3.val() || !input4.val() || !input5.val()) {
            input1.css("border", "2px solid red");
            input2.css("border", "2px solid red");
            input3.css("border", "2px solid red");
            input4.css("border", "2px solid red");
            input5.css("border", "2px solid red");
        } else {

            var dependsonf = $('#dependsonid').val();

            var dependson = dependsonf.toString().split("/")[0];
            var bal = dependsonf.toString().split("/")[1];
            var days = input3.val()


            var verif = "staffid=" + input1.val() + "&lvtype=" + input2.val();//1. Check if staff has pending leave
            $.ajax({
                type: 'POST',
                url: 'VerifyDoubleLeave',
                data: verif,
                dataType: "text",
                success: function (data) {
                    var ip = data;
                    if (ip.toString().includes("not actioned")) { //1.0 Abort if staff has pending leave
                        swal({
                            icon: "warning",
                            title: "Sorry, You have (a) pending " + input2.val() + " leave application(s) and cannot proceed with this application before action is taken on them",
                            text: "Please check your leave history."
                        });
                    } else { //1.1 Proceed if staff has no pending leave
                        //applyLeave(request);// DO whatever you ought....
                        if (dependson.toString().length > 3) { //Check if has dependant
                            if (parseInt(days) <= parseInt(bal)) { //Check if dependant has balance 
                                var leaveyr = $('#lvyrid').text();
                                var pf = input1.val();
                                var leavetype = input2.val();

                                var covering = input4.val()
                                var month = input5.val();
                                var date = $('#grantdate').val();
                                var request = "pfno=" + pf + "&leaveyr=" + leaveyr + "&leavetype=" + leavetype + "&days=" + days + "&covering=" + covering + "&month=" + month + "&date=" + date;
                                swal({
                                    title: "Please Confirm this Information to be correct before clicking [OK]: ",
                                    text: "PF No: " + pf + "\nName:" + $('#nigganame').text() + "\nLeavetype: " + leavetype + "\nDays: " + days + "\nCovering Officer: " + covering + "\nfor the month of " + month,
                                    icon: "info",
                                    buttons: true,
                                    dangerMode: true,
                                })
                                        .then((willDelete) => {
                                            if (willDelete) {

                                                saveEntitleMent(request, pf, "");
                                            } else {
                                                swal("Transaction aborted!");
                                            }
                                        });
                            } else {
                                swal("Sorry, there is no sufficient " + dependson + " leave balance to proceed with your entry ");
                            }

                        } else {
                            var leaveyr = $('#lvyrid').text();
                            var pf = input1.val();
                            var leavetype = input2.val();

                            var covering = input4.val()
                            var month = input5.val();
                            var date = $('#grantdate').val();
                            var request = "pfno=" + pf + "&leaveyr=" + leaveyr + "&leavetype=" + leavetype + "&days=" + days + "&covering=" + covering + "&month=" + month + "&date=" + date;
                            swal({
                                title: "Please Confirm this Information to be correct before clicking [OK]: ",
                                text: "PF No: " + pf + "\nName:" + $('#nigganame').text() + "\nLeavetype: " + leavetype + "\nDays: " + days + "\nCovering Officer: " + covering + "\nfor the month of " + month,
                                icon: "info",
                                buttons: true,
                                dangerMode: true,
                            })
                                    .then((willDelete) => {
                                        if (willDelete) {

                                            saveEntitleMent(request, pf, "");
                                        } else {
                                            swal("Transaction aborted!");
                                        }
                                    });
                        }
                        //--TO here
                    }
                },
                error: function (xhr, err) {
                    alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                }
            });//
        }


    });
    $('#LeaveRotaid').click(function () {
        bioData();
        var dept = window.myDept;
        $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",
            success: function (data) {
                var ip = data;
                var doctype = "ENTITLEMENTS_DEPT";
                var trans = "pdfname=" + doctype + "&refno=&dept=" + dept;
                window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
            },
            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });
    $('#updEntitlement').click(function () {
        var oid = $('#oidid').text();
        var lvtype = $('#lvtypeselectid2').val();
        var days = $('#daysEntitled1').val();
        var cvof = $('#cvoffid1').val();
        var month = $('#monthid2').val();
        if (cvof.toString().length > 2) {

            var request = "oid=" + oid + "&lvtype=" + lvtype + "&days=" + days + "&cvof=" + cvof + "&month=" + month;
            $.ajax({
                type: 'POST',
                url: 'LeaveRota',
                data: request,
                dataType: "text",
                success: function (data) {
                    if (data == "NOT OK") {
                        swal({
                            icon: "warning",
                            title: "Transaction did not complete",
                            text: "There was a leave conflict between the selected month for the staff and their releiver"
                        });
                    } else {
                        var staffid = $('#pfnoToSearch').val();
                        getLeaveEntitlement(staffid);
                        $('#closeeditBtnid').click();
                        swal("Leave Information Updated Successfully");
                    }
                }
            });
        } else {
            swal({
                icon: "warning",
                title: "Covering officer cannot be Empty"
            });
        }
    });
    myData();
    leaveBalances();
    leaveHistory();
    $('#showEntitlements').click(function () {
        var input = $('#pfnoToSearch');
        if (!input.val()) {
            input.css("border", "2px solid red");
        } else {
            var staffid = $('#pfnoToSearch').val();
            getLeaveEntitlement(staffid)
        }
    });
});
function searchPF() {



    // Get the input field
    var input = document.getElementById("pfnoToSearch");
// Execute a function when the user releases a key on the keyboard
    input.addEventListener("keyup", function (event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Trigger the search Fuunction
            var staffid = $('#pfnoToSearch').val();
            getLeaveEntitlement(staffid);
        }
    });
}
///
function ShowMyApprovals() {
    $.ajax({
        type: 'POST',
        url: 'MyTasks',
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $('#activitiesTblbd').empty();
            $.each(data, function (key, value) {
                activitydata += '<tr>';
                activitydata += '<td>' + value.refno + '</td>'; //1
                activitydata += '<td>' + value.level + '</td>'; //2
                activitydata += '<td>' + value.applicant + '</td>'; //3
                activitydata += '<td>' + value.leavetype + '</td>'; //4                
                var action = value.action.toString().toUpperCase();

                if (action.toString().includes("APPROVED")) {
                    activitydata += '<td><span class="label label-primary">' + value.action + '</span></td>'; //5
                } else {
                    activitydata += '<td><span class="label label-danger">' + value.action + '</span></td>';//5
                }

                activitydata += '<td><button onclick="openApplication(this)">Open</button></td>';//6


                activitydata += '</tr>';
                i += 1;
            });

            $('#activitiesTblbd').empty().append(activitydata);

            if (i > 0) {
                $('#tasksstat').text('');
            } else {
                $('#tasksstat').text('No tasks for now');
            }
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
function openApplication(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("activitiesTbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);
    print(cellvalue)

}
////
function saveEntitleMent(request, staffid, transno) {//transno is for deleting                  
    $.ajax({
        type: 'GET',
        url: 'LeaveSetup',
        data: request + "&transno=" + transno,
        dataType: "text",
        success: function (data) {

            if (data == "NOT OK") {
                swal({
                    icon: "warning",
                    title: "Transaction did not complete",
                    text: "There was a leave conflict between the selected month for the staff and their releiver"
                });
            } else {
                getLeaveEntitlement(staffid);
                swal("Transaction completed successfully");
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem removing transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}
function getLeaveEntitlement(staffid) {


    var request = "staffid=" + staffid;
    $.ajax({
        type: 'POST',
        url: 'LeaveSetup',
        data: request,
        dataType: "json",
        success: function (data) {



            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {
                //   i+=1;
// String[] columns = new String[]{"refno", "empname", "leavetype", "particulars"};
                activitydata += '<tr id=' + value.rota_fy + '>';
                activitydata += '<td>' + value.rota_fy + '</td>';
                activitydata += '<td>' + value.leavetype + '</td>';
                activitydata += '<td>' + value.days_plus + '</td>';
                activitydata += '<td>' + value.coveringofficer + '</td>';
                activitydata += '<td>' + value.month + '</td>';
                activitydata += '<td>' + value.oid + '</td>';
                activitydata += '<td> <span onclick="editEntitlement(this)" class="table-remove glyphicon glyphicon-check"></span></td>';
                activitydata += '<td> <span onclick="deleteentitlement(this)" class="table-remove glyphicon glyphicon-remove"></span> </td>';
                $('#designationID').text(value.designation);
                activitydata += '</tr>';
                i += 1;
            });
            $('#cvoffid').prop('disabled', false);
            myEntitlementColleagues("", staffid);
            getStaffBioByStaffid("", staffid);
            if (i >= 1) {
                console.log("Good");
            } else {
                console.log("Bad ");
            }

            $('#lvtbodyID').empty().append(activitydata);
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



function editEntitlement(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("entitlementTblid");
    var col = 5;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var staffid = $('#pfnoToSearch').val();
    var staffname = $('#nigganame').text();
    $('#oidid').text(cellvalue);
    $('#nametoeditid').text(staffname);
    var leavetype = tbl.rows[row].cells[1].innerHTML;
    var days = tbl.rows[row].cells[2].innerHTML;
    var personcov = tbl.rows[row].cells[3].innerHTML;
    var month = tbl.rows[row].cells[4].innerHTML;
    console.log(personcov);
    // myEntitlementColleagues("", staffid);

    myEntitlementColleagues("", staffid);
    $('#lvtypeselectid2').val(leavetype);
    $('#cvoffid1').val(personcov.toString().trim().toUpperCase());
    $('#daysEntitled1').val(days);
    $('#monthid2').val(month);
    $('#editEntitlementModal').modal({
        backdrop: 'static',
        keyboard: true
    });
    // saveEntitleMent("",staffid,cellvalue)

}

function deleteentitlement(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("entitlementTblid");
    var col = 5;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    alert("Deleted " + cellvalue);
    var staffid = $('#pfnoToSearch').val();
    saveEntitleMent("", staffid, cellvalue)

}

function confirmLeaveMonth(leavetype, month) {

    if (leavetype.length < 1) {

    } else {

    }
    var request = "leavetype=" + leavetype + "&month=" + month;
    $.ajax({
        type: 'POST',
        url: 'LeaveDates',
        data: request,
        dataType: "text",
        success: function (data) {

            if (data == 1) {
                var sel_date = $('#appldate').val(); //Selected Date

                var leavetype = $('#lvtypeselectid').val();

                //var date = new Date(Date.parse(sel_date));//new Date(sel_date) - 30;


                var today = new Date(); //Today's date
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();

                if (dd < 10) {
                    dd = '0' + dd;
                }

                if (mm < 10) {
                    mm = '0' + mm;
                }

                today = yyyy + '/' + mm + '/' + dd;



                var todaysec = Date.parse(today);

                var futuresec = Date.parse(sel_date);

                var diff = futuresec - todaysec;

                var limit = parseInt(threshold) * 86400000

                if (diff >= limit) {

                } else {

                    $('#appldate').val('');

                    swal({
                        icon: "warning",
                        title: "Sorry the minimum date threshold for " + leavetype + " application is " + threshold + " days",
                        text: "Please change the date appropriately"
                    });
                }


            } else {
                $('#appldate').val('');
                swal({
                    icon: "warning",
                    title: "Sorry, you do not have " + leavetype + " scheduled for " + month,
                    text: "Please contact Human Resources"
                });
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem completing transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}

function sendMsg(pfno, refid, message) {


    var request = "refid=" + refid + "&staffid=" + pfno + "&message=" + message;
    $.ajax({
        type: 'POST',
        url: 'MessagingServlet',
        data: request,
        dataType: "text",
        success: function (data) {
//clear the text area
            //$('#commentboxid1').text("");
            document.getElementById('commentboxid1').value = "";
            swal({
                icon: "success",
                title: data
                        // text: "Reload page or seek for assistance from the Systems Administrator"
            });
            $('#closeMsgr').click();
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem completing transaction",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}


function leaveyr() {
    $.ajax({
        type: 'POST',
        url: 'LeaveYear',
        //  data: request,
        dataType: "text",
        success: function (data) {

            $('#lvyrid').text(data);
            if (data.length < 1) {
                $('#lvyrid').text('0');
            }

        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem fetching data",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}

function managerEntitled1(stage) {
//alert("ngee "+stage);
    var request = "stage=" + stage;
    $.ajax({
        type: 'GET',
        url: 'ApprovalLevel',
        data: request,
        dataType: "text",
        success: function (data) {

            if (data == 0) {

                $('#analyticsID').prop('disabled', false);
            } else {
                $('#analyticsID').prop('disabled', true);
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem fetching data",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}


function managerEntitled(stage) {
//alert("ngee "+stage);
    var request = "stage=" + stage;
    $.ajax({
        type: 'GET',
        url: 'ApprovalLevel',
        data: request,
        dataType: "text",
        success: function (data) {

            if (data == 0) {

                $('#uplRota').prop('disabled', false);
            } else {
                $('#uplRota').prop('disabled', true);
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem fetching data",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });//0729227431
}
function populate(stage) {
// alert("ngee "+stage);
    var request = "stage=" + stage;
    $.ajax({
        type: 'GET',
        url: 'ApprovalLevel',
        data: request,
        dataType: "text",
        success: function (data) {

            if (stage.includes("Resourcing")) {
                if (data == 0) {
                    window.approvalLevel = stage;
                    fetchLeaveApplicationData(stage);
//                swal({
//                    icon: "success",
//                    title: "yaay" + data
//                });
                } else {
                    swal({
                        icon: "warning",
                        title: "You have no approval privileges to: " + stage

                    });
                }
            } else {
                window.approvalLevel = stage;
                fetchLeaveApplicationData(stage);
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem fetching data",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}


function fetchLeaveApplicationData(stage) {
    var request = "stage=" + stage;
    $.ajax({
        type: 'POST',
        url: 'ApprovalLevel',
        data: request,
        dataType: "JSON",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {
// String[] columns = new String[]{"refno", "empname", "leavetype", "particulars"};


                activitydata += '<tr id=' + value.refno + '>';
                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td>' + value.empname + '</td>';
                activitydata += '<td>' + value.leavetype + '</td>';
                //activitydata += '<td>' + value.particulars + '</td>';
                var name = value.particulars.split(",")[0];

                // swal("Showing hist of "+staffid);


                var finame = name.toString().replace("Applicant Name: ", "") + "'s";


                activitydata += '<td>' + value.particulars + '<br><br><strong>Department:</strong> ' + value.dept + '<br><strong>Section:</strong> ' + value.section + '<br><button class="btn-link" onclick="openhist(this)">View ' + finame + ' leave history</button><br></td>';
                //activitydata += '<td>' + value.particulars + '<br><br><strong>Department:</strong> ' + value.dept + '<br><strong>Section:</strong> ' + value.section + '<br><button class="btn-link" onclick="openhist(this)">View leave history</button><br><button class="btn-link" onclick="opencomputation(this)">View leave computation</button></td>';


                activitydata += '<td>  <button onclick="approve(this)" class="btn btn-success  btn-app"><i class="fa fa-check"></i></button></td>';
                activitydata += '<td>  <button onclick="view(this)" class="btn btn-warning btn-app"><i class="fa fa-folder-open"></i></button></td>';
                activitydata += '<td>  <button onclick="reject(this)" class="btn btn-danger btn-app"><i class="fa fa-remove"></i></button></td>';
                activitydata += '<td>  <button onclick="mail(this)" class="btn btn-app"><i class="fa fa-envelope-o"></i></button></td>';

                if (!stage.includes("Confirmation")) {
                    activitydata += '<td>  <button onclick="editLeave(this)" class="btn btn-app"><i class="fa fa-edit"></i></button></td>';
                }

                activitydata += '</tr>';
                i += 1;
            });
            $('#myTasksTblBody').empty().append(activitydata);
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Problem fetching data",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}

function openhist(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    // alert()
    // var tbl = document.getElementById($(element).parent().id);
    var lvid = tbl.rows[row].cells[0].innerHTML;
    var staffid = tbl.rows[row].cells[1].innerHTML;

    var name = tbl.rows[row].cells[3].innerHTML.split(",")[0];

    // swal("Showing hist of "+staffid);
    $('#statstitle').text(name);

    var finame = name.toString().replace("Applicant Name: ", "");
    $.ajax({
        type: 'GET',
        url: 'LeaveBalances',
        dataType: "json",
        data: "staffid=" + staffid,
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.leaveid + '</td>';
                activitydata += '<td>' + value.leavetype + '</td>';
                activitydata += '<td>' + value.daysreq + '</td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                var stat = value.status;
                if (stat === "approved") {
                    activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-success">' + value.status + '</button></td>'
                } else if (stat === "rejected") {
                    activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-danger">' + value.status + '</button></td>'
                } else {
                    activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-warning">' + value.status + '</button></td>'
                }

                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#myStatstblbd').empty().append(activitydata);
        }
    });

    $('#viewstatmodal').modal({
        keyboard: false,

    })
}

function opencomputation(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var lvid = tbl.rows[row].cells[0].innerHTML;
    var staffid = tbl.rows[row].cells[1].innerHTML;

    var leavetype = tbl.rows[row].cells[2].innerHTML.split("/")[0];

    //swal("Showing computation for " + lvid);
    //$('#myStatstbl').empty();
    $.ajax({
        type: 'POST',
        url: 'LeaveHistory',
        dataType: "json",
        data: "refno=" + lvid + "&lvtype=" + leavetype,
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.parameter + '</td>';
                activitydata += '<td>' + value.answer + '</td>';
                activitydata += '<td></td>';
                activitydata += '<td></td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';




                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';

            $('#myStatstbl').empty().append(activitydata);
        }
    });

    $('#viewstatmodal').modal({
        keyboard: false,

    });






}
function editLeave(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var lvid = tbl.rows[row].cells[0].innerHTML;
    var staffid = tbl.rows[row].cells[1].innerHTML;

    var typeofleave = tbl.rows[row].cells[2].innerHTML;

    var particulars = tbl.rows[row].cells[3].innerHTML;


    $('#lvid').val(lvid);
    $('#staffiddef').val(staffid);
    $('#typeoflvrq').val(typeofleave);
    $('#lvbaldef').val(0);
    $('#partdef').val(particulars);


    var elem = element;
    $('#deferrmentModal').modal({
        backdrop: 'static',
        keyboard: false

    });



}
function mail(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    col = 1;
    var pfno = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
    $('#communicationTitleID').text(cellvalue + "-" + pfno)
//msgtitleid
//$('#msgtitleid').text(cellvalue + "-" + pfno)
//  $('#newmsgmodal').modal({
//            keyboard: false,
//            backdrop: 'static'
//        });
    $('#communicationID').modal({
        backdrop: 'static',
        keyboard: true
    });
}

function view(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);
    print(cellvalue)

}

function print(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "LEAVE_APPLICATION";
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function printEntitlements() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "ENTITLEMENTS";
            var trans = "pdfname=" + doctype + "&refno=";
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function approve(element) {


    $(element).prop('disabled', true);
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);
    var approvallevel = level;

    if (level.toString().includes("Confirmation")) {
        approvallevel = "Immediate Supervisor Approval"
    }
    if (level.toString().includes("HOD")) {
        approvallevel = "Department Head/Manager Approval"
    }

    console.log(level);


    if (level.toString().includes("Resourcing")) {//Resourcing approval chack approval month
        $.ajax({
            type: 'POST',
            url: 'CheckForBiddenMonth',
            data: "rqid=" + cellvalue,
            success: function (data) {
                
                console.log(data)
                if (data.toString().includes("YES")) { //Confirm if they really want to approve
                    swal({
                        title: "Error: Leave Spilled over- "+cellvalue,
                        text: "This leave spills over to months that aren't authorized. Would you like to approve or defer first?",
                        icon: "warning",
                        //type: "error",
                        buttons: true,
                        dangerMode: true,
                    })
                            .then((willDelete) => {
                                if (willDelete) {

                                    var action = "approve";
                                    console.log(level + "" + cellvalue + "" + action)
                                    saveData(level, cellvalue, action);
                                } else {
                                    swal("Please defer the leave appropriately");
                                    $(element).prop('disabled', false);
                                }
                            });

                } else { //Just Approve
                    swal({
                        title: "Approval Level: " + approvallevel,
                        text: "Sure to approve " + cellvalue,
                        icon: "info",
                        buttons: true,
                        dangerMode: true,
                    })
                            .then((willDelete) => {
                                if (willDelete) {

                                    var action = "approve";
                                    console.log(level + "" + cellvalue + "" + action)
                                    saveData(level, cellvalue, action);
                                } else {
                                    swal("Transaction cancelled!");
                                    $(element).prop('disabled', false);
                                }
                            });

                }

            }
        });


    } else {

        swal({
            title: "Approval Level: " + approvallevel,
            text: "Sure to approve " + cellvalue,
            icon: "info",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {

                        var action = "approve";
                        console.log(level + "" + cellvalue + "" + action)
                        saveData(level, cellvalue, action);
                    } else {
                        swal("Transaction cancelled!");
                        $(element).prop('disabled', false);
                    }
                });
    }
    //     element.prop('disabled', false);
}
function reject(element) {


    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("myTasksTlid");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    var pfno = tbl.rows[row].cells[1].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);

    $('#communicationTitleID').text(cellvalue + "-" + pfno)
    $('#communicationID').modal({
        backdrop: 'static',
        keyboard: true
    });

    swal({
        title: "Approval Level: " + level,
        text: "Sure to reject " + cellvalue,
        icon: "info",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {


                    var action = "reject";
                    saveData(level, cellvalue, action);
                } else {
                    swal("Transaction cancelled!");
                }
            });
}

function saveData(level, cellvalue, action) {
    var request = "transtype=" + level + "&refid=" + cellvalue + "&action=" + action;
    $.ajax({
        type: 'POST',
        url: 'Approvals',
        data: request,
        success: function () {

            swal({
                icon: "success",
                title: cellvalue + " has been " + action + "'d successfully"
            });
            populate(level);
        }
    });
}


function calculateDays() {
    var leavetype = $('#lvtypeselectid').val();
    var leaveBalance = parseInt($('#leavebalanceid').val());
    var daysApplied = parseInt($("#daysAppliedid").val());
    console.log(leavetype + " " + leaveBalance + " Applied: " + daysApplied);
    if (leaveBalance == null) {

    } else {
        //  if(leavetype.toString().includes(""))
        if (daysApplied > leaveBalance) {
            swal({
                icon: "warning",
                title: "Error in the days entered",
                text: "Please edit the Days Applied For:\n " + daysApplied + " > " + leaveBalance
            });
            $("#daysAppliedid").val("0");
        } else {

        }
    }
}
function applyLeave(request) {
//   var request = "name=" + name;
    $('#closapplmodalid').click();
    $.ajax({
        type: 'GET',
        url: 'LeaveApplication',
        data: request,
        dataType: "text",
        success: function (data) {
            if (data.toString().includes("Failed")) {
                swal({
                    icon: "warning",
                    title: data


                });
            } else {
                swal({
                    icon: "success",
                    title: "Leave Application Sent for Approval\nRef Number: " + data



                });
            }
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error sending data to the server",
                text: "Reload page or seek for assistance from the Systems Administrator"
            });
        }
    });
}


/////
function getStaffBioByStaffid(name, staffid) {
    var request = "name=" + name + "&staffid=" + staffid;
    $.ajax({
        type: 'GET',
        url: 'MyColleagues',
        data: request,
        dataType: "json",
        success: function (data) {

//               /new String[]{"empno", "name", "designation", "email", "phone", "account","department"};

            var email = data.email;
            var phone = data.phone;
            var name = data.name;
            window.UserDept = data.department;
            $('#nigganame').text(name);
            //emailid
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data for selected value"
            });
        }
    });
}
function getUserBio(name) {
    var request = "name=" + name;
    $.ajax({
        type: 'GET',
        url: 'MyColleagues',
        data: request,
        dataType: "json",
        success: function (data) {

//               /new String[]{"empno", "name", "designation", "email", "phone", "account","department"};

            var email = data.email;
            var phone = data.phone;
            //$('#fullnameid').val(fullname);
            //$('#designationid').val(rank);
            //$('#empnoid').val(staffid);
            // $('#sectionid').val(department);
            //   $('#dept2id').val(department);
            $('#cvphoneid').val(phone);
            $('#cvemail').val(email);
            //emailid
        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data for selected value"
            });
        }
    });
}
//getLeaveThreshold
function getLeaveThreshold(selectedLvtype) {
//console.log("Getting balances")
    $('#appldate').val('');
    $.ajax({
        type: 'POST',
        url: 'LeaveThreshold',
        dataType: "json",
        success: function (data) {

            var i = 0;
            $.each(data, function (key, value) {

                var leavetype = value.leavetype

                //  console.log(leavetype+"-"+value.days);

                if (leavetype.toUpperCase() == selectedLvtype.toString().toUpperCase()) {
                    console.log(leavetype + "-Threshold " + value.days);
                    //$('#leavebalanceid').val(value.days);
                    threshold = value.days;
                }




            });
            // activitydata += '</tbody>';

        }
    });
}
function getLeaveBal(selectedLvtype) {
//console.log("Getting balances")

    $.ajax({
        type: 'POST',
        url: 'LeaveBalances',
        dataType: "json",
        success: function (data) {

            var i = 0;
            $.each(data, function (key, value) {

                var leavetype = value.leavetype

                //  console.log(leavetype+"-"+value.days);

                if (leavetype.toUpperCase() == selectedLvtype.toString().toUpperCase()) {
                    console.log(leavetype + "-" + value.days);
                    $('#leavebalanceid').val(value.days);
                }




            });
            // activitydata += '</tbody>';

        }
    });
}
function bioDataEdit(staffid) {
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
            $('#fullnameid').val(fullname);
            $('#designationid').val(rank);
            $('#empnoid').val(staffid);
            $('#sectionid').val(department);
            $('#dept2id').val(department);
            $('#emailid').val(email);
//currDeptid

            $('#currDeptid').text(department);
            window.myDept = department;
            myColleagues(department);
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
            myColleagues(department);
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


function myColleagues(department) {
//populate colleagues 
//console.log(department + "fuck this");
    var request = "department=" + department;
    $.ajax({
        type: 'POST',
        url: 'MyColleagues',
        data: request,
        dataType: "json",
        success: function (data) {
            $('#example').empty();
            $('#example').append('<option>-Select-</option>');
            console.log("I am here")
            $.each(data, function (key, value) {

                var name = value.name;
                console.log(name)
                if (name.toString().includes("on leave")) {
                    $('#example').append('<option disabled="disabled" value="' + name + '">' + name + '</option>');
                } else {
                    $('#example').append('<option value="' + name + '">' + name + '</option>');
                }
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

function leavetypes() {

    $.ajax({
        type: 'POST',
        url: 'LeaveTypes',
        // data: request,
        dataType: "json",
        success: function (data) {
            $('#lvtypeselectid2').empty();
            $('#lvtypeselectid1').empty();
            $('#lvtypeselectid').empty();
            $('#lvtypeselectid2').append(' <option selected="selected">-</option>  ');
            $('#lvtypeselectid1').append(' <option selected="selected">-</option>  ');
            $('#lvtypeselectid').append(' <option selected="selected">-</option>  ');
            console.log("I am here populating leavetypes")
            $.each(data, function (key, value) {

                var name = value.name;
                $('#lvtypeselectid2').append('<option value="' + name + '">' + name + '</option>');
                $('#lvtypeselectid1').append('<option value="' + name + '">' + name + '</option>');//lvtypeselectid
                $('#lvtypeselectid').append('<option value="' + name + '">' + name + '</option>');//lvtypeselectid
                //
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



function myEntitlementColleagues(department, staffid) {
//populate colleagues 
//console.log(department + "fuck this");
    var request = "department=" + department + "&transtype=allof" + "&staffid=" + staffid;
    $.ajax({
        type: 'POST',
        url: 'MyColleagues',
        data: request,
        dataType: "json",
        success: function (data) {
            $('#cvoffid').empty();
            $('#cvoffid1').empty();
            $('#cvoffid').append(' <option selected="selected">-</option>  ');
            $('#cvoffid1').append(' <option selected="selected">-</option>  ');
            console.log("I am here")
            $.each(data, function (key, value) {

                var name = value.name;
                $('#cvoffid').append('<option value="' + name + '">' + name + '</option>');
                $('#cvoffid1').append('<option value="' + name + '">' + name + '</option>');
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
function myData() {
    console.log("Getting User Data");
    $.ajax({
        type: 'POST',
        url: 'DashBoard',
        dataType: "json",
        success: function (data) {


        }
    });
    $.ajax({
        type: 'GET',
        url: 'DashBoard',
        dataType: "json",
        success: function (data) {


        }
    });
}
function leaveBalances() {
    console.log("Getting balances")
    var lvyr = $('#lvyrid').text();
    console.log(lvyr)
    $.ajax({
        type: 'POST',
        url: 'LeaveBalances',
        data: "leaveyear=" + lvyr,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.leavetype + '</td>';
                activitydata += '<td>' + value.days + '</td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                activitydata += '<td ><button  class="btn btn-outline-success btn-sm">More Details</button></td>'
                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#lvbalBdid').empty().append(activitydata);
        }
    });
}




function leaveHistory() {
    $.ajax({
        type: 'GET',
        url: 'LeaveBalances',
        dataType: "json",
        data: "staffid=",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.leaveid + '</td>';
                activitydata += '<td>' + value.leavetype + '</td>';
                activitydata += '<td>' + value.daysreq + '</td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                var stat = value.status;
                if (stat === "approved") {
                    activitydata += '<td ><button onclick="preview(this,1)" class="btn btn-success">' + value.status + '</button></td>'
                } else if (stat === "rejected") {
                    activitydata += '<td ><button onclick="preview(this,1)" class="btn btn-danger">' + value.status + '</button></td>'
                } else {
                    activitydata += '<td ><button onclick="preview(this,1)" class="btn btn-warning">' + value.status + '</button></td>'
                }

                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#lvHistidbd').empty().append(activitydata);
        }
    });
}

function preview(element, val) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    if (val.toString().includes("1")) {
        tbl = document.getElementById("lvHistTbl");
        console.log("Default..")
    } else {
        tbl = document.getElementById("myStatstbl");
        console.log("Fabricated..")
    }
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);
    print(cellvalue)

}
function moreInfo(leavetype) {

    $.ajax({
        type: 'GET',
        url: 'LeaveInfo',
        data: 'leavetype=' + leavetype,
        dataType: "text",
        success: function (data) {

            swal("Info:" + leavetype, data.replace("?", "-"));
        }
    });
}

function ctmmonth() {
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var now = new Date();
    var thisMonth = months[now.getMonth()]; // getMonth method returns the month of the date (0-January :: 11-December)
    var thisYear = now.getFullYear();
    var output = document.getElementById('ctmhrs');
    var output2 = document.getElementById('LeaveRotaid');
    console.log(thisMonth);
    thisMonth = "CTM HOURS: (" + thisMonth + ")";
    thisYear = "Leave Rota: (" + thisYear + ")";
    if (output.textContent !== undefined) {
        output2.textContent = thisYear;
        output.textContent = thisMonth;
    } else {
        output.innerText = thisMonth;
        output2.textContent = thisYear;
    }

// check also http://stackoverflow.com/questions/4822852/how-to-get-the-day-of-week-and-the-month-of-the-year
}

function verifyleave() {
    var leavetype = $('#lvtypeselectid1').val();

    if (leavetype.toString().length > 1) {

    } else {

        $('#daysEntitled').val(0)
        swal({
            icon: "warning",
            title: "Leave type not selected",
        });
    }

}


function enter2sendMsg() {
    // Get the input field
    var input = document.getElementById("askhrmessageboxid");
// Execute a function when the user releases a key on the keyboard
    input.addEventListener("keyup", function (event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Trigger the search Fuunction
            var staffid = $('#askhrmessageboxid').val();
            // swal(staffid)

            var user = window.askhrveruser;

            if (user.length > 3) {
                sendMsg();
            } else {
                swal("Sorry, you are not permitted to use this service without PF Number Verification")
            }
        }
    });
}

function sendMsg() {

    //  var ipinfo = getMyIP();

    //console.log(ipinfo);



    $('#askhrmessageboxid').focus();

    var text = $('#askhrmessageboxid').val();
    // swal(text)

    var userdet = window.askhrveruser;

    var arr = userdet.split("/");

    var trans = "";

    var usr = arr[0];

    var timestamp = new Date();

    var pfinq = arr[1];

    if (window.msgrefno.length < 2) {
        trans = "S";
        console.log("Logging a new tx " + trans)
        var newid = makeid(10) + "/" + pfinq;
        window.msgrefno = newid;
        $('#chatidTxt').text("Chat ID:" + newid)


    } else {
        trans = "R";
    }
    var refno = window.msgrefno;


    var msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + usr + ' @ ' + timestamp + '</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'

    // swal("refno=" + refno + "&message=" + text + "&username=" + usr + "@" + timestamp + "&pfno=" + pfinq + "&transtype=" + trans)
    if (text.toString().length > 2) {

        $.ajax({
            type: 'POST',
            url: 'AskHR',
            data: "refno=" + refno + "&message=" + text + "&username=" + usr + "@" + timestamp + "&pfno=" + pfinq + "&transtype=" + trans,
            dataType: "text",
            success: function (data) {


                if (data.toString().includes("FAIL")) {
                    alert("Failure sending data\n" + data)
                } else {
                    $('#parentchatbox').append(msg2append);
                }

            }, error: function (xhr, err) {

                alert(xhr)
            }
        });


    }

    $('#askhrmessageboxid').val('');
}




function askHRVer(staffid) {

    //  swal("Invoked..");
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
            $('#verid').val(fullname + "/" + staffid);

            window.askhrveruser = fullname + "/" + staffid;

            window.loggedinuser = fullname;

            $('#verbtnid').prop('disabled', 'true');
            $('#verbtnid').text('Verified');

            $('#askhrsendmsgbtn').prop('disabled', false);

            $('#verid').prop('readonly', true);




        },
        error: function (xhr, err) {

            swal({
                icon: "warning",
                title: "Error loading data"
            });
        }
    });
}

function fetchMsgs() {
    var refno = window.msgrefno;

    console.log("Getting messages for...[" + refno + "]")
    if (msgrefno.length > 2) {
        $.ajax({
            type: 'GET',
            url: 'AskHR',
            data: "refno=" + refno,
            dataType: "json",
            success: function (data) {




                if (data.toString().includes("FAIL")) {
                    alert("Failure sending data\n" + data)
                } else {
                    var activitydata = '';

                    $.each(data, function (key, value) {

                        var text = value.message;
                        var meta = value.meta;



                        //activitydata += '<tr>';
                        //activitydata += '<td>' + value.parameter + '</td>';
                        //activitydata += '<td>' + value.answer + '</td>';
                        //activitydata += '<td></td>';
                        //activitydata += '<td></td>';


                        var msg2append = "";

                        if (meta.toString().includes(window.loggedinuser)) {

                            msg2append = '<div class="direct-chat-msg right"><div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-right"></span> <span class="direct-chat-timestamp pull-left">' + meta + '</span> </div><div class="direct-chat-text">' + text + '</div><!-- /.direct-chat-text --></div>'

                        } else {
                            msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + meta + '</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'
                        }
                        activitydata += msg2append;


                    });
                    $('#parentchatbox').empty().append(activitydata);
                }

            }, error: function (xhr, err) {

                alert(xhr)
            }
        });

    }
}

function makeid(length) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}





function getMyIP() {
    var ipinfo = "";
    $.getJSON('http://www.geoplugin.net/json.gp?jsoncallback=?', function (data) {
        console.log(JSON.stringify(data, null, 2));
        ipinfo = JSON.stringify(data, null, 2)
    });
    return ipinfo;
}