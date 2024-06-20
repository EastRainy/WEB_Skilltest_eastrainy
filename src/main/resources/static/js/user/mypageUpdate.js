

//수정 페이지 로딩 초기화
function updateInitialize(){
    initPhoneValues();
    initEmailValue();
    initEventListener();
}
//페이지 초기화 중 이벤트 리스너 초기화
function initEventListener(){

    initPasswordEventListener();
    initPhoneInputControlEvent();

}


//class-----

//비밀번호 검증 class
class checkPasswordValidation{
    constructor(password) {
        this.password = password;
        this.passwordPatternValidity = {
            'notAcceptedChar' : false,
            'needToContainChar' : false
        }
        passwordPatternCheck(this.password,this.passwordPatternValidity);
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

//function--------


// phone 값 입력란 초기화
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

    document.getElementById('emailID').value = splitEmailValue[0];
    document.getElementById('emailAddress').value = splitEmailValue[1];
}

//password, password_check를 위한 초기화
function initPasswordEventListener(){

    //엘리먼트 받아오기
    const passwordEle = document.getElementById('password');
    const passwordCheckEle = document.getElementById('password_check');

    //유저가 입력한 이후 확인되는 password 이벤트 리스너
    passwordEle.addEventListener('change', (event) =>{

        // 비밀번호 validate 체크
        const passwordValidation = new checkPasswordValidation(password);

        if(passwordValidation.isValid()) {
            changeIsValid(passwordEle, true);
        }
        else {
            changeIsValid(passwordEle, false);
            //해당하는 invalid-feedback 갱신
            passwordVeliErrorDisplay(passwordEle, passwordValidation.crrPatternValidity());
            //에러메세지 디스플레이
        }
    });

    //유저가 입력한 이후 확인되는 password_check 이벤트 리스너
    passwordCheckEle.addEventListener('change', (event)=>{

        const equalCheck = passwordCheckEqual(passwordEle, passwordCheckEle);

        if(passwordCheckEle.validity.valid && equalCheck) {

            changeIsValid(passwordCheckEle, true);
            //invalid-feedback 비활성화
        }
        else {
            changeIsValid(passwordCheckEle, false);
            //invalid-feedback 활성화
            passwordCheckValidErrorDisplay(password_check, equalCheck);
            //해당하는 invalid-feedback 갱신
        }
    });
}
//휴대폰 입력창에서 숫자가 아닌 입력값은 실시간으로 지움처리
function initPhoneInputControlEvent(){
    const phoneElements = [document.getElementById('phone1'), document.getElementById('phone2'),
        document.getElementById('phone3')];

    for(let phoneElement of phoneElements){
        phoneElement.addEventListener('input', (event) => {
            phoneElement.value = phoneElement.value.replace(/[^0-9]/g, '');
        });
    }
}

//유저 데이터 업데이트 요청
function doSubmit(){

    //Element 초기화
    const usernameElement = document.getElementById('username');
    const emailElements = [document.getElementById('emailID'), document.getElementById('emailAddress')];
    const formEmailElement =  document.getElementById('email');
    const personnameElement = document.getElementById('personname');
    const birthdateElement = document.getElementById('birthdate');
    const phoneElements = [document.getElementById('phone1'), document.getElementById('phone2'),
        document.getElementById('phone3')];
    const formPhoneElement = document.getElementById('phone');
    const postnumElement = document.getElementById('postnum');
    const addressElement = document.getElementById('address');
    const addressDetailElement = document.getElementById('address_detail');

    const invalidEmailFeedbackElements = [document.getElementById('invalidEmailId'), document.getElementById('invalidEmailAddress')];
    const invalidPersonnameFeedbackElement = document.getElementById('invalidPersonname');
    const invalidBirthdateFeedbackElement = document.getElementById('invalidBirthdate');
    const invalidPhoneFeedbackElement = document.getElementById('invalidPhone');
    const invalidAddressFeedbackElement = document.getElementById('invalidAddress');
    const invalidAddressDetailFeedbackElement = document.getElementById('invalidAddressDetail');

    const invalidElements = [];

    /* 각 입력 데이터에 맞는 validation 추가*/

    //이메일 확인
    if(!checkEmail(emailElements, invalidEmailFeedbackElements, formEmailElement)){
        invalidElements.push(emailElements[0]);
    }
    //이름 확인
    if(!checkPersonname(personnameElement, invalidPersonnameFeedbackElement)){
        invalidElements.push(personnameElement);
    }
    //생년월일 확인
    if(!checkBirthdate(birthdateElement, invalidBirthdateFeedbackElement)){
        invalidElements.push(birthdateElement);
    }
    //전화번호 확인
    if(!checkPhone(phoneElements,invalidPhoneFeedbackElement)){
        invalidElements.push(phoneElements[0]);
    }
    //주소 확인
    if(!checkAddress(postnumElement, addressElement, addressDetailElement, invalidAddressFeedbackElement, invalidAddressDetailFeedbackElement)){
        invalidElements.push(addressDetailElement);
    }

    //통과하지 못한 요소가 있을 경우 가장 첫번째 포커스하고 리턴
    if(invalidElements.length>0){
        invalidElements.shift().focus();
        return;
    }

    //조합이 필요한 번호, 이메일 조합
    combinePhone(phoneElements, formPhoneElement);
    combineEmail(emailElements, formEmailElement);
    
    //이상이 없을 경우 전달할 유저데이터 선언하여 DOM 에서 가져오기
    let userData = {
        username: usernameElement.value,
        email: formEmailElement.value,
        personname: personnameElement.value,
        birthdate_string: birthdateElement.value,
        phone: formPhoneElement.value,
        postnum: postnumElement.value,
        address: addressElement.value,
        address_detail: addressDetailElement.value
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
    }).catch((error)=>{
        console.log(error);
    })
}

//비밀번호 변경기능
function doPasswordChange(){

    //DOM 으로부터 element 가져오기
    const passwordEle = document.getElementById('password');
    const passwordCheckEle = document.getElementById('password_check');
    const passwordValidation = new checkPasswordValidation(passwordEle);
    const passwordForm = document.getElementById('password_form');

    //password 유효성 검사
    if(!passwordValidation.isValid()) {
        changeIsValid(passwordEle, false);
        passwordVeliErrorDisplay(passwordEle, passwordValidation.crrPatternValidity());
        return;
    }
    //password_check과 동일값인지 판별
    const pwc_valid = passwordCheckEqual(passwordEle, passwordCheckEle);
    if(!pwc_valid) {
        changeIsValid(passwordCheckEle, false);
        passwordCheckValidErrorDisplay(passwordCheckEle, pwc_valid);
        return;
    }
    const pwData = {
        password : passwordEle.value,
        password_check : passwordCheckEle.value
    }

    //확인완료된 비밀번호를 전송함수에 전달
    passwordChangeTransfer(pwData);
    //비밀번호 모달 초기화
    $('#passwordChangeModal').modal('hide');
    passwordForm.reset();

}
//비밀번호 변경 기능 fetch
function passwordChangeTransfer(pwData){

    const announce = document.getElementById('passwordAnnounce');

    //fetch API를 통해 서버에 전송
    fetch('/mypage/update/password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pwData)
    }).then(response=>{
        if(response.ok || response.status===409){
            return response.json();
        }
        else{
            window.location.replace(location.protocol+'//'+location.host+'/error/'+response.status.valueOf());
        }
    }).then((data)=>{
        if(data.status === 200){
            if(announce.classList.contains('fail')){
                announce.classList.remove('fail');
            }
            announce.classList.add('success');
            announce.textContent = '변경 성공!'
        }else{
            if(announce.classList.contains('success')){
                announce.classList.remove('success');
            }
            announce.classList.add('fail');
            announce.textContent = data.announceMessage + ' 다시 시도해주세요.';
        }
        announce.focus();
    });
}

//비밀번호와 비밀번호확인 동일 확인
function passwordCheckEqual(password, password_check){
    return password.value === password_check.value;
}


//나눠진 email값 합성하여 userData email에 넣는 함수
function combineEmail(emailElements, formEmailElement){

    let emailValue = '';

    emailValue += emailElements[0].value;
    emailValue += '@';
    emailValue += emailElements[1].value;

    formEmailElement.value = emailValue;
}
//나눠진 phone값 합성하여 userData phone에 넣는 함수
function combinePhone(phoneElements, formPhoneElement){

    let phoneValue = '';
    for(let element of phoneElements){
        phoneValue += element.value;
        if(element !== phoneElements.at(phoneElements.lastIndexOf())){
            phoneValue += '-';
        }
    }

    formPhoneElement.value = phoneValue;
}

//password 검증 에러시 에러 분석하여 사용자 안내
function passwordVeliErrorDisplay(password, passwordPatternValidity){

    const passwordInvalidAnnounce = document.getElementById('invalidPassword');

    //길이에 대한 비교
    if(password.validity.valueMissing) {
        passwordInvalidAnnounce.textContent = '비밀번호를 입력해주세요.';
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
function passwordCheckValidErrorDisplay(password_check,equalCheck){

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

// 이메일 유효성 검사
function checkEmail(emailElements, invalidEmails, formEmailElement){

    let invalidElement = [];

    for(let i = 0; i<emailElements.length; i++){
        if(emailElements[i].validity.valueMissing){
            changeIsValid(emailElements[i], false);
            invalidEmails[i].textContent = '값을 입력해주세요.';
            invalidElement.push(emailElements[i]);
        }
    }
    if(invalidElement.length>0){
        return false;
    }
    formEmailElement.value = emailElements[0].value +'@'+ emailElements[1].value;
    if(!formEmailElement.validity.typeMismatch){
        changeIsValid(emailElements[0], false);
        changeIsValid(emailElements[1], false);
        invalidEmails[0].textContent = '올바른 이메일 형식이 아닙니다.';
        invalidEmails[1].textContent = '';
        formEmailElement.value ='';
        return false;
    }

    for(let element of emailElements){
        changeIsValid(element, true);
    }
    return true;
}
// 이름 유효성 검사
function checkPersonname(personnameElement, invalidPersonname){

    if(personnameElement.value.trim() === ''){
        changeIsValid(personnameElement, false);
        invalidPersonname.textContent = '값을 입력해주세요.';
        return false;
    }

    if(!personnameElement.validity.valid){
        if(personnameElement.validity.valueMissing){
            invalidPersonname.textContent = '값을 입력해주세요.';
            changeIsValid(personnameElement, false);
            return false;
        }
        if(personnameElement.validity.tooLong) {
            invalidPersonname.textContent = '입력된 값의 길이가 너무 깁니다.';
            changeIsValid(personnameElement, false);
            return false;
        }
    }
    changeIsValid(personnameElement, true);
    return true;
}
//생년월일 유효성 검사
function checkBirthdate(birthdateElement, invalidBirthdate){

    if(!birthdateElement.validity.valid){
        if(birthdateElement.validity.valueMissing){
            invalidBirthdate.textContent = '값을 선택해주세요.';
            changeIsValid(birthdateElement, false);
            return false;
        }
    }
    changeIsValid(birthdateElement, true);
    return true;
}
// 전화번호 유효성 검사
function checkPhone(phoneElements, invalidPhone){

    const invalidElements = [];

    for(let element of phoneElements){
        if(!element.validity.valid){
            if(element.validity.valueMissing){
                invalidElements.push(element);
                changeIsValid(element, false);
            }
        }
    }
    if(invalidElements.length>0){
        invalidPhone.textContent = '값을 입력해주세요.';
        invalidPhone.style.display = 'block';
        return false;
    }

    invalidPhone.style.display = 'hidden';
    for(let element of phoneElements){
        changeIsValid(element, true);
    }
    return true;
}
// 주소 유효성 검사
function checkAddress(postnumElement ,addressElement, addressDetailElement,
                      invalidAddress, invalidAddressDetail){

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

