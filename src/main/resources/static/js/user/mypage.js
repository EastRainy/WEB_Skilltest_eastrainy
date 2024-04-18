



function doSubmit(){

    let userData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        password_check: document.getElementById("password_check").value,
        personname: document.getElementById("personname").value,
        birthdate: document.getElementById("birthdate").value,
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        postnum: document.getElementById("postnum").value,
        address: document.getElementById("address").value,
        address_detail: document.getElementById("address_detail").value
    }
    userDataSubmit(userData);
}


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
    })
    .catch((error)=>{
        console.log(error);
    })
}