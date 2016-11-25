## projecto
Projecto lava-lava, o enunciado encontra-se na página

##tpcs
### tpc1
a) Escreva uma expressão recursiva produto( m, n ) para o cálculo do produto de m por n, ou seja, n vezes o número m. Escreva a recursão na variável n, considerando que m está fixo.

Expressão iterativa do produto:

produto( m, n ) = m + m + m + m + ... + m (n vezes), com n>=0

Num ficheiro tpc1.c escreva um programa em C, incluindo as funções 
produtoRec e produtoIter correspondentes às duas fórmulas (recursiva e iterativa, respectivamente).

No comentário da função produtoRec escreva a expressão recursiva do produto.

Na main, leia do teclado dois inteiros m e n e imprima o resultado da chamada da função produtoIter( m,n ) e o resultado da chamada da função produtoRec( m,n ).

b) Repita o exercício anterior para a função potencia( m,n ), ou seja, m elevado a n, onde m está fixo, n é qualquer inteiro positivo ou nulo. Para tal construa funções potenciaRec e potenciaIter que calculam a potência através de um algoritmo recursivo e de um algoritmo iterativo, respectivamente. Na main, faça escrever o resultado da invocação destas funções usando como argumentos os inteiros m e n já lidos.

###tpc2

Considere as seguintes operações a adicionar ao TDA Lista de inteiros numa extensão deste TDA:

- lista_penultimo - retorna o penúltimo elemento da lista

- lista_remover_penultimo - remove o penúltimo elemento da lista

Escreva o código das funções que implementam estas operações tendo o cuidado de especificar pré-condições, caso existam.

Pode testar as suas funções usando a codificação do TDA Lista de inteiros e da aplicação que usa e testa esse TDA.			
###bases
Código para o funcionamento das pilhas, filas, árvores binárias, ordenação, etc.