import numpy as np
import scipy.misc as smp
from collections import deque

colors = {1 : [255,0,0],
		2 : [0,255,255],
		3 : [0,0,255],
		0 : [0, 0, 0]}

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
		q = deque() # contains only indices with value >3

		# Look for values greater than 3, put those indices into a queue
		for i in range(self.dim):
			for j in range(self.dim):
				if self.pile[i,j] > 3:
					q.append((i,j))

		while len(q) != 0:
			(a,b) = q.popleft()
			if self.pile[a,b] > 3:	
				inc = self.pile[a,b]/4
				self.pile[a,b] = self.pile[a,b]%4
				if a > 0:
					self.pile[a-1,b] += inc
					if self.pile[a-1,b] > 3:
						q.append((a-1,b))
				if b > 0:
					self.pile[a,b-1] += inc
					if self.pile[a,b-1] > 3:
						q.append((a,b-1))
				if a < self.dim-1:
					self.pile[a+1,b] += inc
					if self.pile[a+1,b] > 3:
						q.append((a+1,b))
				if b < self.dim-1:	
					self.pile[a,b+1] += inc
					if self.pile[a,b+1] > 3:
						q.append((a,b+1))

	def toFractal(self):
		self.normalize()
		print("Finished Normalizing...")
		data = np.zeros((self.dim, self.dim, 3), dtype=np.uint8)
		for i in range (self.dim):
			for j in range(self.dim):
				data[i,j] = colors[self.pile[i,j]]
		smp.imsave('rgb_fractal.png', data)

size = 2**8 + 1
mid = 2**7
testpile = Sandpile(size)
testpile.set(mid, mid, 2**14)
print("Building Fractal...")
testpile.toFractal()