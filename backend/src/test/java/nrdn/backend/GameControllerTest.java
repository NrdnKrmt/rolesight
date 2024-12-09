package nrdn.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GameRepository gameRepository;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAvailableGamesByUserId() throws Exception {
        //GIVEN
        Preference preference1 = new Preference("5", "Tank");
        Preference preference2 = new Preference("6", "Damage Dealer");
        List<Preference> preferences = List.of(preference1, preference2);
        User user1 = new User("1", preferences);
        when(userRepository.findById("1")).thenReturn(Optional.of(user1));

        Game game4 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game5 = new Game("5", "Guild Wars 2", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game6 = new Game("6", "SMITE", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game7 = new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        List<Game> games = List.of(game4, game5, game6, game7);
        gameRepository.saveAll(games);

        //WHEN
        mockMvc.perform(get("/api/games/1"))


        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                          [
                              {
                                  "id": "4",
                                  "name": "League of Legends",
                                  "genre": "MOBA",
                                  "image": "https://picsum.photos/200/300",
                                  "description": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua."
                              },
                              {
                                  "id": "7",
                                  "name": "Throne and Liberty",
                                  "genre": "MMORPG",
                                  "image": "https://picsum.photos/200/300",
                                  "description": "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua."
                              }
                          ]
                          """));
    }
}
