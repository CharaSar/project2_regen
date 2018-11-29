function showSearchResults() {
    var dateFrom = $('#date_from').val();
    var dateTo = $('#date_to').val();
    var search_for = $('#search_for').val();

    if (typeof search_for === "undefined") {
        var myURI = 'from=' + dateFrom + '&to=' + dateTo;
        var myEncodedURI = encodeURI(myURI);
    }else {
        var myURI = 'from=' + dateFrom + '&to=' + dateTo + '&search_for=' + search_for;
        var myEncodedURI = encodeURI(myURI);
    }

    $(location).attr('href','doc_appointments.html?' + myEncodedURI);
}