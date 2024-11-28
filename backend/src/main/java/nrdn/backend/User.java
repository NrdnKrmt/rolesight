package nrdn.backend;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
public record User(
        String id,
        List<Preference> preferences
) {
}
