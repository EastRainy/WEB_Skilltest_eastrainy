
### BoardView Format 관련 이슈 내용
 
------

현재 boardview 페이지 구현 시, 각 속성 단순 String 형식으로 저장 및 전송중

=> plain text로 입력 및 출력 중, html 표시 시 라인체인지가 태그형식이 아니여서 인식 X

==> 따라서 표시 시 데이터를 변환해주거나 다른 방법을 이용하여 표시해줘야함

-----

### 해결법 : 간단한 위지윅 에디터 도입

 - CDN 방식으로 먼저 간편하게 적용 가능
 - 추후 게시판의 사진 업로드 기능 개발 시 에디터 수정하여 적용 가능

이를 위해 CKEditor5 CDN 버전을 간단하게 도입하여 사용

----

### 입력 => CKEditor5 에디터를 이용한 입력

```html
    <script src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
```
해당 스크립트로 classic 버전 이용 가능

```html
                <div class="form-group">
                <label for="editor">내용 : </label><br>
                    <textarea class="content" name="content" id="editor"></textarea>
                </div>
```
- id를 editor로 설정한 부분에 에디터가 들어감
- form의 데이터를 보낼때는 name에 들어간 속성(content)로 전송됨

```html

    <script>
        ClassicEditor
        .create( document.querySelector( '#editor' ), {
            toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ],
            heading: {
                options: [
                    { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                    { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                    { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' }
                ]
            }
        } )
        .catch( error => {
                console.error( error );
        } );
    </script>
```
 - 에디터 생성 시 속성 정하는 부분
 - 현재 간단한 옵션들만 넣어 제공

----

### 출력 => 저장된 html포멧 데이터를 디스플레이

이때 출력 시, content-display에 직접 innerHTML로 바꾸지 않음
 - XSS 공격에 취약해질 수 있음

```JavaScript
function contentConvert(content){

    contentDisplay = document.querySelector('.content-display')
    contentDisplay.innerHTML = '';

    var tmpdiv = document.createElement('div');
    tmpdiv.innerHTML = content;

    contentDisplay.appendChild(tmpdiv);

}
```


