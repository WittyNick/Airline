var doc = document;

/*
Отправляет POST запрос в сервлет.
Получает массив JSON рейсов.
Вызывает фукцию fillTable(json).
*/
window.onload = function() {
    fillMainTable();
};

function fillMainTable() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "welcome", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var flights = JSON.parse(this.responseText);
            fillTableBody(flights);
        }
    };
    xhr.send();
}

function fillTableBody(flights) {
    var tableBody = doc.getElementById("table_body");

    for (var i = 0; i < flights.length; i++) {
        var flight = flights[i];

        var row = doc.createElement("TR");
        tableBody.appendChild(row);

        var tdFlightNumber = doc.createElement("TD");
        var tdStartPoint = doc.createElement("TD");
        var tdDestinationPoint = doc.createElement("TD");
        var tdDepartureDate = doc.createElement("TD");
        var tdDepartureTime = doc.createElement("TD");
        var tdArrivalDate = doc.createElement("TD");
        var tdArrivalTime = doc.createElement("TD");
        var tdPlane = doc.createElement("TD");
        row.appendChild(tdFlightNumber);
        row.appendChild(tdStartPoint);
        row.appendChild(tdDestinationPoint);
        row.appendChild(tdDepartureDate);
        row.appendChild(tdDepartureTime);
        row.appendChild(tdArrivalDate);
        row.appendChild(tdArrivalTime);
        row.appendChild(tdPlane);

        tdFlightNumber.innerHTML = flight["flightNumber"];
        tdStartPoint.innerHTML = flight["startPoint"];
        tdDestinationPoint.innerHTML = flight["destinationPoint"];
        tdDepartureDate.innerHTML = flight["departureDate"];
        tdDepartureTime.innerHTML = flight["departureTime"];
        tdArrivalDate.innerHTML = flight["arrivalDate"];
        tdArrivalTime.innerHTML = flight["arrivalTime"];
        tdPlane.innerHTML = flight["plane"];
    }
}