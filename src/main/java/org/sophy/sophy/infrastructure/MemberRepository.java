package org.sophy.sophy.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) { em.persist(member);}

    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}
