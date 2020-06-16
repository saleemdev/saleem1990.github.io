/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {




    ajaxd();




    $('#navid').click();

    $('#accesscodeid').click(function () {
        $('#accesscodeid').prop('disabled', true);
        var uname = $('#inputUserid').val();
        var pwd = $('#inputPassword').val();
        var logintype = $('#loginMethod').val();
        //logintype

        var request = 'username=' + uname + '&password=' + pwd + "&logintype=" + logintype;

        //  alert(logintype + ": " + uname);
        $.ajax({
            type: 'POST',
            url: 'LoginController',
            data: request,
            dataType: "text",
            success: function (data) {
                console.log(data.response);

                if (data == 1) {

                    $('#accessBtn').prop('disabled', false);
                    document.getElementById('accessBtn').className = "form-control btn btn-primary";


                    document.getElementById("secKeyid").focus();


                } else {
                    alert(data);
                }

                //   $('#accesscodeid').prop('disabled', false);

            },
            error: function (xhr, err) {
                //  errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            }
        });
    });


    $('#accessBtn').click(function () {

        var securitykey = $('#secKeyid').val();
        //     alert(securitykey)
        var request = 'sessionkey=' + securitykey;
        $.ajax({
            type: 'POST',
            url: 'VerifyUserInitially',
            data: request,
            dataType: "json",
            success: function (data) {
                console.log(data);

                window.setInterval(ajaxd, 10000);
                var fullname = data.name;
                var rank = data.designation;

                $('#rankid').text(rank);
                $('#nameid').text(fullname);
                $('#nameid2').text(fullname);

                swal("Welcome " + fullname)
                $('#pwdID').modal('hide');


                //  $('#nameid').text('');
                //  $('#nameid2').text('');

                $('#inputUserid').val('');
                $('#inputPassword').val('');



            },
            error: function (xhr, err) {
                //errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                //   alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
                swal({
                    icon: "warning",
                    title: "The code entered does not match"
                });
            }
        });


    });
    $('#oasid').click(function () {

        window.location.href = "pages/Scheduler.jsp"

    });
    $('#hrisid').click(function () {


        window.location.href = "HRPage.jsp"
//        $('#pwdID').modal({
//           backdrop:'static',
//           keyboard: false
//           
//       });
    });
    $('#fmsid').click(function () {


    });
    $('#signoutid').click(function () {

        var name = $('#nameid').text();
        swal({
            title: "Exit options",
            text: "Sure to exit " + name + " ?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                $('#nameid').text('');
                $('#nameid2').text('');
                $.ajax({
                    type: 'GET',
                    url: 'LoginController',
                    //dataType: "json",
                    success: function () {


                        ajaxd();
                    }
                });
            } else {
                swal("Okay, proceed!");
            }
        });
    });


//
    $('#nwuserid').click(function () {
        $('#userAdmin').modal({
            backdrop: "static",
            keyboard: false
        });
    });

});
function ajaxd() {

    $.ajax({
        type: 'GET',
        url: 'SessionEngine',
        dataType: "text",
        success: function (data) {
            if (data == "active") {
                ajaxPopulate();
            } else {
              //  swal("hj");


                $('#pwdID').modal({
                    backdrop: 'static',
                    keyboard: false

                });


            }
        }
    });
}
function ajaxPopulate() {
    $.ajax({
        type: 'GET',
        url: 'VerifyUserInitially',
        //data: request,
        dataType: "json",
        success: function (data) {
            console.log(data);

            var fullname = data.name;
            var rank = data.designation;

            $('#rankid').text(rank);
            $('#nameid').text(fullname);
            $('#nameid2').text(fullname);

            // swal("Welcome " + fullname)
            //  $('#pwdID').modal('hide');



        },
        error: function (xhr, err) {
            //errorDisplay('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
            alert('Ajax readyState: ' + xhr.readyState + '\nstatus: ' + xhr.status + ' ' + err);
        }
    });
}