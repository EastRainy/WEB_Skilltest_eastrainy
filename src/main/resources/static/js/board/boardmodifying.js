
//Form submit 함
function sendModifyForm(){
    const form = document.getElementById('modifyingForm');
    console.log(form.checkValidity());
    if(form.checkValidity()){
        form.submit();
    }
}
function sendNewForm(){
    const form = document.getElementById('newForm');
    console.log(form.checkValidity());
    if(form.checkValidity()){
        form.submit();
    }
}


//TODO 데이터 전송 선 Validation check 추가할것