package com.dch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insert() {
		String sql = "insert into role values(default,?,?)";
		jdbcTemplate.update(sql,"fuck","fuckyou");
	}
}
