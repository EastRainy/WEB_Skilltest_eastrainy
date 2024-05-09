

//이벤트리스너 초기화
window.onload = function () {
    document.getElementById("username").addEventListener("input", function () {usernameChangeEvent()})
    document.getElementById('password').addEventListener('change', function () {passwordValidationEvent()});
    document.getElementById('password_check').addEventListener('change', function () { passwordCheckValidationEvent()});
    document.getElementById('userSelectedEmailAddress').addEventListener('change', function () {emailSelectEvent()});
    document.getElementById('formSubmit').addEventListener('click', function () {doSignup()});
    document.getElementById('usernameCheck').addEventListener('click', function () {usernameServerCheck()});
}

//회원가입 메인 함수
function doSignup(){
    //회원가입 스크립트

    const usernameElement = document.getElementById('username');
    const passwordElement = document.getElementById('password');
    const passwordCheckElement = document.getElementById('password_check');

    const usernameInput = String(usernameElement.value);
    const passwordInput = String(passwordElement.value);
    const passwordCheckInput = String(passwordCheckElement.value);

    const usernameInvalidFeedback = document.getElementById('invalidUsername');
    const passwordInvalidFeedback = document.getElementById('invalidPassword');
    const nameMessageElement = document.querySelector('.announce-name');
    const phoneMessageElement = document.querySelector('.announce-phone');
    const emailMessageElement = document.querySelector('.announce-email');
    const birthdateMessageElement = document.querySelector('.announce-birthdate');


    const form = document.getElementById('signupForm');


    let formData = {
        username: "",
        password: "",
        password_check: "",
        personname: "",
        birthdate: "",
        email: "",
        phone: "",
        postnum: "",
        address: "",
        address_detail: ""
    };

    //document 에서 입력 데이터 받아옴

    pwMessageElement.textContent = "";
    pwcheckMessageElement.textContent = "";
    phoneMessageElement.textContent = "";
    emailMessageElement.textContent="";
    birthdateMessageElement.textContent="";
    //announce 메세지 엘리먼트 초기

    //id검증
    const usernameValid = checkUsername(usernameInput);

    if(!usernameValid.valid){
        usernameInvalidFeedback.textContent = usernameValid.message;

        if(usernameElement.classList.contains('is-valid')){
            usernameElement.classList.remove('is-valid');
        }
        usernameElement.classList.add('is-invalid');
        usernameElement.focus();
        return;
    }
    formData.username = usernameInput;

    //비밀번호 검증
    const passwordValid = checkPw(passwordInput);
    if(!passwordValid.valid){
        passwordInvalidFeedback.textContent = passwordValid.message;
        if(passwordElement.classList.contains('is-valid')){
            passwordElement.classList.remove('is-valid');
        }
        passwordElement.classList.add('is-invalid');
        passwordElement.focus();
        return;
    }
    formData.password = passwordInput;

    //비밀번호 확인 검증
    if(!checkPwRepeat(passwordInput, passwordCheckInput)){
        if(passwordCheckElement.classList.contains('is-valid')){
            passwordCheckElement.classList.remove('is-valid');
        }
        passwordCheckElement.classList.add('is-invalid');
        passwordCheckElement.focus();
        return;
    }
    formData.password_check = passwordCheckInput;

    //이름 입력 임시
    if(document.getElementById('personname').value===""){
        nameMessageElement.classList.remove("announce-green");
        nameMessageElement.classList.add("announce-red");
        nameMessageElement.textContent = "이름을 입력해주세요.";
        document.getElementById('name').focus();
    }else{
        formData.personname = document.getElementById('personname').value
    }

    //핸드폰번호 검증
    try{
        formData.phone = phoneCheck();
    }catch(e){
        phoneMessageElement.classList.remove("announce-green");
        phoneMessageElement.classList.add("announce-red");
        phoneMessageElement.textContent = e.message;
        document.getElementById('phone').focus();
        return;
    }
    //이메일검증
    try{
        formData.email = emailCheck();
    }catch(e){
        emailMessageElement.classList.remove("announce-green");
        emailMessageElement.classList.add('announce-red');
        emailMessageElement.textContent = e.message;
        document.getElementById('email').focus();
        return;
    }
    //생년월일 검증
    try{
        formData.birthdate = checkbirthDate();
    }catch(e){
        birthdateMessageElement.classList.remove('announce-green');
        birthdateMessageElement.classList.add('announce-red');
        birthdateMessageElement.textContent = e.messages;
        document.getElementById('birthdate').focus();
        return;
    }

    //임시로 formData에 검증없이 바로 입력
    formData.postnum = document.getElementById("postnum").value;
    formData.address = document.getElementById("address").value;
    formData.address_detail = document.getElementById("address_detail").value;

    //구성된 formData를 Fetch API 이용 함수에 전달
    signupSubmit(formData);
}
//Fetch 이용 JSON 형식으로 form 데이터 전송
function signupSubmit(formData){
    const bottomMessageElement = document.querySelector('.announce-bottom');

    fetch('/signup',{
        method : 'POST',
        headers : {
            'Content-type' : 'application/json'
        },
        body : JSON.stringify(formData),
    })
        .then((response)=>{
            if(!response.ok){
                bottomMessageElement.classList.remove("announce-green");
                bottomMessageElement.classList.add("announce-red");
                bottomMessageElement.textContent = "네트워크 에러가 발생했습니다. 잠시 후 시도해주세요.";
            }
            return response.json();
        })
        .then((data) => {
            if(data.status === 201){
                let replacePage = location.protocol+'//'+location.host+'/signupSuccess';
                window.location.replace(replacePage);
            }
            else if (data.status === 400 || data.status === 409){
                bottomMessageElement.classList.remove("announce-green");
                bottomMessageElement.classList.add("announce-red");
                bottomMessageElement.textContent = data.responseMessage;
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

//ID 입력 변경 시 인증여부 초기화
function usernameChangeEvent(){
    const usernameElement=document.getElementById('username');
    const idInvalidMessage = document.getElementById('invalidUsername');

    if(usernameElement.classList.contains('is-valid')){
        usernameElement.classList.remove('is-valid');
        usernameElement.classList.add('is-invalid');

        idInvalidMessage.textContent = '아이디가 변경되었습니다. 다시 확인해주세요.'
    }
}
//아이디 확인 함수
//아이디 유효성 검사 후 서버에 조회하여 중복여부 확인
function usernameServerCheck(){

    const usernameElement = document.getElementById('username')
    const userInvalidFeedback = document.getElementById('invalidUsername');
    const param = {"username" : usernameElement.value};

    //유효성 검사
    if(usernameElement.validity.valueMissing){
        userInvalidFeedback.textContent = '아이디를 입력해주세요.';

        if(usernameElement.classList.contains('is-valid')){
            usernameElement.classList.remove('is-valid');
        }
        usernameElement.classList.add('is-invalid');
        return;
    }
    const usernameValid = checkUsername(usernameElement.value);

    if(!usernameValid.valid){
        userInvalidFeedback.textContent = usernameValid.message;

        if(usernameElement.classList.contains('is-valid')){
            usernameElement.classList.remove('is-valid');
        }
        usernameElement.classList.add('is-invalid');
        return;
    }

    //검사 이후 서버에 확인

    fetch('/signup/checkusername',{
        method : 'POST',
        headers: {
            'Content-type' : 'application/json;'
        },
        body : JSON.stringify(param)
    })
        .then((response)=>{
            if(!response.ok){
                userInvalidFeedback.textContent = '확인 중 오류가 발생하였습니다. 다시 시도해주세요.'

                if(usernameElement.classList.contains('is-valid')){
                    usernameElement.classList.remove('is-valid');
                    usernameElement.classList.add('is-invalid');
                }
            }
            return response.json();
        })
        .then((data)=>{
                if(data.usable==='true'){
                    if(usernameElement.classList.contains('is-invalid')) {
                        usernameElement.classList.remove('is-invalid');
                    }
                    usernameElement.classList.add('is-valid');

                }
                else{
                    userInvalidFeedback.textContent = '사용 불가능한 아이디입니다. 다른 아이디를 입력해주세요.'

                    if(usernameElement.classList.contains('is-valid')){
                        usernameElement.classList.remove('is-valid');
                    }
                    usernameElement.classList.add('is-invalid');
                }
            }
        )
        .catch((error)=>{
            console.error('Error:', error);
        })
}
//비밀번호 form 변경 시 유효성 검사 및 안내
function passwordValidationEvent(){

    const password = document.getElementById('password');
    const passwordCheck = document.getElementById("password_check");
    const invalidAnnounce = document.getElementById('invalidPassword');
    const checkInvalidAnnounce = document.getElementById('invalidPasswordCheck')


    if(passwordCheck.classList.contains('is-valid')){
        passwordCheck.classList.remove('is-valid');

        checkInvalidAnnounce.textContent = '비밀번호가 변경되었습니다.';
        passwordCheck.classList.add('is-invalid');
    }

    const validCheck = checkPw(password.value);
    if(!validCheck.valid){
        invalidAnnounce.textContent = validCheck.message;

        if(password.classList.contains('is-valid')){
            password.classList.remove('is-valid');
        }
        password.classList.add('is-invalid');
        return;
    }

    if(password.classList.contains('is-invalid')){
        password.classList.remove('is-invalid');
    }
    password.classList.add('is-valid');
}
//비밀번호확인 form 변경시 유효성 검사 및 안내
function passwordCheckValidationEvent(){
    const password = document.getElementById("password");
    const passwordCheck = document.getElementById("password_check");
    const invalidAnnounce = document.getElementById('invalidPasswordCheck')

    const passwordCheckValid = checkPwRepeat(password.value,passwordCheck.value);

    if(!passwordCheckValid){
        invalidAnnounce.textContent = '비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.';
        if(passwordCheck.classList.contains('is-valid')){
            passwordCheck.classList.remove('is-valid');
        }
        passwordCheck.classList.add('is-invalid');

        return;
    }
    if(passwordCheck.classList.contains('is-invalid')){
        passwordCheck.classList.remove('is-invalid');
    }
    passwordCheck.classList.add('is-valid');

}


//ID 유효성 체크
function checkUsername(usernameValue){

    let outputMap = {
        valid:false,
        message:''
    }
    //영문자 및 숫자만 정규식 범위 지정
    const idRegex = /^[a-zA-Z\d]+$/;

    if(usernameValue.length > 20 || usernameValue.length < 4){
        outputMap.message = 'ID는 4자 이상 20자 이하여야 합니다.';
        return outputMap;
    }
    const charCheck = idRegex.test(usernameValue);
    if(!charCheck){
        outputMap.message = 'ID는 영어 소문자, 대문자 및 숫자의 조합만 가능합니다.';
        return outputMap;
    }
    outputMap.valid = true;
    return outputMap;
}
//비밀번호 유효성 체크
function checkPw(input){
    const checkValid = /^[a-zA-Z\d!@#$%^&*()?_=+<>;:~.,`\[\]\-]*$/.test(input)
    const checkLower = /[a-z]/.test(input);
    const checkUpper = /[A-Z]/.test(input);
    const checkSpecial = /[!@#$%^&*()?_=+<>;:~.,`\[\]\-]/.test(input);
    const checkLength = input.length>9 && input.length<31;
    //정규식 통하여 각각 확인해야 하는 케이스의 경우 정의

    let outputMap = {
        valid:false,
        message:''
    }

    if(!checkValid){
        outputMap.message = '비밀번호에 허용되지 않는 문자가 포함되어 있습니다.';
        return outputMap;
    }
    if(!checkLength){
        outputMap.message = '비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.';
        return outputMap;
    }
    if(!checkLower||!checkUpper||!checkSpecial){
        outputMap.message = '비밀번호엔 영문 소문자, 대문자, 특수문자가 각각 한 개 이상 포함되어야 합니다.';
        return outputMap;
    }

    outputMap.valid = true;
    return outputMap;
}
//비밀번호 동일 체크
function checkPwRepeat(input1, input2){
    //비밀번호와 비밀번호 확인 프로세스

    if(input2 !== input1){
        return false;
        throw new Error('비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.')
    }
    return true;
}
//휴대폰 체크하여 전달
function phoneCheck(){

    let phoneInput = document.getElementById("phone").value;
    let phone2Input = document.getElementById("phone2").value;
    let phone3Input = document.getElementById("phone3").value;

    //입력 검증, 현재는 임시로 적용
    if(phoneInput==="" || phone2Input==="" || phone3Input===""){
        throw new Error("번호를 입력해주세요.")
    }
    return phoneInput +'-'+ phone2Input +'-'+ phone3Input;
}
//이메일 체크하여 전달
function emailCheck(){
    const emailID = document.getElementById("emailID").value;
    const email2 = document.getElementById("email2").value;

    //들어온 이메일 검증
    if(emailID===""||email2===""){
        throw new Error("이메일을 입력해주세요.")
    }
    return emailID+ "@" + email2;
}

//생녕월일에 대한 검사
function checkbirthDate (){
    const birthdate = document.getElementById('birthdate').value;

    if(birthdate===""){
        throw new Error("생년월일을 입력해주세요.");
    }
    return birthdate;
}

//이메일 select 변경 시 입력 form에서 value 지정
function emailSelectEvent(){

    let email2 = document.getElementById("email2");
    let selectList = document.getElementById("userSelectedEmailAddress");

    email2.value = selectList.value;
    email2.readOnly = selectList.value !== "";
}