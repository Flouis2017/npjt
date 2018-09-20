<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>NPJT Index</title>
<style type="text/css">
    #exportDiv{
        height: 100px;
        border: 2px solid cadetblue;
        padding: 10px;
    }
    .exportSpan{
        margin-right: 10px;
        margin-left: 2px;
    }
    button{
        cursor: pointer;
    }

</style>
<script type="text/javascript" src="static/js/jquery-3.1.1.min.js" ></script>
<script type="text/javascript">

    $(function() {
        $("#btn").click(function() {
            window.location.href = "";
        });
    });

</script>
</head>
<body>
    <h2>Hello World!</h2>

    <%--<div id="exportDiv">
        <span class="exportSpan">
            <label>Username:</label>
            <input id="username" type="text" placeholder="Please input username" />
        </span>

        <span class="exportSpan">
            <label>Gender:</label>
            <select id="gender" >
                <option value="0">Please choose</option>
                <option value="1">Male</option>
                <option value="2">Female</option>
            </select>
        </span>

        <span class="exportSpan">
            <label>Age:</label>
            <input id="age" type="text" placeholder="Please input age" />
        </span>

        <button id="exportBtn">Export</button>
    </div>--%>
    <button id="btn" >Export</button>
    <br>
    <span><%=basePath%></span>

</body>
</html>
