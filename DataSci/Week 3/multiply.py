import MapReduce
import sys

"""
Matrix Multiply Example in the Simple Python MapReduce Framework
Assuming size 5x5
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
  matrix = record[0]
  if matrix == 'a':
    for j in range(0,5):
      mr.emit_intermediate((record[1], j), record)
  else:
    for i in range(0,5):
      mr.emit_intermediate((i, record[2]), record)

def reducer(key, list_of_values):
  sum = 0
  temp = {}
  for l in range(0,5):
    temp[l] = [0, 0] # Value and count
  for record in list_of_values:
    matrix = record[0]
    i = record[1]
    j = record[2]
    value = record[3]
    if matrix == "a":
      product, count = temp[j][0], temp[j][1]
      if count == 0:
        temp[j] = [value, 1]
      else:
        temp[j] = [product * value, count + 1]
    else:
      product, count = temp[i][0], temp[i][1]
      if count == 0:
        temp[i] = [value, 1]
      else:
        temp[i] = [product * value, count + 1]
  for v in temp:
    product, count = temp[v][0], temp[v][1]
    if count == 2:
      sum += product
  mr.emit((key[0], key[1], sum))

# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
