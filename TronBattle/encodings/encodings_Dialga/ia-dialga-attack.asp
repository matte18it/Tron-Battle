% cella(X,Y, distanzaDaNemico, distanzaDaMe, celleRaggiungibiliDaNemico,celleRaggiunbiliDaMe, conNemico)
% Guess
cellaSceltaDialga(X,Y) | cellaNonSceltaDialga(X,Y) :- cellaDialga(X,Y,_,_,_,_,_).

% scegli solo una cella
:- #count{X,Y : cellaSceltaDialga(X,Y)}=T, T!=1.

% preferisci le celle senza alcun nemico
:~ cellaSceltaDialga(X,Y), cellaDialga(X,Y,_,_,_,_,D). [D@3]

% preferisci le celle che diminuiscono la celle raggiungibili del nemico
:~ cellaSceltaDialga(X,Y), cellaDialga(X,Y,_,_,D,_,_). [D@4]

% preferisci le celle che aumentano le celle raggiungibili da me
:~ cellaSceltaDialga(X,Y), cellaDialga(X,Y,_,_,_,D,_), T=2560-D. [T@5]

% preferisci le celle più lontane dal nemico
:~ cellaSceltaDialga(X,Y), cellaDialga(X,Y,D,_,_,_,_), T=2560-D. [T@2]

% preferisci le celle più vicine da me
:~ cellaSceltaDialga(X,Y), cellaDialga(X,Y,_,D2,_,_). [D2@1]

#show cellaSceltaDialga/2.
