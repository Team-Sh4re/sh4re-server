package share.sh4re.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import share.sh4re.domain.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long>, JpaSpecificationExecutor<Code> {

}
