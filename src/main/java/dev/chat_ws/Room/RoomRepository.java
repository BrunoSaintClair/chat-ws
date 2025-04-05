package dev.chat_ws.Room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomModel, UUID> {
    RoomModel findByTitle(String title);
}
