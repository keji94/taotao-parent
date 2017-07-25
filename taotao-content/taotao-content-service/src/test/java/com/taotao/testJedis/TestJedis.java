package com.taotao.testJedis;

import com.taotao.content.service.redis.JedisClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wb-ny291824 on 2017/7/14.
 */
public class TestJedis {

    public static void main(String[] args) {
   /*     //创建一个Jedis对象.需要指定服务端的ip及端口
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //使用jedis对象操作数据库,每个redis命令对应一个方法
        jedis.set("keji","帅");
        String result = jedis.get("keji");
        System.out.println(result);*/

   //测试JedisPool
/*        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("jedispool","hahaah");
        String jedispool = jedis.get("jedispool");
        System.out.println(jedispool);
        jedis.close();
        jedisPool.close();*/

    //测试Jedis和spring整合
        //初始化spring容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从容器中获得JedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("first","100");
        String first = jedisClient.get("first");
        System.out.println(first);
    }
/*    @Test
    public void testJedis() throws Exception{

    }*/
}
