extends Node

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
var host
var client

var board
var boarder = load("res://Board.gd")

func _ready():
	# Called every time the node is added to the scene.
	# Initialization here
	client = StreamPeerTCP.new()
	client.connect("localhost", 2003)
	client.set_big_endian(true)
	board = boarder.new()
	set_process(true)

func _process(delta):
	if(client.is_connected() && client.get_available_bytes() > 0):
		var length = client.get_available_bytes()
		var rawNumbers = []
		
		
		for characters in range(length / 4):
			rawNumbers.append(client.get_32())
			
		for number in rawNumbers:
			print(number)
		
		var tempBoard = []
		tempBoard.resize(5)
		for x in range(5):
			tempBoard[x] = []
			tempBoard[x].resize(3)
			for y in range(3):
				#looks like we're gonna have to load every card in the game first, to save memory. Or at least a decent chunk, so ppl can't chet. Maybe every single one in the used resources? Idk. Maybe just decks are enough.
				tempBoard[x][y] = []
		
		#now we've got all the numbers. Time to do stuff the hard way
		var x = 0
		var y = 0
		
		for index in range(length / 4):
			if(rawNumbers[index] < 0):
				#negative numbers means we have new xs and ys
				x = rawNumbers[index] * -1 - 1
				y = rawNumbers[index + 1] * -1 - 1
				index = index + 1
			else:
				tempBoard[x][y].append(rawNumbers[index])
			#everything else is added to thte board object
		
		self.board.set_board(tempBoard)
		_draw()

func _draw():
	board.draw(get_node("."))
	