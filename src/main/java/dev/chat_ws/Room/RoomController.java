package dev.chat_ws.Room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomModel>> getAll() {
        var roomsList = roomService.getAllRooms();
        return ResponseEntity.ok(roomsList);
    }

    @PostMapping
    public ResponseEntity<RoomModel> create(@RequestBody RoomModel room){
        var newRoom = roomService.createRoom(room);
        return ResponseEntity.ok(newRoom);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> delete(@PathVariable String title){
        roomService.deleteRoom(title);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{title}")
    public ResponseEntity<RoomModel> update(@PathVariable String title, @RequestBody UpdateRoomDto roomData){
        var updatedRoom = roomService.updateRoom(title, roomData);
        return ResponseEntity.ok(updatedRoom);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePassword(@RequestBody RoomPasswordDto data) {
        var isValid = roomService.validateRoomPassword(data);
        return ResponseEntity.ok(isValid);
    }
}
