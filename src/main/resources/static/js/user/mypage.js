
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



