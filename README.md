# Snakes
Rappresentazione Grafica
![image](https://github.com/VolpiSte/Snakes/assets/101709267/d86013c4-64d5-4853-98dc-3df559a3e893)
Legenda:

Player 1
![image](https://github.com/VolpiSte/Snakes/assets/101709267/ae29b748-9bdd-41fe-b4d9-06dab8d5ed59)

Player 2
![image](https://github.com/VolpiSte/Snakes/assets/101709267/cbd94675-7f17-4be3-99f8-fbb3229bee97)

Nutrimento
![image](https://github.com/VolpiSte/Snakes/assets/101709267/bc8a1ad0-a77d-4e85-800c-d762a9610c61)


Matrice Lunghezza 20X10 Altezza


# Funzionamento
Campo da gioco costituito da una matrice (20x10) dove i vari player si muovono.

Giocatore 1 ha il serpente “verde” da muovere (tramite freccette della tastiera)
Giocatore 2 ha il serpente “blu” da muovere (tramite freccette della tastiera)
Ogni tot secondi la lunghezza del serpente aumenta di 1 (5 sec, 10 sec, 10 sec)
Ogni qualvolta un giocatore “mangia” un frutto aumenta il suo punteggio di 1
Un giocatore muore se incontra il corpo del giocatore avversario

Un giocatore vince quando:
- Giocatore avversario muore (si mangia da solo)
![image](https://github.com/VolpiSte/Snakes/assets/101709267/3ee43e03-f8fa-435c-b2e5-3d21f9a5c6b8)

- Giocatore mangia 10 frutti
- ![image](https://github.com/VolpiSte/Snakes/assets/101709267/8f6ba23d-f96c-4ffd-96a6-69df380674c2)

- Giocatore avversario mangia Giocatore
- ![image](https://github.com/VolpiSte/Snakes/assets/101709267/55b89e0c-24a5-4537-a05a-2d2cab91fa4c)

# Classi
Server
Gestione del server per i 2 giocatori

Thread Server
Gestione dei 2 giocatori in contemporanea

Client
Gestione del gioco



