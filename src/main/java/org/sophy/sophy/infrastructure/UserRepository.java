package org.sophy.sophy.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) { em.persist(user);}

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select u from User u where u.email= :email", User.class)
                .setParameter("email", email)
                .getSingleResult());
    }
}
