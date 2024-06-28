
const maxCharacter = 2000;
const wordCounter = document.querySelector('.editor-words-counter');
const sendButton = document.getElementById('send-button');
const limitExceedAnnounce = document.getElementById('limit-exceeded-announce');

ClassicEditor
	.create( document.querySelector( '#editor' ), {
		// Editor configuration.
		wordCount:{
		    onUpdate: stats =>{

		        const limitExceed = stats.characters > maxCharacter;

		        wordCounter.textContent = `${stats.characters} / 2000`;

		        if(limitExceed){
                    wordCounter.classList.add("limitexceed");
					limitExceedAnnounce.style.visibility = 'visible';
		        }else{
                    wordCounter.classList.remove("limitexceed");
					limitExceedAnnounce.style.visibility = 'hidden';
		        }
		        sendButton.toggleAttribute('disabled', limitExceed);
		    }
		}
	} )
	.then( editor => {
		window.editor = editor;
	} )
    .catch( error => {
            console.error( error );
    });