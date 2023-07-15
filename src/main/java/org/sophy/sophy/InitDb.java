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
            citizen.setCompleteBookTalkCount(8);
            citizen.setWaitingBookTalkCount(5);
            em.persist(citizen);

            AuthorProperty memauthor1 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .expectedBookTalkCount(10)
                    .build();
            AuthorProperty memauthor2 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .expectedBookTalkCount(10)
                    .build();
            AuthorProperty memauthor3 = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .expectedBookTalkCount(7)
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
            author1.setCompleteBookTalkCount(1);
            author1.setWaitingBookTalkCount(0);

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
            author2.setCompleteBookTalkCount(2);
            author2.setWaitingBookTalkCount(1);

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
            author3.setCompleteBookTalkCount(1);
            author3.setWaitingBookTalkCount(0);

            em.persist(author1);
            em.persist(author2);
            em.persist(author3);

            Book book1 = Book.builder()
                    .title("행성어서점")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(0)
                    .isRegistration(false)
                    .build();
            book1.setAuthorProperty(memauthor1);
            Book book2 = Book.builder()
                    .title("지구 끝의 온실")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(5)
                    .isRegistration(true)
                    .build();
            book2.setAuthorProperty(memauthor1);
            Book book3 = Book.builder()
                    .title("사이보그가 되다")
                    .bookCategory(BookCategory.SOCIETY)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
                    .build();
            book3.setAuthorProperty(memauthor1);
            Book book4 = Book.builder()
                    .title("우리가 빛의 속도로 갈 수 없다면")
                    .bookCategory(BookCategory.LITERATURE)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
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
                    .build();
            Place place2 = Place.builder()
                    .name("의정부시 청년센터")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 둔야로 9 3,4층")
                    .maximum(10)
                    .build();
            Place place3 = Place.builder()
                    .name("의정부 정보도서관")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 의정로41")
                    .maximum(20)
                    .build();
            Place place4 = Place.builder()
                    .name("의정부 바이브")
                    .member(oper)
                    .city(City.UIJEONGBU_DONG)
                    .address("경기 의정부시 평화로483번길 43")
                    .maximum(8)
                    .build();
            Place place5 = Place.builder()
                    .name("의정부 영어도서관")
                    .member(oper)
                    .city(City.HOWON_DONG)
                    .address("경기 의정부시 회룡로 79")
                    .maximum(8)
                    .build();
            Place place6 = Place.builder()
                    .name("책읽는 행복도서관")
                    .member(oper)
                    .city(City.HOWON_DONG)
                    .address("경기 의정부시 평화로 324 한주프라자 2층")
                    .maximum(5)
                    .build();
            Place place7 = Place.builder()
                    .name("장암동 작은도서관")
                    .member(oper)
                    .city(City.JANGAM_DONG)
                    .address("경기 의정부시 장곡로250번길 23")
                    .maximum(5)
                    .build();
            Place place8 = Place.builder()
                    .name("나누미 작은도서관")
                    .member(oper)
                    .city(City.JANGAM_DONG)
                    .address("경기 의정부시 누원로 52 수락리버시티2단지 207동 1층")
                    .maximum(5)
                    .build();
            Place place9 = Place.builder()
                    .name("의정부 과학도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 추동로 124번길 52")
                    .maximum(20)
                    .build();
            Place place10 = Place.builder()
                    .name("의정부 음악도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 장곡로 280")
                    .maximum(20)
                    .build();
            Place place11 = Place.builder()
                    .name("참다운어린이도서관")
                    .member(oper)
                    .city(City.SINGOK_DONG)
                    .address("경기 의정부시 금신로 204 지하 1층")
                    .maximum(5)
                    .build();
            Place place12 = Place.builder()
                    .name("맑은샘 도서관")
                    .member(oper)
                    .city(City.YOUNGHYUN_DONG)
                    .address("경기 의정부시 오목로 21-1 의정부교회 1층")
                    .maximum(5)
                    .build();
            Place place13 = Place.builder()
                    .name("송산1동 작은도서관")
                    .member(oper)
                    .city(City.YOUNGHYUN_DONG)
                    .address("경기 의정부시 민락로 13")
                    .maximum(6)
                    .build();
            Place place14 = Place.builder()
                    .name("송산2동 작은도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 용민로 115 6층")
                    .maximum(6)
                    .build();
            Place place15 = Place.builder()
                    .name("의정부 미술도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 민락로 248")
                    .maximum(20)
                    .build();
            Place place16 = Place.builder()
                    .name("민락복합문화 작은도서관")
                    .member(oper)
                    .city(City.MINRAK_DONG)
                    .address("경기 의정부시 오목로205번길 61 로데오프라자 8층 801호")
                    .maximum(5)
                    .build();
            Place place17 = Place.builder()
                    .name("금강 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 263 404동 1층")
                    .maximum(4)
                    .build();
            Place place18 = Place.builder()
                    .name("휴스토리 꿈자람 도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 오목로 252 LH휴스토리 205동 1층")
                    .maximum(4)
                    .build();
            Place place19 = Place.builder()
                    .name("LH 햇볕뜰 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 373-17 3층")
                    .maximum(8)
                    .build();
            Place place20 = Place.builder()
                    .name("민락 푸르지오 푸른 도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 송양로 46 의정부 민락푸르지오아파트")
                    .maximum(6)
                    .build();
            Place place21 = Place.builder()
                    .name("반딧불이 작은도서관")
                    .member(oper)
                    .city(City.NAKYANG_DONG)
                    .address("경기 의정부시 용민로 419 주민공동시설 2층")
                    .maximum(5)
                    .build();
            Place place22 = Place.builder()
                    .name("자금동 작은도서관")
                    .member(oper)
                    .city(City.GEUMO_DONG)
                    .address("경기 의정부시 거북로 13 자금동주민센터 2층")
                    .maximum(5)
                    .build();
            Place place23 = Place.builder()
                    .name("홈플러스 주민센터")
                    .member(oper)
                    .city(City.GEUMO_DONG)
                    .address("경기 의정부시 청사로 38")
                    .maximum(10)
                    .build();
            Place place24 = Place.builder()
                    .name("가능동 작은도서관")
                    .member(oper)
                    .city(City.GANEUNG_DONG)
                    .address("경기 의정부시 신촌로 35 1층")
                    .maximum(4)
                    .build();
            Place place25 = Place.builder()
                    .name("흥선동 행정복지센터")
                    .member(oper)
                    .city(City.GANEUNG_DONG)
                    .address("경기 의정부시 흥선로 20")
                    .maximum(5)
                    .build();
            Place place26 = Place.builder()
                    .name("녹양동 작은도서관")
                    .member(oper)
                    .city(City.NOKYANG_DONG)
                    .address("경기 의정부시 진등로 21 2층")
                    .maximum(6)
                    .build();
            Place place27 = Place.builder()
                    .name("책이랑나랑 작은도서관")
                    .member(oper)
                    .city(City.NOKYANG_DONG)
                    .address("경기 의정부시 녹양로 87번길 38-20")
                    .maximum(4)
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
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

            CompletedBooktalk completedBooktalk = CompletedBooktalk.builder()
                    .title("끝난 북토크")
                    .bookName("끝난 책")
                    .authorName("끝난 작가")
                    .booktalkDate(LocalDateTime.of(2023, 6, 3, 15, 0))
                    .placeName("북토크가 끝난 장소")
                    .build();

            completedBooktalk.setMember(citizen);
            em.persist(completedBooktalk);
            citizen.getCompletedBookTalkList().add(completedBooktalk);
        }
    }
}
