package com.stackroute.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.BlogController;
import com.stackroute.domain.Blog;
import com.stackroute.exceptions.BlogAlreadyExistsException;
import com.stackroute.exceptions.BlogNotFoundException;
import com.stackroute.exceptions.GlobalExceptionHandler;
import com.stackroute.service.BlogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {

    private MockMvc mockMvc;
    @Mock
    BlogService blogService;
    @InjectMocks
    private BlogController blogController;

    private Blog blog;
    private List<Blog> blogList;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).setControllerAdvice(new GlobalExceptionHandler()).build();
        blog = new Blog();
        blog.setBlogId(1);
        blog.setBlogTitle("DemoBlog");
        blog.setAuthorName("Imneet");
        blog.setBlogContent("SampleBlogforTesting");
        blogList = new ArrayList<>();
        blogList.add(blog);
    }

    @AfterEach
    void tearDown() {
        blog = null;
    }

    @Test
    void givenBlogToSaveThenShouldReturnSavedBlog() throws Exception {
        when(blogService.saveBlog(any())).thenReturn(blog);
        mockMvc.perform(post("/api/v1/blog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenBlogToSaveThenShouldNotReturnSavedBlog() throws Exception {
        when(blogService.saveBlog((Blog) any())).thenThrow(BlogAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/blog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenGetAllBlogsThenShouldReturnListOfAllBlogs() throws Exception {
        when(blogService.getAllBlogs()).thenReturn(blogList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/blogs")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(blog)))
                .andDo(MockMvcResultHandlers.print());
        verify(blogService).getAllBlogs();
        verify(blogService, times(1)).getAllBlogs();

    }

    @Test
    void givenBlogIdThenShouldReturnRespectiveBlog() throws Exception, BlogNotFoundException {
        when(blogService.getBlogById(blog.getBlogId())).thenReturn(blog);
        mockMvc.perform(get("/api/v1/blog/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    void givenBlogIdToDeleteThenShouldReturnDeletedBlog() throws Exception {
        when(blogService.deleteBlog(blog.getBlogId())).thenReturn(blog);
        mockMvc.perform(delete("/api/v1/blog/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenBlogIdToDeleteThenShouldNotReturnDeletedBlog() throws BlogNotFoundException, Exception {
        when(blogService.deleteBlog(blog.getBlogId())).thenThrow(BlogNotFoundException.class);
        mockMvc.perform(delete("/api/v1/blog/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenBlogToUpdateThenShouldReturnUpdatedBlog() throws Exception {
        when(blogService.updateBlog(any())).thenReturn(blog);
        mockMvc.perform(put("/api/v1/blog").contentType(MediaType.APPLICATION_JSON).content(asJsonString(blog)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenBlogToUpdateThenShouldNotReturnUpdatedBlog() throws BlogNotFoundException, Exception {
        when(blogService.updateBlog(any())).thenThrow(BlogNotFoundException.class);
        mockMvc.perform(put("/api/v1/blog").contentType(MediaType.APPLICATION_JSON).content(asJsonString(blog)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}