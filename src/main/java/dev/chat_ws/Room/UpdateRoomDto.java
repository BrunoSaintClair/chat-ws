package dev.chat_ws.Room;

public record UpdateRoomDto(String title, Integer maxCapacity, String password) {
}
