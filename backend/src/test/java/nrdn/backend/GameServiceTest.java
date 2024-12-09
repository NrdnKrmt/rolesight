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
    private final UserRepository mockUserRepo = mock(UserRepository.class);

    @Test
    void getGameByIdTest_WhenGameExists() {
        //GIVEN
        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");

        when(mockGameRepo.findById("4")).thenReturn(Optional.of(game1));

        GameService gameService = new GameService(mockGameRepo, mockUserRepo);

        //WHEN
        Game actual = gameService.getGameById("4");

        //THEN
        assertEquals(game1, actual);
    }

    @Test
    void getGameByIdTest_WhenGameDoesNotExist() {
        //GIVEN
        when(mockGameRepo.findById("100")).thenReturn(Optional.empty());

        GameService gameService = new GameService(mockGameRepo, mockUserRepo);

        //WHEN

        //THEN
        assertThrows(NoSuchElementException.class, () -> gameService.getGameById("100"));
    }

    @Test
    void getAvailableGamesByUserIdTest() {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        Preference preference2 = new Preference("7", "Damage Dealer");
        List<Preference> preferences = List.of(preference1, preference2);
        User user1 = new User("1", preferences);
        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));

        Game game4 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game5 = new Game("5", "Guild Wars 2", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game6 = new Game("6", "SMITE", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game7 = new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        List<Game> games = List.of(game4, game5, game6, game7);
        when(mockGameRepo.findAll()).thenReturn(games);

        GameService gameService = new GameService(mockGameRepo, mockUserRepo);

        //WHEN
        List<Game> actual = gameService.getAvailableGamesByUserId("1");

        //THEN
        List<Game> expected = List.of(game5, game6);
        assertEquals(expected, actual);
    }
}
