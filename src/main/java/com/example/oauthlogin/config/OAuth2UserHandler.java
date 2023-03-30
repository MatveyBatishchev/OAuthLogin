package com.example.oauthlogin.config;

import com.example.oauthlogin.model.domain.User;
import com.example.oauthlogin.model.domain.UserProvider;
import com.example.oauthlogin.model.domain.UserRole;
import com.example.oauthlogin.model.domain.responseSchemas.GithubEmailResponse;
import com.example.oauthlogin.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * Example {@link OAuthConsumer} to perform JIT provisioning of an {@link OAuth2User}.
 */
@Component
@RequiredArgsConstructor
public final class OAuth2UserHandler implements OAuthConsumer<OAuth2User> {

    private final UserRepository userRepository;

    private final WebClient webClient;

    @Override
    public void accept(OAuth2User user, String authProvider) {

        switch (authProvider) {
            case "google" -> {
                if (userRepository.findByEmail(user.getName()).isEmpty()) {
                    userRepository.save(User.builder()
                            .email(user.getName())
                            .name(user.getAttribute("name"))
                            .image(user.getAttribute("picture"))
                            .provider(UserProvider.GOOGLE)
                            .roles(Collections.singletonList(UserRole.USER))
                            .build());
                }
            }
            case "github" -> {
                List<GithubEmailResponse> githubEmails = webClient.get()
                        .uri("https://api.github.com/user/emails")
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<GithubEmailResponse>>() {})
                        .block();

                Assert.notNull(githubEmails, "Retrieved github emails are null");
//                String githubEmail = githubEmails
//                        .stream()
//                        .filter(GithubEmailResponse::isPrimary)
//                        .findFirst()
//                        .orElseThrow(() ->  new NullPointerException("There is no primary email in github response"))
//                        .getEmail();

                // Assume primary email is always first in response
                String githubEmail = githubEmails.get(0).getEmail();

                if (userRepository.findByEmail(githubEmail).isEmpty()) {
                    userRepository.save(User.builder()
                            .email(githubEmail)
                            .name(user.getAttribute("name"))
                            .image(user.getAttribute("avatar_url"))
                            .provider(UserProvider.GITHUB)
                            .roles(Collections.singletonList(UserRole.USER))
                            .build());
                }
            }
        }

    }

}
