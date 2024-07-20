package org.gfg.JBDL_71_Oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    OAuth2AuthorizedClientService clientService;

    @GetMapping("/")
    public String home() {
        return "Hello, Home";
    }

    @GetMapping("/secured")
    public String secured() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();

        log.info("user: {}", authentication.getPrincipal());
        log.info("access token: {}", accessToken);

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        return "Hello, ".concat(user.getAttribute("login")).concat(" from ")
                .concat(user.getAttribute("company"));

//        return "Hello, Secured";
    }
}
