package ex5;

import redis.clients.jedis.Jedis;

public class RedisPerformanceTest {
    public static void main(String[] args) {
        String redisURI = "redis://localhost:6379";
        Jedis jedis = new Jedis(redisURI);
        int numberOfOperations = 10000; // Número de operações para testar

        // Medir desempenho no Redis
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfOperations; i++) {
            jedis.set("key" + i, "value" + i);
            jedis.get("key" + i);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Desempenho do Redis: " + executionTime + " ms para " + numberOfOperations + " operações.");

        jedis.close();
    }
}
