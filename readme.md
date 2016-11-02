##Sistema Distribuído

Trabalho realizado em Python

As chaves no projecto já não funcionam, se quiserem por a funcionar terão de criar umas novas
The keys in the project are not working, if you want it to work you have to create a new one

python hkvs_server.py 9995 <num_para_o_log>    <<< Por "defeito" é o primário

python hkvs_server.py 9994 <num_para_o_log>

python hkvs_server.py 9993 <num_para_o_log>

python configservice.py <num_operacoes_para_o_alive> <timeout>

python hkvs_cliente.py <ip_server> <porto>


Efectua backups com o primário e quando é atribuido um novo primário
Cliente tenta-se ligar interativamente aos servidores caso a ligação não se efectue
Cliente não se liga quando o servidor é backup ou não tem atribuição
Ligação SSL feita em todas as conexões 
Servidor primário vai abaixo, o backup com o porto 9994 passa a sê-lo. Só temos a definição do primário apenas quando o servidor do porto 9995 vai abaixo.
Se o cliente estiver ligado ao servidor primário e este vá abaixo, não temos maneira de recuperar a ligação para o novo primário.
											


####		#############Readme do projecto 3 #################


Enviamos ficheiros extra, de acordo com o procedimento para testar o programa:

Client/client_na.req 	<---		para testar a Teia de Autorização
CA_key/file.srl		<---		foi necessário para executar o comando de criação de certificados através do CA

Checkpoints 								- feito
Recuperação a Falhas  (os 3 casos) 					- feito
Segurança(comunicação segura,confidencialidade e teias de autorização) 	- feito


Não esquecer de colocar o argumento do número de linhas para o log(no servidor).  
	
		>>>> python hkvs_server.py 9999 <num_linhas_para_o_log>

Não há restrições no projecto(que se tenha verificado até agora).

Os certificados terminam a 15 de Maio.


####		#############Readme do projecto 2 #################



		Em relação ao primeiro projecto resolvemos uns bugs, efectuando todo o projecto 2.

		Não esquecer de colocar o argumento do número de linhas para o log(no servidor).

		Não há restrições no projecto(que se tenha verificado até agora).

		Pode seguir os mesmos testes indicados no readme do projecto anterior.


											
####		#############Readme do projecto 1 Parcial #################
					


	>>>Todos os caminhos são introduzidos entre "/", p.ex:
	>>>	/caminho1/caminho2

	>>>Para inserir na raiz usar simplesmente "/".

	>>>Tivemos em consideração:

	Geral
		- Confirmação da existência do path separado por barras
		- O utilizador não fecha o servidor
		- Caso não ponha "/",como raiz,o sistema nao aceita para a concretizacao dos comandos

		opcao: 12345 (insucesso)		->> nao aceita outras opcoes
		opcao: <1-6>
		path: <vazio>  (insucesso) ->> nao aceita espaços vazios
		opcao:1
		path: qualquercoisa (insucesso) ->> nao aceita apenas palavras(uso de "/" obrigatorio)
		opcao:1
		path: isto (insucesso) --> path: /isto

	Create:
		- Cria dicts dentro de dicts 
		- Evita substituiçoes 	
	ex:
		opcao:1
		path:/
		name:ola (sucesso)

		opcao:1
		path:/ola
		name: ola2 (sucesso)

		opcao:1
		path:/
		name:ola (insucesso,existe uma direcotira no mesmo caminho designado "ola")

	Put:
		- Evita subsituicoes
		opcao:2
		path:/
		name:ola
		value: 1234 (insucesso, existe uma directoria no mesmo caminho designado "ola")

		opcao:2
		path:/ola
		name:ola123
		value: 1235 (sucesso)


	Cas
		- Verifica se o nome e cur_val existem
		opcao:3
		path:/ola
		name:ola123
		Cur_val:1235
		New_vale:ola9876 (sucesso)

		opcao:3
		path:/ola
		name:ola123
		Cur_val:adeus
		New_vale: outro (insucesso, a key "ola123" nao corresponde ao valor "ola123" mas sim a "ola9876")

	Delete
		- Apaga uma entrada e só apaga uma directoria se esta estiver vazia
		- Nao da para apagar a raiz
		opcao:4
		Path: /ola (insucesso, porque a pasta nao esta vazia)

		opcao:4
		Path: /ola/ola2 (sucesso)	

	Get
		- Retorna apenas o value da key pedida, caso a key seja uma entrada
		opcao:5
		Path: /ola (insucesso, porque ola eh uma directoria)

		opcao:5
		Path: /ola/ola123
		Value: ola9876 (sucesso)

	List
		- Lista os elementos, se a key pedida for uma directoria
		opcao: 6
		Path: /ola/ola123 (insucesso, porque ola eh uma entrada)

		opcao: 6
		Path: /ola
		--------[ Lista ]--------
		1- ola2
		2- ola123 (sucesso)
												
											

