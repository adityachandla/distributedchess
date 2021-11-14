# Distributed Chess Engine
## What is it ?
This is a java based project for a chess engine which can take move input
and based on evaluation criteria, finds out the best next move. Currently,
it uses a basic point based evaluator where the position in which a player
has the most points is the best. With that evaluation criteria we apply minmax
algorithm with a certain depth to identify the best move (This part is 
multi-threaded for performance). When we have more than one node, the computation
of moves gets split between those two nodes.

## How to run ?
- Package the jar
```bash
mvn clean package
```
- Run a server (On one machine)
```bash
java -jar distributedchess-1.0-SNAPSHOT.jar server 2 4
```
The first number is the number of nodes this server expects before getting started.
The second number represents the half-move depth up to which we evaluate the moves.

- Run clients

```bash
java -jar distributedchess-1.0-SNAPSHOT.jar client <server-ip>
```
Clients can be run using the preceding command.
The server's port 8080 should be open for client to be able to establish connection.

## How to play ?
The playing strategy is simple, you enter a move and after some time you get a move back.
A move is represented with a start position and an end position. The notation for a start and
end position is a little different and is defined in the image below: 

![Image](/markedBoard.png "board positions")