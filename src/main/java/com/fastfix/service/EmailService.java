package com.fastfix.service;

import com.fastfix.entity.User;
import jakarta.mail.MessagingException;

public interface EmailService {

    void updateUserAccountDataEmail(User user, String siteUrl) throws MessagingException;
}
