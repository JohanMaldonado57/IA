import csv

# Lectra de CSV
def construir_grafo(archivo_csv):
    grafo = {}
    with open(archivo_csv, mode='r') as archivo:
        lector_csv = csv.reader(archivo)
        next(lector_csv) 
        for fila in lector_csv:
            nodo_inicial = int(fila[0])
            if nodo_inicial not in grafo:
                grafo[nodo_inicial] = []
            for i in range(1, len(fila[1:]), 2):
                if fila[i] and fila[i + 1]:
                    valor = int(fila[i])
                    sucesor = int(fila[i + 1])
                    grafo[nodo_inicial].append((sucesor, valor))
    print("Grafo construido:", grafo) 
    return grafo


def escalada_maxima_pendiente(grafo, inicio, fin, sentido_horario=True):
    ruta = [inicio]
    nodo_actual = inicio
    visitados = set()  

    while nodo_actual != fin:
        if nodo_actual not in grafo or not grafo[nodo_actual]:
            print("No hay Ruta. Sin sucesores.") 
            return None
        vecinos = grafo[nodo_actual]
        vecinos.sort(key=lambda x: x[1])
        nodo_siguiente = None

        for vecino in vecinos:
            if vecino[0] not in visitados:
                nodo_siguiente = vecino[0]
                break

        if nodo_siguiente is None:  
            print("No existe Ruta") 
            return None

        ruta.append(nodo_siguiente)
        visitados.add(nodo_actual)  
        nodo_actual = nodo_siguiente
        print("Nodo actual:", nodo_actual)  

    return ruta

#dirección
direccion = input("¿Sentido Horario? (sí/no): ").strip().lower()
sentido_horario = direccion == 'sí'

#CSV
archivo_csv = "C:/Users/johan/Downloads/BusquedasGrafos-main/BusquedasGrafos-main/valores.csv"

#grafo
grafo = construir_grafo(archivo_csv)

#busqueda
ruta = escalada_maxima_pendiente(grafo, inicio=8, fin=14, sentido_horario=sentido_horario)


if ruta:
    print(' -> '.join(map(str, ruta)))
else:
    print("No se encontró una ruta válida.")
