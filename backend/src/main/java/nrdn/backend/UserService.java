package nrdn.backend;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    UserRepository userRepository;
    GameService gameService;

    public UserService(UserRepository userRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameService = gameService;

    }

    public List<Map<String, Object>> getUserPreferencesByUserId(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Map<String, Object>> preferencesWithGameInfo = new ArrayList<>();

        for (Preference preference : user.preferences()) {
            Game game = gameService.getGameById(preference.gameId());
            Map<String, Object> preferenceMap = new LinkedHashMap<>();
            preferenceMap.put("gameId", game.id());
            preferenceMap.put("gameName", game.name());
            preferenceMap.put("gameGenre", game.genre());
            preferenceMap.put("gameImage", game.image());
            preferenceMap.put("gameDescription", game.description());
            preferenceMap.put("preferredRole", preference.role());

            preferencesWithGameInfo.add(preferenceMap);
        }

        return preferencesWithGameInfo;
    }

    public User addUserPreferencesByGameId(String id, String gameId, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        gameService.getGameById(gameId);

        Preference newPreference = new Preference(gameId, role);
        List<Preference> updatedPreferences = new ArrayList<>(user.preferences());
        updatedPreferences.add(newPreference);

        User updatedUser = new User(user.id(), updatedPreferences);

        userRepository.save(updatedUser);

        return updatedUser;
    }
}
