import csv
from collections import deque

class Grafo():
    # Constructor de inicialización del grafo
    def __init__(self):
        self.list_ady = {}
        self.valores = {}

    def leer_lista(self, ruta_archivo):
        with open(ruta_archivo, 'r') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                if len(row) > 0:
                    nodo = row[0]
                    self.add_node(nodo)
                    # Agregamos sus aristas
                    for vecinos in row[1:]:
                        if vecinos.strip():
                            self.add_arista(nodo, vecinos)

    def leer_lista_con_valores(self, ruta_archivo):
        with open(ruta_archivo, 'r') as csvfile:
            reader = csv.reader(csvfile)
            # Saltar la primera fila de encabezado
            next(reader, None)
            for row in reader:
                if len(row) > 0:
                    nodo = row[0]
                    self.add_node(nodo)
                    self.valores[nodo] = {}
                    for i in range(1, len(row) - 1, 2):
                        sucesor = row[i]
                        valor_str = row[i + 1]
                        if sucesor.strip():
                            self.add_node(sucesor)  # Añadir el sucesor como nodo si no está
                            if valor_str.strip():  # Verifica que no esté vacío
                                try:
                                    valor = int(valor_str)
                                    self.add_arista(nodo, sucesor)
                                    self.valores[nodo][sucesor] = valor
                                except ValueError:
                                    print(f"Valor inválido para el nodo {nodo} y sucesor {sucesor}: {valor_str}")




    def add_node(self, node):
        self.list_ady[node] = []

    def add_arista(self, node1, node2):
        self.list_ady[node1].append(node2)

    def print_list_ady(self):
        for node, vecinos in self.list_ady.items():
            print(f"{node} -> {vecinos}")

    # Búsqueda por Escala Máxima Pendiente utilizando el valor más bajo
    def busqueda_con_valores(self, inicio, final, visitados=None, ruta=None):
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
        # Tomar valor bajo
        sucesores.sort(key=lambda nodo: self.valores[inicio].get(nodo, float('inf')))
        for vecino in sucesores:
            if vecino not in visitados:
                if self.busqueda_con_valores(vecino, final, visitados, ruta):
                    return True
        ruta.pop()
        return False


def ejecutar_busqueda():
    print("Programa de búsqueda (DFS, BFS o con valores) con opción de sentido\n")
    g = Grafo()
    g.leer_lista("C:/Users/johan/Downloads/BusquedasGrafos-main/BusquedasGrafos-main/lista_adyacencia.csv")
    g.leer_lista_con_valores(            )
    g.print_list_ady()
    nodo_inicio = '8'  # Nodo inicial
    nodo_final = '14'  # Nodo final 
    # Preguntar tipo de búsqueda 
    tipo_busqueda = input("¿Qué tipo de búsqueda deseas realizar? ('dfs' para profundidad, 'bfs' para anchura, 'valores' para búsqueda con valores): ").strip().lower()
    # Preguntar el sentido de la búsqueda
    sentido = input("Ingrese el sentido de la búsqueda ('horario' o 'antihorario'): ").strip().lower()
    if sentido not in ['horario', 'antihorario']:
        print("Sentido inválido, se usará el sentido horario por defecto.")
        sentido = 'horario'
    
    print(f"\nIniciando la búsqueda desde el nodo {nodo_inicio} hasta el nodo {nodo_final} en sentido {sentido}:\n")
    if tipo_busqueda == 'dfs':
        g.dfs(nodo_inicio, nodo_final, sentido)
    elif tipo_busqueda == 'bfs':
        g.bfs(nodo_inicio, nodo_final, sentido)
    elif tipo_busqueda == 'valores':
        g.busqueda_con_valores(nodo_inicio, nodo_final)
    else:
        print("Tipo de búsqueda inválido. Elige 'dfs', 'bfs' o 'valores'.")
        

ejecutar_busqueda()
