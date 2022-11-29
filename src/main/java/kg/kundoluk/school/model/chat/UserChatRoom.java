package kg.kundoluk.school.model.chat;

import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_chat_room")
public class UserChatRoom {

    @Id
    @NotNull
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id_one")
    private User userOne;

    @ManyToOne
    @JoinColumn(name = "user_id_two")
    private User userTwo;

    public UserChatRoom() {
    }

    public UserChatRoom(User userOne, User userTwo) {
        this.uuid = UUID.randomUUID().toString();
        this.userOne = userOne;
        this.userTwo = userTwo;
    }
}
