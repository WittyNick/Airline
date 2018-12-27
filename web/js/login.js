var formSignIn = document.getElementById("formSignIn");
var buttonSubmit = document.getElementById("buttonSubmit");

/*
Авторизация пользователя.
ajax post запрос в сервлет LoginServlet.
Получам из сервлета тип пользователя:
administrator - переход на страницу administrator.html через AdministratorServlet,
dispatcher - переход на страницу dispatcher.html через DispatcherServlet,
user - остаемся на странице login.html, очищаем поля ввода.
 */
function buttonSubmitAction() {
    var elements = formSignIn.elements;
    var xhr = new XMLHttpRequest();
    var body = "login=" + encodeURIComponent(elements[0].value) +
            "&password=" + encodeURIComponent(elements[1].value);
    xhr.open("POST", "/airline/login", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if ("administrator" === this.responseText) {
                document.location.href = "/airline/administrator";
            } else if ("dispatcher" === this.responseText) {
                document.location.href = "/airline/dispatcher";
            } else {
                elements[0].value = "";
                elements[1].value = "";
            }
        }
    };
    xhr.send(body);
}

buttonSubmit.addEventListener("click", buttonSubmitAction, false);