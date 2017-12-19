import numpy as np
import scipy.misc as smp
from collections import deque
from math import ceil

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

	def mid(self, e):
		if self.dim <= 0:
			return
		if self.dim%2 == 0:
			self.pile[int(self.dim/2)][int(self.dim/2)] = e
			self.pile[int(self.dim/2)][int((self.dim/2)-1)] = e
			self.pile[int((self.dim/2)-1)][int(self.dim/2)] = e
			self.pile[int((self.dim/2)-1)][int((self.dim/2)-1)] = e
		if self.dim%2 == 1:
			self.pile[int(self.dim/2), int(self.dim/2)] = e

	def set(self, x, y, e):
		if x > 0 and y > 0 and x < self.dim and y < self.dim:
			self.pile[x,y] = e

	def max(self):
		self.pile = np.full((self.dim,self.dim), 3, dtype=np.int)

	def normalize(self):
		q = deque() # contains only indices with value >3

		sym_hor = True;
		sym_vert = True;
		print("Sandpile dimension is " + str(self.dim));

		# determine is sandpile is symmetric
		for i in range(self.dim):
			for j in range(self.dim):
				if i < int(self.dim/2):
					sym_hor &= self.pile[i,j] == self.pile[self.dim-1-i,j];
				if j < int(self.dim/2):
					sym_vert &= self.pile[i,j] == self.pile[i,self.dim-1-j];

		print("Sandpile is " + (""if sym_hor else "not") + "horizontally symmetric");
		print("Sandpile is " + (""if sym_hor else "not") + "vertically symmetric");

		# Look for values greater than 3, put those indices into a queue
		axis_hor = int(ceil(self.dim)/2) if sym_hor else self.dim-1
		axis_vert = int(ceil(self.dim)/2) if sym_vert else self.dim-1
		for i in range(axis_hor+1):
			for j in range(axis_vert+1):
				if self.pile[i,j] > 3:
					q.append((i,j))
		print("Populated the queue with initial indexes, considering a " + str(axis_hor+1) + " by " + str(axis_vert+1) + " grid");

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
				if a < self.dim-1 and (not sym_hor or a < ceil(float(self.dim)/2)):
					if a+1 == ceil(self.dim/2):
						self.pile[a+1,b] += inc
					self.pile[a+1,b] += inc
					if self.pile[a+1,b] > 3:
						q.append((a+1,b))
				if b < self.dim-1 and (not sym_vert or b < ceil(float(self.dim)/2)):
					if b+1 == ceil(self.dim/2):
						self.pile[a,b+1] += inc	
					self.pile[a,b+1] += inc
					if self.pile[a,b+1] > 3:
						q.append((a,b+1))

		print("Toppled all entries, no more values > 3");

		# copy entries due to symmetry
		if sym_hor:
			for i in range(axis_hor+1):
				for j in range(self.dim):
					self.pile[self.dim-1-i,j] = self.pile[i,j]
			print("Copied values due to horizontal symmetry");

		if sym_vert: 
			for i in range(self.dim):
				for j in range(axis_vert+1):
					self.pile[i,self.dim-1-j] = self.pile[i,j]
			print("Copied values due to vertical symmetry");
		
	def toFractal(self, path):
		self.normalize()
		print("Finished Normalizing...")
		data = np.zeros((self.dim, self.dim, 3), dtype=np.uint8)
		for i in range (self.dim):
			for j in range(self.dim):
				data[i,j] = colors[self.pile[i,j]]
		smp.imsave(path, data)


size = 257
sand = 10000
path = "fractal.png"
pile = Sandpile(size)
pile.mid(sand)
print("Building Fractal...")
pile.toFractal(path)