package com.stackroute.service;
import com.stackroute.exceptions.BlogAlreadyExistsException;
import com.stackroute.exceptions.BlogNotFoundException;
import com.stackroute.domain.Blog;


import java.util.List;

/* Throw suitable exceptions for all methods. Also handle Database Connectivity Failure incase your database connectivity fails,it should throw suitable exception *

 */
public interface BlogService {
     /**
      * AbstractMethod to save a blog
      */
     Blog saveBlog(Blog blog) throws BlogAlreadyExistsException;
     /**
      * AbstractMethod to get all blogs
      */
     List<Blog> getAllBlogs() ;
     /**
      * AbstractMethod to get blog by id
      */
     Blog getBlogById(int id) throws BlogNotFoundException;
     /**
      * AbstractMethod to delete blog by id
      */
     Blog deleteBlog(int id) throws BlogNotFoundException ;
     /**
      * AbstractMethod to update a blog
      */
     Blog updateBlog(Blog blog) throws BlogNotFoundException, BlogAlreadyExistsException;
}

