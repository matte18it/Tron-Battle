# Tron Battle
Nel gioco Tron Battle, l'ambiente in cui opera il nostro agente basato sull'utilità ha le seguenti caratteristiche:

Totalmente Osservabile: perchè l'agente può osservare l'intero stato del gioco, inclusa la posizione degli altri giocatori e le tracce lasciate dalle moto.
Ambiente Multiagente Competitivo: perchè le azioni di un giocatore influenzano direttamente le possibilità di vittoria degli altri.
Strategico: perchè le decisioni non possono essere prese solo sulla base della situazione attuale, ma devono considerare l'evoluzione futura del gioco.
Discreto: perchè le azioni sono limitate e il tempo è diviso in passi discreti.
Noto: le regole del gioco sono ben definite e note a tutti i giocatori. Non ci sono elementi nascosti o incognite sulle meccaniche di gioco.
Dinamico: il campo di gioco cambia continuamente poiché le moto si muovono e lasciano tracce dietro di sé. Questi cambiamenti devono essere costantemente monitorati e gestiti dai giocatori.
Sequenziale: le decisioni prese in ogni momento influenzano le opzioni disponibili in futuro. Ad esempio, una mossa che lascia la moto in una posizione rischiosa può limitare drasticamente le scelte successive.
