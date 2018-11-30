function login(usernameElement,passwordElement) {
	let username = usernameElement && usernameElement.value ? usernameElement.value : "";
	let password = passwordElement && passwordElement.value ? passwordElement.value : "";

	var fd = new FormData();
	fd.append( 'username', username);
	fd.append( 'password', password);

	$.ajax({
        url: ROOT_PATH + '/login',
        data: fd,
        processData: false,
        contentType: false,
        type: 'POST',
        beforeSend: function() {
            $('#loading').html("<img src='../images/loading.gif'/>");
        },
        success: function(data){
            $.ajax({
                url:ROOT_PATH+'/api/user',
                processData:false,
                contentType:false,
                type:'GET',
                success:function(userdata, textStatus, jQxhr){
                    $('#loading').html("");
                    if(userdata=="CITIZEN") {
                        sessionStorage.setItem(SESSION_STORAGE_LOGIN_TOKEN_NAME, username);
                        sessionStorage.setItem(SESSION_STORAGE_ROLE_NAME, userdata);//save user's role
                        window.location.replace(ROOT_PATH + "/users/citizen/index.html");
                    } else {
                        sessionStorage.setItem(SESSION_STORAGE_LOGIN_TOKEN_NAME, username);
                        sessionStorage.setItem(SESSION_STORAGE_ROLE_NAME, userdata);//save user's role
                        window.location.replace(ROOT_PATH + "/users/doctor/index.html");
                    }
                }
            });
        },
        statusCode: {
            401 : function() {
                $('#loading').html("");
                alert("Invalid username or password!");
                }
        }
	});

}