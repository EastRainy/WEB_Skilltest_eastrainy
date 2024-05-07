

//수정 페이지 로딩 초기화
function updateInitialize(){
    initPhoneValues();
    initEmailValue();
    initPasswordEventListener();
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

//password, password_check를 위한 초기화
function initPasswordEventListener(){

    //엘리먼트 받아오기
    const password = document.getElementById('password');
    const password_check = document.getElementById('password_check');

    //유저가 입력한 이후 확인되는 password 이벤트 리스너
    password.addEventListener('change', (event) =>{

        // 비밀번호 validate 체크
        const passwordValidation = new checkPasswordValidation(password);

        if(passwordValidation.isValid()) {
            if(password.classList.contains('is-invalid')){
                password.classList.remove('is-invalid');
            }//invalid-feedback 비활성화
            if(!password.classList.contains('is-valid')) {
                password.classList.add('is-valid');
            }//valid-feedback 활성화
        }
        else {
            if (password.classList.contains('is-valid')) {
                password.classList.remove('is-valid')
            }
            if (!password.classList.contains('is-invalid')) {
                password.classList.add('is-invalid');
            }
            //해당하는 invalid-feedback 갱신
            passwordVeliErrorDisplay(password, passwordValidation.crrPatternValidity());
            //에러메세지 디스플레이
        }
    });

    //유저가 입력한 이후 확인되는 password_check 이벤트 리스너
    password_check.addEventListener('change', (event)=>{

        const equalCheck = passwordCheckEqual(password, password_check);

        if(password_check.validity.valid && equalCheck) {
            if (!password_check.classList.contains('is-valid')) {
                password_check.classList.add('is-valid');
            }//valid-feedback 활성화
            if (password_check.classList.contains('is-invalid')) {
                password_check.classList.remove('is-invalid');
            }//invalid-feedback 비활성화
        }
        else {
            if (password_check.classList.contains('is-valid')) {
                password_check.classList.remove('is-valid');
            }//valid-feedback 비활성화
            if (!password_check.classList.contains('is-invalid')) {
                password_check.classList.add('is-invalid');
            }//invalid-feedback 활성화

            passwordCheckVeliErrorDisplay(password_check, equalCheck);
            //해당하는 invalid-feedback 갱신
        }
    });
}

//class

//비밀번호 검증 class
class checkPasswordValidation{
    constructor(password) {
        this.password = password;
        this.passwordPatternValidity = {
            'notAcceptedChar' : false,
            'needToContainChar' : false
        }
    }

    isValid(){
        const password = this.password;
        const passwordPatternValidity = this.passwordPatternValidity;

        passwordPatternCheck(password,passwordPatternValidity);
        return password.validity.valid && passwordPatternValidity.notAcceptedChar && passwordPatternValidity.needToContainChar;
    }
    crrPatternValidity(){
        const password = this.password;
        const passwordPatternValidity = this.passwordPatternValidity;

        passwordPatternCheck(password,passwordPatternValidity);
        return passwordPatternValidity;
    }

}

//function

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

    //DOM 으로부터 element 가져오기
    const passwordEle = document.getElementById("password");
    const passwordCheckEle = document.getElementById("password_check");
    const passwordValidation = new checkPasswordValidation(passwordEle);

    //password 유효성 검사
    if(!passwordValidation.isValid()) {
        if (passwordEle.classList.contains('is-valid')) {
            passwordEle.classList.remove('is-valid')
        }
        if (!passwordEle.classList.contains('is-invalid')) {
            passwordEle.classList.add('is-invalid');
        }

        passwordVeliErrorDisplay(passwordEle, passwordValidation.crrPatternValidity());
        return;
    }
    //password_check과 동일값인지 판별
    const pwc_valid = passwordCheckEqual(passwordEle, passwordCheckEle);
    if(pwc_valid) {
        if (passwordCheckEle.classList.contains('is-valid')) {
            passwordCheckEle.classList.remove('is-valid');
        }
        if (!passwordCheckEle.classList.contains('is-invalid')) {
            passwordCheckEle.classList.add('is-invalid');
        }
        passwordCheckVeliErrorDisplay(passwordCheckEle, pwc_valid);
        return;
    }

    //완료된 비밀번호를 전송함수에 전달
    passwordChangeTransfer();

}
//TODO 비밀번호 변경 기능 fetch
function passwordChangeTransfer(){

    //fetch API를 통해 서버에 전송



    //서버에서 전송된 답변을 바탕으로 반응 생성

}

//비밀번호와 비밀번호확인 동일 확인
function passwordCheckEqual(password, password_check){

    return password.value === password_check.value;
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
function passwordVeliErrorDisplay(password, passwordPatternValidity){

    const passwordInvalidAnnounce = document.getElementById('invalidPassword');

    //길이에 대한 비교
    if(password.validity.valueMissing) {
        passwordInvalidAnnounce.textContent = '패스워드를 입력해주세요.';
        return;
    } else if(password.validity.tooShort || password.validity.tooLong){
        passwordInvalidAnnounce.textContent = '비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.';
        return;
    }

    //입력값 패턴에 대한 비교
    if(!passwordPatternValidity.notAcceptedChar.valueOf()){
        passwordInvalidAnnounce.textContent = '비밀번호에 허용되지 않는 문자가 포함되어 있습니다.';
    }
    else if(!passwordPatternValidity.needToContainChar.valueOf()){
        passwordInvalidAnnounce.textContent = '비밀번호엔 영문 소문자, 대문자, 특수문자가 각각 한 개 이상 포함되어야 합니다.';
    }
}
//password_check 에러시 화면안내
function passwordCheckVeliErrorDisplay(password_check,equalCheck){

    const passwordCheckInvalidAnnounce = document.getElementById('invalidPasswordCheck');

    if(password_check.validity.valueMissing){
        passwordCheckInvalidAnnounce.textContent = '비밀번호 확인을 입력해주세요.';
    }
    else if(!equalCheck){
        passwordCheckInvalidAnnounce.textContent = '비밀번호와 비밀번호 확인이 동일하지 않습니다.';
    }
}

//password 패턴으로 valid 점검
function passwordPatternCheck(password, passwordPatternValidity){

    const notAcceptRegexp = /^[a-zA-Z\d!@#$%^&*()?_=+<>;:~.,`\[\]\-]*$/;
    const needRegexp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()?_=+<>;:~.,`\[\]\-]).*$/;

    passwordPatternValidity.notAcceptedChar = notAcceptRegexp.test(password.value);
    passwordPatternValidity.needToContainChar = needRegexp.test(password.value);
}
