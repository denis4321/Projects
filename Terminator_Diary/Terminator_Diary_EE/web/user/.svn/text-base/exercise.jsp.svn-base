<%-- 
    Document   : exercise
    Created on : 04.02.2011, 21:53:46
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<c:set var="pageTitle" value="Создание упражнения" scope="page"/>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<c:choose>
    <c:when test="${param.command=='add'}">
        <sql:update dataSource="jdbc/arny" sql="INSERT INTO exercises(name,usr) VALUES (?,(SELECT ID FROM users WHERE users.login=?))">
            <sql:param value="${param.exerciseName}"/>
            <sql:param value="${request.remoteUser}" />
        </sql:update>
    </c:when>

    <%-- <sql:update dataSource="jdbc/arny" sql="INSERT INTO exercises(name, rest, type, usr) VALUES (?, ?, ?,(SELECT ID FROM users WHERE users.login=?))">
        <sql:param value="${param.exerciseName}"/>
        <sql:param value="${param.rest}"/>
        <sql:param value="${param.type}"/>
        <sql:param value="${request.remoteUser}"/>
    </sql:update>



    </c:when>
    <c:when test="${param.command=='del'}">
         <sql:update dataSource="jdbc/arny" >
             DELETE FROM workouts_exercises WHERE workouts_exercises.exercise=<c:out value="${param.exersNumber}"/>
         </sql:update>
        <sql:update dataSource="jdbc/arny" >
             DELETE FROM sets WHERE sets.exercise=<c:out value="${param.exersNumber}"/>     
         </sql:update>
        <sql:update dataSource="jdbc/arny" >
        DELETE FROM exercises WHERE exercises.id=<c:out value="${param.exersNumber}"/>
        </sql:update>

    </c:when>
    --%>
    <c:when test="${param.command=='del'}">
        <sql:update dataSource="jdbc/arny" >
            DELETE FROM exercises_details WHERE ID=<c:out value="${param.exersNumber}"/>     
        </sql:update>
        <sql:update dataSource="jdbc/arny" >
            DELETE FROM sets WHERE sets.exercise=<c:out value="${param.exersNumber}"/>     
        </sql:update>
        <sql:update dataSource="jdbc/arny" >
            DELETE FROM workouts_exercises WHERE workouts_exercises.exercise=<c:out value="${param.exersNumber}"/>
        </sql:update>
        <sql:update dataSource="jdbc/arny" >
            DELETE FROM exercises WHERE exercises.ID=<c:out value="${param.exersNumber}"/>
        </sql:update>
        
       
    </c:when>

</c:choose>



<center>
    <h1>ДОСТУПНЫЕ УПРАЖНЕНИЯ:</h1><br>
</center>
<sql:query  var="exercises" dataSource="jdbc/arny" >
    SELECT exercises.id, exercises.name FROM exercises JOIN users ON (users.id=exercises.usr AND users.login='<c:out value="${request.remoteUser}"/>') ORDER BY exercises.id
</sql:query>
<ul>
    <c:forEach var="row" items="${exercises.rows}">
        <li><a href="currentExercise.jsp?current=<c:out value="${row.id}"/>&action=edit">  <c:out value="${row.id}"/>. <c:out value="${row.name}"/> </a> | <a href="exercise.jsp?exersNumber=<c:out value="${row.id}"/>&command=del">Удалить</a></li>
    </c:forEach>
</ul>


<center><h3>Создать упражнение</h3>    </center>
<form method="post" action="exercise.jsp">
    <input type="hidden" name="command" value="add">
    <p> Название упражнения</p>
    <p><input type="text" name="exerciseName"></p>
        <%--<p> Выберите тип упражнения</p>
        <p>  <input name="type" type="radio" value="Базовое" checked>
            Базовое<br>
            <input name="type" type="radio" value="Изолированное">
            Изолированное </p>
        <p> Отдых между подходами в секундах<br> </p>
        <p><input type="text" name="rest"> </p>
        <table>
            <tr>
                   <c:forEach var="i" begin="1" end="5" step="1"  varStatus="my">
                    <td>
                        <c:out value="Подход № ${i}"/><br>
                        Количество раз<br>
                        <input type="text" value="0" name='<c:out value="amount${i}"/>'><br>
                        Вес<br>
                        <input type="text" value="0" name='<c:out value="weight${i}"/>'>
                    </td>
                </c:forEach>--%>

    <p><input type="submit" value="Добавить упражнение"></p>
</form>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
