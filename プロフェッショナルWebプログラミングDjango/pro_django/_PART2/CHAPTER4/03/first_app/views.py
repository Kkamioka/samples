from .models import Article
from django.views import generic
from .forms import ArticleForm
from django.contrib.auth.mixins import LoginRequiredMixin

class IndexView(generic.ListView):
    model = Article
    template_name = 'index.html'

    def get_queryset(self):
        return Article.objects.order_by('-created_at')[:5]


class ArticleView(generic.DetailView):
    model = Article
    template_name = 'article.html'


class NewView(LoginRequiredMixin, generic.CreateView):
    form_class = ArticleForm
    template_name = 'form.html'
    success_url = '/'
    login_url = '/admin/'


index = IndexView.as_view()
article = ArticleView.as_view()
new = NewView.as_view()
