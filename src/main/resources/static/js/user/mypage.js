
//조회 페이지 로딩 시 초기화
function getInitialize() {
    initPhoneValues();
    initEmailValue();
}


// Init함수

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

