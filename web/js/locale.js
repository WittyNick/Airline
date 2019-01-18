var responseObject;

// ---------- welcome.html ----------
function localizeWelcome() {
    var requestArray = [
        "lang",
        "main",
        "administrator",
        "dispatcher",
        "sign_in",
        "sign_out",
        "schedule",
        "number",
        "from",
        "to",
        "departure_date",
        "departure_time",
        "arrival_date",
        "arrival_time",
        "plane"
    ];
    ajaxPost("locale", requestArray, applyLocaleWelcome);
}

function applyLocaleWelcome() {
    doc.getElementById("mainTab").innerHTML = responseObject["main"];
    doc.getElementById("administratorTab").firstElementChild.innerText = responseObject["administrator"];
    doc.getElementById("dispatcherTab").firstElementChild.innerText = responseObject["dispatcher"];
    doc.getElementById("sign").children[0].innerText = responseObject["sign_in"];
    doc.getElementById("sign").children[1].innerText = responseObject["sign_out"];
    doc.getElementById("content").lang = responseObject["lang"];
    doc.getElementById("tableCaption").innerText = responseObject["schedule"];
    var headElements = document.getElementById("hatRow").children;
    headElements[0].innerHTML = responseObject["number"];
    headElements[1].innerHTML = responseObject["from"];
    headElements[2].innerHTML = responseObject["to"];
    headElements[3].innerHTML = responseObject["departure_date"];
    headElements[4].innerHTML = responseObject["departure_time"];
    headElements[5].innerHTML = responseObject["arrival_date"];
    headElements[6].innerHTML = responseObject["arrival_time"];
    headElements[7].innerHTML = responseObject["plane"];
}

// ---------- signIn.html ----------
function localizeSignIn() {
    var requestArray = [
        "lang",
        "legend_sign_in",
        "login",
        "password",
        "enter",
        "cancel"
    ];
    ajaxPost("locale", requestArray, applyLocaleSignIn);
}

function applyLocaleSignIn() {
    doc.getElementById("content").lang = responseObject["lang"];
    doc.getElementById("legendFieldset").innerText = responseObject["legend_sign_in"];
    doc.getElementById("labelLogin").innerText = responseObject["login"] + ":";
    doc.getElementById("labelPassword").innerText = responseObject["password"] + ":";
    doc.getElementById("buttonSubmit").value = responseObject["enter"];
    doc.getElementById("buttonCancel").value = responseObject["cancel"];
}

// ---------- administrator.html ----------
function localizeAdministrator() {
    var requestArray = [
        "lang",
        "main",
        "administrator",
        "sign_out",
        "flights",
        "number",
        "from",
        "to",
        "departure_date",
        "departure_time",
        "arrival_date",
        "arrival_time",
        "plane",
        "crew",
        "flight.edit",
        "flight.add",
        "flight.delete"
    ];
    ajaxPost("locale", requestArray, applyLocaleAdministrator);
}

function applyLocaleAdministrator() {
    doc.getElementById("mainTab").firstElementChild.innerHTML = responseObject["main"];
    doc.getElementById("administratorTab").innerText = responseObject["administrator"];
    doc.getElementById("sign").firstElementChild.innerText = responseObject["sign_out"];
    doc.getElementById("content").lang = responseObject["lang"];
    doc.getElementById("tableCaption").innerText = responseObject["flights"];
    var headElements = document.getElementById("hatRow").children;
    headElements[1].innerHTML = responseObject["number"];
    headElements[2].innerHTML = responseObject["from"];
    headElements[3].innerHTML = responseObject["to"];
    headElements[4].innerHTML = responseObject["departure_date"];
    headElements[5].innerHTML = responseObject["departure_time"];
    headElements[6].innerHTML = responseObject["arrival_date"];
    headElements[7].innerHTML = responseObject["arrival_time"];
    headElements[8].innerHTML = responseObject["plane"];
    headElements[10].innerHTML = responseObject["crew"];
    doc.getElementById("buttonEdit").value = responseObject["flight.edit"];
    doc.getElementById("buttonAdd").value = responseObject["flight.add"];
    doc.getElementById("buttonDelete").value = responseObject["flight.delete"];
}


// ---------- dispatcher.html ----------
function localizeDispatcher() {
    var requestArray = [
        "lang",
        "main",
        "dispatcher",
        "sign_out",
        "crews",
        "number",
        "from",
        "to",
        "departure_date",
        "departure_time",
        "arrival_date",
        "arrival_time",
        "plane",
        "crew",
        "crew.edit",
        "crew.delete"
    ];
    ajaxPost("locale", requestArray, applyLocaleDispatcher);
}

function applyLocaleDispatcher() {
    doc.getElementById("mainTab").firstElementChild.innerHTML = responseObject["main"];
    doc.getElementById("dispatcherTab").innerText = responseObject["dispatcher"];
    doc.getElementById("sign").firstElementChild.innerText = responseObject["sign_out"];
    doc.getElementById("content").lang = responseObject["lang"];
    doc.getElementById("tableCaption").innerText = responseObject["crews"];
    var headElements = document.getElementById("hatRow").children;
    headElements[1].innerHTML = responseObject["number"];
    headElements[2].innerHTML = responseObject["from"];
    headElements[3].innerHTML = responseObject["to"];
    headElements[4].innerHTML = responseObject["departure_date"];
    headElements[5].innerHTML = responseObject["departure_time"];
    headElements[6].innerHTML = responseObject["arrival_date"];
    headElements[7].innerHTML = responseObject["arrival_time"];
    headElements[8].innerHTML = responseObject["plane"];
    headElements[10].innerHTML = responseObject["crew"];
    doc.getElementById("buttonEdit").value = responseObject["crew.edit"];
    doc.getElementById("buttonDelete").value = responseObject["crew.delete"];
}










// --------------------
function ajaxPost(url, requestObject, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            responseObject = JSON.parse(xhr.responseText);
            callback();
        }
    };
    xhr.send(JSON.stringify(requestObject));
}