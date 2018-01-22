extends Node

var x
var y
var hitbox

var pressed = false

var width = 100
var height = 100


func clear():
	pressed = false
	

func getPressed():
	return pressed


func checkStuff(node, mouse, mPos):
	#responding to clicks
	if(hitbox.collide(Matrix32(), mouse, Matrix32().translated(mPos))):
		pressed = true
		#print("there's a mouse: " + str(x) + "," + str(y))
	

func _init(var olY, var olX):
	self.x = olX
	self.y = olY
	
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