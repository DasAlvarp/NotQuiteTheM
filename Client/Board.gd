extends Node

var board = []
var mine

var cardList = []
var cu = load("res://CardUtiler.gd")
var CardUtiler
var sq = load("res://Square.gd")
var square = []
var command = null

#so here's how the board works:
#every position has an arrayList or w/e.
#every entry in said arrayList is a card (represented as an array: id, atk, hp, cd)
#everything else should be stored locally, so we're all good there.
var width = Globals.get("display/width") - 100

func _init(var mine):
	self.mine = mine
	CardUtiler = cu.new()
	#load all card art by id
	cardList = CardUtiler.getCardList()
	
	square.resize(5)
	board.resize(5)
	for x in range(5):
		square[x] = []
		board[x] = []
		square[x].resize(3)
		board[x].resize(3)
		for y in range(3):
			#looks like we're gonna have to load every card in the game first, to save memory. Or at least a decent chunk, so ppl can't chet. Maybe every single one in the used resources? Idk. Maybe just decks are enough.
			board[x][y] = []
			board[x][y].append(0)
			#creating clicked hexes
			if(mine):
				if(x % 2 == 0):
					square[x][y] = sq.new(y * 100 + 50, x * 75)
				else:
					square[x][y] = sq.new(y * 100, x * 75)
			else:
				if(x % 2 == 0):
					square[x][y] = sq.new(width - y * 100 - 50, x * 75)
				else:
					square[x][y] = sq.new(width - y * 100, x * 75)


func set_board(var nuBoard):
	self.board = nuBoard


#check hitboxes, etc every click
func onInput(node, mouse, mPos):
	for x in range(5):
		for y in range(3):
			square[x][y].checkStuff(node, mouse, mPos)
	return maxSel(1)


#draw board. Includes cases for my board and theirs
func draw(var node):
	if(mine):
		for x in range(5):
			for y in range(3):
				if(x % 2 == 0):
					node.draw_texture_rect(cardList[board[x][y][0]], Rect2(y * 100 + 50, x * 75, 100, 100), false)
				else:
					node.draw_texture_rect(cardList[board[x][y][0]], Rect2(y * 100, x * 75, 100, 100), false)
	else:
		for x in range(5):
			for y in range(3):
				if(x % 2 == 0):
					node.draw_texture_rect(cardList[board[x][y][0]], Rect2(width - y * 100 - 50, x * 75, 100, 100), false)
				else:
					node.draw_texture_rect(cardList[board[x][y][0]], Rect2(width - y * 100, x * 75, 100, 100), false)


#get the number of hings that are selected twice
func numSel():
	var number = 0
	for x in range(5):
		for y in range(3):
			if(square[x][y].getPressed()):
				number += 1
	print(number)
	return number


#clears board of selections
func wipeSel():
	var stuff = []
	for x in range(5):
		for y in range(3):
			if(square[x][y].getPressed()):
				stuff.append(Vector2(x, y))
			square[x][y].clear()
	return stuff


#clears board if the number of selections breaks a number. Default 2
func maxSel(var type, var top = 2):
	if(numSel() >= top):
		var selected = wipeSel()
		if(type == 1):
			if(#board[selected[0].x][selected[0].y][0] !=  board[selected[1].x][selected[1].y][0] &&
				(board[selected[0].x][selected[0].y][0] == 0 || board[selected[1].x][selected[1].y][0] == 0)):
				print("oi")
				return [1, selected[0], selected[1]]
	print("null")
	return null