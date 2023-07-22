package org.sophy.sophy;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.*;
import org.sophy.sophy.domain.enumerate.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void dbInit() {
            Member citizen = Member.builder()
                    .name("주민")
                    .email("member@gmail.com")
                    .password(passwordEncoder.encode("Iammember10!"))
                    .phoneNum("01012345678")
                    .marketingAgree(true)
                    .isAuthor(false)
                    .isOperator(false)
                    .authority(Authority.ROLE_USER)
                    .build();
            em.persist(citizen);

            AuthorProperty memauthor1 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .build();
            AuthorProperty memauthor2 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .build();
            AuthorProperty memauthor3 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .build();

            em.persist(memauthor1);
            em.persist(memauthor2);
            em.persist(memauthor3);

            Member author1 = Member.builder()
                    .name("김초엽")
                    .email("choy@naver.com")
                    .password(passwordEncoder.encode("sophy123"))
                    .phoneNum("01012345678")
                    .marketingAgree(false)
                    .isAuthor(true)
                    .isOperator(false)
                    .authority(Authority.ROLE_USER)
                    .build();
            author1.setAuthorProperty(memauthor1);

            Member author2 = Member.builder()
                    .name("이승희")
                    .email("leesh@naver.com")
                    .password(passwordEncoder.encode("sophy234"))
                    .phoneNum("01023456789")
                    .marketingAgree(false)
                    .isAuthor(true)
                    .isOperator(false)
                    .authority(Authority.ROLE_USER)
                    .build();
            author2.setAuthorProperty(memauthor2);

            Member author3 = Member.builder()
                    .name("김상욱")
                    .email("kimsang@naver.com")
                    .password(passwordEncoder.encode("sophy345"))
                    .phoneNum("01098765432")
                    .marketingAgree(false)
                    .isAuthor(true)
                    .isOperator(false)
                    .authority(Authority.ROLE_USER)
                    .build();
            author3.setAuthorProperty(memauthor3);

            em.persist(author1);
            em.persist(author2);
            em.persist(author3);

            Book book1 = Book.builder()
                    .title("행성어서점")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(0)
                    .isRegistration(false)
                    .bookImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3e806fb3-1d00-43bf-965a-c2feed78592e%2FUntitled.png?id=e1e12bac-87fc-4a18-ab28-96bc2b666461&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=380&userId=&cache=v2")
                    .build();
            book1.setAuthorProperty(memauthor1);
            Book book2 = Book.builder()
                    .title("지구 끝의 온실")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(5)
                    .isRegistration(true)
                    .bookImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F50dcc45e-c830-41be-94bb-01851ba262e0%2FUntitled.png?id=7300b623-d30f-4e9b-8946-d3f04b271528&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=380&userId=&cache=v2")
                    .build();
            book2.setAuthorProperty(memauthor1);
            Book book3 = Book.builder()
                    .title("사이보그가 되다")
                    .bookCategory(BookCategory.SOCIETY)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
                    .bookImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8478d342-3f80-4ae5-b8d9-b64d42e7f5c3%2FUntitled.png?id=29801121-bbac-4cd5-b19c-b928c4544f6c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=380&userId=&cache=v2")
                    .build();
            book3.setAuthorProperty(memauthor1);
            Book book4 = Book.builder()
                    .title("우리가 빛의 속도로 갈 수 없다면")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
                    .bookImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8e0ece4-fb34-4553-bf3f-4bc2e8e89e4d%2FUntitled.png?id=b43c767e-ae15-407e-95cd-73e0a52c72cb&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=380&userId=&cache=v2")
                    .build();
            book4.setAuthorProperty(memauthor1);
            Book book5 = Book.builder()
                    .title("별게 다 영감")
                    .bookCategory(BookCategory.ESSAY)
                    .booktalkOpenCount(5)
                    .isRegistration(true)
                    .build();
            book5.setAuthorProperty(memauthor2);
            Book book6 = Book.builder()
                    .title("기록의 쓸모")
                    .bookCategory(BookCategory.SOCIETY)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
                    .build();
            book6.setAuthorProperty(memauthor2);
            Book book7 = Book.builder()
                    .title("브랜드 마케터들의 이야기")
                    .bookCategory(BookCategory.SOCIETY)
                    .booktalkOpenCount(0)
                    .isRegistration(false)
                    .build();
            book7.setAuthorProperty(memauthor2);
            Book book8 = Book.builder()
                    .title("과학은 논쟁이다")
                    .bookCategory(BookCategory.SCIENCE)
                    .booktalkOpenCount(1)
                    .isRegistration(true)
                    .build();
            book8.setAuthorProperty(memauthor3);
            Book book9 = Book.builder()
                    .title("과학, 누구냐 넌?")
                    .bookCategory(BookCategory.YOUTH)
                    .booktalkOpenCount(1)
                    .isRegistration(true)
                    .build();
            book9.setAuthorProperty(memauthor3);
            Book book10 = Book.builder()
                    .title("과학은 그 책을 고전이라 한다")
                    .bookCategory(BookCategory.SCIENCE)
                    .booktalkOpenCount(0)
                    .isRegistration(false)
                    .build();
            book10.setAuthorProperty(memauthor3);
            Book book11 = Book.builder()
                    .title("김상욱의 양자 공부")
                    .bookCategory(BookCategory.SCIENCE)
                    .booktalkOpenCount(2)
                    .isRegistration(true)
                    .build();
            book11.setAuthorProperty(memauthor3);
            em.persist(book1);
            em.persist(book2);
            em.persist(book3);
            em.persist(book4);
            em.persist(book5);
            em.persist(book6);
            em.persist(book7);
            em.persist(book8);
            em.persist(book9);
            em.persist(book10);
            em.persist(book11);

            OperatorProperty memOper = OperatorProperty.builder()
                    .myPlaceList(new ArrayList<>())
                    .recruitScheduledBooktalks(new ArrayList<>())
                    .build();

            em.persist(memOper);

            Member oper = Member.builder()
                    .name("공간운영자")
                    .email("Operator@gmail.com")
                    .password(passwordEncoder.encode("Iamoperator10!"))
                    .phoneNum("01056784321")
                    .marketingAgree(true)
                    .isAuthor(false)
                    .isOperator(true)
                    .authority(Authority.ROLE_USER)
                    .build();
            oper.setOperatorProperty(memOper);
            em.persist(oper);


            Place place1 = Place.builder()
                    .name("북부경기문화창조허브")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 신흥로 234 10층~13층")
                    .maximum(20)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa4c288a7-88b0-431e-9ac6-a5b839fc2dca%2FUntitled.png?table=block&id=ab90066b-1fc5-4393-a4cd-8322f49845bf&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place2 = Place.builder()
                    .name("의정부시 청년센터")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 둔야로 9 3,4층")
                    .maximum(10)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8e00c724-3f88-4aff-9472-9232d45c0564%2FUntitled.png?table=block&id=7256291e-3ac6-44fc-bef2-764036e1ed0b&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place3 = Place.builder()
                    .name("의정부 정보도서관")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 의정로41")
                    .maximum(20)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Feb6e54ae-2ee5-4e40-a906-a90623ffd65f%2FUntitled.png?table=block&id=8dd51f2a-5c0e-4a2a-af4b-ba3d7c91b350&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place4 = Place.builder()
                    .name("의정부 바이브")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 평화로483번길 43")
                    .maximum(8)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F2f9f7781-a630-4174-beff-be71abd1ce6c%2FUntitled.png?table=block&id=04ca3b4a-6d29-42b3-b2ea-7b4f9b1833d6&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place5 = Place.builder()
                    .name("의정부 영어도서관")
                    .member(oper)
                    .city(City.HOWON_DONG)
                    .address("경기 의정부시 회룡로 79")
                    .maximum(8)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F340e0894-79cf-4095-8c36-21a1f7a3f719%2FUntitled.png?table=block&id=c962284e-6f58-49ef-a352-fa4a5a2c74fc&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place6 = Place.builder()
                    .name("책읽는 행복도서관")
                    .member(oper)
                    .city(City.HOWON_DONG)
                    .address("경기 의정부시 평화로 324 한주프라자 2층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F4af168f4-8e5c-44fc-95cd-839d7cb5aba1%2FUntitled.png?table=block&id=cc17e221-1435-46dd-9b3e-abed0035d2eb&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place7 = Place.builder()
                    .name("장암동 작은도서관")
                    .member(oper)
                    .city(City.JANGAM_DONG)
                    .address("경기 의정부시 장곡로250번길 23")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46df927-68ad-4493-8434-20fa9c407561%2FUntitled.png?table=block&id=c24225a2-1a61-4c6b-8879-fac86684098a&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place8 = Place.builder()
                    .name("나누미 작은도서관")
                    .member(oper)
                    .city(City.JANGAM_DONG)
                    .address("경기 의정부시 누원로 52 수락리버시티2단지 207동 1층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8be07ae5-208e-41a5-baa3-cc784bbc20fb%2FUntitled.png?table=block&id=611e10ab-6122-4dd7-ba0b-b03e0c052aca&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place9 = Place.builder()
                    .name("의정부 과학도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 추동로 124번길 52")
                    .maximum(20)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F45d47421-d990-4ae4-b56f-fa00ae41690a%2FUntitled.png?table=block&id=4febe76d-600b-410f-855d-5abc51116b4d&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place10 = Place.builder()
                    .name("의정부 음악도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 장곡로 280")
                    .maximum(20)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F757ce1bd-33ff-4ffb-9a54-06177c13fccd%2FUntitled.png?table=block&id=f0b9ebc4-f041-42c5-8473-2228af8608bd&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place11 = Place.builder()
                    .name("참다운어린이도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 금신로 204 지하 1층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F757ce1bd-33ff-4ffb-9a54-06177c13fccd%2FUntitled.png?table=block&id=f0b9ebc4-f041-42c5-8473-2228af8608bd&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place12 = Place.builder()
                    .name("맑은샘 도서관")
                    .member(oper)
                    .city(City.YOUNGHYUN_DONG)
                    .address("경기 의정부시 오목로 21-1 의정부교회 1층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1bd595fd-9204-492e-a7a1-c3edbebec0dc%2FUntitled.png?table=block&id=40e5d6b2-6fb1-4187-a8b7-aca88c6cdaa7&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place13 = Place.builder()
                    .name("송산1동 작은도서관")
                    .member(oper)
                    .city(City.YOUNGHYUN_DONG)
                    .address("경기 의정부시 민락로 13")
                    .maximum(6)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc0434427-b7b2-4502-b404-a3fa5f68af20%2FUntitled.png?table=block&id=06606987-1b92-45ca-82b1-0c0eb4d3c7f5&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place14 = Place.builder()
                    .name("송산2동 작은도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 용민로 115 6층")
                    .maximum(6)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc6b5f865-4a5c-419e-9eb8-0c4fcc33266d%2FUntitled.png?table=block&id=8c9e82b7-a19e-426f-b3f3-abfffa3d2946&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place15 = Place.builder()
                    .name("의정부 미술도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 민락로 248")
                    .maximum(20)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff1dbbe88-d006-4643-9cab-61f6c2c4e44b%2FUntitled.png?table=block&id=9ff0eb66-b918-424e-8adb-91b683203247&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place16 = Place.builder()
                    .name("민락복합문화 작은도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 오목로205번길 61 로데오프라자 8층 801호")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fe8585729-112b-414e-a7f3-9939cadf0b87%2FUntitled.png?table=block&id=02fca598-c5c3-429d-ae68-c2a61607bae9&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place17 = Place.builder()
                    .name("금강 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 263 404동 1층")
                    .maximum(4)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F65cca3e0-867f-4a37-ac5c-91b6ddee0853%2FUntitled.png?table=block&id=3bc2a968-d70c-4029-aaac-6b16ec1c29ed&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place18 = Place.builder()
                    .name("휴스토리 꿈자람 도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 오목로 252 LH휴스토리 205동 1층")
                    .maximum(4)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fbe70bcbd-e591-4b6c-9d36-4c3a66f8e09f%2FUntitled.png?table=block&id=3ba75500-6ae2-4029-8faf-6f4834ba477b&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place19 = Place.builder()
                    .name("LH 햇볕뜰 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 373-17 3층")
                    .maximum(8)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fde293bf6-6e65-4195-b6b1-b3854a3dc9a3%2FUntitled.png?table=block&id=39cf7bea-8296-450d-8268-2ddbf5a04843&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place20 = Place.builder()
                    .name("민락 푸르지오 푸른 도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 송양로 46 의정부 민락푸르지오아파트")
                    .maximum(6)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F2a853b9a-107d-49fb-a231-60c437f332ad%2FUntitled.png?table=block&id=f606f0ca-5244-4164-85fc-77c0eaddaa8a&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place21 = Place.builder()
                    .name("반딧불이 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 419 주민공동시설 2층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F39e698f5-7667-40f6-9580-90ac5f12b27e%2FUntitled.png?table=block&id=c3f28ceb-47f9-43aa-981b-aa4b46f0a337&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place22 = Place.builder()
                    .name("자금동 작은도서관")
                    .member(oper)
                    .city(City.GEUMO_DONG)
                    .address("경기 의정부시 거북로 13 자금동주민센터 2층")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb76013b4-ee80-4c67-8b86-27d61c24debf%2FUntitled.png?table=block&id=9540fa08-fc34-434f-a832-179973c09a11&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place23 = Place.builder()
                    .name("홈플러스 주민센터")
                    .member(oper)
                    .city(City.GEUMO_DONG)
                    .address("경기 의정부시 청사로 38")
                    .maximum(10)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcffd0d61-8831-4508-a364-a21c721ff8b3%2FUntitled.png?table=block&id=fd1b35b1-3d85-4b3d-aaea-8149f9b84b32&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place24 = Place.builder()
                    .name("가능동 작은도서관")
                    .member(oper)
                    .city(City.GANEUNG_DONG)
                    .address("경기 의정부시 신촌로 35 1층")
                    .maximum(4)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fe82d9c36-0de4-4d55-af27-f5b09be3c029%2FUntitled.png?table=block&id=57622766-5feb-41dd-94a3-0b945232e3bc&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place25 = Place.builder()
                    .name("흥선동 행정복지센터")
                    .member(oper)
                    .city(City.GANEUNG_DONG)
                    .address("경기 의정부시 흥선로 20")
                    .maximum(5)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F35af2ab9-26f7-452e-9824-551fcaac6659%2FUntitled.png?table=block&id=54599de2-04dc-4a89-858b-01fe9d6775b8&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place26 = Place.builder()
                    .name("녹양동 작은도서관")
                    .member(oper)
                    .city(City.NOKYANG_DONG)
                    .address("경기 의정부시 진등로 21 2층")
                    .maximum(6)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F2327c89d-c52c-44c5-9c23-4dfcd2d5eac4%2FUntitled.png?table=block&id=6e115dbc-1d05-4763-9fe9-cb664b31526e&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            Place place27 = Place.builder()
                    .name("책이랑나랑 작은도서관")
                    .member(oper)
                    .city(City.NOKYANG_DONG)
                    .address("경기 의정부시 녹양로 87번길 38-20")
                    .maximum(4)
                    .placeImage("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F2327c89d-c52c-44c5-9c23-4dfcd2d5eac4%2FUntitled.png?table=block&id=6e115dbc-1d05-4763-9fe9-cb664b31526e&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=290&userId=&cache=v2")
                    .build();
            em.persist(place1);
            em.persist(place2);
            em.persist(place3);
            em.persist(place4);
            em.persist(place5);
            em.persist(place6);
            em.persist(place7);
            em.persist(place8);
            em.persist(place9);
            em.persist(place10);
            em.persist(place11);
            em.persist(place12);
            em.persist(place13);
            em.persist(place14);
            em.persist(place15);
            em.persist(place16);
            em.persist(place17);
            em.persist(place18);
            em.persist(place19);
            em.persist(place20);
            em.persist(place21);
            em.persist(place22);
            em.persist(place23);
            em.persist(place24);
            em.persist(place25);
            em.persist(place26);
            em.persist(place27);

            Booktalk booktalk1 = Booktalk.builder()
                    .place(place1)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 1, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 1, 19, 0))
                    .maximum(20)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk1);

            Booktalk booktalk2 = Booktalk.builder()
                    .place(place2)
                    .title("김상욱의 양자 공부")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff725c906-ce23-45cd-802c-c9114f2c467a%2FUntitled.png?id=f23867ae-ea7f-4810-a23d-4c7d267b50d4&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book11)
                    .member(author3)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 11, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 11, 19, 0))
                    .maximum(10)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("양자 역학의 탄생부터 최전선까지 모든 역사와 20세기 물리학의 ‘제자백가’들이 펼치는 이론과 법칙을 마음껏 만끽할 수 있도록 단 한 권에 담았다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk2);

            Booktalk booktalk3 = Booktalk.builder()
                    .place(place3)
                    .title("별게 다 영감")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46cf5bf-42d5-4f10-89bd-cd188944a519%2FUntitled.png?id=bc1e32e5-4a8d-4db5-bc7d-e3437225ac76&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book5)
                    .member(author2)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 12, 20, 0))
                    .endDate(LocalDateTime.of(2023, 8, 12, 21, 0))
                    .maximum(20)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("항상 새로운 아이디어가 쏟아지는 시대, 아이디어를 찾는 방법도 여러 가지. 하루하루 아이디어를 위해 새로움을 찾는 마케터들은 어떤 방법을 쓸까요? 마케터 이승희의 영감을 만나보세요!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk3);

            Booktalk booktalk4 = Booktalk.builder()
                    .place(place4)
                    .title("기록의 쓸모")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8588350-419c-4698-9c55-fbb8d1f81b60%2FUntitled.png?id=5e27057e-d695-4a04-9dee-b9dbbbf3704f&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book6)
                    .member(author2)
                    .bookCategory(book6.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 18, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 18, 15, 0))
                    .maximum(8)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("“기록을 통해 경험을 찾고, 경험을 통해 나만의 쓸모를 만들어갑니다!” 오늘 나의 ‘기록’이 생각의 도구가 되고 나를 성장시키는 자산이 된다! 마케터 이승희의 기록으로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk4);

            Booktalk booktalk5 = Booktalk.builder()
                    .place(place5)
                    .title("지구 끝에 온실")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1b491b1b-756b-4450-ac7a-97a72e6ab43b%2FUntitled.png?id=93eccc64-b674-4871-90a5-a7579e109165&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book2)
                    .member(author1)
                    .bookCategory(book2.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 13, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 13, 15, 0))
                    .maximum(8)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 첫 장편소설. 덩굴식물이 뻗어 나가는 곳, 그곳에 숨겨진 기묘한 이야기! 김초엽 작가의 SF 세계로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk5);

            Booktalk booktalk6 = Booktalk.builder()
                    .place(place6)
                    .title("별게 다 영감")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46cf5bf-42d5-4f10-89bd-cd188944a519%2FUntitled.png?id=bc1e32e5-4a8d-4db5-bc7d-e3437225ac76&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book5)
                    .member(author2)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 25, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 25, 15, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("항상 새로운 아이디어가 쏟아지는 시대, 아이디어를 찾는 방법도 여러 가지. 하루하루 아이디어를 위해 새로움을 찾는 마케터들은 어떤 방법을 쓸까요? 마케터 이승희의 영감을 만나보세요!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk6);

            Booktalk booktalk7 = Booktalk.builder()
                    .place(place7)
                    .title("기록의 쓸모")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8588350-419c-4698-9c55-fbb8d1f81b60%2FUntitled.png?id=5e27057e-d695-4a04-9dee-b9dbbbf3704f&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book6)
                    .member(author2)
                    .bookCategory(book6.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 19, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 19, 19, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("“기록을 통해 경험을 찾고, 경험을 통해 나만의 쓸모를 만들어갑니다!” 오늘 나의 ‘기록’이 생각의 도구가 되고 나를 성장시키는 자산이 된다! 마케터 이승희의 기록으로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk7);

            Booktalk booktalk8 = Booktalk.builder()
                    .place(place8)
                    .title("과학은 논쟁이다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F98f0e79a-e3ec-4541-95d3-0193a2c2269b%2FUntitled.png?id=15eb495d-4a1f-4f1d-8b0e-a7fcdbfe254e&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book8)
                    .member(author3)
                    .bookCategory(book8.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 9, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 9, 19, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("‘철학적 과학자’들과 ‘과학적 철학자’들의 대논쟁 우리가 믿고 있던 과학적 진실이 송두리째 흔들린다")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk8);

            Booktalk booktalk9 = Booktalk.builder()
                    .place(place9)
                    .title("지구 끝에 온실")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1b491b1b-756b-4450-ac7a-97a72e6ab43b%2FUntitled.png?id=93eccc64-b674-4871-90a5-a7579e109165&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book2)
                    .member(author1)
                    .bookCategory(book2.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 2, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 2, 15, 0))
                    .maximum(20)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 첫 장편소설. 덩굴식물이 뻗어 나가는 곳, 그곳에 숨겨진 기묘한 이야기! 김초엽 작가의 SF 세계로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk9);

            Booktalk booktalk10 = Booktalk.builder()
                    .place(place10)
                    .title("떨림과 울림")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff39305e8-f927-4c47-87cd-e72c935ede26%2FUntitled.png?id=b0226716-30e9-4fbf-9d28-92b52b12d1cb&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book9)
                    .member(author3)
                    .bookCategory(book9.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 26, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 26, 15, 0))
                    .maximum(20)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("물리학자 김상욱이 바라본 우주와 세계 그리고 우리, 물리라는 언어를 통해 세계와 우리 존재를 바라보는 다른 눈을 뜨게 하다")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk10);

            Booktalk booktalk11 = Booktalk.builder()
                    .place(place11)
                    .title("김상욱의 양자 공부")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff725c906-ce23-45cd-802c-c9114f2c467a%2FUntitled.png?id=f23867ae-ea7f-4810-a23d-4c7d267b50d4&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book11)
                    .member(author3)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 20, 16, 0))
                    .endDate(LocalDateTime.of(2023, 8, 20, 17, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("양자 역학의 탄생부터 최전선까지 모든 역사와 20세기 물리학의 ‘제자백가’들이 펼치는 이론과 법칙을 마음껏 만끽할 수 있도록 단 한 권에 담았다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk11);

            Booktalk booktalk12 = Booktalk.builder()
                    .place(place12)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 14, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 14, 19, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk12);

            Booktalk booktalk13 = Booktalk.builder()
                    .place(place13)
                    .title("기록의 쓸모")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8588350-419c-4698-9c55-fbb8d1f81b60%2FUntitled.png?id=5e27057e-d695-4a04-9dee-b9dbbbf3704f&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book6)
                    .member(author2)
                    .bookCategory(book6.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 3, 11, 0))
                    .endDate(LocalDateTime.of(2023, 8, 3, 12, 0))
                    .maximum(6)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("“기록을 통해 경험을 찾고, 경험을 통해 나만의 쓸모를 만들어갑니다!” 오늘 나의 ‘기록’이 생각의 도구가 되고 나를 성장시키는 자산이 된다! 마케터 이승희의 기록으로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk13);

            Booktalk booktalk14 = Booktalk.builder()
                    .place(place14)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 7, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 7, 15, 0))
                    .maximum(6)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk14);

            Booktalk booktalk15 = Booktalk.builder()
                    .place(place15)
                    .title("김상욱의 양자 공부")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff725c906-ce23-45cd-802c-c9114f2c467a%2FUntitled.png?id=f23867ae-ea7f-4810-a23d-4c7d267b50d4&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book11)
                    .member(author3)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 21, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 21, 15, 0))
                    .maximum(20)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("양자 역학의 탄생부터 최전선까지 모든 역사와 20세기 물리학의 ‘제자백가’들이 펼치는 이론과 법칙을 마음껏 만끽할 수 있도록 단 한 권에 담았다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk15);

            Booktalk booktalk16 = Booktalk.builder()
                    .place(place16)
                    .title("별게 다 영감")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46cf5bf-42d5-4f10-89bd-cd188944a519%2FUntitled.png?id=bc1e32e5-4a8d-4db5-bc7d-e3437225ac76&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book5)
                    .member(author2)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 15, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 15, 15, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("항상 새로운 아이디어가 쏟아지는 시대, 아이디어를 찾는 방법도 여러 가지. 하루하루 아이디어를 위해 새로움을 찾는 마케터들은 어떤 방법을 쓸까요? 마케터 이승희의 영감을 만나보세요!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk16);

            Booktalk booktalk17 = Booktalk.builder()
                    .place(place17)
                    .title("과학은 논쟁이다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F98f0e79a-e3ec-4541-95d3-0193a2c2269b%2FUntitled.png?id=15eb495d-4a1f-4f1d-8b0e-a7fcdbfe254e&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book2)
                    .member(author1)
                    .bookCategory(book2.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 27, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 27, 19, 0))
                    .maximum(4)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("‘철학적 과학자’들과 ‘과학적 철학자’들의 대논쟁, 우리가 믿고 있던 과학적 진실이 송두리째 흔들린다")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk17);

            Booktalk booktalk18 = Booktalk.builder()
                    .place(place18)
                    .title("지구 끝에 온실")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1b491b1b-756b-4450-ac7a-97a72e6ab43b%2FUntitled.png?id=93eccc64-b674-4871-90a5-a7579e109165&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book2)
                    .member(author1)
                    .bookCategory(book2.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 4, 11, 0))
                    .endDate(LocalDateTime.of(2023, 8, 4, 12, 0))
                    .maximum(8)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 첫 장편소설. 덩굴식물이 뻗어 나가는 곳, 그곳에 숨겨진 기묘한 이야기! 김초엽 작가의 SF 세계로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk18);

            Booktalk booktalk19 = Booktalk.builder()
                    .place(place19)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 22, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 22, 15, 0))
                    .maximum(8)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk19);

            Booktalk booktalk20 = Booktalk.builder()
                    .place(place20)
                    .title("기록의 쓸모")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8588350-419c-4698-9c55-fbb8d1f81b60%2FUntitled.png?id=5e27057e-d695-4a04-9dee-b9dbbbf3704f&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book6)
                    .member(author2)
                    .bookCategory(book6.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 28, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 28, 19, 0))
                    .maximum(6)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("“기록을 통해 경험을 찾고, 경험을 통해 나만의 쓸모를 만들어갑니다!” 오늘 나의 ‘기록’이 생각의 도구가 되고 나를 성장시키는 자산이 된다! 마케터 이승희의 기록으로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk20);

            Booktalk booktalk21 = Booktalk.builder()
                    .place(place21)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 5, 20, 0))
                    .endDate(LocalDateTime.of(2023, 8, 5, 21, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk21);

            Booktalk booktalk22 = Booktalk.builder()
                    .place(place22)
                    .title("떨림과 울림")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff39305e8-f927-4c47-87cd-e72c935ede26%2FUntitled.png?id=b0226716-30e9-4fbf-9d28-92b52b12d1cb&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book9)
                    .member(author3)
                    .bookCategory(book9.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 16, 16, 0))
                    .endDate(LocalDateTime.of(2023, 8, 16, 17, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("물리학자 김상욱이 바라본 우주와 세계 그리고 우리, 물리라는 언어를 통해 세계와 우리 존재를 바라보는 다른 눈을 뜨게 하다")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk22);

            Booktalk booktalk23 = Booktalk.builder()
                    .place(place23)
                    .title("별게 다 영감")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46cf5bf-42d5-4f10-89bd-cd188944a519%2FUntitled.png?id=bc1e32e5-4a8d-4db5-bc7d-e3437225ac76&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book5)
                    .member(author2)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 23, 20, 0))
                    .endDate(LocalDateTime.of(2023, 8, 23, 21, 0))
                    .maximum(10)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("항상 새로운 아이디어가 쏟아지는 시대, 아이디어를 찾는 방법도 여러 가지. 하루하루 아이디어를 위해 새로움을 찾는 마케터들은 어떤 방법을 쓸까요? 마케터 이승희의 영감을 만나보세요!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk23);

            Booktalk booktalk24 = Booktalk.builder()
                    .place(place24)
                    .title("지구 끝에 온실")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1b491b1b-756b-4450-ac7a-97a72e6ab43b%2FUntitled.png?id=93eccc64-b674-4871-90a5-a7579e109165&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book2)
                    .member(author1)
                    .bookCategory(book2.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 29, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 29, 15, 0))
                    .maximum(4)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 첫 장편소설. 덩굴식물이 뻗어 나가는 곳, 그곳에 숨겨진 기묘한 이야기! 김초엽 작가의 SF 세계로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk24);

            Booktalk booktalk25 = Booktalk.builder()
                    .place(place25)
                    .title("사이보그가 되다")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcf5d0cb8-3d40-40ff-a4e4-13a16b9afd18%2FUntitled.png?id=629f1cfd-7c2c-44c0-bbfb-db3f9295625c&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book1)
                    .member(author1)
                    .bookCategory(book1.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 6, 14, 0))
                    .endDate(LocalDateTime.of(2023, 8, 6, 15, 0))
                    .maximum(5)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PROVIDE_EXCERPT)
                    .description("2019년 오늘의 작가상, 2020년 문학동네 젊은작가상을 수상한 김초엽 작가의 북토크. 인간의 몸은 과학기술과 어떻게 만나야 할까? 김초엽 작가와 함께 하는 시간!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk25);

            Booktalk booktalk26 = Booktalk.builder()
                    .place(place26)
                    .title("기록의 쓸모")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fd8588350-419c-4698-9c55-fbb8d1f81b60%2FUntitled.png?id=5e27057e-d695-4a04-9dee-b9dbbbf3704f&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book6)
                    .member(author2)
                    .bookCategory(book6.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 17, 18, 0))
                    .endDate(LocalDateTime.of(2023, 8, 17, 19, 0))
                    .maximum(6)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("“기록을 통해 경험을 찾고, 경험을 통해 나만의 쓸모를 만들어갑니다!” 오늘 나의 ‘기록’이 생각의 도구가 되고 나를 성장시키는 자산이 된다! 마케터 이승희의 기록으로 당신을 초대합니다.")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk26);

            Booktalk booktalk27 = Booktalk.builder()
                    .place(place27)
                    .title("별게 다 영감")
                    .booktalkImageUrl("https://spicy-gatsby-1c7.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb46cf5bf-42d5-4f10-89bd-cd188944a519%2FUntitled.png?id=bc1e32e5-4a8d-4db5-bc7d-e3437225ac76&table=block&spaceId=6e5cda1d-8fe4-4662-9a20-1d9546982dc5&width=400&userId=&cache=v2")
                    .book(book5)
                    .member(author2)
                    .bookCategory(book11.getBookCategory())
                    .startDate(LocalDateTime.of(2023, 8, 24, 11, 0))
                    .endDate(LocalDateTime.of(2023, 8, 24, 12, 0))
                    .maximum(4)
                    .participationFee(0)
                    .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                    .description("항상 새로운 아이디어가 쏟아지는 시대, 아이디어를 찾는 방법도 여러 가지. 하루하루 아이디어를 위해 새로움을 찾는 마케터들은 어떤 방법을 쓸까요? 마케터 이승희의 영감을 만나보세요!")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk27);

            MemberBooktalk memberBooktalk = MemberBooktalk.builder()
                    .member(citizen)
                    .booktalk(booktalk1)
                    .build();

            MemberBooktalk memberBooktalk2 = MemberBooktalk.builder()
                    .member(citizen)
                    .booktalk(booktalk2)
                    .build();


            em.persist(memberBooktalk);
            em.persist(memberBooktalk2);

            CompletedBooktalk completedBooktalk1 = CompletedBooktalk.builder()
                    .title("지구에 종말이 온다해도")
                    .bookName("지구 끝 온실")
                    .authorName("김초엽")
                    .booktalkDate(LocalDateTime.of(2023, 6, 22, 15, 0))
                    .placeName("의정부시 과학도서관")
                    .bookCategory(BookCategory.LITERATURE)
                    .build();

            CompletedBooktalk completedBooktalk2 = CompletedBooktalk.builder()
                    .title("일상에서 영감 찾는 법")
                    .bookName("별게 다 영감")
                    .authorName("이승희")
                    .booktalkDate(LocalDateTime.of(2023, 7, 1, 15, 0))
                    .placeName("의정부시 책이랑나랑 작은도서관")
                    .bookCategory(BookCategory.ESSAY)
                    .build();
            CompletedBooktalk completedBooktalk3 = CompletedBooktalk.builder()
                    .title("양자학이 궁금해!")
                    .bookName("김상욱의 양자공부")
                    .authorName("김상욱")
                    .booktalkDate(LocalDateTime.of(2023, 7, 19, 15, 0))
                    .placeName("의정부시 미술도서관")
                    .bookCategory(BookCategory.LITERATURE)
                    .build();

            CompletedBooktalk completedBooktalk4 = CompletedBooktalk.builder()
                    .title("야 너도 기록할 수 있어!")
                    .bookName("기록의 쓸모")
                    .authorName("이승희")
                    .booktalkDate(LocalDateTime.of(2023, 5, 15, 15, 0))
                    .placeName("민락 푸르지오 푸른 도서관")
                    .bookCategory(BookCategory.ESSAY)
                    .build();

            CompletedBooktalk completedBooktalk5 = CompletedBooktalk.builder()
                    .title("다정한 물리를 원해?")
                    .bookName("떨림과 울림")
                    .authorName("김상욱")
                    .booktalkDate(LocalDateTime.of(2023, 6, 10, 15, 0))
                    .placeName("자금동 작은도서관")
                    .bookCategory(BookCategory.CHILDREN)
                    .build();
            CompletedBooktalk completedBooktalk6 = CompletedBooktalk.builder()
                    .title("해피해피해피 싸이언스")
                    .bookName("과학은 논쟁이다")
                    .authorName("김상욱")
                    .booktalkDate(LocalDateTime.of(2023, 7, 19, 15, 0))
                    .placeName("금강 작은도서관")
                    .bookCategory(BookCategory.SCIENCE)
                    .build();

            completedBooktalk1.setMember(citizen);
            completedBooktalk2.setMember(citizen);
            completedBooktalk3.setMember(citizen);
            em.persist(completedBooktalk1);
            em.persist(completedBooktalk2);
            em.persist(completedBooktalk3);

            completedBooktalk4.setMember(author1);
            completedBooktalk5.setMember(author1);
            completedBooktalk6.setMember(author1);
            em.persist(completedBooktalk4);
            em.persist(completedBooktalk5);
            em.persist(completedBooktalk6);
        }
    }
}
