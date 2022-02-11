import datetime

def log_middleware(get_response):
  def middleware(request):
    start = datetime.datetime.now()
    print(f'start: {request.path}: {start}')
    response = get_response(request)
    end = datetime.datetime.now()
    print(f'finish: {request.path}: {end}...{end - start}ms')
    return response
  return middleware