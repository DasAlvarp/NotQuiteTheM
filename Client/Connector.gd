extends Node

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
var node 

var host
var client
var hander = load("res://Hand.gd")
var hand
var board
var otherBoard
var boarder = load("res://Board.gd")

func _ready():
	node = get_node(".")
	client = StreamPeerTCP.new()
	client.connect("localhost", 2003)
	client.set_big_endian(true)
	board = boarder.new(true)
	otherBoard = boarder.new(false)
	var handCards = [1,1,1,1] #sample start hand
	
	hand = hander.new(handCards)
	set_process(true)
	set_process_input(true)


#janky click detection, but it seems like the most consistent way
func _input(ev):
	var mouse = CircleShape2D.new()
	mouse.set_radius(1)
	var mouse_pos = get_global_mouse_pos()
	var mPos = Vector2(mouse_pos.x, mouse_pos.y)
	var selSquares = board.onInput(node, mouse, mPos)
	otherBoard.onInput(node, mouse, mPos)
	#client.put_utf8_string("Words plase work\n")
	
	if(selSquares != null):
		print("sent")
		
		client.put_32(selSquares[0])
		for x in range(1, selSquares.size()):
			client.put_32(int(selSquares[x].x))
			client.put_32(int(selSquares[x].y))
		client.put_32(-1)#over command


#mostly reading data. Some updating
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
		elif(boardState == 3):
			updateHand(rawNumbers)


#sets cards in hand to what you're given
func updateHand(var rawNumbers):
	hand.set_hand(rawNumbers)
	update()


#updates boards to sent board states.
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


#converts the byte list to a board state.
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
		else:
			tempBoard[x][y].append(rawNumbers[index])
			#everything else is added to thte board object
		index += 1
	return tempBoard


#very simple draw function
func _draw():
	board.draw(node)
	otherBoard.draw(node)
	hand.draw(node)