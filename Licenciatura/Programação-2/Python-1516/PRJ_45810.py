#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Universidade de Lisboa
Faculdade de Ciências
Departamento de Informática
Licenciatura em Tecnologias da Informação
2015/2016

Programação II

Projeto de programação:
Crime numa grande cidade
"""

import collections,pylab, csv, numpy
import datetime
from math import radians, cos, sin, asin, sqrt, pi
from matplotlib.dates import YEARLY, rrulewrapper, RRuleLocator

def crimes(nome_ficheiro):
    """
    Esta função vai tracar uma figura com quatro gráficos, estes valores correspondem
    aos crimes por distancia, crimes por tipo, crimes por hora e crimes por data da cidade de Baltimore

    Requires: recebe o nome de um ficheiro csv como parâmetro
    Ensures: Traça uma figura com quatro gráficos
    """
    tabela = ler_tabela_de_csv(nome_ficheiro)
    
    grafico_crimes_por_hora = crimes_por_hora(tabela)
    grafico_crimes_por_tipo = crimes_por_tipo(tabela)
    grafico_crimes_por_data = crimes_por_data(tabela)
    grafico_crimes_por_distancia = crimes_por_distancia(tabela)
    traca_crimes_por_data(grafico_crimes_por_data)
    traca_crimes_por_hora(grafico_crimes_por_hora)
    traca_crimes_por_tipo(grafico_crimes_por_tipo)
    traca_crimes_por_distancia(grafico_crimes_por_distancia, 1)
    
def traca_crimes_por_data(grafico):
    """
    Esta função vai traçar um grafico em que as abcissas são os anos e
    as ordenadas são o numero de crimes.

    Requires: Requer um grafico que será um par de listas.
    Ensures: Devolve um grafico com o numero de crimes por data.
    """
    pylab.figure(1)
    pylab.suptitle(u'Crime na cidade de Baltimore')
    ax = pylab.subplot(221)
    x1 = sorted(list(set(map(lambda x : datetime.datetime.strptime(x, "%Y%m%d"),grafico[0] ))))
    y1 = grafico[1]
    rule = rrulewrapper(YEARLY, bymonthday=1,bymonth=1)
    years = RRuleLocator(rule)
    yearsFmt = pylab.DateFormatter('%Y')
    ax.plot(x1, y1)
    ax.set_title('Numero de crimes por dia')
    ax.set_ylabel('# Crimes')
    ax.set_xlabel('Anos')
    ax.xaxis.set_major_locator(years)
    ax.xaxis.set_major_formatter(yearsFmt)
    ax.autoscale_view()

def traca_crimes_por_hora(grafico):
    """
    Esta função vai traçar um grafico em que as abcissas são as horas e
    as ordenadas são o numero de crimes.

    Requires: Requer um grafico que será um par de listas.
    Ensures: Devolve um grafico com o numero de crimes por hora
    """
    pylab.figure(1)
    pylab.subplot(222)
    x1 = grafico[0]
    y1 = grafico[1]
    pylab.title(u"Número de crimes por hora")
    pylab.xlabel(u'Horas')
    pylab.ylabel(u'# Crimes')
    valoresx = numpy.arange(0, len(x1))
    pylab.bar(valoresx, y1,1)
    pylab.xticks(valoresx + 0.5, x1)
    pylab.xlim(0,24)
    pylab.show()

def traca_crimes_por_tipo(grafico):
    """
    Esta função vai traçar um grafico para os crimes por tipo
    em que as abcissas são os tipos de crime e
    as ordenadas são o numero de crimes.

    Requires: Requer um grafico que será um par de listas.
    Ensures: Devolve um grafico com o numero de crimes por tipo
    """
    pylab.figure(1)
    pylab.subplot(223)
    x1 = grafico[0]
    y1 = grafico[1]
    pylab.title(u"Número de crimes por tipo")
    pylab.ylabel(u'# Crimes')
    valoresx = numpy.arange(0, len(x1))
    pylab.bar(valoresx, y1,1)
    pylab.xticks(valoresx + 0.5, x1, rotation='vertical', fontsize=10)
    pylab.xlim(0, len(x1))
    pylab.show()

def traca_crimes_por_distancia(grafico, distancia_maxima):
    """
    Esta função vai traçar um gráfico para os crimes por distância
    em que as abcissas são as distâncias x100m e as ordenadas
    são o número de crimes por km2.
    
    Requires: Requer um grafico que será um par de listas.
    Requires: distancia_maxima seja do tipo inteiro
    Ensures: Devolve um gráfico com numero de crimes por distâncias
    """
    pylab.figure(1)
    pylab.subplots_adjust(wspace = 0.25, hspace = 0.25)
    pylab.subplot(224)
    x1 = grafico[0]
    y1 = grafico[1]
    pylab.title(u"Número de crimes por distância ao centro")
    pylab.xlabel(u'Distância (x 100m)')
    pylab.ylabel(u'# Crimes por km2')
    pylab.plot(x1, y1, '-')
    pylab.show()

def crimes_por_data(tabela):
    """
    Esta função recebe uma tabela, recolhe o campo CrimeDate linha a linha
    e incrementa o número de vezes que este aparece,retornando duas listas,
    uma com o numero de crimes e outra com a data, respetivando com o numero do index

    Requires: tabela, uma lista de dicionarios
    Ensures: tuplo de duas listas em que a primeira lista corresponde as datas e a segunda lista aos numeros de crimes.
    """
    contadores = {} #dicionario auxiliar para guardar Data : Contador

    date1 = datetime.datetime.strptime(tabela[-1]["CrimeDate"], "%m/%d/%Y").strftime("%Y%m%d") #obter data mais antiga (ultimo registo)
    today = datetime.date.today()
    date2 = str(today.year)+""+str(today.month)+""+str(today.day)            #converter data para formato desejado

    start = datetime.datetime.strptime(date1, '%Y%m%d')
    end = datetime.datetime.strptime(date2, '%Y%m%d')
    step = datetime.timedelta(days=1)                        #timedelta so concatena com datetimes, não funciona com str

    while start <= end:
        date_str=datetime.datetime.strptime(str(start.date()), "%Y-%m-%d").strftime("%Y%m%d")
        contadores[date_str]=0

        start += step

    return processa_tabela(tabela,contadores,"byDate")

def crimes_por_hora(tabela):
    """
    Esta função recebe uma tabela, e associa as 24h do dia todos os numeros de
    crimes ocorridos.

    Requires: Requer uma tabela que será uma lista de dicionarios.
    Ensures: Devolve um par de listas: primeira lista corresponde as horas e a segunda lista ao numero de crimes.
    """
    contadores = {}

    for i in range(24):
        contadores[i] = 0

    return processa_tabela(tabela,contadores,"byHour")

def crimes_por_tipo(tabela):
    """
    Esta função recebe uma tabela, e associa os tipos de crimes com
    o numero de crimes ocorridos.

    Requires: Requer uma tabela que será uma lista de dicionarios.
    Ensures: Devolve um par de listas: a primeira lista corresponde aos tipos de crimes que existem e a segunda
    lista ao numero de crimes.
    """
    contadores = {}

    return processa_tabela(tabela,contadores,"byType")

def crimes_por_distancia(tabela):
    """
    Esta função recebe uma tabela e associa os numeros de crimes por distância.

    Requires: Requer uma tabela que será uma lista de dicionarios.
    Ensures: Devolve uma par de listas: a primeira lista corresponde as distâncias múltiplos de 100m ao centro da cidade e
    a segunda lista contêm a densidade dos crimes em cada coroa circular.
    """
    contadores ={}

    return processa_tabela(tabela,contadores,"byDistance")

def haversine(lat1, lon1, lat2, lon2):
    """
    Returns the great circle distance between two GPS points given in degrees.
    See:
        http://stackoverflow.com/questions/4913349/haversine-formula-in-python-bearing-and-distance-between-two-gps-points
        http://www.movable-type.co.uk/scripts/latlong.html
    """
    lat1, lat2, dlat, dlon = map(radians, [lat1, lat2, lat2 - lat1, lon2 - lon1])
    a = sin(dlat / 2.0) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2.0) ** 2
    c = 2 * asin(sqrt(a))
    raio_da_terra_em_metros = 6371000
    return c * raio_da_terra_em_metros

def ler_tabela_de_csv(nome_ficheiro_csv):
    """
    Requires: o nome de um ficheiro CSV com cabeçalho na primeira linha.
    Ensures: retorna uma tabela no formato de lista de dicionários.
    """
    with open(nome_ficheiro_csv, 'rU') as ficheiro_csv:
        leitor = csv.DictReader(ficheiro_csv, delimiter=',')
        return [linha for linha in leitor]
        
### Verificando o tempo que leva a construir os dados dos gráficos

from timeit import timeit

# Uma string representando o nome do ficheiro CSV contendo os dados de
# interesse para o trabalho. Coloquem aqui o nome do vosso ficheiro.
ficheiro_dados_crimes = 'BPD_Part_1_Victim_Based_Crime_Data.csv'

def go_time():
    """Ensures: Devolve o tempo de execução da função dados_crimes()
    quando aplicada ao ficheiro ficheiro_dados_crimes.
    """
    return timeit("dados_crimes(ficheiro_dados_crimes)",
            setup = "from PRJ_45810 import dados_crimes;\
            ficheiro_dados_crimes= 'BPD_Part_1_Victim_Based_Crime_Data.csv'", number = 1)

def dados_crimes(nome_ficheiro):
    """Esta função não deve levar mais do que um determinado tempo
    quando executada numa máquina do laboratório do Departamento de
    Informática. O tempo em questão será anunciado na semana 9 de maio
    de 2016.

    Requires: nome_ficheiro é uma string representando o nome de um
    ficheiro CSV com dados sobre crimes.

    Ensures: Devolve um quadrúplo com os dados referentes a cada um dos
    quatro gráficos, de acordo com o enunciado do projeto.
    """
    t = ler_tabela_de_csv(nome_ficheiro)
    return crimes_por_data(t), crimes_por_hora(t), \
           crimes_por_tipo(t), crimes_por_distancia(t)

#####################################
######## Funcoes Auxiliares #########
#####################################

def processa_tabela(tabela,contadores,type_value):
    """
    Esta função processa os valores da tabela do ficheiro, por cada tipo de valor(hora,distancia,tipo e data),
    guardando os valores num dicionario ordenado, incrementado-os por cada ocorrencia no ficheiro. No caso da distancia,
    é necessario modificar os valores do dicionario, aplicando a respectiva formula de calculo do número de crimes por km2
    
    Requires: tabela, seja uma lista de dicionarios.
    Requires: type_value, do tipo STRING para identificar o grafico (hora,data,tipo e distancia)
    Requires: contadores, variavel do tipo dicionario, inicializado.
    ensures: tuplo , em que cada elemento é uma lista (abs e oord)
    """
    for n in tabela:
        k = trata_dados(n,type_value)

        ########## incrementa valores
        if  contadores.has_key(k):        #caso o dict tenha a key incrementa o valor
            i = contadores[k]
            contadores[k] = i+1
        else:                                #caso o dict nao tenha, cria o primeiro valor
            contadores[k] = 1
        
    contadores = collections.OrderedDict(sorted(contadores.items()))   #ordena os valores pelas keys, elementos das abcissas
    i = 0
    if type_value == "byDistance":
        map(contadores.pop,[-2])    #retira do dicitionario os valores acima de 100 (marcados pela key -2)
        for (k,v) in contadores.iteritems():
            if k != 0:
                new_value = v/ (pi*((k+1)*0.1)**2 - pi*(k*0.1)**2)
                contadores[contadores.keys()[i]] = new_value
            i+=1
    return converte_tuple(contadores)

def trata_dados(key,type_value):
    """
    Esta função faz o tratamento de dados dependendo do tipo recebido

    Requires: key, do tipo dicionario com as várias chaves referentes ao ficheiro .csv
    Requires: type_value, do tipo string para identificar o grafico (hora,data,tipo e distancia)
    Ensures: k, o tipo varia consoante o tipo de dados que estamos a tratar, se for pela Data é do tipo DATETIME
                    se for pela hora ou pela distancia é do tipo INT, se for pelo tipo é uma STRING
    """

    if type_value == "byDate":
        k = datetime.datetime.strptime(key["CrimeDate"], "%m/%d/%Y").strftime("%Y%m%d")  #converte a data

    if type_value == "byHour":
        k = int(key["CrimeTime"][:2])
        if k == 24:                    #existe uma hora que tem 24 em vez de 00
            k = 0

    if type_value == "byType":
        k = key["Description"]

    if type_value == "byDistance":
        k=0
        if key["Location 1"] != '':
            local_lat = key["Location 1"][1:14]
            local_long = key["Location 1"][16:30]

            dist_centro = haversine( 39.289444,-76.616667, float(local_lat),float(local_long) )
            if dist_centro/100 < 100:    #valores abaixo da coroa 100x100m , 1 a 99
                k = int(dist_centro/100)
            else:
                k=-2

    return k

def converte_tuple(contadores):
    """
    Esta função converte o dicionario CONTADORES para
        um tuplo de duas listas

    Requires: contadores, do tipo dicionario
    Ensures: tuplo de duas listas(abcs e oord)
    """

    abcs = [None]*len(contadores)    #tipo de crime
    oord = [None]*len(contadores)    #numero de crimes
    i=0

    for (k, v) in contadores.iteritems():    #itera os pares de chave/valor e guarda nas listas respectivas
        abcs[i] = k
        oord[i] = v
        i+=1

    return (abcs,oord)
