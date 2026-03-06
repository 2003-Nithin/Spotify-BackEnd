package spotify_app.repository;


import spotify_app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByArtistContainingIgnoreCase(String artist);
    List<Song> findByTitleContainingIgnoreCase(String title);
    List<Song> findByGenre(String genre);
}
