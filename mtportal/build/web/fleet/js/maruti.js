
var approvlevel = "";
$(document).ready(function () {



    // === Sidebar navigation === //
   // window.setInterval(ajaxd, 10000);

    window.setInterval(validate("SENIOR MANAGEMENT"), 10000);
    window.setInterval(validate("TRANSPORT MANAGER"), 10000);

    $('.submenu > a').click(function (e)
    {
        e.preventDefault();
        var submenu = $(this).siblings('ul');
        var li = $(this).parents('li');
        var submenus = $('#sidebar li.submenu ul');
        var submenus_parents = $('#sidebar li.submenu');
        if (li.hasClass('open'))
        {
            if (($(window).width() > 768) || ($(window).width() < 479)) {
                submenu.slideUp();
            } else {
                submenu.fadeOut(250);
            }
            li.removeClass('open');
        } else
        {
            if (($(window).width() > 768) || ($(window).width() < 479)) {
                submenus.slideUp();
                submenu.slideDown();
            } else {
                submenus.fadeOut(250);
                submenu.fadeIn(250);
            }
            submenus_parents.removeClass('open');
            li.addClass('open');
        }
    });

    var ul = $('#sidebar > ul');

    $('#sidebar > a').click(function (e)
    {
        e.preventDefault();
        var sidebar = $('#sidebar');
        if (sidebar.hasClass('open'))
        {
            sidebar.removeClass('open');
            ul.slideUp(250);
        } else
        {
            sidebar.addClass('open');
            ul.slideDown(250);
        }
    });

    // === Resize window related === //
    $(window).resize(function ()
    {
        if ($(window).width() > 479)
        {
            ul.css({'display': 'block'});
            $('#content-header .btn-group').css({width: 'auto'});
        }
        if ($(window).width() < 479)
        {
            ul.css({'display': 'none'});
            fix_position();
        }
        if ($(window).width() > 768)
        {
            $('#user-nav > ul').css({width: 'auto', margin: '0'});
            $('#content-header .btn-group').css({width: 'auto'});
        }
    });

    if ($(window).width() < 468)
    {
        ul.css({'display': 'none'});
        fix_position();
    }
    if ($(window).width() > 479)
    {
        $('#content-header .btn-group').css({width: 'auto'});
        ul.css({'display': 'block'});
    }

    // === Tooltips === //
    $('.tip').tooltip();
    $('.tip-left').tooltip({placement: 'left'});
    $('.tip-right').tooltip({placement: 'right'});
    $('.tip-top').tooltip({placement: 'top'});
    $('.tip-bottom').tooltip({placement: 'bottom'});

    // === Search input typeahead === //
    $('#search input[type=text]').typeahead({
        source: ['Dashboard', 'Form elements', 'Common Elements', 'Validation', 'Wizard', 'Buttons', 'Icons', 'Interface elements', 'Support', 'Calendar', 'Gallery', 'Reports', 'Charts', 'Graphs', 'Widgets'],
        items: 4
    });

    // === Fixes the position of buttons group in content header and top user navigation === //
    function fix_position()
    {
        var uwidth = $('#user-nav > ul').width();
        $('#user-nav > ul').css({width: uwidth, 'margin-left': '-' + uwidth / 2 + 'px'});

        var cwidth = $('#content-header .btn-group').width();
        $('#content-header .btn-group').css({width: cwidth, 'margin-left': '-' + uwidth / 2 + 'px'});
    }

    // === Style switcher === //
    $('#style-switcher i').click(function ()
    {
        if ($(this).hasClass('open'))
        {
            $(this).parent().animate({marginRight: '-=190'});
            $(this).removeClass('open');
        } else
        {
            $(this).parent().animate({marginRight: '+=190'});
            $(this).addClass('open');
        }
        $(this).toggleClass('icon-arrow-left');
        $(this).toggleClass('icon-arrow-right');
    });

    $('#style-switcher a').click(function ()
    {
        var style = $(this).attr('href').replace('#', '');
        $('.skin-color').attr('href', 'css/maruti.' + style + '.css');
        $(this).siblings('a').css({'border-color': 'transparent'});
        $(this).css({'border-color': '#aaaaaa'});
    });

    $('.lightbox_trigger').click(function (e) {

        e.preventDefault();

        var image_href = $(this).attr("href");

        if ($('#lightbox').length > 0) {

            $('#imgbox').html('<img src="' + image_href + '" /><p><i class="icon-remove icon-white"></i></p>');

            $('#lightbox').slideDown(500);
        } else {
            var lightbox =
                    '<div id="lightbox" style="display:none;">' +
                    '<div id="imgbox"><img src="' + image_href + '" />' +
                    '<p><i class="icon-remove icon-white"></i></p>' +
                    '</div>' +
                    '</div>';

            $('body').append(lightbox);
            $('#lightbox').slideDown(500);
        }

    });

    $('#lightbox').live('click', function () {
        $('#lightbox').hide(200);
    });



    $('#transprqid').click(function () {
     //   swal("Not yet deployed")
     console.log("I am here")
     $('#flrqid').modal({
         backdrop:'static',
         keyboard:false
     });
    });


    $('#trmgrapprid').click(function () {
        window.approvlevel = "TRANSPORT APPROVAL";
        populateWaitingList('TRANSPORT APPROVAL')
    });
//--//
    $('#snrapprid').click(function () {
        window.approvlevel = "SENIOR MANAGEMENT";
        populateWaitingList('SENIOR MANAGEMENT')
    });



    $('#apprvid').click(function () {
        $.ajax({
            type: 'POST',
            url: '../CheckApproval',
            dataType: "text",
            data: "rqtype=no",
            success: function (data) {
                if (data.toString().includes("YES")) {



                    $('#realapprovalid').modal({
                        keyboard: false,
                    });

                } else {
                    swal("Sorry, you are not authorized to approve transport requests\n")
                }

            }
        });
    });


    $('#fuelid').click(function () {
        swal("Not yet deployed")
    });
    $('#mtceid').click(function () {
        swal("Not yet deployed")
    });
    $('#incidencerpt').click(function () {
        swal("Not yet deployed")
    });

    $('#apprvdeployid').click(function () {
        if ($('#vehselid').val().toString().length < 2 || $('#drivselid').val().toString().length < 2) {
            alert("Driver and vehicle selected cannot be empty")
        } else {
            var arr_concat = $('#titleid').text().toString().split("_");
            var refid = arr_concat[1];
            var transtype = "";

            if (window.approvlevel.toString().includes("TRANSPORT")) {
                transtype = "APPROVE TRANSPORT";
            } else {
                transtype = "AUTHORIZE REQUEST";
            }
            // console.log(refid + ", " + transtype)

            //   alert(refid+"---"+transtype);

            updateReq(refid, transtype);



        }
    })

});

function ajaxd() {

    console.log("Reloading dash")
    $.ajax({
        type: 'GET',
        url: '../ActiveSession',
        dataType: "text",
        success: function (data) {
            if (data === "active") {
                //  dashboard();

            } else {
                window.location.href = "/mtportal/"
                //window.location.reload(1);
                //    swal("uLELE");

            }

        }, error: function (data) {
            console.log("Error: " + data)
            window.location.href = "/mtportal/"
        }
    });



}


function populateWaitingList(level) {
    $.ajax({
        type: 'GET',
        url: '../CheckApproval',
        data: "level=" + level,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.request_id + '</td>';
                activitydata += '<td>' + value.request_type + '</td>';
                //   activitydata += '<td><strong>Entered on:</strong>' + value.requestedon + '<br>Requested by: '+value.requestedby+'<br>From: '+value.from+'<br>Destination: '+value.to+'<br>Purpose: '+value.purpose+'</td>';

                activitydata += '<td><strong>Entered on:</strong>' + value.requestedon + '<br><strong>Requested by:</strong> ' + value.requestedby + '<br><strong>Purpose: </strong>' + value.purpose + '<br><button onclick=printRequest(this) class="btn btn-link">View Details</button></td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                /*                var stat = value.status;
                 if (stat === "approved") {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-success">' + value.status + '</button></td>'
                 } else if (stat === "rejected") {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-danger">' + value.status + '</button></td>'
                 } else {
                 activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-warning">' + value.status + '</button></td>'
                 }
                 
                 */
                activitydata += '<td ><button onclick="approvereq(this)" class="btn btn-success">Approve</button><br><br><button onclick="rejectreq(this)" class="btn btn-danger"> Reject </button><br></td>'

                /*activitydata += '<td ><button onclick="approvereq(this)" class="btn btn-success">Approve</button><br><br><button onclick="rejectreq(this)" class="btn btn-danger"> Reject </button><br><br><button onclick="addcomment(this)" class="btn btn-warning fa-envelope"> Add/View comments </button></td>'*/

//  activitydata += '<td ><button onclick="preview(this,2)" class="btn btn-danger">Reject</button></td>'

                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#pendingidtbltbd').empty().append(activitydata);

        }
    });
}

function rejectreq(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var rank = window.approvlevel;

    var typeofreq = tbl.rows[row].cells[1].innerHTML;

    var transtype = "";

    $('#closeformodal').click();
    swal({
        title: "Are you sure to reject? Please confirm before proceeding",
        text: "Request type: " + typeofreq + "\nRef Number:" + cellvalue,
        icon: "info",
        buttons: true,
        dangerMode: true,
    })
            .then((willDelete) => {
                if (willDelete) {
                    if (window.approvlevel.toString().includes("TRANSPORT")) {
                        transtype = "DECLINE TRANSPORT";
                    } else {
                        transtype = "DECLINE REQUEST";
                    }

                    updateReq(cellvalue, transtype)

                    //saveEntitleMent(request, pf, "");
                } else {
                    swal("Transaction aborted!");
                }
            });
}
function approvereq(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var rank = window.approvlevel;

    var typeofreq = tbl.rows[row].cells[1].innerHTML;

    var transtype = "";
    if (cellvalue.toString().includes("/TR/") && window.approvlevel.toString().includes("TRANSPORT")) {
        freev();
        drivers();
        $('#titleid').text(typeofreq + "_" + cellvalue);
        $('#realtransportappr').modal({
            keyboard: false,

        })
    } else {
        $('#closeformodal').click();
        swal({
            title: "Please Confirm this Information to be correct before clicking [OK]: ",
            text: "Request type: " + typeofreq + "\nRef Number:" + cellvalue,
            icon: "info",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {
                        if (window.approvlevel.toString().includes("TRANSPORT")) {
                            transtype = "APPROVE TRANSPORT";
                        } else {
                            transtype = "AUTHORIZE REQUEST";
                        }

                        console.log(cellvalue + ", " + transtype);
                        updateReq(cellvalue, transtype)

                        //saveEntitleMent(request, pf, "");
                    } else {
                        swal("Transaction aborted!");
                    }
                });
    }

    //swal(cellvalue+" "+rank);

}
function printRequest(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingidtbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    //var level = window.approvalLevel;
    swal(cellvalue);
    print(cellvalue)

}

function print(reqid) {
    $.ajax({
        type: 'GET',
        url: '../HostIP',
        // data: request,
        dataType: "text",

        success: function (data) {
            console.log(data);
            var ip = data;
            // var reqid = window.rqNo
            var doctype = "";
            if (reqid.toString().includes("/TR/")) {
                doctype = "REQUEST FOR TRANSPORT";
            } else if (reqid.toString().includes("/MTR/")) {
                doctype = "REQUEST FOR MAINTENANCE";
            } else if (reqid.toString().includes("/FR/")) {
                doctype = "REQUEST FOR FUEL";
            }



            var trans = "pdfname=" + doctype + "&refno=" + reqid;

            // swal(trans)
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
            //  return data;
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function validate(level) {

    var rank = level;
    // window.approvlevel = rank;

    $.ajax({
        type: 'POST',
        url: '../CheckApproval',
        dataType: "text",
        data: "rqtype=validate&level=" + rank,
        success: function (data) {

            if (rank.toString().includes("SENIOR")) {
                if (data.toString().includes("YES")) {


                    $('#snrapprid').prop("disabled", false);

                } else {
                    $('#snrapprid').prop("disabled", true);
                }
            } else {

                if (data.toString().includes("YES")) {
                    console.log("It is a YES for transport")

                    $('#trmgrapprid').prop("disabled", false);

                } else {
                    console.log("It is a NO for transport")
                    $('#trmgrapprid').prop("disabled", true);
                }
            }

        }
    });
}

function updateReq(reqid, transtype) {
    // $("#overlay").css("display", "block");

    var vehreq = "";

    var vehissued = $('#vehselid').val();
    var driver = $('#drivselid').val();
    var officer = "";

    var request = 'transtype=' + transtype + '&rqid=' + reqid + '&vehreq=' + vehreq + '&vehissued=' + vehissued + '&driver=' + driver + '&officer=' + officer;
    var req = reqid;
    $.ajax({
        type: 'GET',
        url: '../TrequestApproval',
        data: request,
        dataType: "text",

        success: function (data) {
            var response = data;

            // swal(transtype);

            if (transtype.toString() == "PRINT TICKET") {
                var path = response.toString().trim();

                //  changePath(path);
                document.getElementById("embedpath").src = path;

                var output = document.getElementById("embedpath").src;
                //$('#embedpath').EZView(); 
                print(reqid);



            } else {
                $('#trmgrapprid').click();
                $('#snrapprid').click();
                $('#apprvid').click();
                swal({icon: "success",
                    title: "Done successfully...", response});
            }

            // populateActivities();
        },

        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
    //  $("#overlay").css("display", "none");

}
function freev() {
    $.ajax({

        type: 'POST',
        url: '../VehUserData',
        data: 'transtype=free_v', //free_v
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //   $('#vehreq1Av').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#vehselid').append('<option value="' + value.vehicle + '">' + value.vehicle + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function drivers() {
    $.ajax({

        type: 'POST',
        url: '../VehUserData',
        data: 'transtype=drivers', //free_v
        dataType: "json",
        success: function (data) {

            var dataset = data;
            //   $('#vehreq1Av').empty().append('<option value="">--Select--</option>');
            $.each(dataset, function (key, value) {
                $('#drivselid').append('<option value="' + value.drivers + '">' + value.drivers + '</option>');
                //     i++;
            });
        },
        error: function (xhr, err) {
            swal('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}