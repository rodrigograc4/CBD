from neo4j import GraphDatabase

class MarioKart:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()

    def insert_data(self):
        self.insert_nodes()
        self.insert_relationships()

    def insert_nodes(self):
        with self.driver.session() as session:
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///gliders.csv' AS Row MERGE (glider:Glider {name: Row.Glider})")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///tires.csv' AS Row MERGE (tire:Tire {name: Row.Tire})")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///bodies_karts.csv' AS Row MERGE (body:Body {name: Row.Body})")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///drivers.csv' AS Row MERGE (driver:Driver {name: Row.Driver})")
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///races.csv' AS Row "
                "MERGE (cup:Cup {name: Row.name}) "
                "MERGE (race:Race {name: Row.name, track: Row.track, date: Row.date}) "
                "MERGE (race)-[:PART_OF]->(cup)"
            )

    def insert_relationships(self):
        with self.driver.session() as session:
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///races.csv' AS Row "
                "UNWIND range(1, 12) AS position "
                "MATCH (driver:Driver {name: Row.`{}`}),(race:Race {name: Row.name}) "
                "MERGE (driver)-[:RACED_IN {position: position}]->(race)"
            )
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///drivers_gliders.csv' AS Row "
                "MATCH (driver:Driver {name: Row.driver}),(glider:Glider {name: Row.glider}) "
                "MERGE (driver)-[:USES_GLIDER]->(glider)"
            )
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///drivers_tires.csv' AS Row "
                "MATCH (driver:Driver {name: Row.driver}),(tire:Tire {name: Row.tire}) "
                "MERGE (driver)-[:USES_TIRE]->(tire)"
            )
            session.run(
                "LOAD CSV WITH HEADERS FROM 'file:///drivers_bodies_karts.csv' AS Row "
                "MATCH (driver:Driver {name: Row.driver}),(body:Body {name: Row.body}) "
                "MERGE (driver)-[:USES_BODY]->(body)"
            )


    def run_query(self, query):
        with self.driver.session() as session:
            return list(session.run(query))

    def run_queries(self, query_title, query_cypher):
        counter = 1

        with open("CBD_L44c_output.txt", "w") as writer:
            for current_query in query_cypher:
                writer.write("Query n" + str(counter) + ": " + query_title[counter - 1] + "\n")
                writer.write(current_query + "\n\n")

                counter += 1

                result = self.run_query(current_query)

                i = result[0]
                s = ""
                for j in range(len(i.items())):
                    string = "| " + str(i.items()[j][0])
                    s += "{: <50}".format(string)
                writer.write("   " + str(s) + "\n")

                for i in result:
                    s = ""
                    for j in range(len(i.items())):
                        string = "| " + str(i.items()[j][1])
                        s += "{: <50}".format(string)
                    writer.write("   " + str(s) + "\n")
                writer.write("\n\n\n")



query_cypher = [
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race)-[:PART_OF]->(cup:Cup) RETURN DISTINCT driver.name, cup.name LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race)-[:PART_OF]->(cup:Cup) WHERE driver.nationality = 'Mushroom Kingdom' RETURN DISTINCT driver.name, cup.name LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race) WHERE driver.position = 1 RETURN DISTINCT driver.name, race.name, race.track, race.date LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race) WHERE driver.position <= 3 RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race) WHERE driver.position > 10 RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race) WHERE driver.position > 5 AND driver.position <= 10 RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race)-[:PART_OF]->(cup:Cup) WHERE cup.name = 'Star Cup' RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race)-[:PART_OF]->(cup:Cup) WHERE cup.name = 'Special Cup' RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race)-[:PART_OF]->(cup:Cup) WHERE cup.name = 'Lightning Cup' RETURN DISTINCT driver.name, race.name, race.track, race.date, driver.position LIMIT 5",
    "MATCH (driver:Driver)-[:RACED_IN]->(race:Race) WHERE driver.position IS NULL RETURN DISTINCT driver.name, race.name, race.track, race.date LIMIT 5"
]

query_titles = [
    "Listar os pilotos e as copas em que participaram (limitado a 5)",
    "Listar os pilotos que são da 'Mushroom Kingdom' e as copas em que participaram (limitado a 5)",
    "Listar os pilotos que terminaram em primeiro lugar em uma corrida, juntamente com o nome da corrida, a pista e a data (limitado a 5)",
    "Listar os pilotos que terminaram no pódio (1º a 3º lugar) em uma corrida, juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que terminaram fora dos 10 primeiros em uma corrida, juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que terminaram entre o 6º e o 10º lugar em uma corrida, juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que participaram na 'Star Cup', juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que participaram na 'Special Cup', juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que participaram na 'Lightning Cup', juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)",
    "Listar os pilotos que não têm posição atribuída em uma corrida, juntamente com o nome da corrida, a pista, a data e a posição (limitado a 5)"
]

if __name__ == "__main__":
    mario_kart = MarioKart("bolt://localhost:7687", "neo4j", "rodrigo14")
    mario_kart.insert_data()
    mario_kart.run_queries(query_titles, query_cypher)
    mario_kart.close()
