      $(document).ready(function() {

             $("#username_error_message").hide();
             $("#password_error_message").hide();

//             var error_username = false;
//             var error_password = false;

             $("#username").focusout(function() {
                    check_username();
             });

             $("#password").focusout(function(){
                    check_password();
             });

                function check_username(){
                    var pattern =/^[a-zA-Z]+([0-9]*)+([_-]?[a-zA-Z0-9])*$/;
                    var username=String($("#username").val());

                    if(pattern.test(username) && username!=='') {
                        $("#username_error_message").hide();
                        $("#username").css("border-bottom","2px solid green");
                      //  error_username=false;
                    } else{
                        $("#username_error_message").html("Should contain characters, numbers and special characters (_ and -)");
                        $("#username_error_message").show();
                        $("#username").css("border-bottom","2px solid red");
                      //  error_username=true;
                    }
                }


                function check_password() {
                    var pattern=/^[a-zA-Z0-9]{8,50}$/;
                    var password=String($("#password").val());

                    if (pattern.test(password)) {
                        $("#password_error_message").hide();
                        $("#password").css("border-bottom","2px solid green");
                       // error_password=false;
                    } else{
                        $("#password_error_message").html(" Minimum eight characters and/or numbers");
                        $("#password_error_message").show();
                        $("#password").css("border-bottom","2px solid red");
                       // error_password=true;
                    }
                }

//                 $("#loginBtn").click(function(e) {
//                    if(error_username || error_password)
//                    {
//                        //e.preventDefault();
//                        alert("Wrong input. Try again.");
//                    }
//                });

      });
