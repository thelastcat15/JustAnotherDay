import json

def process_object(obj_id, obj):
    polygon = obj["polygon"]
    
    for pos in polygon:
      pos["x"] = round(pos["x"]) + round(obj["x"])
      pos["y"] = round(pos["y"]) + round(obj["y"])

    return {
        "id": obj_id,
        "polygon": polygon
    }

with open("town.json", "r") as file:
    data = json.load(file)

object_groups = data["tiles"]

processed_objects = [process_object(obj["id"], obj["objectgroup"]["objects"][0]) for obj in object_groups]

for obj in processed_objects:
    print(obj)
