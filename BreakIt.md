Zu testende Anwendung:
https://github.com/SteSt1999/Webanwendung

Anwendungsstand: 08.11.2018 10:00  

X-Frame-Options nicht gesetzt. Clickjacking möglich siehe Clickjacking Demo  
XSS Protection not enabled  
Passwort Autocomplete  

An zwei Rechnern gleichzeitig an einem Account anmelden  
* an beiden kann man geld Abheben  

JSESSIONID -> Java Anwendung, 
ATMServlet -> Servlet Architektur  

Fehlermeldung wenn irgendwas schiefläuft 405, keine weiteren Informationen  


Business Logik:  
Unsinnige Eingaben für Geldfelder werden großteils an der Oberfläche abgefangen,  
eine geht und führt intern zu Errors:  
- 0 eingeben  
- Man kann die Oberflächenprüfung in der Entwicklerconsole manipulieren, das ermöglicht noch andere unsinnige Eingaben, die alle zu Fehlern führen


Scanner verwendet:  
nmap  
wmap  
sqlmap  


Application stored nichts im Cache und im Storage.  



Wenn die .jsp's lassen sich einzeln aufrufen, aber keine weiteren Interaktionen und keine Einsicht in die Daten, oder vielleicht doch (Siehe XSS Testing)...   
http://localhost:8080/ATM/ATMAuswahl.jsp?css=%3CSCRIPT%3Ealert(123)%3C/script%3E  

Bei der Auswahl der Bank lassen sich flasche Eingaben machen, wenn man die .jsps direkt aufruft und über die URL die Werte setzt:  
http://localhost:8080/Auswahl.jsp?BankID=falsche  


XSS Testing:  
http://localhost:8080/Auswahl.jsp?BankID=%3Cscript%3Ealert(123)%3C/script%3E  
http://localhost:8080/Auswahl.jsp?BankID=hostbank%22%3E%3Cscript%3Ealert(123)%3C/script%3E  
Springt weiter, obwohl keine Bank ausgewählt  



**----------------------------------------------------------------------------**

## Schwerwiegende Fehler  

### Broken Session Management  
Ein User ist eingloggt.  
In einem anderen Browser kann man nun die .jsp's direkt aufrufen und ist dann im Account aus dem anderen Browser eingeloggt.  

Es können sich mehrer User anmelden, wenn einer dann z.B. Geld abhebt landen die anderen in der Session dieses Users.  

### CSRF
Ist möglich siehe PoC CSRF.  


### Clickjacking
Ist möglich siehe PoC Clickjacking.  

