
React
======

## 현재 사용 x, 나중 사용을 위해 문서에 메모

---

React 설치 
---
---
#### - Node.js 시스템에 먼저 설치 필요
프로젝트의 src/main으로 이동, cmd 통해 설치
```
cd src/main
npx create-react-app frontend 
```
설치 완료 후 생성된 frontend 폴더로 이동, 실제로 실행되는지 확인
```
npm start
```
<br>

#### - Spring Boot-React Proxing 

참고 링크 ([Configuring the Proxy Manually](https://create-react-app.dev/docs/proxying-api-requests-in-development/#configuring-the-proxy-manually))

다음을 실행하여 필요한 모듈 설치
```
npm install http-proxy-middleware --save
```

frontend 폴더 내에 레퍼런스 코드를 포함한 파일 생성(setupProxy.js)
```javascript
const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api', //<-프론트엔드에서 요청을 보내는곳
    createProxyMiddleware({ 
      target: 'http://localhost:8081', //<- 해당 부분이 도달할 호스트 설정 부분
      changeOrigin: true,
    })
  );
};
```
<br>

#### -Axios 설치
Axios 모듈 설치
```
npm install axios --save
```
설치된 axios는 다음과 같이 import 하여 사용 가능
```javascript
import axios from 'axios';
```
기존에 생성된 App.js를 수정하여 테스트 코드 작성
```javascript
import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
   const [hello, setHello] = useState('')

    useEffect(() => {
        axios.get('/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

    return (
        <div>
            Data bring backend : {hello}
        </div>
    );
}

export default App;
```
=> localhost:3000 에서 8081포트의 데이터를 받아와 이용하는 것을 확인 가능

<br>

#### - gradle.build 파일 수정

빌드 시 React 프로젝트 먼저 빌드 후 Spring boot 프로젝트 빌드에 포함시키는 스크립트 삽입