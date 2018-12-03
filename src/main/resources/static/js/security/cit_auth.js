if(!sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)){
    window.location.replace(ROOT_PATH + "/index.html");

} else if (sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)=="DOCTOR") {
    window.location.replace(ROOT_PATH + "/users/doctor/index.html");
}
