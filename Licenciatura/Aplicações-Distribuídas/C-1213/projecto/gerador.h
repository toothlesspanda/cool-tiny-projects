/**
 * Interface do modulo gerador de numeros pseudo-aleatorios.
 *
 * author: respicio
 * date: April, 2013
 */

#ifndef _GERADOR
#define _GERADOR


/**
 * Inicializacao do gerador de numeros pseudo-aleatorios de forma a que
 * a sequencia de chamadas as funcoes geraInteiro e geraLavagem 
 * seja dependente do system-time. Se esta funcao nao for previamente 
 * invocada, a sequencia de valores obtidos eh sempre a mesma. O algoritmo da 
 * funcao rand() usa uma semente para gerar uma sequencia de numeros, 
 * por isso eh necessario usar o srand para fazer variar essa semente. 
 * A constante RAND_MAX esta definida na standard library (stdlib).
 */
void inicia( void );

/**
 * Retorna um numero inteiro pseudo-aleatorio no intervalo [min, max[
 * @param min o limite inferior do intervalo
 * @param max o limite superior do intervalo
 */
int geraInteiro( int min, int max);

/**
 * um tipo de lavagem gerado de acordo com as percentagens definidas no
 * projecto Lava-lava
 */
int geraTipoLavagem( void );

#endif
