/**
 * 게시판 (글쓰기, ) 
 */
let index = {
	
//	let _this = this; // on("click", function() 으로 사용시에는 반드시 this를 다른이름으로 정의해서 사용해야한다.  on("click", ()=> 람다식으로 사용시에는 this() 그냥 사용해도 인식한다.
	
	init: function(){

		// 게시판 글쓰기
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		

	},
	
	save: function(){
		// 글쓰기 수행 요청
		
		let params = {
			title:$("#title").val(),
			content:$("#content").val()
		};
		console.log("params : "+JSON.stringify(params));
		
		// ajax 호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청		
		$.ajax({
			type : "POST", 								// HTTP method type(GET, POST) 형식이다.
			url : "/api/boardProc", 					// 컨트롤러에서 대기중인 URL 주소
			data : JSON.stringify(params), 	// Json 형식의 데이터			
			contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MINE)
			dataType : "json"
		}).done(function(resp){
			//console.log("resp : "+JSON.stringify(resp));
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert("글쓰기에 실패되었습니다." + JSON.stringify(error));
		});
	}
	

}

index.init();