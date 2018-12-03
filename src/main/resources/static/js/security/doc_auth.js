if(!sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)){
    window.location.replace(ROOT_PATH + "/index.html");

} else if (sessionStorage.getItem(SESSION_STORAGE_ROLE_NAME)=="CITIZEN") {
    window.location.replace(ROOT_PATH + "/users/citizen/index.html");
}
