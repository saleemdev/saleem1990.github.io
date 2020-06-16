/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#loginBtn").on('click', function () {

        var username = $('#uname').val();
        var password = $('#pwd').val();

        $.ajax({
            type: 'POST',
            data: {
                username: username,
                password: password
            },
            url: 'LoginController',
            dataType: "text",
            success: function (data) {

                var msg = data;
                showMessage(msg);

            }, error: function (xhr, err) {
                // alert("There has been some problem with login " + xhr + " Error " + err);

                $("#messageDiv").html(xhr + ": " + err);

            }
        });
    });

//    $("#regForm").validate({
//        rules: {
//            "uname": {
//                required: true
//                        //minlength: 5
//            },
//
//            "pwd": {
//                required: true,
//            }
//        },
//        messages: {
//            "uname": {
//                required: "Please, enter a Valid UserName"
//            },
//
//            "pwd": {
//                required: "password Canot be empty"
//            }
//
//        }
//        //,
////        submitHandler: function (form) { // for demo
////            swal('valid form submitted'); // for demo
////            return false; // for demo
////        }
//    });
});


function showMessage(results) {
    var message = results;

    if (message == "True") {
        $(location).attr('href', 'dashboard.jsp');
    } else {

        $("#messageDiv").html(results);

        //  $('#statLbl').text(message);
        $('#uname').text("");
        $('#pwd').text("");
    }

}
