//Ines Matos - 43538

int lista_penultimo(Lista l){
	NoPtr aux=l->cabeca;
	
	while(aux->prox->prox != NULL){
		if(aux->prox->prox == NULL)
			aux=aux->prox;	
		else
			aux=aux->prox;
	}
	
	return aux->elem;
}	
	
void lista_remover_penultimo(Lista l){
	NoPtr aux=l->cabeca;
	NoPtr c_no;

	if(aux->prox==NULL)	
		printf("Erro. So existe um elemento\n");
	else if(aux->prox->prox==NULL)
		lista_remover_cabeca(l);
		else{
			aux=l->cabeca;
			while(aux->prox->prox->prox != NULL)
				aux=aux->prox;	

			c_no=copiar_no(aux->prox->prox);	
			aux->prox=c_no;
			remover_no(aux->prox->prox);
			c_no->prox=c_no;
			c_no->prox->prox=NULL;
		}		
}
