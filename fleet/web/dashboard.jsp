<%-- 
    Document   : dashboard
    Created on : May 11, 2018, 4:21:45 PM
    Author     : Saleem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Transport | Dashboard</title>
        <!-- <link rel="stylesheet" href="css/bootstrap.min.css" />-->
        <link rel="stylesheet" href="css/bootstrap-glyphs.css" />
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/style.css" />




        <script src="js/jquery-1.12.4.js"></script>

        <link rel="stylesheet" href="css/jquery-ui.css" />

        <script src="js/EZView.js"></script>
        <script src="js/draggable.js"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="js/sweetalert.js"></script>  
        <script src="js/sweetalert.min.js"></script>  



        <!--JQuery Datatables css-->
        <link rel="stylesheet" href="css/jQuery.dataTables.css" />
        <link rel="shortcut icon" href="images/favicon.png" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="node_modules/jquery/dist/jquery.min.js"></script>

        <!--  <script src="js/bootstrap.min.js"></script>    -->

        <style>
            #invamt{
                font-size: 2.0em;
                font-family: "Times New Roman", Times, serif;

            }
        </style>
        <style>

            * {
                box-sizing: border-box;
            }


            .modal {
                position: fixed;
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
                overflow: hidden;

            }

           

            .modal-dialog {
                position: fixed;
                margin: 0;
                width: 100%;
                height: 100%;
                padding: 0;
            }

            .modal-content {
                position: absolute;
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
                border: 2px solid #3c7dcf;
                border-radius: 0;
                box-shadow: none;
            }

            .modal-header {
                position: absolute;
                top: 0;
                right: 0;
                left: 0;
                height: 50px;
                padding: 10px;
                background: #6598d9;
                border: 0;
            }

            .modal-title {
                font-weight: 300;
                font-size: 2em;
                color: #fff;
                line-height: 30px;
            }

            .modal-body {
                position: absolute;
                top: 50px;
                bottom: 60px;
                width: 100%;
                font-weight: 300;
                overflow: auto;
            }

            .modal-footer {
                position: absolute;
                right: 0;
                bottom: 0;
                left: 0;
                height: 60px;
                padding: 10px;
                background: #f1f3f5;
            }


        </style>
        <style>



            table {
                border: 1px solid #ccc;
                border-collapse: collapse;
                margin: 0;
                padding: 0;
                width: 100%;
                table-layout: fixed;
            }

            table caption {
                font-size: 1.5em;
                margin: .5em 0 .75em;
            }

            table tr {
                background-color: #f8f8f8;
                border: 1px solid #ddd;
                padding: .35em;
            }

            table th,
            table td {
                padding: .625em;
                text-align: center;
            }

            table th {
                font-size: .85em;
                letter-spacing: .1em;
                text-transform: uppercase;
            }

            @media screen and (max-width: 600px) {
                table {
                    border: 0;
                }

                table caption {
                    font-size: 1.3em;
                }

                table thead {
                    border: none;
                    clip: rect(0 0 0 0);
                    height: 1px;
                    margin: -1px;
                    overflow: hidden;
                    padding: 0;
                    position: absolute;
                    width: 1px;
                }

                table tr {
                    border-bottom: 3px solid #ddd;
                    display: block;
                    margin-bottom: .625em;
                }

                table td {
                    border-bottom: 1px solid #ddd;
                    display: block;
                    font-size: .8em;
                    text-align: right;
                }

                table td::before {
                    /*
                    * aria-label has no advantage, it won't be read inside a table
                    content: attr(aria-label);
                    */
                    content: attr(data-label);
                    float: left;
                    font-weight: bold;
                    text-transform: uppercase;
                }

                table td:last-child {
                    border-bottom: 0;
                }}




        </style>


    </head>
    <body>
        <div class=" container-scroller">
            <!-- partial:partials/_navbar.html -->            
            <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="bg-white text-center navbar-brand-wrapper">

                    <!-- <a class="navbar-brand brand-logo" href="index2.html"><img src="images/logo_star_black.png" /></a>
                      <a class="navbar-brand brand-logo-mini" href="index2.html"><img src="images/logo_star_mini.jpg" alt=""></a>-->
                    <a class="navbar-brand brand-logo" href="index.html"><img src="images/MTRH_KEBS LOGO_1.jpg" /></a>
                    <a class="navbar-brand brand-logo-mini" href="index.html"><img src="images/COMPANY_LOGO.jpg" alt=""></a>

                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <button class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                        <input style="display:none" class="form-control mr-sm-2 search" type="text" placeholder="Search">
                    </form>

                    <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">

                        <li class="nav-item">
                            <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="#">2<i class="fa fa-bell"></i></a>
                        </li>
                        <li class="nav-item">
                            <!-- <label id="myloginId">user001</label> -->
                            <a  id="profilePic" class="nav-link profile-pic"><img class="rounded-circle" src="images/logoutbtn.png" alt=""></a>
                        </li>




                    </ul>

                    <button class="navbar-toggler navbar-dark navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
            </nav>



            <!-- partial -->
            <div class="container-fluid">
                <div class="row row-offcanvas row-offcanvas-right">
                    <!-- partial:partials/_sidebar.html -->
                    <nav class="bg-white sidebar sidebar-offcanvas" id="sidebar">
                        <div class="user-info">

                            <img src="images/admin1.png" alt="">
                            <p id="uName" class="name">Anonymous User</p>
                            <p id ="rank"  class="designation">User</p>
                            <span class="online"></span>
                        </div>
                        <ul class="nav">
                            <li class="nav-item active">
                                <a class="nav-link" href="dashboard.jsp">
                                    <img src="images/icons/1.png" alt="">
                                    <span class="menu-title">Dashboard</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a  class="nav-link" href="reports.jsp">
                                    <img src="images/rpt.png" alt="" width="35" height="35">
                                    <span class="menu-title">Reports</span>
                                </a>
                            </li>


                            <li class="nav-item">
                                <a class="nav-link" href="pages/ui-elements/buttons.html">
                                    <img src="images/icons/001-map.png" alt="">
                                    <span class="menu-title">Location Services</span>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a id="adminForm" class="nav-link" >
                                    <img src="images/icons/10.png" alt="">
                                    <span class="menu-title">Administration</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                    <!--Start Content Page-->
                    <div class="content-wrapper">
                        <h3 class="page-heading mb-4">Dashboard</h3>
                        <div class="row">
                            <div class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card card-statistics">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <div class="float-left">
                                                <h4 class="text-danger">
                                                    <!--<i class="fa fa-bar-chart-o highlight-icon" aria-hidden="true"></i>-->
                                                    <i class="fa fa-drivers-license fa-4x highlight-icon" aria-hidden="true"></i>


                                                </h4>
                                            </div>
                                            <div class="float-right">
                                                <p class="card-text text-dark">Drivers</p>
                                                <h4 id="drivers" class="bold-text">0</h4>
                                            </div>
                                        </div>
                                        <p class="text-muted">
                                            <i class="fa fa-exclamation-circle mr-1" aria-hidden="true"></i> On active duty &nbsp
                                            <button class="badge badge-success" id="showdrv">Dept List</button>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div id ="cars" class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card card-statistics">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <div class="float-left">
                                                <h4 class="text-warning">
                                                    <i class="fa fa-car fa-4x highlight-icon" aria-hidden="true"></i>
                                                    <!-- <i class="fa fa-shopping-cart highlight-icon" aria-hidden="true"></i>-->
                                                </h4>
                                            </div>
                                            <div class="float-right">
                                                <p class="card-text text-dark">Vehicles</p>
                                                <h4 id="vehicles" class="bold-text">0</h4>
                                            </div>
                                        </div>
                                        <p class="text-muted">
                                            <i class="fa fa-bookmark-o mr-1" aria-hidden="true"></i> Active Vehicles &nbsp
                                            <a  href="vehicles.jsp" class="badge badge-success" id="showvpdf" >  Fleet List</a>

                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card card-statistics">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <div class="float-left">
                                                <h4 class="text-success">
                                                    <i class="fa fa-file fa-2x highlight-icon" aria-hidden="true"></i>
                                                </h4>
                                            </div>
                                            <div class="float-right">
                                                <p class="card-text text-dark">Requisitions Today</p>
                                                <h4 id="requisitions" class="bold-text">0</h4>
                                            </div>
                                        </div>
                                        <p class="text-muted">
                                            <i class="fa fa-file-archive-o" aria-hidden="true"></i> Requisitions &nbsp
                                            <button class="badge badge-success" id="showreqs"> View Reqs </button>

                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card card-statistics">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <div class="float-left">
                                                <h4 class="text-primary">
                                                    <i class="fa fa-calendar fa-2x" aria-hidden="true"></i>
                                                </h4>
                                            </div>
                                            <div class="float-right">
                                                <p class="card-text text-dark">Active Work Orders</p>
                                                <h4 id ="tickets" class="bold-text">0</h4>
                                            </div>
                                        </div>
                                        <p class="text-muted">
                                            <i class="fa fa-repeat mr-1" aria-hidden="true"></i>Updated &nbsp
                                            <button class="badge badge-success" id="showticket">View Tickets</button>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--End of Row-->
                        <!--Start of Tasks-->
                        <div class="card-deck">
                            <div class="card col-lg-12 px-0 mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">My Activities</h5>&nbsp;
                                    <button class="badge badge-info" id="refreshactivities">Refresh</button>&nbsp;
                                    <input type="text" align="right" id="searchveh" name="searchveh" value=""   placeholder="Search request.."  />&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;
                                    <!--hh-->
                                    <button class="button btn-outline-primary" id="depfleetid"> Daily Work Ticket (Deployed Fleet)</button>&nbsp;

                                    <div class="table-responsive" style="overflow: "auto"">
                                         <!-- <table id="dataTable" class="table center-aligned-table">-->
                                         <table id="activitiesTbl" class="dataTable table table-striped">
                                            <thead>
                                                <tr class="text-primary">
                                                    <th>Request ID</th>
                                                    <th>Activity Type</th>
                                                    <th>Date Posted</th>
                                                    <th>Requested By</th>
                                                    <th>Status</th>
                                                    <th id="showOrder"></th>                                                  
                                                </tr>
                                            </thead>


                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!--End of Tasks-->
                    </div>

                    <!-- partial -->
                    <div class="content-wrapper">
                    </div>


                    <!-- partial:partials/_footer.html -->
                    <footer class="footer">
                        <div class="container-fluid clearfix">
                            <span class="float-right">
                                <a href="#">Star Admin</a> &copy; 2017
                            </span>
                        </div>
                    </footer>

                    <!-- partial -->
                </div>
            </div>
        </div> 
        <div class="modal fade" id="wrkTicketModal" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        Transport Daily Work Ticket
                        <button id="closeticket" type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div id ="ticketBodyid" class="modal-body">

                        <h3>Allocated Vehicles:</h3> 
                        <select class="form-control select-editable" id="vehalloc" name="vehalloc">
                            <option  value="">- Select -</option> 
                        </select>


                        <div class="table-responsive">
                            <h3>  Allocated Trips: </h3>
                            <table id="ticketTbl" class="dataTable table table-sm">

                                <thead>
                                    <tr class="text-primary">
                                        <th>Request ID</th>
                                        <th>Requested By</th>
                                        <th>Destination</th>
                                        <th>Status</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id ="ticketBodyID" ></tbody>
                            </table>
                        </div>

                        <hr>



                        <fieldset form="form2">

                            <legend><h3>Request Details:</h3></legend>
                            <strong>Requested By:</strong> <input type="text" class="form-control md-input" id="reqbyid"   autocomplete="off" required>
                            <div class="row">
                                <div class="col-sm">
                                    <strong>Organization:</strong> <input type="text" value="MTRH" class="form-control" id="orgnameid"   autocomplete="off" required>
                                </div>
                                <div class="col-sm">
                                    <strong>Department: </strong>  <input type="text" class="form-control" id="deptid1"   autocomplete="off" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                    <strong>From:</strong> <input type="text" class="form-control" id="fromid"   autocomplete="off" required>
                                </div>
                                <div class="col-sm">
                                    <strong>Destination: <input type="text" class="form-control" id="destid"   autocomplete="off" required>
                                        </div> 
                                        </div>
                                        <div class="row">

                                            <div class="col-sm">
                                                <strong>Average Distance(km):</strong> <input type="text" class="form-control" id="avd1"   autocomplete="off" required>
                                            </div>

                                            <div class="col-sm">
                                                <strong style="font-color: red">Current Mileage(Km)</strong> 
                                                <input type="text" class="form-control" id="avd11"   autocomplete="off" required>
                                            </div>

                                        </div>

                                        <div class="row">
                                            <div class="col-sm">
                                                <strong style="font-color: red">Allocated Vehicle</strong> 
                                                <input type="text" class="form-control" id="allochehregid"   autocomplete="off" disabled>
                                            </div>
                                        </div>


                                        <div class="row">
                                            <div class="col-sm">
                                                Purpose of Transport: <textarea class="form-control" id="purposeTxt1" value="-" rows="5"></textarea>
                                            </div>

                                        </div>

                                        </fieldset>



                                </div>
                                <div class="modal-footer">
                                    <span>  

                                        <button id="apprvtck" class="btn btn-success glyphicon-save">Enter Work Log</button>
                                        <button id="rjtck" class="btn btn-warning">Clear Window</button>
                                        <button id="closemod" class="btn btn-primary" data-dismiss="modal">Close</button>
                                    </span>
                                </div>

                            </div>

                    </div>

                </div>

                <!--Start Fuel and Mtce-->
                <div class="modal fade" id="fuelAndMtcemodal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                Transport Daily Work Ticket for Fueling and Maintenance
                                <button id="closeticket" type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>


                            <div id ="fuelmtcebody" class="modal-body">

                                <input type="text" align="right" id="searchfuel" name="searchfuel" value=""   placeholder="Search ticket.."  />&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="table-responsive">

                                    <table id="mtcefueltbl" class="dataTable table table-striped">

                                        <thead>
                                            <tr class="text-primary">
                                                <th>Vehicle</th>
                                                <th>Service Type</th>
                                                <th>Ref No</th>
                                                <th>Planned Date</th>
                                                <th>Status</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id ="mtcefueltbody" ></tbody>
                                    </table>
                                </div>


                            </div>
                            <div class="modal-footer">
                                <span>  


                                    <button class="btn btn-primary" data-dismiss="modal">Close</button>
                                </span>
                            </div>

                        </div>

                    </div>

                </div>
                <!--End Fuel and MTCE-->

                <div class="modal fade" id="apprVrqModal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">

                                Request ID: <p id ="apprvhd" class="modal-title">Modal Header</p>&nbsp  &nbsp
                                <button id="closeModalBtn" type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div id ="mbodyid" class="modal-body">

                                <fieldset form="form1">
                                    <legend>Approval Stage:</legend>                            
                                    <!--class="fa fa-check-circle"-->

                                    <span  id="trav" class="badge badge-secondary"><strong>1.Transport Approval</strong></span>  <i id="trachk"  style="font-size:24px;color:green"></i>
                                    <span id="snr" class="badge badge-secondary"><strong>2. Snr Manager Approval</strong></span>  <i id ="snrchk"  style="font-size:24px;color:green"></i>
                                    <span id ="ticket" class="badge badge-secondary"><strong>3. Printing Ticket</strong></span>   <i id="tickchk"  style="font-size:24px;color:green"></i>
                                    <span id="secrel" class="badge badge-secondary"><strong>4. Security Release</strong></span>  <i id="securityr"  style="font-size:24px;color:green"></i>


                                    &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp 
                                    &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp 
                                    &nbsp  &nbsp &nbsp  &nbsp &nbsp  &nbsp &nbsp  

                                    <span id="secrel" class="fa fa-envelope fa-3x" style="color:green" onclick="sendMsg()"><strong> 3</strong></span>   

                                </fieldset>
                                <hr>

                                <strong>Requested By:</strong> <input type="text" class="form-control" id="reqby"   autocomplete="off" required>

                                <fieldset form="form2">
                                    <legend>Request Details:</legend>
                                    <div class="row">
                                        <div class="col-sm">
                                            <strong>Organization:</strong> <input type="text" class="form-control" id="orgname"   autocomplete="off" required>
                                        </div>
                                        <div class="col-sm">
                                            <strong>Department: </strong>  <input type="text" class="form-control" id="dept"   autocomplete="off" required>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm">
                                            <strong>From:</strong> <input type="text" class="form-control" id="from"   autocomplete="off" required>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm">
                                            <strong>Destination: <input type="text" class="form-control" id="dest"   autocomplete="off" required></strong>
                                        </div> 
                                        <div class="col-sm">
                                            <strong>Average Distance(km):</strong> <input type="text" class="form-control" id="avd"   autocomplete="off" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm">
                                            <strong>Vehicle Requested:</strong> <!--<input type="text" class="form-control" id="vehreq1"   autocomplete="off" required>-->
                                            <select class="form-control" id="vehreq1" name="f103">
                                                <option  value="">- Select -</option> 
                                            </select>
                                        </div>

                                        <div class="col-sm">
                                            <strong style="font-color: red">Available Fleet</strong> <!--<input type="text" class="form-control" id="vehreq1"   autocomplete="off" required>-->
                                            <select class="form-control" id="vehreq1Av" name="vehreq1Av" disabled onchange="this.nextElementSibling.value = this.value">
                                                <option  value="">- Select -</option> 
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm">
                                            <strong style="font-color: red">Allocated Driver</strong> <!--<input type="text" class="form-control" id="vehreq1"   autocomplete="off" required>-->
                                            <select class="form-control" id="drvallocid" name="drvallocid" disabled>
                                                <option  value="">- Select -</option> 
                                            </select>
                                        </div>
                                    </div>
                                    <!--30924936-->
                                </fieldset>
                                <hr>
                                <fieldset form="form3">
                                    <legend>Contact Info:</legend>

                                    <div class="row">
                                        <div class="col-sm">
                                            Tel/Phone No: <input type="text" class="form-control" id="phoneid" placeholder="Phone No"  autocomplete="off" required>
                                        </div>
                                        <div class="col-sm">
                                            Email Address: <input type="text" class="form-control" id="email" placeholder="Email" autocomplete="off" required>
                                        </div>

                                    </div>



                                </fieldset>

                                <hr>
                                <fieldset form="4">
                                    <legend>Requested Items:</legend>
                                    <div class="row">
                                        <div class="col-sm">

                                            <div class="table-responsive">
                                                <!-- <table id="dataTable" class="table center-aligned-table">-->
                                                <table id="invoiceTbl" class="dataTable table table-sm">
                                                    <col width="130">
                                                    <col width="80">
                                                    <col width="80">
                                                    <thead>
                                                        <tr class="text-primary">
                                                            <th>Particulars</th>
                                                            <th>Unit Price</th>
                                                            <th>Line Total</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody></tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-sm">
                                            Invoice Amount: <input type="text" class="form-control currency" id="invamt" placeholder="$0.00" autocomplete="off" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm">
                                            <button id="addItems" class="fa fa-edit btn btn-primary"></button>
                                        </div>
                                        <div class="col-sm">

                                            <div class="form-check">
                                                <label>
                                                    <input type="checkbox" id="billItems" class="form-check-input" checked>
                                                    Send to Billing
                                                </label>
                                            </div>
                                            <!--<label for="fancy-checkbox-default" class="[ btn btn-default active ]">
                                                Raise Bill
                                            </label>-->
                                        </div>
                                    </div>

                                </fieldset>

                                <hr>

                                <fieldset form="form5">
                                    <legend>Comments/Attachments:</legend>

                                    <div class="row">
                                        <div class="col-sm">
                                            Purpose of Transport: <textarea class="form-control" id="purposeTxt" rows="3"></textarea>
                                        </div>
                                        <div class="col-sm">
                                            Attached Files: <br> 
                                            <div class="table-responsive">
                                                <!-- <table id="dataTable" class="table center-aligned-table">-->
                                                <table id="fileList" class="dataTable table table-sm">

                                                    <thead>
                                                        <tr class="text-danger">
                                                            <th>File ID</th>
                                                            <th>File Description</th>
                                                            <th></th>

                                                        </tr>
                                                    </thead>
                                                </table>
                                            </div>

                                        </div>
                                    </div>
                                </fieldset>


                            </div>

                            <div class="modal-footer">
                                <span>  

                                    <button id="aprvBtn" class="btn btn-success glyphicon-save">Approve Request</button>
                                    <button id="rejectbtn" class="btn btn-warning">Decline Request</button>
                                    <button id="closemod" class="btn btn-primary" data-dismiss="modal">Close</button>
                                </span>
                            </div>

                        </div>

                    </div>

                </div>

                <div id="verRcpt" class="modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">RECEIPT VERIFICATION</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Please Enter Receipt Number And Click Verify.</p>
                                Amt:(KES)  <p id="amtfld"></p>
                                <input type="text" class="form-control" id="rcptver"   autocomplete="off" required>
                            </div>

                            <div class="modal-footer">
                                <button id="verbtn" type="button" class="btn btn-primary">Verify</button>
                                <button id="closeverbtn" type="button" class="btn btn-secondary" data-backdrop="false" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>




                <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">Confirmation</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                Are you Sure to Logout?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">No, I'll Stay</button>
                                <button id='logMeOutNow' type="button" class="btn btn-primary">Log Me Out</button>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="pdfModal" class="modal fade" role="dialog" tabindex="-1">
                    <div class="modal-dialog modal-lg">

                        <!-- Modal content-->
                        <div class="modal-content" style="size: 1000px;width: 1000px" >
                            <div class="modal-header">
                                <h4 class="modal-title">Ticket Report</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>

                            </div>
                            <div  class="modal-body">

                                <embed id="embedpath" src="C:\Users\owner\Desktop\OL 330 Syllabus.pdf"
                                       frameborder="0" width="100%" height="400px">

                                <div class="modal-footer">
                                    <button id="print" type="button" class="btn btn-default" data-dismiss="modal"  >Close</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div id="overlay"></div>
                <div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='images/spinning-loading-bar.gif' width="64" height="64" /><br>Loading..</div>


                <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
                <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
                <script src="node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
                <script src="js/off-canvas.js"></script>
                <script src="js/hoverable-collapse.js"></script>
                <script src="js/misc.js"></script>
                <!--Data Tables-->
                <script src="js/jquery-ui.js"></script>

                <script src="js/DashboardInfo.js"></script>

                <script src="js/CustomDashBoard.js"></script>




                <script>
                                                function w3_open() {
                                                    document.getElementById("mySidebar").style.display = "block";
                                                }
                                                function w3_close() {
                                                    document.getElementById("mySidebar").style.display = "none";
                                                }


                                                //Left Menu

                </script>
                <script>
                    $(document).ready(function () {
                        // $('[data-toggle="popover"]').popover({
                        //       title: "My Account",
                        //      html:true
                        //  });
                        $("[data-toggle=popover]").popover({
                            html: true,
                            content: function () {
                                return $('#popover-content').html();
                            }
                        });
                    });
                </script>
                <style>
                    #overlay {
                        position: fixed; /* Sit on top of the page content */
                        display: none; /* Hidden by default */
                        width: 100%; /* Full width (cover the whole page) */
                        height: 100%; /* Full height (cover the whole page) */
                        top: 0; 
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background-color: rgba(0,0,0,0.5); /* Black background with opacity */
                        z-index: 2; /* Specify a stack order in case you're using a different order for other elements */
                        cursor: pointer; /* Add a pointer on hover */
                    }
                </style>

                <div class="modal fade" id="userAdmin" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">

                                Administration Panel
                                <button id="closeAdmin" type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div id ="adminBody" class="modal-body">

                                <a>Sign In & Security</a>


                                <button id="userReport" type="button" class="close fa fa-print">  Print Users Report</button>
                                <hr>

                                <div id="tab1">
                                    <form id="userRequestForm" autocomplete="off">
                                        <div class="form-group">
                                            <input type="text" id ="pffnoid" class="form-control p_input" placeholder="Enter your PF No Here and Press Enter" name="idno">
                                        </div>

                                        <div class="row">
                                            <div class="col-sm">
                                                <strong>Name</strong> <input type="text" class="form-control" id="nameid"   autocomplete="off" disabled>
                                            </div>
                                            <div class="col-sm">
                                                <strong>Tel No </strong>  <input type="text" class="form-control" id="telid"   autocomplete="off" required>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm">
                                                <strong>E-Mail</strong> <input type="text" class="form-control" id="emailid"   autocomplete="off" required>
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="col-sm">
                                                <strong>Department</strong> <input type="text" class="form-control" id="deptid"   autocomplete="off" required>
                                            </div>
                                            <div class="col-sm">
                                                <strong>Section </strong>  <input type="text" class="form-control" id="secid"   autocomplete="off" required>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm">
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
                                            <div class="col-sm">
                                                <strong>LoginID</strong>  <input type="text" class="form-control" id="secloginid"   autocomplete="off" required>
                                            </div>

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

            </div>

    </body>
</html>
