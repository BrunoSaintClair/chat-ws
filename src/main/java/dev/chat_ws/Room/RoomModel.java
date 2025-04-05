package dev.chat_ws.Room;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name = "tb_rooms")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RoomModel {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    @Column(name = "maxCapacity")
    private Integer maxCapacity;
    private String password;

    public RoomModel(String title, Integer maxCapacity, String password) {
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.password = password;
    }
}
