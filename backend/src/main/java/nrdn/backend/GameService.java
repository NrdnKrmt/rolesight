package nrdn.backend;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameService {
    GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGameById(String id) {
        return gameRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Game with id " + id + " not found."));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
