<%-- 
    Document   : error
    Created on : 26.02.2011, 17:21:11
    Author     : Конарх
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintWriter" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ОШИБКА!</title>
    </head>
    <body bgcolor="#FF0000">
       <b><%=exception.toString()%></b>
       <pre>
            <%exception.printStackTrace(new PrintWriter(out));%>
       </pre>
    </body>
</html>
