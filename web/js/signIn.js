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
Авторизация пользователя.
ajax post запрос в сервлет LoginServlet.
Получам из сервлета тип пользователя:
administrator - переход на страницу administrator.html через AdministratorServlet,
dispatcher - переход на страницу dispatcher.html через DispatcherServlet,
user - остаемся на странице signIn.html, очищаем поля ввода.
 */
function buttonSubmitAction() {
    var inputName = doc.getElementById("login");
    var inputPassword = doc.getElementById("password");
    var xhr = new XMLHttpRequest();
    var body = "login=" + encodeURIComponent(inputName.value) +
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
                inputName.value = "";
                inputPassword.value = "";
            }
        }
    };
    xhr.send(body);
}