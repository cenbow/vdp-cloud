package com.vdp.sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.vdp.core.tools.redis.config.RedisProps;

@RestController
public class MqController {
	@Autowired
	private RedisProps props;
}
