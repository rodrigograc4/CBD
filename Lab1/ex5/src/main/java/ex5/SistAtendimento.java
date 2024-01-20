package ex5;

import redis.clients.jedis.Jedis;

public class SistAtendimento {
    private final Jedis jedis;
    private final int limit;
    private final int timeslot;

    public SistAtendimento(Jedis jedis, int limit, int timeslot) {
        this.jedis = jedis;
        this.limit = limit;
        this.timeslot = timeslot;
    }

    public void processRequest(String username, String product) {
        String userKey = "user:" + username;

        if (isNewTimeslot(userKey)) {
            resetTimeslot(userKey);
            acceptRequest(username, product, "Novo pedido");
        } else if (canAcceptRequest(userKey)) {
            acceptRequest(username, product, "Pedido existente");
        } else {
            rejectRequest(username);
        }
    }

    private boolean isNewTimeslot(String userKey) {
        long ttl = jedis.ttl(userKey);
        return ttl < 0;
    }

    private void resetTimeslot(String userKey) {
        jedis.del(userKey);
        jedis.rpush(userKey, "placeholder"); // Adiciona um item apenas para criar a chave
        jedis.expire(userKey, timeslot);
    }

    private boolean canAcceptRequest(String userKey) {
        long requestCount = jedis.llen(userKey) - 1; // Desconta o item de placeholder
        return requestCount < limit;
    }

    private void acceptRequest(String username, String product, String status) {
        System.out.println("Pedido atendido: " + username + " - " + product + " - " + status);
        jedis.rpush("user:" + username, product);
    }

    private void rejectRequest(String username) {
        System.out.println("Limite máximo de pedidos atingido para " + username + ".");
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        int limit = 10;
        int timeslot = 60;

        System.out.println("Sistema de atendimento A:");
        System.out.println("O sistema atende, para cada utilizador, um máximo de " + limit + " de produtos diferentes a cada " + timeslot + " segundos.");
        
        SistAtendimento system = new SistAtendimento(jedis, limit, timeslot);

        long startTime = System.currentTimeMillis();

        int pedido = 0;
        while (pedido < 8) {
            String username = "Rodrigo";
            String product = "Bolachas";

            system.processRequest(username, product);
            pedido++;
        }

        pedido = 0;
        while (pedido < 12) {
            String username = "Ana";
            String product = "Croissants";

            system.processRequest(username, product);
            pedido++;
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("\nTempo total de execução: " + executionTime + " ms");


        jedis.close();
    }
}
