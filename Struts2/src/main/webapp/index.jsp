<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文件上传示例</title>
</head>
<body>
<h2>文件上传示例</h2>
<s:form action="upload" method="post" enctype="multipart/form-data">
    <s:file name="Upload" label="请选择文件" />
    <s:submit value="上传文件" />
</s:form>
</body>
</html>