package web.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.dao.JdbcService;

@Service
public class TestService {
	@Resource
	private JdbcService jdbcService;
}
