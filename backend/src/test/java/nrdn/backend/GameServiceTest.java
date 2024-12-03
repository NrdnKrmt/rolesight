package nrdn.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameServiceTest {

    private final GameRepository mockGameRepo = mock(GameRepository.class);

    @Test
    void getGameByIdTest_WhenGameExists() {
        //GIVEN
        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");

        when(mockGameRepo.findById("4")).thenReturn(Optional.of(game1));

        GameService gameService = new GameService(mockGameRepo);

        //WHEN
        Game actual = gameService.getGameById("4");

        //THEN
        assertEquals(game1, actual);
    }

    @Test
    void getGameByIdTest_WhenGameDoesNotExist() {
        //GIVEN
        when(mockGameRepo.findById("100")).thenReturn(Optional.empty());

        GameService gameService = new GameService(mockGameRepo);

        //WHEN

        //THEN
        assertThrows(NoSuchElementException.class, () -> gameService.getGameById("100"));
    }

    @Test
    void getAllGamesTest() {
        //GIVEN
        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game2 = new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        List<Game> games = List.of(game1, game2);

        when(mockGameRepo.findAll()).thenReturn(games);

        GameService gameService = new GameService(mockGameRepo);

        //WHEN
        List<Game> actual = gameService.getAllGames();

        //THEN
        assertEquals(games, actual);
    }
}
