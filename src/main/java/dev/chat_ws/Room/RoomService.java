package dev.chat_ws.Room;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }

    public RoomModel createRoom(RoomModel room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(String title) {
        RoomModel room = roomRepository.findByTitle(title);
        roomRepository.delete(room);
    }

    public RoomModel updateRoom(String title, UpdateRoomDto roomData) {
        RoomModel room = roomRepository.findByTitle(title);

        if (roomData.title() != null) room.setTitle(roomData.title().trim());
        if (roomData.maxCapacity() != null) room.setMaxCapacity(roomData.maxCapacity());
        if (roomData.password() != null) room.setPassword(roomData.password().trim());

        return roomRepository.save(room);
    }
}
