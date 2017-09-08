<%--
    Document   : currentExercise
    Created on : 10.03.2011, 20:27:17
    Author     : RSDENIS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Редактирование упражнения"/>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<center><h3>ВЫ НАХОДИТЕСЬ В РЕДАКТОРЕ УПРАЖНЕНИЙ</h3><BR>
    <h4><font color="red">МОЖЕТЕ ВНОСИТЬ ИЗМЕНЕНИЯ</font><br>
        ДАННЫЕ ОПИСЫВАЮТ ТЕКУЩИЕ ПАРМЕТРЫ УПРАЖНЕИЯ</h4>
</center>

<c:choose>
    <c:when test="${param.action=='edit'}">
        <sql:query var="exercises_details" dataSource="jdbc/arny">
            SELECT * FROM exercises_details WHERE ID=<c:out value="${param.current}"/>
        </sql:query>
        <sql:query var="sets" dataSource="jdbc/arny">
            SELECT * FROM sets WHERE sets.exercise=<c:out value="${param.current}"/>
        </sql:query>
        <sql:query var="name" dataSource="jdbc/arny">
            SELECT * FROM exercises WHERE exercises.id=<c:out value="${param.current}"/>
        </sql:query>
    </c:when>




    <c:when test="${param.action=='add'}">
        <sql:query var="exercises_details" dataSource="jdbc/arny">
            SELECT * FROM exercises_details WHERE ID=<c:out value="${param.id}"/>
        </sql:query>
        <sql:query var="sets" dataSource="jdbc/arny">
            SELECT * FROM sets WHERE sets.exercise=<c:out value="${param.id}"/>
        </sql:query>


        <c:choose>
            <c:when test="${exercises_details.rows[0]==null}">
                <sql:update dataSource="jdbc/arny" sql="INSERT INTO exercises_details (rest,type,id) VALUES (?, ?, ?)">
                    <sql:param value="${param.rest}"/>
                    <sql:param value="${param.type}"/>
                    <sql:param value="${param.id}"/>
                </sql:update>
            </c:when>
            <c:otherwise>
                <sql:update dataSource="jdbc/arny" sql="UPDATE exercises_details SET rest=?, type=? WHERE id=?">
                    <sql:param value="${param.rest}"/>
                    <sql:param value="${param.type}"/>
                    <sql:param value="${param.id}"/>
                </sql:update>
            </c:otherwise>
        </c:choose>


        <%--   <c:forEach var="index" begin="1" end="5" varStatus="status">
        <c:set var="paramWeight" value="weight${index}"/>
        <c:set var="paramAmount" value="amount${index}"/>
     <c:if test="${param[paramAmount]!=0}">
        <sql:update dataSource="jdbc/arny" sql="INSERT INTO sets(number, weight, repeats, exercise) VALUES (?, ?, ?,?)">
            <sql:param value="${index}"/>
            <sql:param value="${param[paramWeight]}"/>
            <sql:param value="${param[paramAmount]}"/>
            <sql:param value="${param.id}"/>
        </sql:update>
    </c:if>
        </c:forEach>

        <% response.sendRedirect("exercise.jsp");%>
        --%>



        <c:forEach var="index" begin="1" end="5" varStatus="status">
            <c:set var="paramWeight" value="weight${index}"/>
            <c:set var="paramAmount" value="amount${index}"/>
            <c:if test="${param[paramAmount]!=0}">
                <c:choose>
                    <c:when test="${sets.rows[index-1]!=null}">
                        <sql:update dataSource="jdbc/arny" sql="UPDATE sets SET weight=?, repeats=? WHERE exercise=? AND number=?">
                            <sql:param value="${param[paramWeight]}"/>
                            <sql:param value="${param[paramAmount]}"/>
                            <sql:param value="${param.id}"/>
                            <sql:param value="${index}"/>
                        </sql:update>
                    </c:when>
                    <c:otherwise>
                        <sql:update dataSource="jdbc/arny" sql="INSERT INTO sets(number, weight, repeats, exercise) VALUES (?, ?, ?,?)">
                            <sql:param value="${index}"/>
                            <sql:param value="${param[paramWeight]}"/>
                            <sql:param value="${param[paramAmount]}"/>
                            <sql:param value="${param.id}"/>
                        </sql:update>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${param[paramAmount]==0}">
                <sql:update dataSource="jdbc/arny" sql="DELETE FROM sets WHERE number=? AND exercise=?">
                            <sql:param value="${index}"/>
                            <sql:param value="${param.id}"/>
                        </sql:update>
            </c:if>
        </c:forEach>
        <% response.sendRedirect("exercise.jsp");%>
    </c:when>
</c:choose>




<form method="post" action="currentExercise.jsp">
    <input type="hidden" name="id" value="<c:out value="${param.current}"/>">
    <input type="hidden" name="action" value="add">
    <c:forEach var="curName" items="${name.rows}">
        <p> Название упражнения</p>
        <p><font color="red"><c:out value="${curName.name}"/></font></p>
    </c:forEach>

    <c:set var="isEdit" value="0"/>
    <c:forEach var="row" items="${exercises_details.rows}">
        <p> Выберите тип упражнения</p>
        <c:choose>
            <c:when test="${row.type=='Базовое'}">
                <p>  <input name="type" type="radio" value="Базовое" checked>
                    Базовое<br>
                    <input name="type" type="radio" value="Изолированное">
                    Изолированное </p>
                </c:when>
                <c:when test="${row.type=='Изолированное'}">
                <p>  <input name="type" type="radio" value="Базовое" >
                    Базовое<br>
                    <input name="type" type="radio" value="Изолированное" checked>
                    Изолированное </p>
                </c:when>
            </c:choose>
        <p> Отдых между подходами в секундах<br> </p>
        <p><input type="text" name="rest" value="<c:out value="${row.rest}"/>"> </p>
            <c:set var="isEdit" value="1"/>
        </c:forEach>

    <c:if test="${isEdit==0}">
        <p> Выберите тип упражнения</p>
        <p>  <input name="type" type="radio" value="Базовое" >
            Базовое<br>
            <input name="type" type="radio" value="Изолированное" checked>
            Изолированное </p>
        <p> Отдых между подходами в секундах<br> </p>
        <p><input type="text" name="rest" value="0"> </p>
        </c:if>

    <table>
        <tr>
            <c:set var="i" value="0"/>
            <c:forEach var="r" items="${sets.rows}" varStatus="my" >
                <td>
                    <c:out value="Подход № ${my.index+1}"/><br>
                    Количество раз<br>
                    <input type="text" value="<c:out value="${r.repeats}"/>" name='<c:out value="amount${my.index+1}"/>'><br>
                    Вес<br>
                    <input type="text" value="<c:out value="${r.weight}"/>" name='<c:out value="weight${my.index+1}"/>'>
                </td>
                <c:set var="i" value="${my.index+1}"/>
            </c:forEach>
            <c:if test="${i<5}">
                <c:forEach var="j" begin="${i+1}" end="5" step="1" >
                    <td>
                        <c:out value="Подход № ${j}"/><br>
                        Количество раз<br>
                        <input type="text" value="0" name='<c:out value="amount${j}"/>'><br>
                        Вес<br>
                        <input type="text" value="0" name='<c:out value="weight${j}"/>'>
                    </td>
                </c:forEach>
            </c:if>

        </tr>
    </table>
    <p><input type="submit" value="ОБНОВИТЬ"></p>
</form>

<%@include file="/WEB-INF/jspf/footer.jspf" %>
