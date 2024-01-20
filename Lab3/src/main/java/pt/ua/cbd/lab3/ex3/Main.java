package pt.ua.cbd.lab3.ex3;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Row;

public class Main {
    static Session session;

    public static void main(String[] args) {
        try {
            Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
            session = cluster.connect("share_videos");
            
            System.out.println("SUCCESSFULLY CONNECTED TO CASSANDRA");

            ex3_A();
            ex3_B();

        } catch (Exception e) {
            System.err.println("COULD NOT CONNECT TO CASSANDRA \n" + e.getMessage());
            System.exit(1);
        }
    }

    private static void ex3_A() {

        System.out.println("========= EXERCICIO 3 - ALINEA A =========\n");
        
        try {
            System.out.println("INSERTS USERS");
            session.execute("INSERT INTO users (username, name, email, ts) VALUES ('joaosilva', 'João Silva', 'joao.silva@example.com', toTimestamp(now()));");
            session.execute("INSERT INTO users (username, name, email, ts) VALUES ('mariagomes', 'Maria Gomes', 'maria.gomes@example.com', toTimestamp(now()));");
            session.execute("INSERT INTO users (username, name, email, ts) VALUES ('carlospereira', 'Carlos Pereira', 'carlos.pereira@example.com', toTimestamp(now()));");

            System.out.println("INSERTS VIDEOS");
            session.execute("INSERT INTO videos (id, author, name, description, tags, ts) VALUES (1, 'joaosilva', 'Aventuras no Espaço', 'Explorando planetas distantes', {'aventura', 'espaço'}, toTimestamp(now()));");
            session.execute("INSERT INTO videos (id, author, name, description, tags, ts) VALUES (2, 'mariagomes', 'Receitas Deliciosas', 'Cozinhando pratos incríveis', {'culinária', 'receitas'}, toTimestamp(now()));");
            session.execute("INSERT INTO videos (id, author, name, description, tags, ts) VALUES (3, 'carlospereira', 'Segredos da Fotografia', 'Dicas para fotos incríveis', {'fotografia', 'dicas'}, toTimestamp(now()));");

            System.out.println("INSERTS COMMENTS");
            session.execute("INSERT INTO comments_by_user (username, id_video, comment, ts) VALUES ('carlospereira', 1, 'Que incrível! Adoro o espaço!', toTimestamp(now()));");
            session.execute("INSERT INTO comments_by_user (username, id_video, comment, ts) VALUES ('anacarvalho', 2,  'Essa receita parece deliciosa, vou experimentar!', toTimestamp(now()));");

            System.out.println("INSERTS COMPLETED");

        } catch (Exception e) {
            System.err.println("INSERTS FAILED \n" + e.getMessage());
            System.exit(1);
        }

        
        try {
            System.out.println("EDIT USERS");
            session.execute("UPDATE users SET email='joaobalao@example.com' WHERE username='joaosilva';");
            session.execute("UPDATE users SET name='Carlitos Pera' WHERE username='carlospereira';");
    
            System.out.println("EDIT VIDEOS");
            session.execute("UPDATE videos SET description='Deem o Likezão' WHERE id=2 AND ts = toTimestamp(now());;");
            session.execute("UPDATE videos SET name='Curso de Fotografia' WHERE id=3 AND ts = toTimestamp(now());;");


            System.out.println("EDITS COMPLETED");

        } catch (Exception e) {
            System.err.println("EDITS FAILED \n" + e.getMessage());
            System.exit(1);
        }

        
        try {
            System.out.println("SEARCH USERS");
            System.out.println("===============");
            for (Row r : session.execute("SELECT * FROM users;")) {
                System.out.println(r.toString());
            System.out.println("===============");

            }
            System.out.println();
            System.out.println("SEARCH COMMENTS FROM USER NAMED 'carlospereira'");
            System.out.println("===============");
            for (Row r : session.execute("SELECT * FROM comments_by_user WHERE username='carlospereira';")) {
                System.out.println(r.toString());
                System.out.println("===============");
            }

            System.out.println("SEARCHS COMPLETED");

        } catch (Exception e) {
            System.err.println("SEARCH FAILED \n" + e.getMessage());
            System.exit(1);
        }
    }

    private static void ex3_B() {

        System.out.println("\n========= EXERCICIO 3 - ALINEA B =========\n");

        try {
            System.out.println();
            System.out.println("1 - Os últimos 3 comentários introduzidos para um vídeo");
            System.out.println("===============");
            for (Row r : session.execute("SELECT * FROM comments_by_video WHERE id_video=1 LIMIT 3;")) {
                System.out.println(r.toString());
            System.out.println("===============");
            }

            System.out.println();
            System.out.println("4 - Lista das tags de determinado video;");
            System.out.println("===============");
            for (Row r : session.execute("SELECT id, tags FROM videos WHERE id=5;")) {
                System.out.println(r.toString());
            System.out.println("===============");
            }

            System.out.println();

            System.out.println("5 - Videos partilhados por determinado utilizador (joaosilva, por exemplo) num determinado periodo de tempo (Agosto de 2017, por exemplo)");
            for (Row r : session.execute("SELECT * FROM videos_by_author WHERE author = 'joaosilva' AND ts >= toTimestamp('2017-08-01') AND ts <= toTimestamp(now());")) {
                System.out.println(r.toString());
            }

            System.out.println();

            System.out.println("13 - Obter todas as ratings de um video (Video de id 1, por exemplo)");
            for (Row r : session.execute("SELECT * FROM ratings WHERE id_video =1 ;")) {
                System.out.println(r.toString());
            }

            System.out.println();
            System.out.println("QUERIES COMPLETED");

        } catch (Exception e) {
            System.err.println("QUERIES FAILED \n" + e.getMessage());
            System.exit(1);
        }
    }
}