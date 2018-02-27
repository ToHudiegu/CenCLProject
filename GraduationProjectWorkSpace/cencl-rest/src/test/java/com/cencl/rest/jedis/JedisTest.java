package com.cencl.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	@Test
	public void TestJedis() {
		// 创建一个Jedis对象
		Jedis jedis = new Jedis("192.168.1.224", 6379);
//		Jedis jedis = new Jedis("192.168.43.242", 6379);

		// 调用Jedis方法(方法名称和Redis一致)
		jedis.set("key1", "jedis Test");
		String string = jedis.get("key1");

		System.out.println(string);

		// 关闭Jedis
		jedis.close();

	}

	// 使用Jedis连接池
	@Test
	public void TestJedisPool() {
		// 创建一个JedisPool对象
		JedisPool jedisPool = new JedisPool("192.168.1.224", 6379);
//		JedisPool jedisPool = new JedisPool("192.168.43.242", 6379);
		
		// 从连接池中获得对象
		Jedis jedis = jedisPool.getResource();

		// 调用Jedis方法(方法名称和Redis一致)
		String string = jedis.get("key1");

		System.out.println(string);

		// 关闭Jedis
		jedis.close();
	}

	@Test
	public void testSpringJedisSingle() {
		// 初始化ApplicationContext
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext*.xml");
		
		//创建实例
		JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
		
		//获得Jedis对象
		Jedis jedis = jedisPool.getResource();
		
		String string = jedis.get("key1");
		
		System.out.println(string);
		
		jedis.close();
		jedisPool.close();
	}
	
	
	
	@Test
	public void testSpringJedisCluster(){
		// 初始化ApplicationContext
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext*.xml");
		
		//创建实例
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
		
		String string = jedisCluster.get("key1");
		
		System.out.println(string);
		
		jedisCluster.close();
		
	}

}
