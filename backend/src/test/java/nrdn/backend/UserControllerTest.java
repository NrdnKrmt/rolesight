package nrdn.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void getUserPreferencesByUserIdTest_WhenUserExists() throws Exception {
        //GIVEN
        Preference preference1 = new Preference("League of Legends", "Tank");
        Preference preference2 = new Preference("Throne and Liberty", "Damage Dealer");
        Preference preference3 = new Preference("World of Warcraft", "Support");
        Preference preference4 = new Preference("Final Fantasy XIV", "Tank");
        List<Preference> preferences = List.of(preference1, preference2, preference3, preference4);
        User user1 = new User("1", preferences);
        userRepository.save(user1);

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
                                              },
                                              {
                                                  "gameName": "World of Warcraft",
                                                  "role": "Support"
                                              },
                                              {
                                                  "gameName": "Final Fantasy XIV",
                                                  "role": "Tank"
                                              }
                                          ]
                                          """));
    }
}
