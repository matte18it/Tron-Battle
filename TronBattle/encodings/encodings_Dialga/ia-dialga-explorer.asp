% zona(nome,  numeroAvversari, distanzaDaMe, numeroCelleRaggiungibiliDopoPath, numeroCelle)

% Guess
zonaSceltaDialga(N) | zonaNonSceltaDialga(N) :- zonaDialga(N, _, _,_,_).

% posso scegliere solo una zona
:- #count{N : zonaSceltaDialga(N)}=T, T!=1.

% scegli le zone con il numero di avversari minore
:~ zonaSceltaDialga(A), zonaDialga(A,D, _,_,_). [D@2]

% scegli le zone con il numero di celle raggiungibili maggiore
:~ zonaSceltaDialga(A), zonaDialga(A,_,_,D,_), T=2560-D. [T@3]

% scegli la zona più lontana
:~ zonaSceltaDialga(A), zonaDialga(A,_,D,_,_), T=2560-D. [T@1]

#show zonaSceltaDialga/1.
