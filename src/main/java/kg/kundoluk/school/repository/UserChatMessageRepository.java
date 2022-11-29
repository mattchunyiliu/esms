package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.chat.UserParentUnreadDTO;
import kg.kundoluk.school.model.chat.UserChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserChatMessageRepository extends JpaRepository<UserChatMessage, Long> {

    Page<UserChatMessage> findAllByChatRoom(String chatRoom, Pageable pageable);

    @Query("select c from UserChatMessage c where c.chatRoom = ?1 and (?2 = 0 or c.status = ?2)")
    Page<UserChatMessage> findAllByChatRoomAndStatus(String chatRoom, Integer status, Pageable pageable);

    @Query(value = "select count(*) from user_chat_message where recipient_id=?1 and status = 1",nativeQuery = true)
    Integer getCountByRecipient(Long userId);

    @Modifying
    @Query(value = "UPDATE user_chat_message SET status = ?2 WHERE id = ?1",nativeQuery = true)
    @Transactional
    void updateStatus(Long id, Integer status);

    @Query(value = "select status from user_chat_message where id = ?1 limit 1", nativeQuery = true)
    Integer getUserChatMessageStatus(Long chatId);

    @Query(value = "select\n" +
            "    concat(p.last_name,' ',p.first_name) as parentTitle,\n" +
            "    sp.parental_type as parentalType,\n" +
            "    s.id as studentId,\n" +
            "    concat(s.last_name,' ',s.first_name) as studentTitle,\n" +
            "    p.user_id as parentUserId,\n" +
            "    u.avatar as parentAvatar,\n" +
            "    count(case when ucm.status =  1 and ucm.recipient_id = ?2 then 1 end) as unreadCount\n" +
            "from student_parent sp\n" +
            "         left join person p on p.id = sp.parent_id\n" +
            "         left join student s on sp.student_id = s.id\n" +
            "         left join user_chat_message ucm on ucm.recipient_id = p.user_id\n" +
            "         left join users u on u.id = p.user_id\n" +
            "where s.class_id=?1 group by p.id, s.id, sp.parental_type, u.id", nativeQuery = true)
    List<UserParentUnreadDTO> findAllByParentListByClass(Long classId, Long userId);
}
