USE share_videos;

INSERT INTO share_videos.users (username, name, email, ts) VALUES ('joaosilva', 'João Silva', 'joao.silva@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('mariagomes', 'Maria Gomes', 'maria.gomes@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('carlospereira', 'Carlos Pereira', 'carlos.pereira@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('anacarvalho', 'Ana Carvalho', 'ana.carvalho@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('pedroalmeida', 'Pedro Almeida', 'pedro.almeida@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('andreasantos', 'André Santos', 'andre.santos@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('inesoliveira', 'Inês Oliveira', 'ines.oliveira@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('ricardosilva', 'Ricardo Silva', 'ricardo.silva@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('susanarodrigues', 'Susana Rodrigues', 'susana.rodrigues@example.com', toTimestamp(now()));
INSERT INTO share_videos.users (username, name, email, ts) VALUES ('davidsousa', 'David Sousa', 'david.sousa@example.com', toTimestamp(now()));

INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (1, 'joaosilva', 'Aventuras no Espaço', 'Explorando planetas distantes', {'aventura', 'espaço'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (2, 'mariagomes', 'Receitas Deliciosas', 'Cozinhando pratos incríveis', {'culinária', 'receitas'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (3, 'carlospereira', 'Segredos da Fotografia', 'Dicas para fotos incríveis', {'fotografia', 'dicas'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (4, 'anacarvalho', 'Passeio pela Natureza', 'Descobrindo belas paisagens', {'natureza', 'viagem'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (5, 'pedroalmeida', 'Gamer Profissional', 'Dominei o último jogo!', {'gaming', 'pro'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (6, 'andreasantos', 'Fitness em Casa', 'Treino para manter a forma', {'fitness', 'saúde'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (7, 'inesoliveira', 'Arte em Papel', 'Criações artísticas incríveis', {'arte', 'papel'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (8, 'ricardosilva', 'Desafio Gastronômico', 'Experimentando comidas exóticas', {'culinária', 'desafio'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (9, 'susanarodrigues', 'Diário de Viagem', 'Minhas experiências ao redor do mundo', {'viagem', 'diário'}, toTimestamp(now()));
INSERT INTO share_videos.videos (id, author, name, description, tags, ts) VALUES (10, 'davidsousa', 'Desvendando Mistérios', 'Investigação de casos misteriosos', {'mistério', 'investigação'}, toTimestamp(now()));

INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (1, 1, 'carlospereira', 'Que incrível! Adoro o espaço!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (2, 2, 'anacarvalho', 'Essa receita parece deliciosa, vou experimentar!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (3, 3, 'joaosilva', 'Suas dicas de fotografia são valiosas, obrigado!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (4, 4, 'mariagomes', 'Que paisagem maravilhosa! Onde fica?', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (5, 5, 'pedroalmeida', 'Parabéns pelo domínio no jogo!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (6, 6, 'andreasantos', 'Ótimo treino! Vou tentar em casa também.', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (7, 7, 'inesoliveira', 'Suas criações são inspiradoras!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (8, 8, 'ricardosilva', 'Quero ver mais desafios como esse!', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (9, 9, 'susanarodrigues', 'Incríveis aventuras! Continue compartilhando.', toTimestamp(now()));
INSERT INTO share_videos.comments (id, id_video, author, comment, ts) VALUES (10, 10, 'davidsousa', 'Estou ansioso para mais mistérios!', toTimestamp(now()));

INSERT INTO share_videos.followers (id_video, users) VALUES (1, {'ricardosilva', 'susanarodrigues', 'joaosilva', 'carlospereira', 'anacarvalho', 'pedroalmeida', 'andreasantos', 'mariagomes', 'inesoliveira', 'davidsousa'});
INSERT INTO share_videos.followers (id_video, users) VALUES (2, {'joaosilva', 'carlospereira', 'pedroalmeida', 'mariagomes', 'ricardosilva', 'susanarodrigues', 'inesoliveira', 'davidsousa', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (3, {'susanarodrigues', 'pedroalmeida', 'carlospereira', 'ricardosilva', 'joaosilva', 'mariagomes', 'inesoliveira', 'davidsousa', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (4, {'mariagomes', 'pedroalmeida', 'joaosilva', 'carlospereira', 'susanarodrigues', 'ricardosilva', 'davidsousa', 'inesoliveira', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (5, {'pedroalmeida', 'joaosilva', 'mariagomes', 'ricardosilva', 'carlospereira', 'susanarodrigues', 'inesoliveira', 'davidsousa', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (6, {'pedroalmeida', 'ricardosilva', 'joaosilva', 'carlospereira', 'mariagomes', 'susanarodrigues', 'inesoliveira', 'davidsousa', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (7, {'mariagomes', 'pedroalmeida', 'ricardosilva', 'joaosilva', 'carlospereira', 'susanarodrigues', 'davidsousa', 'inesoliveira', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (8, {'carlospereira', 'joaosilva', 'pedroalmeida', 'ricardosilva', 'mariagomes', 'susanarodrigues', 'davidsousa', 'inesoliveira', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (9, {'mariagomes', 'joaosilva', 'carlospereira', 'ricardosilva', 'susanarodrigues', 'pedroalmeida', 'davidsousa', 'inesoliveira', 'andreasantos', 'anacarvalho'});
INSERT INTO share_videos.followers (id_video, users) VALUES (10, {'joaosilva', 'pedroalmeida', 'ricardosilva', 'mariagomes', 'carlospereira', 'susanarodrigues', 'davidsousa', 'inesoliveira', 'andreasantos', 'anacarvalho'});

INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (1, 'joaosilva', 'pause', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (1, 'joaosilva', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (1, 'joaosilva', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (2, 'mariagomes', 'pause', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (2, 'mariagomes', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (3, 'carlospereira', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (3, 'carlospereira', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (4, 'anacarvalho', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (4, 'anacarvalho', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (5, 'pedroalmeida', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (5, 'pedroalmeida', 'pause', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (6, 'andreasantos', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (6, 'andreasantos', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (7, 'inesoliveira', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (7, 'inesoliveira', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (8, 'ricardosilva', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (8, 'ricardosilva', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (9, 'susanarodrigues', 'stop', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (9, 'susanarodrigues', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (10, 'davidsousa', 'play', toTimestamp(now()), toTimestamp(now()));
INSERT INTO share_videos.events (id_video, username, type, ts, instant) VALUES (10, 'davidsousa', 'pause', toTimestamp(now()), toTimestamp(now()));

INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (1, 'joaosilva', 'Explorando Galáxias', 'Viagem intergaláctica em busca de novos mundos', {'aventura', 'espaço'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (2, 'mariagomes', 'Cozinha Gourmet', 'Pratos sofisticados para surpreender seus convidados', {'culinária', 'gourmet'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (3, 'carlospereira', 'Fotografia Noturna', 'Capturando a magia da noite', {'fotografia', 'noturna'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (4, 'anacarvalho', 'Roteiro Natural', 'Descobrindo lugares incríveis em meio à natureza', {'natureza', 'viagem'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (5, 'pedroalmeida', 'Desafios Gamer', 'Conquistas e desafios no mundo dos games', {'gaming', 'desafio'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (6, 'andreasantos', 'Treino Explosivo', 'Rotina de exercícios intensos para resultados rápidos', {'fitness', 'treino'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (7, 'inesoliveira', 'Origami Criativo', 'Arte em papel que vai além das dobras convencionais', {'arte', 'origami'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (8, 'ricardosilva', 'Tour Gastronômico', 'Viajando pelos sabores do mundo', {'culinária', 'tour'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (9, 'susanarodrigues', 'Aventuras pelo Mundo', 'Experiências únicas em diferentes culturas', {'viagem', 'aventura'}, toTimestamp(now()));
INSERT INTO share_videos.videos_by_author (id, author, name, description, tags, ts) VALUES (10, 'davidsousa', 'Enigmas Desvendados', 'Soluções para mistérios intrigantes', {'mistério', 'enigmas'}, toTimestamp(now()));

INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('joaosilva', 1, 'Que incrível! Adoro o espaço!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('mariagomes', 2, 'Essa receita parece deliciosa, vou experimentar!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('carlospereira', 3, 'Suas dicas de fotografia são valiosas, obrigado!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('anacarvalho', 4, 'Que paisagem maravilhosa! Onde fica?', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('pedroalmeida', 5, 'Parabéns pelo domínio no jogo!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('andreasantos', 6, 'Ótimo treino! Vou tentar em casa também.', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('inesoliveira', 7, 'Suas criações são inspiradoras!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('ricardosilva', 8, 'Quero ver mais desafios como esse!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('susanarodrigues', 9, 'Incríveis aventuras! Continue compartilhando.', toTimestamp(now()));
INSERT INTO share_videos.comments_by_user (username, id_video, comment, ts) VALUES ('davidsousa', 10, 'Estou ansioso para mais mistérios!', toTimestamp(now()));

INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('joaosilva', 1, 'Que incrível! Adoro o espaço!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('mariagomes', 1, 'Essa receita parece deliciosa, vou experimentar!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('carlospereira', 1, 'Suas dicas de fotografia são valiosas, obrigado!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('anacarvalho', 4, 'Que paisagem maravilhosa! Onde fica?', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('pedroalmeida', 5, 'Parabéns pelo domínio no jogo!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('andreasantos', 6, 'Ótimo treino! Vou tentar em casa também.', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('inesoliveira', 7, 'Suas criações são inspiradoras!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('ricardosilva', 8, 'Quero ver mais desafios como esse!', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('susanarodrigues', 9, 'Incríveis aventuras! Continue compartilhando.', toTimestamp(now()));
INSERT INTO share_videos.comments_by_video (username, id_video, comment, ts) VALUES ('davidsousa', 10, 'Estou ansioso para mais mistérios!', toTimestamp(now()));

INSERT INTO share_videos.ratings (id_video, rate) VALUES (1, 1);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (1, 2);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (1, 5);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (1, 5);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (2, 4);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (2, 5);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (3, 3);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (3, 4);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (4, 2);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (4, 3);
INSERT INTO share_videos.ratings (id_video, rate) VALUES (5, 5);


