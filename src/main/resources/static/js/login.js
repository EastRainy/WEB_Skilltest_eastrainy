
//URL param UTF-8 decode
//param으로 메세지가 들어왔을때 디코드-message에 넣어줌
document.addEventListener('DOMContentLoaded', function() {
    const urlParam = new URLSearchParams(window.location.search);
    const urlMessage = urlParam.get('message');

    const decodedMessage = decodeURIComponent(urlMessage);
    const messageEli = document.querySelector('.announce-message');
    const brElement = document.getElementById('announce-br');

    if(decodedMessage !== 'null' && decodedMessage !== null){
        messageEli.textContent = decodedMessage;

        messageEli.style.display = 'inline';
        brElement.style.display = 'none';
    } else{
        messageEli.style.display = 'none';
        brElement.style.display ='inline';
    }
});


//빈칸 체크 로직, 빈칸체크로직은 이미 있어 사용 x
function doLogin(){
    const usernameInput = new String(document.getElementById('username').value);
    const passwordInput = new String(document.getElementById('password').value);
    const messageElement = document.getElementById('message');
    const form = document.getElementById('loginForm');

    try{
        checkUsername(usernameInput);
        checkPassword(passwordInput);
    }
    catch (error){
        messageElement.textContent = error;
        return;
    }

    form.submit();
}
function checkUsername(username){

    if(username.length == 0){
        throw new Error('아이디를 입력하세요.');
    }
}
function checkPassword(password){

    if(password.length == 0){
        throw new Error('비밀번호를 입력하세요.');
    }

}

