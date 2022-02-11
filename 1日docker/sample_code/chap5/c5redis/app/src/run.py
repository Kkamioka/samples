import redis
REDIS = redis.Redis(host='db', port=6379, db=0)

def get(key):
  value = REDIS.get(key)
  if value is None:
    raise Exception('key does not exist')
  return value.decode()

def post(key, value):
  if REDIS.get(key) is not None:
    raise Exception('key already exists')
  REDIS.set(key, value)

def put(key, value):
  REDIS.set(key, value)

def delete(key):
  if REDIS.get(key) is None:
    raise Exception('key does not exist')
  REDIS.delete(key)

post('apple', 'red')
post('banana', 'yellow')
value = get('apple')
print(value) # => red
put('apple', 'green')
delete('banana')