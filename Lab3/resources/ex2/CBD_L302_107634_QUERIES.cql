// b)

SELECT JSON * FROM users;
SELECT JSON * FROM videos;
SELECT JSON * FROM comments;
SELECT JSON * FROM followers;
SELECT JSON * FROM events;
SELECT JSON * FROM videos_by_author;
SELECT JSON * FROM comments_by_user;
SELECT JSON * FROM comments_by_video;
SELECT JSON * FROM ratings;






// 10. Permitir a pesquisa do rating medio de um video e quantas vezes foi votado;

SELECT id_video, AVG(rate) AS average_rate, COUNT(rate) AS number_of_rates FROM ratings WHERE id_video=1;

 id_video | average_rate | number_of_rates
----------+--------------+-----------------
        1 |            2 |               3








// 1. Os ultimos 3 comentarios introduzidos para um video;

SELECT * FROM comments_by_video WHERE id_video=1 LIMIT 3;   

 id_video | ts                              | comment                                          | username
----------+---------------------------------+--------------------------------------------------+---------------
        1 | 2023-11-25 17:03:42.520000+0000 | Suas dicas de fotografia são valiosas, obrigado! | carlospereira
        1 | 2023-11-25 17:03:42.519000+0000 | Essa receita parece deliciosa, vou experimentar! |    mariagomes
        1 | 2023-11-25 17:03:42.517000+0000 |                    Que incrível! Adoro o espaço! |     joaosilva


// 2. Lista das tags de determinado video;

SELECT id, tags FROM videos WHERE id=5;

 id | tags
----+-------------------
  5 | {'gaming', 'pro'}


// 3. Todos os videos com a tag Aveiro;

O modelo atual não suporta consultas com "allow filtering", impedindo a execução da query.
Uma alternativa seria criar uma tabela sem indexar a coluna "tags", mas isso geraria uma tabela com dados repetidos, o que não é recomendado.


// 4. Os ultimos 5 eventos de determinado video realizados por um utilizador;

SELECT * FROM events WHERE id_video = 7 AND username = 'joaosilva' ORDER BY ts DESC LIMIT 5;

 id_video | username  | ts                              | instant                         | type
----------+-----------+---------------------------------+---------------------------------+-------
        1 | joaosilva | 2023-11-25 17:03:42.451000+0000 | 2023-11-25 17:03:42.451000+0000 |  stop
        1 | joaosilva | 2023-11-25 17:03:42.449000+0000 | 2023-11-25 17:03:42.449000+0000 |  play
        1 | joaosilva | 2023-11-25 17:03:42.446000+0000 | 2023-11-25 17:03:42.446000+0000 | pause


// 5. Videos partilhados por determinado utilizador (joaosilva, por exemplo) num determinado periodo de tempo (Agosto de 2017, por exemplo);

SELECT * FROM videos_by_author WHERE author = 'joaosilva' AND ts >= toTimestamp('2017-08-01') AND ts <= toTimestamp(now());

 author    | ts                              | description                                    | id | name                | tags
-----------+---------------------------------+------------------------------------------------+----+---------------------+------------------------
 joaosilva | 2023-11-25 17:03:42.482000+0000 | Viagem intergaláctica em busca de novos mundos |  1 | Explorando Galáxias | {'aventura', 'espaço'}


// 6. Os ultimos 10 videos, ordenado inversamente pela data da partilhada;

A query não é viável devido à impossibilidade de filtrar por colunas não indexadas, para além de que o Cassandra não suporta pesquisa global.


// 7. Todos os seguidores (followers) de determinado video;

SELECT * FROM followers WHERE id_video = 9;

 id_video | users
----------+--------------------------------------------------------------------------------------------------------------------------------------------------------------
        9 | {'anacarvalho', 'andreasantos', 'carlospereira', 'davidsousa', 'inesoliveira', 'joaosilva', 'mariagomes', 'pedroalmeida', 'ricardosilva', 'susanarodrigues'}


// 8. Todos os comentarios (dos videos) que determinado utilizador esta a seguir (following);

Não é possível devido ao Cassandra não suportar joins.



// 9. Os 5 videos com maior rating;

O Cassandra não suporta pesquisa global.



// 10. Uma query que retorne todos os videos e que mostre claramente a forma pela qual estao ordenados;

SELECT * FROM videos;

 id | ts                              | author          | description                           | name                   | tags
----+---------------------------------+-----------------+---------------------------------------+------------------------+------------------------------
  5 | 2023-11-25 17:03:42.393000+0000 |    pedroalmeida |                Dominei o último jogo! |     Gamer Profissional |            {'gaming', 'pro'}
 10 | 2023-11-25 17:03:42.402000+0000 |      davidsousa |     Investigação de casos misteriosos |  Desvendando Mistérios | {'investigação', 'mistério'}
  1 | 2023-11-25 17:03:42.384000+0000 |       joaosilva |         Explorando planetas distantes |    Aventuras no Espaço |       {'aventura', 'espaço'}
  8 | 2023-11-25 17:03:42.398000+0000 |    ricardosilva |       Experimentando comidas exóticas |   Desafio Gastronômico |     {'culinária', 'desafio'}
  2 | 2023-11-25 17:03:42.387000+0000 |      mariagomes |           Cozinhando pratos incríveis |    Receitas Deliciosas |    {'culinária', 'receitas'}
  4 | 2023-11-25 17:03:42.391000+0000 |     anacarvalho |           Descobrindo belas paisagens |  Passeio pela Natureza |       {'natureza', 'viagem'}
  7 | 2023-11-25 17:03:42.396000+0000 |    inesoliveira |         Criações artísticas incríveis |          Arte em Papel |            {'arte', 'papel'}
  6 | 2023-11-25 17:03:42.394000+0000 |    andreasantos |            Treino para manter a forma |        Fitness em Casa |         {'fitness', 'saúde'}
  9 | 2023-11-25 17:03:42.400000+0000 | susanarodrigues | Minhas experiências ao redor do mundo |       Diário de Viagem |         {'diário', 'viagem'}
  3 | 2023-11-25 17:03:42.389000+0000 |   carlospereira |            Dicas para fotos incríveis | Segredos da Fotografia |      {'dicas', 'fotografia'}



// 11. Lista com as Tags existentes e o numero de videos catalogados com cada uma delas;

Não é possível, porque seria necessário criar outra tabela com as tags como chaves primárias.


// 12. Uma consulta para obter a contagem total de vídeos para cada autor:

SELECT author, COUNT(*) AS total_videos FROM videos_by_author GROUP BY author;

 author          | total_videos
-----------------+--------------
    ricardosilva |            1
 susanarodrigues |            1
     anacarvalho |            1
      davidsousa |            1
    pedroalmeida |            1
    inesoliveira |            1
       joaosilva |            1
      mariagomes |            1
    andreasantos |            1
   carlospereira |            1


// 13. Obter todas as ratings de um video (Video de id 1, por exemplo):

SELECT * FROM ratings WHERE id_video =1 ;

 id_video | rate
----------+------
        1 |    1
        1 |    2
        1 |    5
