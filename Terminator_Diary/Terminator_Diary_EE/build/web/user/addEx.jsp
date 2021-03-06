<%--
    Document   : addEx
    Created on : 08.03.2011, 19:31:54
    Author     : RSDENIS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<c:set var="pageTitle" value="Добавление упражнений"/>
<%@include file="/WEB-INF/jspf/header.jspf"%>

<center><h3>ВЫ НАХОДИТЕСЬ В РЕДАКТОРЕ ТРЕНИРОВКИ <font color="red">
            <c:forEach var="curW" items="${curWorkout.rows}">
                <c:out value="${curW.name}"/>
            </c:forEach>
        </font></h3><BR>
    <h4><font color="red">МОЖЕТЕ ВНОСИТЬ ИЗМЕНЕНИЯ</font><br>
        ДАННЫЕ ОПИСЫВАЮТ ТЕКУЩИЕ ПАРМЕТРЫ УПРАЖНЕИЯ</h4>
</center>

<c:if test="${param.command=='edit'}">
    <sql:query var="currentExercises" dataSource="jdbc/arny">
        SELECT * FROM workouts_exercises WHERE workouts_exercises.workout=<c:out value="${param.workout}"/> ORDER BY workouts_exercises.number
    </sql:query>

    <c:forEach var="index" begin="1" end="20" varStatus="status">
        <c:set var="paramKey" value="ex${index}"/>
        <c:if test="${param[paramKey]!='null'}">
            <c:choose>
                <c:when test="${currentExercises.rows[index-1]!=null}">
                    <sql:update dataSource="jdbc/arny" sql="UPDATE workouts_exercises set exercise=(SELECT ID FROM exercises WHERE exercises.name=? AND exercises.usr=(SELECT ID FROM users WHERE users.login=?)) where workout=? and number=?">
                        <sql:param value="${param[paramKey]}"/>
                        <sql:param value="${request.remoteUser}"/>
                        <sql:param value="${param.workout}"/>
                        <sql:param value="${index}"/>
                    </sql:update>
                </c:when>

                <c:otherwise>
                    <sql:update dataSource="jdbc/arny" sql="INSERT INTO workouts_exercises(workout, exercise, number) VALUES (?, (SELECT ID FROM exercises WHERE exercises.name=? AND exercises.usr=(SELECT ID FROM users WHERE users.login=?)), ?)">
                        <sql:param value="${param.workout}"/>
                        <sql:param value="${param[paramKey]}"/>
                        <sql:param value="${request.remoteUser}"/>
                        <sql:param value="${index}"/>
                    </sql:update>
                </c:otherwise>
            </c:choose>
        </c:if>


        <c:if test="${param[paramKey]=='null'}">
            <sql:update dataSource="jdbc/arny" sql="DELETE FROM workouts_exercises WHERE workout=? AND number=? ">
                <sql:param value="${param.workout}"/>
                <sql:param value="${index}"/>
            </sql:update>
        </c:if>


    </c:forEach>

    <%response.sendRedirect("trainings.jsp");%>
</c:if>

<form method="post" action="addEx.jsp">
    <input type="hidden" name="workout" value="${param.workout}">
    <input type="hidden" name="command" value="edit">
    <sql:query  var="exercises" dataSource="jdbc/arny" >
        SELECT exercises.id, exercises.name FROM exercises JOIN users ON (users.id=exercises.usr AND users.login='<c:out value="${request.remoteUser}"/>') ORDER BY exercises.id
    </sql:query>

    <sql:query var="currentExercises" dataSource="jdbc/arny">
        SELECT * FROM workouts_exercises WHERE workouts_exercises.workout=<c:out value="${param.workout}"/> ORDER BY workouts_exercises.number
    </sql:query>
    <center>
        <table >
            <c:forEach var="i" begin="1" end="10">
                <tr>
                    <c:forEach var="j" begin="0" end="1">
                        <td align="left">
                            <c:set var="curInd" value="${i+j*10}"/>

                            <c:choose>
                                <c:when test="${currentExercises.rows[curInd-1]!=null}">
                                    <p>
                                        Текущее значение упражнения <c:out value="№ ${curInd}"/><br>
                                        <select name="ex<c:out value="${curInd}"/>">
                                            <option > null </option>
                                            <c:forEach var="row" items="${exercises.rows}">
                                                <c:choose>
                                                    <c:when test="${row.id != currentExercises.rows[curInd-1].exercise}">
                                                        <option ><c:out value="${row.name}"/></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option selected><c:out value="${row.name}"/></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </p><br>
                                </c:when>


                                <c:otherwise>



                                    <p>Добавить упражнение <c:out value="№ ${curInd}"/><br>
                                        <select name="ex<c:out value="${curInd}"/>">
                                            <option >null</option>
                                            <c:forEach var="row" items="${exercises.rows}">
                                                <option ><c:out value="${row.name}"/></option>
                                            </c:forEach>
                                        </select></p><BR>


                                </c:otherwise>


                            </c:choose>



                        </c:forEach>
                    </td>
                </tr>

            </c:forEach>
        </table>

    </center>

    <input type="submit" value="Добавить/изменить выбранные упражнения">
</form>

<%@include file="/WEB-INF/jspf/footer.jspf"%>
