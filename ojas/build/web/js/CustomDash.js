/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var sessiontype = "";
var insession = ""

var disability = "No"
var offence = "No"
var dismissal = "No"

$(document).ready(function () {
//    $("vacanciesID").click(function () {
//        $('html,body').animate({
//            scrollTop: $("#vacanciesDivID").offset().top},
//                'slow');
//    });
    // loadDefault();
    ajaxd();
    populateCounties()
    populateEthnicities()
    function bs_input_file() {
        $(".input-file").before(
                function () {
                    if (!$(this).prev().hasClass('input-ghost')) {
                        var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                        element.attr("name", $(this).attr("name"));
                        element.change(function () {
                            element.next(element).find('input').val((element.val()).split('\\').pop());
                        });
                        $(this).find("button.btn-choose").click(function () {
                            element.click();
                        });
                        $(this).find("button.btn-reset").click(function () {
                            element.val(null);
                            $(this).parents(".input-file").find('input').val('');
                        });
                        $(this).find('input').css("cursor", "pointer");
                        $(this).find('input').mousedown(function () {
                            $(this).parents('.input-file').prev().click();
                            return false;
                        });
                        return element;
                    }
                }
        );
    }

    bs_input_file();

    $('#loginstat2').click(function () {
        loadDefault();
        window.location.reload(true);

    })

    $("#uploadfrm").submit(function (event) {
        event.preventDefault();
        var url = "FileUploads";
        console.log("Got Form")
        //    alert($('#file').val())
        var data = new FormData($(this)[0]);

        $.ajax({
            type: "POST",
            //encType: "multipart/form-data",
            url: url,
            cache: false,
            async: false,

            processData: false,
            //  dataType: "text",
            contentType: false,
            data: data,
            success: function (msg) {

                alert("Success--" + msg);
                showuploads();

            },
            error: function (msg) {
                alert("Couldn't upload file" + msg);
            }
        });
    });

    $('input[name=disabilityappl]').on('change', function () {
        var stat = $(this).val();

        window.disability = stat
        // alert("Disability status: " + stat);

        //$("#place").val( gender );
    });

    $('input[name=dismissedappl]').on('change', function () {
        var stat = $(this).val();
        window.dismissal = stat
        //  alert("Dismissed status: " + stat);

        //$("#place").val( gender );
    });

    $('input[name=offenceappl]').on('change', function () {
        var stat = $(this).val();
        window.offence = stat;
        //alert("Offence status: " + stat);

        //$("#place").val( gender );
    });

//$('#acceptBtn').on('change', function () {
////     var check = $("input[name='accessBtn']:checked");
////     alert(check)
////      if($('#accessBtn').is(":checked")){
////            alert("checked");
////        }
////        else{
////            alert("not checked");
////        }
//})
    $('#acceptBtn').click(function () {
        //  alert('changed '+$("input[name='accessBtn']:checked").val());

        var check = $("input[name='acceptBtn']:checked").val();
//
        //      alert(check)
        // alert($('#acceptBtn').prop('checked'));
//        if ($('#accessBtn').is(":checked")) {
//            alert("checked");
//        } 
//        if (!$('#accessBtn').is(":checked")) {
//            alert("not checked");
//        }
        if (check === "on") {
            $('#confirmBtn1').prop('disabled', false);
        } else {
            $('#confirmBtn1').prop('disabled', true);
        }

    });


    $("#elbasico").submit(function (event) {
        event.preventDefault();
        var url = "Applicant";

        // var formdata = new FormData($(this)[0]);
        var request = 'firstnameid=' + $('#fnameappl').val() + "&secondnameid=" + $('#snameapll').val() + "&lastnameid=" + $('#lnameappl').val() + "&emailaddressid=" + $('#emailapplid').val() + "&mobilenoid=" + $('#mobilenoapplid').val() + "&pinnoid=" + $('#pinapplid').val() + "&jobref=&transtype=elb" // passport="+$('#passportphotoid').val()
        // alert(request);
        $.ajax({
            url: url,
            cache: false,
            async: false,

            processData: false,
            //  dataType: "text",
            contentType: false,

            data: request,
            success: function (msg) {

                alert("Success--" + msg);
                populateloggerBio()
                // showuploads();

            },
            error: function (msg) {
                alert("Couldn't update file" + msg);
            }
        });
    });

    $("#elhefe").submit(function (event) {
        event.preventDefault();
        var url = "Applicant";

        // var formdata = new FormData($(this)[0]);
        var request = 'dob=' + $('#appldate').val() + "&gender=" + $('#genderapplid').val() + "&postal=" + $('#postaladdrappl').val() + "&nhif=" + $('#nhifapplid').val() + "&nssf=" + $('#nssfapplid').val() + "&homecounty=" + $('#homecountyapplid').val() + "&ethnicity=" + $('#ethnicityapplid').val() + "&subgroup=" + $('#subgroupapplid').val() + "&transtype=elh" // passport="+$('#passportphotoid').val()
        // alert(request);
        $.ajax({
            url: url,
            cache: false,
            async: false,

            processData: false,
            //  dataType: "text",
            contentType: false,

            data: request,
            success: function (msg) {

                alert("Success--" + msg);
                populateloggerBio()
                // showuploads();

            },
            error: function (msg) {
                alert("Couldn't update file" + msg);
            }
        });
    });





    var nowY = new Date().getFullYear(),
            options = "";

    for (var Y = nowY; Y >= 1980; Y--) {
        options += "<option>" + Y + "</option>";
    }

    $("#yearcertaplid").empty().append(options);
//
//
//
//    $('#homecountyapplid').select2({
//        
//    });
//
// $('#yearcertaplid').editableSelect({
//        filter: false
//    });
//    
//     $('#certypeapplid').editableSelect({
//        filter: false
//    });

    /* END JQUERY KNOB */
    var dates = $('#appldate').datepicker({
        autoclose: true,
        //  minDate: dateToday,
        format: 'yyyy/mm/dd',
        //minDate: dateToday

    }).on("changeDate", function () {
        //  calculateAge($(this).val());
        //   alert($(this).val())
    });

    window.setInterval(ajaxd, 100000);

    $('#submitid').click(function () {
        var uid = $('#loginid').val();
        var pwd = $('#pwdid').val();

        if (uid.length < 2 || pwd.length < 3) {
            $('#warninglbl').text("You must enter a vald username and password to proceed.");
            if (uid.length < 2) {
                $('#loginid').css("border", "2px solid red");
            }
            if (pwd.length < 3) {
                $('#pwdid').css("border", "2px solid red");
            }

        } else {
            var request = "uid=" + uid + "&pwd=" + pwd;

            $.ajax({
                type: 'GET',
                url: 'AuthenticationServlet',
                data: request,
                dataType: "text",
                success: function (data) {

                    //alert(data);
                    if (data.toString().includes("noluck")) {
                        $('#warninglbl').text("Sorry, the entered values do not match our records. Please retry")
                    } else {
                        //open a Larger Modal for Job application
                        $('#closeloginid').click();
                        populateloggerBio();
                    }
                },
                error: function (xhr, err) {
                    alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                }
            });
        }
    });

    $('#ethnicityapplid').on("change", function () {
        var ethselected = $('#ethnicityapplid').val();
        // alert(ethselected);
        filtersSubgroups(ethselected)
    })
    $('#loginstat').click(function () {
        openmodal();
    });

    $('#newregid').click(function () {
        $('#newregmodal').modal({
            backdrop: "static",
            keyboard: true
        });
    });


    $('#submitidreg').click(function () {
        var input1 = $('#idtype');
        var input2 = $('#loginidreg');
        var input3 = $('#fnameid');
        var input4 = $('#lnameid');
        var input5 = $('#emailidreg');


        if (input1.val().toString().includes("-Select-") || input2.val().length < 4 || input3.val().length < 2 || input4.val().length < 2 || !input5.val().toString().includes('@')) {

            $('#warninglblreg').text("Invalid entries. Please retry")


            if (input1.val().toString().includes("Select")) {
                $('#idtypedivid').css("border", "2px solid red");
                //   alert(input1.val());
            } else {
                $('#idtypedivid').css('border', '2px solid green');
            }

            if (input2.val().length < 4) {
                input2.css("border", "2px solid red");
            } else {
                input2.css('border', '2px solid green');
            }

            if (input3.val().length < 2) {
                input3.css("border", "2px solid red");
            } else {
                input3.css('border', '2px solid green');
            }

            if (input4.val().length < 2) {
                input4.css("border", "2px solid red");
            } else {
                input4.css('border', '2px solid green');
            }

            if (!input5.val().toString().includes('@')) {
                // alert(input5.val() + "=invalid")
                input5.css("border", "2px solid red");
            } else {
                input5.css('border', '2px solid green');
                // alert(input5.val() + "=valid")
            }
        } else {
            $('#warninglblreg').css('color', 'green');
            input2.css('border', '2px solid green');
            input3.css('border', '2px solid green');
            input4.css('border', '2px solid green');
            input5.css('border', '2px solid green');

            var idtype = input1.val();
            var loginid = input2.val();
            var fnameid = input3.val();
            var lnameid = input4.val();
            var email = input5.val();


            swal({
                title: "Please Confirm this Information to be correct before clicking [OK]: ",
                text: "Identification type: " + idtype + "\nID Number:" + loginid + "\nFirst Name: " + fnameid + "\nLast Name: " + lnameid + "\nE-Mail: " + email,
                icon: "info",
                buttons: true,
                dangerMode: true,
            })
                    .then((willDelete) => {
                        if (willDelete) {
                            var request = "idtype=" + idtype + "&idno=" + loginid + "&fname=" + fnameid.toString().toUpperCase() + "&lname=" + lnameid.toString().toUpperCase() + "&email=" + email.toString().toLowerCase()

                            savecredentials(request);
                            // saveEntitleMent(request, pf, "");
                        } else {
                            swal("Transaction aborted!");
                        }
                    });


            //$('#warninglblreg').text("Valid entries. Please proceed")
        }

    })

});

function savecredentials(request) {

    $.ajax({
        type: 'GET',
        url: 'ApplicantCreds',
        data: request,
        dataType: "text",
        success: function (data) {

            if (data.toString().includes("successfully")) {
                swal({
                    icon: 'success',
                    title: data
                });
            } else {

                if (data.toString().includes("duplicate")) {
                    data = "Sorry. The ID you entered already exists in our database. Please retry";
                }
                swal({
                    icon: 'warning',
                    title: data
                })
            }
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });


}
function openmodal(element) {
    //alert("Thanks");
    $.ajax({
        type: 'POST',
        url: 'ActiveSession',
        // data: request,
        dataType: "text",
        success: function (data) {

            if (data.toString().includes("no")) {
                $('#dataid').modal({
                    backdrop: "static",
                    keyboard: true
                });
            } else {
                //open a Larger Modal for Job application
                var row = element.parentNode.parentNode.rowIndex;
                var tbl = "";
                tbl = document.getElementById("jobslisting");

                var col = 0;
                var jobid = tbl.rows[row].cells[col].innerHTML;
                col = 1;

                var jdesc = tbl.rows[row].cells[col].innerHTML;

                $('#headeridappl').html("JOB APPLICATION FORM REF:" + jobid);


                $('#refno').val(jobid)
                $('#jobref').val(jobid)



                $('#jobapplicationmodal').modal({
                    backdrop: "static",
                    keyboard: true
                })
            }
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}


function ajaxd() {

    $.ajax({
        type: 'POST',
        url: 'ActiveSession',
        // data: request,
        dataType: "text",
        success: function (data) {
            window.sessiontype = data;
            //  console.log(data)
            //   alert(data);


            if (data.toString() == "yes") {

                loadWithSession()
                populateloggerBio();



            } else {
                loadDefault();
            }
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}


function populateloggerBio() {
    $.ajax({
        type: 'POST',
        url: 'AuthenticationServlet',
        //  data: request,
        dataType: "text",
        success: function (data) {

            $('#userheaderid').text("Welcome, " + data);


            $('#loginstat2').css("display", "block");
            $('#loginstat2').text("Sign Out")


            showBio();
            showaca();
            showuploads();
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}


function loadDefault() {
    $.ajax({
        type: 'GET',
        url: 'Vacancies',
        // data: request,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                var descriptor = '<p><b>JOB CATEGORY</b>:' + value.category + '</p>\n\
                                    <p><b>DESIGNATION</b>: ' + value.designation + '</p>\n\
                                     <p><b>JOB GROUP</b>: ' + value.jobgroup + '</p>\n\
                                      <p><b>AVAILABLE POSITIONS</b>: ' + value.positions + '</p>\n\
                                       <p><b>POSTED ON</b>: ' + value.postedon + '</p>\n\
                                         <p><b>APPLICATION DEADLINE</b>: ' + value.deadline + '</p>';

                activitydata += '<td>' + value.vacancyid + '</td>';
                activitydata += '<td>' + descriptor + '</td>';



                activitydata += '<td>  <button onclick="view(this)" class="btn btn-mini"><i class="icon-folder-open"></i> Download Job Requirements </button></td>';

                activitydata += '<td>  <button onclick="openmodal(this)" class="btn btn-mini btn-success"><i class="icon-folder-open"></i> Apply For this Job </button></td>';

                //    activitydata += '<td>  <button onclick="view(this)" class="btn btn-mini"><i class="icon-file"></i>appraise</button></td>';
///Version 2





                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#jobslistingbd').empty().append(activitydata);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}


function view(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = "";
    tbl = document.getElementById("jobslisting");

    var col = 0;
    var jobid = tbl.rows[row].cells[col].innerHTML;
    
    printblobwdoctype(jobid, "JOBLOB");
    
    
}


function loadWithSession() {
    $.ajax({
        type: 'POST',
        url: 'Vacancies',
        // data: request,
        dataType: "json",
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';

                var descriptor = '<p><b>JOB CATEGORY</b>:' + value.category + '</p>\n\
                                    <p><b>DESIGNATION</b>: ' + value.designation + '</p>\n\
                                     <p><b>JOB GROUP</b>: ' + value.jobgroup + '</p>\n\
                                      <p><b>AVAILABLE POSITIONS</b>: ' + value.positions + '</p>\n\
                                       <p><b>POSTED ON</b>: ' + value.postedon + '</p>\n\
                                         <p><b>APPLICATION DEADLINE</b>: ' + value.deadline + '</p>';

                activitydata += '<td>' + value.vacancyid + '</td>';
                activitydata += '<td>' + descriptor + '</td>';



                activitydata += '<td>  <button onclick="view(this)" class="btn btn-mini"><i class="icon-folder-open"></i> Download Job Requirements </button></td>';

                activitydata += '<td>  <button onclick="openmodal(this)" class="btn btn-mini btn-success"><i class="icon-folder-open"></i> Apply For this Job </button></td>';

                //    activitydata += '<td>  <button onclick="view(this)" class="btn btn-mini"><i class="icon-file"></i>appraise</button></td>';
///Version 2





                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#jobslistingbd').empty().append(activitydata);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

function calculateAge(dob) {
    var mdate = dob
    //  alert(mdate)
    var arr = mdate.toString().split("/")
    var yearThen = parseInt(arr[2].toString())//parseInt(mdate.substring(0, 4), 10);
    var monthThen = parseInt(arr[1].toString())//parseInt(mdate.substring(5, 7), 10);
    var dayThen = parseInt(arr[0].toString())//parseInt(mdate.substring(8, 10), 10);

    var today = new Date();
    var birthday = new Date(yearThen, monthThen - 1, dayThen);

    var differenceInMilisecond = today.valueOf() - birthday.valueOf();

    var year_age = Math.floor(differenceInMilisecond / 31536000000);
    var day_age = Math.floor((differenceInMilisecond % 31536000000) / 86400000);

    if ((today.getMonth() == birthday.getMonth()) && (today.getDate() == birthday.getDate())) {
        alert("Happy B'day!!!");
    }

    var month_age = Math.floor(day_age / 30);

    day_age = day_age % 30;

    if (isNaN(year_age) || isNaN(month_age) || isNaN(day_age)) {
        //  $("#exact_age").text("Invalid birthday - Please try again!");
    } else {

        //$('#ageapplid').val("<br/><span id=\"age\">" + year_age + " years " + month_age + " months " + day_age + " days</span>")
        //   alert(year_age);
        alert("<br/><span id=\"age\">" + year_age + " years " + month_age + " months " + day_age + " days</span>")
        //
//$("#exact_age").html("You are<br/><span id=\"age\">" + year_age + " years " + month_age + " months " + day_age + " days</span> old");
    }

    // alert(birthday);
}

function uploadcl() {
    $('#nav-uploads-tab').click();
}

function addact() {
    var qual = $('#qualfapplid2').val();
    var year = $('#yearcertaplid').val();
    var grpos = $('#gradeattained').val();
    var refno = $('#certrefapplid').val();







    if (qual.toString().length > 2 && year.toString().length > 2 && grpos.toString().length > 1) {

        var request = "qualification=" + qual + "&year=" + year + "&gradepos=" + grpos + "&reference=" + refno;

        $.ajax({
            type: 'POST',
            url: 'Academics',
            data: request,
            dataType: "text",
            success: function (data) {

                alert(data)
                showaca();
            },
            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });


//        var activitydata = '';
//        var i = 1;
//        //  $('#table-custom-2bd').empty();
//
//        console.log("value.rqid.....")
//        activitydata += '<tr>';
//        activitydata += '<td>' + qual + '</td>'; //Index
//        activitydata += '<td>' + year + '</td>'; //Index
//        activitydata += '<td>' + grpos + '</td>'; //Index
//        activitydata += '<td>' + refno + '</td>'; //Index
//        activitydata += '<td><button class="btn btn-danger btn-sm ">Remove</button></td>';//5
//
//
//
//
//        activitydata += '</tr>';
//        i += 1;
//
//
//        $('#qualtblbd').append(activitydata);

    } else {
        swal({icon: "warning", title: "Sorry, you cannot add an empty activity"});
    }


}


function showBio() {
    var request = "rqtype=bio";
    $.ajax({
        type: 'POST',
        url: 'Applicant',
        data: request,
        dataType: "json",
        success: function (data) {

            $.each(data, function (key, value) {
                //  alert(value.idtype);
                $('#idtypeappl1').val(value.idtype.toString().toUpperCase());
                $('#idnoappl').val(value.idno);
                $('#fnameappl').val(value.fname);
                $('#snameapll').val(value.sname);
                $('#lnameappl').val(value.lname);
                $('#emailapplid').val(value.email);
                $('#appldate').val(value.dob);
                $('#genderapplid').val(value.gender);
                $('#postaladdrappl').val(value.postal);
                $('#nhifapplid').val(value.nhif);
                $('#nssfapplid').val(value.nssf);
                $('#homecountyapplid').val(value.homecounty);
                $('#ethnicityapplid').val(value.ethnicity);
                filtersSubgroups(value.ethnicity);
                console.log(value.ethnicity)
                $('#subgroupapplid').val(value.subgroup);
                $('#pinapplid').val(value.pin);
                $('#mobilenoapplid').val(value.mobileno);
            })



        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

function showaca() {
    var request = "rqtype=aca";
    $.ajax({
        type: 'POST',
        url: 'Applicant',
        data: request,
        dataType: "json",
        success: function (data) {

            var activitydata = '';
            var i = 1;
            //  $('#table-custom-2bd').empty();

            console.log("value.rqid.....")
            //{"qualification", "year", "gradepos","reference"};
            $.each(data, function (key, value) {
                activitydata += '<tr>';
                activitydata += '<td>' + value.uniqueid + '</td>'; //Index
                activitydata += '<td>' + value.qualification + '</td>'; //Index
                activitydata += '<td>' + value.year + '</td>'; //Index
                activitydata += '<td>' + value.gradepos + '</td>'; //Index
                activitydata += '<td>' + value.reference + '</td>'; //Index
                activitydata += '<td><button class="btn btn-danger btn-sm " onclick="delqual(this)">Remove</button></td>';//5
            })


            activitydata += '</tr>';
            i += 1;


            $('#qualtblbd').empty().append(activitydata);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function delatt(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("attachmenttbl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    // alert(cellvalue)


    var request = "uniqueid=" + cellvalue;
    $.ajax({
        type: 'GET',
        url: 'Attachments',
        data: request,
        dataType: "text",
        success: function (data) {
            alert(data);
            showuploads()()

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}
function delqual(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("qualtbl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    // alert(cellvalue)


    var request = "uniqueid=" + cellvalue;
    $.ajax({
        type: 'GET',
        url: 'Academics',
        data: request,
        dataType: "text",
        success: function (data) {
            alert(data);
            showaca()

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

    //printblob(cellvalue);
}
function showuploads() {
    var request = "rqtype=upl";
    $.ajax({
        type: 'POST',
        url: 'Applicant',
        data: request,
        dataType: "json",
        success: function (data) {

            var activitydata = '';
            var i = 1;
            //  $('#table-custom-2bd').empty();

            console.log("value.rqid.....")
            //{"qualification", "year", "gradepos","reference"};
            $.each(data, function (key, value) {
                activitydata += '<tr>';
                activitydata += '<td>' + value.fileref + '</td>'; //Index
                activitydata += '<td>' + value.description + '</td>'; //Index
                activitydata += '<td>' + value.remarks + '</td>'; //Index

                activitydata += '<td><button class="btn btn-danger btn-sm" onclick="delatt(this)">Remove</button></td>';//5
                activitydata += '<td><button class="btn btn-primary btn-sm " onclick="opendoc(this)">Preview</button></td>';//5
            })


            activitydata += '</tr>';
            i += 1;


            $('#attachmenttblbd').empty().append(activitydata);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function opendoc(element) {
    //filesupdbd  
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("attachmenttbl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    //alert(cellvalue)

    printblob(cellvalue);
}

function printblob(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "BLOBUPLOAD";
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/ojas/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function printblobwdoctype(reqid, documenttype) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = documenttype;
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/ojas/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function application(requestpara) {
    var request = requestpara;

    alert(request);
    $.ajax({
        type: 'POST',
        url: 'ProfileCompleteness',
        data: request,
        dataType: "text",
        success: function (data) {
            swal({
                icon: 'success',
                title: "Congratulations," + data,
                text: "Please be patient with the review process and you will be notified on e-mail."
            })


        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
function applyNow() {
    var request = "rqtype=bio";



    var bio = 0, aca = 0, atta = 0;
    $.ajax({
        type: 'GET',
        url: 'ProfileCompleteness',
        data: request,
        dataType: "json",
        success: function (data) {
            //{"bio", "academics", "uploads"};
            $.each(data, function (key, value) {
                bio = parseFloat(value.bio)
                aca = parseFloat(value.academics)
                atta = parseFloat(value.uploads)
            });
            //Time to bring out the big guns
            if (bio > 99.00 && aca > 99.00 && atta > 99.00) {

                var postrequestdata = "jobref=" + $('#refno').val() + "&disabilitystat=" + window.disability + "&offencestat=" + window.offence + "&dismissalstat=" + window.dismissal + "&disabilitytext=" + $('#disabilitytxt').val() + "&offencetext=" + $('#offencetxt').val() + "&dismissaltext=" + $('#dismissedtxt').val();

                application(postrequestdata);
            } else {
                swal({
                    icon: 'warning',
                    title: "Sorry, your profile is not complete to meet the requirements",
                    text: "Bio Profile: " + bio + "%\nAcademic Profile: " + aca + "%\nUploads: " + atta
                })
            }



        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function readURL(input) {
    // alert("here")
    var path = $('#file').val();

    //  alert(path)

    if (path.toString().endsWith(".JPG") || path.toString().endsWith(".JPEG") || path.toString().endsWith(".jpg") || path.toString().endsWith(".jpeg")) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah2').attr('src', e.target.result);
                swal({
                    // title: make,
                    text: "Preview" + path,
                    icon: e.target.result,
                });
            };

            reader.readAsDataURL(input.files[0]);


        }




//        $('#photoidmodal').modal({
//            keyboard:false
//        })
    }
}


function populateCounties() {
//    var url = "/ojas/counties.json";
//    $.getJSON(url, function (data) {
//        $.each(data, function (key, value) {
//            $('#homecountyapplid').empty().append('<option>'+value.county+'</option>');
//        })
//    });
    $.ajax({
        type: 'GET',
        url: "counties2.json",
        dataType: "json",
        success: function (data) {
            // alert(data);
            var i = 0;
            var activitydata = '';
            $.each(data, function (key, value) {
                i += 1;
                $('#homecountyapplid').append('<option value="' + value.county + '">' + value.county + '</option>');
            });
            console.log(i);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

function populateEthnicities() {

    $.ajax({
        type: 'GET',
        url: "ethnicities.json",
        dataType: "json",
        success: function (data) {
            var uniqueNames = [];
            for (i = 0; i < data.length; i++) {
                if (uniqueNames.indexOf(data[i].ethnicity) === -1) {
                    uniqueNames.push(data[i].ethnicity);
                }
            }
            $('#ethnicityapplid').empty()
            for (i = 0; i < uniqueNames.length; i++) {
                //   alert(uniqueNames[i]);
                $('#ethnicityapplid').append('<option value="' + uniqueNames[i] + '">' + uniqueNames[i] + '</option>');
            }
            console.log(i);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

function filtersSubgroups(ethnicity) {

    $.ajax({
        type: 'GET',
        url: "ethnicities.json",
        dataType: "json",
        success: function (data) {


            var as = $(data).filter(function (i, n) {
                return n.ethnicity === ethnicity
            });


            $('#subgroupapplid').empty();
            for (var i = 0; i < as.length; i++) {
                $('#subgroupapplid').append('<option value="' + as[i].subgroup + '">' + as[i].subgroup + '</option>');
                // alert(as[i].name + "         " + as[i].website)
            }



            console.log(i);

        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });

}

