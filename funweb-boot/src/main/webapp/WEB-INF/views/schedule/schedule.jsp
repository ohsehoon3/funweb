<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스케줄 작업 관리</title>
</head>
<body>

	<h1>스케줄 작업 관리</h1>
	
	<fieldset>
		<legend>작업1</legend>
		
		<form action="" id="frm">
		<label for="firstTime">시작일자시각</label>
		<input type="datetime-local" name="firstTime" id="firstTime"><br>
		
		<label for="period">시간간격(초)</label>
		<input type="number" name="period" id="period"><br>
		</form>

		<input type="button" value="시작" id="btn1" data-start="true">
	
	</fieldset>
	
	<script src="/script/jquery-3.5.1.js"></script>
	<script>
		$('#btn1').click(function () {
			// start 변수는 클릭할때 실행할지 여부를 나타냄
			var $btn = $(this);
			
			var start = $(this).data('start');
			console.log('start = ' + start);
			console.log(typeof start); // boolean 자동 변환됨.
			
			var params = $('#frm').serialize();
			console.log('params = ' + params);
			
			// ajax로 스케줄링 작업 수행
			$.ajax({
				url: '/schedulePro.do',
				data: params + '&start='+start, // { 문자열(파라미터 이름): 변수 } 
				success: function (data) { // 서버 응답 data
					process(data, $btn);
				}
			});
		});
		
		function process(start, $selfBtn) {
			if (start) {
				$selfBtn.closest('fieldset').find('#firstTime').prop('disabled', false);
				$selfBtn.closest('fieldset').find('#period').prop('disabled', false);
				$selfBtn.val('시작');
			} else {
				$selfBtn.closest('fieldset').find('#firstTime').prop('disabled', true);
				$selfBtn.closest('fieldset').find('#period').prop('disabled', true);
				$selfBtn.val('종료');
			}
			$selfBtn.data('start', start);
		} // process()
		
	</script>
</body>
</html>