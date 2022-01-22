from django.http import HttpResponse

def index(request):
    message = 'Hello, world!'
    return HttpResponse(message)

def page(request, page_id):
    message = 'ページ' + str(page_id)
    return HttpResponse(message)
