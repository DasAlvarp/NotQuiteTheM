extends Node

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
var host
var client

var board
var otherBoard
var boarder = load("res://Board.gd")

func _ready():
	# Called every time the node is added to the scene.
	# Initialization here
	client = StreamPeerTCP.new()
	client.connect("localhost", 2003)
	client.set_big_endian(true)
	board = boarder.new(true)
	otherBoard = boarder.new(false)
	set_process(true)

func _process(delta):
	if(client.is_connected() && client.get_available_bytes() > 0):
		var length = client.get_available_bytes()

		var boardState = client.get_32()
		var msgState = client.get_32()

		#fill up the types, differentiate there
		var rawNumbers = []
		for characters in range(length / 4 - 2):
			rawNumbers.append(client.get_32())
		
		if(boardState == 1):
			updateBoards(rawNumbers)


func updateBoards(var rawNumbers):
	var myStuff = []
	var theirStuff = []
	var myBoard = true;
	for number in rawNumbers:
		if(number == 1000):#next board
			myBoard = false
		elif(myBoard):
			myStuff.append(number)
		else:
			theirStuff.append(number)

	self.board.set_board(byteToBoard(myStuff))
	self.otherBoard.set_board(byteToBoard(theirStuff))
	update();
	

func byteToBoard(var rawNumbers):
	var tempBoard = []
	tempBoard.resize(5)
	for x in range(5):
		tempBoard[x] = []
		tempBoard[x].resize(3)
		for y in range(3):
			tempBoard[x][y] = []
	
	#now we've got all the numbers. Time to do stuff the hard way
	var x = 0
	var y = 0
	
	var index = 0
	while index < rawNumbers.size():
		if(rawNumbers[index] < 0):
			#negative numbers means we have new xs and ys
			x = rawNumbers[index] * -1 - 1
			index += 1
			y = rawNumbers[index] * -1 - 1
			print(str(x) + ", " + str(y))
		else:
			print(rawNumbers[index])
			tempBoard[x][y].append(rawNumbers[index])
			#everything else is added to thte board object
		index += 1
	return tempBoard


func _draw():
	board.draw(get_node("."))
	otherBoard.draw(get_node("."))