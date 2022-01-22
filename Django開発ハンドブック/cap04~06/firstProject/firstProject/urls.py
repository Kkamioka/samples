from django.contrib import admin
from django.urls import path, include  # includeを追加
urlpatterns = [
    path('admin/', admin.site.urls),
    
    # http(s)://<ホスト名>/以下のURLパターンにblogアプリの
    # URLConf(urls.py)を含める
    path('', include('blog.urls')),
]
