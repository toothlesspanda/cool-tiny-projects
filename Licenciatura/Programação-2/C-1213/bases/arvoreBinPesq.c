

/* Inserir  numa arvore de pesquisa */
ArvBin BST_arvore_inserir_ordem (ArvBin arv, int valor)
{
  ArvBin aux = arv;
  ArvBin ant = NULL;

  /*criar no*/
  ArvBin novo = (ArvBin)malloc( sizeof(struct noArvBin) );
  if (novo==NULL)
  {
	  erro = ARVBIN_MEM_ERROR;
	  return NULL;
  }
    /* preencher atributos */
     novo->elem = valor;
     novo->esquerda = NULL;
     novo->direita = NULL;
  

  /* inserir na arvore vazia */
  if (arv == NULL)
    return novo;

/*arvore não vazia*/
  while (aux != NULL) {
    ant = aux;
    if (valor <= aux -> elem)
      aux = aux -> esquerda;
    else
      aux = aux -> direita;
  }
  
  /* inserir a nova folha */
  if (valor <= ant -> elem)
    ant -> esquerda = novo;
  else 
    ant -> direita = novo;

  return arv;
}

/* Procura um elemento numa arvore binaria de pesquisa recursivamente */
ArvBin BST_arvore_procurar_elem_rec (ArvBin arv, int valor) 
{
  if (arv == NULL)
    return NULL;
  else
  if (valor == arv -> elem)
	return arv;
  else
    if (valor < arv -> elem)
      return BST_arvore_procurar_elem_rec (arv -> esquerda, valor);
    else   
	return BST_arvore_procurar_elem_rec (arv -> direita, valor);
}

/* Procura um elemento numa arvore binaria de pesquisa iterativamente */
ArvBin BST_arvore_procurar_elem_iter (ArvBin arv, int valor) 
{
  ArvBin aux = arv;
  int naoEncontrou=1;

  while (aux != NULL && naoEncontrou) {
	  if (valor == aux->elem )
		  naoEncontrou=0;
	  else
		  if (valor < aux->elem )
			  aux = aux -> esquerda;
		  else 
			  aux = aux  -> direita;
  }
  return aux;
}

/* Procura um elemento numa arvore binaria de pesquisa iterativamente 
   Outra solucao  */
ArvBin BST_arvore_procurar_elem_iter_v2 (ArvBin arv, int valor) 
{
  ArvBin aux = arv;

  while (aux != NULL &&  aux->elem!=valor ) {
		  if (valor < aux->elem )
			  aux = aux -> esquerda;
		  else 
			  aux = aux  -> direita;
  }
  return aux;
}

