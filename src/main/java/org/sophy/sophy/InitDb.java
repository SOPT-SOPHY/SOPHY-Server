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

            AuthorProperty memauthor = AuthorProperty.builder()
                    .myBookTalkList(new ArrayList<>())
                    .myBookList(new ArrayList<>())
                    .expectedBookTalkCount(3)
                    .build();

            em.persist(memauthor);

            Member author = Member.builder()
                    .name("작가")
                    .email("author@gmail.com")
                    .password(passwordEncoder.encode("Iamauthor10!"))
                    .phoneNum("01087654321")
                    .marketingAgree(true)
                    .isAuthor(true)
                    .isOperator(false)
                    .authority(Authority.ROLE_USER)
                    .build();
            author.setAuthorProperty(memauthor);
            author.setCompleteBookTalkCount(3);
            author.setWaitingBookTalkCount(3);
            em.persist(author);

            Book book = Book.builder()
                    .title("테스트 책")
                    .bookCategory(BookCategory.HUMANITIES)
                    .booktalkOpenCount(3)
                    .isRegistration(true)
                    .build();
            book.setAuthorProperty(memauthor);
            em.persist(book);

            Place place = Place.builder()
                    .name("장소")
                    .city(City.NAKYANG_DONG)
                    .address("의정부시 낙양동")
                    .maximum(30)
                    .build();
            Place place2 = Place.builder()
                    .name("장소")
                    .city(City.GANEUNG_DONG)
                    .address("의정부시 가능동")
                    .maximum(10)
                    .build();
            em.persist(place);
            em.persist(place2);

            Booktalk booktalk = Booktalk.builder()
                    .place(place2)
                    .title("테스트 타이틀")
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
                    .book(book)
                    .member(author)
                    .bookCategory(BookCategory.HUMANITIES)
                    .startDate(LocalDateTime.of(2023, 7, 13, 13, 0))
                    .endDate(LocalDateTime.of(2023, 7, 13, 15, 0))
                    .maximum(6)
                    .participationFee(1000)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("테스트입니당")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk);

            Booktalk booktalk2 = Booktalk.builder()
                    .place(place)
                    .title("테스트 타이틀2")
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
                    .book(book)
                    .member(author)
                    .bookCategory(BookCategory.HEALTH_COOKING)
                    .startDate(LocalDateTime.of(2023, 7, 18, 16, 0))
                    .endDate(LocalDateTime.of(2023, 7, 18, 18, 0))
                    .maximum(6)
                    .participationFee(10000)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("재밌습니다~")
                    .booktalkStatus(BooktalkStatus.PLACE_CONFIRMED)
                    .build();
            em.persist(booktalk2);

            Booktalk booktalk3 = Booktalk.builder()
                    .place(place)
                    .title("테스트 타이틀3 - 낙양동")
                    .booktalkImageUrl("dwqE@EWQDQFQEWQ")
                    .book(book)
                    .member(author)
                    .bookCategory(BookCategory.HEALTH_COOKING)
                    .startDate(LocalDateTime.of(2023, 7, 18, 16, 0))
                    .endDate(LocalDateTime.of(2023, 7, 18, 18, 0))
                    .maximum(6)
                    .participationFee(10000)
                    .preliminaryInfo(PreliminaryInfo.PRE_READING)
                    .description("재밌습니다~")
                    .booktalkStatus(BooktalkStatus.RECRUITING)
                    .build();
            em.persist(booktalk3);

            MemberBooktalk memberBooktalk = MemberBooktalk.builder()
                    .member(citizen)
                    .booktalk(booktalk)
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
