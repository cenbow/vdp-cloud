package com.vdp.core.tools.redis.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.vdp.core.tools.redis.utils.KeyInfo;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * ReidsClient接口, 实现类需要继承该类, 实现该类的抽象方法
 * 
 * @author longxiaonan
 * @email longxiaonan@163.com
 * @date 2017年11月1日 上午11:24:19
 * @version 1.0
 */
public interface RedisClient {

	/**
	 * 设置单个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract String set(String key, String value);

	/**
	 * 获取单个值
	 * 
	 * @param key
	 * @return
	 */
	public abstract String get(String key);

	public abstract Boolean exists(String key);

	/**
	 * 返回 key 所储存的值的类型。
	 * 
	 * @param key
	 * @return none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash
	 *         (哈希表)
	 */
	public abstract String type(String key);

	/**
	 * 设置一个key的过期时间
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            多少秒后过期
	 * @return 1:设置了过期时间, null:没有设置过期时间/不能设置过期时间
	 */
	public abstract Long expire(String key, int seconds);

	/**
	 * 设置key在某个时间点过期
	 * 
	 * @param key
	 *            key值
	 * @param unixTime
	 *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
	 * @return 1:设置了过期时间, null:没有设置过期时间/不能设置过期时间
	 */
	public abstract Long expireAt(String key, long unixTime);

	public abstract Long ttl(String key);

	public abstract boolean setbit(String key, long offset, boolean value);

	public abstract boolean getbit(String key, long offset);

	public abstract long setrange(String key, long offset, String value);

	public abstract String getrange(String key, long startOffset, long endOffset);

	public abstract String getSet(String key, String value);

	public abstract Long setnx(String key, String value);

	public abstract String setex(String key, int seconds, String value);

	public abstract Long decrBy(String key, long integer);

	public abstract Long decr(String key);

	public abstract Long incrBy(String key, long integer);

	public abstract Long incr(String key);

	public abstract Long append(String key, String value);

	public abstract String substr(String key, int start, int end);

	/**
	 * 设置HashSet对象
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            域名
	 * @param value
	 *            Json String or String value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。 如果哈希表中域 field
	 *         已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public abstract Long hset(String key, String field, String value);

	public abstract String hget(String key, String field);

	/**
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
	 * 
	 * 若域 field 已经存在，该操作无效。
	 * 
	 * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 设置成功，返回 1 。 如果给定域已经存在且没有操作被执行，返回 0 。
	 */
	public abstract Long hsetnx(String key, String field, String value);

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 此命令会覆盖哈希表中已存在的域。 如果 key
	 * 不存在，一个空哈希表被创建并执行 HMSET 操作。map的key会变成filed
	 * 
	 * @param key
	 * @param hash
	 *            Map<String,String> field作为key, field的值为value
	 * @return
	 */
	public abstract String hmset(String key, Map<String, String> hash);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 nil 值。 因为不存在的 key
	 * 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 * 
	 * @param key
	 * @param fields
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	public abstract List<String> hmget(String key, String... fields);

	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。 增量也可以为负数，相当于对给定域进行减法操作。 如果 key
	 * 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
	 */
	public abstract Long hincrBy(String key, String field, long value);

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * 
	 * @param key
	 * @param field
	 * @return 如果哈希表含有给定域，返回 1 。 如果哈希表不含有给定域，或 key 不存在，返回 0 。
	 */
	public abstract Boolean hexists(String key, String field);

	public abstract Long del(String host, int port, String key);

	public abstract Long del(String key);

	/**
	 * 删除HashSet对象
	 * 
	 * @param key
	 * @param field
	 *            域名
	 * @return 删除的记录数
	 */
	public abstract Long hdel(String key, String... field);

	/**
	 * 返回哈希表 key 中域的数量。
	 * 
	 * @param key
	 * @return 哈希表中域的数量。 当 key 不存在时，返回 0 。
	 */
	public abstract Long hlen(String key);

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 * 
	 * @param key
	 * @return
	 */
	public abstract Set<String> hkeys(String key);

	/**
	 * 返回 domain 指定的哈希集中所有域的value值
	 * 
	 * @param key
	 * @return 一个包含哈希表中所有值的表。 当 key 不存在时，返回一个空表。
	 */
	public abstract List<String> hvals(String key);

	/**
	 * 返回哈希表 key 中，所有的域和值。 在返回值里，紧跟每个域名(field
	 * name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
	 * 
	 * @param key
	 * @return
	 */
	public abstract Map<String, String> hgetAll(String key);

	// ================list ====== l表示 list或 left, r表示right====================
	public abstract Long rpush(String key, String string);

	/**
	 * 添加到list
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	public abstract Long lpush(String key, List<String> list);

	/**
	 * 添加到list
	 * 
	 * @param key
	 * @param string
	 * @return
	 */
	public abstract Long lpush(String key, String... string);

	/**
	 * 检查List长度
	 * 
	 * @param key
	 *            list的key
	 * @return
	 */
	public abstract Long llen(String key);

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
	public abstract List<String> lrange(String key, long start, long end);

	public abstract String ltrim(String key, long start, long end);

	public abstract String lindex(String key, long index);

	public abstract String lset(String key, long index, String value);

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
	public abstract Long lrem(String key, long count, String value);

	/**
	 * 移除并返回列表 key 的头元素。
	 * 
	 * @param key
	 * @return 列表的头元素。 当 key 不存在时，返回 null 。
	 */
	public abstract String lpop(String key);

	/**
	 * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
	 * 超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
	 * 
	 * @param key
	 * @return
	 */
	public abstract String blpop2(String key);

	/**
	 * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 */
	public abstract String blpop(String key, int timeout);

	/**
	 * 移除并返回列表 key 的尾元素。
	 * 
	 * @param key
	 * @return 列表的尾元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract String rpop(String key);

	public abstract String brpop(String key);

	public abstract String brpop(String key, int timeout);

	/**
	 * 添加到Set中
	 * 
	 * @param key
	 *            set中的key
	 * @param member
	 * @return 1:添加成功; 0:添加失败(key已经存在); null:发生异常
	 */
	public abstract Long sadd(String key, String member);

	public abstract Set<String> smembers(String key);

	/**
	 * 从set中删除member
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public abstract Long srem(String key, String... member);

	public abstract String spop(String key);

	/**
	 * 检查Set长度
	 * 
	 * @param key
	 *            Set的key
	 * @return Set的长度; null:未获取到长度
	 */
	public abstract Long scard(String key);

	/**
	 * 判断值是否包含在set中
	 * 
	 * @param key
	 *            Set的key
	 * @param member
	 * @return
	 */
	public abstract Boolean sismember(String key, String member);

	public abstract String srandmember(String key);

	/**
	 * 设置排序集合
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public abstract Long zadd(String key, double score, String member);

	/**
	 * 获取排序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public abstract Set<String> zrange(String key, int start, int end);

	/**
	 * 删除排序集合
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public abstract Long zrem(String key, String member);

	public abstract Double zincrby(String key, double score, String member);

	public abstract Long zrank(String key, String member);

	public abstract Long zrevrank(String key, String member);

	public abstract Set<String> zrevrange(String key, int start, int end);

	public abstract Set<Tuple> zrangeWithScores(String key, int start, int end);

	public abstract Set<Tuple> zrevrangeWithScores(String key, int start, int end);

	public abstract Long zcard(String key);

	/**
	 * 获取排序的打分
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public abstract Double zscore(String key, String member);

	public abstract List<String> sort(String key);

	public abstract List<String> sort(String key, SortingParams sortingParameters);

	public abstract Long zcount(String key, double min, double max);

	public abstract Set<String> zrangeByScore(String key, double min, double max);

	public abstract Set<String> zrevrangeByScore(String key, double max, double min);

	public abstract Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	public abstract Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	public abstract Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	public abstract Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	public abstract Long zremrangeByRank(String key, int start, int end);

	public abstract Long zremrangeByScore(String key, double start, double end);

	public abstract Long linsert(String key, LIST_POSITION where, String pivot, String value);

	public abstract String set(byte[] key, byte[] value);

	public abstract byte[] get(byte[] key);

	public abstract Boolean exists(byte[] key);

	public abstract String type(byte[] key);

	public abstract Long expire(byte[] key, int seconds);

	public abstract Long expireAt(byte[] key, long unixTime);

	public abstract Long ttl(byte[] key);

	public abstract byte[] getSet(byte[] key, byte[] value);

	public abstract Long setnx(byte[] key, byte[] value);

	public abstract String setex(byte[] key, int seconds, byte[] value);

	public abstract Long decrBy(byte[] key, long integer);

	public abstract Long decr(byte[] key);

	public abstract Long incrBy(byte[] key, long integer);

	public abstract Long incr(byte[] key);

	public abstract Long append(byte[] key, byte[] value);

	public abstract byte[] substr(byte[] key, int start, int end);

	public abstract Long hset(byte[] key, byte[] field, byte[] value);

	public abstract byte[] hget(byte[] key, byte[] field);

	public abstract Long hsetnx(byte[] key, byte[] field, byte[] value);

	public abstract String hmset(byte[] key, Map<byte[], byte[]> hash);

	public abstract List<byte[]> hmget(byte[] key, byte[]... fields);

	public abstract Long hincrBy(byte[] key, byte[] field, long value);

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            域名
	 * @return
	 */
	public abstract Boolean hexists(byte[] key, byte[] field);

	public abstract Long hdel(byte[] key, byte[] field);

	public abstract Long hlen(byte[] key);

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 * 
	 * @param key
	 * @return
	 */
	public abstract Set<byte[]> hkeys(byte[] key);

	public abstract Collection<byte[]> hvals(byte[] key);

	public abstract Map<byte[], byte[]> hgetAll(byte[] key);

	public abstract Long rpush(byte[] key, byte[] string);

	public abstract Long lpush(byte[] key, byte[] string);

	public abstract Long llen(byte[] key);

	public abstract List<byte[]> lrange(byte[] key, int start, int end);

	public abstract String ltrim(byte[] key, int start, int end);

	public abstract byte[] lindex(byte[] key, int index);

	public abstract String lset(byte[] key, int index, byte[] value);

	public abstract Long lrem(byte[] key, int count, byte[] value);

	public abstract byte[] lpop(byte[] key);

	public abstract String blpop(String key);

	public abstract String blpop(int timeout, String key);

	public abstract byte[] rpop(byte[] key);

	public abstract String brpop(int timeout, String key);

	public abstract Long sadd(byte[] key, byte[] member);

	/**
	 * 获取set
	 * 
	 * @param key
	 * @return
	 */
	public abstract Set<byte[]> smembers(byte[] key);

	public abstract Long srem(byte[] key, byte[] member);

	public abstract byte[] spop(byte[] key);

	public abstract Long scard(byte[] key);

	public abstract Boolean sismember(byte[] key, byte[] member);

	public abstract byte[] srandmember(byte[] key);

	public abstract Long zadd(byte[] key, double score, byte[] member);

	/**
	 * 获取排序集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public abstract Set<byte[]> zrange(byte[] key, int start, int end);

	public abstract Long zrem(byte[] key, byte[] member);

	public abstract Double zincrby(byte[] key, double score, byte[] member);

	public abstract Long zrank(byte[] key, byte[] member);

	public abstract Long zrevrank(byte[] key, byte[] member);

	public abstract Set<byte[]> zrevrange(byte[] key, int start, int end);

	public abstract Set<Tuple> zrangeWithScores(byte[] key, int start, int end);

	public abstract Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end);

	public abstract Long zcard(byte[] key);

	public abstract Double zscore(byte[] key, byte[] member);

	public abstract List<byte[]> sort(byte[] key);

	public abstract List<byte[]> sort(byte[] key, SortingParams sortingParameters);

	/**
	 * 计算排序长度
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public abstract Long zcount(byte[] key, double min, double max);

	public abstract Set<byte[]> zrangeByScore(byte[] key, double min, double max);

	public abstract Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count);

	public abstract Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max);

	public abstract Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count);

	public abstract Set<byte[]> zrevrangeByScore(byte[] key, double max, double min);

	public abstract Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count);

	public abstract Long zremrangeByRank(byte[] key, int start, int end);

	public abstract Long zremrangeByScore(byte[] key, double start, double end);

	public abstract Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value);

	public abstract Jedis getShard(byte[] key);

	public abstract Jedis getShard(String key);

	public abstract JedisShardInfo getShardInfo(byte[] key);

	public abstract JedisShardInfo getShardInfo(String key);

	public abstract String getKeyTag(String key);

	/**
	 * 监控redis，获取节点信息
	 * 
	 * @return
	 */
	public abstract Map<String, Map<String, KeyInfo>> keys();

	public abstract Collection<JedisShardInfo> getAllShardInfo();

	public abstract Collection<Jedis> getAllShards();

	public abstract TreeSet<String> keys(String pattern);

}
