package nrdn.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @MockBean
    GameService gameService;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void getUserPreferencesByUserIdTest_WhenUserExists() throws Exception {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        Preference preference2 = new Preference("7", "Damage Dealer");
        List<Preference> preferences = List.of(preference1, preference2);
        User user1 = new User("1", preferences);
        userRepository.save(user1);

        when(gameService.getGameById("4")).thenReturn(new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua."));
        when(gameService.getGameById("7")).thenReturn(new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua."));

        //WHEN
        mockMvc.perform(get("/api/users/1/preferences"))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          [
                                              {
                                                "gameId": "4",
                                                "gameGenre": "MOBA",
                                                "gameName": "League of Legends",
                                                "gameImage": "https://picsum.photos/200/300",
                                                "gameDescription": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
                                                "preferredRole": "Tank"
                                              },
                                              {
                                                "gameId": "7",
                                                "gameGenre": "MMORPG",
                                                "gameName": "Throne and Liberty",
                                                "gameImage": "https://picsum.photos/200/300",
                                                "gameDescription": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
                                                "preferredRole": "Damage Dealer"
                                              }
                                          ]
                                          """));
    }

    @Test
    void addUserPreferencesByGameIdTest_WhenUserExists() throws Exception {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        List<Preference> preferences = List.of(preference1);
        User user1 = new User("1", preferences);
        userRepository.save(user1);

        //WHEN
        mockMvc.perform(post("/api/users/1/preferences/10/Support"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                               "id": "1",
                                               "preferences": [
                                                   {
                                                       "gameId": "4",
                                                       "role": "Tank"
                                                   },
                                                   {
                                                       "gameId": "10",
                                                       "role": "Support"
                                                   }
                                               ]
                                          }
                                          """));
    }

    @Test
    void editUserPreferencesByGameId_WhenUserExists() throws Exception {
        //GIVEN
        Preference preference1 = new Preference("4", "Tank");
        Preference preference2 = new Preference("7", "Support");
        List<Preference> preferences = List.of(preference1, preference2);
        User user1 = new User("1", preferences);
        userRepository.save(user1);

        //WHEN
        mockMvc.perform(put("/api/users/1/preferences/7/Damage Dealer"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                               "id": "1",
                                               "preferences": [
                                                   {
                                                       "gameId": "4",
                                                       "role": "Tank"
                                                   },
                                                   {
                                                       "gameId": "7",
                                                       "role": "Damage Dealer"
                                                   }
                                               ]
                                          }
                                          """));
    }

    @Test
    void removeUserPreferencesByGameId() throws Exception {
        //GIVEN
        Preference preferenceToKeep = new Preference("4", "Tank");
        Preference preferenceToRemove = new Preference("7", "Damage Dealer");
        List<Preference> preferences = List.of(preferenceToKeep, preferenceToRemove);
        User user1 = new User("1", preferences);
        userRepository.save(user1);

        //WHEN
        mockMvc.perform(delete("/api/users/1/preferences/7"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          {
                                               "id": "1",
                                               "preferences": [
                                                   {
                                                       "gameId": "4",
                                                       "role": "Tank"
                                                   }
                                               ]
                                          }
                                          """));
    }
}
