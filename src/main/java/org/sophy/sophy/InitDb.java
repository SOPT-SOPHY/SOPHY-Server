package org.sophy.sophy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.AuthorProperty;
import org.sophy.sophy.domain.Book;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.OperatorProperty;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.BookCategory;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.domain.enumerate.PreliminaryInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
            OperatorProperty memOperBeak = OperatorProperty.builder()
                .myPlaceList(new ArrayList<>())
                .recruitScheduledBooktalks(new ArrayList<>())
                .build();

            em.persist(memOperBeak);

            Member operBeak = Member.builder()
                .name("백만원공간운영자")
                .email("OperBeak@gmail.com")
                .password(passwordEncoder.encode("Iamoperator10!"))
                .phoneNum("01012345678")
                .marketingAgree(true)
                .authority(Authority.OPERATOR)
                .build();
            operBeak.setOperatorProperty(memOperBeak);
            em.persist(operBeak);

            Place placeBeak = Place.builder()
                .name("책방 옥상에앉아")
                .member(operBeak)
                .city(City.UIJEONGBU_SI)
                .address("경기도 의정부시 동일로 478")
                .maximum(20)
                .placeImage(
                    "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20201208_270%2F1607420258707SpWxa_JPEG%2FQH1LLyy0L22MQw4v64W13X9j.jpeg.jpg")
                .build();
            em.persist(placeBeak);

            AuthorProperty authorNamPro = AuthorProperty.toBuild();
            em.persist(authorNamPro);
            Member authorNam = Member.builder()
                .name("남강")
                .email("authorNam@naver.com")
                .password(passwordEncoder.encode("sophy123"))
                .phoneNum("01012345678")
                .marketingAgree(false)
                .authority(Authority.AUTHOR)
                .build();
            authorNam.setAuthorProperty(authorNamPro);
            em.persist(authorNam);

            Book bookBeak = Book.builder()
                .title("고양이 물그릇에 빌게")
                .bookCategory(BookCategory.ESSAY)
                .booktalkOpenCount(0)
                .isRegistration(false)
                .bookImageUrl(
                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/namkang.png")
                .build();
            em.persist(bookBeak);

            Booktalk booktalkBeak = Booktalk.builder()
                .place(placeBeak)
                .title("고양이 물그릇에 빌게")
                .booktalkImageUrl(
                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/namkang.png")
                .book(bookBeak)
                .member(authorNam)
                .bookCategory(bookBeak.getBookCategory())
                .startDate(LocalDateTime.of(2023, 11, 4, 14, 0))
                .endDate(LocalDateTime.of(2023, 11, 4, 15, 30))
                .maximum(5)
                .participationFee(0)
                .preliminaryInfo(PreliminaryInfo.PROVIDE_BOOK)
                .description(
                    "\"사람들은 늘상 무당도 사람이라는 점을 잊고 살아간다. 그들의 특수한 능력이나 생활 때문이기도 하지만 미디어에서 나타나는 무당들의 이미지 때문일 것이다. 맥락과는 상관없이 살을 날리고, 돼지피를 뿜어대고 칼을 연신 어르고, 작두를 타는 이미지 말이다. 무당은 소수자인 사람이다. 무당이 신이 아닌 사람이라는 점을 잊지는 말았으면 좋겠다. 나는 판타지가 아닌 지금 발 딛고 있는 현실에 기댄 이야기를 풀어내고 싶었다.\"\n"
                        + "\n"
                        + "소피의 첫번째 북토크는 <고양이 물그릇에 빌게>를 집필하신 남강 작가님과 함께 합니다!")
                .booktalkStatus(BooktalkStatus.RECRUITING)
                .build();
            em.persist(booktalkBeak);
        }
    }
}