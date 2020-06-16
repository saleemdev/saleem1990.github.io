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
    allChats();
    bioDataAskHR();
    justMyChats();

    window.setInterval(allChats, 10000);
    window.setInterval(fetchMsgs, 10000);
    $('#opentasksid').click(function () {
        $('#mytasksbtn').click();
    });


    $('#closeissueid').click(function () {
        var selectedExpert = $('#staffname').val();
        var issueID = window.msgrefno;

        if (issueID.length < 0) {
            var modalHeader = $('#resheader').text();

            var arr = modalHeader.toString().split(" ")

            issueID = arr[3];
        }

        var userdet = window.askhrveruser;
        var arr = userdet.split("/");
        var trans = "R";
        var usr = arr[0];
        var timestamp = new Date();
        var pfinq = arr[1];

        var typed = document.getElementById("resolutionid").value;//$('#resolutionid').val();
        
        console.log(typed+" "+typed.length);

        if (typed.length > 20) {

            var text = typed + "[" + issueID + "]";


            swal({
                icon: "warning",
                title: "Please confirm this info before clicking [OK]",
                text: "Issue ID " + issueID + "\nTransaction type: Mark Issue as Resolved",
                buttons: true,
                dangerMode: true,
            })
                    .then((willDelete) => {
                        if (willDelete) {
                            updateWorkorder(text, issueID, "TICKET RESOLUTION")  //(activity, refid, issue) 
                            //
                            sendCustomMsg(issueID, text, usr, timestamp, pfinq, "R");
                            // saveEntitleMent(request, pf, "");
                        } else {
                            swal("Transaction aborted!");
                        }
                    });

        } else {
            swal({
                icon: "error",
                title: "Sorry, you must type a substantial resolution report."
            })
        }
    })



    $('#sendticketbtn').click(function () {
        var selectedExpert = $('#staffname').val();
        var issueID = window.msgrefno;

        if (issueID.length < 0) {
            var modalHeader = $('#resheader').text();

            var arr = modalHeader.toString().split(" ")

            issueID = arr[3];
        }

        var userdet = window.askhrveruser;
        var arr = userdet.split("/");
        var trans = "R";
        var usr = arr[0];
        var timestamp = new Date();
        var pfinq = arr[1];

        var text = "Allocated ticket to " + selectedExpert;


        swal({
            icon: "warning",
            title: "Please confirm this info before clicking [OK]",
            text: "Issue ID " + issueID + "\nExpert Selected " + selectedExpert,
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {
                        updateWorkorder(text, issueID, "Ticket Allocation")  //(activity, refid, issue) 
                        //
                        sendCustomMsg(issueID, text, usr, timestamp, pfinq, "R");
                        
                        
                        // saveEntitleMent(request, pf, "");
                    } else {
                        swal("Transaction aborted!");
                    }
                });


    })


    $('#rstickbtn').click(function () {

        myColleagues3();

        var issueID = window.msgrefno;

        if (issueID.length < 0) {
            var modalHeader = $('#resheader').text();

            var arr = modalHeader.toString().split(" ")

            issueID = arr[3];
        }

        console.log("IssueID " + issueID)

        $('#expertselect').text("Issue Resolution for " + issueID);

        $('#closeresolution').click();
        $('#raiseticketmodal').modal({
            keyboard: false,

        })
    });

    $('#staffname').select2({

    })

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
});
function bioDataAskHR() {
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
            var section = data.section;
            var email = data.email;
            var phone = data.phone;
            $('#verid').val(staffid);
            // askHRVer(staffid);
            $('#verbtnid').click();
            $('#currDeptid').text(section);
            window.myDept = department;
            // myColleagues3(department);
            //emailid
        },
        error: function (xhr, err) {

            console.log(xhr + "\n" + err)
//            const toast = Swal.mixin({
//               toast: true, 
//               position: 'top-end',
//            })
//
//            swal({
//                toast: true,
//                icon: "warning",
//                position:'top-end',
//                timer: 1000,
//                title: "Error loading data"
//            });
        }
    });
}


function allChats() {
    console.log("Getting chats")
    //   var lvyr = $('#lvyrid').text();
    // console.log(lvyr)
    //swal("I am here")
    $.ajax({
        type: 'GET',
        url: 'AskHRB',
        //  data: "leaveyear=" + lvyr,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {
                activitydata += '<tr>';
                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td>' + value.uname + '</td>';
                activitydata += '<td>' + value.tstamp + '</td>';
                activitydata += '<td>' + value.read + '</td>';
                activitydata += '<td>' + value.stat + '</td>';
                

                activitydata += '<td ><button  class="btn btn-primary btn-sm" onclick="openchat(this)">Open Chat</button></td>'

                activitydata += '<td ><button  class="btn btn-success btn-sm" onclick="opendialog(this)">Resolve Issue</button></td>'
                
                activitydata += '<td ><button  class="btn btn-warning btn-sm" onclick="printchattranscript(this)">Print Chat Transcript</button></td>'




                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#myChatsTblbd').empty().append(activitydata);
        }
    });
}

function justMyChats() {
    console.log("Getting chats")
    //   var lvyr = $('#lvyrid').text();
    // console.log(lvyr)
    //swal("I am here")
    $.ajax({
        type: 'POST',
        url: 'AskHRB',
        //  data: "leaveyear=" + lvyr,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                //    {"refno", "uname", "tstamp","read","stat"};

                activitydata += '<tr>';
                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td><a href="#"><div class="contacts-list-info"> <span class="contacts-list-name"> ' + value.uname + ' <small class="contacts-list-date pull-right">' + value.tstamp + '</small></span> <span class="contacts-list-msg"></span></td>';
                /*  activitydata += '<td>' + value.uname + '</td>';
                 activitydata += '<td>' + value.tstamp + '</td>';
                 activitydata += '<td>' + value.read + '</td>';
                 activitydata += '<td>' + value.stat + '</td>';
                 // activitydata += '<td>-</td>';
                 //activitydata += '<td>' + value.rqon + '</td>';
                 */

                activitydata += '<td ><button data-toggle="tooltip" data-widget="chat-pane-toggle" class="btn btn-primary btn-sm" onclick="openmychat(this)">Open Chat</button></td>'

                //    activitydata += '<td ><button  class="btn btn-success btn-sm" onclick="opendialog(this)">Resolve Issue</button></td>'




                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#myprivatechatsbd').empty().append(activitydata);
        }
    });
}


function printchattranscript(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    tbl = document.getElementById("myChatsTbl");
    console.log("Default..")

    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
//    swal(cellvalue);
    //  print(cellvalue)

    window.msgrefno = cellvalue;
    $('#chatidTxt').text("Chat ID:" + cellvalue)

    fetchMsgs();
    openForm();
    
   printpdf("ETICKET", cellvalue)
}

function printpdf(doctype,refno){
     $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",
            success: function (data) {
                var ip = data;
               // var doctype = "ETICKET";
                var trans = "pdfname=" + doctype + "&refno="+refno;
                window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
            },
            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
}






function openchat(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    tbl = document.getElementById("myChatsTbl");
    console.log("Default..")

    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
//    swal(cellvalue);
    //  print(cellvalue)

    window.msgrefno = cellvalue;
    $('#chatidTxt').text("Chat ID:" + cellvalue)

    fetchMsgs();
    openForm();
}



function openmychat(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    tbl = document.getElementById("myprivatechats");
    console.log("Default..")

    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
//    swal(cellvalue);
    //  print(cellvalue)

    window.msgrefno = cellvalue;
    $('#chatidTxt').text("Chat ID:" + cellvalue)

    fetchMsgs();
    //  openForm();
}





function opendialog(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    tbl = document.getElementById("myChatsTbl");
    console.log("Default..")

    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
//    swal(cellvalue);
    //  print(cellvalue)

    window.msgrefno = cellvalue;
    $('#chatidTxt').text("Chat ID:" + cellvalue)

    fetchMsgs();
    openResolution(cellvalue)
    //openForm();

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

                    justMyChats();
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

            console.log(xhr + "\n" + err)
//            swal({
//                icon: "warning",
//                title: "Error loading data"
//            });
        }
    });
}

function fetchMsgs() {
    var refno = window.msgrefno;
    $('#chatidTxt').text("Chat ID:" + refno)

    $('#askhrmessageboxid').focus();
    $('#parentchatbox').empty();
    console.log("Getting messages for...[" + refno + "]")
    if (msgrefno.length > 2) {
        $('#expertSelectid').text("Issue resolution for " + refno)
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
                    $('#parentchatbox2').empty().append(activitydata);
                }

            }, error: function (xhr, err) {

                alert(xhr)
            }
        });
    }
}



function newConvo() {
    window.msgrefno = "";
    fetchMsgs();
}




function openResolution(refno) {
    $('#resheader').text("Issue Resolution for " + refno)

    $('#expertselect').text("Issue Resolution for " + refno)

    $('#resolvmodal').modal({
//  backdrop: 'static',
        keyboard: false
    })
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


function updateWorkorder(activity, refid, issue) {
    var request = "activity=" + activity + "&refid=" + refid + "&issue=" + issue;

    //   alert(request)
    $.ajax({
        type: 'GET',
        url: 'WorkOrders',
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

function myColleagues3() {

    // var request = "department=" + department;
    $.ajax({
        type: 'POST',
        url: 'AskHRC',
        //    data: request,
        dataType: "json",
        success: function (data) {
            $('#staffname').empty();
            $('#staffname').append('<option>-Select-</option>');
            console.log("I am here")
            $.each(data, function (key, value) {

                var name = value.name;
                $('#staffname').append('<option value="' + name + '">' + name + '</option>');
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



function sendCustomMsg(refno, text, usr, timestamp, pfinq, trans) {

    //  var msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + usr + ' @ ' + timestamp + '</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'
    $.ajax({
        type: 'POST',
        url: 'AskHR',
        data: "refno=" + refno + "&message=" + text + "&username=" + usr + "@" + timestamp + "&pfno=" + pfinq + "&transtype=" + trans,
        dataType: "text",
        success: function (data) {


            if (data.toString().includes("FAIL")) {
                alert("Failure sending data\n" + data)
            } else {
                fetchMsgs();
                //  $('#parentchatbox').append(msg2append);

//                    justMyChats();
            }

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}