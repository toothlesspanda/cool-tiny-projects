# Multi-Agentes

Contrução de agentes na plataforma JADE (exercícios)

###Projecto Final [17/20]
Mercado Imobiliário com negociação entre clientes e dois Seller Agents. A documentação explica o funcionamento.

###agentsTP2
Simples agente de envio/receção de mensagem

###assignment2
Uma conversa entre agentes

Monte uma conversa com vários agentes com as seguintes regras:

- Os agentes registam-se no DF com o serviço "conversadores"  
- Um agente inicia a conversa com um assunto (por exemplo "conversa 1") e com o conteudo "0", enviando a mensagem para os outros agentes
Os outros agentes ao receberem a mensagem enviam uma nova mensagem para todos os agentes com o mesmo assunto e conteúdo "1" (este processo é repetido)  
- Um agente ao receber uma mensagem com o assunto X e conteúdo "10", deve enviar logo para os restantes uma mensagem com o assunto "terminar" e conteúdo X, deve também iniciar uma nova conversa com um novo assunto (por exemplo "conversa 2").

Em resumo, um agente inicia uma conversa sobre um assunto, são trocadas dez mensagens sobre esse assunto, quando isto é atingido, é iniciado um novo assunto repetindo o processo. Cada agente apenas intervem numa conversa em cada momento.  
  
Nota: Tendo os agentes um comportamento semelhante, apenas temos de definir um especial para iniciar a conversa (pode depois desaparecer) sendo os restantes cópias uns dos outros.   

###agentsTP3
Refazer o exercício 2 usando comportamentos paralelos. Cada agente conversador deve ter dois comportamentos em paralelo: um recebe as mensagens e outro envia as mensagens. Não pode haver repetição de mensagens com o mesmo conteúdo.


###agentsTP4
Uma máquina de estados:
- a formiga começa no ninho
- quando está com fome vai procurar comida
- a comida rejeita 95% das vezes e aceita nas restantes
- quando a formiga comer, volta para o ninho
- o ninho rejeita 80% das vezes e aceita nas restantes

Uso de FSMBehaviour, para a máquina de estados, e o TickerBehaviour para activar a fome da formiga.
#### Como correr:
-port 1222 -agents "Formiga:agentsTP4.Formiga;Ninho:agentsTP4.Ninho;Comida:agentsTP4.Comida"

###agentsTP5
//

###agentsTP6

Federação de DFs  
Calculadora Distribuída, cada agente trata da uma operação e cada agente está num container diferente. Cada DF tem 2 operações  
Starter - pede as operações e envia aos serviços(agente/operação) respectivos  

#### Como correr

-gui -port 1222 -agents "Boot:agentsTP6.BootAgent(1222)"   => argumento corresponde ao porto (se alterar aqui é preciso alterar no Starter)
  
-gui -port 1223 -agents "Boot2:agentsTP6.BootAgent(1223)"  => argumento corresponde ao porto (se alterar aqui é preciso alterar no Starter)  
  
-gui -port 1221 -agents "Starter:agentsTP6.StarterAgent"  
  
Antes de iniciar a conta, federar os DF's (Tools > Show Gui; Super DF > Federate), federar DF do Starter à plataforma 1222 e 1223(e vice-versa).  

Inserção da conta na consola: **[número]**[espaço]**[número]**[espaço]**[operação]**   => operação pode ser: + - / *  



