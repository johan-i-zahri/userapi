package com.test.usersapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubApiService {

    public GithubApiService() {
    }

    public String searchUsers(String searchQuery) {
        String url = "https://api.github.com/search/users?q=" + searchQuery;
        //System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error calling Github API");
        }
    }
}
