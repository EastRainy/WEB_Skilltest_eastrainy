

function doSignup(){
    //회원가입 스크립트

    const usernameInput = new String(document.getElementById('username').value);
    const passwordInput = new String(document.getElementById('password').value);
    const passwordCheckInput = new String(document.getElementById('password_check').value);
    const idMessageElement = document.querySelector('.announce-id');
    const pwMessageElement = document.querySelector('.announce-pw');
    const bottomMessageElement = document.querySelector('.announce-bottom');
    const form = document.getElementById('signupForm');
    //document 에서 입력 데이터 받아옴

    idMessageElement = '';
    pwMessageElement = '';
    bottomMessageElement = '';

    //검증 실패한 경우 해당하는 에러 메세지로 announce 메세지 변경

    //id검증
    try{
        checkId(usernameInput);
    }catch(e){
        idMessageElement.textContent = e.message;
        return;
    }
    //비밀번호 검증
    try{
        checkPw(passwordInput);
    }catch(e){
        pwMessageElement.textContent = e.message;
        return;
    }
    //비밀번호 확인 검증
    try{
        checkPwRepeat(passwordInput, passwordCheckInput);
    }
    catch(e){
        bottomMessageElement.textContent = e.message;
        return;
    }


    //체크에 성공하면 서버로 데이터 전송
    form.submit();
}

function checkId(usernameInput){
    //ID 체크

    const idRegex = /^[a-zA-Z\d]+$/;

    if(usernameInput.length > 20 || usernameInput.length < 4){
        throw new Error('ID는 4자 이상 20자 이하여야 합니다.');
    }

    const charCheck = idRegex.test(usernameInput);
    if(!charCheck){
        throw new Error('ID는 영어 소문자, 대문자 및 숫자의 조합만 가능합니다.')
    }
    return;
}
function checkPw(input){
    //비밀번호 체크

    const checkValid = /[^a-zA-Z\d!@#$%^&*()/?_=+<>;:~`\[\]\\-]/
    const checkLower = /[a-z]/.test(input);
    const checkUpper = /[A-Z]/.test(input);
    const checkSpecial = /[!@#$%^&*()/?_=+<>;:~`\[\]\\-]/.test(input);
    const checkLength = input>10 && input<31;
    //정규식 통하여 각 케이스 경우 정의

    //통과하지 못한 경우 에러 생성
    if(!checkValid){
        throw new Error('비밀번호에 허용되지 않는 문자가 포함되어 있습니다.');
    }
    if(!checkLength){
        throw new Error('비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.');
    }
    if(!checkLower||checkUpper||!checkSpecial){
        throw new Error('비밀번호엔 영문 소문자, 대문자, 특수문자가 각각 한 개 이상 포함되어야 합니다.')
    }
    return;

}
function checkPwRepeat(input1, input2){
    //비밀번호와 비밀번호 확인 프로세스

    if(!input1===input2){
        throw new Error('비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.')
    }
    return;
}