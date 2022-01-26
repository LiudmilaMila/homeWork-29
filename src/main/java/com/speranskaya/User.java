package com.speranskaya;

public class User {
    public int id;
    public String nickname;

    public User() {
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
