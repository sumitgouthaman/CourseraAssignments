import MapReduce
import sys

"""
Relational Join Example in the Simple Python MapReduce Framework
"""

mr = MapReduce.MapReduce()

# =============================
# Do not modify above this line

def mapper(record):
  # Each input record is a list of strings representing a tuple in the database.
  # Each list element corresponds to a different attribute of the table
  # The first item (index 0) in each record is a string that identifies the 
  # table the record originates from. This field has two possible values:

  # "line_item" indicates that the record is a line item.
  # "order" indicates that the record is an order.
  # The second element (index 1) in each record is the order_id.

  # LineItem records have 17 attributes including the identifier string.

  # Order records have 10 elements including the identifier string.
  
  order_id = record[1]
  mr.emit_intermediate(order_id, record)

def reducer(key, list_of_values):
  # The output should be a joined record: a single list of length 27 that 
  # contains the attributes from the order record followed by the fields from 
  # the line item record. Each list element should be a string.
  
  order_list = []
  line_item_list = []
  
  for record in list_of_values:
    if record[0] == "order":
      order_list.append(record)
    else:
      line_item_list.append(record)
  for order in order_list:
    for line_item in line_item_list:
      mr.emit(order + line_item)
  

# Do not modify below this line
# =============================
if __name__ == '__main__':
  inputdata = open(sys.argv[1])
  mr.execute(inputdata, mapper, reducer)
