<%-- 
    Document   : Main Log In Page
    Created on : May 9, 2018, 6:34:30 PM
    Author     : owner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>MTRH FMS</title>
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/w3schools.css" />
        <link rel="shortcut icon" href="images/favicon.png" />

        <script src="js/sweetalert.js"></script>  
        <script src="js/sweetalert.min.js"></script> 







        <style>
          
            .center {
                display: block;
                margin-left: auto;
                margin-right: auto;
                width: 50%;
            }
            body{
                display:block;
                background-image: "images/HILUX.jpg";

            }
            .sidenav {
                height: 100%;
                width: 0;
                position: fixed;
                z-index: 1;
                top: 0;
                left: 0;
                background-color: whitesmoke;
                overflow-x: hidden;
                transition: 0.5s;
                padding-top: 60px;
            }

            .sidenav a {
                padding: 8px 8px 8px 32px;
                text-decoration: none;
                font-size: 25px;
                color: #818181;
                display: block;
                transition: 0.3s;
            }

            .sidenav a:hover {
                color: #f1f1f1;
            }

            .sidenav .closebtn {
                position: absolute;
                top: 0;
                right: 25px;
                font-size: 36px;
                margin-left: 50px;
            }

            @media screen and (max-height: 450px) {
                .sidenav {padding-top: 15px;}
                .sidenav a {font-size: 18px;}
            </style>
            <script>

            </script>
        </head>
        <body>

            <div id="errorpad" class="alert alert-danger" style="display:none" role="alert">Error</div>

            <div id="mySidenav" class="sidenav">
                <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>

                <form>
                    <h1>Please enter your email address to receive a verification code</h1>
                    <div class="form-group">
                        <input type="text" class="form-control p_input" placeholder="you@example.com" name="email" id ="mail" >
                    </div>
                    <div class="form-group">
                        <button onclick="closeNav()" class="w3-bar-item w3-large">#Verify</button>
                    </div>

                </form>



            </div>

            <div class="container-scroller">
                <div class="container-fluid">
                    <div class="row">
                        <div class="content-wrapper full-page-wrapper d-flex align-items-center auth-pages">
                            <div class="card col-lg-4 mx-auto">
                                <div class="card-body px-5 py-5">
                                    <img style="width:128px;height:128px; align-content: center" class="center" src ="images/mtrh_logo.gif"><br><br>
                                    <h3 class="card-title text-center mb-3 ">MTRH FLEET MANAGEMENT SYSTEM</h3>
                                    <p class="text-center">Login Window</p>

                                    <form id="regForm"  autocomplete="off">
                                        <!--   <form id="regForm" action="LoginController" method="post" autocomplete="off">-->

                                        <div class="form-group">
                                            <input type="text" class="form-control p_input" placeholder="Username" name="username" id ="uname" >
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control p_input" placeholder="Password" name="password" id="pwd">
                                        </div>
                                        <div class="form-group d-flex align-items-center justify-content-between">
                                            <!-- <div class="form-check"><label><input type="checkbox" class="form-check-input">Remember me</label></div>-->
                                            <a  id="rqtid" class="forgot-pass">Forgot password?</a>

                                        </div>
                                        <div class="text-center">
                                            <button type="button" id="loginBtn" class="btn btn-primary btn-block enter-btn">LOG IN</button>
                                        </div>

                                    </form>
                                    <br>

                                    <div id ="messageDiv">
                                        OR
                                        <br>
                                        <button onclick="openNav()" class="btn btn-success">Fill a #Transport Request Form</button>


                                    </div>
                                    <!-- <p class="Or-login-with my-3">Or login with</p>
                                     <a href="#" class="facebook-login btn btn-facebook btn-block">Sign in with Facebook</a>
                                     <a href="#" class="google-login btn btn-google btn-block">Sign in with Google+</a>
                                     <a href="#" class="google-login btn btn-create-account btn-block">Create An Account</a>-->

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="newUserModal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">

                            <h1 id ="RegNoHeader" class="modal-title">Secure Request Form</h1>&nbsp  &nbsp



                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">

                            <form id="userRequestForm" autocomplete="off">
                                <div class="form-group">
                                    <input type="text" id ="pffnoid" class="form-control p_input" placeholder="Enter your PF No Here and Press Enter ot Tab" onfocusout="pfFocosout()">
                                </div>

                                <div class="row">
                                    <div class="col-sm">
                                        <strong>Account: </strong> <input type="text" class="form-control" id="accountid"   autocomplete="off" disabled>
                                    </div>
                                    <div class="col-sm">
                                        <strong>Email: </strong>  <input type="text" class="form-control" id="emailid"   autocomplete="off" disabled>
                                    </div>
                                </div>
                                <hr>
                                Minimum password length requirement: 8 characters.

                                <hr>
                                <div class="row">
                                    <div class="col-sm">
                                        <strong>Password:</strong> <input type="password" class="form-control" id="pwdid"   autocomplete="off">
                                    </div>

                                </div>


                                <div class="row">
                                    <div class="col-sm">
                                        <strong>Repeat Password:</strong> <input type="password" class="form-control" id="pwdid2"   autocomplete="off" >
                                    </div>

                                </div>

                            </form>
                            <!-- <div id="errorUserPad" class="alert alert-danger" style="display:none" role="alert"></div>-->

                        </div>
                        <div class="modal-footer">

                            <button type="button" id="updfile" class="btn btn-outline-success"  disabled>Update Password</button>

                            <button type="button" id="close" data-dismiss="modal" class="btn btn-default" >Close</button>
                        </div>
                    </div>
                </div>
            </div>



            <script src="node_modules/jquery/dist/jquery.min.js"></script>
            <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
            <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
            <script src="node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
            <script src="js/misc.js"></script>
            <script src="js/jquery-validate.min.js"></script>


            <script>
                                            $(document).ready(function () {
                                                window.location.href="404.html"
                                                
                                                $('#rqtid').click(function () {
                                                    $('#updfile').prop("disabled", true);
                                                    $('#newUserModal').modal({
                                                        backdrop: 'static',
                                                        keyboard: false

                                                    });
                                                });


                                                $('#pwd').keyup(function (event) {

                                                    if (event.keyCode == 13) {
                                                        event.preventDefault();

                                                        $('#loginBtn').click();
                                                    }
                                                    //  alert(event.keyCode);

                                                });


                                                $('#pffnoid').keyup(function (event) {

                                                    if (event.keyCode == 13) {
                                                        event.preventDefault();
                                                        var refid = $(this).val();

                                                        UserDetails(refid);

                                                    }
                                                    //  alert(event.keyCode);

                                                });


                                                $('#pwdid2').keyup(function (event) {

                                                    if (event.keyCode == 13) {
                                                        event.preventDefault();

                                                        $('#updfile').click();
                                                    }
                                                });

                                                $('#updfile').click(function () {
                                                    var login = $('#accountid').val();
                                                    var email = $('#emailid').val();
                                                    var password = $('#pwdid').val();
                                                    var password1 = $('#pwdid2').val();
                                                    //var email =$('#emailid').val();
                                                    var transtype = "chpwd";//chpwd

                                                    if (password === null || password === undefined || password !== password1 || password.length < 8 || password1.length < 8) {
                                                        swal({
                                                            icon: "warning",
                                                            title: "Check your password entries please",
                                                            text: "Ensure that you meet the minimum requirements"
                                                        });
                                                    } else {


                                                        $('#pwdid').prop("disabled", true);
                                                        $('#pwdid2').prop("disabled", true);

                                                        var request = 'transtype=' + transtype + '&login=' + login + '&email=' + email + '&password=' + password;
                                                        console.log(request)
                                                        $.ajax({
                                                            type: 'POST',
                                                            data: request,
                                                            url: 'MasterFile',
                                                            dataType: "text",
                                                            success: function (response) {

                                                                swal({
                                                                    title: response,
                                                                    text: "RefID " + login,
                                                                    icon: "success",
                                                                });

                                                                $('#updfile').prop("disabled", true);

                                                            }, error: function (xhr, err) {
                                                                swal("System Error: " + xhr + " Error " + err);
                                                            }
                                                        });

                                                    }
                                                });

                                                $('#loginBtn').click(function () {
                                                    var uname = $('#uname').val();
                                                    var pwd = $('#pwd').val();

                                                    //alert(uname);

                                                    if (uname == null || pwd == null) {
                                                        errorDisplay("Username and Password Cannot be null");
                                                    } else {
                                                        var request = 'username=' + uname + '&password=' + pwd;
                                                        $.ajax({
                                                            type: 'POST',
                                                            url: 'LoginController',
                                                            data: request,
                                                            dataType: "text",
                                                            success: function (data) {
                                                                console.log(data.response);

                                                                if (data == 1) {
                                                                    //window.location.replace(data.redirecturl);
                                                                    window.location.href = "dashboard.jsp"

                                                                } else {
                                                                    errorDisplay(data);
                                                                }

                                                            },
                                                            error: function (xhr, err) {
                                                                errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                                                                //alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                                                            }
                                                        });
                                                    }
                                                });


                                            });


                                            function pfFocosout() {
                                                var refid = $('#pffnoid').val();

                                                UserDetails(refid);

                                            }
                                            function UserDetails(refid) {
                                                var request = 'userid=' + refid
                                                $.ajax({
                                                    type: 'POST',
                                                    data: request,
                                                    url: 'VerifyUser',
                                                    dataType: "json",
                                                    success: function (response) {

                                                        $('#updfile').prop("disabled", false);
                                                        var dataset = response;

                                                        var email = response.email;
                                                        var name = response.name;
                                                        var account = response.account;

                                                        $('#emailid').val(email);
                                                        $('#accountid').val(account);
                                                        // $('#emailid').val(email);


                                                    }, error: function (xhr, err) {
                                                        $('#updfile').prop("disabled", true);

                                                        swal({
                                                            icon: "warning",
                                                            title: "Sorry, PF No " + refid + " is not permitted to login",
                                                            text: ""
                                                        });
                                                        // swal("Sorry, you do not have access to the system.\nSystem Error: " + xhr + " Error " + err);
                                                        //window.location.href = "index.jsp"
                                                    }
                                                });

                                            }
            </script>

            <script>
                function openNav() {
                    window.location.href = "transportrequest.jsp"
                }
                function triggerAction() {
                    swal(event.which);
                }

                function errorDisplay(errormessage) {
                    $("#errorpad").text(errormessage);
                    $("#errorpad").css("display", "block");

                    window.setTimeout(function () {
                        $("#errorpad").fadeTo(500, 0).slideUp(500, function () {
                            $(this).remove();
                        });
                        window.location.href = "/fleet/";
                    }, 4000);
                }

                function errorDisplay2(errormessage) {
                    $("#errorUserPad").text(errormessage);
                    $("#errorUserPad").css("display", "block");

                    window.setTimeout(function () {
                        $("#errorUserPad").fadeTo(500, 0).slideUp(500, function () {
                            //   event.preventDefault();
                            //       $("#errorUserPad").html(errormessage).fadeOut(4000, function(){
                            $(this).remove();

                        });
                    }, 1000);


                }



            </script>

            <script>
                function myAlertTop() {
                    $(".myAlert-top").show();
                    setTimeout(function () {
                        $(".myAlert-top").hide();
                    }, 2000);
                }

                function myAlertBottom(errormessage) {
                    $(".myAlert-bottom").text(errormessage).show();
                    setTimeout(function () {
                        $(".myAlert-bottom").hide();
                    }, 2000);
                }
            </script>

            <!--  <script src="js/custom.js"></script>-->



        </body>
    </html>
