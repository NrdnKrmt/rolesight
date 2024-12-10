package nrdn.backend;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/preferences")
    public List<Map<String, Object>> getUserPreferencesById(@PathVariable String id) {
        return userService.getUserPreferencesByUserId(id);
    }

    @PostMapping("{id}/preferences/{gameId}/{role}")
    public User addUserPreferencesByGameId(@PathVariable String id, @PathVariable String gameId, @PathVariable String role) {
        return userService.addUserPreferencesByGameId(id, gameId, role);
    }

    @DeleteMapping("{id}/preferences/{gameId}")
    public User removeUserPreferencesByGameId(@PathVariable String id, @PathVariable String gameId) {
        return userService.removeUserPreferencesByGameId(id, gameId);
    }

}
