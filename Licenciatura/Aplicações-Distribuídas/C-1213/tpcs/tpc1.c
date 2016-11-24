/*tpc 1
 * Inês Matos - 43538 */
 
#include <stdio.h>

int produtoRec(int m, int n);
int produtoIter(int m, int n);
int potenciaRec(int m, int n);
int potenciaIter(int m, int n);

int main (void) {

	int s1,s2;
	
	printf("Introduza dois numeros(n vezes o m):\n");
	scanf("%d",&s1);
	scanf("%d",&s2);
	printf("O seu n:%d\n",s1);
	printf("O seu m:%d\n",s2);

	printf("Produto(n*m)\n");
	printf("Resultado em recursivo:%d\n",produtoRec(s2,s1));
	printf("Resultado em iterativo:%d\n",produtoIter(s2,s1));
	printf("\n");
	
	printf("Potencia(m^n)\n");
	printf("Resultado em recursivo:%d\n",potenciaRec(s2,s1));
	printf("Resultado em iterativo:%d\n",potenciaIter(s2,s1));
	printf("\n");
	return 0;
}

/*base: quando n = 0, 0 vezes m = 0(retorna)
 *passos: somatório de m, n vezes 
 *Produto(m,n)=m(indice n) + m(indice n-1)+...+m(indice 1)*/
int produtoRec(int m, int n){

	if(n==0)
		return 0;
	else
		return produtoRec(m,n-1)+m;
} 

int produtoIter(int m, int n){
	
	int i, resultado=0;

	for(i=1;i<=n;i++){
		resultado= resultado + m;
	}
	
	return resultado;
}

/*base: quando n = 0, m^0 = 1(retorna)
 *passos: multiplicação de m, n vezes 
 *Produto(m,n)=m(indice n) * m(indice n-1)*...*m(indice 1)*/
int potenciaRec(int m, int n){
	if(n==0)
		return 1;
	else
		return m*potenciaRec(m,n-1);
}

int potenciaIter(int m, int n){
	
	int i, resultado=1;

	for(i=1;i<=n;i++){
		resultado= resultado * m;
	}
	
	return resultado;
}