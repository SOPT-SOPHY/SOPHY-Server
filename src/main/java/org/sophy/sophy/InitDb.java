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

    //@PostConstruct
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
//            OperatorProperty memOperBeak = OperatorProperty.builder()
//                .myPlaceList(new ArrayList<>())
//                .recruitScheduledBooktalks(new ArrayList<>())
//                .build();
//
//            em.persist(memOperBeak);
//
//            Member operBeak = Member.builder()
//                .name("백만원공간운영자")
//                .email("OperBeak@gmail.com")
//                .password(passwordEncoder.encode("Iamoperator10!"))
//                .phoneNum("01012345678")
//                .marketingAgree(true)
//                .authority(Authority.OPERATOR)
//                .build();
//            operBeak.setOperatorProperty(memOperBeak);
//            em.persist(operBeak);
//
//            Place placeBeak = Place.builder()
//                .name("책방 옥상에앉아")
//                .member(operBeak)
//                .city(City.UIJEONGBU_SI)
//                .address("경기도 의정부시 동일로 478")
//                .maximum(20)
//                .placeImage(
//                    "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20201208_270%2F1607420258707SpWxa_JPEG%2FQH1LLyy0L22MQw4v64W13X9j.jpeg.jpg")
//                .build();
//            em.persist(placeBeak);
//
//            AuthorProperty authorNamPro = AuthorProperty.toBuild();
//            em.persist(authorNamPro);
//            Member authorNam = Member.builder()
//                .name("남강")
//                .email("authorNam@naver.com")
//                .password(passwordEncoder.encode("sophy123"))
//                .phoneNum("01012345678")
//                .marketingAgree(false)
//                .authority(Authority.AUTHOR)
//                .build();
//            authorNam.setAuthorProperty(authorNamPro);
//            em.persist(authorNam);
//
//            Book bookBeak = Book.builder()
//                .title("고양이 물그릇에 빌게")
//                .bookCategory(BookCategory.ESSAY)
//                .booktalkOpenCount(0)
//                .isRegistration(false)
//                .bookImageUrl(
//                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/namkang.png")
//                .build();
//            em.persist(bookBeak);
//
//            Booktalk booktalkBeak = Booktalk.builder()
//                .place(placeBeak)
//                .title("고양이 물그릇에 빌게")
//                .booktalkImageUrl(
//                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/namkang.png")
//                .book(bookBeak)
//                .member(authorNam)
//                .bookCategory(bookBeak.getBookCategory())
//                .startDate(LocalDateTime.of(2023, 11, 4, 14, 0))
//                .endDate(LocalDateTime.of(2023, 11, 4, 15, 30))
//                .maximum(5)
//                .participationFee(0)
//                .preliminaryInfo(PreliminaryInfo.PROVIDE_BOOK)
//                .description(
//                    "\"사람들은 늘상 무당도 사람이라는 점을 잊고 살아간다. 그들의 특수한 능력이나 생활 때문이기도 하지만 미디어에서 나타나는 무당들의 이미지 때문일 것이다. 맥락과는 상관없이 살을 날리고, 돼지피를 뿜어대고 칼을 연신 어르고, 작두를 타는 이미지 말이다. 무당은 소수자인 사람이다. 무당이 신이 아닌 사람이라는 점을 잊지는 말았으면 좋겠다. 나는 판타지가 아닌 지금 발 딛고 있는 현실에 기댄 이야기를 풀어내고 싶었다.\"\n"
//                        + "\n"
//                        + "소피의 첫번째 북토크는 <고양이 물그릇에 빌게>를 집필하신 남강 작가님과 함께 합니다!")
//                .booktalkStatus(BooktalkStatus.RECRUITING)
//                .build();
//            em.persist(booktalkBeak);
            OperatorProperty memOperAn = OperatorProperty.builder()
                .myPlaceList(new ArrayList<>())
                .recruitScheduledBooktalks(new ArrayList<>())
                .build();

            em.persist(memOperAn);

            Member operAn = Member.builder()
                .name("안부공간운영자")
                .email("OperAn@gmail.com")
                .password(passwordEncoder.encode("Iamoperator10!"))
                .phoneNum("01012345678")
                .marketingAgree(true)
                .authority(Authority.OPERATOR)
                .build();
            operAn.setOperatorProperty(memOperAn);
            em.persist(operAn);

            Place placeAn = Place.builder()
                .name("안부, 카페")
                .member(operAn)
                .city(City.UIJEONGBU_SI)
                .address("경기 의정부시 의정로 198 지상 1층")
                .maximum(20)
                .placeImage(
                    "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20230317_103%2F1679029132306UlhtA_JPEG%2F1678938318592.jpg")
                .build();
            em.persist(placeAn);

            AuthorProperty authorMinPro = AuthorProperty.toBuild();
            em.persist(authorMinPro);
            Member authorMin = Member.builder()
                .name("김민희")
                .email("authorMin@naver.com")
                .password(passwordEncoder.encode("sophy123"))
                .phoneNum("01012345678")
                .marketingAgree(false)
                .authority(Authority.AUTHOR)
                .build();
            authorMin.setAuthorProperty(authorMinPro);
            em.persist(authorMin);

            Book bookMin = Book.builder()
                .title("이것도 출판이라고")
                .bookCategory(BookCategory.ESSAY)
                .booktalkOpenCount(0)
                .isRegistration(false)
                .bookImageUrl(
                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/minbook.jpeg")
                .build();
            em.persist(bookMin);

            Booktalk booktalkMin = Booktalk.builder()
                .place(placeAn)
                .title("이것도 출판이라고")
                .booktalkImageUrl(
                    "https://sophy-bucket.s3.ap-northeast-2.amazonaws.com/image/minbooktalk.png")
                .book(bookMin)
                .member(authorMin)
                .bookCategory(bookMin.getBookCategory())
                .startDate(LocalDateTime.of(2023, 11, 15, 19, 30))
                .endDate(LocalDateTime.of(2023, 11, 15, 21, 0))
                .maximum(5)
                .participationFee(0)
                .preliminaryInfo(PreliminaryInfo.COMFORTABLE_COMING)
                .description(
                    "이런 분들이 들으면 좋아요\n"
                        + "✅ 영드 <미란다>의 팬이신 분\n"
                        + "✅ 1인출판, 독립출판에 관심 있으신 분\n"
                        + "✅ 출판 과정이 궁금하신 분\n"
                        + "\n"
                        + "'책 한 권 내고 망해도 좋다'라는 모토로 겁 없이 1인 출판사를 차린 책덕후가 있다. 미드 덕후이기도 한 김민희는 영국 시트콤 <미란다>에 푹 빠져 살았는데, <미란다>의 주인공을 연기한 여성 코미디언 미란다 하트가 쓴 책을 발견한 후, 무작정 판권을 알아보고 직접 번역해서 출간했다. 기존의 출판 시스템 속에서 자기만의 방식을 찾아 10년 가까이 출판을 지속하고 있다.\n"
                        + "\n"
                        + "이 책은 '출판사를 차려서 성공하고 행복하게 살았습니다.'라는 해피엔딩으로 끝나지 않는다. 1인 출판을 운영하며 책 한 권을 만들어서 독자에게 전달하기까지의 과정을 솔직담백하게 기록한 출판 개척기인 동시에, 저자가 자신의 엉뚱발랄한 출판 프로젝트를 응원하고 도움의 손길을 내밀어 준 이들에게 전하는 러브레터다.")
                .booktalkStatus(BooktalkStatus.RECRUITING_EXPECTED)
                .build();
            em.persist(booktalkMin);
        }
    }
}