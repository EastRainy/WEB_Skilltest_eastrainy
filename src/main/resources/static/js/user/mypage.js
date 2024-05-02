
//페이지 로딩 시 초기화
function initialize() {
    initPhoneValues();
    initEmailValue();
}



//유저 데이터 업데이트 요청
function doSubmit(){
    
    //나눠져있는 phone, email 입력 조합
    phoneCombine();
    emailCombine();

    //전달할 유저데이터 선언하여 DOM 에서 가져오기
    let userData = {
        username: document.getElementById("username").value,
        personname: document.getElementById("personname").value,
        birthdate_string: document.getElementById("birthdate").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        postnum: document.getElementById("postnum").value,
        address: document.getElementById("address").value,
        address_detail: document.getElementById("address_detail").value
    }
    
    //fetch 에 전달
    userDataSubmit(userData);
}

//업데이트 요청 fetch 부분
function userDataSubmit(userData){

    fetch('/mypage/update',{
        method: 'POST',
        headers: {
                    'Content-Type': 'application/json'
                },
        body: JSON.stringify(userData)
    }).then((response) => {
        if(!response.ok){
            window.location.replace(location.protocol+'//'+location.host+'/error/'+response.status.valueOf());
        }
        else{
            window.location.replace(location.protocol+'//'+location.host+'/mypage');
        }
    })
    .catch((error)=>{
        console.log(error);
    })
}

//TODO 비밀번호 변경 기능 추가
function doPasswordChange(){

    //DOM으로부터 element 가져오기
    const passwordEle = document.getElementById("password");
    const passwordCheckEli = document.getElementById("password_check");



    //password_check과 동일값인지 판별
    
    //가져온 value 유효성 검사
    


    //완료된 비밀번호를 전송함수에 전달
}
//TODO 비밀번호 변경 기능 fetch
function passwordChangeTransfer(){

    //fetch API를 통해 서버에 전송

    //서버에서 전송된 답변을 바탕으로 반응 생성

}
//비밀번호 검증
function checkPasswordValidation(){
    





}

//phone을 -로 스플릿하여 입력란 초기화
function initPhoneValues(){
    let phoneValue = document.getElementById("phone").value;
    let splitPhoneValue = phoneValue.split("-");

    let num = 1;

    for(let n of splitPhoneValue){
        if(num<4){
            let id = 'phone'+num;
            document.getElementById(id).value = n.valueOf();
            num += 1;
        }
    }
}

// email을 @로 스플릿하여 입력란 초기화
function initEmailValue(){

    let emailValue = document.getElementById("email").value;
    let splitEmailValue = emailValue.split("@");

    document.getElementById('email1').value = splitEmailValue[0];
    document.getElementById('email2').value = splitEmailValue[1];
}

//TODO password, password_check를 위한 초기화, 코드 작성중
function initPasswordEventListener(){

    //엘리먼트 받아오기
    const password = document.getElementById("password");
    const password_check = document.getElementById("password_check");
    const password_form = document.getElementById("password_form");

    //유저가 입력한 이후 확인되는 password 이벤트 리스너
    password.addEventListener("change", (event) =>{

        //TODO 비밀번호 패턴에 따른 validate 판정 2번

        if(password.validity.valid){
            //invalid-feedback 비활성화
            //valid-feedback 활성화
        }
        else{
            //해당하는 invalid-feedback 갱신
        }
    });

    //유저가 입력한 이후 확인되는 password_check 이벤트 리스너
    password_check.addEventListener("change", (event)=>{

        if(password_check.validity.valid){
            //invalid-feedback 비활성화
            //valid-feedback 활성화
        }
        else {
            //해당하는 invalid-feedback 갱신
        }
    });


    //유저가 submit 실행 이후 확인되는 form 이벤트 리스너 추가
    //해당하는 각 검증 실패시 에러메세지 갱신
    password_form.addEventListener("submit", (event) =>{

        //password가 valid하지 않으면
        //password_check가 vaild하지 않으면
        //이벤트 submit 취소
        event.preventDefault();
    });

}


//나눠진 email값 합성하여 userData email에 넣는 함수
function emailCombine(){

    let emailValue = '';

    emailValue += document.getElementById("email1").value;
    emailValue += '@';
    emailValue += document.getElementById("email2").value;

    document.getElementById('email').value = emailValue;

}

//나눠진 phone값 합성하여 userData phone에 넣는 함수
function phoneCombine(){

    let phoneValue = '';
    for(let i = 1 ; i<4; i++){
        phoneValue += document.getElementById("phone"+i).value;
        if(i!==3){
            phoneValue += '-';
        }
    }
    document.getElementById("phone").value = phoneValue;
}

//password 검증 에러시 에러 분석하여 사용자 안내
function passwordVeliErrorDisplay(){




}

function passwordCheckVeliErrorDisplay(){



}

