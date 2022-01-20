package com.speranskaya;

public class Message {
    public int id;
    public String text;
    public int userId;
    public int bookId;

    public Message() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
