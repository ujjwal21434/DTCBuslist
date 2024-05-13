'''from geopy.distance import geodesic
from google.transit import gtfs_realtime_pb2
import requests

def find_closest_vehicle(coord):
    response = requests.get('https://otd.delhi.gov.in//api/realtime/VehiclePositions.pb?key=VHbR9yEQLzXkcD2WQK819EyNmjZ3CrI0')
    feed = gtfs_realtime_pb2.FeedMessage()
    feed.ParseFromString(response.content)


    min_distance = float('inf')
    closest_coord = None
    closest_vehicle_id = None
    closest_route_id = None


    for entity in feed.entity:
        if entity.HasField('vehicle') and entity.vehicle.HasField('position'):

            lat = entity.vehicle.position.latitude
            lon = entity.vehicle.position.longitude
            vehicle_id = entity.id
            route_id = entity.vehicle.trip.route_id


            distance = geodesic(coord, (lat, lon)).kilometers


            if distance < min_distance:
                min_distance = distance
                closest_coord = (lat, lon)
                closest_vehicle_id = vehicle_id
                closest_route_id = route_id


    return min_distance, closest_coord, closest_vehicle_id, closest_route_id'''


