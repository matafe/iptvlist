<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload List</title>
	<style type="text/css">
		body {
			font-family: Arial, Helvetica, sans-serif;
			font-size: 14px;
		}		
	</style>
</head>
<body>
	<h2>Upload list</h2>
	<form action="upload" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username"
					placeholder="Admin username" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password"
					placeholder="Admin password" /></td>
			</tr>
		</table>
		<hr />
		<br />
		<label>Select a M3U file: </label><input type="file" name="file" /> <br />
		<br /> <input type="submit" title="Save" value="Save" />
	</form>
	<c:if test="${requestScope.errorMessage} ">
		<h3>Result:</h3>
	</c:if>

	<h4 style="color: red">${requestScope.errorMessage}</h4>
	<h4>${requestScope.message}</h4>
</body>
</html>