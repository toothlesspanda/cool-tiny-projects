# Multi-Agentes

Contrução de agentes na plataforma JADE (exercícios)


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
