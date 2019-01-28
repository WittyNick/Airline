var doc = document;

/*
Send a POST request to the servlet.
Gets an array of JSON Flights.
Calls the fillTable() function.
*/
window.onload = function() {
    setUserPageView();
    localizeWelcome();
    doc.getElementById("lang").addEventListener("change", function() {
        var body = "locale=" + document.getElementById("lang").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "locale/change", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if ("ok" === xhr.responseText) {
                    localizeWelcome();
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
    var tableBody = doc.getElementById("tableBody");

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

function setUserPageView() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "user/validate", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var sign = doc.getElementById("sign");
            var administratorTab = doc.getElementById("administratorTab");
            var dispatcherTab = doc.getElementById("dispatcherTab");
            if ("guest" === xhr.responseText) {
                sign.children[1].classList.add("hidden");
                administratorTab.classList.add("hidden");
                dispatcherTab.classList.add("hidden");
            } else if ("administrator" === xhr.responseText) {
                sign.children[0].classList.add("hidden");
                dispatcherTab.classList.add("hidden");
            } else if ("dispatcher" === xhr.responseText) {
                sign.children[0].classList.add("hidden");
                administratorTab.classList.add("hidden");
            }
        }
    };
    xhr.send();
}

function signOut() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "signout", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            doc.getElementById("sign").children[0].classList.remove("hidden");
            setUserPageView();
        }
    };
    xhr.send();
}