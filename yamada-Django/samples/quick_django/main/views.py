import csv
import random
import tempfile
import urllib.parse
from datetime import date
from datetime import datetime

from django.contrib import messages
from django.db.models import Q
from django.db.models import Count
from django.db.models.functions import Substr

from django.http import FileResponse
from django.http import Http404
from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import get_object_or_404
from django.shortcuts import redirect
from django.shortcuts import render
from django.urls import reverse
from django.views.decorators.http import require_GET
from django.views.decorators.http import require_POST
from django.views.generic import TemplateView

from .forms import BookForm
from .forms import  BookModelForm
from .forms import UploadForm
from .models import Book

# Part2
def index(request):
  return HttpResponse('こんにちは、世界！')

def temp(request):
  context = {
    'msg' : 'こんにちは、世界！'
  }
  return render(request, 'main/temp.html', context)

def list(request):
  books = Book.objects.all() 
  return render(request, 'main/list.html', {
    'books': books
  })

# Part3
def iftag(request):
  return render(request, 'main/iftag.html', {
    'random': random.randint(0, 100)
  })

def yesno(request):
  return render(request, 'main/yesno.html', {
    'flag': True
  })

def firstof(request):
  return render(request, 'main/firstof.html', {
    # 'a': 'おはようございます！',
    # 'b': 'こんにちは！',
    # 'c': 'こんばんは！'
  })

def forloop(request):
  return render(request, 'main/forloop.html', {
    'weeks' : [ '月', '火', '水', '木', '金', '土', '日' ]
  })

def forempty(request):
  return render(request, 'main/forempty.html', {
    'members' : [ '鈴木三郎', '佐藤洋子', '山田次郎' ]
  })

def fortag(request):
  return render(request, 'main/fortag.html')

def ifchanged(request):
  return render(request, 'main/ifchanged.html', {
      'schedule': [
        (10, 'A企画反省会'),
        (10, 'B書籍脱稿'),
        (15, 'WINGS定例会議'),
        (30, 'C企画打ち合わせ'),
      ]
  })

def regroup(request):
  return render(request, 'main/regroup.html', {
    'members': [
      { 'name': '鈴木三郎', 'sex': '男', 'birth': '1980-12-23' },
      { 'name': '山田次郎', 'sex': '男', 'birth': '1978-10-13' },
      { 'name': '佐藤健司', 'sex': '男', 'birth': '1976-04-06' },
      { 'name': '山本花子', 'sex': '女', 'birth': '1981-07-28' },
      { 'name': '田中久美', 'sex': '女', 'birth': '1980-09-07' },
    ]
  })

def cycle(request):
  return render(request, 'main/cycle.html', {
    'members': [
      { 'name': '鈴木三郎', 'sex': '男', 'birth': '1980-12-23' },
      { 'name': '山田次郎', 'sex': '男', 'birth': '1978-10-13' },
      { 'name': '佐藤健司', 'sex': '男', 'birth': '1976-04-06' },
      { 'name': '山本花子', 'sex': '女', 'birth': '1981-07-28' },
      { 'name': '田中久美', 'sex': '女', 'birth': '1980-09-07' },
    ]
  })

def escape(request):
  return render(request, 'main/escape.html', {
    'msg': '''<img src="https://wings.msn.to/image/wings.jpg" title="ロゴ" />
      <p>WINGSへようこそ</p>'''
  })

def temptag(request):
  return render(request, 'main/temptag.html')

def verbatim(request):
  return render(request, 'main/verbatim.html')

def master(request):
  return render(request, 'main/master.html', {
    'msg' : 'こんにちは、世界！',
  })

def include(request):
  return render(request, 'main/include.html', {
    'name' : '鈴木',
    'current': datetime.now(), 
  })

def slice(request):
  return render(request, 'main/slice.html', {
      'data': [ 'い', 'ろ', 'は', 'に', 'ほ', 'へ' ]
  })

def strformat(request):
  return render(request, 'main/strformat.html', {
      'data1': None,
      'data2': 'WINGS\nWww INtegrated Guide on Server-architecture',
      'data3': 'https://wings.msn.to/',
      'data4': [ 'い', 'ろ', 'は', 'に', 'ほ', 'へ' ],
      'data5': 987.654,
      'data6': 'hello django'
  })

def lists(request):
  return render(request, 'main/lists.html', {
      'data': [ 'いろは歌', [ 'い', 'ろ', 'は', 'に', 'ほ', 'へ','と' ] ]
  })

def date_time(request):  
  return render(request, 'main/date_time.html', {
    'today': datetime.now()
  })

def static(request):  
  return render(request, 'main/static.html')

# Part4
def filter(request):
  books = Book.objects.filter(publisher='翔泳社')

  # AND条件
  # books = Book.objects.filter(publisher='翔泳社', price=2800)

  # 大小比較
  # books = Book.objects.filter(price__lt = 3000)

  # 部分一致
  # books = Book.objects.filter(title__contains = '独習')

  # 正規表現比較
  # books = Book.objects.filter(title__regex = r'[0-9]+')

  # NULL比較
  # books = Book.objects.filter(title__isnull = True)

  # 範囲比較
  # books = Book.objects.filter(published__range = 
  #   (date(2018, 1, 1), date(2018, 12, 31)))

  # 候補比較
  # books = Book.objects.filter(publisher__in = ['翔泳社', '技術評論社', '日経BP'])

  # 日付比較（年／月／日）
  # books = Book.objects.filter(published__year = 2019)
  # books = Book.objects.filter(published__year__lte = 2019)
  # books = Book.objects.filter(published__week_day__range = (2, 6))

  return render(request, 'main/book_list.html', {
    'books': books
  })

def filter_or(request):
  # books = Book.objects.filter(publisher='翔泳社') \
  #   .filter(price__gte = 2800)

  # Qオブジェクトの基本
  # books = Book.objects.filter(
  #   Q(publisher='翔泳社') | Q(price__gte = 2800))

  # より複雑な条件式
  books = Book.objects.filter(
    ~Q(publisher='翔泳社')
    & (Q(published__year = 2018) | Q(published__year = 2020))
  )
  return render(request, 'main/book_list.html', {
    'books': books
  })

def filter_other(request):
  # 出版社（昇順）／刊行日（降順）に
  books = Book.objects.order_by('publisher', '-published')

  # ランダムに
  # books = Book.objects.order_by('?')

  # 取得範囲の指定
  # books = Book.objects.order_by('-price')[2:5]

  # 取得列の制約
  # books = Book.objects.values('title', 'price')

  # 書名から最初の5文字だけを取り出す
  # books = Book.objects.values('price', short_title=Substr('title', 1, 5))
  # return HttpResponse(books[0]['short_title'])

  # リストとして取得
  # books = Book.objects.values_list('title', flat = True)
  # return HttpResponse(books[0])

  return render(request, 'main/book_list.html', {
    'books': books
  })

def exclude(request):
  books = Book.objects.exclude(publisher='翔泳社')
  return render(request, 'main/book_list.html', {
    'books': books
  })

def get(request):
  book = Book.objects.get(pk=1)
  return render(request, 'main/book_detail.html', {
    'book': book
  })  

def groupby(request):
  return render(request, 'main/groupby.html', {
    'groups': Book.objects.values('publisher')
      .annotate(pub_count=Count('publisher')).order_by('-pub_count')
  })

  # cnt = Book.objects.filter(publisher='翔泳社').count()
  # return HttpResponse(cnt)

  # flag = Book.objects.filter(publisher='翔泳社').exists()
  # return HttpResponse(flag)
  
def union(request):
  b1 = Book.objects.filter(publisher = '翔泳社')
  b2 = Book.objects.filter(publisher = '技術評論社')
  return render(request, 'main/book_list.html', {
    'books' : b1.union(b2)
  })

def raw(request):
  books = Book.objects.raw(
    'SELECT * FROM main_book WHERE publisher = %s', ['翔泳社'])
  return render(request, 'main/book_list.html', {
    'books': books
  })  

def rel(request):
  return render(request, 'main/rel.html', {
    'book' : Book.objects.get(pk=1)
  })

def rel2(request):
  return render(request, 'main/rel2.html', {
    'books' : Book.objects.all()
  })

# Part5
def route_param(request, id=1):
  return HttpResponse(f'id値：{id}')

def search(request, keywd):
  return HttpResponse(f'パラメーター：{keywd}')

def req_query(request):
  return HttpResponse(f'id値：{request.GET["id"]}')

def req_header(request):
  return HttpResponse(f'Use-Agent： {request.headers["User-Agent"]}')

def req_redirect(request):
  # return redirect('list')
  # return redirect('http://wings.msn.to/', permanent=True)
  # return redirect('route_param', id=10)

  # path = reverse('route_param', kwargs={ 'id': 1 })
  # return HttpResponse(path)
  
  book = Book.objects.get(pk=1)
  return redirect(book)

def details(request, id):
  return HttpResponse(f'id値：{id}')

def res_notfound(request):
  # try:
  #   book = Book.objects.get(pk=108)
  # except Book.DoesNotExist:
  #   raise Http404('指定の書籍情報が存在しません。')

  book = get_object_or_404(Book, pk=108)
  return render(request, 'main/book_detail.html', {
    'book': book
  })

def res_header(request):
  response = HttpResponse('<message>Hello, world!!</message>',
    content_type='text/xml')
  # response = HttpResponse('<message>Hello, world!!</message>')
  response['Content-Disposition'] = 'attachment; filename="hoge.xml"' 
  # response['Content-Type'] = 'text/xml'
  return response

def res_csv(request):
  response = HttpResponse(content_type='text/csv')
  response['Content-Disposition'] = 'attachment; filename="data.csv"'
  writer = csv.writer(response)
  writer.writerows([
    ['tyamada', '山田太郎', '30'],
    ['ksuzuki', '鈴木健司', '26'],
    ['itanaka', '田中一郎', '34']
  ])
  return response

def res_json(request):
  return JsonResponse({
    'title' : '独習Python',
    'price' : 3200,
    'publisher' : '翔泳社',
    'published' : date(2020, 6, 23) 
  })
  # return JsonResponse(['Python', 'Ruby', 'PHP'], safe = False)

def res_file(request):
  return FileResponse(open('c:/data/wings.png', 'rb'),
    as_attachment=True, filename='download.png')

def setcookie(request):
  response = HttpResponse(render(request, 'main/setcookie.html'))
  response.set_cookie('app_title',
    urllib.parse.quote('速習Django'), 60 * 60 * 24 * 30, samesite='Strict')
  # return response
  return redirect('http://wings.msn.to/')

def getcookie(request):
  app_title = urllib.parse.unquote(request.COOKIES['app_title']) \
    if 'app_title' in request.COOKIES else '－'
  return render(request, 'main/getcookie.html', {
    'app_title': app_title
  })

def setsession(request):
  request.session['app_title'] = '速習Django'
  return HttpResponse('セッションを保存しました。')

def getsession(request):
  title = request.session['app_title'] \
    if 'app_title' in request.session else '－' 
  return HttpResponse(title)

class MyTempView(TemplateView):
  template_name = 'main/temp.html'
  def get_context_data(self, **kwargs):
    context = super().get_context_data(**kwargs)
    context['msg'] = 'こんにちは、世界！'
    return context

# Part6
def form_input(request):
  form = BookForm()
  return render(request, 'main/form_input.html', {
    'form': form
  })

@require_POST
def form_process(request):
  form = BookForm(request.POST)
  if form.is_valid():
    return render(request, 'main/form_process.html', {
      'form': form
    })
  else:
    return render(request, 'main/form_input.html', {
      'form': form
    })

def upload_input(request):
  form = UploadForm()
  return render(request, 'main/upload_input.html', {
    'form': form
  })

@require_POST
def upload_process(request):
  form = UploadForm(request.POST, request.FILES)
  if form.is_valid():
    file = form.cleaned_data['body']
    with open(file.name, 'wb+') as dest:
      for chunk in file.chunks():
        dest.write(chunk)
    return HttpResponse(f'{file.name}のアップロードに成功しました。')
  return HttpResponse('アップロードに失敗しました。')

# Part7
def crud_new(request):
  form = BookModelForm()
  return render(request, 'main/crud_new.html', {
    'form': form
  })

@require_POST
def crud_create(request):
  obj = Book()
  form = BookModelForm(request.POST, instance=obj)
  if form.is_valid():
    form.save()
    messages.add_message(request, messages.SUCCESS,
      'データの保存に成功しました。')
    return redirect('crud_new')
  else:
    return render(request, 'main/crud_new.html', {
      'form': form
    })

def crud_edit(request, id):
  obj = Book.objects.get(pk=id)
  form = BookModelForm(instance=obj)
  return render(request, 'main/crud_edit.html', {
    'id': id,
    'form': form
  })

@require_POST
def crud_update(request, id):
  obj = Book.objects.get(pk=id)
  form = BookModelForm(request.POST, instance=obj)
  if form.is_valid():
    form.save()
    messages.set_level(request, messages.WARNING)
    messages.success(request, 'データの更新に成功しました。')
    return redirect(reverse('crud_edit', kwargs={ 'id': id }))
  else:
    return render(request, 'main/crud_edit.html', {
      'id': id,
      'form': form
    })

def crud_show(request, id):
  obj = Book.objects.get(pk=id)
  form = BookModelForm(instance=obj)
  return render(request, 'main/crud_show.html', {
    'id': id,
    'form': form
  })

@require_POST
def crud_delete(request, id):
  obj = Book.objects.get(pk=id)
  obj.delete()
  messages.success(request, 'データの削除に成功しました。')
  return redirect(reverse('list'))