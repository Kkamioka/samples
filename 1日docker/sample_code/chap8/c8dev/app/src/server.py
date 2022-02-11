import flask, os, re, redis
from random import randint
REDIS = redis.Redis(host=os.environ['REDIS_HOST'], port=6379, db=0)

COLOR = f'rgb({randint(0, 255)},{randint(0, 255)},{randint(0, 255)})'
HTML = '''<!DOCTYPE html>
<html><body style="background-color:{}">
  <h1>Version:1, AccessCount:{}, HostName:{}</h1>
</body><html>'''

app = flask.Flask('app server')
@app.route('/', methods=['GET'])
def index():
  value = REDIS.get('count')
  count = 1 if value is None else int(value.decode())
  REDIS.set('count', str(count + 1))
  return HTML.format(COLOR, count, os.uname()[1])

app.run(debug=False, host='0.0.0.0', port=80)