/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var editingVals = "1";

var uname = "";
var pw = "";


$(window).on('load', function () {
    // your logic here`enter code here`
    //alert("Denied")
    $.ajax({
        type: 'POST',
        url: 'AuthenticationServlet',
        dataType: "text",
        //data: "email=" + email.val() + "&phone=" + tel.val(),
        success: function (data) {

            // $('#confirmID').modal('hide');
            if (data.toString() === "no") {

                $('#authenticateid').modal({
                    backdrop: 'static',
                    keyboard: false
                });
//                swal({
//                    icon: "info",
//                    keyboard: false,
//                    allowOutsideClick:false,
//                    title: "Authentication status. " + data
//                })

            }

        }
    });

});
//
$(document).ready(function () {
    window.setInterval(ajaxd, 10000);
    ajaxd();



    $('#authenticatebtn').click(function () {

        var input = $('#authcodid').val();
        $.ajax({
            type: 'GET',
            url: 'AuthenticationServlet',
            dataType: "text",
            data: "sessionid=" + input,
            success: function (data) {

                window.location.reload();
//                if (data.toString() === "no") {
//
//                    $('#authenticateid').modal({
//                        backdrop: 'static',
//                        keyboard: false
//                    });
////               
//
//                }

            }
        });
    });


    $('#resendid').click(function () {
        var input = $('#authcodid').val();
        $.ajax({
            type: 'GET',
            url: 'ResendCode',
            dataType: "text",
            // data: "sessionid=" + input,
            success: function (data) {


            }
        });
    });


    $('#confbtn').click(function () {
        $.ajax({
            type: 'GET',
            url: 'ResendCode',
            dataType: "text",
            // data: "sessionid=" + input,
            success: function (data) {


            }
        });
    });

    $('#navid').click();




    $('#confirmBtn1').click(function () {
        var tel = $('#phoneNumberid1');
        var email = $('#emailID1');

        if (!email || !tel) {
            email.css("border", "2px solid red");
            tel.css("border", "2px solid red");
        } else {
            $.ajax({
                type: 'POST',
                url: 'FirstTimeLogin',
                dataType: "text",
                data: "email=" + email.val() + "&phone=" + tel.val(),
                success: function (data) {

                    $('#confirmID').modal('hide');
                    swal({
                        icon: "success",
                        title: "Thank you\n Your details were updated."
                    })

                }
            });
        }
    });

    $('#messagingid').click(function () {
        //   window.location.href = "sms.html"

        var win = window.open('sms.html', '_blank');

    });
    $('#accesscodeid').click(function () {
        $('#accesscodeid').prop('disabled', true);
        var uname = $('#inputUserid').val();
        var pwd = $('#inputPassword').val();
        var logintype = $('#loginMethod').val();
        //logintype

        var request = 'username=' + uname + '&password=' + pwd + "&logintype=" + logintype;



        //  alert(logintype + ": " + uname);
        $.ajax({
            type: 'POST',
            url: 'LogginController',
            data: request,
            dataType: "text",
            success: function (data) {
                //  console.log(data.response);

                if (data == 1) {

                    $('#accessBtn').prop('disabled', false);
                    document.getElementById('accessBtn').className = "form-control btn btn-primary";


                    document.getElementById("secKeyid").focus();

                    window.uname = uname;
                    window.pw = pwd;

                } else {
                    alert(data);
                }
            },
            error: function (xhr, err) {
                //  errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });

    $('#acceptBtn').click(function () {
        //  alert('changed '+$("input[name='accessBtn']:checked").val());

        var check = $("input[name='accessBtn']:checked").val();
        if (check == "on") {
            $('#confirmBtn1').prop('disabled', false);
        } else {
            $('#confirmBtn1').prop('disabled', true);
        }

    });

    $('#accessBtn').click(function () {

        var securitykey = $('#secKeyid').val();
        //     alert(securitykey)
        var request = 'sessionkey=' + securitykey;
        $.ajax({
            type: 'POST',
            url: 'VerifyUser',
            data: request,
            dataType: "json",
            success: function (data) {
                //   console.log(data);


                var fullname = data.name;
                var rank = data.designation;
                var fslogin = data.fslogin;

                $('#rankid').text(rank);
                $('#nameid').text(fullname);
                $('#nameid2').text(fullname);

                $('#pwdID').modal('hide');
                //var fslogin = data.fslogin;
                console.log(fslogin);
                //  if (fslogin == "fs") {

                // } else {
                swal({
                    title: "Welcome " + fullname,
                    icon: 'success'
                });
                //}


                //  $('#nameid').text('');
                //  $('#nameid2').text('');

                $('#inputUserid').val('');
                $('#inputPassword').val('');



            },
            error: function (xhr, err) {
                //errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                //   alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                swal({
                    icon: "warning",
                    title: "The code entered does not match"
                });
            }
        });


    });

    $('#oasid').click(function () {

        window.location.href = "mydocuments.html"

    });


    //appraisalid
    //
    $('#appraisalid').click(function () {

        window.location.href = "appraisal.html"

    });
    //fmsid
    $('#fmsid').click(function () {
//        var request = "username=id_" + window.uname + "&password=" + window.pw;
//        if (window.uname.includes("7915")) {
//            request = "username=admin&password=" + window.pw;
//        }

        window.location.href = "Fleet.jsp";



//        $.ajax({
//            type: 'GET',
//            url: 'HostIP',
//            //data: request,
//            dataType: "text",
//
//            success: function (data) {
//                var ip = data;
//                //Create a connection to the FMS container
//            //    console.log(request);
////                $.ajax({
////                    type: 'POST',
////                    url: "http://" + ip + ":8280/fleet/LoginController",
////                    data: request,
////                    dataType: "text",
////
////                    success: function (data) {
////                        var ip = data;
////
////                        window.open("http://" + ip + ":8280/fleet/dashboard.jsp");
////                    },
////
////                    error: function (xhr, err) {
////                        alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
////                    }
////                });
//
//                window.open("http://" + ip + ":8280/fleet/dashboard.jsp");
//            },
//
//            error: function (xhr, err) {
//                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
//            }
//        });


    });
    //tudefltid

    $('#tudefltid').click(function () {

        //  openBiomed();
        window.location.href = "AskHR.jsp"

    });



    $('#biomedid').click(function () {

        openBiomed();

    });


    $('#hrisid').click(function () {


        window.location.href = "HRPage.jsp"
//        $('#pwdID').modal({
//           backdrop:'static',
//           keyboard: false
//           
//       });
    });
    $('#frgtPwd').click(function () {
        console.log("Clicked")
        $('#changePasswordModal').modal({
            backdrop: 'static',
            keyboard: false
        });

    });

    $('#tempPwd').click(function () {
        var staffid = $('#nwpwdinput').val();
        swal({
            title: "Password change",
            text: "Sure to proceed with ID number " + staffid + " ?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: 'GET',
                    url: 'ChangePWD',
                    data: "staffid=" + staffid,
                    //dataType: "json",
                    success: function (data) {
                        if (data == "1") {
                            swal({
                                icon: "warning",
                                title: "There was an error reseting the password for " + staffid,
                                text: "Please confirm whether the entry was wrong and try again"
                            });
                        } else {
                            swal({
                                icon: "success",
                                title: "A temporary password has been sent to your mobile/corpotate email \nUse it to login to MTP, then change it after login"
                            });
                        }
                    }
                });
            } else {

            }
        });
    })
    $('#ihrisid').click(function () {
        var name = $('#nameid').text();
        console.log("Name is " + name);
        swal({
            title: "Redirecting you to iHRIS",
            text: "By clicking [OK] will logged out from MTP and redirected to iHRIS Analytics\nSure to continue " + name + " ?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $('#nameid').text('');
                $('#nameid2').text('');
                $.ajax({
                    type: 'GET',
                    url: 'LogginController',
                    //dataType: "json",
                    success: function () {


                        window.location.href = "http://41.89.183.14/mtrh/mtrh"
                    }
                });
            } else {
                swal("Okay, proceed!");
            }
        });

    });
    $('#footerid').text("MTRH");
    //footerid
    $('#policyID').click(function () {
        printpolicy();
    });
    $('#policyID2').click(function () {
        printpolicy();
    });
    $('#signoutid').click(function () {

        var name = $('#nameid').text();
        swal({
            title: "Exit options",
            text: "Sure to exit " + name + " ?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $('#nameid').text('');
                $('#nameid2').text('');
                $('#confbtn').text('');
                $.ajax({
                    type: 'GET',
                    url: 'LogginController',
                    //dataType: "json",
                    success: function () {


                        ajaxd();
                    }
                });
            } else {
                swal("Okay, proceed!");
            }
        });
    });


//
    $('#nwuserid').click(function () {
        $('#userAdmin').modal({
            backdrop: "static",
            keyboard: false
        });
    });

});
function ajaxd() {

    $.ajax({
        type: 'GET',
        url: 'ActiveSession',
        dataType: "text",
        success: function (data) {
            if (data === "active") {
                ajaxPopulate();

            } else {
                //window.location.reload(1);
                //    swal("uLELE");
                $('#pwdID').modal({
                    backdrop: 'static',
                    keyboard: false
                });
            }

        }
    });
}
function splitValue(value) {
    var str = value;
    var res = str.substring(str.length, 7);
    var res2 = str.substring(0, str.length - 4);
    //document.getElementById("demo").innerHTML = res2.replace(res2, "******")+""+res;
    var result = "";
    if (value.toString().incudes("@")) {
        result  = function (value) {
            return value.replace(/(.{2})(.*)(?=@)/,
                    function (gp1, gp2, gp3) {
                        for (let i = 0; i < gp3.length; i++) {
                            gp2 += "*";
                        }
                        return gp2;
                    });
        };

    } else {
        result = res2.replace(res2, "******") + "" + res;
    }
    return result;
}
function ajaxPopulate() {
    $.ajax({
        type: 'GET',
        url: 'VerifyUser',
        data: "staffid=",
        dataType: "json",
        success: function (data) {
            //  console.log(data);

            var fullname = data.name;
            var rank = data.designation;
            var phone = data.phone;
            var email = data.email;
            $('#rankid').text(rank);
            $('#nameid').text(fullname);
            $('#nameid2').text(fullname);

            console.log(splitValue(phone));

              $('#confbtn').text("OTP Code sent to " + splitValue(phone) + " or email " + splitValue(email) + ". Resend ?");
//            if (phone.length > 2) {
//                $('#confbtn').text("OTP Code sent to " + splitValue(phone) + " or email " + splitValue(email) + ". Resend ?");
//            } else {
//                $('#confbtn').text("Resend code to " + phone);
//            }

            var fslogin = data.fslogin;
            var tlogin = data.tlogin;
            console.log(fslogin);
            if (fslogin == "fs") {

                if (window.editingVals == "1") {
                    $('#phoneNumberid1').val(phone)
                    $('#nameofLoggerid').text(fullname);
                    $('#emailID1').val(email);
                }
                window.editingVals = "0";
                $('#confirmID').modal({
                    keyboard: false,
                    backdrop: 'static'
                });
            } else {


            }

            /*if (tlogin == "tl") {
             swal({
             icon: "warning",
             title: "We have realized that you're using temporary login credentials",
             text: "For security measures, please change your password to disable this reminder"
             })
             }*/

        },
        error: function (xhr, err) {
            //errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function printpolicy() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            var ip = data;
            window.open("http://" + ip + ":8280/mtportal/docs/EmailPolicy.pdf");
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
//tudefltid

function openBiomed() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            var ip = data;
            window.open("http://" + ip + ":8280/biomed/");
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}




