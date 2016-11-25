/**
 * Interface para o tipo de dados abstracto Polinomio.
 * Author: Francisco Martins; alteracoes por csl, alteracoes respicio
 * date: April, 2007; April, 2008, March 2011
 */
#ifndef _POLINOMIO
#define _POLINOMIO

/* tipos de dados */
/* polinomio, apt para struct pol */
typedef struct pol *Polinomio;

/* operacoes */
/* criar polinomio nulo com o grau indicado */
/* pre: g >= 0 */
Polinomio polCriarNulo (int g);

/* alterar o valor do coeficiente em pos num polinomio p */
/* pre: pos <= polGrau(p) */
void polEscreverCoef (Polinomio p, int pos, double valor);

/* aceder ao coeficiente na posicao pos de um polinomio p */
/* pre: pos <= polGrau(p) */
double polLerCoef (Polinomio p, int pos);

/* apagar o polinomio p */
Polinomio polApagar (Polinomio p);

/* criar uma copia de um polinomio p */
Polinomio polCopiar (Polinomio p);

/* grau de um polinomio p */
int polGrau (Polinomio p);

/* verificar se um polinomio p eh nulo */
int polEhNulo (Polinomio p);

/* soma de dois polinomios p1 e p2 */
Polinomio polSoma (Polinomio p1, Polinomio p2);

/* polinomio simetrico de um polinomio p */
Polinomio polSimetrico (Polinomio p);

/* polinomio que resulta de subtrair polinomios p1 e p2 */
Polinomio polSubtraccao (Polinomio p1, Polinomio p2);

/* polinomio produto de dois polinomios p1 e p2 */
Polinomio polProduto (Polinomio p1, Polinomio p2);

/* verificar se dois polinomios p1 e p2 sao iguais */
int polSaoIguais (Polinomio p1, Polinomio p2);

/* avaliar polinomio p num ponto valor */
double polAvaliar (Polinomio p, double valor);

/* operacoes auxiliares para controlo do erro*/

/* devolve o erro associado as operacoes sobre polinomios */
int polErro (void);

/* reinicia o erro associado as operacoes sobre polinomios */
void polResetErro (void);

/* constantes (sendo estas usadas no controlo do erro) */
/* operacao correu sem erros */
#define POL_OK 0

/* erro ao reservar memoria para o polinomio */
#define POL_MEM_ERROR 1

#endif
