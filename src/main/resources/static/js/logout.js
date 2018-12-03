function logout(){
    swal({
          title: "Are you sure...",
          text: "...you want to logout?",
          icon: ROOT_PATH + '/images/favicon.ico',
          buttons: ["Cancel", "Sign out"],
          dangerMode: true,
    })
    .then((willSignout) => {
          if (willSignout) {
                sessionStorage.clear();
                window.location.replace(ROOT_PATH + "/logout");
          }
    });
}

// Old-simple logout function
//
//function logout(){
//    if(confirm("Are you sure you want to logout?")){
//        sessionStorage.clear();
//        window.location.replace(ROOT_PATH + "/logout");
//    }
//}