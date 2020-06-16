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
        <title>Transport | Reports</title>
        <!-- <link rel="stylesheet" href="css/bootstrap.min.css" />-->
        <link rel="stylesheet" href="css/bootstrap-glyphs.css" />
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/style.css" />


        <link rel="stylesheet" href="css/buttons.css" />




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
                                <a class="nav-link" href="reports.jsp">
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
                        <h3 class="page-heading mb-4">Reports</h3>

                        <p>Click any of the tiles to print the respective report</p>
                        <div class="row">
                            <div id="userslistid" class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <i class="fa fa-users float-right icon-grey-big"></i>
                                        </div>
                                        <h4 class="card-title font-weight-normal text-success">Users</h4>
                                        <h6 class="card-subtitle mb-4">Active</h6>
                                        <div class="progress progress-slim">
                                            <div class="progress-bar bg-success-gadient" role="progressbar" style="width: 100%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!---->

                            <div id="vehiclelistid"  class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <i class="fa fa-cab float-right icon-grey-big"></i>
                                        </div>
                                        <h4 class="card-title font-weight-normal text-success">Vehicles</h4>
                                        <h6 class="card-subtitle mb-4">Registered</h6>
                                        <div class="progress progress-slim">
                                            <div class="progress-bar bg-success-gadient" role="progressbar" style="width: 100%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!---->
                            <div id="dwkid" class="col-xl-3 col-lg-3 col-md-3 col-sm-6 mb-4">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="clearfix">
                                            <i class="fa fa-ticket float-right icon-grey-big"></i>
                                        </div>
                                        <h4 class="card-title font-weight-normal text-success">Daily Work Ticket</h4>
                                        <h6 class="card-subtitle mb-4">Summary</h6>
                                        <div class="progress progress-slim">
                                            <div class="progress-bar bg-success-gadient" role="progressbar" style="width: 100%" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                           
                        </div>


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

        <!-- Modal -->
        <div class="modal fade" id="ticketModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Work TIcket Panel</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class ="col-sm">
                                <label class="control-label" for="f26">Date From </label>

                                <div class="input-group date">
                                    <p><input type="text" id="datepicker"></p>                                    
                                </div>
                            </div>
                            <div class ="col-sm">

                                <label class="control-label" for="f26">Date To </label>

                                <div class="input-group date">
                                    <p><input type="text" id="datepicker2"></p>                                    
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class ="col-sm">
                                <h3>Vehicle Reg</h3> 
                                <select class="form-control" id="vehRegid" name="vehRegid">
                                    <option  value="">- Select -</option> 
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button id="printReport" type="button" class="btn btn-primary fa fa-print">  Print</button>
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



        <script src="js/jquery-ui.js"></script>  
        <!--Data Tables-->

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
