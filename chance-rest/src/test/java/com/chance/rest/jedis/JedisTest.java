package com.chance.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	/**
	 * 单机版Jedis连接测试
	 */
	@Test
	public void testJedisSingle() {
		// 创建一个Jedis对象
		Jedis jedis = new Jedis("192.168.25.128", 6379);
		// 调用Jedis对象的方法，方法名称和redis的命令一致
		jedis.set("key1", "jedis test");
		System.out.println(jedis.get("key1"));
		// 关闭redis
		jedis.close();
	}
	
	/**
	 * 单机版JedisPool连接池测试
	 */
	@Test
	public void testJedisPool() {
		// 使用连接池管理Jedis连接
		JedisPool pool = new JedisPool("192.168.25.128", 6379);
		// 获取一个Jedis连接
		Jedis jedis = pool.getResource();
		
		jedis.set("key2", "JedisPool Test");
		System.out.println(jedis.get("key2"));
		// 关闭连接
		jedis.close();
		pool.close();
	}
	
	/**
	 * 集群版测试
	 */
	@Test
	public void testJedisCluster() {
		// 设置集群的所有节点
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		
		// 创建JedisCluster对象
		JedisCluster cluster = new JedisCluster(nodes);
		
		// 设置值并取值
		cluster.set("key1", "1000");
		System.out.println(cluster.get("key1"));
		
		// 关闭连接
		cluster.close();
	}
	
	/**
	 * Spring配置测试-单机版
	 */
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool)ioc.getBean("redisClient");
		Jedis jedis = pool.getResource();
		System.out.println(jedis.get("key1"));
		jedis.close();
		pool.close();
	}
	
	/**
	 * Spring配置测试-集群版
	 */
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster cluster = (JedisCluster)ioc.getBean("redisClusterClient");
		
		System.out.println();
		System.out.println(cluster.get("key1"));
		System.out.println();
		
		cluster.close();
	}
	
}
