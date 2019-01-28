var doc = document;

window.onload = function() {
    localizeSignIn();
    doc.getElementById("lang").addEventListener("change", function() {
        var body = "locale=" + document.getElementById("lang").value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "locale/change", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if ("ok" === xhr.responseText) {
                    localizeSignIn();
                }
            }
        };
        xhr.send(body);
    });
    var buttonSubmit = doc.getElementById("buttonSubmit");
    buttonSubmit.addEventListener("click", buttonSubmitAction, false);
};

/*
User Authorization
ajax post request to LoginServlet.
Get user type from servlet:
administrator - go to the page administrator.html via AdministratorServlet;
dispatcher - go to the dispatcher.html page via DispatcherServlet;
user - stay on the signIn.html page, clear password input field.
 */
function buttonSubmitAction() {
    if (!isValid()) {
        return;
    }
    var inputLogin = doc.getElementById("login");
    var inputPassword = doc.getElementById("password");
    var xhr = new XMLHttpRequest();
    var body = "login=" + encodeURIComponent(inputLogin.value) +
            "&password=" + encodeURIComponent(inputPassword.value);
    xhr.open("POST", "signin", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if ("administrator" === this.responseText) {
                doc.location.href = "administrator";
            } else if ("dispatcher" === this.responseText) {
                doc.location.href = "dispatcher";
            } else {
                inputPassword.value = "";
                inputLogin.select();
                messageFailIndex = 3;
                doc.getElementById("messageFail").innerText = messages[messageFailIndex];
            }
        }
    };
    xhr.send(body);
}

var messagePasswordIndex = 0;
var messageLoginIndex = 0;
var messageFailIndex = 0;

function isValid() {
    var valid = true;
    var inputLogin = doc.getElementById("login");
    var inputPassword = doc.getElementById("password");
    if (!inputPassword.value) {
        messagePasswordIndex = 2;
        inputPassword.focus();
        valid = false;
    } else {
        messagePasswordIndex = 0;
    }
    if (!inputLogin.value.trim()) {
        messageLoginIndex = 1;
        inputLogin.select();
        valid = false;
    } else {
        messageLoginIndex = 0;
    }
    messageFailIndex = 0;
    setMessages();
    return valid;
}

function setMessages() {
    doc.getElementById("messagePassword").innerText = messages[messagePasswordIndex];
    doc.getElementById("messageLogin").innerText = messages[messageLoginIndex];
    doc.getElementById("messageFail").innerText = messages[messageFailIndex];
}