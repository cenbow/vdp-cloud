package com.vdp.core.redis.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.vdp.core.redis.utils.KeyInfo;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * @ClassName: RedisClientCluster 针对Redis3.0的cluster集群,
 *             通过JedisCluster操作Redis的工具类
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年10月31日 下午5:40:11
 * @version 1.0
 */
@Component
@ConditionalOnBean(JedisCluster.class)
public class RedisClientCluster implements RedisClient {
	@Autowired
	private JedisCluster jedisCluster;

//	public void setJedisCluster(JedisCluster jedisCluster) {
//		this.jedisCluster = jedisCluster;
//	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public String type(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setbit(String key, long offset, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getbit(String key, long offset) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long setrange(String key, long offset, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setnx(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decrBy(String key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incrBy(String key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long append(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String substr(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		Long hsetnx = jedisCluster.hsetnx(key, field, value);
		return hsetnx;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		String hmset = jedisCluster.hmset(key, hash);
		return hmset;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		List<String> hmget = jedisCluster.hmget(key, fields);
		return hmget;
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Long hincrBy = jedisCluster.hincrBy(key, field, value);
		return hincrBy;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return jedisCluster.hexists(key, field);
	}

	@Override
	public Long del(String host, int port, String key) {
		return null;
	}

	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public Long hlen(String key) {
		Long hlen = jedisCluster.hlen(key);
		return hlen;
	}

	@Override
	public Set<String> hkeys(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> hvals(String key) {
		List<String> hvals = jedisCluster.hvals(key);
		return hvals;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Map<String, String> hgetAll = jedisCluster.hgetAll(key);
		return hgetAll;
	}

	@Override
	public Long rpush(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, List<String> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String... string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ltrim(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lindex(String key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lset(String key, long index, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lrem(String key, long count, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blpop(String key, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String brpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String brpop(String key, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(String key, String... member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String spop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sismember(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String srandmember(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrem(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrevrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zscore(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> sort(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByRank(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exists(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String type(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expire(byte[] key, int seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expireAt(byte[] key, long unixTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long ttl(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decrBy(byte[] key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decr(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incrBy(byte[] key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incr(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long append(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] substr(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hincrBy(byte[] key, byte[] field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hlen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<byte[]> hvals(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(byte[] key, byte[] string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(byte[] key, byte[] string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ltrim(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] lindex(byte[] key, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lset(byte[] key, int index, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lrem(byte[] key, int count, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] lpop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blpop(int timeout, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] rpop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String brpop(int timeout, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] spop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] srandmember(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrange(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrem(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrange(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zscore(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> sort(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByRank(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jedis getShard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jedis getShard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JedisShardInfo getShardInfo(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JedisShardInfo getShardInfo(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyTag(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<String> keys(String pattern) {
		TreeSet<String> keys = new TreeSet<>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		return keys;
	}

	@Override
	public Collection<JedisShardInfo> getAllShardInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Jedis> getAllShards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blpop2(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, KeyInfo>> keys() {
		// TODO Auto-generated method stub
		return null;
	}

}
