

let usernameValidate = false;

//이벤트리스너 초기화
window.onload = function () {
    document.getElementById("username").addEventListener("change", function (e) {idChangeEvent()})
    document.getElementById('password').addEventListener('change', function (e) {passwordValidationEvent()});
    document.getElementById('password_check').addEventListener('change', function (e) { passwordCheckValidationEvent()});
    document.getElementById('userSelectedEmailAddress').addEventListener('change', function (e) {emailSelectEvent()});
    document.getElementById('formSubmit').addEventListener('click', function (e) {doSignup()});
    document.getElementById('usernameCheck').addEventListener('click', function (e) {usernameServerCheck().then(r => {})})
}

//회원가입 메인 함수
function doSignup(){
    //회원가입 스크립트

    const usernameInput = String(document.getElementById('username').value);
    const passwordInput = String(document.getElementById('password').value);
    const passwordCheckInput = String(document.getElementById('password_check').value);

    const idMessageElement = document.querySelector('.announce-id');
    const pwMessageElement = document.querySelector('.announce-pw');
    const pwcheckMessageElement = document.querySelector('.announce-pwcheck');
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

    idMessageElement.textContent = "";
    pwMessageElement.textContent = "";
    pwcheckMessageElement.textContent = "";
    phoneMessageElement.textContent = "";
    emailMessageElement.textContent="";
    birthdateMessageElement.textContent="";
    //announce 메세지 엘리먼트 초기

    //id검증
    try{
        checkId(usernameInput);
    }catch(e){
        idMessageElement.classList.remove("announce-green");
        idMessageElement.classList.add("announce-red");
        idMessageElement.textContent = e.message;
        document.getElementById('username').focus();
        return;
    }
    formData.username = usernameInput;

    //비밀번호 검증
    try{
        checkPw(passwordInput);
        idMessageElement.textContent = "사용 가능합니다.";
    }catch(e){
        pwMessageElement.classList.remove("announce-green");
        pwMessageElement.classList.add("announce-red");
        pwMessageElement.textContent = e.message;
        document.getElementById('password').focus();
        return;
    }
    formData.password = passwordInput;

    //비밀번호 확인 검증
    try{
        checkPwRepeat(passwordInput, passwordCheckInput);
    }
    catch(e){
        pwcheckMessageElement.classList.remove("announce-green");
        pwcheckMessageElement.classList.add("announce-red");
        pwcheckMessageElement.textContent = e.message;
        document.getElementById('password_check').focus();
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
            else if (data.status === 400 || data.status === 422){
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
function idChangeEvent(){
    let idElement=document.getElementById("username")
    const idMessageElement = document.querySelector('.announce-id');
    if(usernameValidate){
        if(idElement.value===""){
            idMessageElement.textContent = "";
        }else{
            idMessageElement.classList.remove("announce-green");
            idMessageElement.classList.add("announce-red");
            idMessageElement.textContent = "값이 변경되었습니다. 확인을 다시 진행해주세요."
        }
        usernameValidate = false;
    }
}
//아이디 확인 함수
//아이디 유효성 검사 후 서버에 조회하여 중복여부 확인
async function usernameServerCheck(){

    const usernameInput = document.getElementById('username').value;
    const idMessageElement = document.querySelector('.announce-id');
    const param = {"username" : usernameInput};

    if(usernameInput === ""){
        idMessageElement.textContent = "";
        return;
    }

    try{
        checkId(usernameInput);
    }catch (e){
        idMessageElement.classList.remove("announce-green");
        idMessageElement.classList.add("announce-red");
        idMessageElement.textContent = e.message;
        return;
    }

    fetch('/signup/checkusername',{
        method : 'POST',
        headers: {
            'Content-type' : 'application/json;',
        },
        body : JSON.stringify(param)
    })
        .then((response)=>{
            if(!response.ok){
            }
            return response.json();
        })
        .then((data)=>{
                if(data.usable==='true'){
                    idMessageElement.classList.remove("announce-red");
                    idMessageElement.classList.add("announce-green");
                    idMessageElement.textContent = "사용 가능한 아이디입니다.";
                    usernameValidate = true;
                }
                else{
                    idMessageElement.classList.remove("announce-green");
                    idMessageElement.classList.add("announce-red");
                    idMessageElement.textContent = "사용 불가능한 아이디입니다.";
                    usernameValidate = false;
                }
            }
        )
        .catch((error)=>{
            console.error('Error:', error);
        })
}
//비밀번호 form 변경 시 유효성 검사
function passwordValidationEvent(){
    let passwordInput = document.getElementById("password").value;
    let pwMessageElement = document.querySelector('.announce-pw');

    pwMessageElement.textContent = "";

    try{
        checkPw(passwordInput);
    }catch(error){
        pwMessageElement.classList.remove("announce-green");
        pwMessageElement.classList.add("announce-red");
        pwMessageElement.textContent = error.message;
        return;
    }

    pwMessageElement.classList.remove("announce-red");
    pwMessageElement.classList.add("announce-green");
    pwMessageElement.textContent = "사용 가능한 비밀번호입니다."
}
//비밀번호확인 form 변경시 유효성 검사
function passwordCheckValidationEvent(){
    let passwordInput = document.getElementById("password").value;
    let passwordCheckInput = document.getElementById("password_check").value;
    let pwCheckMessageElement = document.querySelector('.announce-pwcheck');

    pwCheckMessageElement.textContent="";

    try{
        checkPwRepeat(passwordInput,passwordCheckInput);
    }catch(error){
        pwCheckMessageElement.classList.remove("announce-green");
        pwCheckMessageElement.classList.add("announce-red");
        pwCheckMessageElement.textContent = error.message;
        return;
    }

    pwCheckMessageElement.textContent="";
}



//ID 유효성 체크
function checkId(usernameInput){

    //영문자 및 숫자만 정규식 범위 지정
    const idRegex = /^[a-zA-Z\d]+$/;

    if(usernameInput.length > 20 || usernameInput.length < 4){
        throw new Error('ID는 4자 이상 20자 이하여야 합니다.');
    }
    const charCheck = idRegex.test(usernameInput);
    if(!charCheck){
        throw new Error('ID는 영어 소문자, 대문자 및 숫자의 조합만 가능합니다.')
    }

}
//비밀번호 유효성 체크
function checkPw(input){
    const checkValid = /[^a-zA-Z\d!@#$%^&*()?_=+<>.,;:~`\[\]\-]/.test(input)
    const checkLower = /[a-z]/.test(input);
    const checkUpper = /[A-Z]/.test(input);
    const checkSpecial = /[!@#$%^&*()?_=+<>;:~.,`\[\]\-]/.test(input);
    const checkLength = input.length<10 || input.length>30;
    //정규식 통하여 각각 확인해야 하는 케이스의 경우 정의

    //통과하지 못한 경우 에러 생성
    if(checkValid){
        throw new Error('비밀번호에 허용되지 않는 문자가 포함되어 있습니다.');
    }
    if(checkLength){
        throw new Error('비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.');
    }
    if(!checkLower||!checkUpper||!checkSpecial){
        throw new Error('비밀번호엔 영문 소문자, 대문자, 특수문자가 각각 한 개 이상 포함되어야 합니다.')
    }
}
//비밀번호 동일 체크
function checkPwRepeat(input1, input2){
    //비밀번호와 비밀번호 확인 프로세스
    const str1 = input1.valueOf();
    const str2 = input2.valueOf();
    if(str1 !== str2){
        throw new Error('비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.')
    }
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
    return phoneInput + phone2Input + phone3Input;
}
//이메일 체크하여 전달
function emailCheck(){
    const emailID = document.getElementById("emailID").value;
    const email2 = document.getElementById("email2").value;

    //들어온 이메일 검증
    if(emailID===""||email2===""){
        throw new Error("이메일을 입력해주세요.")
    }
    return emailID+ "@" + email2
}

//생녕월일에 대한 검사
function checkbirthDate (){
    const birthdate = document.getElementById('birthdate');

    if(birthdate===""){
        throw new Error("생년월일을 입력해주세요.");
    }
    return birthdate.value;
}

//이메일 select 변경 시 입력 form에서 value 지정
function emailSelectEvent(){

    let email2 = document.getElementById("email2");
    let selectList = document.getElementById("userSelectedEmailAddress");

    email2.value = selectList.value;
    email2.readOnly = selectList.value !== "";
}