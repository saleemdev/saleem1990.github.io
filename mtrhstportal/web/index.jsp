<%-- 
    Document   : index
    Created on : Aug 31, 2018, 5:10:17 PM
    Author     : owner
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
        <title>MTRH  |Staff Portal</title>
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
        <link rel="stylesheet"
              href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">


        <link rel="stylesheet" href="dist/css/custom.css">


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
                <a  class="logo">
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

                                        <p>

                                        </p>
                                    </li>
                                    <!-- Menu Body -->
                                    <li class="user-body">
                                        <div class="row">
                                            <span id="rankid"></span>
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
                        <div id="oasid" class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>OAS</h3>

                                    <p>Scheduling/Bookings</p>
                                </div>
                                <div class="icon">
                                    <i class="fa fa-calendar-check-o"></i>
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
                <strong>Copyright &copy; 2018 <a >MTRH</a>.</strong> All rights reserved.
            </footer>


            <!-- /.control-sidebar -->
            <!-- Add the sidebar's background. This div must be placed
            immediately after the control sidebar -->
            <div class="control-sidebar-bg"></div>
        </div>
        <!-- ./wrapper -->

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
                            <br>
                            <!-- <div class="forgot">
                                 <a href="reset.html">Forgot password?</a>
                             </div>-->

                            <button type="button" id="accessBtn" class="form-control btn btn-file" disabled>Login</button>

                            <div class="row">

                                <div class="col-md-6">
                                    <a id="frgtp" class="btn btn-secondary">Forgot Password?</a>         
                                </div> 
                                
                                 <div class="col-md-6">
                                    <a id="nwuserid" class="btn btn-secondary">New User?</a>         
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

        <script src="js/CustomDash.js"></script>





        <!-- Optionally, you can add Slimscroll and FastClick plugins.
             Both of these plugins are recommended to enhance the
             user experience. -->
    </body>
</html>
