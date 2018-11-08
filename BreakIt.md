Zu testende Anwendung:
https://github.com/SteSt1999/Webanwendung

Stand: 08.11.2018 10:00


10:12 Logout generiert Error 405, User ist ausgeloggt, sieht die Anwendung aber noch. Aktionen generieren Error 404

X-Frame-Options nicht gesetzt. ClickJacking möglich
XSS Protection not enabled
Passwort Autocomplete

Datenbankverbindung wird nicht geschlossen, nur ein User kann gleichzeitig auf die Anwendung zugreifen (Serverlogs)

An zwei Rechnern gleichzeitig an einem Account anmelden
* an beiden kann man geld Abheben

JSESSIONID -> Java Anwendung, 
ATMServlet -> Servlet Architektur

Fehlermeldung wenn irgendwas schiefläuft 405, keine weiteren Informationen


Business Logik:
Unsinnige Eingaben für Geldfelder werden großteils an der Oberfläche abgefangen,
eine geht und führt intern zu Errors:
- 0 eingeben
