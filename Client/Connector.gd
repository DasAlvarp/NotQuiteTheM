extends Node

# class member variables go here, for example:
# var a = 2
# var b = "textvar"
var host
var client

func _ready():
	# Called every time the node is added to the scene.
	# Initialization here
	client = StreamPeerTCP.new()
	client.connect("localhost", 2003)
	set_process(true)

func _process(delta):
	if(client.is_connected() && client.get_available_bytes() > 0):
		var bytes = str(client.get_utf8_string(client.get_available_bytes()))
		print(bytes)