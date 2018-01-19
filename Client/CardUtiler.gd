extends Node

var cardList = []

func _init():
	for x in range(0, 2):
		cardList.append(load(getImagePath(x)))

func getCardList():
	return cardList

func getImagePath(var id):
	if(id == 0):
		return "res://img/Blank.png"
	elif(id == 1):
		return "res://img/IceSoldier.png"
	else:
		return "err"