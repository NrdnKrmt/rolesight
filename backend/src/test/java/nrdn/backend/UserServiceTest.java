package nrdn.backend;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository mockUserRepo = mock(UserRepository.class);

    @Test
    void getUserPreferencesByUserIdTest_WhenUserExists() {
        //GIVEN
        Preference preference1 = new Preference("League of Legends", "Tank");
        Preference preference2 = new Preference("Throne and Liberty", "Damage Dealer");
        Preference preference3 = new Preference("World of Warcraft", "Support");
        Preference preference4 = new Preference("Final Fantasy XIV", "Tank");
        List<Preference> preferences = List.of(preference1, preference2, preference3, preference4);
        User user1 = new User("1", preferences);

        when(mockUserRepo.findById("1")).thenReturn(Optional.of(user1));
        UserService userService = new UserService(mockUserRepo);

        //WHEN
        List<Preference> actual = userService.getUserPreferencesByUserId("1");

        //THEN
        assertEquals(preferences, actual);
    }
}
