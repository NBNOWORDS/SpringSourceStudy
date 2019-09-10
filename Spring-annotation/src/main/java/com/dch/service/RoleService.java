package com.dch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.dao.RoleDao;
@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Transactional
	public void insertRole() {
		roleDao.insert();
		System.out.println("³É¹¦");
		int i = 10 / 0;
	}
}
