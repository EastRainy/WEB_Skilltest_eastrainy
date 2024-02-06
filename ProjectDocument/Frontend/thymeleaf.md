
Thymeleaf
---

오래된 jsp를 대신할 뷰 템플릿 엔진

html 파일에 다음과 같이 태그 수정 후 사용

```html
<html xmlns:th="http://www.thymeleaf.org">
```

간단한 thymeleaf 테이블 표현 예시
```html
    <tr th:object="${board}">
        <td><span th:text="*{no}"></span></td>
        <td><span th:text="*{title}"></span></td>
        <td><span th:text="*{writer}"></span></td>
        <td><span th:text="${#temporals.format(board.updateTime, 'yyyy-MM-dd HH:mm')}"></span></td>
    </tr>

출처:https://jongminlee0.github.io/2020/03/12/thymeleaf/
```
-> ${}로 변수를 가져오고, 해당 변수 내 *{}로 선택적 변수 사용

-> 날짜 형식 변환을 위해 temporals 사용 (혹은 #dates : java.util.Date 대응 처리 가능)


```html
<a th:href="@{/main}">리다이렉트</a>
```
-> 해당 형식으로 전체 URL을 적지 않고도 페이지 이동 가능



```html
<a th:each="board : ${boradList}">
<a th:if="${ifcheck} != 0">
```

타임리프의 each나 if 등을 통하여 반복문, 조건 처리 가능



```html
    navbar.html
    <div th:fragment="navbar-div">
    
    ...
        
    main.html
    <div th:replace="/html/common/navbar.html :: navbar-div">navbar div</div>

```

-> fragment와 replace를 이용하여 다른 파일에서 작성한 부분을 타 파일에서 불러오기 가능


추가적인 정보를 위해 타임리프 doc을 확인

https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#introducing-thymeleaf