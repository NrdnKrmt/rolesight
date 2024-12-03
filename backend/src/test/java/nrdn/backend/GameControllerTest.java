package nrdn.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @BeforeEach
    void setup() {
        gameRepository.deleteAll();
    }

    @Test
    void getAllGamesTest() throws Exception {
        //GIVEN
        Game game1 = new Game("4", "League of Legends", "MOBA", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        Game game2 = new Game("7", "Throne and Liberty", "MMORPG", "https://picsum.photos/200/300", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam voluptua.");
        List<Game> games = List.of(game1, game2);
        gameRepository.saveAll(games);

        //WHEN
        mockMvc.perform(get("/api/games"))

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
