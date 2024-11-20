package spring.security.practice.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.security.practice.auth.model.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, String > {
    Optional<RoleEntity> findByName(String name);
    List<RoleEntity> findByNameIn(List<String> roles);
}
