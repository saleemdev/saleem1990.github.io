<%-- 
    Document   : index
    Created on : Aug 31, 2018, 5:10:17 PM
    Author     : Salim
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>MTRH  |Staff Portal Version 2-Beta</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
              page. However, you can choose any other skin. Make sure you
              apply the skin class to the body tag so the changes take effect. -->
        <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Google Font -->

        <script src="dist/js/sweetalerts.min.js"></script> 
        <!--  <script src="js/SweetAlert2.js"></script>-->
        <link rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">


        <link rel="stylesheet" href="dist/css/select2.min.css">
        <link rel="stylesheet" href="dist/css/custom.css">


        <style>
            .center-mod{

                display: block;
                margin-left: auto;
                margin-right: auto;
                width: 50%;

            }

            #policyID,#policyID2:hover {
                cursor:pointer;
            }
        </style>

        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            * {box-sizing: border-box;}

            /* Button used to open the chat form - fixed at the bottom of the page */
            .open-button {
                background-color: #555;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                opacity: 0.8;
                position: fixed;
                bottom: 23px;
                right: 28px;
                width: 280px;

            }

            /* The popup chat - hidden by default */
            .chat-popup {
                display: none;
                position: fixed;
                bottom: 0;
                right: 15px;
                border: 3px solid #f1f1f1;
                z-index: 9;
                width:500px;

            }
            #chatheader{

                padding: 60px;
                text-align: center;
                background: #1abc9c;
                color: white;
                font-size: 30px;

            }

            /* Add styles to the form container */
            .form-container {
                max-width: 300px;
                padding: 10px;
                background-color: white;
            }

            /* Full-width textarea */
            .form-container textarea {
                width: 100%;
                padding: 15px;
                margin: 5px 0 22px 0;
                border: none;
                background: #f1f1f1;
                resize: none;
                min-height: 200px;
            }

            /* When the textarea gets focus, do something */
            .form-container textarea:focus {
                background-color: #ddd;
                outline: none;
            }

            /* Set a style for the submit/send button */
            .form-container .btn {
                background-color: #4CAF50;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-bottom:10px;
                opacity: 0.8;
            }

            /* Add a red background color to the cancel button */
            .form-container .cancel {
                background-color: red;
            }

            /* Add some hover effects to buttons */
            .form-container .btn:hover, .open-button:hover {
                opacity: 1;
            }
        </style>


    </head>
    <!--
    BODY TAG OPTIONS:
    =================
    Apply one or more of the following classes to get the
    desired effect
    |---------------------------------------------------------|
    | SKINS         | skin-blue                               |
    |               | skin-black                              |
    |               | skin-purple                             |
    |               | skin-yellow                             |
    |               | skin-red                                |
    |               | skin-green                              |
    |---------------------------------------------------------|
    |LAYOUT OPTIONS | fixed                                   |
    |               | layout-boxed                            |
    |               | layout-top-nav                          |
    |               | sidebar-collapse                        |
    |               | sidebar-mini                            |
    |---------------------------------------------------------|
    -->
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <!-- Main Header -->
            <header class="main-header">

                <!-- Logo -->
                <a href="/mtportal/" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini"><b>MT</b>P</span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg"><b>MTRH</b>PORTAL</span>
                </a>

                <!-- Header Navbar -->
                <nav class="navbar navbar-static-top" role="navigation">
                    <!-- Sidebar toggle button-->
                    <a id="navid"  class="sidebar-toggle" data-toggle="push-menu" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>
                    <!-- Navbar Right Menu -->
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- Messages: style can be found in dropdown.less-->
                            <li class="dropdown messages-menu">
                                <!-- Menu toggle button -->
                                <a  class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-envelope-o"></i>
                                    <span class="label label-success"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 4 messages</li>
                                    <li>
                                        <!-- inner menu: contains the messages -->
                                        <ul class="menu">
                                            <li><!-- start message -->
                                                <a >
                                                    <div class="pull-left">
                                                        <!-- User Image -->
                                                        <img src="dist/img/logoutbtn.png" class="img-circle" alt="User Image">
                                                    </div>
                                                    <!-- Message title and timestamp -->
                                                    <h4>
                                                        Support Team
                                                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                                    </h4>
                                                    <!-- The message -->
                                                    <p>Why not buy a new awesome theme?</p>
                                                </a>
                                            </li>
                                            <!-- end message -->
                                        </ul>
                                        <!-- /.menu -->
                                    </li>
                                    <li class="footer"><a >See All Messages</a></li>
                                </ul>
                            </li>
                            <!-- /.messages-menu -->

                            <!-- Notifications Menu -->
                            <li class="dropdown notifications-menu">
                                <!-- Menu toggle button -->
                                <a  class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-bell-o"></i>
                                    <span class="label label-warning"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 10 notifications</li>
                                    <li>
                                        <!-- Inner Menu: contains the notifications -->
                                        <ul class="menu">
                                            <li><!-- start notification -->
                                                <a >
                                                    <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                                </a>
                                            </li>
                                            <!-- end notification -->
                                        </ul>
                                    </li>
                                    <li class="footer"><a >View all</a></li>
                                </ul>
                            </li>
                            <!-- Tasks Menu -->
                            <li class="dropdown tasks-menu">
                                <!-- Menu Toggle Button -->
                                <a  class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-flag-o"></i>
                                    <span class="label label-danger"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 9 tasks</li>
                                    <li>
                                        <!-- Inner menu: contains the tasks -->
                                        <ul class="menu">
                                            <li><!-- Task item -->
                                                <a >
                                                    <!-- Task title and progress text -->
                                                    <h3>
                                                        Design some buttons
                                                        <small class="pull-right">20%</small>
                                                    </h3>
                                                    <!-- The progress bar -->
                                                    <div class="progress xs">
                                                        <!-- Change the css width attribute to simulate progress -->
                                                        <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar"
                                                             aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                            <span class="sr-only">20% Complete</span>
                                                        </div>
                                                    </div>
                                                </a>
                                            </li>
                                            <!-- end task item -->
                                        </ul>
                                    </li>
                                    <li class="footer">
                                        <a >View all tasks</a>
                                    </li>
                                </ul>
                            </li>
                            <!-- User Account Menu -->
                            <li class="dropdown user user-menu">
                                <!-- Menu Toggle Button -->
                                <a id="userProf"  class="dropdown-toggle" data-toggle="dropdown">
                                    <!-- The user image in the navbar-->
                                    <img src="dist/img/logoutbtn.png" class="user-image" alt="User Image">
                                    <!-- hidden-xs hides the username on small devices so only the image appears. -->
                                    <span id="nameid" class="hidden-xs">Anon</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- The user image in the menu -->
                                    <li class="user-header">
                                        <img src="dist/img/logoutbtn.png" class="img-circle" alt="User Image">

                                        <p id="rankid">

                                        </p>
                                    </li>
                                    <!-- Menu Body -->
                                    <li class="user-body">
                                        <div class="row">
                                            <span ></span>
                                        </div>
                                        <!-- /.row -->
                                    </li>
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <a  class="btn btn-default btn-flat">Profile</a>
                                        </div>
                                        <div class="pull-right">
                                            <a id="signoutid" class="btn btn-default btn-flat">Sign out</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <!-- Control Sidebar Toggle Button -->
                            <li>
                                <a id="adminid" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">

                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">

                    <!-- Sidebar user panel (optional) -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="dist/img/logoutbtn.png" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p id="nameid2">Anon</p>
                            <!-- Status -->
                            <a ><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>

                    <!-- search form (Optional) -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->

                    <!-- Sidebar Menu -->
                    <ul class="sidebar-menu" data-widget="tree">

                    </ul>
                    <!-- /.sidebar-menu -->
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Moi Teaching and Referral Hospital | Staff PORTAL
                        <small >User Dashboard</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a ><i class="fa fa-dashboard"></i> Level</a></li>
                        <li class="active">Here</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content container-fluid">

                    <!--------------------------
                    | Your Page Content Here |
                    -------------------------->
                    <div id="row">                                         
                        <!-- ./col -->
                        <div id="hrisid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-yellow">
                                <div class="inner">
                                    <h3>e-Leave</h3>

                                    <p>HR & Capacity Building</p>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-android-person"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                        <!-- ./col -->
                        <!-- ./col -->
                        <div id="ihrisid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-lime">
                                <div class="inner">
                                    <h3>i-HRIS KE</h3>

                                    <p>HR & Capacity Building</p>
                                </div>
                                <div class="icon">
                                    <!-- <img src="/mtportal/images/ihris-logo.png" alt="iHRIS Logo">-->
                                    <i class="ion ion-android-archive"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                        <!-- ./col -->

                        <!-- ./col -->
                        <div id="messagingid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-green-gradient">
                                <div class="inner">
                                    <h3>e-Records</h3>

                                    <p>Registry & Records Mgt</p>
                                </div>
                                <div class="icon">
                                    <!-- <img src="/mtportal/images/ihris-logo.png" alt="iHRIS Logo">-->
                                    <i class="ion ion-email"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                        <!-- ./col -->
                        <div id="oasid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>My Documents</h3>

                                    <p>My corporate documents</p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-download"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div id="row">
                        <div id="tudefltid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-olive-active">
                                <div class="inner">
                                    <h3></h3>

                                    <h3>Ask HR</h3>

                                    <p>Ask HR Backend</p>

                                    <br>
                                    <p></p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-envelope-square"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>



                        <!-- ./col -->
                        <div id="appraisalid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>Staff Bio/<br>Appraisal</h3>

                                    <p>Resourcing</p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-calendar-check-o"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>


                        <div id="fmsid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-teal-active">
                                <div class="inner">
                                    <h3>Fleet Mgt.<br>System</h3>

                                    <p>FMS</p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-cab"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>

                        <div id="amgtid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-teal-active">
                                <div class="inner">
                                    <h4 style="font-size: 18pt">Ticketing<br>& Equipment<br>Maintenance</h4>

                                    <p>Technical support</p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-adn"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                    </div>

                    <div id="row">
                        <div id="biomedid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-olive-active">
                                <div class="inner">
                                    <h3><img src="images/tudelf2.PNG" style="height: 50%; width: 50%"></h3>

                                    <h3>Biomed Portal</h3>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-balance-scale"></i>
                                </div>
                                <a  class="small-box-footer">
                                    Link <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>

                    </div>





                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <!-- Main Footer -->
            <footer class="main-footer">
                <!-- To the right -->
                <div class="pull-right hidden-xs">
                    Excellence in Health care, Training and Research
                </div>
                <!-- Default to the left -->
                <strong >Copyright &copy; 2019 <a id="footerid">MTRH</a>.</strong> All rights reserved.
            </footer>


            <!-- /.control-sidebar -->
            <!-- Add the sidebar's background. This div must be placed
            immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper -->



        <div class="modal modal-danger fade" id="authenticateid">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">


                        <h4 class="modal-title">OTP Authentication</h4>
                    </div>
                    <div class="modal-body">
                        <h4>Sorry, your session is not authenticated</h4>

                        <p>Please enter the code sent to your SMS in the field below and click [Authenticate];</p>
                        <hr>
                        <input type="text" maxlength="5" class="form-control" id="authcodid" placeholder="5-Digit Authentication" autocomplete="off">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="resendid" class="btn btn-link">Resend OTP Message</button>
                        <button type="button" id="authenticatebtn" class="btn btn-outline">Authenticate</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->

        <button class="open-button" onclick="openForm()">Chat</button>

        <div class="box box-info direct-chat direct-chat-danger chat-popup"  id="myForm">
            <div class="box-header with-border" id="chatheader">
                <br>

                <div class="box-tools pull-right">

                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus" style="color:white;"></i></button>                                    
                    <button  class="btn btn-box-tool" data-toggle="tooltip" title="Contacts" data-widget="chat-pane-toggle"><i class="fa fa-comments" style="color:white;" ></i></button>
                    <button class="btn btn-box-tool" id="removewg"><i class="fa fa-times" style="color:white;"></i></button>
                </div>

                <h5 id="chatidTxt" class="box-title">Chat ID</h5>
                <input id="verid" type="text"  placeholder="Type your PF number here and click verify..." class="form-control"><button id="verbtnid" class="btn btn-small btn-success">Verify PF</button>





            </div>
            <!-- /.box-header -->

            <div class="box-body">
                <!-- Conversations are loaded here -->
                <div class="direct-chat-messages" id="parentchatbox">

                </div>

                <div class="direct-chat-contacts">

                    <div class="box-body">
                        <div class="table-responsive">
                            <table id="myprivatechats" class="table no-margin">
                                <thead>

                                </thead>
                                <tbody id="myprivatechatsbd">

                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>







                    <!-- /.contatcts-list -->

                    <!-- <ul class="contacts-list">
                         <li disabled="true">
                             <a href="#">
 
 
 
                                 <div class="contacts-list-info">
                                     <span class="contacts-list-name">
                                         Msg
                                         <small class="contacts-list-date pull-right">D/Mm/yyyy</small>
                                     </span>
                                     <span class="contacts-list-msg">The Msg.</span>
                                 </div>
                                 
                             </a>
                         </li>
                        
                     </ul>-->




                </div>
                <!-- /.direct-chat-pane -->
            </div>
            <!-- /.box-body -->
            <div class="box-footer">
                <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-sm btn-link" onclick="newConvo()"> Start a new conversation</button>
                        <button data-toggle="tooltip" data-widget="chat-pane-toggle" class="btn btn-sm btn-link" > Open my older conversations</button><br></span>
                </div>
                <div class="input-group">
                    <input id="askhrmessageboxid" type="text" onkeypress="enter2sendMsg()" name="message" placeholder="Ask anything here..." class="form-control">
                    <span class="input-group-btn">
                        <button id="askhrsendmsgbtn" type="button" class="btn btn-danger btn-flat" disabled="true">Send</button>
                    </span>
                </div>
            </div>
            <!-- /.box-footer-->
        </div>


        <div class="modal fade" id="pwdID">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!-- <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                             <span aria-hidden="true">&times;</span></button>-->
                        <span class="fa fa-key"><h4 class="modal-title">Login Window</h4></span>

                    </div>
                    <div class="modal-body">
                        <form id="Login">


                            <div class="form-group">
                                <label>Login Method</label>

                                <select id="loginMethod" class="form-control select2" style="width: 100%;">
                                    <option value="Staff ID" selected="selected">Staff ID</option>
                                    <option value="Staff E-Mail">Staff E-mail</option>

                                </select>
                            </div>



                            <div class="form-group">


                                <input type="text" class="form-control" id="inputUserid" placeholder="Login ID" autocomplete="off">

                            </div>

                            <div class="form-group">

                                <input type="password" class="form-control" id="inputPassword" placeholder="Password">

                            </div>

                            <div class="row">                                                                
                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="secKeyid" placeholder="Security Key" autocomplete="off"> 
                                </div>

                                <div class="col-md-6">
                                    <button id="accesscodeid" type="button" class="btn btn-success">Get Code</button>         
                                </div>
                            </div>


                            <div class="row">                                                                                                
                                <div class="col-md-6">
                                    <button id="confbtn" type="button" class="btn btn-link"></button>         
                                </div>
                            </div>
                            <br>
                            <!-- <div class="forgot">
                                 <a href="reset.html">Forgot password?</a>
                             </div>-->

                            <button type="button" id="accessBtn" class="form-control btn btn-file" disabled>Login</button>

                            <div class="row">
                                <div class="col-md-6">
                                    <a id="frgtPwd">Forgot Password?</a>
                                </div>

                                <div class="col-md-6">
                                    <a id="ndhlp" onclick="openForm()">Need Help?</a>
                                </div>
                            </div>



                        </form>
                    </div>
                    <div class="modal-footer">
                        &copy; Property of Moi Teaching and Referral Hospital
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <div class="modal modal-info fade" id="changePasswordModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Change Password</h4>
                    </div>
                    <div class="modal-body">
                        <p>Please enter your PF Number and click <strong>Reset</strong> to reset your password</p>
                        <input type="text" id="nwpwdinput" class="form-control" placeholder="Enter your personal file number here"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                    <button type="button" id="tempPwd" class="btn btn-outline">Reset</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <!-- /.modal -->

    <div class="modal fade" id="confirmID">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" >
                    <img  src="/mtportal/images/mtrh_logo.gif" class="center-mod">
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <h4 style="line-height: 1.5;"> Hello  <strong id="nameofLoggerid"><br></strong><p>As a business continuity measure, at times the OTP (One time Pin) will be sent to your <b>corporate e-mail</b> in accordance to the<a id="policyID">institution's digital communication usage policy</a>.
                            </p><p> Please confirm whether your contact information details are <font size="3" color="red">ACCURATE and UP-TO-DATE</font></p></h4>
                        <a id="policyID2">Click me to read the Institution's digital communication usage policy</a>
                        <hr>
                        Mobile:  <input type="number"  class="form-control"  style='font-size: 24px;' id="phoneNumberid1" placeholder="Phone number, starts with 07..." autocomplete="off">
                        Email: <input type="email" class="form-control" style='font-size: 24px;' id="emailID1" placeholder="E-Mail Address" autocomplete="off">
                        <br>

                        <br>

                        NB: WE will resend the OTP code to your updated credentials.
                    </div>
                </div>
                <div class="modal-footer">
                    <label>
                        <input id="acceptBtn" name="accessBtn" type="checkbox" class="flat-red">
                        Check to accept our Communications Policy             
                    </label>
                    <button id="confirmBtn1" type="button" class="btn btn-primary" disabled>Confirm Details</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <div class="modal fade" id="userAdmin" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">

                    Administration Panel
                    <button id="closeAdmin" type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div id ="adminBody" class="modal-body">

                    <a>Sign In & Security</a>



                    <hr>


                    <form id="userRequestForm" autocomplete="off">
                        <div class="form-group">
                            <input type="text" id ="pffnoid" class="form-control p_input" placeholder="Enter your PF No Here and Press Enter" name="idno">
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <strong>Name</strong> <input type="text" class="form-control" id="nameid"   autocomplete="off" disabled>
                            </div>
                            <div class="col-md-6">
                                <strong>Tel No </strong>  <input type="text" class="form-control" id="telid"   autocomplete="off" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <strong>E-Mail</strong> <input type="text" class="form-control" id="emailid"   autocomplete="off" required>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <strong>Department</strong> <input type="text" class="form-control" id="deptid"   autocomplete="off" required>
                            </div>
                            <div class="col-md-6">
                                <strong>Section </strong>  <input type="text" class="form-control" id="secid"   autocomplete="off" required>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-6">
                                <strong>User Level Access:</strong> <!--<input type="text" class="form-control" id="desigid"   autocomplete="off" required>-->
                                <select class="form-control" id="ulacid" name="f102">
                                    <option  value="">- Select -</option> 
                                    <option > GENERAL USER </option> 
                                    <option > DRIVER </option> 
                                    <option > TRANSPORT PERSONNEL </option> 
                                    <option > ICT ADMINISTRATOR </option> 
                                    <option > TRANSPORT MANAGER </option> 
                                    <option > SENIOR MANAGER </option>
                                    <option > CHIEF EXECUTIVE OFFICER </option> 
                                </select>                                                                                
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <strong>LoginID</strong>  <input type="text" class="form-control" id="secloginid"   autocomplete="off" required>
                            </div>

                        </div>


                    </form>
                </div>
            </div>

            <div class="modal-footer">
                <span>  

                    <button id="aprvAdmin" class="btn btn-success fa-save">Save</button>                            
                    <button id="clloseAdmin" class="btn btn-primary" data-dismiss="modal">Close</button>
                </span>
            </div>

        </div>

    </div>



    <!-- REQUIRED JS SCRIPTS -->

    <!-- jQuery 3 -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap 3.3.7 -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/adminlte.min.js"></script>
    <!-- Select2 -->
    <script src="dist/js/select2.full.min.js"></script>

    <script src="js/CustomDash.js"></script>

    <script src="js/CustomDash2.js"></script>

    <script  src="js/AskHR.js"></script>


    <script>
                                        function openForm() {
                                            document.getElementById("myForm").style.display = "block";
                                        }

                                        function closeForm() {
                                            document.getElementById("myForm").style.display = "none";
                                        }
    </script>



    <!--Start of Tawk.to Script-->
    <!--   <script type="text/javascript">
           var Tawk_API = Tawk_API || {}, Tawk_LoadStart = new Date();
           (function () {
               var s1 = document.createElement("script"), s0 = document.getElementsByTagName("script")[0];
               s1.async = true;
               // s1.src = 'https://embed.tawk.to/5bba7930b8198a0410489ea2/default';
               s1.src = 'https://embed.tawk.to/5c90ea2d101df77a8be35ebd/default';
               s1.charset = 'UTF-8';
               s1.setAttribute('crossorigin', '*');
               s0.parentNode.insertBefore(s1, s0);
           })();
       </script>-->
    <!--End of Tawk.to Script-->


    <!-- Optionally, you can add Slimscroll and FastClick plugins.
         Both of these plugins are recommended to enhance the
         user experience. -->
</body>
</html>
