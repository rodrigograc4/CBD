package ex5;

import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Random;

public class SistAtendimentoB {
    private final Jedis jedis;
    private final int limit;
    private final int timeslot;
    private final static Random random = new Random();

    public SistAtendimentoB(Jedis jedis, int limit, int timeslot) {
        this.jedis = jedis;
        this.limit = limit;
        this.timeslot = timeslot;
    }

    public void processRequest(String username, String product, int quantity) {
        String userKey = "user:" + username;

        if (isNewTimeslot(userKey)) {
            resetTimeslot(userKey);
            acceptRequest(username, product, quantity, "Novo pedido");
        } else if (canAcceptRequest(userKey, quantity)) {
            acceptRequest(username, product, quantity, "Pedido existente");
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

    private boolean canAcceptRequest(String userKey, int quantity) {
        long requestCount = jedis.llen(userKey) - 1; // Desconta o item de placeholder
        long totalQuantity = getTotalQuantity(userKey);

        return requestCount < limit && totalQuantity + quantity <= limit;
    }

    private void acceptRequest(String username, String product, int quantity, String status) {
        System.out.println("Pedido atendido: " + username + " - " + product + " - " + quantity + " unidades - " + status);
        jedis.rpush("user:" + username, product + " - " + quantity + " unidades");
    }

    private static void rejectRequest(String username) {
        System.out.println("Limite máximo de pedidos atingido para " + username + ".");
    }

    private long getTotalQuantity(String userKey) {
        List<String> requestList = jedis.lrange(userKey, 1, -1); // Ignora o item de placeholder
        long totalQuantity = 0;

        for (String request : requestList) {
            String[] parts = request.split(" - ");
            if (parts.length == 2) {
                try {
                    int quantity = Integer.parseInt(parts[1].split(" ")[0]);
                    totalQuantity += quantity;
                } catch (NumberFormatException e) {
                    // Ignore invalid format
                }
            }
        }

        return totalQuantity;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        int limit = 30;
        int timeslot = 60;

        System.out.println("Sistema de atendimento B:");
        System.out.println("O sistema atende, para cada utilizador, um máximo de " + limit + " unidades de produtos a cada " + timeslot + " segundos.");
        
        SistAtendimentoB system = new SistAtendimentoB(jedis, limit, timeslot);

        long startTime = System.currentTimeMillis();

        int quantity = 0;
        boolean excessPedido = false;

        while (quantity < 30 || !excessPedido) {
            String username = "Rodrigo";
            String product = "Bolachas";
            quantity = random.nextInt(30) + 1; // Quantidade aleatória entre 1 e 30 unidades

            if (quantity <= (30 - system.getTotalQuantity("user:" + username))) {
                system.processRequest(username, product, quantity);
            } else if (!excessPedido) {
                excessPedido = true;
                rejectRequest(username);
            }
        }

        quantity = 0;
        excessPedido = false;

        while (quantity < 30 || !excessPedido) {
            String username = "Ana";
            String product = "Croissants";
            quantity = random.nextInt(30) + 1; // Quantidade aleatória entre 1 e 30 unidades

            if (quantity <= (30 - system.getTotalQuantity("user:" + username))) {
                system.processRequest(username, product, quantity);
            } else if (!excessPedido) {
                excessPedido = true;
                rejectRequest(username);
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("\nTempo total de execução: " + executionTime + " ms");

        jedis.close();
    }
}
