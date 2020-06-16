
var maximumScore = 0;
var scoredPoints = 0;
var loggedinuser = "";
var msgrefno = "";
var pfinq = ""
var fulldetails = "";
$(document).ready(function () {


//msgTrail

    //   ajaxd();

    window.setInterval(ajaxd, 10000);


//    $('#q1').on("change", function () {
//// alert($('#lvtypeselectid').val());
//
//
//        var selectedLvtype = $('#q1').val();
//        //var index = $("lvtypeselectid").index(this);
//        alert(selectedLvtype)
//    });




    $('#pendingbtnid').click(function () {
        populateWaitingList();
        $('#approvalfrm').modal({
            backdrop: 'static',
            keyboard: false
        });
    })


    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

        $('#msgTrail').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });


    $('#sendmsgbtn').click(function () {
        sendMsg();


    });

    $('#newappraisalid').click(function () {


        var fulldet = window.fulldetails;
        $('#closerqid2').click();
        var name = fulldet.toString().split("/")[0] + "/" + fulldet.toString().split("/")[1]
        $('#appraisseeid').text(name);
        $('#appraisalform').modal({
            backdrop: 'static',
            keyboard: false
        });

    });

    $('#pdf').on('click', function () {
        $("#MsgTrailTbl").tableHTMLExport({type: 'pdf', filename: 'sample.pdf'});
    });

    $('#csv').on('click', function () {
        $("#MsgTrailTbl").tableHTMLExport({type: 'csv', filename: 'sample.csv'});
    });





    // === Sidebar navigation === //

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
    dashboard();
    populateDetails();

    $('#reqbtnid').click(function () {
        $('#closerqid').click();

        var appraisse = $('#appraisseeid').text();
        var staffid = appraisse.toString().split("/")[1];

        var date1 = $('#fromdateid').val();
        var date2 = $('#todateid').val();
        var message = $('#commentsdef').val();
        var typeofappraisal = $('#typeofappraisalid').val();
        var request = "staffid=" + staffid + "&date1=" + date1 + "&date2=" + date2 + "&message=" + message + "&appraisaltype=" + typeofappraisal

        console.log(request);
        $.ajax({
            type: 'POST',
            url: 'Appraisal',
            data: request,
            dataType: "text",
            success: function (data) {
                if (data.toString().includes("FAILED")) {
                    swal({
                        icon: "warning",
                        title: data

                    });
                } else {
                    swal({
                        icon: "success",
                        title: "Appraisal form activated successfully for " + data + ""



                    });
                    dashboard();
                    populateDetails();

                }
            },
            error: function (xhr, err) {

                swal({
                    icon: "warning",
                    title: "Error sending data to the server",
                    text: "Reload page or seek for assistance from the Systems Administrator"
                });
            }
        });
    })

    $('#reloadid').click(function () {
        // alert("Clicked");
        dashboard();
        populateDetails();
    });
    var dates2 = $('#todateid').datepicker({
        autoclose: true,
        // minDate: dateToday,
        format: 'yyyy/mm/dd',
        //  minDate: dateToday,

    });

    var dates3 = $('#fromdateid').datepicker({
        autoclose: true,
        // minDate: dateToday,
        format: 'yyyy/mm/dd',
        //  minDate: dateToday,

    });
});
function dashboard() {
    //output 1. Number of staff, 2. Departments , 3. Sections, 4. Files Pending approval
    // $("#deptDropDown").empty();
    console.log("Loading dash...")
    $.ajax({
        type: 'GET',
        url: 'Staffbio',

        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {
                console.log("data here " + value.deptno.toString());
                $('#nostaffid').text(value.staffno);
                $('#nodeptid').text(value.deptno);
                $('#nosecid').text(value.secno);
                $('#noapprid').text(value.pendingno);
                window.loggedinuser = value.loguser;
            });

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });

}


function populateDetails() {
    //output 1. Pfno, 2. Staffname, Designation, Department, Section, Edit Button


    // $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'POST',
        url: 'Staffbio',

        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.staffid + '</td>';
                activitydata += '<td>' + value.fullname + '</td>';

                activitydata += '<td>' + value.designation + '</td>';
                activitydata += '<td>' + value.department + '</td>';
                activitydata += '<td>' + value.section + '</td>';

                activitydata += '<td>  <button onclick="view(this)" class="btn btn-mini"><i class="icon-folder-open"></i> Open </button></td>';


                var action = value.status.toString().toUpperCase();
                console.log(action)
                if (action.toString().includes("PROGRESS")) {
                    activitydata += '<td>  <button onclick="appraise(this)" class="btn btn-mini btn-warning"><i class="icon-asterisk"></i>' + action + '</button></td>';
                } else {
                    activitydata += '<td>  <button onclick="appraise(this)" class="btn btn-mini btn-info"><i class="icon-book"></i> ' + action + ' </button></td>';
                }

                activitydata += '<td>  <button onclick="appraisals(this)" class="btn btn-mini btn-link"><i class="icon-file"></i>Appraisal History</button></td>';



                activitydata += '</tr>';
                i += 1;
            });

            $('#msgTrail').empty().append(activitydata);

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });




}

function appraisals(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("MsgTrailTbl");

    var col = 0;//Staffid
    var cellvalue = tbl.rows[row].cells[col].innerHTML;


    col = 1;//Staffid
    var fullname = tbl.rows[row].cells[col].innerHTML;

    alert("You selected: " + fullname + "/" + cellvalue);
    $('#titlehistid').text(fullname + "/" + cellvalue)

    populateAppraisalHistorie(cellvalue);

    $('#realopenmod').modal({
        backdrop: 'static',
        keyboard: false
    })

}


function populateAppraisalHistorie(staffid) {
    //output 1. Pfno, 2. Staffname, Designation, Department, Section, Edit Button


    // $("#deptDropDown").empty();

    $.ajax({
        type: 'GET',
        url: 'AppraisalHistorie',
        data: "pf=" + staffid,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            //03:26:13,307 ERROR [stderr] (default task-8) I am here names [{"period":"Jul 03, 2019 to Jul 27, 2019","refid":"7115JII","type":"Performance Improvement Plan(PIP)"}]

            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.refid + '</td>';//1

                activitydata += '<td>' + value.type + '</td>';//2


                activitydata += '<td>' + value.period + '</td>';//2



                activitydata += '<td>  <button onclick="printapprpdf(this)" class="btn btn-mini btn-link"><i class="icon-file"></i>Open</button></td>';//6



                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#apprhisttbl1bd').empty().append(activitydata);

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });




}

function printapprpdf(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("apprhisttbl1");

    var col = 0;//Refid
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    print2(cellvalue, "APPRAISALPDF");
}
function populateWaitingList() {
    //output 1. Pfno, 2. Staffname, Designation, Department, Section, Edit Button


    // $("#deptDropDown").empty();
    console.log("Getting appraisallist")
    $.ajax({
        type: 'GET',
        url: 'AppraisalList',

        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.staffname + '</td>';//1

                activitydata += '<td>' + value.type + '</td>';//2




                var lvl1 = value.level1.toString().toUpperCase();
                var lvl2 = value.level2.toString().toUpperCase();
                var lvl3 = value.level3.toString().toUpperCase();

                // console.log(value.staffname+" "+lvl1+" "+lvl2)


                if (lvl1.toString().includes("ACTIONED")) {
                    activitydata += '<td><span class="label label-primary">' + lvl1 + '</span></td>'; //5
                } else {
                    activitydata += '<td><span class="label label-warning">' + lvl1 + '</span></td>';//5
                }//3
                //                                
                if (lvl2.toString().includes("ACTIONED")) {
                    activitydata += '<td><span class="label label-primary">' + lvl2 + '</span></td>'; //5
                } else {
                    activitydata += '<td><span class="label label-warning">' + lvl2 + '</span></td>';//5
                }//4

                //


                if (lvl3.toString().includes("ACTIONED")) {
                    activitydata += '<td><span class="label label-primary">' + lvl3 + '</span></td>'; //5
                } else {
                    activitydata += '<td><span class="label label-warning">' + lvl3 + '</span></td>';//5
                }//5

                activitydata += '<td>  <button onclick="openfile(this)" class="btn btn-mini btn-link"><i class="icon-file"></i>Open</button></td>';//6



                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#pendingfilestblbd').empty().append(activitydata);

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });




}



function view(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("MsgTrailTbl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    var pfnumber = cellvalue.toString();

    col = 1;

    var name = tbl.rows[row].cells[col].innerHTML;



    $('#modaltitid').text("Online Staff Documentation Form - " + name + "/" + cellvalue);

    //populateStaffBio(pfnumber, "");

    populateAllDocs(pfnumber);

    $('#fullnameid').val(name);





    // $('#taba').prop("disaled", value);

    $('#approvalfrm').modal({
        backdrop: 'static',
        keyoard: false
    });

    $('#tabd').click();

    setprop(true);

    $('#refno').val(pfnumber + "" + makeid(5))
}


function setprop(value) {
    $('#taba').prop("disabled", value);
    $('#tabb').prop("disabled", value);
    $('#tabc').prop("disabled", value);
    $('#tabmsgid').prop("disabled", value);
    $('#tabf').prop("disabled", value);

}
function openfile(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("pendingfilestbl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    var pfnumber = cellvalue.toString().split("/")[1];
    var name = cellvalue.toString().split("/")[0];
    var refid = cellvalue.toString().split("/")[2];

    $('#fullnameid').val(name);
    $('#appraiseename').text(cellvalue);

    $('#modaltitid').text("Online Staff Appraisal Form - " + cellvalue);

    $('#refno').val(refid)
    // $('#refno').text(refid)

    window.fulldetails = cellvalue;
    //alert($('#refno').val());

    populateStaffBio(pfnumber, refid);

    //     alert(pfnumber+"---"+cellvalue.toString().split("/")[0]);


}
function appraise(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("MsgTrailTbl");

    var col = 1;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;


    col = 6;//Appraisal Status
    var appraisalstat = tbl.rows[row].cells[col].innerHTML;



    col = 0; //Staff ID
    var staffid = tbl.rows[row].cells[col].innerHTML;

    var stat = "";
    if (appraisalstat.toString().includes("PROGRESS")) {
        stat = "PENDING PROCESS";

        populateWaitingList();
        $('#approvalfrm').modal({
            backdrop: 'static',
            keyboard: false
        });

    } else {
        stat = "APPRAISE";

        swal({
            title: "Appraisee Name: " + cellvalue,
            text: "Do you want to start the appraisal process for the selected staff ?",
            icon: "info",
            buttons: true,
            dangerMode: true,
        })
                .then((willDelete) => {
                    if (willDelete) {

                        $('#appraisseeid').text(cellvalue + "/" + staffid);
                        $('#appraisalform').modal({
                            backdrop: 'static',
                            keyboard: false
                        });

                    } else {
                        swal("Transaction aborted!");
                    }
                });

    }

    console.log(stat);





}


function ajaxd() {

    console.log("Reloading dash")
    $.ajax({
        type: 'GET',
        url: 'ActiveSession',
        dataType: "text",
        success: function (data) {
            if (data === "active") {
                dashboard();

            } else {
                window.location.href = "/mtportal/"
                //window.location.reload(1);
                //    swal("uLELE");

            }

        }
    });
}


function populateStaffBio(staffid, refid) {
    $('#tabb').click();

    window.msgrefno = refid;

    window.pfinq = staffid;


    $.ajax({
        type: 'GET',
        url: 'StaffDetails',
        data: "staffid=" + staffid + "&reference=" + refid,
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {
                console.log("data here " + value.designation.toString());
                $('#desiid').val(value.designation);
                $('#salgradeid').val(value.salgrade);
                $('#appointid').val(value.firstappoint);
                $('#educid').val(value.education);
                $('#supid').val(value.supervisor);
                $('#gradeappointid').val(value.gradeappoint);

                var status = value.status;

                console.log(status)
                //  alert(status);
                //     console.log(value.stat);
                console.log("I am "+status)
                if (status.includes("UNLOCKED")) {
                    
                    populateCompetencies(staffid);
                } else {
                    console.log("Looking for " + refid);
                    populateFilledCompetencies(refid);
                }

                populateDocs(refid);


                fetchMsgs(refid);

            });

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });

}

function getselected(element) {
    var selected = $(element).val();


//alert(selected)

    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("scoresheettbl");

    var col = 1;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var points = selected.toString().split("[")[1].replace("]", "");

    if (selected.toString().length > 3) {
        tbl.rows[row].cells[6].innerHTML = "+";

        tbl.rows[row].cells[2].innerHTML = selected;


        //alert("Confirm Before proceeding:\nQuestion:\n" + cellvalue + "\nResponse:\n" + selected + "\nPoints: " + points);


        tbl.rows[row].cells[4].innerHTML = points;

        compute();

        var max = window.maximumScore;
        var weighted = (parseInt(points) / parseInt(max)) * 100

        tbl.rows[row].cells[5].innerHTML = Math.round(weighted * 100) / 100;


    } else {
        alert("Here because of -")
        tbl.rows[row].cells[6].innerHTML = "-";
    }


}

function populateFilledCompetencies(refno) {
    $('#btnsavescore').prop("disabled", true);
    //alert(refno)
    $('#tabc').click();
    $('#metaid').text('');
    $('#metaid1').text('');
    $.ajax({
        type: 'POST',
        url: 'AppraisalResponses',
        data: "refno=" + refno,
        dataType: "json",
        success: function (data) {
            //{"questionid", "question", "max"};
            var activitydata = '';
            var i = 0;
            var scoredpoints = 0;
            var totalscore = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.qid + '</td>';
                activitydata += '<td>' + value.qn + '</td>';

                activitydata += '<td align="center">' + value.response + '</td>';

                //  populateResponses('q' + value.questionid, value.questionid);


                activitydata += '<td>' + value.max + '</td>';
                activitydata += '<td>' + value.score + '</td>';
                activitydata += '<td>0</td>';


                activitydata += '<td>+</td>';

                $('#metaid').text("Entered by: " + value.meta);
                $('#metaid1').text("Entered by: " + value.meta);

                $('#metaid2').text(value.hrentry);
                $('#metaid3').text(value.snrentry);

                totalscore += parseInt(value.max);
                scoredpoints += parseInt(value.score);
                i += 1;
            });
            // activitydata += '</tbody>';

            window.maximumScore = totalscore;
            $('#scoresheettblbd').empty().append(activitydata);

            showSanction(scoredpoints, totalscore);

            compute();





        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}

function showSanction(score, total) {

    $('#sanctionid').text('');
    $('#sanctionid1').text('');
    //  var rounded = window.scoredPoints;
    var rounded = Math.round((parseInt(score / total) * 100) * 100) / 100
    var applicant = $('#appraiseename').text();

    $.ajax({
        type: 'GET',
        url: 'PerformanceObjectives',
        data: "score=" + rounded,
        dataType: "text",
        success: function (data) {

            $('#sanctionid').text(data);
            $('#sanctionid1').text(data);
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });

}
function populateCompetencies(staffid) {
    $('#tabb').click();


    $('#btnsavescore').prop("disabled", false);

    $.ajax({
        type: 'POST',
        url: 'StaffDetails',
        //data: "staffid=" + staffid,
        dataType: "json",
        success: function (data) {
            //{"questionid", "question", "max"};
            var activitydata = '';
            var i = 0;
            var totalscore = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.questionid + '</td>';
                activitydata += '<td>' + value.question + '</td>';

                activitydata += '<td align="center"><select id=q' + value.questionid + ' onchange=getselected(this)></select></td>';

                populateResponses('q' + value.questionid, value.questionid);


                activitydata += '<td>' + value.max + '</td>';
                activitydata += '<td>0</td>';
                activitydata += '<td>0</td>';


                activitydata += '<td>N</td>';

                totalscore += parseInt(value.max);
                i += 1;
            });
            // activitydata += '</tbody>';

            window.maximumScore = totalscore;
            $('#scoresheettblbd').empty().append(activitydata);

            //  compute();




        }, error: function (xhr, err) {

            alert(xhr)
        }
    });


}


function populateDocs(refno) {

    $.ajax({
        type: 'GET',
        url: 'FileUploads',
        data: "refno=" + refno,
        dataType: "json",
        success: function (data) {
            //{"questionid", "question", "max"};
            var activitydata = '';
            var i = 0;
            var totalscore = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td>' + value.description + '</td>';

                activitydata += '<td>  <button onclick="opendoc(this)" class="btn btn-mini"><i class="icon-folder-open"></i> Open </button></td>';


                i += 1;
            });

            $('#filesupdbd').empty().append(activitydata);

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });


}



function postrecomm(type) {


    var input;

    if (type.toString().includes("HR")) {
        input = $('#remarksfid').val();
    } else {
        input = $('#finalremarksid').val();
    }

    // alert(type + "-->" + input);


    var refinq = window.fulldetails;
    var pfnumber = refinq.toString().split("/")[1];
    var name = refinq.toString().split("/")[0];
    var refid = refinq.toString().split("/")[2];
    if (input.length < 2) {
        alert("Empty Values not allowes")
    } else {
        $.ajax({
            type: 'GET',
            url: 'AppraisalRecommendations',
            data: "refid=" + refid + "&recomtype=" + type + "&recomtext=" + input,
            dataType: "text",
            success: function (data) {
                alert(data + "\n" + refinq);

                populateFilledCompetencies(refid);

                $('#tabf').click();

            }, error: function (xhr, err) {

                alert(xhr)
            }
        });

    }
}

function populatehrfield(val) {
    var verdict = val;

    $('#remarksfid').val(verdict);
}
function populateAllDocs(refno) {
    $('#tabd').click();
    console.log("Hitting the motherload")
    $.ajax({
        type: 'GET',
        url: 'Documents',
        data: "refno=" + refno,
        dataType: "json",
        success: function (data) {

            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                activitydata += '<td>' + value.refno + '</td>';
                activitydata += '<td>' + value.description + '</td>';



                activitydata += '<td>  <button onclick="opendoc(this)" class="btn btn-mini"><i class="icon-folder-open"></i> Open </button></td>';


                console.log("Populating " + value.description);


                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';

            $('#filesupdbd').empty().append(activitydata);


        }
    });

}

function makeid(length) {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    for (var i = 0; i < length; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;

}




function compute() {

    var tbody = $("#scoresheettblbd");

    if (tbody.children().length > 0) {




        //Then insert Invoice Data
        var max = 0;
        var scored = 0;





        $('#scoresheettblbd tr').each(function () {
            max += parseInt($(this).find("td:eq(3)").html());
            scored += parseInt($(this).find("td:eq(4)").html());
        });


        window.maximumScore = max;

        var weight = (scored / max) * 100;
        var rounded = Math.round(weight * 100) / 100;


        window.scoredPoints = rounded;


        //Loop now setting weighted

        var max = window.maximumScore;

        var k = parseInt(0);
        $('#scoresheettblbd tr').each(function () {

            // var $row = $(this);
            var scoredpoints = parseInt($(this).find("td:eq(4)").html()); //Get the scored points in each row/column 4

            var weighted = (parseInt(scoredpoints) / parseInt(max)) * 100 //Divide by the already calculated max

            var tblbody = $('#scoresheettblbd');

            //     tblbody.rows[$(this)].cells[5].innerHTML = Math.round(weighted * 100) / 100;//Round off to two decimal places and set it to column 5

            $(this).find("td:eq(5)").html(Math.round(weighted * 100) / 100); //=  Math.round(weighted * 100) / 100;

            $(this).find("td:eq(6)").html('+');

            k += 1;
        });





        //   alert(weight + " " + rounded);


        $('#scoreid').text(scored + "/" + max);


    }


}

function populateResponses(elemid, questionid) {

    $.ajax({
        type: 'GET',
        url: 'AppraisalResponses',
        data: "questionid=" + questionid,
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {

                var name = value.response;
                console.log("Name is " + name)

                $('#' + elemid).append('<option value="' + name + '">' + name + '</option>');

                //    $('#example').append('<option value="' + data[value+ '">' + data.name + '</option>');
            });


        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}

function viewAndPost() {
    var tbody = $("#scoresheettblbd");

    var caniSave = "yes";

    if (tbody.children().length > 0) {



        $('#scoresheettblbd tr').each(function () {
            var response = $(this).find("td:eq(6)").html();
            console.log(response)
            if (response.toString().includes("N")) {
                caniSave = "no";
            }

        });


    }

    if (caniSave.includes("yes")) {
        //Post
        var rounded = window.scoredPoints;
        var applicant = $('#appraiseename').text();
        $('#closerqid2').click();
        $.ajax({
            type: 'GET',
            url: 'PerformanceObjectives',
            data: "score=" + rounded,
            dataType: "text",
            success: function (data) {

                swal({
                    icon: "info",
                    title: "Appraisee: " + applicant + "\nPercentage score: " + rounded + "/100",
                    text: "Reward/Sanction: " + data + "\nProceed?",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {

                                saveTabledata(applicant);

                            }
                        });


            }, error: function (xhr, err) {

                alert(xhr)
            }
        });
    } else {
        swal({
            icon: "warning",
            title: "Sorry, one of the entries is not filled",

        })
    }
}

function saveTabledata(applicant) {
    var staffid = applicant.toString().split("/")[1];
    var refno = applicant.toString().split("/")[2];
    var level = "SUPERVISOR";
    var staffname = applicant.toString().split("/")[0];


    var tbody = $("#scoresheettblbd");

//lOOP THROUGH THE TABLE GETTING VALUES
    if (tbody.children().length > 0) {

        $('#scoresheettblbd tr').each(function () {
            var questionid = $(this).find("td:eq(0)").html();
            var question = $(this).find("td:eq(1)").html();
            var answer = $(this).find("td:eq(2)").html();
            var score = $(this).find("td:eq(4)").html();

            var data = "level=" + level + "&refno=" + refno + "&questionid=" + questionid + "&question=" + question + "&answer=" + answer + "&score=" + score;

            $.ajax({
                type: 'POST',
                url: 'AppraisalApproval',
                data: data,
                dataType: "text",
                success: function (data) {
                    if (data.toString().includes("FAIL")) {
                        swal({
                            icon: "warning",
                            title: "Error in saving data",
                            text: data
                        })
                    } else {
                        swal({
                            icon: "success",
                            title: "Transaction was successful",
                            text: "Questionnaire was closed and " + staffname + "'s file was updated succesfully"
                        });

                    }


                }, error: function (xhr, err) {

                    alert(xhr)
                }
            });



        });


    }
}

function enter2sendMsg() {

    // Get the input field
    var input = document.getElementById("messageboxid");
// Execute a function when the user releases a key on the keyboard
    input.addEventListener("keyup", function (event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Trigger the search Fuunction
            var staffid = $('#messageboxid').val();
            sendMsg();
        }
    });
}

function sendMsg() {
    var text = $('#messageboxid').val();
    var usr = window.loggedinuser;

    var timestamp = new Date();

    var refno = window.msgrefno;
    var pfno = window.pfinq;

    var msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + usr + ' @ ' + timestamp + '</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'
    if (text.toString().length > 2) {

        $.ajax({
            type: 'POST',
            url: 'InternalMessaging',
            data: "refno=" + refno + "&message=" + text + "&username=" + usr + "@" + timestamp + "&pfno=" + pfinq,
            dataType: "text",
            success: function (data) {


                if (data.toString().includes("FAIL")) {
                    alert("Failure sending data\n" + data)
                } else {
                    $('#parent').append(msg2append);
                }

            }, error: function (xhr, err) {

                alert(xhr)
            }
        });


    }

    $('#messageboxid').val('');
}

function fetchMsgs(refno) {





    $.ajax({
        type: 'GET',
        url: 'InternalMessaging',
        data: "refno=" + refno,
        dataType: "json",
        success: function (data) {


            if (data.toString().includes("FAIL")) {
                alert("Failure sending data\n" + data)
            } else {

                $('#parent').empty();
                
                $.each(data, function (key, value) {

                    var text = value.message;
                    var meta = value.meta;

                    var msg2append = "";

                    if (meta.toString().includes(window.loggedinuser)) {
                        msg2append = '<div class="direct-chat-msg right"><div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-right"></span> <span class="direct-chat-timestamp pull-left">' + meta + '</span> </div><div class="direct-chat-text">' + text + '</div><!-- /.direct-chat-text --></div>'
                    } else {
                        msg2append = '<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"><span class="direct-chat-timestamp pull-right">' + meta + '</span>  </div><div class="direct-chat-text">' + text + '  </div></div>'
                    }
                    $('#parent').append(msg2append);

                });

            }

        }, error: function (xhr, err) {

            alert(xhr)
        }
    });


}

function opendoc(element) {
    //filesupdbd  
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("filesupd");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    print(cellvalue);
}


function uploadDoc() {
    var request = "refno=354657&filedesc=somefile&file=" + $('#file').val();
    $.ajax({
        type: 'POST',
        url: 'FileUploads',
        data: request,
        // dataType: "text",
        enctype: "multipart/form-data",
        success: function (data) {
            swal(data);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

function print(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "BLOBUPLOAD";
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}



function print2(reqid, typeadoc) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = typeadoc.toString().toUpperCase();
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}







