package by.gstu.airline.controller.servlet.util;

public class PageTemplate {
    private PageTemplate() {}

    public static String getFlightEditTemplate() {
        return "<!DOCTYPE html>" +
        "<html lang=\"en\">" +
        "<head>" +
        "<meta charset=\"UTF-8\">" +
        "<title>FLIGHT EDIT</title>" +
        "<link rel=\"shortcut icon\" href=\"%s\" type=\"image/x-icon\">" +
        "<link rel=\"stylesheet\" href=\"%s\">" +
        "<link rel=\"stylesheet\" href=\"%s\">" +
        "<script src=\"%s\"></script>" +
        "<script src=\"%s\"></script>" +
        "</head>" +
        "<body>" +
        "<div id=\"content\">" +
        "<div id=\"empty\"></div>" +
        "<table id=\"header\">" +
        "<tr>" +
        "<td id=\"space\"></td>" +
        "<td id=\"mainTab\" class=\"tab\"><a href=\"../\">main</a></td>" +
        "<td id=\"administratorTab\" class=\"picketTab\">administrator</td>" +
        "<td id=\"locale\">" +
        "<select id=\"lang\">" +
        "<option value=\"default\">default</option>" +
        "<option value=\"en_US\">english</option>" +
        "<option value=\"ru_RU\">русский</option>" +
        "</select>" +
        "<td id=\"sign\">" +
        "<span class=\"pseudolink\" onclick=\"signOut()\">sign out</span>" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<form id=\"formMain\">" +
        "<input id=\"id\" type=\"hidden\" value=\"%s\">" +
        "<input id=\"crewId\" type=\"hidden\" value=\"%s\">" +
        "<label id=\"labelFlightNumber\" for=\"flightNumber\">flight number:</label><br>" +
        "<input id=\"flightNumber\" type=\"text\" value=\"%s\">" +
        "<span id=\"messageFlightNumber\" class=\"message\"></span><br>" +
        "<label id=\"labelStartPoint\" for=\"startPoint\">from:</label><br>" +
        "<input id=\"startPoint\" type=\"text\" maxlength=\"30ch\" value=\"%s\">" +
        "<span id=\"messageStartPoint\" class=\"message\"></span><br>" +
        "<label id=\"labelDestinationPoint\" for=\"destinationPoint\">to:</label><br>" +
        "<input id=\"destinationPoint\" type=\"text\" maxlength=\"30ch\" value=\"%s\">" +
        "<span id=\"messageDestinationPoint\" class=\"message\"></span>" +
        "<table>" +
        "<tr>" +
        "<td>" +
        "<label id=\"labelDepartureDate\" for=\"departureDate\">departure date:</label><br>" +
        "<input id=\"departureDate\" type=\"date\" value=\"%s\">" +
        "</td>" +
        "<td>" +
        "<label id=\"labelDepartureTime\" for=\"departureTime\">time:</label><br>" +
        "<input id=\"departureTime\" type=\"time\" value=\"%s\">" +
        "</td>" +
        "<td>" +
        "<br><span id=\"messageDepartureDateTime\" class=\"message\"></span>" +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td>" +
        "<label id=\"labelArrivalDate\" for=\"arrivalDate\">arrival date:</label><br>" +
        "<input id=\"arrivalDate\" type=\"date\" value=\"%s\">" +
        "</td>" +
        "<td>" +
        "<label id=\"labelArrivalTime\" for=\"arrivalTime\">time:</label><br>" +
        "<input id=\"arrivalTime\" type=\"time\" value=\"%s\">" +
        "</td>" +
        "<td>" +
        "<br><span id=\"messageArrivalDateTime\" class=\"message\"></span>" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<label id=\"labelPlane\" for=\"plane\">plane:</label><br>\n" +
        "<input id=\"plane\" type=\"text\" maxlength=\"20ch\" value=\"%s\">" +
        "<span id=\"messagePlane\" class=\"message\"></span><br>" +
        "<input id=\"buttonSave\" type=\"button\" value=\"Save\" onclick=\"buttonSaveAction()\">" +
        "<input id=\"buttonCancel\" type=\"button\" value=\"Cancel\" onclick=\"buttonCancelAction()\">" +
        "</form>" +
        "</div>" +
        "</body>" +
        "</html>";
    }

    public static String getCrewEditTemplate() {
        return "<!DOCTYPE html>" +
        "<html lang=\"en\">" +
        "<head>" +
        "<meta charset=\"UTF-8\">" +
        "<title>CREW EDIT</title>" +
        "<link rel=\"shortcut icon\" href=\"%s\" type=\"image/x-icon\">" +
        "<link rel=\"stylesheet\" href=\"%s\">" +
        "<link rel=\"stylesheet\" href=\"%s\">" +
        "<script src=\"%s\"></script>" +
        "<script src=\"%s\"></script>" +
        "</head>"  +
        "<body>" +
        "<div id=\"content\">" +
        "<div id=\"empty\"></div>" +
        "<table id=\"header\">" +
        "<tr>" +
        "<td id=\"space\"></td>" +
        "<td id=\"mainTab\" class=\"tab\"><a href=\"../..\">main</a></td>" +
        "<td id=\"dispatcherTab\" class=\"picketTab\">dispatcher</td>" +
        "<td id=\"locale\">" +
        "<select id=\"lang\">" +
        "<option value=\"default\">default</option>" +
        "<option value=\"en_US\">english</option>" +
        "<option value=\"ru_RU\">русский</option>" +
        "</select>" +
        "</td>" +
        "<td id=\"sign\">" +
        "<span class=\"pseudolink\" onclick=\"signOut()\">sign out</span>" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<input id=\"flightId\" type=\"hidden\" value=\"%s\">" +
        "<input id=\"crewId\" type=\"hidden\" value=\"%s\">" +
        "<div id=\"editElements\">" +
        "<label id=\"labelName\" for=\"name\">crew name:</label><br>" +
        "<input id=\"name\" type=\"text\" value=\"%s\">" +
        "<span id=\"messageName\" class=\"message\"></span>" +
        "<table id=\"employeeList\">" +
        "<caption id=\"captionEmployeeList\">Employee List</caption>" +
        "<thead>" +
        "<tr id=\"hatEmployeeListRow\">" +
        "<th>id</th>" +
        "<th>Name</th>" +
        "<th>Surname</th>" +
        "<th>positionEnum</th>" +
        "<th>Position</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody id=\"employeeListBody\">%s</tbody>" +
        "</table>" +
        "<input id=\"buttonRemoveFromCrew\" type=\"button\" value=\"Remove from Crew\" onclick=\"removeFromCrewAction()\">" +
        "<table id=\"tableNewEmployee\">" +
        "<tr>" +
        "<td>" +
        "<label id=\"labelNewEmployeeName\" for=\"newEmployeeName\">name:</label><br>" +
        "<input id=\"newEmployeeName\" type=\"text\" value=\"\">" +
        "</td>" +
        "<td>" +
        "<label id=\"labelNewEmployeeSurname\" for=\"newEmployeeSurname\">surname:</label><br>" +
        "<input id=\"newEmployeeSurname\" type=\"text\">" +
        "</td>" +
        "<td>" +
        "<label id=\"labelNewEmployeePosition\" for=\"newEmployeePosition\">position:</label><br>" +
        "<select id=\"newEmployeePosition\">" +
        "<option value=\"PILOT\">pilot</option>" +
        "<option value=\"NAVIGATOR\">navigator</option>" +
        "<option value=\"COMMUNICATOR\">communicator</option>" +
        "<option value=\"STEWARDESS\">stewardess</option>" +
        "</select>" +
        "</td>" +
        "<td>" +
        "<br>" +
        "<input id=\"buttonEngageEmployee\" type=\"button\" value=\"Engage Employee\" onclick=\"engageEmployeeAction()\">" +
        "</td>" +
        "</tr>" +
        "</table>" +
        "<div id=\"messageNewEmployee\" class=\"message\"></div>" +
        "<table id=\"employeeBase\">" +
        "<caption id=\"captionEmployeeBase\">Employee Base</caption>" +
        "<thead>" +
        "<tr id=\"hatEmployeeBaseRow\">" +
        "<th>id</th>" +
        "<th>Name</th>" +
        "<th>Surname</th>" +
        "<th>positionEnum</th>" +
        "<th>Position</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody id=\"employeeBaseBody\">%s</tbody>" +
        "</table>" +
        "<input id=\"buttonAddToCrew\" type=\"button\" value=\"Add to Crew\" onclick=\"addToCrewAction()\">" +
        "<input id=\"buttonFireEmployee\" type=\"button\" value=\"Fire Employee\" onclick=\"fireEmployeeAction()\"><br>" +
        "</div>" +
        "<div id=\"buttons\">" +
        "<input id=\"buttonSave\" type=\"button\" value=\"Save\" onclick=\"saveAction()\">" +
        "<input id=\"buttonCancel\" type=\"button\" value=\"Cancel\" onclick=\"cancelAction()\">" +
        "</div>" +
        "</div>" +
        "</body>" +
        "</html>";
    }
}
