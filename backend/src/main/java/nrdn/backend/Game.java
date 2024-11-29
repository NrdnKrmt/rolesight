package nrdn.backend;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("games")
public record Game(
        String id,
        String name,
        String genre,
        String image,
        String description
) {
}
