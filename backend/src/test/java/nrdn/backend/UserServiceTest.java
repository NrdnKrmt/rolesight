package nrdn.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/303", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game2 = new Game("7", "Throne and Liberty", "RPG", "https://picsum.photos/200/306", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");

        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));
        when(mockGameService.getGameById("4")).thenReturn(game1);
        when(mockGameService.getGameById("7")).thenReturn(game2);

        UserService userService = new UserService(mockUserRepo, mockGameService);

        //WHEN
        List<Map<String, Object>> actual = userService.getUserPreferencesByUserId("1");

        //THEN
        List<Map<String, String>> expected = List.of(
                Map.of("gameName", "League of Legends", "genre", "MOBA", "image", "https://picsum.photos/200/303", "description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.", "role", "Tank"),
                Map.of("gameName", "Throne and Liberty", "genre", "RPG", "image", "https://picsum.photos/200/306", "description", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.", "role", "Damage Dealer")
        );

        assertEquals(expected, actual);
    }
}
