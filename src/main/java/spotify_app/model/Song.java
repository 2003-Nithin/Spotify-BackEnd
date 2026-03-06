package spotify_app.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String album;
    private String genre;
    private int duration;
    private String imageUrl;

    private String audioUrl;      // URL to stream the file
    private String fileName;      // original file name
    private String filePath;      // where file is stored on server
}