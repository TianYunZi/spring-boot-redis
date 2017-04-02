package cn.timebusker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.timebusker.entity.DemoInfoToCache;

/**
 * 对象存储测试
 * 
 * @author Administrator
 *
 *         通过自动配置的StringRedisTemplate对象进行Redis的读写操作， 该对象从命名中就可注意到支持的是String类型，
 *         StringRedisTemplate就相当于RedisTemplate<String, String>的实现
 * 
 *         spring-data-redis中有RedisTemplate<K, V>接口
 *         除了String类型，我们还经常会在Redis中存储对象，使用类似RedisTemplate<String,
 *         User>来初始化并进行操作。 但是Spring Boot并不支持直接使用，需要我们自己实现RedisSerializer
 *         <T>接口来对传入对象进行序列化和反序列化。
 * 
 *         总之，存储在redis中的对象需要序列化为字符串，类似于IO流里传输对象
 * 
 *         以特定的方式对类实例的瞬时状态进行编码保存的一种操作 序列化作用的对象是类的实例
 *         对实例进行序列化,就是保存实例当前在内存中的状态.包括实例的每一个属性的值和引用等
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class RedisObjectSerializerTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, DemoInfoToCache> redisTemplate;

	@Test
	public void test() throws Exception {

		System.out.println(stringRedisTemplate);
		
		System.out.println(redisTemplate);
		
		stringRedisTemplate.opsForValue().set("aaa", "111");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

		// 保存对象
		DemoInfoToCache user = new DemoInfoToCache(1L, "超人", "qqq");
		redisTemplate.opsForValue().set(user.getName(), user);
		System.out.println(redisTemplate.opsForValue().get("超人"));
		System.out.println(((DemoInfoToCache) redisTemplate.opsForValue().get("超人")));
		System.out.println(((DemoInfoToCache) redisTemplate.opsForValue().get("超人")).toString());
		System.out.println("\n******************************************************\n");
	}
}
