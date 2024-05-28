% guess
mossaSceltaDialga(D) | mossaNonSceltaDialga(D) :- mossaDialga(D).

% posso scegliere solo una mossa
:- #count{D : mossaSceltaDialga(D)}=T, T!=1.

% Minimizza le mie celle non raggiungibili
:~ celleNonRaggiungibiliDialga(D,N), mossaSceltaDialga(D). [N@3]

% Minimizza le celle nemiche non raggiungibili
:~ celleNemicoNonRaggiungibiliDialga(I,D,N), mossaSceltaDialga(D). [N@2,I]

% Minimizza le celle vuote vicine
:~ celleVuoteVicineDialga(D,N), mossaSceltaDialga(D). [N@1]

#show mossaSceltaDialga/1.
