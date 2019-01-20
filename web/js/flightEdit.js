var doc = document;

window.onload = function() {
    localizeFligntEdit();
    doc.getElementById("lang").addEventListener("change", function() {
        var body = "locale=" + document.getElementById("lang").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "locale/change", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if ("ok" === xhr.responseText) {
                    localizeFligntEdit();
                }
            }
        };
        xhr.send(body);
    });
};

function buttonSaveAction() {
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

function buttonCancelAction() {
    doc.location.href = "../administrator";
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
