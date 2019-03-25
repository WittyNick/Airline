var doc = document;

window.onload = function() {
    localizeFlightEdit();
    doc.getElementById("lang").addEventListener("change", function() {
        var body = "locale=" + document.getElementById("lang").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "locale/change", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if ("ok" === xhr.responseText) {
                    localizeFlightEdit();
                }
            }
        };
        xhr.send(body);
    });
};

function buttonSaveAction() {
    if (!isValid()) {
        return;
    }
    var flight = {
        "id": Number(doc.getElementById("id").value),
        "flightNumber": Number(doc.getElementById("flightNumber").value),
        "startPoint": doc.getElementById("startPoint").value,
        "destinationPoint": doc.getElementById("destinationPoint").value,
        "departureDate": doc.getElementById("departureDate").value,
        "departureTime": doc.getElementById("departureTime").value,
        "arrivalDate": doc.getElementById("arrivalDate").value,
        "arrivalTime": doc.getElementById("arrivalTime").value,
        "plane": doc.getElementById("plane").value,
        "crew": {
            "id": Number(doc.getElementById("crewId").value)
        }
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../administrator/save", true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if (xhr.responseText === "ok") {
                doc.location.href = "../administrator";
            }
        }
    };
    var json = JSON.stringify(flight);
    xhr.send(json);
}

function signOut() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../signout", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            doc.location.href = "../";
        }
    };
    xhr.send();
}

var messageFlightNumberIndex = 0;
var messageStartPointIndex = 0;
var messageDestinationPointIndex = 0;
var messageDepartureDateTimeIndex = 0;
var messageArrivalDateTimeIndex = 0;
var messagePlaneIndex = 0;

function isValid() {
    var valid = true;
    var inputFlightNumber = doc.getElementById("flightNumber");
    var inputStartPoint = doc.getElementById("startPoint");
    var inputDestinationPoint = doc.getElementById("destinationPoint");
    var inputDepartureDate = doc.getElementById("departureDate");
    var inputDepartureTime = doc.getElementById("departureTime");
    var inputArrivalDate = doc.getElementById("arrivalDate");
    var inputArrivalTime = doc.getElementById("arrivalTime");
    var inputPlane = doc.getElementById("plane");

    if (!inputFlightNumber.value.trim()) {
        messageFlightNumberIndex = 1;
        valid = false;
    } else {
        if (/[0-9]/.test(inputFlightNumber.value)) {
            messageFlightNumberIndex = 0;
        } else {
            messageFlightNumberIndex = 2;
            valid = false;
        }
    }
    if (!inputStartPoint.value.trim()) {
        messageStartPointIndex = 1;
        valid = false;
    } else {
        messageStartPointIndex = 0;
    }
    if (!inputDestinationPoint.value.trim()) {
        messageDestinationPointIndex = 1;
        valid = false;
    } else {
        messageDestinationPointIndex = 0;
    }
    if (!inputDepartureDate.value || !inputDepartureTime.value) {
        messageDepartureDateTimeIndex = 3;
        valid = false;
    } else {
        messageDepartureDateTimeIndex = 0;
    }
    if (!inputArrivalDate.value || !inputArrivalTime.value) {
        messageArrivalDateTimeIndex = 3;
        valid = false;
    } else {
        messageArrivalDateTimeIndex = 0;
    }
    if (!inputPlane.value) {
        messagePlaneIndex = 1;
        valid = false;
    } else {
        messagePlaneIndex = 0;
    }
    setMessages();
    return valid;
}

// function isValidDate(value) {
//     var arrDate = value.split("-");
//     var yyyy = Number(arrDate[0]);
//     var mm = Number(arrDate[1]) - 1;
//     var dd = Number(arrDate[2]);
//     var date = new Date(yyyy, mm, dd);
//     console.log(date.getFullYear());
//     console.log(date.getMonth());
//     console.log(date.getDate());
//     return (date.getFullYear() === yyyy && date.getMonth() === mm && date.getDate() === dd);
// }

function setMessages() {
    doc.getElementById("messageFlightNumber").innerHTML = messages[messageFlightNumberIndex];
    doc.getElementById("messageStartPoint").innerHTML = messages[messageStartPointIndex];
    doc.getElementById("messageDestinationPoint").innerHTML = messages[messageDestinationPointIndex];
    doc.getElementById("messageDepartureDateTime").innerHTML = messages[messageDepartureDateTimeIndex];
    doc.getElementById("messageArrivalDateTime").innerHTML = messages[messageArrivalDateTimeIndex];
    doc.getElementById("messagePlane").innerHTML = messages[messagePlaneIndex];
}