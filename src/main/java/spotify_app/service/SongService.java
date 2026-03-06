package spotify_app.service;

import spotify_app.model.Song;
import spotify_app.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public List<Song> searchByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}