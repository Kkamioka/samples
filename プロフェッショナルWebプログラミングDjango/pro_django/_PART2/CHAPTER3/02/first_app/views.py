from django.http import HttpResponse
from django.template import loader

def article(request):
    template = loader.get_template('article.html')
    context = {
        'title': '記事のタイトル',
        'content': '記事の本文'
    }
    return HttpResponse(template.render(context, request))
