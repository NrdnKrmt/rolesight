package nrdn.backend.security;

import lombok.RequiredArgsConstructor;
import nrdn.backend.User;
import nrdn.backend.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oauthUser = super.loadUser(userRequest);

        if (!userRepo.existsById(oauthUser.getName())) {
            createAndSaveUser(oauthUser);
        }

        return oauthUser;
    }

    private void createAndSaveUser(OAuth2User oauthUser) {
        User newUser = User.builder()
                .id(oauthUser.getName())
                .preferences(new ArrayList<>())
                .build();

        userRepo.save(newUser);
    }
}