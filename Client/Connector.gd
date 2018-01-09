extends Node

# class member variables go here, for example:
# var a = 2
# var b = "textvar"

func _ready():
	# Called every time the node is added to the scene.
	# Initialization here
	var client = StreamPeerTCP.new()
	var host = client.connect("localhost", 2003)
	pass
