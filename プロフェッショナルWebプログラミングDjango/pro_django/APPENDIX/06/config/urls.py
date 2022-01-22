from django.contrib import admin
from django.urls import path, re_path, include
from django.contrib.staticfiles.urls import static
from . import settings
from django.views.generic import RedirectView

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('timeline.urls')),
    path('accounts/email/', RedirectView.as_view(pattern_name='timeline:index')),
    path('accounts/inactive/', RedirectView.as_view(pattern_name='timeline:index')),
    path('accounts/confirm-email/', RedirectView.as_view(pattern_name='timeline:index')),
    path('accounts/password/change/', RedirectView.as_view(pattern_name='timeline:index')),
    re_path(r"^accounts/confirm-email/[^/]+/", RedirectView.as_view(pattern_name='timeline:index'), kwargs=None),
    path('accounts/', include('allauth.urls')),
    path('accounts/', include('accounts.urls')),
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
if settings.DEBUG:
    import debug_toolbar
    urlpatterns = [
        path('__debug__/', include(debug_toolbar.urls)),
    ] + urlpatterns
