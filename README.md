# SOPHY-Server
> 32th GO SOPT Sophy <br>

<br>

### <strong>  Server  </strong>

| <img src="https://avatars.githubusercontent.com/u/70002218?v=4" width="200">|<img src="https://avatars.githubusercontent.com/u/31067658?v=4" width="200">|
| :-----------------------------------: | :-----------------------------------------------: 
|                강수현                 |                      김동휘                       |
| [  onpyeong ](https://github.com/onpyeong) | [  dong2ast ](https://github.com/dong2ast) |
| AWS S3<br/>도메인 설계<br/> API 명세서 정리 <br /> 북토크 관련 API | 프로젝트 세팅 <br /> AWS EC2/RDS<br /> 도메인 설계 <br /> 무중단 배포 & CI/CD <br/> Spring Security & Redis <br /> 마이페이지, 소피스토리 관련 API |
<br>

### 사용 스택
| 통합 개발 환경 | IntelliJ |
| --- | --- |
| Spring 버전 | 2.7.13 |
| 데이터베이스 | AWS RDS(MySQL) |
| 배포 | AWS EC2(Ubuntu), S3, CodeDepoly |
| Project 빌드 관리 도구 | Gradle |
| CI/CD 툴 | Github Actions |
| ERD 다이어그램 툴 | MySQLWorkbench |
| Java version | Java 11  |
| 패키지 구조 | 계층형 패키지 구조 |
<br>

### <strong> 서비스 소개 </strong>
<strong>📖 작가와 함께 하는 우리 동네 지적 대화 플랫폼 ‘소피’!</strong>

‘<strong>소피</strong>’에서는 책을 출판한 경험이 있는 작가를 직접 만나 이야기를 들을 수 있어요. 바로 우리 동네에서 말이죠! 지역 도서관에서 하는 작가 북토크는 경쟁이 치열해요. 그렇다고 서울까지 가기에는 한시간을 위해 왕복 4시간을 달려야 하죠. 소피와 함께라면, 우리 동네에서 양질의 지적 모임을 경험할 수 있어요. 작가의 입을 통해 듣는 세상의 다양한 이야기들은 우리에게 새로운 자극이 되고 세상을 보는 눈을 넓혀줄 거예요. 작가는 집필 기간 동안 불안정한 수입 구조를 소피를 통해 해결할 수 있어요. 지역의 공간운영자도 자신의 공간을 소피에게 내어주어 공간 활성화와 홍보에 도움을 받을 수 있어요. 책 한 권을 쓸 정도의 이야기를 갖고 있는 사람이라면 누구나 소피의 Teller 가 될 수 있답니다! 우리 지역에서 맞이할 새로운 나의 라이프 스타일, 궁금하지 않으신가요? 소피와 함께 지역에서의 당신의 시간을 더 가치 있고 특별하게 만드세요!

</aside>
<br>

### <strong> 📚 APIs </strong>
| View            | Method | Detail | Developer |
|:-----------------:|:--------:|:--------:|:-----------:|
| 회원가입/로그인 |  POST  | 회원가입 |     동휘     |
|  |  POST  | 로그인 |    동휘   |
|  |  GET  | 이메일 중복 확인 |    동휘   |
|  |  POST  | 리이슈 |    동휘   |
| 마이페이지 |  GET  | 마이페이지 조회 |    동휘   |
|  |  GET  | 내 정보 조회 |    동휘   |
|  |  PATCH  | 내 정보 수정하기 |    동휘   |
|  |  GET  | 예정된 북토크 조회 |    동휘   |
|  |  POST  | 성별 및 생일 입력 |    동휘   |
| 작가_마이페이지 |  GET  | 공간 매칭 북토크 조회 |    동휘   |
|  |  GET  | 청중 매칭 북토크 조회 |    동휘   |
|  |  GET  | 내 도서 관리 조회 |    동휘   |
| 소피스토리 |  GET  | 지난 북토크 조회 |    동휘   |
|  |  GET  | 나의 서재 조회 |    동휘   |
| 작가_북토크 개설 |  GET  | 지역으로 공간 리스트 조회 |    수현   |
|  |  POST  | 북토크 개설 신청하기 |    수현   |
|  |  PATCH  | 북토크 수정하기 |    수현   |
|  |  DELETE  | 북토크 삭제하기 |    수현   |
| 주민_북토크 신청 |  GET  | 지역으로 북토크 리스트 조회 |    수현   |
|  |  POST  | 북토크 참여 신청하기 |    수현   |
| 공통_북토크 상세 |  GET  | 북토크 상세 조회 |    수현   |
| 공통 |  GET  | 북토크 모아보기 조회 |    수현   |


<br>



<br>

## <strong> ERD & Directory Tree</strong>
<details>
<summary>🗄 ERD</summary>
<img alt="??" src="https://github.com/onpyeong/Algorithm/assets/70002218/1f1b66c8-9229-447e-bbf4-f301f4b81f05">

</details>
<br>
<details>
<summary>📦 Directory Tree</summary>

```
	
src/main/java/org/sophy
└── sophy
    ├── common
    │   ├── advice
    │   └── dto
    ├── config
    ├── controller
    │   └── dto
    │       ├── request
    │       └── response
    ├── domain
    │   └── dto
    ├── exception
    │   └── model
    ├── external
    │   └── client
    │       └── aws
    ├── infrastructure
    ├── jwt
    ├── service
    └── util

```
</details>
<br>
	
	
## <strong> 아키텍처 구조 </strong>

	
	
	
	
	
