<%-- 
    Document   : treinings
    Created on : 24.01.2011, 16:51:50
    Author     : RSDENIS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<c:set var="pageTitle" value="Тренировки" scope="page"/>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<c:choose>
    <c:when test="${param.command=='add'}">
        <sql:update dataSource="jdbc/arny" sql="INSERT INTO workouts(name, number, usr) VALUES (?, ?, (SELECT ID FROM users WHERE users.login=?))">
            <sql:param value="${param.name}"/>
            <sql:param value="${param.number}"/>
            <sql:param value="${request.remoteUser}"/>
        </sql:update>
    </c:when>
    <c:when test="${param.command=='del'}">
          <sql:update dataSource="jdbc/arny" >
             DELETE FROM workouts_exercises WHERE workouts_exercises.workout=<c:out value="${param.workout}"/>
         </sql:update>
        <sql:update dataSource="jdbc/arny" sql="DELETE FROM workouts WHERE ID=?">
            <sql:param value="${param.workout}"/>
        </sql:update>
    </c:when>
</c:choose>
        <center>
            <h1>ДОСТУПНЫЕ ТРЕНИРОВКИ:</h1><br>
        </center>

<sql:query var="trainings" dataSource="jdbc/arny">
    SELECT workouts.id, workouts.name, workouts.number FROM workouts JOIN users ON (users.id=workouts.usr AND users.login='<c:out value="${request.remoteUser}"/>') ORDER BY workouts.number
</sql:query>
<ul>
                <c:forEach var="row" items="${trainings.rows}">
                    <li><a href="addEx.jsp?workout=<c:out value="${row.ID}"/>"><c:out value="${row.number}"/>. <c:out value="${row.name}"/></a> | <a href="trainings.jsp?workout=<c:out value="${row.ID}"/>&command=del">Удалить</a></li>
                </c:forEach>
            </ul>

        <center><h3>Создать тренировку</h3>    </center>
            <form action="trainings.jsp" method="post">
                <input type="hidden" name="command" value="add">
                <p>Название тренировки</p>
                <p><input type="text" name="name"><br>
               <p>Номер тренировки</p>
                <p><input type="text" name="number"></p>
                <p><input type="submit" value="Создать тренировку"></p>
            </form>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
