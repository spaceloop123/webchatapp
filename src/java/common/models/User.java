package common.models;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
