var analytics = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
var max = 4000;
$(document).ready(function () {
// plotChart();
    leaveAnalytics("");
    populateDepts();
    populateAllStaff();
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var now = new Date();
    var thisMonth = months[now.getMonth()];
    $('#upcominglvtxt').text("Upcoming Leaves (" + thisMonth + ")");
    //upcominglvtxt


//
    var dates4 = $('#grantdate').datepicker({
        autoclose: true,
        // minDate: dateToday,
        format: 'yyyy/mm/dd',
        // minDate: dateToday,

    });
    $('#rejectdapplid').click(function () {

        printrejected(thisMonth);
        //  printupcoming(thisMonth);
    });
    $('#printupcomingid').click(function () {
        var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
        var now = new Date();
        var thisMonth = months[now.getMonth()];
        printupcoming(thisMonth);
    });
    $('#printstaffonleave').click(function () {
        printStaffOnLeave();
    });
    $('#activeapplid').click(function () {
        printpending();
    });
    $('#updedit').click(function () {
        var refno = $('#edlvid').text();
        var days = $("#daysEntitled").val();
        var covering = $('#staffeditid').val();
        var date = $('#grantdate').val();

        var request = "refid=" + refno+"&days="+days+"&covering="+covering+"&date="+date;
        $.ajax({
            type: 'POST',
            url: 'LeaveModification',
            data: request,
            dataType: "text",
            success: function (data) {
                print(refno);
               swal(data);
            },
            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });
    //activeapplid

    $('#example').on('change', function () {
//alert(this.value);
        $('#staffnameid2').text(this.value);
        var alldet = this.value.split("/");
        var staffid = alldet[1];
        //   alert(staffid);
        leaveHistory(staffid);
    });
    $('#prinStmtid').click(function () {
        printElement(document.getElementById("printThis"));
    });
    $('#lvstmtid').click(function () {
        $('#leaveStatementid').modal({
            backdrop: "static",
            keyboard: false
        });
    });
    $('#example').select2({
//   placeholder: 'Select a month'
    });
    $('#staffeditid').select2({
//   placeholder: 'Select a month'
    });
    //$("#loadChart").click();
    $("#loadChart").click(function () {
        console.log(analytics);
        // leaveAnalytics("");
        plotChart();
    });
});
// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';
// Area Chart Example
var ctx = document.getElementById("myAreaChart");
function plotChart() {
    console.log("Plotting things");
    // ctx.empty();
    //leaveAnalytics("");
    var myLineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"],
            datasets: [{
                    label: "Staffs scheduled on leave",
                    lineTension: 0.3,
                    backgroundColor: "rgba(2,117,216,0.2)",
                    borderColor: "rgba(2,117,216,1)",
                    pointRadius: 5,
                    pointBackgroundColor: "rgba(2,117,216,1)",
                    pointBorderColor: "rgba(255,255,255,0.8)",
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "rgba(2,117,216,1)",
                    pointHitRadius: 50,
                    pointBorderWidth: 2,
                    data: analytics,
                }],
        },
        options: {
            scales: {
                xAxes: [{
                        time: {
                            unit: 'date'
                        },
                        gridLines: {
                            display: false
                        },
                        ticks: {
                            maxTicksLimit: 7
                        }
                    }],
                yAxes: [{
                        ticks: {
                            min: 0,
                            max: max,
                            maxTicksLimit: 5
                        },
                        gridLines: {
                            color: "rgba(0, 0, 0, .125)",
                        }
                    }],
            },
            legend: {
                display: false
            }
        }
    });
}

function preview(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("lvHistTbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    var level = window.approvalLevel;
//    swal(cellvalue);
    print(cellvalue)

}
//printStaffOnLeave


function printStaffOnLeave() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "STAFFONLEAVEREPORT";
            var trans = "pdfname=" + doctype + "&refno=";
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function printpending() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "PENDINGLEAVES";
            var trans = "pdfname=" + doctype + "&refno=";
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
//printrejected

function printrejected() {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "REJECTEDAPPLICATIONS";
            var trans = "pdfname=" + doctype + "&refno=";
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function printupcoming(month) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "PRINTUPCOMING";
            var trans = "pdfname=" + doctype + "&refno=" + month;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}
//rejectdapplid




function print(reqid) {
    $.ajax({
        type: 'GET',
        url: 'HostIP',
        // data: request,
        dataType: "text",
        success: function (data) {
            var ip = data;
            var doctype = "LEAVE_APPLICATION";
            var trans = "pdfname=" + doctype + "&refno=" + reqid;
            window.open("http://" + ip + ":8280/mtportal/PDFActions?" + trans);
        },
        error: function (xhr, err) {
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}

function process(element) {
    $('#selectedDeptid').text((element.text));
    max = 1500;
    leaveAnalytics(element.text)
    // alert(element.text)

    plotChart();
}

function printElement(elem) {
    var domClone = elem.cloneNode(true);
    var $printSection = document.getElementById("printSection");
    if (!$printSection) {
        var $printSection = document.createElement("div");
        $printSection.id = "printSection";
        document.body.appendChild($printSection);
    }

    $printSection.innerHTML = "";
    $printSection.appendChild(domClone);
    window.print();
}


function edid(element) {
    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("lvHistTbl");
    var col = 0;
    var cellvalue = tbl.rows[row].cells[col].innerHTML;
    $('#edlvid').text(cellvalue)
    $('#realeditleave').modal({

    });
    // alert("Deleted " + cellvalue);
    //var staffid = $('#pfnoToSearch').val();
    //  saveEntitleMent("", staffid, cellvalue)

}

function leaveHistory(staffid) {
    $.ajax({
        type: 'GET',
        url: 'LeaveBalances',
        dataType: "json",
        data: "staffid=" + staffid,
        success: function (data) {
            var activitydata = '';
            var i = 0;
            $.each(data, function (key, value) {

                activitydata += '<tr>';
                activitydata += '<td>' + value.leaveid + '</td>';
                activitydata += '<td>' + value.leavetype + '</td>';
                activitydata += '<td>' + value.daysreq + '</td>';
                // activitydata += '<td>-</td>';
                //activitydata += '<td>' + value.rqon + '</td>';


                var stat = value.status;
                if (stat === "approved") {
                    activitydata += '<td ><button onclick="preview(this)" class="btn btn-success">' + value.status + '</button></td>'
                } else if (stat === "rejected") {
                    activitydata += '<td ><button onclick="preview(this)" class="btn btn-danger">' + value.status + '</button></td>'
                } else {
                    activitydata += '<td ><button onclick="preview(this)" class="btn btn-warning">' + value.status + '</button></td>'
                }

                activitydata += '<td ><button onclick="edid(this)" class="btn btn-link btn-success">Edit</button></td>'

<!--        activitydata += '<td ><button onclick="delid(this)" class="btn btn-link">Delete</button></td>'--->



activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#lvHistidbd').empty().append(activitydata);
        }
    });
}


function populateDepts() {
// $("#deptDropDown").empty();
    console.log("Getting depts")
    $.ajax({
        type: 'GET',
        url: 'LeaveDistribution',
        dataType: "json",
        success: function (data) {

            console.log("data here")
            $.each(data, function (key, value) {
                $("#deptDropDown").append('<a onclick=process(this) class="dropdown-item">' + value.dept + '</a>');
            })
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}


function populateAllStaff() {
// $("#deptDropDown").empty();
    console.log("Getting staff")
    $.ajax({
        type: 'GET',
        url: 'AllStaff',
        dataType: "json",
        success: function (data) {

            console.log("data here")
            $.each(data, function (key, value) {
                $("#example").append('<option>' + value.staffdet + '</option>');
                $('#staffeditid').append('<option>' + value.staffdet + '</option>');
            })
            // $("#loadChart").click();
        }, error: function (xhr, err) {

            alert(xhr)
        }
    });
}
function leaveAnalytics(dept) {

    console.log("Started work....ss")
    $.ajax({
        type: 'POST',
        url: 'LeaveDistribution',
        data: "dept=" + dept,
        dataType: "json",
        success: function (data) {
            analytics = [parseInt(data.JANUARY), parseInt(data.FEBRUARY), parseInt(data.MARCH), parseInt(data.APRIL),
                parseInt(data.MAY), parseInt(data.JUNE), parseInt(data.JULY), parseInt(data.AUGUST), parseInt(data.SEPTEMBER)
                        , parseInt(data.OCTOBER), parseInt(data.NOVEMBER), parseInt(data.DECEMBER)]



            $("#loadChart").click();
        }
    });
}



 