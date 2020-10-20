<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>엘솔루 API 테스트</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#chinese").click(function(){
			var ko = $("#korean").val();
			$.ajax({
				url:"/contensChina",
				type:"GET",
				dataType:"json",
				data:{korean:ko},
				success:function(v){
					var chinese = v.outputs[0];
					$("#china").text(chinese.output);
					console.log(v);
				},error:function(e){
					console.log(e);
					alert(e);
				}
			});
		});
		$("#english").click(function(){
			var ko = $("#korean").val();
			$.ajax({
				url:"/contensEnglish",
				type:"GET",
				dataType:"json",
				data:{korean:ko},
				success:function(v){
					var english = v.outputs[0];
					$("#usa").text(english.output);
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
</head>
<body>
	<h1>엘솔루 API 테스트</h1>
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
					<td><textarea rows="50" cols="50" id="korean" name="korean"></textarea></td>
					<td><textarea rows="50" cols="50"  id="china" disabled="disabled" ></textarea></td>
					<td><textarea rows="50" cols="50" id="usa" disabled="disabled" ></textarea></td>
				</tr>
			</tbody>
		</table>
		<input type="button" id="chinese" value="중국어">
		<input type="button" id="english" value="영어">
		<input type="button" id="clean" value="리셋"><br>
	
</body>
</html>