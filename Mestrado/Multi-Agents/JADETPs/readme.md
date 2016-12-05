# Multi-Agentes

Contrução de agentes na plataforma JADE (exercícios)

###agentsTP2
###agentsTP3
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


###agentsTP6

Federação de DFs
Calculadora Distribuída, cada agente trata da uma operação e cada agente está num container diferente. Cada DF tem 2 operações
Starter - pede as operações e envia aos serviços(agente/operação) respectivos

#### Como correr
Inserção da conta na consola: [número]<espaço>[númmero]<espaço>[operação]   => operação pode ser: + - / *
-gui -port 1222 -agents "Boot:agentsTP6.BootAgent(1222)"   => argumento corresponde ao porto (se alterar aqui é preciso alterar no Starter)
-gui -port 1223 -agents "Boot2:agentsTP6.BootAgent(1223)"  => argumento corresponde ao porto (se alterar aqui é preciso alterar no Starter)
-gui -port 1221 -agents "Starter:agentsTP6.StarterAgent"





