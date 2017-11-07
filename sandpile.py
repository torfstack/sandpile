import numpy as np

class Sandpile:
	"""Sandpile data structure, topple if entry >3"""

	def __init__(self, dim=3):
		self.dim = dim
		self.pile = np.zeros((dim,dim), dtype=np.int)

	def __str__(self):
		return str(self.pile)

	def set(self, x, y, e):
		if x > 0 and y > 0 and x < self.dim and y < self.dim:
			self.pile[x,y] = e

	def max(self):
		self.pile = np.full((self.dim,self.dim), 3, dtype=np.int)

	def normalize(self):
		modified = False
		for i in range(self.dim):
			for j in range(self.dim):
				if self.pile[i,j] > 3:
					modified = True
					self.pile[i,j] -= 4
					if i > 0:
						self.pile[i-1,j] += 1
					if j > 0:
						self.pile[i,j-1] += 1
					if i < self.dim-1:
						self.pile[i+1,j] += 1
					if j < self.dim-1:
						self.pile[i,j+1] += 1
		if modified:
			self.normalize()

	def add(self, other):
		"""TODO"""

testpile = Sandpile()
print(str(testpile))
testpile.max()
print(str(testpile))
testpile.set(1,1,4)
print(str(testpile))
testpile.normalize()
print(str(testpile))