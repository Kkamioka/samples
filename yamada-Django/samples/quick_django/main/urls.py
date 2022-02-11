from django.urls import path

from . import views
from django.urls import path, re_path
from django.views.generic import TemplateView

urlpatterns = [
  # Part2
  path('', views.index, name='index'),
  path('temp', views.temp, name='temp'),
  path('list', views.list, name='list'),

  # Part3
  path('iftag', views.iftag, name='iftag'),
  path('yesno', views.yesno, name='yesno'),
  path('firstof', views.firstof, name='firstof'),
  path('forloop', views.forloop, name='forloop'),
  path('forempty', views.forempty, name='forempty'),
  path('fortag', views.fortag, name='fortag'),
  path('ifchanged', views.ifchanged, name='ifchanged'),
  path('regroup', views.regroup, name='regroup'),
  path('cycle', views.cycle, name='cycle'),
  path('escape', views.escape, name='escape'),
  path('temptag', views.temptag, name='temptag'),
  path('verbatim', views.verbatim, name='verbatim'),
  path('master', views.master, name='master'),
  path('include', views.include, name='include'),
  path('strformat', views.strformat, name='strformat'),
  path('slice', views.slice, name='slice'),
  path('lists', views.lists, name='lists'),
  path('date_time', views.date_time, name='date_time'),
  path('static', views.static, name='static'),

  # Part4
  path('filter', views.filter, name='filter'),
  path('exclude', views.exclude, name='exclude'),
  path('get', views.get, name='get'),
  path('filter_or', views.filter_or, name='filter_or'),
  path('filter_other', views.filter_other, name='filter_other'),
  path('groupby', views.groupby, name='groupby'),
  path('union', views.union, name='union'),
  path('raw', views.raw, name='raw'),  
  path('rel', views.rel, name='rel'),
  path('rel2', views.rel2, name='rel2'),

  # Part5
  path('route_param', views.route_param, name='route_param'),
  path('route_param/<int:id>', views.route_param, name='route_param'),

  # re_path('^route_param/(?P<id>[0-9]{2,3})$',
  #   views.route_param, name='route_param'),
  
  path('search/<path:keywd>', views.search, name='search'),
  path('req_query', views.req_query, name='req_query'),
  path('req_header', views.req_header, name='req_header'),
  path('req_redirect', views.req_redirect, name='req_redirect'),
  path('details/<int:id>', views.details, name='details'),
  path('res_notfound', views.res_notfound, name='res_notfound'),
  path('res_notfound', views.res_notfound, name='res_notfound'),
  path('res_header', views.res_header, name='res_header'),
  path('res_csv', views.res_csv, name='res_csv'),
  path('res_json', views.res_json, name='res_json'),
  path('res_file', views.res_file, name='res_file'),
  path('setcookie', views.setcookie, name='setcookie'),
  path('getcookie', views.getcookie, name='getcookie'),
  path('setsession', views.setsession, name='setsession'),
  path('getsession', views.getsession, name='getsession'),
  path('temp_view', views.MyTempView.as_view(), name='temp_view'),

  # path('temp_view', TemplateView.as_view(
  #   template_name='main/temp.html', extra_context={ 'msg': 'こんにちは、世界！！' }
  # ), name='temp_view'),

  # Part6
  path('form_input', views.form_input, name='form_input'),
  path('form_process', views.form_process, name='form_process'),
  path('upload_input', views.upload_input, name='upload_input'),
  path('upload_process', views.upload_process, name='upload_process'),

  # Part7
  path('crud_new', views.crud_new, name='crud_new'),
  path('crud_create', views.crud_create, name='crud_create'),
  path('crud_edit/<int:id>', views.crud_edit, name='crud_edit'),
  path('crud_update/<int:id>', views.crud_update, name='crud_update'),
  path('crud_show/<int:id>', views.crud_show, name='crud_show'),
  path('crud_delete/<int:id>', views.crud_delete, name='crud_delete'),
]