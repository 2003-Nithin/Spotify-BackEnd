package spotify_app.controller;

import spotify_app.model.Playlist;
import spotify_app.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:3003"})
@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        return ResponseEntity.ok(playlistService.createPlaylist(playlist));
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> addSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok(playlistService.addSongToPlaylist(playlistId, songId));
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> updatePlaylist(
            @PathVariable Long playlistId,
            @RequestBody Playlist playlist
    ) {
        return ResponseEntity.ok(playlistService.updatePlaylist(playlistId, playlist));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.ok("Playlist deleted successfully");
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> removeSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok(playlistService.removeSongFromPlaylist(playlistId, songId));
    }


}