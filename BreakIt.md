Zu testende Anwendung:
https://github.com/SteSt1999/Webanwendung


## Beschreibung Entwicklungsumgebung:  
Anwendungsstand: 08.11.2018 10:00  
Windows 10 Build 17134.345




Erster Scan der Anwendung mit dem OWASP ZAP:  
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

## Überschrift

### Broken Session Management  
Ein User ist eingloggt.  
In einem anderen Browser kann man nun die .jsp's direkt aufrufen und ist dann im Account aus dem anderen Browser eingeloggt.  

Es können sich mehrer User anmelden, wenn einer dann z.B. Geld abhebt landen die anderen in der Session dieses Users.  

### CSRF
Ist möglich siehe PoC CSRF.  


### Clickjacking
Ist möglich siehe PoC Clickjacking.  

### SQL Injection  
Die Anwendung ist prinzipiell anfällig für SQL Injection.  

Die Eingaben werden nicht auf Steuerzeichen überprüft und es wird kein Prepared Statement verwendet.  
An der Oberfläche ist die länge der Eingabe meistens auf 50 Zeichen begrenzt, lässt sich aber ganz einfach hochsetzten um gültige Querrys zu erzeugen.  
Folgende Eingabe in das Feld "Benutzername" auf der Seite "Mitarbeiter" ist zum Beispiel valide:    
`stein.linda";INSERT INTO MITARBEITER VALUES("2","2","2","2"); SELECT vorname FROM KUNDEN WHERE ID = "1`  

Allerdings wird diese Querry in einem Großteil der Fälle trotzdem nicht funktionieren, da der MySQL Connector/J ab Version 3.1.1 (stark veraltete legacy Version) keine Multi Querries mehr erlaubt. 

Es ist jedoch möglich einen timebased Angriff durchzuführen.  
Mit der folgenden Querry kann man Benutzernamen und die die dazugehörenden Passwörter Character für Character aus der Datenbank ziehen.  

`1 UNION SELECT IF(SUBSTRING(KENNWORT,1,1) = CHAR(50),BENCHMARK(5000000,ENCODE('MSG','by 5 seconds')),null) FROM KUNDEN WHERE ID = 1;#`  


## XSS  
Die Anwendung ist theoretisch für XSS anfällig, da keine XSS Protection enabled ist.
Allerdings lässt sich XSS praktisch nicht ausnutzen, da es auf keiner Seite eine Einfabe gibt, welche die nächste beeinflusst.
Somit lässt sich dort kein Script mit rein integrieren.


### Privilage Escalation
Da es keine Privilages gibt und die Berechtigungen davon abhängen, ob der Account in der Tabelle Kunde oder Mitarbeiter steht, lässt sich keine priviliage escalation durchführen.
