from django.urls import path

from . import views

urlpatterns = [
    path('alertsld', views.getAlertsForLower, name='alerts.getAlertsForLower'),
    path('alertshd', views.getAlertsForHigher, name='alerts.getAlertsForHigher')
]