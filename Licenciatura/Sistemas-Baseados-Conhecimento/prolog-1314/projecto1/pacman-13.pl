pacman(_,_,_,N,_,_,P,O,L,F,M,_,_,_,OO) :-
	M =< 1,
	ahFantamasLinha(P,O,L,F),
	cruzamento(P,O,L,DIR),
	ahFantamasLinha(P,OO,L,F).

pacman(_,_,_,N,_,_,P,O,L,F,M,_,_,_,OO) :-
	M =< 1,
	ahFantamasLinha2(P,O,L,F),
	inv(O,OO).

pacman(_,_,_,_,_,_,P,O,L,F,M,_,_,_,OO) :-
	M > 4,
	correAtrasFanta(P,O,L,F,OO).

pacman(_,_,_,_,_,_,P,O,L,_,_,_,_,S,OO) :-
	S \= [],
	predicadoEstrela(P,O,L,S,OO).

pacman(_,_,_,_,_,_,P,O,L,_,_,PAS,SPAS,_,OO) :-
	ahpastilhasNesteCanto(P,O,PAS,SPAS,OO).


pacman(_,_,_,_,_,_,P,O,L,_,_,_,_,_,DIR) :-
	asParedeMalditas(P,O,L,DIR).

pacman(_,_,_,5,_,_,P,O,L,_,_,PAS,SPAS,_,OO):-
	apos(P,O,PP),
	\+exist(PP,L),
	inv(O,OO).

pacman(_,_,_,_,_,_,P,O,L,F,M,PAS,SPAS,_,DIR) :-
	predicadoSecreto(P,O,L,PAS,SPAS,DIR).

pacman(_,_,_,_,_,_,P,O,L,F,M,PAS,SPAS,_,DIR) :-
	inv(O,OO),
	\+ ahFantamasLinha(P,OO,L,F),
	predicadoSecreto(P,OO,L,PAS,SPAS,DIR).

pacman(_,_,_,_,_,_,P,O,L,_,_,PAS,SPAS,_,DIR) :-
	esquina(O,OO),
	predicadoSecreto(P,OO,L,PAS,SPAS,DIR).

pacman(_,_,_,_,_,_,P,O,L,_,_,PAS,SPAS,_,DIR) :-
 	PAS \= [],
	where(P,O,L,PAS,DIR).

pacman(_,_,_,_,_,_,P,O,L,_,_,PAS,SPAS,_,DIR) :-
	cruzamento2(P,O,L,DIR).

pacman(_,_,_,_,_,_,P,O,L,_,_,PAS,SPAS,_,O).


predicadoEstrela(P,O,L,S,OO):-
	elem(S,(PS,_,D)),
	caminhoLivre(P,O,L,PS,D,OO).

caminhoLivre((X,Y),O,L,(XS,YS),D,O):-
	D > 0,
	exist((X,Y),L),
	X == XS,
	Y == YS.

caminhoLivre((X,Y),O,L,(XS,YS),D,OO):-
	D > 0,
	esquina(O,OO),
	apos((X,Y),OO,(XX,YY)),
	exist((XX,YY),L),
	XX == XS,
	YY == YS.

caminhoLivre(P,O,L,(XS,YS),D,OO):-
	DD is D - 1,
	DD > 0,
	apos(P,O,PP),
	exist(PP,L),
	caminhoLivre(PP,O,L,(XS,YS),DD,OO).

where(P,O,L,PAS,DIR):-
	sort(PAS,SPAS),
	PAS = [X|_],
	pastilha(X,P,O,L,DIR).


pastilha((XP,YP),(X,Y),0,L,DIR):-
	X > XP,
	esquinaE(0,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).

pastilha((XP,YP),(X,Y),0,L,DIR):-
	X < XP,
	esquinaD(0,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).


pastilha((XP,YP),(X,Y),90,L,DIR):-
	Y > YP,
	esquinaD(90,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).

pastilha((XP,YP),(X,Y),90,L,DIR):-
	Y < YP,
	esquinaE(90,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).


pastilha((XP,YP),(X,Y),180,L,DIR):-
	X < XP,
	esquinaE(180,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).

pastilha((XP,YP),(X,Y),180,L,DIR):-
	X > XP,
	esquinaD(180,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).


pastilha((XP,YP),(X,Y),270,L,DIR):-
	Y < YP,
	esquinaD(270,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).

pastilha((XP,YP),(X,Y),270,L,DIR):-
	Y > YP,
	esquinaE(270,DIR),
	apos((X,Y),DIR,PP),
	exist(PP,L).

correAtrasFanta(P,O,L,F,O):-
    ahFantamasLinha(1,P,O,L,F).

correAtrasFanta(P,O,L,F,OO):-
    esquina(O,OO),
    ahFantamasLinha(1,P,OO,L,F).



ahFantamasLinha(P,O,L,F):-
	ahFantamasLinha(1,P,O,L,F).

ahFantamasLinha2(P,O,L,F):-
	ahFantamasLinha(4,P,O,L,F).



ahFantamasLinha(K,P,O,L,F):-
	esquina(O,OO),
	apos(P,OO,(X,Y)),
	exist((X,Y),L),
	elem(F,((XF,YF),C)),
	C == false,
	X == XF,
	Y == YF.

ahFantamasLinha(K,P,O,L,F):-
	esquina(O,OO),
	apos(P,OO,PP),
	exist(PP,L),
	apos(PP,OO,(X,Y)),
	exist((X,Y),L),
	elem(F,((XF,YF),C)),
	C == false,
	X == XF,
	Y == YF.

ahFantamasLinha(K,P,O,L,F):-
	apos(P,O,(X,Y)),
	exist((X,Y),L),
	elem(F,((XF,YF),C)),
	C == false,
	X == XF,
	Y == YF.


ahFantamasLinha(K,P,O,L,F):-
	K < 6,
	apos(P,O,PP),
	exist(PP,L),
	KK is K +1,
	ahFantamasLinha(KK,PP,O,L,F).

cruzamento(P,O,L,DIR):-
	appendCruz(P,L,O,[0,90,180,270],[],List),
	randMember(DIR,List).

cruzamento2(P,O,L,DIR):-
	appendCruz2(P,L,O,[0,90,180,270],[],List),
	randMember(DIR,List).

%Pellets


predicadoSecreto(P,O,L,PAS,SPAS,O):-
	exist(P,L),
	(exist(P,PAS);exist(P,SPAS)).

predicadoSecreto(P,O,L,PAS,SPAS,O):-
	esquina(O,OO),
	apos(P,OO,PP),
	(exist(PP,PAS);exist(PP,SPAS)).


predicadoSecreto(P,O,L,PAS,SPAS,O):-
	apos(P,O,PP),
	exist(PP,L),
	predicadoSecreto(PP,O,L,PAS,SPAS,OO).

predicadoSecreto(P,O,L,PAS,SPAS,OO):-
	ahpastilhasNesteCanto(P,O,PAS,SPAS,OO).


ahpastilhasNesteCanto(P,O,PAS,SPAS,OO):-
	appendPASDIR(P,PAS,SPAS,O,[0,90,180,270],[],PASP),
	PASP \= [],
	randMember(OO,PASP).

appendPASDIR(P,PAS,SPAS,O,[],List,[]).

appendPASDIR(P,PAS,SPAS,O,[360|_],List2,List2).

appendPASDIR(P,PAS,SPAS,O,[Head|Tail],List2,[Head|Result]):-
	inv(O,OO),
	Head \= OO,
	apos(P,Head,PP),
	(exist(PP,PAS);exist(PP,SPAS)),
    appendPASDIR(P,PAS,SPAS,O,Tail,List2,Result).

appendPASDIR(P,PAS,SPAS,O,[Head|Tail],List2,Result):-
    appendPASDIR(P,PAS,SPAS,O,Tail,List2,Result).


asParedeMalditas(P,O,L,OO):-
	apos(P,O,PP),
	\+exist(PP,L),
	append(P,L,O,[0,90,180,270],[],LIST),
	LIST \= [],
	randMember(OO,LIST).


ahRandom(P,O,L,DIR):-
	append(P,L,O,[0,90,180,270],[],LIST),
	LIST\= [],
	randMember(DIR,LIST).


%Sem direção actual%
append(P,L,O,[],List,[]).

append(P,L,O,[Head|Tail],List2,[Head|Result]):-
	Head \= O,
	inv(O,OO),
	Head \= OO,
	apos(P,Head,PP),
	exist(PP,L),
    append(P,L,O,Tail,List2,Result).

append(P,L,O,[Head|Tail],List2,Result):-
    append(P,L,O,Tail,List2,Result).


%Com direção actual%
appendCruz(P,L,O,[],List,[]).

appendCruz(P,L,O,[Head|Tail],List2,[Head|Result]):-
	inv(O,OO),
	Head \= O,
	Head \= OO,
	apos(P,Head,PP),
	exist(PP,L),
    appendCruz(P,L,O,Tail,List2,Result).

appendCruz(P,L,O,[Head|Tail],List2,Result):-
    appendCruz(P,L,O,Tail,List2,Result).


appendCruz2(P,L,O,[],List,[]).

appendCruz2(P,L,O,[Head|Tail],List2,[Head|Result]):-
	inv(O,OO),
	Head \= OO,
	apos(P,Head,PP),
	exist(PP,L),
    appendCruz2(P,L,O,Tail,List2,Result).

appendCruz2(P,L,O,[Head|Tail],List2,Result):-
    appendCruz2(P,L,O,Tail,List2,Result).

ahpastilhas(P,PAS,SPAS):-
	(exist(PP,PAS);exist(PP,SPAS)).

apos((X,Y),0,(XX,YY)):-
	XX is X,
	YY is Y + 1.

apos((X,Y),90,(XX,YY)):-
	XX is X + 1,
	YY is Y.

apos((X,Y),180,(XX,YY)):-
	XX is X,
	YY is Y - 1.

apos((X,Y),270,(XX,YY)):-
	XX is X - 1,
	YY is Y.
	

exist(P,[P|_]).

exist(P,[_|P]).

exist(P,[_|R]):-
	exist(P,R).


elem([X|_],X).

elem([_|R],X):-
	elem(R,X).

inv(0,180).
inv(180,0).
inv(270,90).
inv(90,270).

esquina(0,90).
esquina(90,180).
esquina(180,270).
esquina(270,0).
esquina(0,270).
esquina(90,0).
esquina(180,90).
esquina(270,180).

esquinaD(0,90).
esquinaD(90,180).
esquinaD(180,270).
esquinaD(270,0).

esquinaE(0,270).
esquinaE(90,0).
esquinaE(180,90).
esquinaE(270,180).


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