package com.vdp.core.redis.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.vdp.core.redis.utils.KeyInfo;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * RedisClientShard 针对客户端的Shard集群, 通过ShardedJedisPool操作Redis的工具类
 * 
 * @author longxiaonan
 * @email longxiaonan@163.com
 * @date 2017年11月1日 上午11:38:06
 * @version 1.0
 */
@Component
@ConditionalOnBean(ShardedJedisPool.class)
public class RedisClientShard implements RedisClient {

	private static final Logger log = LoggerFactory.getLogger(RedisClientShard.class);

	private ShardedJedisPool ShardedJedisPool;

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		ShardedJedisPool = shardedJedisPool;
	}

	public void disconnect() {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		shardedJedis.disconnect();
	}

	/**
	 * 设置单个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.set(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取单个值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Boolean exists(String key) {
		Boolean result = false;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.exists(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String type(String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.type(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 设置一个key的过期时间
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            多少秒后过期
	 * @return 1:设置了过期时间, null:没有设置过期时间/不能设置过期时间
	 */
	public Long expire(String key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 设置key在某个时间点过期
	 * 
	 * @param key
	 *            key值
	 * @param unixTime
	 *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
	 * @return 1:设置了过期时间, null:没有设置过期时间/不能设置过期时间
	 */
	public Long expireAt(String key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.expireAt(key, unixTime);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long ttl(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.ttl(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public boolean setbit(String key, long offset, boolean value) {

		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		boolean result = false;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setbit(key, offset, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public boolean getbit(String key, long offset) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		boolean result = false;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getbit(key, offset);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public long setrange(String key, long offset, String value) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		long result = 0;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setrange(key, offset, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String getrange(String key, long startOffset, long endOffset) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getrange(key, startOffset, endOffset);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String getSet(String key, String value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getSet(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long setnx(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setnx(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String setex(String key, int seconds, String value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.setex(key, seconds, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long decrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.decrBy(key, integer);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long decr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.decr(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long incrBy(String key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.incrBy(key, integer);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long incr(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.incr(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long append(String key, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.append(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String substr(String key, int start, int end) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.substr(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 设置HashSet对象
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            域名
	 * @param value
	 *            Json String or String value
	 * @return
	 */
	public Long hset(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hset(key, field, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String hget(String key, String field) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hget(key, field);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hsetnx(String key, String field, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hsetnx(key, field, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String hmset(String key, Map<String, String> hash) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hmset(key, hash);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<String> hmget(String key, String... fields) {
		List<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hmget(key, fields);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hincrBy(String key, String field, long value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hincrBy(key, field, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Boolean hexists(String key, String field) {
		Boolean result = false;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hexists(key, field);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long del(String host, int port, String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Collection<Jedis> jedisList = shardedJedis.getAllShards();
		try {
			for (Jedis jedis : jedisList) {
				if (jedis.getClient().getHost().equals(host) && jedis.getClient().getPort() == port) {
					result = jedis.del(key);
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long del(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.del(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 删除HashSet对象
	 * 
	 * @param key
	 * @param field
	 *            域名
	 * @return 删除的记录数
	 */
	public Long hdel(String key, String... field) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hdel(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 返回 domain 指定的哈希key值总数
	 * 
	 * @param key
	 * @return
	 */
	public Long hlen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hlen(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hkeys(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的value值
	 * 
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hvals(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Map<String, String> hgetAll(String key) {
		Map<String, String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.hgetAll(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	// ================list ====== l表示 list或 left, r表示right====================
	public Long rpush(String key, String string) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.rpush(key, string);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 添加到list
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	public Long lpush(String key, List<String> list) {
		Long result = null;
		if (key == null || list == null || list.size() == 0) {
			return null;
		}
		for (String value : list) {
			result = lpush(key, value);
		}
		return result;
	}

	/**
	 * 添加到list
	 * 
	 * @param key
	 * @param string
	 * @return
	 */
	public Long lpush(String key, String... string) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lpush(key, string);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 检查List长度
	 * 
	 * @param key
	 *            list的key
	 * @return
	 */
	public Long llen(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.llen(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取列表, 如果获取整个列表, 那么start为0, end为-1
	 * 
	 * @param key
	 *            列表key
	 * @param start
	 *            初始位置, 从0开始
	 * @param end
	 *            结束位置, 最大值为列表长度减一
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		List<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String ltrim(String key, long start, long end) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.ltrim(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String lindex(String key, long index) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lindex(key, index);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String lset(String key, long index, String value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lset(key, index, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 从list中删除value
	 * 
	 * @param key
	 *            list的key
	 * @param count
	 *            要删除的个数
	 * @param value
	 * @return
	 */
	public Long lrem(String key, long count, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lrem(key, count, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String lpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.lpop(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String blpop2(String key) {
		return blpop(key, 0);
	}

	public String blpop(String key, int timeout) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			List<String> list = shardedJedis.blpop(timeout, key);
			if (!list.isEmpty()) {
				return list.get(1);
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String rpop(String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.rpop(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String brpop(String key) {
		return brpop(key, 0);
	}

	public String brpop(String key, int timeout) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			List<String> list = shardedJedis.brpop(timeout, key);
			if (!list.isEmpty()) {
				return list.get(1);
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 添加到Set中
	 * 
	 * @param key
	 *            set中的key
	 * @param member
	 * @return 1:添加成功; 0:添加失败(key已经存在); null:发生异常
	 */
	public Long sadd(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sadd(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> smembers(String key) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.smembers(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 从set中删除member
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long srem(String key, String... member) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();

		Long result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.srem(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String spop(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.spop(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 检查Set长度
	 * 
	 * @param key
	 *            Set的key
	 * @return Set的长度; null:未获取到长度
	 */
	public Long scard(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Long result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.scard(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 判断值是否包含在set中
	 * 
	 * @param key
	 *            Set的key
	 * @param member
	 * @return
	 */
	public Boolean sismember(String key, String member) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Boolean result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.sismember(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String srandmember(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.srandmember(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 设置排序集合
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取排序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 删除排序集合
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrem(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrem(key, member);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Double zincrby(String key, double score, String member) {
		Double result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zincrby(key, score, member);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrank(key, member);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zrevrank(String key, String member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrank(key, member);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> zrevrange(String key, int start, int end) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrange(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeWithScores(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeWithScores(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zcard(String key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcard(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取排序的打分
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) {
		Double result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zscore(key, member);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<String> sort(String key) {
		List<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		List<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key, sortingParameters);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zcount(String key, double min, double max) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcount(key, min, max);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Set<String> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zremrangeByRank(String key, int start, int end) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByRank(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zremrangeByScore(String key, double start, double end) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByScore(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.linsert(key, where, pivot, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String set(byte[] key, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.set(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] get(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.get(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Boolean exists(byte[] key) {
		Boolean result = false;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.exists(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String type(byte[] key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.type(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long expire(byte[] key, int seconds) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.expire(key, seconds);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long expireAt(byte[] key, long unixTime) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.expireAt(key, unixTime);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long ttl(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.ttl(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] getSet(byte[] key, byte[] value) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.getSet(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long setnx(byte[] key, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.setnx(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.setex(key, seconds, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long decrBy(byte[] key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.decrBy(key, integer);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long decr(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.decr(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long incrBy(byte[] key, long integer) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.incrBy(key, integer);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long incr(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.incr(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long append(byte[] key, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.append(key, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] substr(byte[] key, int start, int end) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.substr(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hset(key, field, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] hget(byte[] key, byte[] field) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hget(key, field);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hsetnx(key, field, value);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hmset(key, hash);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hmget(key, fields);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hincrBy(byte[] key, byte[] field, long value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hincrBy(key, field, value);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            域名
	 * @return
	 */
	public Boolean hexists(byte[] key, byte[] field) {
		Boolean result = false;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hexists(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hdel(byte[] key, byte[] field) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hdel(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long hlen(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hlen(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 * 
	 * @param key
	 * @return
	 */
	public Set<byte[]> hkeys(byte[] key) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.hkeys(key);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Collection<byte[]> hvals(byte[] key) {
		Collection<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hvals(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.hgetAll(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long rpush(byte[] key, byte[] string) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.rpush(key, string);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long lpush(byte[] key, byte[] string) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lpush(key, string);
		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long llen(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.llen(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<byte[]> lrange(byte[] key, int start, int end) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.lrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String ltrim(byte[] key, int start, int end) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.ltrim(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] lindex(byte[] key, int index) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lindex(key, index);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String lset(byte[] key, int index, byte[] value) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lset(key, index, value);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long lrem(byte[] key, int count, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lrem(key, count, value);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] lpop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.lpop(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String blpop(String key) {
		return this.blpop(0, key);
	}

	public String blpop(int timeout, String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {

			List<String> list = shardedJedis.blpop(timeout, key);
			if (!list.isEmpty()) {
				return list.get(1);
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] rpop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.rpop(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String brpop(int timeout, String key) {
		String result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			List<String> list = shardedJedis.brpop(timeout, key);
			if (!list.isEmpty()) {
				return list.get(1);
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long sadd(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sadd(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取set
	 * 
	 * @param key
	 * @return
	 */
	public Set<byte[]> smembers(byte[] key) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.smembers(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long srem(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.srem(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] spop(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.spop(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long scard(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.scard(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Boolean sismember(byte[] key, byte[] member) {
		Boolean result = false;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sismember(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public byte[] srandmember(byte[] key) {
		byte[] result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.srandmember(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zadd(byte[] key, double score, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zadd(key, score, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 获取排序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zrem(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrem(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Double zincrby(byte[] key, double score, byte[] member) {
		Double result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zincrby(key, score, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zrank(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrank(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zrevrank(byte[] key, byte[] member) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrank(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<byte[]> zrevrange(byte[] key, int start, int end) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrange(key, start, end);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeWithScores(key, start, end);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeWithScores(key, start, end);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zcard(byte[] key) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zcard(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Double zscore(byte[] key, byte[] member) {
		Double result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zscore(key, member);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<byte[]> sort(byte[] key) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		List<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.sort(key, sortingParameters);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 计算排序长度
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long zcount(byte[] key, double min, double max) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.zcount(key, min, max);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScore(key, min, max, offset, count);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		Set<byte[]> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		Set<Tuple> result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zremrangeByRank(byte[] key, int start, int end) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByRank(key, start, end);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long zremrangeByScore(byte[] key, double start, double end) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.zremrangeByScore(key, start, end);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		Long result = null;
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return result;
		}

		try {

			result = shardedJedis.linsert(key, where, pivot, value);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Jedis getShard(byte[] key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Jedis result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShard(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Jedis getShard(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Jedis result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShard(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public JedisShardInfo getShardInfo(byte[] key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		JedisShardInfo result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShardInfo(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public JedisShardInfo getShardInfo(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		JedisShardInfo result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getShardInfo(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public String getKeyTag(String key) {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		String result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getKeyTag(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	/**
	 * 监控redis，获取节点信息
	 * 
	 * @return
	 */
	public Map<String, Map<String, KeyInfo>> keys() {
		Map<String, Map<String, KeyInfo>> setMap = new HashMap<String, Map<String, KeyInfo>>();
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		if (shardedJedis == null) {
			return setMap;
		}
		try {
			Collection<Jedis> result = shardedJedis.getAllShards();
			for (Jedis jedis : result) {
				Set<String> keys = jedis.keys("*");
				Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>();
				String host = jedis.getClient().getHost();
				int port = jedis.getClient().getPort();
				for (String key : keys) {
					String type = jedis.type(key);
					long len = 0;
					if ("list".equals(type)) {
						len = jedis.llen(key);
					} else if ("hash".equals(type)) {
						len = jedis.hlen(key);
					}
					long ttl = jedis.ttl(key);
					KeyInfo info = new KeyInfo();
					info.setHost(host);
					info.setPort(port);
					info.setKey(key);
					info.setType(type);
					info.setLen(len);
					info.setTtl(ttl);
					keyMap.put(key, info);
				}
				setMap.put(host + ":" + port, keyMap);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return setMap;
	}

	public Collection<JedisShardInfo> getAllShardInfo() {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Collection<JedisShardInfo> result = null;
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.getAllShardInfo();

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	public Collection<Jedis> getAllShards() {
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
		Collection<Jedis> result = null;
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.getAllShards();

		} catch (Exception e) {
			log.error(e.getMessage(), e);

		} finally {
			shardedJedis.close();
		}
		return result;
	}

	@Override
	public TreeSet<String> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
