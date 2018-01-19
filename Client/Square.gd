extends Node

var x
var y
var hitbox

var width = 100
var height = 100

func _input_event(viewport, event, shape_idx):
	pass

func _ready():
	set_process(true)
	set_process_input(true)


func _input(ev):
	#print("oi")
	pass
	
func checkStuff(node, mouse, mPos):
	#print("hi")
	if(hitbox.collide(Matrix32(), mouse, Matrix32().translated(mPos))):
		print("there's a mouse: " + str(mPos))
	

func _init(var x, var y):
	self.x = x
	self.y = y
	
	#points in the hexagon
	var points = []
	points.append(Vector2(y + width / 4, x))
	points.append(Vector2(y + 3 * width / 4, x))
	points.append(Vector2(y + width, x + height / 2))
	points.append(Vector2(y + 3 * width / 4, x + height))
	points.append(Vector2(y + width / 4, x + height))
	points.append(Vector2(y, x + height / 2))
	points = Vector2Array(points)
	
	var shape = ConvexPolygonShape2D.new()
	shape.set_points(points)
	hitbox = shape
	
func mouse_enter():
	print("Moused")