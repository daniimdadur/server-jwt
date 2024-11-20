package spring.security.practice.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.security.practice.auth.model.entity.TokenEntity;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<TokenEntity, String > {
    @Query(value = """
      select t from TokenEntity t inner join UserEntity u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenEntity> findAllValidTokenByUser(String id);
    Optional<TokenEntity> findByToken(String token);
}
