package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.user.UserApplicationForm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserApplicationFormRepository extends JpaRepository<UserApplicationForm, Long> {

    @EntityGraph(attributePaths = {"school","role","schoolClass"})
    @Query("select uaf from UserApplicationForm uaf order by uaf.createdAt desc")
    List<UserApplicationForm> findAll();
}
