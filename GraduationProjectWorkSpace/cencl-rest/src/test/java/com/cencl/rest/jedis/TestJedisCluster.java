package com.cencl.rest.jedis;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestJedisCluster {
	//JedisCluster自带连接池
	@Test
	public void testJedisClusterDemo(){
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.1.224", 7001));
		nodes.add(new HostAndPort("192.168.1.224", 7002));
		nodes.add(new HostAndPort("192.168.1.224", 7003));
		nodes.add(new HostAndPort("192.168.1.224", 7004));
		nodes.add(new HostAndPort("192.168.1.224", 7005));
		nodes.add(new HostAndPort("192.168.1.224", 7006));
		/*
		nodes.add(new HostAndPort("192.168.43.242", 7001));
		nodes.add(new HostAndPort("192.168.43.242", 7002));
		nodes.add(new HostAndPort("192.168.43.242", 7003));
		nodes.add(new HostAndPort("192.168.43.242", 7004));
		nodes.add(new HostAndPort("192.168.43.242", 7005));
		nodes.add(new HostAndPort("192.168.43.242", 7006));
		*/
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		jedisCluster.set("key1", "1000");
		
		String string = jedisCluster.get("key1");
		
		System.out.println(string);
		
		jedisCluster.close();
		
	}
	
	
}
