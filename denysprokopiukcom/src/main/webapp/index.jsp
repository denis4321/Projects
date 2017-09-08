<%-- 
    Document   : index
    Created on : Jul 11, 2016, 3:32:48 AM
    Author     : Администратор
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XML Services</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        There are 2 implementations of XML processing services:<br>
        1. RESTful        
        <br>
        <p>
            http://denysprokopiukcom-denysproject.rhcloud.com/rest/upload
        </p>
        <br>
        2. SOAP
        <p>
            http://denysprokopiukcom-denysproject.rhcloud.com/ConsumerSoapService
            <br>
            (http://denysprokopiukcom-denysproject.rhcloud.com/ConsumerSoapService?wsdl)
        </p>        
        <br>
        Services consume XML data in 2 steps:<br>
        1. Store files in Tomcat's temp folder under Red Hat OpenShift cloud platform.<br>
        2. Use JAXB for parsing of xml content and save data to MongoDB in cloud.<br>
          All upload actions are logging with log4j and HttpFilter.<br>
        <br>
        <br>
        <br>
        There is another option available in this application - to upload xml files from tmp folder to another resources 
        <br>
        <ul>
            <li>http://localhost/1.asp</li>
            <li>http://localhost/2.jsp</li>
            <li>http://localhost:9999/3.php</li>
        <ul>        
            <br>
            To start this action please go to protected page:
            <br>
            <a href="ProtectedAction">Protected Page</a>. Basic auth. is required here based on tomcat-users.xml file in cloud.
            <br>
            Or please go to free resource:
            <br>
            <a href="ActionServlet">Free Page</a>
            <p>
                SoapUI based test project is available here: <a href="REST-Project-3-soapui-project.xml">SoapUI-test</a>
            </p>
             <p>
                .xsd schema for xml data:<a href="persons.xsd">XSD</a>
            </p>
            <p>
                .xml sample:<a href="sample.xml">XML</a>
            </p>
            
            
    </body>
</html>
