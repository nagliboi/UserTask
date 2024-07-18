package ge.bog.hw22.repositories;

import ge.bog.hw22.modules.Task;
import ge.bog.hw22.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :startOfDay AND :endOfDay")
    List<Task> findTasksDueToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    void deleteByUserId(long userId);
}
