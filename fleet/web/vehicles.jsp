<%-- 
    Document   : Vehicles
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Vehicles</title>
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/jquery-ui.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/vendor.css" />
        <link rel="stylesheet" href="css/bootstrap-datepicker3.min.css" />

        <script src="js/sweetalert.js"></script>  
        <script src="js/sweetalert.min.js"></script>  


        <style>
            @import url(https://fonts.googleapis.com/css?family=Roboto+Condensed:300,400);

            .font-roboto {
                font-family: 'roboto condensed';
            }

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
                    <!--<a class="navbar-brand brand-logo" href="index2.html"><img src="images/logo_star_black.png" /></a>
                    <a class="navbar-brand brand-logo-mini" href="index2.html"><img src="images/logo_star_mini.jpg" alt=""></a>-->
                    <a class="navbar-brand brand-logo" ><img src="images/MTRH_KEBS LOGO_1.jpg" /></a>
                    <a class="navbar-brand brand-logo-mini" ><img src="images/COMPANY_LOGO.jpg" alt=""></a>
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
                            <a  id="profilePic" class="nav-link profile-pic"><img class="rounded-circle" src="images/logoutbtn.png" alt=""></a>
                            <!--   <a id="profilePic" class="nav-link profile-pic" href="#"><img class="rounded-circle" src="images/face.jpg" alt=""></a>-->
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
                            <li class="nav-item">
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
                                <a class="nav-link" href="#">
                                    <img src="images/icons/10.png" alt="">
                                    <span class="menu-title">Administration</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                    <!--Start Content Page-->

                    <div class="content-wrapper">
                        <h3 class="page-heading mb-4">Vehicle Register</h3>


                        <!--End of Row-->
                        <!--Start of Tasks-->
                        <div class="container">

                            <button id="vehform" class="button active" >Add New</button>&nbsp;
                            <button id="refrveh" class="button refresh-storage-status-bar-item" >Refresh</button>&nbsp;&nbsp;&nbsp;
                            <button id="pdfbtn" type="button" class ="btn btn-outline-success">Print PDF</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <label id="count">No of Vehicles</label>



                            <br><br>


                            <br><br>


                            <!--   <button id="export-btn" class="button refresh-storage-status-bar-item" >Export to Excel</button>-->
                            <input type="text" align="right" id="searchveh" name="searchveh" value=""   placeholder="Search by vehicle reg no"  />



                            <div class="table-responsive">
                                <!-- <table id="dataTable" class="table center-aligned-table">-->
                                <table id="vehiclesTbl" class="dataTable table table-striped">
                                    <thead>
                                        <tr class="text-primary">
                                            <th>Fleet ID</th>
                                            <th>Reg No</th>
                                            <th>Make</th>
                                            <th>Model/YOM</th>
                                            <th>Classification</th>
                                            <th>Status</th>
                                            <th></th>                                                    
                                        </tr>
                                    </thead>

                                    <tbody id="vehiclesTbltb"></tbody>
                                </table>

                            </div>


                            <!--   <form action="PDFActions" method="GET"> 
                                   <button id="pdfbtn" type="submit" class ="btn btn-outline-success">Print PDF</button>
                               </form>-->




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
                                <a href="#">Saleem's Lair</a> &copy; 2017
                            </span>
                        </div>
                    </footer>

                    <!-- partial -->
                </div>
            </div>
        </div> 
        <div class="modal fade" id="newVehicleModal" role="dialog"   >
            <div class="modal-dialog modal-lg " >
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Register Vehicle</h4>



                        <button type="button" class="close" data-dismiss="modal">&times;</button>


                    </div>
                    <div class="modal-body"  >
                        <!--Start-->
                        <div class="container jf-form">
                            <!--<form data-licenseKey="" name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='admin.php' method='post' enctype='multipart/form-data' novalidate autocomplete="on">
                             <form  name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='VehicleServlet' method='GET' enctype='multipart/form-data' novalidate autocomplete="on">-->
                            <form  name="regVehicle" id="regVehicle"   autocomplete="off">


                                <div class="form-group f17 " data-fid="f17">
                                    <label class="control-label" for="f17">General Details</label>



                                </div >


                                <div class="form-group f5 " data-fid="f5">
                                    <label class="control-label" for="f5">Reg no</label>


                                    <input type="text" class="form-control" id="f5" name="f5" value=""     
                                           data-mask="SSS ###S" />



                                </div>

                                <div class="form-group f26 " data-fid="f26">
                                    <label class="control-label" for="f26">Date of Registration</label>

                                    <div class="input-group date">
                                        <p><input type="text" id="datepicker"></p>                                    
                                    </div>


                                </div>

                                    <div class="form-group f19 " data-fid="f19">
                                        <label class="control-label" for="f19">Make</label>


                                        <select class="form-control" id="f19" name="f19">
                                            <option  id="makeid" value="">- Select -</option>

                                        </select>


                                    </div>




                                    <div class="form-group f18 " data-fid="f18">
                                        <label class="control-label" for="f18">Model</label>


                                        <select class="form-control" id="f18" name="f18"   
                                                >
                                            <option  value="">- Select -</option>

                                        </select>


                                    </div>


                                    <div class="form-group f18 " data-fid="f18">
                                        <label class="control-label" for="f10">Classification</label>


                                        <select class="form-control" id="f30" name="f30"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>



                                    <div class="form-group f10 " data-fid="f10">
                                        <label class="control-label" for="f10">Year Of Manufacture</label>


                                        <select class="form-control" id="f10" name="f10"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>







                                    <div class="form-group f22 " data-fid="f22">
                                        <label class="control-label" for="f22">Engine CC</label>


                                        <input type="text" class="form-control" id="f22" name="f22" value="0"   placeholder="e.g 1500 cc"  />



                                    </div>




                                    <div class="form-group f23 " data-fid="f23">
                                        <label class="control-label" for="f23">Fuel Used</label>


                                        <!--    <input type="text" class="form-control" id="f23" name="f23" value="unspecified"   placeholder="e.g Diesel, Petrol, Gasoline etc"  />-->


                                        <select class="form-control" id="f23" name="f23">
                                            <option  value="">- Select -</option> 


                                        </select>

                                    </div>




                                    <div class="form-group f20 " data-fid="f20">
                                        <!-- <label class="control-label" for="f20">Vehicle Images</label>
 
                                         <input type="file" class="form-control" id="f20" name="f20[]" data-multiple="true" multiple value=""        data-showUpload="false" data-showZoom="false"/>-->

                                    </div>




                                    <div class="form-group f21 " data-fid="f21">
                                        <label class="control-label" for="f21">Current Mileage *(Kilometers as at present)</label>


                                        <input type="text" class="form-control " id="f21" name="f21" value="0"    
                                               data-rule-number="true"   />


                                    </div>




                                    <div class="form-group f24 " data-fid="f24">
                                        <label class="control-label"  for="f24">Chassis No</label>


                                        <input type="text" class="form-control" id="f24" name="f24" value="0"     />



                                    </div>




                                    <div class="form-group f25 " data-fid="f25">
                                        <label class="control-label" for="f25">Engine No</label>


                                        <input type="text" class="form-control" id="f25" name="f25" value="0"     />



                                    </div>




                                    <div class="form-group submit f0 " data-fid="f0" >
                                        <label class="control-label sr-only" for="f0" style="display: block;">Submit Button</label>

                                        <div class="progress" style="display: none; z-index: -1; position: absolute;">
                                            <div class="progress-bar progress-bar-striped active" role="progressbar" style="width:100%">
                                            </div>
                                        </div>

                                        <button id= "submitveh" type="submit" class="btn btn-primary btn-lg" style="z-index: 1;">
                                            Submit Data
                                        </button>



                                        <div class="submit">
                                            <p class="error bg-warning" style="display:none;">
                                                Please check the required fields.  </p>
                                        </div>

                                        <div class="clearfix"></div>





                                    </div>

                                    <!--End-->

                                </div>

                            </form>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <div class="modal fade" id="add" role="dialog" >
            <div class="modal-dialog" >
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Register New Vehicle</h4>







                        <button type="button" class="button active" >Save Data</button>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>


                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>

        </div>

        <div class="modal fade" id="editModal" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">

                        <h1 id ="RegNoHeader" class="modal-title">Modal Header</h1>&nbsp  &nbsp



                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <h2 id="fleetreg"></h2>

                        <div class="container jf-form1">
                            <!--<form data-licenseKey="" name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='admin.php' method='post' enctype='multipart/form-data' novalidate autocomplete="on">
                             <form  name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='VehicleServlet' method='GET' enctype='multipart/form-data' novalidate autocomplete="on">-->
                            <form  name="regVehicle" id="regVehicle1"   autocomplete="off">

                                <div class="form-group f26 " data-fid="f26">

                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="form-check">
                                                <label>
                                                    <input type="checkbox" id="deployed" class="form-check-input">
                                                    Deployed Fleet?
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-sm">
                                            <label class="control-label" for="section">Section</label>


                                            <select class="form-control" id="secid" name="secid" disabled>
                                                <option  value="">- Select -</option>

                                            </select>
                                        </div>


                                    </div>
                                    <div class="row">
                                        <div class="col-sm">
                                            <label class="control-label" for="driver">Allocated Driver</label>


                                            <select class="form-control" id="driverid" name="driverid" disabled>
                                                <option   value="">- Select -</option>

                                            </select>
                                        </div>

                                        <div class="col-sm">
                                            <label class="control-label" for="7487">Officer In-Charge</label>


                                            <select class="form-control" id="authofid" name="authofid" disabled>
                                                <option   value="">- Select -</option>

                                            </select>

                                        </div>

                                    </div>
                                    <hr>
                                    <div class="form-group f5 " data-fid="f5">
                                        <label class="control-label" for="f5">Reg no</label>
                                        <input type="text" class="form-control" id="f51" name="f51" value=""     
                                               data-mask="SSS ###S" />
                                    </div>


                                    <label class="control-label" for="f26">Date of Registration</label>
                                    <div class="input-group date">
                                        <p><input type="text" id="datepicker1"></p>                                        
                                    </div>




                                    <div class="form-group f19 " data-fid="f191">
                                        <label class="control-label" for="f191">Make</label>


                                        <select class="form-control" id="f191" name="f191">
                                            <option  id="makeid1" value="">- Select -</option>

                                        </select>


                                    </div>




                                    <div class="form-group f18 " >
                                        <label class="control-label" for="f181">Model</label>


                                        <select class="form-control" id="f181" name="f181"   
                                                >
                                            <option  value="">- Select -</option>

                                        </select>


                                    </div>


                                    <div class="form-group f301 " data-fid="f301">
                                        <label class="control-label" for="f10">Classification</label>


                                        <select class="form-control" id="f301" name="f301"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>



                                    <div class="form-group f10 " data-fid="f10">
                                        <label class="control-label" for="f10">Year Of Manufacture</label>


                                        <select class="form-control" id="f101" name="f10"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>







                                    <div class="form-group f22 " data-fid="f22">
                                        <label class="control-label" for="f22">Engine CC</label>


                                        <input type="text" class="form-control" id="f221" name="f22" value="0"  placeholder="e.g 1500" data-mask="#####"  />



                                    </div>




                                    <div class="form-group f23 " data-fid="f23">
                                        <label class="control-label" for="f23">Fuel Used</label>


                                        <!--   <input type="text" class="form-control" id="f23" name="f23" value="unspecified"   placeholder="e.g Diesel, Petrol, Gasoline etc"  />-->
                                        <select class="form-control" id="f231" name="f231"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>




                                    <div class="form-group f20 " data-fid="f20">
                                        <label class="control-label" for="f20">Vehicle Images</label>

                                        <input type="file" class="form-control" id="f201" name="f201[]" data-multiple="true" multiple value=""        data-showUpload="false" data-showZoom="false"/>

                                    </div>




                                    <div class="form-group f21 " data-fid="f21">
                                        <label class="control-label" for="f21">Current Mileage *(Kilometers as at present)</label>


                                        <input type="text" class="form-control " id="f211" name="f211" value="0"    
                                               data-rule-number="true"   />


                                    </div>




                                    <div class="form-group f24 " data-fid="f24">
                                        <label class="control-label"  for="f241">Chassis No</label>


                                        <input type="text" class="form-control" id="f241" name="f24" value="0"     />

                                    </div>




                                    <div class="form-group f25 " data-fid="f25">
                                        <label class="control-label" for="f25">Engine No</label>


                                        <input type="text" class="form-control" id="f251" name="f25" value="0"     />



                                    </div>





                                </div>
                            </form>
                            <!--End-->

                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" id="delfile" class="btn btn-outline-warning" data-dismiss="modal">Deactivate File</button> 
                        <button type="button" id="updfile" class="btn btn-outline-success" data-dismiss="modal">Update File</button>

                        <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- view modal -->

        <!-- view modal -->

        <!-- modal -->
        <div id="fsModal"
             class="modal animated bounceIn"
             tabindex="-1"
             role="dialog"
             aria-labelledby="myModalLabel"
             aria-hidden="true">

            <!-- dialog -->
            <div class="modal-dialog">

                <!-- content -->
                <div class="modal-content">

                    <!-- header -->
                    <div class="modal-header">
                        <h1 id="myModalLabel"
                            class="modal-title">
                            Vehicle Details
                        </h1>
                    </div>
                    <!-- header -->

                    <!-- body -->
                    <div class="modal-body">
                        <h2>1. Modal sub-title</h2>

                        <p>Liquor ipsum dolor sit amet bearded lady, grog murphy's bourbon lancer. Kamikaze vodka gimlet; old rip van winkle, lemon drop martell salty dog tom collins smoky martini ben nevis man o'war. Strathmill grand marnier sea breeze b & b mickey slim. Cactus jack aberlour seven and seven, beefeater early times beefeater kalimotxo royal arrival jack rose. Cutty sark scots whisky b & b harper's finlandia agent orange pink lady three wise men gin fizz murphy's. Chartreuse french 75 brandy daisy widow's cork 7 crown ketel one captain morgan fleischmann's, hayride, edradour godfather. Long island iced tea choking hazard black bison, greyhound harvey wallbanger, "gibbon kir royale salty dog tonic and tequila."</p>

                        <h2>2. Modal sub-title</h2>

                        <p>The last word drumguish irish flag, hurricane, brandy manhattan. Lemon drop, pulteney fleischmann's seven and seven irish flag pisco sour metaxas, hayride, bellini. French 75 wolfram christian brothers, calvert painkiller, horse's neck old bushmill's gin pahit. Monte alban glendullan, edradour redline cherry herring anisette godmother, irish flag polish martini glen spey. Abhainn dearg bloody mary amaretto sour, ti punch black cossack port charlotte tequila slammer? Rum swizzle glen keith j & b sake bomb harrogate nights 7 crown! Hairy virgin tomatin lord calvert godmother wolfschmitt brass monkey aberfeldy caribou lou. Macuá, french 75 three wise men.</p>

                        <h2>3. Modal sub-title</h2>

                        <p>Pisco sour daiquiri lejon bruichladdich mickey slim sea breeze wolfram kensington court special: pink lady white lady or delilah. Pisco sour glen spey, courvoisier j & b metaxas glenlivet tormore chupacabra, sambuca lorraine knockdhu gin and tonic margarita schenley's." Bumbo glen ord the macallan balvenie lemon split presbyterian old rip van winkle paradise gin sling. Myers black bison metaxa caridan linkwood three wise men blue hawaii wine cooler?" Talisker moonwalk cosmopolitan wolfram zurracapote glen garioch patron saketini brandy alexander, singapore sling polmos krakow golden dream. Glenglassaugh usher's wolfram mojito ramos gin fizz; cactus jack. Mai-tai leite de onça bengal; crown royal absolut allt-á-bhainne jungle juice bacardi benrinnes, bladnoch. Cointreau four horsemen aultmore, "the amarosa cocktail vodka gimlet ardbeg southern comfort salmiakki koskenkorva."</p>

                    </div>
                    <!-- body -->

                    <!-- footer -->
                    <div class="modal-footer">
                        <button class="btn btn-secondary"
                                data-dismiss="modal">
                            close
                        </button>
                        <button class="btn btn-default">
                            Default
                        </button>
                        <button class="btn btn-primary">
                            Primary
                        </button>
                    </div>
                    <!-- footer -->

                </div>
                <!-- content -->

            </div>
            <!-- dialog -->

        </div>
        <!-- modal -->



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="js/jquery-1.12.4.js"></script>
        <script src="js/jquery.min.js"></script>        

        <script src="node_modules/jquery/dist/jquery.min.js"></script>
        <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
        <script src="js/off-canvas.js"></script>
        <script src="js/hoverable-collapse.js"></script>
        <script src="js/misc.js"></script>
        <!--Form-->

        <script src="js/bootstrap-datepicker.min.js"></script>


        <script src="js/jquery-validate.min.js"></script>
        <script src="js/jqueryform.com.min.js"></script>
        <script src="js/additional-methods.min.js"></script>
        <script src="js/vendor.js"></script>  
        <script src="js/jqf.js"></script>  
        <script src="js/jquery-ui.js"></script>  
        <!--<script src="js/jquery-form-validator.min.js"></script>  -->
        <script src="js/desktopify.js"></script>


        <script src="js/table2excel.js"></script>




        <script src="js/DashboardInfo.js"></script>
        <script type="text/javascript" src="js/VehicleJS.js"></script> 




    </body>

</html>
