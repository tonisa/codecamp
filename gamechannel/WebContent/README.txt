How to use

Game flow:
1. create game        /gamechannel/games/create?name=test
2. players join game  /gamechannel/api/games/1/join/jamesbond
3. someone must start game /gamechannel/api/games/{game_id}/start
    or use automatic play /gamechannel/api/games/{game_id}/auto
4. for manual play, bomb others: /gamechannel/api/games/{game_id}/player/{player}/bomb?x=2&y=5
   coordinates are optional ;) 
   /bomb output responds with coordinates used and hit count.
   hit count > 0 => bomb again
5. wait for your turn, 
	poll /gamechannel/api/games/{game_id}/player/{player}
	read current player
	also returns your ships sunken by others
	also check for game state


# create game
GET http://localhost:8080/gamechannel/api/games/create?name=test&size=5

# Join with automatic ships placement
GET http://localhost:8080/gamechannel/api/games/1/join/mina

#Join with ships placement provided in POST BODY
POST http://localhost:8080/gamechannel/api/games/1/join/mina2

# start manual game, expects at least 2 players joined in
http://localhost:8080/gamechannel/api/games/1/start

# start full automatic game, expects at least 2 players joined in
# use this for generating data for UI building  
http://localhost:8080/gamechannel/api/games/1/auto

#bomb ships, expects that you are current player 
# automatic version, coordinates are fetched from secure random ;)

http://localhost:8080/gamechannel/api/games/1/player/mina/bomb

# advanced bombing, you have to think to where to bomb!
http://localhost:8080/gamechannel/api/games/1/player/mina2/bomb?x=2&y=5

#  player ranking within the game. 
http://localhost:8080/gamechannel/api/games/1/ranks

# status of players
http://localhost:8080/gamechannel/api/games/1/players

# GET game info
http://localhost:8080/gamechannel/api/games/1

#get player info
http://localhost:8080/gamechannel/api/games/1/player/mina
