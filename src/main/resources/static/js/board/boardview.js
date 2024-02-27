

function contentConvert(content){

    contentDisplay = document.querySelector('.content-display')
    contentDisplay.innerHTML = '';

    var tmpdiv = document.createElement('div');
    tmpdiv.innerHTML = content;

    contentDisplay.appendChild(tmpdiv);

}
