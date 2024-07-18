
package ge.bog.hw22.repositories;

import ge.bog.hw22.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserIdentification(Long id);
}