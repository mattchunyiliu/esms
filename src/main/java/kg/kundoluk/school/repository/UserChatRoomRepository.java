package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.chat.UserChannelDTO;
import kg.kundoluk.school.model.chat.UserChatMessage;
import kg.kundoluk.school.model.chat.UserChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, String> {

    @Query(value = "select cc.uuid from user_chat_room cc where cc.user_id_one in ?1 and cc.user_id_two in ?1 limit 1", nativeQuery = true)
    String findAllByUserOneIdInAndUserTwoIdInSQL(List<Long> ids);

    @Modifying
    @Query(value = "INSERT INTO user_chat_room(\n" +
            "\tuuid, user_id_one, user_id_two)\n" +
            "\tVALUES (?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    void saveChannel(String uuid, Long userOne, Long userTwo);

    @Query(value = "select cc.uuid as channelUuid, u1.id as userIdOne, concat(u1.last_name,' ',u1.first_name) as userTitleOne,\n" +
            "       u2.id as userIdTwo, concat(u2.last_name,' ',u2.first_name) as userTitleTwo, count(CASE WHEN ucm.status=1 THEN 1 END) as unreadCount\n" +
            "from user_chat_room cc\n" +
            "    left join users u1 on cc.user_id_one = u1.id\n" +
            "    left join users u2 on cc.user_id_two = u2.id\n" +
            "    left join user_chat_message ucm on ucm.chat_room = cc.uuid\n" +
            "where cc.user_id_one = ?1 or cc.user_id_two = ?1\n" +
            "group by cc.uuid, u1.id, u2.id ", nativeQuery = true)
    List<UserChannelDTO> findAllChannelByUserInterface(Long userId);

    @Query(value = "select ucr.uuid as channelUuid, ucr.user_id_one as userIdOne, ucr.user_id_two as userIdTwo from user_chat_room ucr where ucr.uuid = ?1", nativeQuery = true)
    List<UserChannelDTO> findAllUsersByChannelInterface(String uuid);
}
