from geopy.distance import geodesic
from google.transit import gtfs_realtime_pb2
import requests

def find_closest_vehicle(coord: tuple) -> tuple:
    """
    Finds the closest vehicle to the given coordinate.

    :param coord: The coordinate to find the closest vehicle to.
    :return: A tuple containing the distance to the closest vehicle,
             the coordinate of the closest vehicle,
             the id of the closest vehicle,
             and the id of the closest route.
    """
    response = requests.get('https://otd.delhi.gov.in//api/realtime/VehiclePositions.pb?key=VHbR9yEQLzXkcD2WQK819EyNmjZ3CrI0')

    # Check if the request was successful before parsing the response
    if response.status_code == 200:
        feed = gtfs_realtime_pb2.FeedMessage()
        feed.ParseFromString(response.content)

        min_distance = float('inf')
        closest_vehicle_coord = None
        closest_vehicle_id = None
        closest_route_id = None

        for entity in feed.entity:
            if entity.HasField('vehicle') and entity.vehicle.HasField('position'):
                vehicle_coord = (entity.vehicle.position.latitude, entity.vehicle.position.longitude)
                vehicle_id = entity.id
                route_id = entity.vehicle.trip.route_id

                distance = geodesic(coord, vehicle_coord).kilometers

                if distance < min_distance:
                    min_distance = distance
                    closest_vehicle_coord = vehicle_coord
                    closest_vehicle_id = vehicle_id
                    closest_route_id = route_id

        return min_distance, closest_vehicle_coord, closest_vehicle_id, closest_route_id
    else:
        print(f"Request failed with status code {response.status_code}")
        return None
