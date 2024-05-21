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
    # Send a GET request to the API endpoint to retrieve real-time vehicle positions
    response = requests.get('https://otd.delhi.gov.in//api/realtime/VehiclePositions.pb?key=VHbR9yEQLzXkcD2WQK819EyNmjZ3CrI0')

    # Check if the request was successful before parsing the response
    if response.status_code == 200:
        # Parse the response content as a FeedMessage object
        feed = gtfs_realtime_pb2.FeedMessage()
        feed.ParseFromString(response.content)

        # Initialize variables to store the minimum distance and related information
        min_distance = float('inf')
        closest_vehicle_coord = None
        closest_vehicle_id = None
        closest_route_id = None

        # Iterate through each entity in the feed
        for entity in feed.entity:
            # Check if the entity contains vehicle and position fields
            if entity.HasField('vehicle') and entity.vehicle.HasField('position'):
                # Extract the vehicle's coordinate, id, and route id
                vehicle_coord = (entity.vehicle.position.latitude, entity.vehicle.position.longitude)
                vehicle_id = entity.id
                route_id = entity.vehicle.trip.route_id

                # Calculate the distance between the given coordinate and the vehicle's coordinate
                distance = geodesic(coord, vehicle_coord).kilometers

                # If the distance is smaller than the current minimum distance, update the minimum distance and related information
                if distance < min_distance:
                    min_distance = distance
                    closest_vehicle_coord = vehicle_coord
                    closest_vehicle_id = vehicle_id
                    closest_route_id = route_id

        # Return the minimum distance, closest vehicle coordinate, closest vehicle id, and closest route id
        return min_distance, closest_vehicle_coord, closest_vehicle_id, closest_route_id
    else:
        # If the request failed, print the status code and return None
        print(f"Request failed with status code {response.status_code}")
        return None
