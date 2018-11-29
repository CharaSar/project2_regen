if(sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)=="CITIZEN"){
    window.location.replace(ROOT_PATH + "/users/citizen/index.html");
} else if (sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)=="DOCTOR") {
    window.location.replace(ROOT_PATH + "/users/doctor/index.html");
}
