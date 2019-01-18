var doc = document;
var tmpSelectedCrewRow = null;
var tmpSelectedBaseRow = null;

window.onload = function() {
    localize();
    addEmployeeListSelect();
    addEmployeeBaseSelect();
};

function localize() {
    var employeeListRows = doc.getElementById("employeeListBody").children;
    for (var i = 0; i < employeeListRows.length; i++) {
        employeeListRows[i].children[4].innerText = employeeListRows[i].children[3].innerText.toLowerCase();
    }

    var employeeBaseRows = doc.getElementById("employeeBaseBody").children;
    for (var j = 0; j < employeeBaseRows.length; j++) {
        employeeBaseRows[j].children[4].innerText = employeeBaseRows[j].children[3].innerText.toLowerCase();
    }
}

function addEmployeeListSelect() {
    var employeeListRows = doc.getElementById("employeeListBody").children;
    for (var i = 0; i < employeeListRows.length; i++) {
        employeeListRows[i].addEventListener("click", onEmployeeListRowClick);
    }
}

function onEmployeeListRowClick() {
    if (tmpSelectedCrewRow != null) {
        tmpSelectedCrewRow.classList.remove("selected");
    }
    this.classList.add("selected");
    tmpSelectedCrewRow = this;
}

function addEmployeeBaseSelect() {
    var employeeBaseRows = doc.getElementById("employeeBaseBody").children;
    for (var j = 0; j < employeeBaseRows.length; j++) {
        employeeBaseRows[j].addEventListener("click", onEmployeeBaseRowClick);
    }
}

function onEmployeeBaseRowClick() {
    if (tmpSelectedBaseRow != null) {
        tmpSelectedBaseRow.classList.remove("selected");
    }
    this.classList.add("selected");
    tmpSelectedBaseRow = this;
}

function removeFromCrewAction() {
    if (tmpSelectedCrewRow == null) {
        return;
    }
    if (tmpSelectedBaseRow != null) {
        tmpSelectedBaseRow.classList.remove("selected");
    }
    var tableEmployeeBaseBody = doc.getElementById("employeeBaseBody");
    var tableEmployeeListBody = doc.getElementById("employeeListBody");
    tmpSelectedCrewRow.removeEventListener("click", onEmployeeListRowClick);
    tableEmployeeListBody.removeChild(tmpSelectedCrewRow);
    tableEmployeeBaseBody.appendChild(tmpSelectedCrewRow);
    tmpSelectedCrewRow.addEventListener("click", onEmployeeBaseRowClick);
    tmpSelectedBaseRow = tmpSelectedCrewRow;
    tmpSelectedCrewRow = null;
}

function addToCrewAction() {
    if (tmpSelectedBaseRow == null) {
        return;
    }

    if (tmpSelectedCrewRow != null) {
        tmpSelectedCrewRow.classList.remove("selected");
    }
    var tableEmployeeBaseBody = doc.getElementById("employeeBaseBody");
    var tableEmployeeListBody = doc.getElementById("employeeListBody");
    tmpSelectedBaseRow.removeEventListener("click", onEmployeeBaseRowClick);
    tableEmployeeBaseBody.removeChild(tmpSelectedBaseRow);
    tableEmployeeListBody.appendChild(tmpSelectedBaseRow);
    tmpSelectedBaseRow.addEventListener("click", onEmployeeListRowClick);
    tmpSelectedCrewRow = tmpSelectedBaseRow;
    tmpSelectedBaseRow = null;
}

function engageEmployeeAction() {
    var inputNewEmployeeName = doc.getElementById("newEmployeeName");
    var inputNewEmployeeSurname = doc.getElementById("newEmployeeSurname");
    var employeeSend = {
        "id": 0,
        "name": inputNewEmployeeName.value,
        "surname": inputNewEmployeeSurname.value,
        "position": doc.getElementById("newEmployeePosition").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "employee/add", true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var employeeResponse = JSON.parse(xhr.responseText);
            var row = doc.createElement("TR");
            var tdId = doc.createElement("TD");
            var tdName = doc.createElement("TD");
            var tdSurname = doc.createElement("TD");
            var tdPositionEnum = doc.createElement("TD");
            var tdPosition = doc.createElement("TD");
            row.appendChild(tdId);
            row.appendChild(tdName);
            row.appendChild(tdSurname);
            row.appendChild(tdPositionEnum);
            row.appendChild(tdPosition);
            tdId.innerText = employeeResponse["id"];
            tdName.innerText = employeeResponse["name"];
            tdSurname.innerText = employeeResponse["surname"];
            tdPositionEnum.innerText = employeeResponse["position"];
            tdPosition.innerText = employeeResponse["position"].toLowerCase(); // TODO: локализацию не забыть
            row.addEventListener("click", onEmployeeListRowClick);
            doc.getElementById("employeeListBody").appendChild(row);
            row.click();
            inputNewEmployeeName.value = "";
            inputNewEmployeeSurname.value = "";
        }
    };
    xhr.send(JSON.stringify(employeeSend));
}

function fireEmployeeAction() {
    if (tmpSelectedBaseRow == null) {
        return;
    }
    if (!confirm("File Employee?")) {
        return;
    }
    var employeeChildren = tmpSelectedBaseRow.children;
    var employee = {
        "id": employeeChildren[0].innerText,
        "name": employeeChildren[1].innerText,
        "surname": employeeChildren[2].innerText,
        "position": employeeChildren[3].innerText
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "employee/delete", true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if ("ok" === xhr.responseText) {
                var employeeBaseBody = doc.getElementById("employeeBaseBody");
                employeeBaseBody.removeChild(tmpSelectedBaseRow);
                tmpSelectedBaseRow = null;
            }
        }
    };
    xhr.send(JSON.stringify(employee));
}

function saveAction() {
    var bobtailFlight = {
        "id": doc.getElementById("flightId").value,
        "crew": {
            "id": doc.getElementById("crewId").value,
            "name": doc.getElementById("name").value,
            "employeeList": []
        }
    };
    var employeeListBodyRows = doc.getElementById("employeeListBody").children;
    for (var i = 0; i < employeeListBodyRows.length; i++) {
        var employeeFields = employeeListBodyRows[i].children;
        var employee = {
            "id": employeeFields[0].innerText,
            "name": employeeFields[1].innerText,
            "surname": employeeFields[2].innerText,
            "position": employeeFields[3].innerText
        };
        bobtailFlight["crew"]["employeeList"].push(employee);
    }
    console.log(JSON.stringify(bobtailFlight));
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "save", true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if ("ok" === xhr.responseText) {
                doc.location.href = "../../dispatcher";
            }
        }
    };
    xhr.send(JSON.stringify(bobtailFlight));
}

function cancelAction() {
    doc.location.href = "../../dispatcher";
}

function signOut() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../../signout", true);
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {

        }
    };
    xhr.send();
}