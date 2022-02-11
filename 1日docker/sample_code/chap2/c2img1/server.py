import flask
app = flask.Flask('app server')

@app.route('/')
def index():
  return 'Hello Docker'

app.run(debug=True, host='0.0.0.0', port=80)