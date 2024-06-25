

//이벤트리스너 초기화
window.onload = function () {
    document.getElementById("username").addEventListener("input", function () {usernameChangeEvent()})
    document.getElementById('userSelectedEmailAddress').addEventListener('change', function () {emailSelectEvent()});
    document.getElementById('formSubmit').addEventListener('click', function () {doSignup()});
    document.getElementById('usernameCheck').addEventListener('click', function () {usernameServerCheck()});

    initPhoneChoice();
    initPasswordValidation();
    phoneInputControlEvent();
}

let usernameChecked = false;

//formData 초기화
function clearFormData(formData){
    formData.username = '';
    formData.password = ''
    formData.password_check= '';
    formData.personname= '';
    formData.birthdate= '';
    formData.email= '';
    formData.phone= '';
    formData.postnum= '';
    formData.address= '';
    formData.address_detail= '';
}

//회원가입 메인 함수
function doSignup(){
    //회원가입 스크립트

    //사용 엘리먼트 초기화
    const usernameElement = document.getElementById('username');
    const passwordElement = document.getElementById('password');
    const passwordCheckElement = document.getElementById('password_check');
    const personnameElement = document.getElementById('personname');
    const phoneElements = [document.getElementById('phone1'),
        document.getElementById('phone2') ,document.getElementById('phone3')];
    const emailElements = [document.getElementById('emailID'), document.getElementById('emailAddress')];
    const birthdateElement = document.getElementById('birthdate');
    const postnumElement = document.getElementById('postnum');
    const addressElement = document.getElementById('address');
    const addressDetailElement = document.getElementById('address_detail');
    let invalidElements = [];

    const usernameInput = String(usernameElement.value);
    const passwordInput = String(passwordElement.value);
    const passwordCheckInput = String(passwordCheckElement.value);

    //피드백 엘리먼트
    const usernameInvalidFeedback = document.getElementById('invalidUsername');
    const passwordInvalidFeedback = document.getElementById('invalidPassword');
    const personnameInvalidFeedback = document.getElementById('invalidPersonname');
    const phoneInvalidFeedback = document.querySelector('.announce-phone');
    const emailInvalidFeedback = [document.getElementById('invalidEmailId'),
        document.getElementById('invalidEmailAddress')];
    const invalidAddressFeedback = document.getElementById('invalidAddress');
    const invalidAddressDetailFeedback = document.getElementById('invalidAddressDetail');


    let formData = {
        username: '',
        password: '',
        password_check: '',
        personname: '',
        birthdate: '',
        email: '',
        phone: '',
        postnum: '',
        address: '',
        address_detail: ''
    };

    //중복체크를 먼저 진행해서 확인해야지만 제출가능하도록 변경
    if(!usernameChecked){
        usernameInvalidFeedback.textContent = '아이디 확인을 먼저 진행해야 합니다.';
        changeIsValid(usernameElement, false);
        usernameElement.focus();
        return;
    }

    //id 검증
    const usernameValid = checkUsername(usernameInput);
    if(!usernameValid.valid){
        usernameInvalidFeedback.textContent = usernameValid.message;
        changeIsValid(usernameElement, false);
        invalidElements.push(usernameElement);
    }
    formData.username = usernameInput;

    //비밀번호 검증
    const passwordValid = checkPw(passwordInput);
    if(!passwordValid.valid){
        passwordInvalidFeedback.textContent = passwordValid.message;
        changeIsValid(passwordElement, false);
        invalidElements.push(passwordElement);
    }
    formData.password = passwordInput;

    //비밀번호확인 검증
    if(!checkPwRepeat(passwordInput, passwordCheckInput)){
        changeIsValid(passwordCheckElement, false);
        invalidElements.push(passwordCheckElement);
    }
    formData.password_check = passwordCheckInput;

    //이름 입력 검증
    if(!checkPersonname(personnameElement, personnameInvalidFeedback)){
        invalidElements.push(personnameElement);
    }
    formData.personname = personnameElement.value;

    //이메일검증
    const emailData = checkEmail(emailElements, emailInvalidFeedback);
    if(emailData===''){
        invalidElements.push(emailElements);
    }
    formData.email = emailData;

    //핸드폰번호 검증
    const phoneData = checkPhone(phoneElements, phoneInvalidFeedback);
    if(phoneData===''){
        invalidElements.push(phoneElements);
    }
    formData.phone = phoneData;

    //생년월일 검증
    if(!checkBirthdate(birthdateElement)){
        invalidElements.push(birthdateElement);
    }
    formData.birthdate = birthdateElement.value;

    //주소 검증
    if(!checkAddress(postnumElement, addressElement, addressDetailElement, invalidAddressFeedback, invalidAddressDetailFeedback)){
        invalidElements.push(addressDetailElement);
    }
    formData.postnum = document.getElementById('postnum').value;
    formData.address = document.getElementById('address').value;
    formData.address_detail = document.getElementById('address_detail').value;


    //모두 체크 후 가장 먼저 실패한 곳으로 포커스
    if (invalidElements.length > 0) {
        invalidElements[0].focus();
        clearFormData(formData);
        return;
    }


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
        idInvalidMessage.textContent = '아이디가 변경되었습니다. 다시 확인해주세요.';
        changeIsValid(usernameElement, false);
        usernameChecked = false;
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
        changeIsValid(usernameElement, false);

        return;
    }
    const usernameValid = checkUsername(usernameElement.value);

    if(!usernameValid.valid){
        userInvalidFeedback.textContent = usernameValid.message;
        changeIsValid(usernameElement, false);

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
                userInvalidFeedback.textContent = '확인 중 오류가 발생하였습니다. 다시 시도해주세요.';
                changeIsValid(usernameElement, false);
            }
            return response.json();
        })
        .then((data)=>{
                if(data.usable==='true'){
                    usernameChecked = true;
                    changeIsValid(usernameElement, true);
                }
                else{
                    userInvalidFeedback.textContent = '중복된 아이디입니다. 다른 아이디를 입력해주세요.';
                    usernameChecked = false;
                    changeIsValid(usernameElement, false);
                }
            }
        )
        .catch((error)=>{
            console.error('Error:', error);
        })
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

//비밀번호, 비밀번호 확인 form 유효성 검사 및 안내 초기화
function initPasswordValidation(){

    const passwordElement = document.getElementById('password');
    const passwordCheckElement = document.getElementById('password_check');
    const invalidAnnounce = document.getElementById('invalidPassword');
    const checkInvalidAnnounce = document.getElementById('invalidPasswordCheck');

    //비밀번호 입력 검증
    passwordElement.addEventListener('change', function(){

        if(passwordCheckElement.classList.contains('is-valid')){
            checkInvalidAnnounce.textContent = '비밀번호가 변경되었습니다.';
            changeIsValid(passwordCheckElement, false);
        }
        const validCheck = checkPw(passwordElement.value);
        if(!validCheck.valid){
            invalidAnnounce.textContent = validCheck.message;
            changeIsValid(passwordElement, false);
            return;
        }
        changeIsValid(passwordElement, true);
    })
    //비밀번호확인 입력 검증
    passwordCheckElement.addEventListener('change', function(){

        let passwordCheckValid = checkPwRepeat(passwordElement.value, passwordCheckElement.value);
        if(!passwordCheckValid){
            checkInvalidAnnounce.textContent = '비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.';
            changeIsValid(passwordCheckElement, false);
            return;
        }
        changeIsValid(passwordCheckElement, true);
    })
}
//비밀번호 유효성 체크 함수
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

    if(!input2.length>0) return false;

    return input2 === input1;

}
//이름 체크
function checkPersonname(nameElement, nameInvalidFeedback){

    if(nameElement.validity.valueMissing){
        nameInvalidFeedback.textContent = '이름을 입력해주세요.';
        changeIsValid(nameElement, false);

        return false;
    }
    if(nameElement.validity.tooLong){
        nameInvalidFeedback.textContent = '입력된 이름의 길이가 너무 깁니다.'
        changeIsValid(nameElement, false);

        return false;
    }

    changeIsValid(nameElement, true);
    return true;
}
//휴대폰 체크하여 전달
function checkPhone(phoneElements, phoneInvalidFeedback){

    //만약 입력되지 않은 값이 있으면 invalid 처리
    phoneElements.forEach((element) => {
        if(element.validity.valueMissing){
            phoneInvalidFeedback.textContent = '전화번호를 입력해주세요.';
            changeIsValid(element,false);
        }
        else{
            changeIsValid(element, true);
        }
    });

    //하나라도 invalid 처리된 부분이 있으면 빈칸리턴
    if(phoneElements.some((element) =>{
        return element.classList.contains('is-invalid');
    })) {return '';}

    phoneInvalidFeedback.textContent='';
    phoneElements.forEach((element)=>{
       changeIsValid(element, true);
    });
    return phoneElements[0].value +'-'+ phoneElements[1].value +'-'+ phoneElements[2].value;
}
//이메일 체크하여 전달
function checkEmail(emailElements, emailInvalidFeedbacks){

    for(let i=0; i<emailElements.length; i++){
        if(emailElements[i].validity.valueMissing){
            emailInvalidFeedbacks[i].textContent='내용을 입력해주세요.';
            changeIsValid(emailElements[i], false);
        }
        else{
            changeIsValid(emailElements[i], true);
        }
    }

    if(emailElements.some((element)=>{
        return element.classList.contains('is-invalid');
    })){return '';}

    for(const element of emailElements){
     changeIsValid(element, true);
    }
    return emailElements[0].value + "@" + emailElements[1].value;
}

//생년월일에 대한 검사
function checkBirthdate (birthdateElement){

    const birthdateAnnouncement = document.getElementById('invalidBirthdate');

    if(birthdateElement.validity.valueMissing){
        changeIsValid(birthdateElement, false);
        birthdateAnnouncement.textContent = '생년월일을 입력해주세요.';
        birthdateElement.focus();
        return false;
    }
    if(birthdateElement.validity.rangeOverflow || birthdateElement.validity.rangeUnderflow){
        changeIsValid(birthdateElement, false);
        birthdateAnnouncement.textContent = '유효한 범위의 날자를 입력해주세요.';
        birthdateElement.focus();
        return false;
    }
    changeIsValid(birthdateElement, true);
    return true;
}

//이메일 select 변경 시 입력 form에서 value 지정
function emailSelectEvent(){

    let emailAddress = document.getElementById("emailAddress");
    let selectList = document.getElementById("userSelectedEmailAddress");

    emailAddress.value = selectList.value;
    emailAddress.readOnly = selectList.value !== "";
}
//주소 입력 검증
function checkAddress(postnumElement ,addressElement, addressDetailElement,
                      invalidAddress, invalidAddressDetail){

    //validity.valueMissing이 안되서 length로 처리
    if(postnumElement.value.length === 0 || addressElement.value.length === 0){
        invalidAddress.textContent = '주소를 입력해주세요.'

        changeIsValid(postnumElement, false);
        changeIsValid(addressElement, false);

        return false;
    }
    changeIsValid(postnumElement, true);
    changeIsValid(addressElement, true);
    if(addressDetailElement.validity.valueMissing){
        invalidAddressDetail.textContent = '상세주소를 입력해주세요.';

        changeIsValid(addressDetailElement, false);
        return false;
    }
    changeIsValid(addressDetailElement, true);

    return true;
}

//휴대폰 입력창에서 숫자가 아닌 입력값은 실시간으로 지움처리
function phoneInputControlEvent(){
    const phoneElements = [document.getElementById('phone2'),
        document.getElementById('phone3')];

    for(let phoneElement of phoneElements){
        phoneElement.addEventListener('input', (event) => {
            phoneElement.value = phoneElement.value.replace(/[^0-9]/g, '');
        });
    }
}
//번호 선택지 초기화
function initPhoneChoice(){
    const element = document.getElementById('phone1');
    let exchangeNums = ['02', '031', '032', '033', '041', '042', '043', '044', '051', '052',
        '053', '054', '055', '061', '062', '063', '064', '010', '011', '016', '017', '018', '019'];
    console.log(0);
    for(let num of exchangeNums){
        console.log(1);
        let option = document.createElement('option');
        option.textContent = num;
        option.value = num;
        element.append(option);
    }
}

//input 의 is-valid 여부를 교체하는 공통함수
function changeIsValid(TargetElement, targetStatus){
    //입력받은 타겟 엘리면트의 클래스를 타겟 스테이터스를 참고하여 변경
    //true -> is-valid, false -> is-invalid
    if(targetStatus){
        TargetElement.classList.contains('is-invalid') ? TargetElement.classList.remove('is-invalid') : null ;
        TargetElement.classList.add('is-valid');
    } else {
        TargetElement.classList.contains('is-valid') ? TargetElement.classList.remove('is-valid') : null;
        TargetElement.classList.add('is-invalid');
    }
}
