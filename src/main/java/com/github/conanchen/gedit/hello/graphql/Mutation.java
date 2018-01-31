package com.github.conanchen.gedit.hello.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import java.util.UUID;

public class Mutation implements GraphQLMutationResolver {
    private PostDao postDao;

    public Mutation(PostDao postDao) {
        this.postDao = postDao;
    }

    public Post writePost(String title, String text, String category, String author) {
        Post post = new Post();
        post.setId(UUID.randomUUID()
                .toString());
        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setAuthorId(author);
        postDao.savePost(post);

        return post;
    }

    public Post writePostWithInput(PostInput postInput) {
        Post post = new Post();
        post.setId(UUID.randomUUID()
                .toString());
        post.setTitle(postInput.getTitle());
        post.setText(postInput.getText());
        post.setCategory(postInput.getCategory());
        post.setAuthorId(postInput.getAuthorId());
        postDao.savePost(post);

        return post;
    }
}
