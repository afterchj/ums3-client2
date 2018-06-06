package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CommentDao;
import com.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	private CommentDao commentDao;

	@Autowired
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
