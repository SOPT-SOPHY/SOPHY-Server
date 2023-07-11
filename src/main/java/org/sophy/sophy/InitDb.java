package org.sophy.sophy;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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
            citizen.setBookTalkCount(5);
            em.persist(citizen);

            Author memauthor = Author.builder()
                    .matchingBookTalkCount(3)
                    .recruitBookTalkCount(3)
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
            author.serAuthor(memauthor);
            author.setBookTalkCount(3);
            em.persist(author);

            Place place = Place.builder()
                    .name("장소")
                    .city(City.UIJEONGBU_SI)
                    .address("의정부시 무슨동 뭐시기")
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
        }
    }
}
