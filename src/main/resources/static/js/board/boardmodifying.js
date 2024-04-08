
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
