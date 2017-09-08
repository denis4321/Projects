<%-- 
    Document   : index
    Created on : 24.01.2011, 16:17:01
    Author     : RSDENIS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Страница входа" scope="page"/>
<%@include file="WEB-INF/jspf/header.jspf" %>
      <h1>Введите данные для авторизации</h1>
      <form method="post" action="j_security_check">
          <p>Логин:<br>
          <input type="text" name="j_username"></p>
          <p>Пароль:<br>
          <input type="password" name="j_password"></p>
          <input type="submit" value="Войти"> 
      </form><br>
      <a href="<%=getServletContext().getContextPath()%>/registration.jsp">Регистрация</a>
 <%@include file="WEB-INF/jspf/footer.jspf" %>
