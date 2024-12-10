package nrdn.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class UserServiceTest {

    private final UserRepository mockUserRepo = mock(UserRepository.class);
    private final GameService mockGameService = mock(GameService.class);

    @Test
    void getUserPreferencesByUserIdTest_WhenUserExists() {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        Preference preference2 = new Preference("7", "Damage Dealer");
        List<Preference> preferences = List.of(preference1, preference2);
        User user1 = new User("1", preferences);

        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game2 = new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");

        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));
        when(mockGameService.getGameById("4")).thenReturn(game1);
        when(mockGameService.getGameById("7")).thenReturn(game2);

        UserService userService = new UserService(mockUserRepo, mockGameService);

        //WHEN
        List<Map<String, Object>> actual = userService.getUserPreferencesByUserId("1");

        //THEN
        List<Map<String, Object>> expected = List.of(
                Map.of("gameId", "4", "gameName", "League of Legends", "gameGenre", "MOBA", "gameImage", "https://picsum.photos/200/300", "gameDescription", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.", "preferredRole", "Tank"),
                Map.of("gameId", "7", "gameName", "Throne and Liberty", "gameGenre", "MMORPG", "gameImage", "https://picsum.photos/200/300", "gameDescription", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.", "preferredRole", "Damage Dealer")
        );

        assertEquals(expected, actual);
    }

    @Test
    void getUserPreferencesByUserIdTest_WhenUserDoesNotExist() {
        //GIVEN
        when(mockUserRepo.findById("100")).thenReturn(Optional.empty());

        UserService userService = new UserService(mockUserRepo, mockGameService);

        //WHEN

        //THEN
        assertThrows(NoSuchElementException.class, () -> userService.getUserPreferencesByUserId("100"));
    }

    @Test
    void addUserPreferencesByGameIdTest() {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        List<Preference> preferences = List.of(preference1);
        User user1 = new User("1", preferences);
        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));

        Preference preferenceToAdd = new Preference("7", "Damage Dealer");

        List<Preference> updatedPreferences = List.of(preference1, preferenceToAdd);
        User updatedUser = new User("1", updatedPreferences);

        when(mockUserRepo.save(any(User.class))).thenReturn(updatedUser);

        UserService userService = new UserService(mockUserRepo, mockGameService);

        //WHEN
        User actual = userService.addUserPreferencesByGameId("1", "7", "Damage Dealer");

        //THEN
        assertEquals("1", actual.id());
        assertEquals("7", actual.preferences().get(1).gameId());
        assertEquals("Damage Dealer", actual.preferences().get(1).role());
    }

    @Test
    void removeUserPreferencesByGameIdTest() {
        //GIVEN
        Preference preferenceToKeep = new Preference("4", "Tank");
        Preference preferenceToRemove = new Preference("7", "Damage Dealer");
        List<Preference> preferences = List.of(preferenceToKeep, preferenceToRemove);
        User user1 = new User("1", preferences);
        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));

        List<Preference> updatedPreferences = List.of(preferenceToKeep);
        User updatedUser = new User("1", updatedPreferences);

        when(mockUserRepo.save(any(User.class))).thenReturn(updatedUser);

        UserService userService = new UserService(mockUserRepo, mockGameService);

        //WHEN
        User actual = userService.removeUserPreferencesByGameId("1", "7");

        //THEN
        assertEquals("1", actual.id());
        assertEquals("4", actual.preferences().getFirst().gameId());
        assertNotEquals("7", actual.preferences().getFirst().gameId());
    }
}