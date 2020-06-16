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
        <title>Transport Request</title>
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/vendor.css" />
        

        <script src="js/jquery-1.12.4.js"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
        <!--JQuery Datatables css-->
        <link rel="shortcut icon" href="images/favicon.png" />

        <link rel="stylesheet" href="css/jquery-ui.css" />

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="node_modules/jquery/dist/jquery.min.js"></script>

        <script src="js/jquery-ui.js"></script>  

        <script src="js/sweetalert2.js"></script>  
        <script src="js/sweetalert2.all.js"></script>  
        <script src="js/sweetalert.js"></script>  
        <script src="js/sweetalert.min.js"></script>
        

        <script src="js/jspdf.js"></script>

        <style>
            .modal-header {
                padding:9px 15px;
                border-bottom:1px solid #eee;
                background-color: #0480be;
                -webkit-border-top-left-radius: 5px;
                -webkit-border-top-right-radius: 5px;
                -moz-border-radius-topleft: 5px;
                -moz-border-radius-topright: 5px;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
            }
        </style>

        <style>


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

            .btn {
                height: 40px;
                border-radius: 0;

                &:focus,
                    &:active,
                    &:active:focus {
                    box-shadow: none;
                    outline: none;
                }
            }

            .btn-modal {
                position: absolute;
                top: 50%;
                left: 50%;
                margin-top: -20px;
                margin-left: -100px;
                width: 200px;
            }

            .btn-primary,
            .btn-primary:hover,
            .btn-primary:focus,
            .btn-primary:active {
                font-weight: 300;
                font-size: 1.6rem;
                color: #fff;
                color: lighten(#484b5b, 20%);
                color: #fff;
                text-align: center;
                background: #60cc69;
                border: 1px solid #36a940;
                border-bottom: 3px solid #36a940;
                box-shadow: 0 2px 4px rgba(0,0,0,0.15);

                &:active {
                    border-bottom: 1px solid #36a940;
                }
            }

            .btn-default,
            .btn-default:hover,
            .btn-default:focus,
            .btn-default:active {
                font-weight: 300;
                font-size: 1.6rem;
                color: #fff;
                text-align: center;
                background: darken(#dcdfe4, 10%);
                border: 1px solid darken(#dcdfe4, 20%);
                border-bottom: 3px solid darken(#dcdfe4, 20%);


                &:active {
                    border-bottom: 1px solid darken(#dcdfe4, 20%);
                }


                .btn-secondary,
                .btn-secondary:hover,
                    .btn-secondary:focus,
                    .btn-secondary:active {
                    color: #cc7272;
                    background: transparent;
                    border: 0;
                }

                h1,
                h2,
                h3 {
                    color: #60cc69;
                    line-height: 1.5;


                    &:first-child {
                        margin-top: 0;
                    }
                }

                p {
                    font-size: 1.4em;
                    line-height: 1.5;
                    color: lighten(#5f6377, 20%);


                    &:last-child {
                        margin-bottom: 0;
                    }
                }

                ::-webkit-scrollbar {
                    -webkit-appearance: none;
                    width: 10px;
                    background: #f1f3f5;
                    border-left: 1px solid darken(#f1f3f5, 10%);
                }

                ::-webkit-scrollbar-thumb {
                    background: darken(#f1f3f5, 20%);
                }
            </style>

        </head>
        <body>


            <div id="tabs-1" class=" container-scroller">
                <!-- partial:partials/_navbar.html -->
                <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">

                    <div class="navbar-menu-wrapper d-flex align-items-center">
                        <button id="closenav" class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                         <!-- <input class="form-control mr-sm-2 search" type="text" placeholder="Search">-->
                        </form>
                        <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">
                            <li class="nav-item">
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
                            </li>
                        </ul>

                        <button class="navbar-toggler navbar-dark navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                    </div>
                </nav><br><br>


                <!-- partial -->
                <div class="container-fluid">
                    <div class="row row-offcanvas row-offcanvas-right">
                        <!-- partial:partials/_sidebar.html -->


                        <nav class="bg-white sidebar sidebar-offcanvas" id="sidebar">
                            <div class="user-info">

                                <!--   <img src="images/face.jpg" alt="">-->
                                <p id="uName3" class="name">Anonymous User</p>
                                <p id ="rank2"  class="designation">USER</p>
                                <span class="online"></span>
                            </div>
                            <ul class="nav">
                                <li  class="nav-item active">
                                    <a id="trequest" class="nav-link" href="#tabs-1">
                                        <img src="images/icons/1.png" alt="">
                                        <span class="menu-title">Transport Request</span>
                                    </a>
                                </li>


                                <li  class="nav-item">
                                    <a id="frequest" class="nav-link" href="#tabs-2">
                                        <img src="images/icons/009-layout.png" alt="">
                                        <span class="menu-title">Fuel Request</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a id="mrequest" class="nav-link" href="#tabs-3">
                                        <img src="images/icons/005-forms.png" alt="">
                                        <span class="menu-title">Maintenance Request</span>
                                    </a>
                                </li>
                                <!--     <li class="nav-item">
                                         <a class="nav-link" href="#">
                                             <img src="images/icons/001-map.png" alt="">
                                             <span class="menu-title">Maps</span>
                                         </a>
                                     </li>-->

                                <li class="nav-item">
                                    <a id="increport"  class="nav-link" href="#tabs-4">
                                        <img src="images/icons/10.png" alt="">
                                        <span class="menu-title">Incidence Reporting</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <!--Start Content Page-->
                        <div class="content-wrapper">
                            <h3 class="page-heading mb-4">Transport Request Form</h3>
                            <a href="/fleet/" class="fa fa-arrow-left">Back to Login</a>

                            <!--End of Row-->
                            <!--Start of Tasks-->
                            <div class="row mb-2">
                                <div class="col-lg-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title mb-4">Details</h5>

                                            <div id="qrcode">

                                            </div> 
                                            
                                            
                                            
                                            <form id="basicdataform" class="forms-sample" autocomplete="off">

                                                <div class="form-group">
                                                    <label >Full Name</label>
                                                    <input type="text" class="form-control p-input" id="requestedby" data-validate="required"  placeholder="Enter Full Name of Person Requesting">
                                                </div>

                                                <div class="form-group">
                                                    <label >Ref Number</label>
                                                    <input type="text" class="form-control p-input"  required="true" id="refid"  placeholder="Enter PF Number or ID of person requesting">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Email address</label>
                                                    <input type="email" class="form-control p-input" id="emailid" aria-describedby="emailHelp" placeholder="Enter email" data-validation="email required">
                                                </div>


                                                <div class="form-group">
                                                    <label >Tel. No</label>
                                                    <input type="text" class="form-control p-input" id="telno" data-validation="text length" data-validation-length="min9" value="" placeholder="Enter Mobile/Tel No">
                                                </div>


                                                <div class="form-group">
                                                    <div class="form-check">
                                                        <label>
                                                            <input type="checkbox" id="chckmail" class="form-check-input" checked>
                                                            Alert the email above on the progress
                                                        </label>
                                                    </div>

                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request For Hire(Staff)" type="radio">
                                                            Request For Hire(Staff)                                                 
                                                        </label>
                                                        <label> <strong>NB: PF No Will be verified</strong></label>
                                                    </div>
                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request For Hire(External)" type="radio">
                                                            Request For Hire(External)
                                                        </label>
                                                    </div>
                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request for Internal Use" type="radio">
                                                            Request for Internal Use
                                                        </label>
                                                    </div>

                                                    

                                                </div>


                                                <div class="form-group">

                                                    <div class="table-responsive">
                                                        <!-- <table id="dataTable" class="table center-aligned-table">-->
                                                        Order items:<br>

                                                        <table id="InvItemsTbl" class="dataTable table table-striped">
                                                            <thead>
                                                                <tr class="text-primary">
                                                                    <th>Item</th>
                                                                    <th>UnitCost</th>
                                                                    <th>#</th>
                                                                    <th>Total</th>
                                                                    <th></th>                                                    
                                                                </tr>
                                                            </thead>

                                                            <tbody id="InvItemsTblbody"></tbody>
                                                        </table>

                                                    </div>
                                                </div>




                                                <div class="form-group">
                                                    <!--  <label for="exampleTextarea">Purpose of Transport</label>
                                                     <textarea class="form-control p-input" id="exampleTextarea" rows="5" value="-" placeholder="Type some comments"></textarea> -->
                                                </div> 


                                                <div class="form-group">
                                                    <!--  <label for="exampleTextarea">Purpose of Transport</label>
                                                     <textarea class="form-control p-input" id="exampleTextarea" rows="5" value="-" placeholder="Type some comments"></textarea> -->
                                                </div> 







                                                <div class="form-group">
                                                    <button type="button" id="btnproceed" class="btn btn-primary">Next</button>
                                                </div> 
                                                <div  class="form-group">
                                                    <button type="button" id="btnfinish"  class="btn btn-success">Submit Details/Print Order</button>
                                                    <button type="button" id="resetTbl" class="btn btn-warning" >Reset Table</button>

                                                </div>
                                                <!-- <div class="form-group">
                                                     <button type="submit" class="btn btn-primary">Submit</button>
                                                 </div>-->
                                            </form>
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
                                    <a href="#">saleem's lair</a> &copy; 2017
                                </span>
                            </div>
                        </footer>
                    </div>
                </div> <!-- partial -->
            </div>



            <!--Another JQM PAge-->


            <div id="tabs-2" class=" container-scroller">
                <!-- partial:partials/_navbar.html -->
                <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">

                    <div class="navbar-menu-wrapper d-flex align-items-center">
                        <button id="closenav" class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                            <input class="form-control mr-sm-2 search" type="text" placeholder="Search">
                        </form>
                        <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">
                            <li class="nav-item">
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
                            </li>
                        </ul>

                        <button class="navbar-toggler navbar-dark navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                    </div>
                </nav><br><br>


                <!-- partial -->
                <div class="container-fluid">
                    <div class="row row-offcanvas row-offcanvas-right">
                        <!-- partial:partials/_sidebar.html -->


                        <nav class="bg-white sidebar sidebar-offcanvas" id="sidebar">
                            <div class="user-info">

                                <!--   <img src="images/face.jpg" alt="">-->
                                <p id="uName4" class="name">Anonymous User</p>
                                <p id ="rank3"  class="designation">USER</p>
                                <span class="online"></span>
                            </div>
                            <ul class="nav">
                                <li  class="nav-item active">
                                    <a id="trequest" class="nav-link" href="#tabs-1">
                                        <img src="images/icons/1.png" alt="">
                                        <span class="menu-title">Transport Request</span>
                                    </a>
                                </li>


                                <li  class="nav-item">
                                    <a id="frequest" class="nav-link" href="#tabs-2">
                                        <img src="images/icons/009-layout.png" alt="">
                                        <span class="menu-title">Fuel Request</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a id="mrequest" class="nav-link" href="#tabs-3">
                                        <img src="images/icons/005-forms.png" alt="">
                                        <span class="menu-title">Maintenance Request</span>
                                    </a>
                                </li>
                                <!--     <li class="nav-item">
                                         <a class="nav-link" href="#">
                                             <img src="images/icons/001-map.png" alt="">
                                             <span class="menu-title">Maps</span>
                                         </a>
                                     </li>-->

                                <li class="nav-item">
                                    <a id="increport"  class="nav-link" href="#tabs-4">
                                        <img src="images/icons/10.png" alt="">
                                        <span class="menu-title">Incidence Reporting</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <!--Start Content Page-->
                        <div class="content-wrapper">
                            <h3 class="page-heading mb-4">Fuel Request Form</h3>
                            <a href="/fleet/" class="fa fa-arrow-left">Back to Login</a>&nbsp;&nbsp;&nbsp;&nbsp;



                            <button id="addItems" class="fa fa-edit btn btn-primary"  data-toggle="modal" data-target="#searchexistingreq" onclick="focusFn()" >Edit Existing</button>


                            <!--End of Row-->
                            <!--Start of Tasks-->
                            <div class="row mb-2">
                                <div class="col-lg-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title mb-4">Details</h5>
                                            <form id="fuelreqformid" class="forms-sample" autocomplete="off">
                                                <div class="form-group">
                                                    <label >Card No:</label>
                                                    <input type="text" class="form-control p-input" id="cardnoid" data-validate="required"  placeholder="Enter Card No:" onfocusout="verifyCard()">
                                                </div>

                                                <div class="form-group">
                                                    <label>Driver ID/PF No</label>
                                                    <input type="text" class="form-control p-input"  required="true" id="driverref"  placeholder="Enter PF Number or ID of person requesting" onfocusout="verifyID()">
                                                </div>

                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Driver Email</label>
                                                    <input type="text" class="form-control p-input"  required="true" id="driveremail"  placeholder="Email of person requesting">
                                                </div>

                                                <div class="form-group">
                                                    <label >Fuel Type</label>
                                                    <input type="text" class="form-control p-input" id="fueltypeid" aria-describedby="emailHelp" placeholder="" >
                                                </div>


                                                <div class="form-group">
                                                    <label >Qty (L)</label>
                                                    <input type="text" class="form-control p-input" id="qtyid" data-validation="text length" data-validation-length="min9" value="" placeholder="Enter qty">
                                                </div>

                                                <div class="form-group">
                                                    <label >Unit Cost (/L)</label>
                                                    <input type="text" class="form-control p-input" id="costid" data-validation="text length" data-validation-length="min9" value="" placeholder="Enter Unit Cost">
                                                </div>


                                                <div class="form-group">
                                                    <!--  <label for="exampleTextarea">Purpose of Transport</label>-->
                                                    <textarea class="form-control p-input" id="exampleTextarea" id="commentsid" rows="5" value="-" placeholder="Type some comments"></textarea> 
                                                </div> 


                                                <div class="form-group">
                                                    <!--  <label for="exampleTextarea">Purpose of Transport</label>
                                                     <textarea class="form-control p-input" id="exampleTextarea" rows="5" value="-" placeholder="Type some comments"></textarea> -->
                                                </div> 




                                                <div class="form-group f103" data-fid="f103">
                                                    <label >Fleet ID/Vehicle Reg</label>


                                                    <select class="form-control" id="vreg" name="vreg" >
                                                        <option  value="">- Select -</option> 
                                                    </select>
                                                </div>


                                                <div class="form-group">
                                                    <label >Card balance</label>
                                                    <input type="text" class="form-control p-input" id="cardbal"  placeholder="0.0" disabled >
                                                </div>



                                                <div class="form-group">
                                                    <!--  <button type="button" id="btnproceed" class="btn btn-primary">Next</button>-->
                                                </div> 
                                                <div  class="form-group">
                                                    <button type="submit" id="btnsavefuel"  class="btn btn-success">Submit Request</button>
                                                    <button type="button" id="configreset" class="btn btn-warning" >Clear Form</button>

                                                </div>
                                                <!-- <div class="form-group">
                                                     <button type="submit" class="btn btn-primary">Submit</button>
                                                 </div>-->
                                            </form>
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
                                    <a href="#">saleem's lair</a> &copy; 2017
                                </span>
                            </div>
                        </footer>
                    </div>
                </div> <!-- partial -->
                <div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='images/spinning-loading-bar.gif' width="64" height="64" /><br>Loading..</div>
            </div>

            <!--End of DIV2-->

            <!--Start of Div 3-->

            <div id="tabs-3" class=" container-scroller">
                <!-- partial:partials/_navbar.html -->
                <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">

                    <div class="navbar-menu-wrapper d-flex align-items-center">
                        <button id="closenav" class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                            <input class="form-control mr-sm-2 search" type="text" placeholder="Search">
                        </form>
                        <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">
                            <li class="nav-item">
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
                            </li>
                        </ul>

                        <button class="navbar-toggler navbar-dark navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                    </div>
                </nav><br><br>


                <!-- partial -->
                <div class="container-fluid">
                    <div class="row row-offcanvas row-offcanvas-right">
                        <!-- partial:partials/_sidebar.html -->


                        <nav class="bg-white sidebar sidebar-offcanvas" id="sidebar">
                            <div class="user-info">

                                <!--   <img src="images/face.jpg" alt="">-->
                                <p id="uName1" class="name">Anonymous User</p>
                                <p id ="rank4"  class="designation">USER</p>
                                <span class="online"></span>
                            </div>
                            <ul class="nav">
                                <li  class="nav-item active">
                                    <a id="trequest" class="nav-link" href="#tabs-1">
                                        <img src="images/icons/1.png" alt="">
                                        <span class="menu-title">Transport Request</span>
                                    </a>
                                </li>


                                <li  class="nav-item">
                                    <a id="frequest" class="nav-link" href="#tabs-2">
                                        <img src="images/icons/009-layout.png" alt="">
                                        <span class="menu-title">Fuel Request</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a id="mrequest" class="nav-link" href="#tabs-3">
                                        <img src="images/icons/005-forms.png" alt="">
                                        <span class="menu-title">Maintenance Request</span>
                                    </a>
                                </li>
                                <!--     <li class="nav-item">
                                         <a class="nav-link" href="#">
                                             <img src="images/icons/001-map.png" alt="">
                                             <span class="menu-title">Maps</span>
                                         </a>
                                     </li>-->

                                <li class="nav-item">
                                    <a id="increport"  class="nav-link" href="#tabs-4">
                                        <img src="images/icons/10.png" alt="">
                                        <span class="menu-title">Incidence Reporting</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <!--Start Content Page-->
                        <div class="content-wrapper">
                            <h3 class="page-heading mb-4">Maintenance Request Form</h3>
                            <a href="/fleet/" class="fa fa-arrow-left">Back to Login</a>

                            <!--End of Row-->
                            <!--Start of Tasks-->
                            <div class="row mb-2">
                                <div class="col-lg-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title mb-4">Details</h5>
                                            <form id="mtcerqform" class="forms-sample" autocomplete="off">

                                                <div class="form-group f103" data-fid="f103">
                                                    <label class="control-label" for="f103">Vehicle Involved</label>
                                                    <select class="form-control" id="vehregid" name="vehregid">
                                                        <option  value="">- Select -</option>                                                   
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label >Current Mileage</label>
                                                    <input type="text" class="form-control p-input" required="true" id="mileageid" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Previous Maintenance</label>
                                                    <input class="form-control p-input" id="dateLastmtc" disabled>
                                                </div>

                                                <div class="form-group" id="searchfield">
                                                    <label >Type of Maintenance</label>
                                                    <input type="text" class="form-control biginput" required="true" id="autocomplete"> 
                                                </div>


                                                <div class="form-group">
                                                    <div class="form-group">
                                                        <label for="exampleInputEmail1">Make</label>
                                                        <input class="form-control p-input" id="makeid" value="-" disabled>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="exampleInputEmail1">Model</label>
                                                        <input class="form-control p-input" id="modelid" disabled>
                                                    </div>

                                                </div>


                                                <div class="form-group"> 

                                                    <div class="table-responsive">



                                                        Service Items:<br>
                                                        <table id="mtceTbl" class="dataTable table table-striped">
                                                            <thead>
                                                                <tr class="text-primary">
                                                                    <th>Service Item</th>
                                                                    <th>Cost</th>

                                                                    <th>Performed By</th>
                                                                    <th></th>                                                    
                                                                </tr>
                                                            </thead>

                                                            <tbody id="mtceTblbody"></tbody>
                                                        </table>

                                                    </div>
                                                </div>




                                                <div class="form-group">


                                                    <!--  <label for="exampleTextarea">Purpose of Transport</label>
                                                     <textarea class="form-control p-input" id="exampleTextarea" rows="5" value="-" placeholder="Type some comments"></textarea> -->
                                                </div> 


                                                <div class="form-group">
                                                    <button id="saveMtcbtn" type="button" class="btn alert-primary" >Save Request</button>

                                                </div> 

                                            </form>
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
                                    <a href="#">saleem's lair</a> &copy; 2017
                                </span>
                            </div>
                        </footer>
                    </div>
                </div> <!-- partial -->
            </div>


            <!--Start of Div 4-->

            <div id="tabs-4" class=" container-scroller">
                <!-- partial:partials/_navbar.html -->
                <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">

                    <div class="navbar-menu-wrapper d-flex align-items-center">
                        <button id="closenav" class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                            <input class="form-control mr-sm-2 search" type="text" placeholder="Search">
                        </form>
                        <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">
                            <li class="nav-item">
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
                            </li>
                        </ul>

                        <button class="navbar-toggler navbar-dark navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                    </div>
                </nav><br><br>


                <!-- partial -->
                <div class="container-fluid">
                    <div class="row row-offcanvas row-offcanvas-right">
                        <!-- partial:partials/_sidebar.html -->


                        <nav class="bg-white sidebar sidebar-offcanvas" id="sidebar">
                            <div class="user-info">

                                <!--   <img src="images/face.jpg" alt="">-->
                                <p id="uName2" class="name">Anonymous User</p>
                                <p id ="rank1"  class="designation">USER</p>
                                <span class="online"></span>
                            </div>
                            <ul class="nav">
                                <li  class="nav-item active">
                                    <a id="trequest" class="nav-link" href="#tabs-1">
                                        <img src="images/icons/1.png" alt="">
                                        <span class="menu-title">Transport Request</span>
                                    </a>
                                </li>


                                <li  class="nav-item">
                                    <a id="frequest" class="nav-link" href="#tabs-2">
                                        <img src="images/icons/009-layout.png" alt="">
                                        <span class="menu-title">Fuel Request</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a id="mrequest" class="nav-link" href="#tabs-3">
                                        <img src="images/icons/005-forms.png" alt="">
                                        <span class="menu-title">Maintenance Request</span>
                                    </a>
                                </li>
                                <!--     <li class="nav-item">
                                         <a class="nav-link" href="#">
                                             <img src="images/icons/001-map.png" alt="">
                                             <span class="menu-title">Maps</span>
                                         </a>
                                     </li>-->

                                <li class="nav-item">
                                    <a id="increport"  class="nav-link" href="#tabs-4">
                                        <img src="images/icons/10.png" alt="">
                                        <span class="menu-title">Incidence Reporting</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <!--Start Content Page-->
                        <div class="content-wrapper">
                            <h3 class="page-heading mb-4">Incidence Report Form</h3>
                            <a href="/fleet/" class="fa fa-arrow-left">Back to Login</a>

                            <!--End of Row-->
                            <!--Start of Tasks-->
                            <div class="row mb-2">
                                <div class="col-lg-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title mb-4">Details</h5>
                                            <form id="basicdataform" class="forms-sample" autocomplete="off">
                                                <div class="form-group">
                                                    <label >Full Name</label>
                                                    <input type="text" class="form-control p-input" id="requestedby" data-validate="required"  placeholder="Enter Full Name of Person Requesting">
                                                </div>

                                                <div class="form-group">
                                                    <label >Ref Number</label>
                                                    <input type="text" class="form-control p-input" required="true" id="refid"  placeholder="Enter PF Number or ID of person requesting">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Email address</label>
                                                    <input type="email" class="form-control p-input" id="emailid" aria-describedby="emailHelp" placeholder="Enter email" data-validation="email required">
                                                </div>


                                                <div class="form-group">
                                                    <label >Tel. No</label>
                                                    <input type="text" class="form-control p-input" id="telno" data-validation="text length" data-validation-length="min9" value="" placeholder="Enter Mobile/Tel No">
                                                </div>


                                                <div class="form-group">
                                                    <div class="form-check">
                                                        <label>
                                                            <input type="checkbox" id="chckmail" class="form-check-input" checked>
                                                            Alert the email above on the progress
                                                        </label>
                                                    </div>

                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request For Hire(Staff)" type="radio">
                                                            Request For Hire(Staff)                                                 
                                                        </label>
                                                        <label> <strong>NB: PF No Will be verified</strong></label>
                                                    </div>
                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request For Hire(External)" type="radio">
                                                            Request For Hire(External)
                                                        </label>
                                                    </div>
                                                    <div class="form-radio">
                                                        <label>
                                                            <input name="rqtype" value="Request for Internal Use" type="radio">
                                                            Request for Internal Use
                                                        </label>
                                                    </div>

                                                </div>


                                                <div class="form-group">

                                                    <div class="table-responsive">
                                                        <!-- <table id="dataTable" class="table center-aligned-table">-->
                                                        Order items:<br>

                                                        <table id="InvItemsTbl" class="dataTable table table-striped">
                                                            <thead>
                                                                <tr class="text-primary">
                                                                    <th>Item</th>
                                                                    <th>UnitCost</th>
                                                                    <th>#</th>
                                                                    <th>Total</th>
                                                                    <th></th>                                                    
                                                                </tr>
                                                            </thead>

                                                            <tbody id="InvItemsTblbody"></tbody>
                                                        </table>

                                                    </div>
                                                </div>




                                                <div class="form-group">

                                                </div> 


                                                <div class="form-group">

                                                </div> 






                                                <div class="form-group">
                                                    <button type="button" id="btnproceed" class="btn btn-primary">Next</button>
                                                </div> 
                                                <div  class="form-group">
                                                    <button type="button" id="btnfinish"  class="btn btn-success">Submit Details/Print Order</button>
                                                    <button type="button" id="resetTbl" class="btn btn-warning" >Reset Table</button>

                                                </div>
                                                <!-- <div class="form-group">
                                                     <button type="submit" class="btn btn-primary">Submit</button>
                                                 </div>-->
                                            </form>
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
                                    <a href="#">saleem's lair</a> &copy; 2017
                                </span>
                            </div>
                        </footer>
                    </div>
                </div> <!-- partial -->
            </div>

            <!--End of Div 4-->



            <!-- Modal Start -->
            <div class="modal fade" id="moddi" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">

                            <h1 id ="RegNoHeader" class="modal-title">Modal Header</h1>&nbsp  &nbsp



                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">

                        </div>
                        <div class="modal-footer">
                            <button type="button" id="delfile1" class="btn btn-outline-warning" data-dismiss="modal">Deactivate File</button> 
                            <button type="button" id="updfile1" class="btn btn-outline-success" data-dismiss="modal">Update File</button>

                            <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Another Modal-->

            <div class="modal fade" id="reqmodal" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">

                            <h1 id ="rqtype" class="modal-title">Modal Header</h1>&nbsp  &nbsp



                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <!--Start Form-->
                            <div class="container jf-form">
                                <!--<form data-licenseKey="" name="jqueryform-9f93e7" id="jqueryform-9f93e7" action='admin.php' method='post' enctype='multipart/form-data' novalidate autocomplete="off">
                                   <input type="hidden" name="method" value="validateForm">
                                    <input type="hidden" id="serverValidationFields" name="serverValidationFields" value="">-->
                                <form data-licenseKey="" name="trnsp" id="trnsp" novalidate autocomplete="off">

                                    <div class="form-group">
                                        <label >Organization Name</label>
                                        <input type="text" class="form-control p-input" id="orgname"  value="MTRH" placeholder="Enter Organization Name">
                                    </div>


                                    <div class="form-group f102" data-fid="f102">
                                        <label class="control-label" for="f102">Department</label>


                                        <select class="form-control" id="f102" name="f102">
                                            <option  value="">- Select -</option> 
                                            <option  > External </option> 
                                            <option  > ICT </option> 


                                        </select>


                                    </div>

                                    <div class="form-group f5" data-fid="f5">
                                        <label class="control-label" for="f5">From</label>


                                        <input type="text" class="form-control" id="txtSource" name="f5" value="Moi Teaching and Referral Hospital. Eldoret, Kenya"     />



                                    </div>




                                    <div class="form-group f6" data-fid="f6">
                                        <label class="control-label" for="f6">Destination</label>


                                        <input type="text" class="form-control" id="txtDestination" name="f6" value=""     />



                                    </div>







                                    <div class="form-group f9" data-fid="f9">
                                        <label class="control-label" for="f9">Approximate Distance</label>


                                        <input type="text" class="form-control" id="f9" name="f9" value=""    
                                               data-rule-number="true"   />


                                    </div>

                                    <div class="form-group f103" data-fid="f103">
                                        <label class="control-label" for="f103">Type of Vehicle Requested</label>


                                        <select class="form-control" id="f103" name="f103"   
                                                >
                                            <option  value="">- Select -</option> 



                                        </select>


                                    </div>

                                    <div class="form-group f10" data-fid="f10">
                                        <label class="control-label" for="f10">Additional items requested</label>


                                        <select class="form-control" id="f10" name="f10"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>

                                    <div class="form-group f9" data-fid="f9">

                                        <input type="text" class="form-control" id="passno" name="passno" value="1"    
                                               data-validation ="number"   />


                                    </div>

                                    <div class="form-group">
                                        <label>

                                            Attach Signed Documentation                                            </label>
                                        <input type="file" class="form-control" id="f20" name="f20[]" data-multiple="true" multiple value=""  multiple      data-showUpload="false" data-showZoom="false"/>
                                    </div>

                                    <div class="form-group f101" data-fid="f101">
                                        <label class="control-label" for="f10">Purpose of Visit</label>


                                        <select class="form-control" id="f101" name="f101"   
                                                >
                                            <option  value="">- Select -</option> 
                                            <option  > Trip </option> 
                                            <option  > Funeral </option> 
                                            <option  > Official Event </option> 
                                            <option  > Other </option> 


                                        </select>

                                    </div>

                                    <div class="form-group">
                                        <label for="exampleTextarea">Remarks/Comments(Other)</label>
                                        <textarea class="form-control p-input" id="exampleTextarea" rows="5"  placeholder="Enter data"></textarea> 
                                    </div> 

                                    <div class="form-group submit f0" data-fid="f0" style="position: relative;">
                                        <label class="control-label sr-only" for="f0" style="display: block;">Submit Button</label>

                                        <div class="progress" style="display: none; z-index: -1; position: absolute;">
                                            <div class="progress-bar progress-bar-striped active" role="progressbar" style="width:100%">
                                                </div>
                                            </div>



                                        </div><div class="clearfix"></div>

                                        <div class="submit">
                                            <p class="error bg-warning" style="display:none;">
                                            Please check the required fields.  </p>
                                    </div>

                                    <div class="clearfix"></div>


                                </form>

                            </div>
                            <!--End Form-->


                        </div>
                        <div class="modal-footer">
                            <button type="button" id="updfile" class="btn btn-lg" data-dismiss="modal">Show Invoice/ Submit</button>

                            <!-- <button type="button" id="close" class="btn btn-default" data-dismiss="modal">Close</button>-->
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade" id="authModal" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">IDENTIFY YOURSELF</h4>
                        </div>
                        <div class="modal-body">


                            <div class="form-group f66" data-fid="f66">
                                <label class="control-label" for="f66">Enter your Name/ID or PF Number</label>


                                <input type="text" class="form-control" id="f66" name="f66" value=""     />



                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <div id="overlay"></div>




            <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
            <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
            <script src="node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
            <script src="js/off-canvas.js"></script>
            <script src="js/hoverable-collapse.js"></script>
            <script src="js/misc.js"></script>

            <script src="js/vendor.js"></script>

            <script src="js/jquery.form-validator.js"></script>


            <!-- Latest compiled and minified JavaScript -->

            <!-- (Optional) Latest compiled and minified JavaScript translation files -->


            <!--Data Tables-->


            <div class="modal fade" id="searchexistingreq" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <!-- <h4 class="modal-title">Existing Requests</h4>-->
                            <input id="searchb" type="text" class="live-search-box" placeholder="search existing request here" autofocus onkeyup="showResult(this.value)" />
                            <!--<button type="button" class="close" data-dismiss="modal">&times;</button>-->

                        </div>
                        <div class="modal-body">

                            <div class="table-responsive">
                                <!-- <table id="dataTable" class="table center-aligned-table">-->
                                Existing requests:<br>

                                <table id="searchtbl" class="dataTable table table-striped">
                                    <thead>
                                        <tr class="text-primary">
                                            <th>RequestID</th>
                                            <th>RequestedBy</th>
                                            <th>Requested On</th>
                                            <th></th>                                                    
                                        </tr>
                                    </thead>

                                    <tbody id="searchtblbody"></tbody>
                                </table>

                            </div>


                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="mtcemodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Vehicle Maintenance</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">


                            <div class="form-group">
                                <label >Request Type</label>
                                <input type="text" class="form-control p-input" required="true" id="rqtypeid"  disabled>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                    <strong>Mileage: </strong> <input type="text" class="form-control" id="mileageserv"   autocomplete="off" required>
                                </div>
                                <div class="col-sm">
                                    <strong>Date of Service </strong>  <input type="text" class="form-control" id="dateofservice"   autocomplete="off" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm">
                                    <strong>Cost:</strong> <input type="text" class="form-control" id="costid"   autocomplete="off" required>
                                </div>
                                <div class="col-sm">
                                    <strong>Performed By </strong>  <input type="text" class="form-control" id="mechanicid"   autocomplete="off" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm">
                                    <strong>Notes: </strong>  <input type="text" class="form-control" id="notes"   autocomplete="off" required>
                                </div>
                            </div>




                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>



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
            .live-search-box {
                width: 100%;
                display: block;
                padding: 1em;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                border: 1px solid #3498db;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
            }


            #searchfield { display: block; width: 100%; text-align: center; margin-bottom: 35px; }

            #searchfield form {
                display: inline-block;
                background: #eeefed;
                padding: 0;
                margin: 0;
                padding: 5px;
                border-radius: 3px;
                margin: 5px 0 0 0;
            }
            #searchfield form .biginput {
                width: 100%;
                height: 40px;
                padding: 0 10px 0 10px;
                background-color: #fff;
                border: 1px solid #c8c8c8;
                border-radius: 3px;
                color: #aeaeae;
                font-weight:normal;
                font-size: 1.5em;
                -webkit-transition: all 0.2s linear;
                -moz-transition: all 0.2s linear;
                transition: all 0.2s linear;
            }
            #searchfield form .biginput:focus {
                color: #858584;
            }
            .flatbtn {
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                display: inline-block;
                outline: 0;
                border: 0;
                color: #f3faef;
                text-decoration: none;
                background-color: #6bb642;
                border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
                font-size: 1.2em;
                font-weight: bold;
                padding: 12px 22px 12px 22px;
                line-height: normal;
                text-align: center;
                vertical-align: middle;
                cursor: pointer;
                text-transform: uppercase;
                text-shadow: 0 1px 0 rgba(0,0,0,0.3);
                -webkit-border-radius: 3px;
                -moz-border-radius: 3px;
                border-radius: 3px;
                -webkit-box-shadow: 0 1px 0 rgba(15, 15, 15, 0.3);
                -moz-box-shadow: 0 1px 0 rgba(15, 15, 15, 0.3);
                box-shadow: 0 1px 0 rgba(15, 15, 15, 0.3);
            }
            .flatbtn:hover {
                color: #fff;
                background-color: #73c437;
            }
            .flatbtn:active {
                -webkit-box-shadow: inset 0 1px 5px rgba(0, 0, 0, 0.1);
                -moz-box-shadow:inset 0 1px 5px rgba(0, 0, 0, 0.1);
                box-shadow:inset 0 1px 5px rgba(0, 0, 0, 0.1);
            }
            .autocomplete-suggestions { border: 1px solid #999; background: #fff; cursor: default; overflow: auto; }
            .autocomplete-suggestion { padding: 20px 5px; font-size: 1.2em; white-space: nowrap; overflow: hidden; }
            .autocomplete-selected { background: #f0f0f0; }
            .autocomplete-suggestions strong { font-weight: normal; color: #3399ff; }

        </style>
        <script src = "js/searchautocomplete.js"></script>
        <script src="js/TransportRequest.js"></script>

        <script src="js/qrcode.js"></script>





        <script src = "js/html2canvas.js"></script>


    </body>

</html>
