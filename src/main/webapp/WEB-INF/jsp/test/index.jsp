<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#chinese").click(function(){
			var ko = $("#korean").val();
			$.ajax({
				url:"/chinese",
				type:"GET",
				dataType:"json",
				data:{korean:ko},
				success:function(v){
					var chinese = v.message.result.translatedText;
					$("#china").text(chinese);
				},error:function(e){
					console.log(e);
					alert(e);
				}
			});
		});
		$("#english").click(function(){
			var ko = $("#korean").val();
			$.ajax({
				url:"/english",
				type:"GET",
				dataType:"json",
				data:{korean:ko},
				success:function(v){
					var english = v.message.result.translatedText;
					$("#usa").text(english);
				},error:function(e){
					console.log(e);
				}
			});
		});
		$("#clean").click(function(){
			location.reload();
		});
	});
</script>
<title>외국어가 안되면 뷰베 스쿨</title>
</head>
<body>
	<h1>파파고 REST API</h1>
		<table border="1">
			<thead>
				<tr>
					<th>한국어</th>
					<th>중국어</th>
					<th>영어</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" id="korean" name="korean"></td>
					<td width="200" height="30" id="china"></td>
					<td width="200" height="30" id="usa"></td>
				</tr>
			</tbody>
		</table>
		<input type="button" id="chinese" value="중국어">
		<input type="button" id="english" value="영어">
		<input type="button" id="clean" value="리셋">
</body>
</html>