<%--
  Created by IntelliJ IDEA.
  User: hongjian.chen
  Date: 2018/6/11
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
    <script type="text/javascript">
        function setValue() {
            var fileName = document.getElementById("file").value;
            document.getElementById("name").value = fileName.substring(fileName.lastIndexOf("/") + 1);
            alert("fileName=" + fileName);
        }
    </script>
</head>
<body>
<h1>springMVC包装类上传文件</h1>
<form name="userForm2" action="heads/upload" enctype="multipart/form-data" method="post">
    <div id="newUpload2">
        <input id="file" type="file" name="file"><br>
        <input id="name" type="hidden" name="fileName" value="">
        描述： <input name="uid" type="text">
    </div>
    <input type="submit" value="上传">

</form>
</body>
</html>
