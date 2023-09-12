

function doSignup(){

    const usernameInput = new String(document.getElementById('username').value);
    const passwordInput = new String(document.getElementById('password').value);
    const passwordCheckInput = new String(document.getElementById('password_check').value);




}

function checkId(String usernameInput){

    if(usernameInput.length > 20 || usernameInput.length < 4){
        throw new Error('ID는 4자 이상 20자 이하여야 합니다.')
    }



}
function checkPw(String passwordInput){


}
function checkPwRepeat(String passwordCheckInput){

}