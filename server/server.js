/*const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const io = require('socket.io')(server);
const path = require('path');
*/
const express = require('express');
const { createServer } = require('node:http');
const { join } = require('node:path');
const { Server } = require('socket.io');

const app = express();
const server = createServer(app);
const io = new Server(server);

const path = require('path');
const { initGame, gameLoop, getUpdatedVelocity } = require('./game');
const { FRAME_RATE } = require('./constants');
const { makeid } = require('./utils');

const state = {};
const clientRooms = {};

app.use(express.static(path.join(__dirname, '..', 'public')));

io.on('connection', (client) => {
  client.on('keydown', (handleKeydown));
  client.on('newGame', (handleNewGame));
  client.on('joinGame', (handleJoinGame));

  function handleJoinGame(roomName) {
    const room = io.sockets.adapter.rooms[roomName];
    
    console.log(room);
    let allUsers;
    if (room) {
      allUsers = room.sockets;
    }
    console.log(allUsers);

    let numClients = 0;
    if (allUsers) {
      numClients = Object.keys(allUsers).length;
    }

    console.log(`Room name: ${roomName}, Number of clients: ${numClients}`);

    if (numClients === 0) {
      client.emit('unknownCode');
      return;
    } else if (numClients > 1) {
      client.emit('tooManyPlayers');
      return;
    }

    clientRooms[client.id] = roomName;

    client.join(roomName);
    client.number = 2;
    client.emit('init', 2);

    startGameInterval(roomName);
  }

  function handleNewGame() {
    let roomName = makeid(5);
    console.log(`New game code: ${roomName}`);
    clientRooms[client.id] = roomName;
  
    // Crea uno stato del gioco solo se non esiste già per la stanza corrispondente
    if (!state[roomName]) {
      state[roomName] = initGame();
    }
  
    // Aggiungi il client alla stanza
    client.join(roomName, (err) => {
      if (err) {
        console.error(err);
      } else {
        //console.log(`Client ${client.id} joined room ${roomName}`);
        
        // Dopo aver aggiunto il client alla stanza, esegui la ricerca
        const room = io.sockets.adapter.rooms[roomName];
        console.log(room);
        
        let allUsers;
        if (room) {
          allUsers = room.sockets;
        }
        //console.log(allUsers);
  
        let numClients = 0;
        if (allUsers) {
          numClients = Object.keys(allUsers).length;
        }
  
        console.log(`Room name: ${roomName}, Number of clients: ${numClients}`);
      }
    });
  
    client.number = 1;
    client.emit('gameCode', roomName);
    client.emit('init', 1);
  
    // Log the current state of clientRooms and state
    console.log(clientRooms);
    console.log(state);
  }
  
  
  
  function handleKeydown(keyCode) {
    const roomName = clientRooms[client.id];
    if (!roomName) {
      return;
    }
    try {
      keyCode = parseInt(keyCode);
    } catch (e) {
      console.error(e);
      return;
    }

    const vel = getUpdatedVelocity(keyCode);

    if (vel) {
      state[roomName].players[client.number - 1].vel = vel;
    }

    // Log the current state of the game
    //console.log(state[roomName]);
  }
});

function startGameInterval(roomName) {
  const intervalId = setInterval(() => {
    const winner = gameLoop(state[roomName]);

    if (!winner) {
      emitGameState(roomName, state[roomName]);
    } else {
      emitGameOver(roomName, winner);
      state[roomName] = null;
      clearInterval(intervalId);
    }
  }, 1000 / FRAME_RATE);
}

function emitGameState(room, gameState) {
  io.sockets.in(room)
    .emit('gameState', JSON.stringify(gameState));
}

function emitGameOver(room, winner) {
  io.sockets.in(room)
    .emit('gameOver', JSON.stringify({ winner }));
}

const PORT = process.env.PORT || 8888;
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, '..', 'public', 'index.html'));
});
server.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
