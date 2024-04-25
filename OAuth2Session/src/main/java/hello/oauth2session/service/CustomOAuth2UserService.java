package hello.oauth2session.service;

import hello.oauth2session.dto.*;
import hello.oauth2session.entity.UserEntity;
import hello.oauth2session.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User: {}", oAuth2User.getAttributes());

        // 어떤 곳에서 넘어온건지
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }


        // 유저가 DB에 있는가?
        UserEntity existData = userRepository.findByUsernameAndSiteType(oAuth2Response.getProviderId(), oAuth2Response.getProvider());

        String role = null;

        // 신규 회원이다.
        if (existData == null) {
            UserEntity userEntity = new UserEntity();

            userEntity.setUsername(oAuth2Response.getProviderId());
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setUserRoll(UserRoll.ROLL_USER);
            userEntity.setSiteType(oAuth2Response.getProvider());

            userRepository.save(userEntity);
        } else {

            role = String.valueOf(existData.getUserRoll());
            // 기존 회원이라면
            existData.setEmail(oAuth2Response.getEmail());
            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);

    }
}
