import csv
from collections import deque

class Grafo():

    def __init__(self):
        self.list_ady = {}

    def leer_lista(self, ruta_archivo):
        with open(ruta_archivo, 'r') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                if len(row) > 0:
                    nodo = row[0]
                    self.add_node(nodo)
                    for vecinos in row[1:]:
                        if vecinos.strip():
                            self.add_arista(nodo, vecinos)

    def add_node(self, node):
        self.list_ady[node] = []

    def add_arista(self, node1, node2):
        self.list_ady[node1].append(node2)

    def print_list_ady(self):
        for node, vecinos in self.list_ady.items():
            print(f"{node} -> {vecinos}")

    # Búsqueda por profundidad (DFS)
    def dfs(self, inicio, final, sentido="horario", visitados=None, ruta=None):
        if visitados is None:
            visitados = set()
        if ruta is None:
            ruta = []

        visitados.add(inicio)
        ruta.append(inicio)

        print(f"Ruta actual: {' -> '.join(ruta)}")

        if inicio == final:
            print(f"\nSe ha llegado al nodo {final}. Ruta final: {' -> '.join(ruta)}")
            return True

        sucesores = self.list_ady[inicio]
        if sentido == "antihorario":
            sucesores = list(reversed(sucesores))

        for vecino in sucesores:
            if vecino not in visitados:
                if self.dfs(vecino, final, sentido, visitados, ruta):
                    return True

        ruta.pop()
        return False

    # Búsqueda ancho (BFS)
    def bfs(self, inicio, final, sentido="horario"):
        visitados = set()
        cola = deque([[inicio]])

        while cola:
            ruta = cola.popleft()
            nodo_actual = ruta[-1]

            if nodo_actual not in visitados:
                visitados.add(nodo_actual)
                print(f"Ruta actual: {' -> '.join(ruta)}")

                if nodo_actual == final:
                    print(f"\nSe ha llegado al nodo {final}. Ruta final: {' -> '.join(ruta)}")
                    return True

                sucesores = self.list_ady[nodo_actual]
                if sentido == "antihorario":
                    sucesores = list(reversed(sucesores))

                for vecino in sucesores:
                    if vecino not in visitados:
                        nueva_ruta = list(ruta)
                        nueva_ruta.append(vecino)
                        cola.append(nueva_ruta)

        print(f"No se pudo encontrar una ruta desde {inicio} hasta {final}.")
        return False


def ejecutar_busqueda():
    print("Programa de búsqueda (DFS o BFS) con opción de sentido\n")
    g = Grafo()
    g.leer_lista("C:/Users/johan/Downloads/BusquedasGrafos-main/BusquedasGrafos-main/lista_adyacencia.csv")
    g.print_list_ady()

    nodo_inicio = '8'  # Nodo inicial
    nodo_final = '14'  # Nodo final

    # tipo de busqueda 
    tipo_busqueda = input("¿Qué tipo de búsqueda deseas realizar? ('dfs' para profundidad, 'bfs' para anchura): ").strip().lower()

    # sentido
    sentido = input("Ingrese el sentido de la búsqueda ('horario' o 'antihorario'): ").strip().lower()

    if sentido not in ['horario', 'antihorario']:
        print("Sentido inválido, se usará el sentido horario por defecto.")
        sentido = 'horario'

    
    print(f"\nIniciando la búsqueda desde el nodo {nodo_inicio} hasta el nodo {nodo_final} en sentido {sentido}:\n")
    if tipo_busqueda == 'dfs':
        g.dfs(nodo_inicio, nodo_final, sentido)
    elif tipo_busqueda == 'bfs':
        g.bfs(nodo_inicio, nodo_final, sentido)
    else:
        print("Tipo de búsqueda inválido. Elige 'dfs' o 'bfs'.")


ejecutar_busqueda()