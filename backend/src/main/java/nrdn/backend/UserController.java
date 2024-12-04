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
}
