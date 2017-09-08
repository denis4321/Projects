<%-- 
    Document   : registration
    Created on : 24.01.2011, 16:59:51
    Author     : RSDENIS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<c:set var="pageTitle" value="Регистрация" scope="page"/>
<c:if test="${param.submit!=null && param.pass1!=param.pass2}">
<c:set var="error" value="Пароли не совпадают!"/>
</c:if>
<c:choose>
    <c:when test="${param.submit!=null && error==null}" >
        <sql:update dataSource="jdbc/arny" sql="INSERT INTO users(login, pass, email) values(?, MD5(?), ?)">
            <sql:param value="${param.login}"/>
            <sql:param value="${param.pass1}"/>
            <sql:param value="${param.email}"/>
        </sql:update>
        <c:redirect url="user/index.jsp"/>
    </c:when>
    <c:otherwise>
    <%@include file="WEB-INF/jspf/header.jspf" %>
        <h1><font color="#ff0000"><c:out value="${error}"/></font></h1>
        <h1>Регистрация нового пользователя</h1>
        <form method="post" action="#">
            <p>Логин (ТОЛЬКО латиница)<br>
            <input type="text" name="login" size="30" value="<c:out value="${param.login}"/>"></p>
            <p>Пароль<br>
            <input type="password" name="pass1" size="30"></p>
            <p>Пароль(проверка)<br>
            <input type="password" name="pass2" size="30"></p>
            <p>E-mail<br>
            <input type="text" name="email" size="30" value="<c:out value="${param.email}"/>"><br></p>
            <p><input type="submit" name="submit" value="Зарегистрироваться"></p>
        </form>
     <%@include file="WEB-INF/jspf/footer.jspf" %>
    </c:otherwise>
</c:choose>
