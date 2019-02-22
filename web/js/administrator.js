var doc = document;
var tmpSelectedRow = null;

window.onload = function() {
    localizeAdministrator();
    doc.getElementById("lang").addEventListener("change", function() {
        var body = "locale=" + document.getElementById("lang").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "locale/change", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if ("ok" === xhr.responseText) {
                    localizeAdministrator();
                    clearMainTable();
                    fillMainTable();
                }
            }
        };
        xhr.send(body);
    });
    fillMainTable();
};

function clearMainTable() {
    var tableBody = doc.getElementById("tableBody");
    while (tableBody.hasChildNodes()) {
        tableBody.removeChild(tableBody.lastChild);
    }
}

function buttonEditAction() {
    if (tmpSelectedRow == null) {
        return;
    }
    var inputFlightId = doc.getElementById("flightId");
    var selectedId = tmpSelectedRow.children[0].innerText; // selected flight id
    inputFlightId.setAttribute("value", selectedId);
    doc.getElementById("formEdit").submit();
}

function buttonAddAction() {
    var inputFlightId = doc.getElementById("flightId");
    inputFlightId.setAttribute("value", "0");
    doc.getElementById("formEdit").submit();
}

function buttonDeleteAction() {
    if (tmpSelectedRow == null) {
        return;
    }
    if (!confirm(responseObject["flight.confirm.delete"])) {
        return;
    }

    var selectedFlightArray = tmpSelectedRow.children;
    var flight = {
        "id": Number(selectedFlightArray[0].innerText),
        "flightNumber": Number(selectedFlightArray[1].innerText),
        "startPoint": selectedFlightArray[2].innerText,
        "destinationPoint": selectedFlightArray[3].innerText,
        "departureDate": selectedFlightArray[4].innerText,
        "departureTime": selectedFlightArray[5].innerText,
        "arrivalDate": selectedFlightArray[6].innerText,
        "arrivalTime": selectedFlightArray[7].innerText,
        "plane": selectedFlightArray[8].innerText,
        "crew": {
            "id": Number(selectedFlightArray[9].innerText)
        }
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "flight/delete", true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if ("ok" === xhr.responseText) {
                var tableBody = doc.getElementById("tableBody");
                tableBody.removeChild(tmpSelectedRow);
                tmpSelectedRow = null;
            }
        }
    };
    xhr.send(JSON.stringify(flight));
}

function fillMainTable() {
    var xhr = new XMLHttpRequest();
    // xhr.open("POST", "administrator", true);
    xhr.open("POST", "welcome", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var flights = JSON.parse(this.responseText);
            createTableBody(flights);
        }
    };
    xhr.send();
}

function createTableBody(flights) {
    var tableBody = doc.getElementById("tableBody");

    for (var i = 0; i < flights.length; i++) {
        var flight = flights[i];

        var row = doc.createElement("TR");
        tableBody.appendChild(row);
        row.addEventListener("click", function() {
            selectTableRow(this);
        }, false);

        var tdId = doc.createElement("TD");
        var tdFlightNumber = doc.createElement("TD");
        var tdStartPoint = doc.createElement("TD");
        var tdDestinationPoint = doc.createElement("TD");
        var tdDepartureDate = doc.createElement("TD");
        var tdDepartureTime = doc.createElement("TD");
        var tdArrivalDate = doc.createElement("TD");
        var tdArrivalTime = doc.createElement("TD");
        var tdPlane = doc.createElement("TD");
        var tdCrewId = doc.createElement("TD");
        var tdCrewName = doc.createElement("TD");
        row.appendChild(tdId);
        row.appendChild(tdFlightNumber);
        row.appendChild(tdStartPoint);
        row.appendChild(tdDestinationPoint);
        row.appendChild(tdDepartureDate);
        row.appendChild(tdDepartureTime);
        row.appendChild(tdArrivalDate);
        row.appendChild(tdArrivalTime);
        row.appendChild(tdPlane);
        row.appendChild(tdCrewId);
        row.appendChild(tdCrewName);

        tdId.innerHTML = flight["id"];
        tdFlightNumber.innerHTML = flight["flightNumber"];
        tdStartPoint.innerHTML = flight["startPoint"];
        tdDestinationPoint.innerHTML = flight["destinationPoint"];
        tdDepartureDate.innerHTML = flight["departureDate"];
        tdDepartureTime.innerHTML = flight["departureTime"];
        tdArrivalDate.innerHTML = flight["arrivalDate"];
        tdArrivalTime.innerHTML = flight["arrivalTime"];
        tdPlane.innerHTML = flight["plane"];
        if (flight.hasOwnProperty("crew")) {
            tdCrewId.innerHTML = flight["crew"]["id"];
            tdCrewName.innerHTML = flight["crew"]["name"];
        } else {
            tdCrewId.innerHTML = "0";
        }
    }
}

function selectTableRow(row) {
    if (tmpSelectedRow != null) {
        tmpSelectedRow.classList.remove("selected");
    }
    row.classList.add("selected");
    tmpSelectedRow = row;
}

function signOut() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "signout", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            doc.location.href = "./";
        }
    };
    xhr.send();
}