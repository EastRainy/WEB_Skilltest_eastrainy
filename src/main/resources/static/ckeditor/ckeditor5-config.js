
ClassicEditor
	.create( document.querySelector( '#editor' ), {
		// Editor configuration.
	} )
	.then( editor => {
		window.editor = editor;
	} )
    .catch( error => {
            console.error( error );
    });