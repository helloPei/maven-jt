<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
         <tbody id="tbodyId">
           <tr>
             <td colspan="3">数据加载中...</td>
           </tr>
         </tbody>
       </table>
    </div>
    <script type="text/javascript">
       //dom事件(页面元素加载完成执行如下函数)
       window.onload=function(){
    	   doGetObjects();
    	   console.log("helloworld");
       }
       //借助此方法异步加载数据,局部更新页面
       function doGetObjects(){
    	  //1.创建ajax请求对象(ajax 引擎)
    	  var xhr=new XMLHttpRequest();
    	  console.log(xhr);
    	  //2.设置ajax请求状态监听(监听响应过程)
    	  //当请求响应状态发生变化时会执行onreadystatechange
    	  //指向的函数
    	  xhr.onreadystatechange=function(){
    		  //4表示响应结束
    		  //200表示响应正常
    		  if(xhr.readyState==4&&xhr.status==200){
    			  //输出服务端响应的结果
    			  //console.log(xhr.responseText);
    			  doHandleResponseResult(xhr.responseText);
    		  }
    	  }
          //3.建立连接并发送请求
    	  doSendPostRequest(xhr)
       }
       
      function doSendGetRequest(xhr){
         //3.1.建立与服务端的连接
         var url="doFindPageObjects.do?pageCurrent=1"
         xhr.open("GET",url,true);//true表示异步(底层会启动工作线程)
        //3.2.发送请求
         xhr.send(null);//post请求此位置传数据
      }

      function doSendPostRequest(xhr){
       //3.1.建立与服务端的连接
         var url="doFindPageObjects.do"
         xhr.open("POST",url,true);//true表示异步(底层会启动工作线程)
         //post请求必须设置此请求头
         xhr.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded");
       //3.2.发送请求
        xhr.send("pageCurrent=1");//post请求此位置传数据
       }
       
       function doHandleResponseResult(text){
    	  //1.解析text(JSON格式的串)将其转换为JSON格式的JS对象
    	  var result=JSON.parse(text);
    	  console.log(result);
    	  //2.处理结果result(JSON格式的对象)
    	  if(result.state==1){
    		 //设置table体中的内容
    		 doSetTableBodyRows(result.data.records);
    	  }else{
    		 alert(result.message);
    	  }
       }
       //设置tbody内容
       function doSetTableBodyRows(records){
    	 //1.获取tbody对象,并清空数据
    	 var tBody=document.getElementById("tbodyId");
    	 tBody.innerHTML="";//清空数据
    	 //2.在tbody中呈现records
    	 for(var i=0;i<records.length;i++){
    	   //2.1创建tr对象
    	   var tr=document.createElement("tr");
    	   //2.2创建多个td对象,并将td追加到tr中
    	   doCreateTds(tr,records[i]);
    	   //2.3将tr追加到tbody中
    	   tBody.appendChild(tr);
    	 }
       }
       function doCreateTds(tr,row){
    	   var td=document.createElement("td");
    	   td.innerText=row.id;
    	   tr.appendChild(td);
    	   
    	   td=document.createElement("td");
    	   td.innerText=row.username;
    	   tr.appendChild(td);
    	   
    	   td=document.createElement("td");
    	   td.innerText=row.operation;
    	   tr.appendChild(td);
       }
       
    </script>
</body>
</html>








