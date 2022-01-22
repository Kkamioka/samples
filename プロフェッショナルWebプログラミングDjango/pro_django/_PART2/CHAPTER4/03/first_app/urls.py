from django.urls import path
from . import views

app = 'first_app'
urlpatterns = [
    path('', views.index, name='index'),
    path('article/<int:pk>/', views.article, name='article'),
    path('article/new/', views.new, name='new'),
]
