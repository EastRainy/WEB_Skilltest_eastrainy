
//빈칸 체크 로직
function doLogin(){
    const usernameInput = String(document.getElementById('username').value);
    const passwordInput = String(document.getElementById('password').value);
    const messageId = document.querySelector('.announce-id');
    const messagePw = document.querySelector('.announce-pw');
    const form = document.getElementById('loginForm');

    try{
        messageId.textContent = "";
        checkUsername(usernameInput);
    }
    catch (e){
        messageId.textContent = e.message;
        return;
    }

    try{
        messagePw.textContent = "";
        checkPassword(passwordInput);
    }
    catch(e){
        messagePw.textContent = e.message;
        return;
    }

    form.submit();
}
function checkUsername(username){

    if(username.length === 0){
        throw new Error('아이디를 입력하세요.');
    }
}
function checkPassword(password){

    if(password.length === 0){
        throw new Error('비밀번호를 입력하세요.');
    }

}
