
const maxCharacter = 2000;
const wordCounter = document.querySelector('.editor-words-counter');
const sendButton = document.getElementById('send-button');

ClassicEditor
	.create( document.querySelector( '#editor' ), {
		// Editor configuration.
		wordCount:{
		    onUpdate: stats =>{

		        const limitExceed = stats.characters > maxCharacter;

		        wordCounter.textContent = `${stats.characters} / 2000`;
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