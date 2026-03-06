package spotify_app.service;

import org.jspecify.annotations.Nullable;
import spotify_app.model.Playlist;
import spotify_app.model.Song;
import spotify_app.repository.PlaylistRepository;
import spotify_app.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist addSongToPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        playlist.getSongs().add(song);

        return playlistRepository.save(playlist);
    }

    // UPDATE PLAYLIST
    public Playlist updatePlaylist(Long playlistId, Playlist updatedPlaylist) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.setName(updatedPlaylist.getName());
        playlist.setDescription(updatedPlaylist.getDescription());

        return playlistRepository.save(playlist);
    }

    // DELETE PLAYLIST
    public void deletePlaylist(Long playlistId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlistRepository.delete(playlist);
    }

    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        playlist.getSongs().remove(song);

        return playlistRepository.save(playlist);
    }

    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    public Playlist update(Long id, Playlist updated) {
        Playlist existing = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return playlistRepository.save(existing);
    }
}