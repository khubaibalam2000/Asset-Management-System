from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from pymongo import MongoClient

cluster = MongoClient("mongodb+srv://kukudb:kukudb@my-cluster.gdjpc4s.mongodb.net/?retryWrites=true&w=majority")
db = cluster["My-Cluster"]
collection = db["assets"]


def getAlertsForLower(request):
    lowDefense = ['password', 'pin', 'OTP', 'updates']
    result = collection.find({'level': { '$gte': 8 }})
    lowDefenseObjects = []
    for i in result:
        if i['currentdefense'] in lowDefense:
            lowDefenseObjects.append(i)
    return JsonResponse(lowDefenseObjects, safe=False)

def getAlertsForHigher(request):
    highDefense = ['encryption', 'firewall', 'MFA']
    result = collection.find({'level': { '$lte': 5 }})
    highDefenseObjects = []
    for i in result:
        if i['currentdefense'] in highDefense:
            highDefenseObjects.append(i)
    return JsonResponse(highDefenseObjects, safe=False)