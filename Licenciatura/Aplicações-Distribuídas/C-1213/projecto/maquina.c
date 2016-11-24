/** 
 * Implementação do TDA Maquina 
 * autores: fc43538 Inês Matos e fc43536 Tiago Moucho*/
 
#include "maquina.h"
#include "fila_carros.h"
#include "gerador.h"
#include "carro.h"
#include <stdlib.h>
#include <stdio.h>


typedef struct maquina {
  int relogio;/*relogio*/
  Filac f;
  int mlucro0,mlucro1,mlucro2, contalucro0,contalucro1,contalucro2;
  int conta0,conta1,conta2; 
  int onOff, mtraco,mtempoSimulacao;/*0 disponivel, 1 ocupado*/
  int ordemChegada, minChegada, tempoEspera, mlimInfChegadas, mlimSupChegadas;
  int tempAtendidos, numtCarros;  
  int numAtendidos;
  double tempoMatendidos;
  int tipo, tempoTipo;
  int minFimLavagem,proxMinChegada;
  int ordemChegada1, minChegada1;
}Maquina_principal;

static int erro = MAQUINA_OK;

Maquina maquina_criar(int lucro0,int lucro1,int lucro2,int limInfChegadas,int
	                   limSupChegadas,int tempoSimulacao,int traco){

	Maquina maq = (Maquina) malloc( sizeof( Maquina_principal ) );
	if ( maq != NULL ){
		maq->relogio=0;
		maq->f = filac_criar();   	
		maq->mlucro0=lucro0;
		maq->mlucro1=lucro1;
		maq->mlucro2=lucro2;
		maq->contalucro0=0;
		maq->contalucro1=0;
		maq->contalucro2=0;
		maq->mlimInfChegadas=limInfChegadas;
		maq->mlimSupChegadas=limSupChegadas;
		maq->mtempoSimulacao=tempoSimulacao*60;
		maq->ordemChegada=0;
		maq->tipo=0;
		maq->ordemChegada1=0;
		maq->numAtendidos=0;
		maq->minChegada=geraInteiro(maq->mlimInfChegadas,maq->mlimSupChegadas);
		maq->minChegada1=0;
		maq->tempAtendidos=0;
		maq->conta0=0;
		maq->conta1=0;
		maq->conta2=0;
		maq->mtraco=traco;
		maq->onOff=0;   
		maq->tempoTipo=0;
		maq->tempoEspera=0;
		maq->numtCarros=0;
		maq->proxMinChegada=0;
		maq->minFimLavagem=0;
		maq->tempoMatendidos=0.000;
		return maq;
	}	
   
	erro = MAQUINA_MEM_ERROR;
	return NULL;
}

void maquina_destruir(Maquina maq){
   filac_destruir(maq->f);
   free(maq);
}

/*processa uma chegada de acordo com as regras.*/ 
void maquina_chegar(Maquina maq){

   Carro car;
  
   
   maq->ordemChegada++;
   car.ordemChegada=maq->ordemChegada;
   car.minChegada=maq->relogio;
   maq->proxMinChegada = geraInteiro(maq->mlimInfChegadas+maq->relogio,
                                     maq->mlimSupChegadas+maq->relogio);
   car.tipoLavagem=geraTipoLavagem();
   
   filac_entrar(maq->f,car);
  
   if(maq->mtraco==1){
      printf("minuto   %d: Carro %d lavagem %d entra na fila \n",car.minChegada,
      car.ordemChegada,car.tipoLavagem);
   }
   
   maq->minChegada = maq->proxMinChegada;
   maq->numtCarros++;
   
}


/* processa o início duma lavagem. */
void maquina_iniciar_lavagem(Maquina maq){

   maq->onOff=1;
      
   maq->ordemChegada1=filac_primeiro(maq->f).ordemChegada;    
   maq->tipo=filac_primeiro(maq->f).tipoLavagem;
   maq->minChegada1=filac_primeiro(maq->f).minChegada;

   filac_sair(maq->f);
      
   if(maq->tipo==0){
      maq->tempoTipo=5;
      maq->conta0++;
      maq->contalucro0=maq->contalucro0+maq->mlucro0;
   }
   else if(maq->tipo==1){
      maq->tempoTipo=7;
      maq->conta1++;
      maq->contalucro1=maq->contalucro1+maq->mlucro1;
   }
   else if(maq->tipo==2){
      maq->tempoTipo=10;
      maq->conta2++;
      maq->contalucro2=maq->contalucro2+maq->mlucro2;
   }
   
      
   if(maq->mtraco ==1){
      printf("minuto   %d: Carro %d lavagem %d entra na maquina \n",
      maq->relogio,maq->ordemChegada1,maq->tipo);
   }

   maq->tempoEspera+= maq->relogio - maq->minChegada1;
   maq->minFimLavagem= maq->relogio + maq->tempoTipo;
}

/*processa o fim de uma lavagem. */
void maquina_sair(Maquina maq){
   maq->onOff=0;

   if(maq->mtraco==1){
      printf("minuto   %d: Carro %d lavagem %d sai da maquina \n",maq->relogio,
      maq->ordemChegada1,maq->tipo);
   } 
   
   maq->numAtendidos++; 
   (maq->tempAtendidos) =+ (maq->relogio - maq->minChegada1);
} 

/*processa as desistências dos carros que estão na fila da máquina.*/ 
void maquina_desistir(Maquina maq){
   int primOrd,primOrd2;

   if(!filac_esta_vazia(maq->f))
      primOrd = filac_primeiro(maq->f).ordemChegada;
   
   filac_desistir(maq->f,10,maq->relogio);
   
   if(!filac_esta_vazia(maq->f)){
      primOrd2 = filac_primeiro(maq->f).ordemChegada;
 
      if(maq->mtraco==1){
         if(!filac_esta_vazia(maq->f) && primOrd!=primOrd2 )
            printf("minuto   %d: desistiu  %d ateh %d  \n",maq->relogio,primOrd,
                   primOrd2-1);                
      }
   }
   if(filac_esta_vazia(maq->f) && maq->mtraco==1)/*nao funciona*/
            printf("minuto   %d: Desistiram todos\n", maq->relogio);    
}

/*processa a simulação recorrendo às outras operações do TDA.*/
void maquina_processar(Maquina maq){

   while(maq->relogio <= maq->mtempoSimulacao){
      if(!filac_esta_vazia(maq->f)){         
         maquina_desistir(maq);
      }
                             
      if(maq->relogio == maq->minChegada){
         maquina_chegar(maq);
      }
      if(maq->relogio == maq->minFimLavagem && maq->onOff==1){  
         maquina_sair(maq);
      }
      if(maq->onOff == 0 && !filac_esta_vazia(maq->f) ){
         maquina_iniciar_lavagem(maq);
      }  
             
      maq->relogio++;  
   }

   while(!filac_esta_vazia(maq->f)){
      if(maq->onOff == 0){
         maquina_iniciar_lavagem(maq);
      }
      if(maq->onOff==1 && maq->relogio==maq->minFimLavagem){  
         maquina_sair(maq);
      }
      
      maq->relogio++;  
   }
   while(filac_esta_vazia(maq->f) && maq->onOff==1){
      if( maq->onOff==1 && maq->relogio==maq->minFimLavagem){
         maquina_sair(maq);
      }

      maq->relogio++;
   }
}


/* escreve o conteúdo da máquina apresentando o conteúdo da fila, quantos carros 
 * entraram no sistema, quantos carros desistiram, o tempo médio de espera por 
 * carro atendido, o tempo médio no sistema por carro atendido (inclui o tempo 
 * de espera e o tempo de atendimento) e o lucro diário da máquina 
 * (lucro total das lavagens efectuadas ‐ custo fixo de operação), conforme se 
 * exemplifica no ficheiro de output.
 */  
void maquina_printf(Maquina maq){

   /*relatorio*/

   printf("Relatorio\n");
   printf("Minuto actual:%d\n",maq->relogio);   
   printf("Conteudo da fila: ");
                              filac_imprimir(maq->f);
   printf("\n");
   printf("Lucro: %d\n",(maq->contalucro0+maq->contalucro1+maq->contalucro2)
          -200);
   printf("Numero de carros que entraram no sistema: %d \n",maq->numtCarros);
   printf("Numero de carros atendidos:%d \n", maq->numAtendidos); 
   printf("Numero de lavagens atendidas por tipo 0: %d, 1: %d, 2:%d \n",
          maq->conta0,maq->conta1,maq->conta2);
   printf("Numero de carros que desistiram: %d\n",
          (maq->numtCarros)-(maq->numAtendidos));
   printf("Tempo medio no sistema:%.3f \n",
          (double)maq->tempAtendidos/maq->numAtendidos);
   
   maq->tempoMatendidos=(double)(maq->tempoEspera/maq->numAtendidos);
                                    
   printf("Tempo medio de espera dos atendidos:%.3f \n",maq->tempoMatendidos);
}

/*o valor do erro do máquina. */
int maquina_erro( void ){
   return erro;
}

 /*reinicia o erroo máquina. */
void maquina_reset_erro( void ){
   erro = 0;
}
