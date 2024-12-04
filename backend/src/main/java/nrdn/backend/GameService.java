package nrdn.backend;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {
    GameRepository gameRepository;
    UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game getGameById(String id) {
        return gameRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Game with id " + id + " not found."));
    }

    public List<Game> getAvailableGamesByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found."));

        Set<String> preferenceGameIds = user.preferences().stream()
                .map(Preference::gameId)
                .collect(Collectors.toSet());

        return gameRepository.findAll().stream()
                .filter(game -> !preferenceGameIds.contains(game.id()))
                .toList();
    }
}
