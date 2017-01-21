
pacman(R,_,Score,_,_,Pacman,O,L,F,_,PAS,_,_,_,_,_,OO):-
	comporEstado(R,_,Score,_,_,Pacman,O,L,F,_,PAS,_,_,_,_,Estado),
	minmaxLim(2,pacman,max,Estado,avalia,[_,_,_,OO,_,_,_],_,V).


comporEstado(R,_,Score,_,_,Pacman,O,L,F,_,PAS,_,_,_,_,[R,Score,Pacman,O,L,PAS,F]).

trocaMinMax(max,min).
trocaMinMax(min,max).

final([_,_,_,_,_,[],_]).
final([800,_,_,_,_,_,_]).

suc(Estado,_, NewEstado):-
	jogadapacman(Estado,NewEstado,_).



jogadapacman([R,Score,Pos,O,L,PAS,F],[RR,NewNewScore,NewPos,OO,L,PASNEW,F],_):-
	RR is R + 1,
	esquina(O, OO),
	apos(Pos,OO,NewPos),
	exist(NewPos,L),
	fantasma(NewPos,F,Score,NewScore),
	pastilha(NewPos,PAS,PASNEW,NewScore,NewNewScore).
	

pastilha(Pos,PAS,PASNEW,Score,NewScore):-
	exist(Pos,PAS),
	delMember(Pos,PAS,PASNEW),
	NewScore is Score + 100.

pastilha(_,_,_,Score,NewScore):-
	NewScore is Score - 100.


fantasma(Pos,F,Score,NewScore):-
	existFas(Pos,F),
	NewScore is -10000.

fantasma(Pos,F,Score,Score).

%caminho()



esquina(0, 90).
esquina(0, 270).
esquina(0, 0).
%esquina(0, 180).


esquina(90, 90).
esquina(90, 180).
%esquina(90,270).
esquina(90, 0).


esquina(180, 90).
%esquina(180,0).
esquina(180, 180).
esquina(180, 270).


esquina(270, 270).
esquina(270, 0).
esquina(270, 180).
%esquina(270,90).
	

apos((X,Y),0,(X,YY)):-
	YY is Y + 1.

apos((X,Y),90,(XX,Y)):-
	XX is X + 1.

apos((X,Y),180,(X,YY)):-
	YY is Y - 1.

apos((X,Y),270,(XX,Y)):-
	XX is X - 1.
	
exist(P,[P|_]).
exist(P,[_|P]).
exist(P,[_|R]):-
	exist(P,R).	

existFas(P,[(P,_,_)|_]).
existFas(P,[_|R]):-
	existFas(P,R).
	

delMember(_, [], []).
delMember(X, [X|Xs], Y) :-
    delMember(X, Xs, Y), !.
delMember(X, [T|Xs], [T|Y]) :-
    delMember(X, Xs, Y).


adv(Pacman,Fant).
adv(Fant,Pacman).

% Calcula qual o melhor par (valor,estado) dado que queremos minimizar ou maximizar

min([MV-MJ-ME|_],ME,MJ,MV).

max(L,ME,MJ,MV) :-
	ultimo(L,MV-MJ-ME).



ultimo([A], A ).

ultimo([_|Res],A):-
	ultimo(Res,A).


avalia(_,[_,Score,_,_,_,_,_],Score).



% Minimax limitado a um certo nível

minmaxLim(Lim,Jogador, _, Estado, Func, Estado, _, Valor) :-
	once( (Lim = 0 ; final(Estado)) ),!,
	Aval =.. [Func,Jogador,Estado,Valor],
	Aval.

minmaxLim(Lim,Jogador, MinMax, Estado, Func, MelhorE, MelhorJ,MelhorV) :- 
	trocaMinMax(MinMax,NMinMax),
	NLim is Lim - 1,
	setof(MV-Jog-Nestado, EE^MJ^(suc(Estado,Jog,Nestado),minmaxLim(NLim, Jogador, NMinMax, Nestado, Func, EE, MJ,MV)), LE),
	Prop =.. [MinMax,LE,MelhorE,MelhorJ,MelhorV], Prop.




% select a random element of a list
randMember(X,L) :-
 L \= [],
 comp(L,N),
 V is random(N),
 extractMember(0,V,L,X),!.

% The length of a list
comp([],0).
comp([_|T],N):-
	comp(T,M),
	N is M+1.

% extract the nth of a list
extractMember(I,E,[H|RL],Res) :-
	(I == E -> Res = H ; (NI is I+1, extractMember(NI,E,RL,Res))).