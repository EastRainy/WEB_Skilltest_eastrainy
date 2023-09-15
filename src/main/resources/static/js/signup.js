

function doSignup(){

    const usernameInput = new String(document.getElementById('username').value);
    const passwordInput = new String(document.getElementById('password').value);
    const passwordCheckInput = new String(document.getElementById('password_check').value);
    const errorMessageElement = document.querySelector('.announce');


    try{
        errorMessageElement.textContent = '';
        checkId(usernameInput);
        checkPw(passwordInput);
        checkPwRepeat(passwordInput, passwordCheckInput)
    }catch(e){
        errorMessageElement.textContent = e.message;
        return;
    }

    form.submit();
}

function checkId(usernameInput){

    const idRegex = /^[a-zA-Z\d]+$/;

    if(usernameInput.length > 20 || usernameInput.length < 4){
        throw new Error('ID는 4자 이상 20자 이하여야 합니다.');
    }

    const charCheck = idRegex.test(usernameInput);
    if(!charCheck){
        throw new Error('ID는 영어 소문자, 대문자 및 숫자의 조합만 가능합니다.')
    }

}
function checkPw(input){

    const checkDigit = /[\d]/.test(input);
    const checkLower = /[a-z]/.test(input);
    const checkUpper = /[A-Z]/.test(input);
    const checkSpecial = /[!@#$%^&*()/?_=+<>;:~`\[\]\\-]/.test(input);
    const checkLength = input>3 && input<21;

    if(checkLength > 31 || checkLength < 10){
        throw new Error('비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.');
    }







}
function checkPwRepeat(input1, input2){

    if(!input1===input2){
        throw new Error('비밀번호와 비밀번호 확인 값이 서로 다릅니다. 다시 입력해 주세요.')
    }

}