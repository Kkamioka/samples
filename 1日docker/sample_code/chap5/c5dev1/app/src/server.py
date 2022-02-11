import os, flask
PORT = int(os.environ['PORT'])
app = flask.Flask('c5dev1_app')
@app.route('/')
def index():
  return 'Hello Compose Flask'
app.run(debug=True, host='0.0.0.0', port=PORT)