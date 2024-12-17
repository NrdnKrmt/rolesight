package nrdn.backend;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("users")
public record User(
        String id,
        List<Preference> preferences
) {
}
