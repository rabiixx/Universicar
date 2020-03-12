package com.example.intento2.data;

import android.content.Context;
import android.widget.Toast;

import com.example.intento2.RegistrationActivity;
import com.example.intento2.data.model.LoggedInUser;
import com.example.intento2.ui.login.LoginActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            ParseUser.logInInBackground(username, password, new LogInCallback() {

                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user == null) {
                        e.printStackTrace();
                    }
                }
            });

            return new Result.Success<>(ParseUser.getCurrentUser());

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
