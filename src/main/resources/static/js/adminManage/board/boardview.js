
//게시글 내용 변경
function contentConvert(content){

    contentDisplay = document.querySelector('.content-display');
    contentDisplay.innerHTML = '';

    var tmpdiv = document.createElement('div');
    tmpdiv.innerHTML = content;

    contentDisplay.appendChild(tmpdiv);

}
//어떤 게시물에서 목록으로 돌아갈 때 해당 게시물이 위치하는 페이지로 이동

function redirectToBoard(){

    const id = document.getElementById("id").value;
    fetch('/adminManage/board/pagerequest/',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id : id})
    })
    .then(response => response.json())
    .then(data=>{
        const destination = data.destination;
        if(destination){
            window.location.href = '/adminManage/board/' + destination;
        }
        else{
            window.location.href = '/adminManage/board/1';
        }

    })
    .catch(error =>{
            window.location.href = '/adminManage/board/1';
    });
}

function modifyHide(mode) {

    const id = document.getElementById("id").value;
    let hideStatusTo;

    if (mode === 'hide') {
        hideStatusTo = true;
    }
    if (mode === 'display') {
        hideStatusTo = false;
    }

    const modifyJSON = {
        'id': id,
        'hideStatusTo': hideStatusTo
    }

    fetch('/adminManage/board/modifyHide', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(modifyJSON)
    })
        .then(response => {
            if (response.ok) {
                alert("처리 되었습니다.");
                redirectToBoard();
            } else {
                alert("처리가 실패하였습니다.");
                redirectToBoard();
            }
        })

}
