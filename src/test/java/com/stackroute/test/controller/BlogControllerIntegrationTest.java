package com.stackroute.test.controller;

import com.stackroute.domain.Blog;
import com.stackroute.exceptions.BlogAlreadyExistsException;
import com.stackroute.exceptions.BlogNotFoundException;
import com.stackroute.service.BlogService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BlogControllerIntegrationTest {
    @Autowired
    private BlogService blogService;
    private Blog blog;
    private List<Blog> blogList;

    @BeforeEach
    void setUp() {
        blog = new Blog(1, "Blog 1", "John", "Sample Blog for Testing");
        blogList = new ArrayList<>();
        blogList.add(blog);
    }

    @AfterEach
    void tearDown() {
        blog = null;
    }

    @Test
    void givenBlogToSaveThenShouldReturnTheSavedBlog() throws BlogAlreadyExistsException {
        Blog savedBlog = blogService.saveBlog(blog);
        assertNotNull(savedBlog);
        assertEquals(blog.getBlogId(), savedBlog.getBlogId());
    }

    @Test
    void givenBlogToSaveThenThrowException() throws BlogAlreadyExistsException {
        assertNotNull(blogService.saveBlog(blog));
        assertThrows(BlogAlreadyExistsException.class, () -> blogService.saveBlog(blog));
    }

    @Test
    void givenBlogToDeleteThenShouldReturnTheDeletedBlog() throws BlogNotFoundException, BlogAlreadyExistsException {
        assertNotNull(blogService.saveBlog(blog));
        Blog deletedBlog = blogService.deleteBlog(blog.getBlogId());
        assertNotNull(deletedBlog);
    }

    @Test
    void givenBlogToDeleteThenThrowException() throws BlogNotFoundException {
        assertThrows(BlogNotFoundException.class, () -> blogService.deleteBlog(blog.getBlogId()));
    }

    @Test
    void givenCallToGetAllBlogsThenListShouldNotBeNull() throws Exception {
        List<Blog> retrievedBlogs = blogService.getAllBlogs();
        assertNotNull(retrievedBlogs);
    }

    @Test
    void givenBlogToUpdateThenShouldReturnUpdatedBlog() throws BlogNotFoundException, BlogAlreadyExistsException {
        Blog savedBlog = blogService.saveBlog(blog);
        savedBlog.setBlogContent("update content");
        Blog updatedBlog = blogService.updateBlog(savedBlog);
        assertNotNull(updatedBlog);
        assertEquals("update content", updatedBlog.getBlogContent());
    }

    @Test
    void givenBlogToUpdateThenThrowException() throws BlogNotFoundException {
        assertThrows(BlogNotFoundException.class, () -> blogService.updateBlog(blog));
    }

    @Test
    void givenBlogIdThenShouldReturnRespectiveBlog() throws BlogNotFoundException, BlogAlreadyExistsException {
        assertNotNull(blogService.saveBlog(blog));
        Blog retrievedBlog = blogService.getBlogById(blog.getBlogId());
        assertNotNull(retrievedBlog);
    }

    @Test
    void givenBlogIdThenShouldThrowException() throws BlogNotFoundException {
        assertThrows(BlogNotFoundException.class, () -> blogService.getBlogById(blog.getBlogId()));
    }


}