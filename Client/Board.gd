extends Node

var board = []
var blankBoard = []

var cardList = []
#so here's how the board works:
#every position has an arrayList or w/e.
#every entry in said arrayList is a card (represented as an array: id, atk, hp, cd)
#everything else should be stored locally, so we're all good there.

func _init():
	#load all card art by id
	for x in range(2):
		cardList.append(load(getImagePath(x)))
	
	board.resize(5)
	blankBoard.resize(5)
	for x in range(5):
		blankBoard[x] = []
		board[x] = []
		board[x].resize(3)
		blankBoard[x].resize(3)
		for y in range(3):
			#looks like we're gonna have to load every card in the game first, to save memory. Or at least a decent chunk, so ppl can't chet. Maybe every single one in the used resources? Idk. Maybe just decks are enough.
			blankBoard[x][y] = load("res://img/Blank.png")
			board[x][y] = []
			board[x][y].append(0)

func set_board(var board):
	self.board = board

func draw(var node):
	for x in range(5):
		for y in range(3):
			blankBoard[x][y].set_size_override(Vector2(100, 100))
			if(x % 2 == 0):
				node.draw_texture_rect(blankBoard[x][y], Rect2(y * 100 + 50, x * 75, 100, 100), false)
				node.draw_texture_rect(cardList[board[x][y][0]], Rect2(y * 100 + 50, x * 75, 100, 100), false)
			else:
				node.draw_texture_rect(blankBoard[x][y], Rect2(y * 100, x * 75, 100, 100), false)
				node.draw_texture_rect(cardList[board[x][y][0]], Rect2(y * 100, x * 75, 100, 100), false)


func getImagePath(var id):
	if(id == 0):
		return "res://img/Blank.png"
	elif(id == 1):
		return "res://img/IceSoldier.png"
	else:
		return "err"
