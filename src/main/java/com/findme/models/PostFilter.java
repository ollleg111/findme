package com.findme.models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFilter {
    boolean ownerPosts;
    boolean friendsPosts;
    long userId;
}
