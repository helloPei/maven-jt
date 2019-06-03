<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>日志列表页面</h1>
    <h1><%=new java.util.Date()%></h1>
    <div>
       <table width="100%" border="1" cellpadding="3" cellspacing="0">
         <thead>
            <tr>
              <th>id</th><!-- 特殊的td -->
              <th>username</th>
              <th>operation</th>
            </tr>
         </thead>
         <tbody>
           <c:forEach items="${pageObject.records}"
                      var="item">
             <tr>
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.operation}</td>
             </tr>
           </c:forEach> 
         </tbody>
       </table>
    </div>
</body>
</html>