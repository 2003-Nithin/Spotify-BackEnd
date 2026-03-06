package spotify_app.controller;

import spotify_app.model.Song;
import spotify_app.service.FileStorageService;
import spotify_app.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:3005"})
@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final FileStorageService fileStorageService;

    // ✅ Get all songs
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // ✅ Get song by ID
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    // ✅ Search songs
    @GetMapping("/search")
    public List<Song> search(@RequestParam String title) {
        return songService.searchByTitle(title);
    }

    // ✅ Upload MP3 + song details
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Song> uploadSong(
            @RequestParam("title")    String title,
            @RequestParam("artist")   String artist,
            @RequestParam("album")    String album,
            @RequestParam("genre")    String genre,
            @RequestParam("duration") int duration,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("file")     MultipartFile file
    ) {
        String fileName = fileStorageService.storeFile(file);
        String audioUrl = "http://localhost:8080/api/songs/stream/" + fileName;

        Song song = Song.builder()
                .title(title)
                .artist(artist)
                .album(album)
                .genre(genre)
                .duration(duration)
                .imageUrl(imageUrl)
                .audioUrl(audioUrl)
                .fileName(fileName)
                .filePath("uploads/" + fileName)
                .build();

        return ResponseEntity.ok(songService.addSong(song));
    }

    // ✅ Stream MP3 file
    @GetMapping("/stream/{fileName}")
    public ResponseEntity<Resource> streamSong(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Delete song
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}