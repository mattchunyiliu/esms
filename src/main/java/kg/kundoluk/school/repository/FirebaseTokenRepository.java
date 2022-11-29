package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.model.user.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {
    FirebaseToken findFirstByToken(String token);
    Integer deleteByToken(String token);

    @Query(value = "select l.code as languageCode, u.id as userId, m2muft.firebase_token as token from m2m_user_firebase_token m2muft left join users u on m2muft.user_id = u.id left join language l on u.language_id = l.id where u.id = ?1",nativeQuery = true)
    List<FirebaseTokenDTO> findAllByUserId(Long userId);
}
