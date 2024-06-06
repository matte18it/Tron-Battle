# 🏍️ Tron Battle 🏍️
Nel gioco Tron Battle, l'ambiente in cui opera il nostro agente basato sull'utilità ha le seguenti caratteristiche:

<ol>
<li><strong>Totalmente Osservabile:</strong> perchè l'agente può osservare l'intero stato del gioco, inclusa la posizione degli altri giocatori e le tracce lasciate dalle moto.</li>
<li><strong>Ambiente Multiagente Competitivo:</strong> perchè le azioni di un giocatore influenzano direttamente le possibilità di vittoria degli altri.</li>
<li><strong>Strategico:</strong> perchè l'ambiente è deterministico tranne che per le azioni degli altri agenti.</li>
<li><strong>Discreto:</strong> perchè le azioni sono limitate e il tempo è diviso in passi discreti.</li>
<li><strong>Noto:</strong> le regole del gioco sono ben definite e note a tutti i giocatori. Non ci sono elementi nascosti o incognite sulle meccaniche di gioco.</li>
<li><strong>Dinamico:</strong> il campo di gioco cambia continuamente poiché le moto si muovono e lasciano tracce dietro di sé. Questi cambiamenti devono essere costantemente monitorati e gestiti dai giocatori.</li>
<li><strong>Sequenziale:</strong> le decisioni prese in ogni momento influenzano le opzioni disponibili in futuro. Ad esempio, una mossa che lascia la moto in una posizione rischiosa può limitare drasticamente le scelte successive.</li>
</ol>
