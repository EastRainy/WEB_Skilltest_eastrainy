
//Form submit 함
function sendModifyForm(){
    const form = document.getElementById('modifyingForm');
    if(form.checkValidity()){
        form.submit();
    }
}
function sendNewForm(){
    const form = document.getElementById('newForm');
    if(form.checkValidity()){
        form.submit();
    }
}


//TODO 데이터 전송 선 Validation check 추가할것