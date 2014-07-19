import MapReduce
import sys

"""
Friend Count Example in the Simple Python MapReduce Framework
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
    personA = record[0]
    mr.emit_intermediate(personA, 1)

def reducer(key, list_of_values):
  person = key
  no_of_friends = len(list_of_values)
  mr.emit((person, no_of_friends))

# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
