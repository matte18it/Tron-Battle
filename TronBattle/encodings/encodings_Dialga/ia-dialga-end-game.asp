% start(X,Y). nodo di partenza
% node(X,Y). nodi

arcDialga(X,Y,X1,Y):- nodeDialga(X,Y), nodeDialga(X1,Y), X=X1+1. % archi
arcDialga(X,Y,X1,Y):- nodeDialga(X,Y), nodeDialga(X1,Y), X=X1-1.
arcDialga(X,Y,X,Y1):- nodeDialga(X,Y), nodeDialga(X,Y1), Y=Y1-1.
arcDialga(X,Y,X,Y1):- nodeDialga(X,Y), nodeDialga(X,Y1), Y=Y1+1.

reachedDialga(X,Y) :- startDialga(X,Y).
reachedDialga(X1,Y1) :- reachedDialga(X,Y), inPathDialga(X,Y,X1,Y1).

:~ nodeDialga(X,Y), not reachedDialga(X,Y). [1@1, X,Y] % preferibilmente raggiungi tutti i nodi
:- inPathDialga(X,Y,X1,Y1), inPathDialga(X,Y,X2,Y2), X2!=X1. % due archi con lo stesso nodo di partenza
:- inPathDialga(X,Y,X1,Y1), inPathDialga(X,Y,X2,Y2), Y1!=Y2.
:- inPathDialga(X1,Y1,X,Y), inPathDialga(X2,Y2,X,Y), Y1!=Y2.  % due archi cono lo stesso nodo di arrivo
:- inPathDialga(X1,Y1,X,Y), inPathDialga(X2,Y2,X,Y), X2!=X1.
:- inPathDialga(X,Y,X1,Y1), startDialga(X1,Y1). % non può essere un ciclo

% Guess
inPathDialga(X,Y,X1,Y1) | outPathDialga(X,Y,X1,Y1) :- arcDialga(X,Y,X1,Y1).

#show inPathDialga/4.
