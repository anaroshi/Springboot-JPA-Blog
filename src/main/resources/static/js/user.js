/**
 * 회원가입, 
 */
let index = {
	
//	let _this = this; // on("click", function() 으로 사용시에는 반드시 this를 다른이름으로 정의해서 사용해야한다.  on("click", ()=> 람다식으로 사용시에는 this() 그냥 사용해도 인식한다.
	
	init: function(){
		
//		$("#btn-save").on("click", function(){
//			_this.save();
//		});

		// 회원가입
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		
		// 회원 정보 수정
		$("#btn-update").on("click",()=>{
			this.update();
		})		

	},
	
	save: function(){
		// 회원가입 수행 요청
		
		let params = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		console.log("params : "+JSON.stringify(params));
		
		// ajax 호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청		
		$.ajax({
			type : "POST", 								// HTTP method type(GET, POST) 형식이다.
			url : "/auth/joinProc", 					// 컨트롤러에서 대기중인 URL 주소
			data : JSON.stringify(params), 	// Json 형식의 데이터			
			contentType : "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MINE)
			dataType : "json"
		}).done(function(resp){
			//console.log("resp : "+JSON.stringify(resp));
			alert("회원 가입이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert("회원 가입에 실패되었습니다." + JSON.stringify(error));
		});
	},
	
	update: function() {
		// 회원정보 수정 수행 요청		
		// alert("회원정보 수정 수행 요청");
		let params = {
			id: $("#id").val(),
			username: $("#username").val(),
			password : $("#password").val(),
			email : $("#email").val()
		}
		console.log("params : "+JSON.stringify(params));
		
		$.ajax({
			type : "PUT",
			url : "/user",
			data : JSON.stringify(params),			
			contentType : "application/json; charset=utf-8",
			dataType : "json"
		}).done(function(resp){
			alert("회원 정보 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert("회원 정보 수정에 실패되었습니다." + JSON.stringify(error));
		});
	}	

}

index.init();