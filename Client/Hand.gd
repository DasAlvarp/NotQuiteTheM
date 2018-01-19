extends Node

var hand = []
var cardList = []
var cu = load("res://CardUtiler.gd")
var CardUtiler

func _init(var hand):
	self.hand = hand
	CardUtiler = cu.new()
	cardList = CardUtiler.getCardList()

func set_hand(var hand):
	self.hand = hand

func draw(var node):
	var increment = Globals.get("display/width") / cardList.size()
	var height = Globals.get("display/height")
	
	for x in range(0, hand.size()):
		node.draw_texture_rect(cardList[hand[x]], Rect2(x * 100, height - 100 , 100, 100), false)