/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {


    window.setInterval(ajaxd, 10000);


    $('#searchveh').on('keyup', function () {
        var value = $(this).val();
        console.log(value)
        var patt = new RegExp(value, "i");

        $('#docstl').find('tr').each(function () {
            var $table = $(this);

            if (!($table.find('td').text().search(patt) >= 0)) {
                $table.not('.t_head').hide();
            }
            if (($table.find('td').text().search(patt) >= 0)) {
                $(this).show();
            }

        });

    });




});

function dashboard() {
    var refno = "-";
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
                activitydata += '<td>' + value.timestamp + '</td>';




                activitydata += '<td>  <button onclick="openfile(this)" class="btn btn-mini btn-link"><i class="icon-file"></i>Open Document</button></td>';



                activitydata += '</tr>';
                i += 1;
            });
            // activitydata += '</tbody>';
            $('#nostaffid').text(i);
            $('#docstlbd').empty().append(activitydata);


        }
    });
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


function openfile(element) {

    var row = element.parentNode.parentNode.rowIndex;
    var tbl = document.getElementById("docstl");

    var col = 0;//Full Name
    var cellvalue = tbl.rows[row].cells[col].innerHTML;

    print(cellvalue);
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