package com.stackroute.service;

import com.stackroute.exceptions.BlogAlreadyExistsException;
import com.stackroute.domain.Blog;
import com.stackroute.exceptions.BlogNotFoundException;
import com.stackroute.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/* This is ServiceImplementation Class which should implement BlogService Interface and override all the implemented methods.
 * Handle suitable exceptions for all the implemented methods*/

@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog saveBlog(Blog blog) throws BlogAlreadyExistsException {
        if(blogRepository.existsById(blog.getBlogId())){
            throw new BlogAlreadyExistsException();
        }
        Blog newBlog = blogRepository.save(blog);
        return newBlog;
    }

    @Override
    public List<Blog> getAllBlogs() {
        return (List<Blog>) blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(int id) throws BlogNotFoundException {
        if(!blogRepository.findById(id).isPresent()){
            throw new BlogNotFoundException();
        }
        return blogRepository.findById(id).get() ;
    }

    @Override
    public Blog deleteBlog(int id) throws BlogNotFoundException {
        Blog blog = null;
        Optional optional = blogRepository.findById(id);
        if(!optional.isPresent()){
            throw new BlogNotFoundException();
        }else {
            blog = blogRepository.findById(id).get();
            blogRepository.deleteById(id);
        }
        return blog;
    }

    @Override
    public Blog updateBlog(Blog blog) throws BlogNotFoundException, BlogAlreadyExistsException {
        Blog updtBlog = null;
        Optional optional = blogRepository.findById(blog.getBlogId());
        if(!optional.isPresent()){
            throw new BlogNotFoundException();
        }else {
            Blog getBlog = blogRepository.findById(blog.getBlogId()).get();
            getBlog.setBlogContent(blog.getBlogContent());
            getBlog.setBlogTitle(blog.getBlogTitle());
            getBlog.setAuthorName(blog.getAuthorName());
            updtBlog = saveBlog(getBlog);
        }
        return updtBlog;
    }
}

