Zu testende Anwendung:
https://github.com/SteSt1999/Webanwendung


## Beschreibung Entwicklungsumgebung:  
Anwendungsstand: 08.11.2018 10:00   
Betriebssystem: Windows 10 Build 17134.345   
IDE: IntelliJ IDEA 2018.2.5 (Ultimate Edition) Build #IU-182.4892.20  
MySQL 8.0.13   
Java 11.0.1   
Tomcat 9.0.12   





## Sammeln von Informationen
Erster Scan der Anwendung mit dem OWASP ZAP:  
X-Frame-Options nicht gesetzt. Clickjacking möglich siehe Clickjacking Demo  
XSS Protection not enabled  
Passwort Autocomplete  

An zwei Rechnern gleichzeitig an einem Account anmelden  
* an beiden kann man geld Abheben  

JSESSIONID -> Java Anwendung, 
ATMServlet -> Servlet Architektur  

Fehlermeldung wenn irgendwas schiefläuft 405, keine weiteren Informationen  


Scanner verwendet:  
nmap  
wmap  
sqlmap  


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

Es können sich immer nur ein User anmelden, wenn einer sich dann anmeldet überschreibt er die Session des ersten Users.  

### CSRF
Ist möglich siehe PoC CSRF.  


### Clickjacking
Ist möglich siehe PoC Clickjacking.  

### SQL Injection  
Die Anwendung ist prinzipiell anfällig für SQL Injection.  

Die Eingaben werden nicht auf Steuerzeichen überprüft und es wird kein Prepared Statement verwendet.  
An der Oberfläche ist die länge der Eingabe meistens auf 50 Zeichen begrenzt, lässt sich aber ganz einfach hochsetzten um gültige Querrys zu erzeugen.  
Folgende Eingabe in das Feld "Benutzername" auf der Seite "Mitarbeiter" ist zum Beispiel valide:    
`stein.linda";INSERT INTO MITARBEITER VALUES("2","2","2","2");#`

Allerdings wird diese Querry in einem Großteil der Fälle trotzdem nicht funktionieren, da der  [MySQL Connector/J ab Version 3.1.1](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html) (stark veraltete legacy Version) keine Multi Querries mehr erlaubt. 

Es ist jedoch möglich einen timebased Angriff durchzuführen.  
Mit der folgenden Querry kann man Benutzernamen und die die dazugehörenden Passwörter Character für Character aus der Datenbank ziehen.  

```1" UNION SELECT IF(SUBSTRING(KENNWORT,1,1) = CHAR(80),BENCHMARK(100000,sha1(1)),null) KENNWORT FROM KUNDEN WHERE ID = "stump.stefan```


## XSS  
Die Anwendung ist theoretisch für XSS anfällig, da keine XSS Protection enabled ist.  
Allerdings lässt sich XSS praktisch nicht ausnutzen, da es auf keiner Seite eine Einfabe gibt, welche die nächste beeinflusst.  
Somit lässt sich dort kein Script mit rein integrieren.  


