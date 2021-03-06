package com.github.conanchen.gedit.hello.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.conanchen.gedit.hello.graphql.mongo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@EnableAutoConfiguration
public class Query implements GraphQLQueryResolver {
    @Autowired
    private CustomerRepository customerRepository;

    private PostDao postDao;

    public Query(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<Post> recentPosts(int count, int offset) {
        System.out.println("customerRepository=" + customerRepository.toString());
        return postDao.getRecentPosts(count, offset);
    }
}
