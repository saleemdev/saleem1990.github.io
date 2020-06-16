//

$(document).ready(function () {
    $('#adminForm').click(function () {
        $('#aprvAdmin').prop("disabled", true);
        $('#secloginid').prop("disabled", true);
        $('#aprvAdmin').text("Save");
        $('#userAdmin').modal({
            backdrop: 'static',
            keyboard: false
        })

    });

    $('#printReport').click(function () {
        var date1 = $("#datepicker").val();
        var date2 = $("#datepicker2").val();
        var vehicle = $('#vehRegid').val();

        if (date1.length < 2 || date2.length < 2 || vehicle.length < 2) {
            swal({
                icon: "warning",
                title: "Vehicle Reg. or Dates cannot be empty",
                text: "Please check your fields"

            });
        } else {
            swal({
                icon: "success",
                title: "Success",
                text: "Printing Shortly"
            });

            $.ajax({
                type: 'GET',
                url: 'HostIP',
                // data: request,
                dataType: "text",

                success: function (data) {
                    console.log(data);
                    var ip = data;
                    var refno = vehicle;
                    var pdfname = "ALLTICKETS"
                    var trans = 'pdfname=' + pdfname + '&refno=' + refno + '&date1=' + date1 + '&date2=' + date2;
                    window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
                    return data;
                },

                error: function (xhr, err) {
                    alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                }
            });


        }
    });


    $('#dwkid').click(function () {
        //swal("Hello","My friend");
        $('#ticketModal').modal({
            backdrop: 'static',
            keyboard: false
        });
    });


    getVehData();
    //datepicker
    $("#datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        yearRange: "-100:+0"
        
    });

    //datepicker
    $("#datepicker2").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        yearRange: "-100:+0"
    });

    $('#vehalloc').on('change', function () {

        var vehicle = $('#vehalloc').val();
        $('#allochehregid').val(vehicle);
    });

    //0722234681
    $('#pffnoid').keyup(function (event) {

        if (event.keyCode == 13) {
            event.preventDefault();
            var pfno = $(this).val();
            UserDetails(pfno);

        }
        //  alert(event.keyCode);

    });

    $('#aprvAdmin').click(function () {
        //  swal("Hapa");
//{empno, login, email, telno, department, section, designation, transtype, fullname};
        var transtype = $('#aprvAdmin').text();
        var empno = $('#pffnoid').val();
        var fullname = $('#nameid').val();
        var telno = $('#telid').val();
        var email = $('#emailid').val();
        var department = $('#deptid').val();
        var section = $('#secid').val();
        var designation = $('#ulacid').val();
        var login = $('#secloginid').val();

        if (designation == null) {
            swal("User Access Level Cannot be empty");
        } else {

            var request = 'transtype=' + transtype + '&empno=' + empno + '&fullname=' + fullname + '&telno=' + telno + '&email=' + email + '&department=' + department + '&section=' + section + '&designation=' + designation + '&login=' + login;
            console.log(request)
            $.ajax({
                type: 'POST',
                data: request,
                url: 'MasterFile',
                dataType: "text",
                success: function (response) {

                    swal({
                        title: response,
                        text: "RefID " + empno,
                        icon: "success",
                    });

                }, error: function (xhr, err) {
                    swal("System Error: " + xhr + " Error " + err);
                }
            });
        }
    });





//$('#vehreq1Av').editableSelect({ effects: 'default' });


    $('#vehiclelistid').click(function () {
        swal({
            icon: "success",
            title: "Success",
            text: "Pinting shortly.."
        });
        $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",

            success: function (data) {
                console.log(data);
                var ip = data;
                var refno = '';
                var pdfname = "ALLVEHICLES"
                var trans = 'pdfname=' + pdfname + '&refno=' + refno;
                window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
                return data;
            },

            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });

    $('#userslistid').click(function () {
        $.ajax({
            type: 'GET',
            url: 'HostIP',
            // data: request,
            dataType: "text",

            success: function (data) {
                console.log(data);
                var ip = data;
                var refno = '';
                var pdfname = "ALLUSERS"
                var trans = 'pdfname=' + pdfname + '&refno=' + refno;
                window.open("http://" + ip + ":8280/fleet/PDFActions?" + trans);
                return data;
            },

            error: function (xhr, err) {
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });

});

function getVehData() {
    //console.log($('#vehregid').val());
    var data2req = 'vehreg=';
    $.ajax({
        type: 'GET',
        url: 'MaintenanceRequests',
        dataType: "json",
        data: data2req,
        success: function (data) {
            var items = data;
            //console.log(items);
            //     window.vehList = items;

            ////////////

            var dataset = items;
            $.each(dataset, function (value) {


                $('#vehRegid').append('<option value="' + dataset[value].value + '">' + dataset[value].value + '</option>');
            });
            ///////////////

            //        console.log(window.vehList);
        }, error: function (xhr, err) {
            swal("There has been some problem " + xhr + " Error " + err);
        }
    });
}
function UserDetails(refid) {
    var request = 'userid=' + refid
    $.ajax({
        type: 'GET',
        data: request,
        url: 'MasterFile',
        dataType: "json",
        success: function (response) {
            var dataset = response;
            var email = response.email;
            var name = response.name;
            var department = response.department;
            var section = response.section;
            var status = response.status;
            //Raise an alert
            swal({
                title: name,
                text: "Department: " + department + "\nStatus: " + status,
                icon: "success",
            });
            //Set values
            $('#aprvAdmin').prop("disabled", false);
            $('#nameid').val(name);
            $('#emailid').val(email);
            $('#deptid').val(department);
            $('#secid').val(section);

            if (status == "inactive") {
                $('#secloginid').prop("disabled", false);
                $('#aprvAdmin').text("Create New User");
                $('#secloginid').val(refid);
            } else {
                $('#aprvAdmin').text("Update User Records");

                OtherUserDetails(refid);
            }

        }, error: function (xhr, err) {
            swal({
                title: "Error in Transaction for " + refid,
                icon: "warning",
                text: "Please repeat the transaction or consult technical assistance"
            });
            // swal("System Error: " + xhr + " Error " + err);
        }
    });
}
function OtherUserDetails(refid) {
    var request = 'userid=' + refid
    $.ajax({
        type: 'POST',
        data: request,
        url: 'VerifyUser',
        dataType: "json",
        success: function (response) {
            //    response = text;


            var dataset = response;
            var designation = response.designation;
            var account = response.account;
            $('#secloginid').val(account);
            $('#ulacid').val(designation);
            $('#secloginid').prop("disabled", true);
        }, error: function (xhr, err) {
            swal("System Error: " + xhr + " Error " + err);
        }
    });
}