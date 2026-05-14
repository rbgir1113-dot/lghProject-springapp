package com.app.springapp.service;

public interface CommunityAuthService {
    public Long getUserId();

    public void checkUserValidity(Long userId);
}
