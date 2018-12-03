$(document).ready(function() {
	
	$("#fname_error_message").hide();
	$("#lname_error_message").hide();
	$("#username_error_message").hide();
	$("#password_error_message").hide();
	$("#email_error_message").hide();
	$("#tel_error_message").hide();
	$("#ssn_error_message").hide();
	
	var error_fname = false;
	var error_lname = false;
	var error_username = false;
	var error_password = false;
	var error_email = false;
	var error_tel = false;
	var error_ssn = false;

	$("#fname").focusout(function() {
		check_fname();
	});

	$("#lname").focusout(function() {
		check_lname();
	});

	$("#username").focusout(function() {
		check_username();
	});

	$("#password").focusout(function(){
		check_password();
	});

	$("#email").focusout(function(){
		check_email();
	});

	$("#tel").focusout(function(){
		check_tel();
	});

	$("#ssn").focusout(function(){
		check_ssn();
	});

	function check_fname() {
		var pattern =/^[a-zA-Z]{2,50}$/;
		var fname = String($("#fname").val());

		if(pattern.test(fname))
		{
			$("#fname_error_message").hide();
			$("#fname").css("border-bottom","2px solid #008731");
			error_fname=false;
		} else {
			$("#fname_error_message").html("Should contain only characters");
			$("#fname_error_message").show();
			$("#fname").css("border-bottom","2px solid #ad0000");
			error_fname=true;
		}
	}

	function check_lname(){
		var pattern =/^[a-zA-Z]+([']?[a-zA-Z])*$/;
		var lname=String($("#lname").val());

		if(pattern.test(lname) && lname!=='') {
			$("#lname_error_message").hide();
			$("#lname").css("border-bottom","2px solid #008731");
			error_lname=false;
		} else{
			$("#lname_error_message").html("Should contain only characters");
			$("#lname_error_message").show();
			$("#lname").css("border-bottom","2px solid #ad0000");
			error_lname=true;
		}
	}

	function check_username(){
		var pattern =/^[a-zA-Z]+([0-9]*)+([_-]?[a-zA-Z0-9])*$/;
		var username=String($("#username").val());

		if(pattern.test(username) && username!=='') {
			$("#username_error_message").hide();
			$("#username").css("border-bottom","2px solid #008731");
			error_username=false;
		} else{
			$("#username_error_message").html("Should contain characters, numbers and special characters (_ and -)");
			$("#username_error_message").show();
			$("#username").css("border-bottom","2px solid #ad0000");
			error_username=true;
		}
	}


	function check_password() {
		var pattern=/^[a-zA-Z0-9]{8,50}$/;
		var password=String($("#password").val());

		if (pattern.test(password)) {
			$("#password_error_message").hide();
			$("#password").css("border-bottom","2px solid #008731");
			error_password=false;
		} else{
			$("#password_error_message").html(" Minimum eight characters and/or numbers");
			$("#password_error_message").show();
			$("#password").css("border-bottom","2px solid #ad0000");
            error_password=true;
		}
	}

	function check_email() {
		var pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var email=String($("#email").val());
		if (email!=='' && pattern.test(email)){
			$("#email_error_message").hide();
			$("#email").css("border-bottom","2px solid #008731");
			error_email=false;
		}else{
			$("#email_error_message").html("Should contain a valid email address ex. example@email.com");
			$("#email_error_message").show();
			$("#email").css("border-bottom","2px solid #ad0000");
			error_email=true;
		}
	}

	function check_tel() {
		var pattern = /^[0-9]{10}$/;
		var tel=String($("#tel").val());
		if(tel!=='' && pattern.test(tel)){
			$("#tel_error_message").hide();
			$("#tel").css("border-bottom","2px solid #008731");
			error_tel=false;
		}else{
			$("#tel_error_message").html("Exactly ten digits");
			$("#tel_error_message").show();
			$("#tel").css("border-bottom","2px solid #ad0000");
			error_tel=true;
		}
	}

	function check_ssn() {
		var pattern = /^[0-9]{11}$/;
		var ssn=String($("#ssn").val());
		if(ssn!=='' && pattern.test(ssn)){
			$("#ssn_error_message").hide();
			$("#ssn").css("border-bottom","2px solid #008731");
			error_ssn=false;
		}else{
			$("#ssn_error_message").html("Exactly eleven digits");
			$("#ssn_error_message").show();
			$("#ssn").css("border-bottom","2px solid #ad0000");
			error_ssn=true;
		}
	}

	$("#registerButton").click(function() {
		if(error_fname || error_lname ||error_username ||error_password ||error_email||error_tel ||error_ssn){
			//lathos
			swal("Wrong input. Try again.");
		}else if($("#fname").val() == "" ||
                 $("#lname").val() == "" ||
                 $("#username").val() == "" ||
                 $("#password").val() == "" ||
                 $("#email").val() == "" ||
                 $("#tel").val() == "" ||
                 $("#ssn").val() == ""){
		    swal("All fields are required!");
		} else {
            //ajax
                var fd=new FormData();
                fd.append('firstname', $("#fname").val());
                fd.append('lastname', $("#lname").val());
                fd.append('username', $("#username").val());
                fd.append('password', $("#password").val());
                fd.append('email', $("#email").val());
                fd.append('phone', $("#tel").val());
                fd.append('ssn', $("#ssn").val());

                var object = {};
                fd.forEach(function(value, key){
                    object[key] = value;
                });
                var requestData = JSON.stringify(object);

                $.ajax({
                        "url": ROOT_PATH + "/registration",
                        "method": "POST",
                        "processData": false,
                        "data": requestData,
                        "contentType": "application/json",
                        "dataType": "json",
                        beforeSend: function() {
                            $('#loading').html("<img src='../images/loading.gif'/>");
                        },
                        success: function(responseData, textStatus, jQxhr){
                            swal("Registration complete.")
                            .then(() => {
                               var loginfd = new FormData();
                               loginfd.append( 'username', $("#username").val());
                               loginfd.append( 'password', $("#password").val());


                               $.ajax({
                                   "url": ROOT_PATH + "/login",
                                   "data": loginfd,
                                   "processData": false,
                                   "contentType": false,
                                   "type": "POST",
                                   success: function(data){
                                       sessionStorage.setItem(SESSION_STORAGE_LOGIN_TOKEN_NAME, responseData.user.username);
                                       sessionStorage.setItem(SESSION_STORAGE_ROLE_NAME, responseData.user.role);//save user's role
                                       $('#loading').html("");
                                       if(responseData.user.role=="CITIZEN"){
                                           window.location.replace(ROOT_PATH + "/users/citizen/index.html");
                                       }else{
                                           window.location.replace(ROOT_PATH + "/users/doctor/index.html");
                                       }
                                   },
                                   statusCode: {
                                       401 : function() {
                                           $('#loading').html("");
                                            swal("Invalid username or password!");
                                           }
                                       }
                                   });
                            });
                        },
                        statusCode: {
                             409 : function(xhr, options, error) {
                                $('#loading').html("");
                                swal(xhr.responseText);
                             }
                        }
                    });

		}



	});

});