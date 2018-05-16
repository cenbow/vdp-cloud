package com.vdp.core.redis.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 该类使用了Java注解，@Configuration与@Bean， 在方法上使用@Bean注解可以让方法的返回值为单例，
 * 该方法的返回值可以直接注入到其他类中去使用.
 * 
 * "@Bean"注解是方法级别的 如果使用的是常用的spring注解@Component， 在方法上没有注解的话，方法的返回值就会是一个多例，
 * 该方法的返回值不可以直接注入到其他类去使用 该方式的注解是类级别的
 * 
 * @author longxn
 *
 */
@Configuration
@ConditionalOnProperty(name = "service_enabled.redis", havingValue = "true")
public class JedisConfig {

	@Autowired
	private RedisProps redisProperties;

	/**
	 * 自定义的redis工具类
	 * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
	 * 
	 * @return
	 */
	@Bean
	public JedisCluster getJedisCluster() {
		String[] serverArray = redisProperties.getClusterNodes().split(",");// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
		Set<HostAndPort> nodes = new HashSet<>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}
		
		int connectionTimeout = redisProperties.getCommandTimeout();
		int soTimeout = redisProperties.getSoTimeout();
		int maxAttempts = redisProperties.getMaxAttempts();
		String password = redisProperties.getPassword();
		
		return new JedisCluster(nodes, connectionTimeout,soTimeout,maxAttempts,password,getJedisPoolConfig());
	}
	
	/** 第三方工具类 redis Data */
	@Bean
	public JedisConnectionFactory getJedisConnectionFactory(){
		//集群模式
//		String[] serverArray = redisProperties.getClusterNodes().split(",");// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
//		RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(Arrays.asList(serverArray));
//		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, getJedisPoolConfig());
//		jedisConnectionFactory.setPassword(redisProperties.getPassword());
//		return jedisConnectionFactory;
		
		//单机模式
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName("183.63.187.149");
		jedisConnectionFactory.setPort(8001);
		jedisConnectionFactory.setTimeout(5_000);
		return jedisConnectionFactory;
	}
	
	private JedisPoolConfig getJedisPoolConfig(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(redisProperties.getMaxTotal());
		poolConfig.setMaxIdle(redisProperties.getMaxIdle());
		poolConfig.setMinIdle(redisProperties.getMinIdle());
		poolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setBlockWhenExhausted(false);
		return poolConfig;
	}

}