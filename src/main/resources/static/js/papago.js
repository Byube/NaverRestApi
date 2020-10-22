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