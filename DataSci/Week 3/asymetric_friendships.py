import MapReduce
import sys

"""
Asymetric friendship Example in the Simple Python MapReduce Framework
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    rectemp = sorted(record)
    mr.emit_intermediate(rectemp[0] + ";" + rectemp[1], record)

def reducer(key, list_of_values):
  if len(list_of_values) == 1:
    key = key.split(";")
    mr.emit((key[0], key[1]))
    mr.emit((key[1], key[0]))

# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
