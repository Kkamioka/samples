from .models import Article
from django.views import generic

class IndexView(generic.ListView):
    model = Article
    template_name = 'index.html'

    def get_queryset(self):
        return Article.objects.order_by('-created_at')[:5]


class ArticleView(generic.DetailView):
    model = Article
    template_name = 'article.html'


index = IndexView.as_view()
article = ArticleView.as_view()
