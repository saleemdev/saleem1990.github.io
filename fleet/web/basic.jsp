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
        <title>Dashboard</title>
        <link rel="stylesheet" href="node_modules/font-awesome/css/font-awesome.min.css" />
        <link rel="stylesheet" href="node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css" />
        <link rel="stylesheet" href="css/style.css" />

        <script src="js/jquery-1.12.4.js"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="js/sweetalert.js"></script>  
        <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
        <!--JQuery Datatables css-->
        <link rel="stylesheet" href="css/jQuery.dataTables.css" />
        <link rel="shortcut icon" href="images/favicon.png" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="node_modules/jquery/dist/jquery.min.js"></script>
        <script src="js/jQuery.dataTables.js"></script>    


    </head>
    <body>
        <div class=" container-scroller">
            <!-- partial:partials/_navbar.html -->
            <nav class="navbar navbar-default col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
                <div class="bg-white text-center navbar-brand-wrapper">
                    <!--<a class="navbar-brand brand-logo" href="index2.html"><img src="images/logo_star_black.png" /></a>
                    <a class="navbar-brand brand-logo-mini" href="index2.html"><img src="images/logo_star_mini.jpg" alt=""></a>-->
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-center">
                    <button class="navbar-toggler navbar-toggler d-none d-lg-block navbar-dark align-self-center mr-3" type="button" data-toggle="minimize">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <form class="form-inline mt-2 mt-md-0 d-none d-lg-block">
                        <input class="form-control mr-sm-2 search" type="text" placeholder="Search">
                    </form>
                    <ul class="navbar-nav ml-lg-auto d-flex align-items-center flex-row">
                        <li class="nav-item">
                            <a id="profilePic" class="nav-link profile-pic" href="#"><img class="rounded-circle" src="images/face.jpg" alt=""></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"><i class="fa fa-th"></i></a>
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
                            <img src="images/face.jpg" alt="">
                            <p id="uName" class="name">Richard V.Welsh</p>
                            <p id ="rank"  class="designation">Manager</p>
                            <span class="online"></span>
                        </div>
                        <ul class="nav">
                            <li class="nav-item active">
                                <a class="nav-link" href="dashboard.jsp">
                                    <img src="images/icons/1.png" alt="">
                                    <span class="menu-title">Dashboard</span>
                                </a>
                            </li>
                            <li class="nav-item active">
                                <a data-toggle="modal" data-target="#newVehicleModal" class="nav-link" >
                                    <img src="images/icons/006-form.png" alt="">
                                    <span class="menu-title">Transport Request</span>


                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <img src="images/icons/009-layout.png" alt="">
                                    <span class="menu-title">Authority to Fuel</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="pages/forms/index.html">
                                    <img src="images/icons/005-forms.png" alt="">
                                    <span class="menu-title">Work Orders</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="pages/ui-elements/buttons.html">
                                    <img src="images/icons/001-map.png" alt="">
                                    <span class="menu-title">Maps</span>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <img src="images/icons/10.png" alt="">
                                    <span class="menu-title">Settings</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                    <!--Start Content Page-->
                    <div class="content-wrapper">
                        <h3 class="page-heading mb-4">Dashboard</h3>
                        
                        <!--End of Row-->
                        <!--Start of Tasks-->
                        <div class="container jf-form">
                            <!--<form data-licenseKey="" name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='admin.php' method='post' enctype='multipart/form-data' novalidate autocomplete="on">
                             <form  name="jqueryform-7ae31c" id="jqueryform-7ae31c" action='VehicleServlet' method='GET' enctype='multipart/form-data' novalidate autocomplete="on">-->
                            <form  name="regVehicle" id="regVehicle"   autocomplete="off">
                               


                                <div class="form-group f17 " data-fid="f17">
                                    <label class="control-label" for="f17">General Details</label>



                                </div>




                                <div class="form-group f5 " data-fid="f5">
                                    <label class="control-label" for="f5">Reg no</label>


                                    <input type="text" class="form-control" id="f5" name="f5" value=""     
                                           data-mask="SSS ###S" />



                                </div>

                                <div class="form-group f26 " data-fid="f26">
                                    <label class="control-label" for="f26">Date of Registration</label>

                                    <div class="input-group date">

                                        <p><input type="text" id="datepicker"></p>

                                        <!--   <input type="text" class="form-control datepicker" id="f26" name="f26" value=""     />
                                           <span class="input-group-addon right"><i class="glyphicon glyphicon-th"></i> </span></div> -->

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




                                    <div class="form-group f10 " data-fid="f10">
                                        <label class="control-label" for="f10">Year Of Manufacture</label>


                                        <select class="form-control" id="f10" name="f10"   
                                                >
                                            <option  value="">- Select -</option> 


                                        </select>


                                    </div>




                                    <div class="form-group f22 " data-fid="f22">
                                        <label class="control-label" for="f22">Engine CC</label>


                                        <input type="text" class="form-control" id="f22" name="f22" value=""   placeholder="e.g 1500 cc"  />



                                    </div>




                                    <div class="form-group f23 " data-fid="f23">
                                        <label class="control-label" for="f23">Fuel Used</label>


                                        <input type="text" class="form-control" id="f23" name="f23" value=""   placeholder="e.g Diesel, Petrol, Gasoline etc"  />



                                    </div>




                                    <div class="form-group f20 " data-fid="f20">
                                        <label class="control-label" for="f20">Vehicle Images</label>

                                        <input type="file" class="form-control" id="f20" name="f20[]" data-multiple="true" multiple value=""        data-showUpload="false" data-showZoom="false"/>

                                    </div>




                                    <div class="form-group f21 " data-fid="f21">
                                        <label class="control-label" for="f21">Current Mileage *(Kilometers as at present)</label>


                                        <input type="text" class="form-control " id="f21" name="f21" value="0"    
                                               data-rule-number="true"   />


                                    </div>




                                    <div class="form-group f24 " data-fid="f24">
                                        <label class="control-label" for="f24">Chassis No</label>


                                        <input type="text" class="form-control" id="f24" name="f24" value=""     />



                                    </div>




                                    <div class="form-group f25 " data-fid="f25">
                                        <label class="control-label" for="f25">Engine No</label>


                                        <input type="text" class="form-control" id="f25" name="f25" value=""     />



                                    </div>



                                    <div class="form-group submit f0 " data-fid="f0" style="position: relative;">
                                        <label class="control-label sr-only" for="f0" style="display: block;">Submit Button</label>

                                        <div class="progress" style="display: none; z-index: -1; position: absolute;">
                                            <div class="progress-bar progress-bar-striped active" role="progressbar" style="width:100%">
                                            </div>
                                        </div>

                                        <button id= "submitveh" type="submit" class="btn btn-primary btn-lg" style="z-index: 1;">
                                            Submit Data
                                        </button>

                                    </div><div class="clearfix"></div>

                                    <div class="submit">
                                        <p class="error bg-warning" style="display:none;">
                                            Please check the required fields.  </p>
                                    </div>

                                    <div class="clearfix"></div>





                                </div>
                            </form>
                            <!--End-->

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
        <div class="modal fade" id="newVehicleModal" role="dialog" >
                <div class="modal-dialog" >
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                             <h4 class="modal-title">Register Vehicle</h4>
                             
                             <form>
                                 
                             </form>
                           
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



        <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
        <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
        <script src="js/off-canvas.js"></script>
        <script src="js/hoverable-collapse.js"></script>
        <script src="js/misc.js"></script>
        <!--Data Tables-->
        
        <script src="js/DashboardInfo.js"></script>

        <script src="js/bootstrap-datepicker.min.js"></script>


        <script src="js/jquery-validate.min.js"></script>
        <script src="js/jqueryform.com.min.js"></script>
        <script src="js/additional-methods.min.js"></script>
        <script src="js/vendor.js"></script>  
        <script src="js/jqf.js"></script>  
        <script src="js/jquery-ui.js"></script>   



        
        <script type="text/javascript" src="js/VehicleJS.js"></script> 
        


    </body>

</html>
