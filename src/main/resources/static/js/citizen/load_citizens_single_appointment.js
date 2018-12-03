$(document).ready(function() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });

    $.ajax({
       url: ROOT_PATH + "/api/citizen/appointment/" + vars["appointment_id"],
       type: "GET",
       dataType: 'json',
       contentType: 'application/json',
       success: function(appointment){
           $('#date').html(appointment.date);
           $('#time').html(appointment.time);
           $('#illness').html(appointment.illnessHistory);
           $('#notes').html(appointment.notes);
       },
       statusCode: {
            404 : function(xhr, options, error) {
                swal(xhr.responseText)
                    .then((value) => {
                    history.go(-1);
                });
            }
       }


    });

    $("#deleteButton").on('click', function(){
         $.ajax({
            "url": ROOT_PATH + "/api/citizen/appointment/" + vars["appointment_id"],
            "method": "DELETE",
            success: function(responseData, textStatus, jQxhr){
                  swal("Appointment deleted.")
                    .then((value) => {
                    window.location.href = "cit_index.html";
                  });
            },
            statusCode: {
                 404 : function(xhr, options, error) {
                     swal(xhr.responseText);
                 }
            }
         });
    });

});