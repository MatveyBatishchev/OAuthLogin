package com.example.oauthlogin.model.domain.responseSchemas;

import lombok.Data;

@Data
public class GithubEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}
