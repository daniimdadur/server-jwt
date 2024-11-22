package spring.security.practice.master.fakultas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.security.practice.master.fakultas.model.FakultasEntity;

@Repository
public interface FakultasRepo extends JpaRepository<FakultasEntity, String> {
}
