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
        when(gameService.getGameById("4")).thenReturn(new Game("4", "League of Legends", "MOBA", "image.jpg", "MOBA game"));
        when(gameService.getGameById("7")).thenReturn(new Game("7", "Throne and Liberty", "RPG", "image.jpg", "MMO"));

        //WHEN
        mockMvc.perform(get("/api/users/1/preferences"))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                          [
                                              {
                                                  "gameName": "League of Legends",
                                                  "role": "Tank"
                                              },
                                              {
                                                  "gameName": "Throne and Liberty",
                                                  "role": "Damage Dealer"
                                              }
                                          ]
                                          """));
    }
}
